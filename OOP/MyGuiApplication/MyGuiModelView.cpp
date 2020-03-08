#include "MyGuiModelView.h"
#include<qdebug.h>
#include<algorithm>
void MyGui::initComponents() {
	QHBoxLayout *mainL = new QHBoxLayout;
	setLayout(mainL);

	QVBoxLayout *leftL = new QVBoxLayout;
	QWidget *leftWnd = new QWidget;
	leftWnd->setLayout(leftL);
	leftL->addWidget(leftWnd);
	leftL->addWidget(Mylist);
	mainL->addWidget(leftWnd);

	QHBoxLayout *btnSortL = new QHBoxLayout;
	btnSortL->addWidget(sortByTitleBtn);
	btnSortL->addWidget(sortByTypeTimeBtn);
	btnSortL->addWidget(sortByDescriptBtn);
	QWidget *btnSortWnd = new QWidget;
	btnSortWnd->setLayout(btnSortL);
	leftL->addWidget(btnSortWnd);

	QHBoxLayout *btnRemoveL = new QHBoxLayout;
	btnRemoveL->addWidget(removeBtn);
	btnRemoveL->addWidget(addCosBtn);
	QWidget *btnRemoveWnd = new QWidget;
	btnRemoveWnd->setLayout(btnRemoveL);
	leftL->addWidget(btnRemoveWnd);


	QVBoxLayout *rightL = new QVBoxLayout;
	QWidget *rightWnd = new QWidget;
	rightWnd->setLayout(rightL);
	rightL->addWidget(rightWnd);

	QFormLayout *lineEdit = new QFormLayout;
	lineEdit->addRow(lblTitle, txtTitle);
	lineEdit->addRow(lblDescript, txtDescript);
	lineEdit->addRow(lblType, txtType);
	lineEdit->addRow(lblTime, txtTime);

	QWidget *wnd = new QWidget;
	wnd->setLayout(lineEdit);
	rightL->addWidget(wnd);
	mainL->addWidget(rightWnd);

	QHBoxLayout *btnAddUpdateL = new QHBoxLayout;
	btnAddUpdateL->addWidget(addBtn);
	btnAddUpdateL->addWidget(updateBtn);
	QWidget *btnAddUpdateWnd = new QWidget;
	btnAddUpdateWnd->setLayout(btnAddUpdateL);
	rightL->addWidget(btnAddUpdateWnd);

	QHBoxLayout *btnFilterL = new QHBoxLayout;
	btnFilterL->addWidget(filterByTypeBtn);
	btnFilterL->addWidget(filterByDescriptBtn);
	btnFilterL->addWidget(undoBtn);
	QWidget* btnUndoFilterWnd = new QWidget;
	btnUndoFilterWnd->setLayout(btnFilterL);
	rightL->addWidget(btnUndoFilterWnd);


	QFormLayout *cosL = new QFormLayout;
	cosL->addRow(lblCos, txtCos);

	QWidget *wndCos = new QWidget;
	wndCos->setLayout(cosL);
	rightL->addWidget(wndCos);
	rightL->addWidget(cosBtn);
	rightL->addWidget(cosReadOnly);
	rightL->addWidget(exitBtn);

	rightL->addStretch();
}
void MyGui::createList(vector<Activity> activities)
{
	model->setActivity(activities);
}

void MyGui::loadInitialState()
{
	removeBtn->setEnabled(false);
	addCosBtn->setEnabled(false);
	txtCos->setText(QString::fromStdString("0"));
}

void MyGui::initSignalsAndSlots()
{
	QObject::connect(exitBtn, &QPushButton::clicked, [&]() {
		QMessageBox::information(nullptr, "Close Window", "Are you sure?");
		this->setVisible(false);
	});

	QObject::connect(Mylist->selectionModel(), &QItemSelectionModel::selectionChanged, [&]() {
		removeBtn->setEnabled(true);
		addCosBtn->setEnabled(true);
		if (Mylist->selectionModel()->selectedIndexes().isEmpty()) {
			txtTitle->setText(" ");
			txtDescript->setText(" ");
			txtType->setText(" ");
			txtTime->setText(" ");
			return;
		}
		auto selIndex = Mylist->selectionModel()->selectedIndexes().at(0);
		QString str = selIndex.data(Qt::DisplayRole).toString();
		QStringList strList = str.split(' ');

		txtTitle->setText(strList[0]);
		txtDescript->setText(strList[1]);
		txtType->setText(strList[2]);
		txtTime->setText(strList[3]);
	});

	QObject::connect(removeBtn, &QPushButton::clicked, this, &MyGui::deleteGUI);
	QObject::connect(addBtn, &QPushButton::clicked, this, &MyGui::adaugaGUI);
	QObject::connect(updateBtn, &QPushButton::clicked, this, &MyGui::updateGUI);

	QObject::connect(sortByTitleBtn, &QPushButton::clicked, this, &MyGui::SortByTitleGUI);
	QObject::connect(sortByTypeTimeBtn, &QPushButton::clicked, this, &MyGui::SortByTypeGUI);
	QObject::connect(sortByDescriptBtn, &QPushButton::clicked, this, &MyGui::SortByDescriptGUI);


	QObject::connect(filterByDescriptBtn, &QPushButton::clicked, this, &MyGui::FilterByDescriptGUI);
	QObject::connect(filterByTypeBtn, &QPushButton::clicked, this, &MyGui::FilterByTypeGUI);

	QObject::connect(addCosBtn, &QPushButton::clicked, this, &MyGui::addCosGUI);
	QObject::connect(cosBtn, &QPushButton::clicked, this, [&]() {
		CosWnd* cosWnd = new CosWnd{ serv,serv.getCos() };
		cosWnd->show();
	});
	QObject::connect(cosReadOnly, &QPushButton::clicked, this, [&]() {
		CosReadOnlyGUI* cosReadOnly = new CosReadOnlyGUI{ serv.getCos() };
		cosReadOnly->show();
	});

	QObject::connect(undoBtn, &QPushButton::clicked, [&]() {
		try {
			serv.undo();
			createList(serv.getAll());
		}
		catch (RepoException& ex) {
			QMessageBox::warning(nullptr, "Warning", QString::fromStdString(ex.getmsg()));
		}

	});
}

void MyGui::deleteGUI()
{
	try {
		serv.removeActivity(txtTitle->text().toStdString());
		createList(serv.getAll());
	}
	catch (RepoException& ex) {
		QMessageBox::warning(nullptr, "Warning", QString::fromStdString(ex.getmsg()));
	}
}

void MyGui::adaugaGUI()
{
	try {
		string title = txtTitle->text().toStdString();
		string descript = txtDescript->text().toStdString();
		string type = txtType->text().toStdString();
		string t = txtTime->text().toStdString();
		double time = stof(t.c_str());
		serv.addActivity(title, descript, type, time);
	}
	catch (RepoException& ex) {
		QMessageBox::warning(nullptr, "Warning", QString::fromStdString(ex.getmsg()));
	}
	catch (ValidException& ex) {
		QMessageBox::warning(nullptr, "Warning", QString::fromStdString(ex.getmsg()));
	}
	catch (exception) {
		QMessageBox::warning(nullptr, "Warning", "Tip de date invalid!");
	}
	createList(serv.getAll());
	txtTitle->setText(" ");
	txtDescript->setText(" ");
	txtType->setText(" ");
	txtTime->setText(" ");
}

void MyGui::updateGUI()
{
	try {
		string title = txtTitle->text().toStdString();
		string descript = txtDescript->text().toStdString();
		string type = txtType->text().toStdString();
		string t = txtTime->text().toStdString();
		double time = stof(t.c_str());
		serv.updateActivity(title, descript, type, time);
	}
	catch (RepoException& ex) {
		QMessageBox::warning(nullptr, "Warning", QString::fromStdString(ex.getmsg()));
	}
	catch (ValidException& ex) {
		QMessageBox::warning(nullptr, "Warning", QString::fromStdString(ex.getmsg()));
	}
	catch (exception) {
		QMessageBox::warning(nullptr, "Warning", "Tip de date invalid!");
	}
	createList(serv.getAll());
	txtTitle->setText(" ");
	txtDescript->setText(" ");
	txtType->setText(" ");
	txtTime->setText(" ");
}

void MyGui::SortByTitleGUI()
{
	createList(sortByKey(serv.getAll(), cmpTitle));
}

void MyGui::SortByDescriptGUI()
{
	createList(sortByKey(serv.getAll(), cmpDescript));
}

void MyGui::SortByTypeGUI()
{
	vector<Activity> sort = sortByKey(serv.getAll(), cmpType);
	createList(sortByKey(sort, cmpTime));
}

void MyGui::FilterByDescriptGUI()
{
	FilterWnd* filterWnd = new FilterWnd{ serv };
	filterWnd->show();
	this->setWindowModality(Qt::WindowModal);
}

void MyGui::FilterByTypeGUI()
{
	bool ok;
	QString text = QInputDialog::getText(this, tr("FilterByType"),
		tr("Type:"), QLineEdit::Normal, QString::null, &ok);
	if (ok && !text.isEmpty()) {
		string txt = text.toStdString();
		model->setFilterType(txt);
	}
}

void MyGui::addCosGUI()
{
	string title = txtTitle->text().toStdString();
	serv.addMyList(title);
	int count = serv.getMyList().size();
	string count_str = to_string(count);
	txtCos->setText(QString::fromStdString(count_str));
}
