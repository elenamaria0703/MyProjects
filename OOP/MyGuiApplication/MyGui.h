#pragma once

#include"Service.h"
#include<vector>
#include<string>
#include <QtWidgets/QApplication>
#include<qlabel.h>
#include<qpushbutton.h>
#include<qlistwidget.h>
#include <QHBoxLayout> 
#include <QFormLayout>
#include <QLineEdit> 
#include <QObject> 
#include<qmessagebox.h>
#include<qformlayout.h>
#include<qinputdialog.h>
#include<qpainter>

class MyGui: public QWidget,public Observer{
	Service& serv;
	QListWidget *Mylist = new QListWidget;

	QPushButton *exitBtn = new QPushButton("Exit");
	QPushButton *addBtn = new QPushButton("Add");
	QPushButton *removeBtn = new QPushButton("Remove");
	QPushButton *addCosBtn = new QPushButton("AddCos");
	QPushButton *updateBtn = new QPushButton("Update");
	QPushButton *undoBtn = new QPushButton("Undo");
	QPushButton *sortByTypeTimeBtn = new QPushButton("SortByType");
	QPushButton *sortByDescriptBtn = new QPushButton("SortByDescription");
	QPushButton *sortByTitleBtn = new QPushButton("SortByTitle");
	QPushButton *filterByTypeBtn = new QPushButton("FilterByType");
	QPushButton *filterByDescriptBtn = new QPushButton("FilterByDescript");
	QPushButton *cosBtn = new QPushButton("Afiseaza Cos");
	QPushButton *cosReadOnly = new QPushButton("CosReadOnly");

	QLineEdit *txtTitle = new QLineEdit;
	QLineEdit *txtDescript = new QLineEdit;
	QLineEdit *txtType = new QLineEdit;
	QLineEdit *txtTime = new QLineEdit;
	QLineEdit *txtCos = new QLineEdit;

	QLabel *lblTitle = new QLabel("Title");
	QLabel *lblDescript = new QLabel("Description");
	QLabel *lblType = new QLabel("Type");
	QLabel *lblTime = new QLabel("Time");
	QLabel *lblCos = new QLabel("Cos");
	
	
	void initComponents();
	void initSignalsAndSlots();
	void createList(vector<Activity> activities);
	void loadInitialState();

	void deleteGUI();
	void adaugaGUI();
	void updateGUI();

	void SortByTitleGUI();
	void SortByDescriptGUI();
	void SortByTypeGUI();

	void FilterByDescriptGUI();
	void FilterByTypeGUI();

	void addCosGUI();

public:
	MyGui(Service& serv) :serv{ serv } {
		initComponents();
		initSignalsAndSlots();
		loadInitialState();
		serv.getCos().addObserver(this);
	};

	void update() override {
		int count = serv.getMyList().size();
		string count_str = to_string(count);
		txtCos->setText(QString::fromStdString(count_str));
	}
};

class FilterWnd :public QWidget {
	Service& serv;
	QLineEdit *txtFilter = new QLineEdit;
	QLabel* lblDescript = new QLabel("Description");
	QPushButton *filterBtn = new QPushButton("Filter");
	QPushButton *backFilter = new QPushButton("Back");
	QListWidget *filterList = new QListWidget;

	void initComponentsFilter() {
		QVBoxLayout* filterL = new  QVBoxLayout;
		QFormLayout* descriptL = new QFormLayout;
		descriptL->addRow(lblDescript, txtFilter);
		QWidget *wnd = new QWidget;
		wnd->setLayout(descriptL);
		filterL->addWidget(wnd);
		this->setLayout(filterL);
		filterL->addWidget(filterList);
		filterL->addWidget(filterBtn);
		filterL->addWidget(backFilter);

	}
	void initSignalsAndSlotsFilter() {
		QObject::connect(backFilter, &QPushButton::clicked, this, [&]() {
			this->close();
		});

		QObject::connect(filterBtn, &QPushButton::clicked, [=]() {
			filterList->clear();
			string descript = txtFilter->text().toStdString();
			vector<Activity> activities = serv.filterByString(descript, getDescript);
			for (const auto& act : activities) {
				std::string activity;
				activity = act.get_title();
				activity = activity + ' ' + act.get_descript() + ' ' + act.get_type() + ' ' + to_string(act.get_time());
				QListWidgetItem *item = new QListWidgetItem{ QString::fromStdString(activity) };
				filterList->addItem(item);
			}
			txtFilter->setText("");
		});
	}
public:
	FilterWnd(Service& serv) :serv{ serv } {
		initComponentsFilter();
		initSignalsAndSlotsFilter();
	}
};

class CosWnd : public QWidget ,public Observer{
	Cos& cos;
	Service& serv;
	QPushButton* golesteCosBtn = new QPushButton("Goleste Cos");
	QPushButton* creeazaCosBtn = new QPushButton("Creeaza Cos");
	QListWidget* CosList = new QListWidget;

	void initComponentsCos() {
		QVBoxLayout* cosL = new QVBoxLayout;
		setLayout(cosL);
		cosL->addWidget(golesteCosBtn);
		cosL->addWidget(creeazaCosBtn);
		cosL->addWidget(CosList);
	}
	void createList(vector<Activity> activities) {
		CosList->clear();
		for (const auto& act : activities) {
			std::string activity;
			activity = act.get_title();
			activity = activity + ' ' + act.get_descript() + ' ' + act.get_type() + ' ' + to_string(act.get_time());
			QListWidgetItem *item = new QListWidgetItem{ QString::fromStdString(activity) };
			CosList->addItem(item);
		}
	}

	void initSignalsAndSlotsCos() {
		QObject::connect(creeazaCosBtn, &QPushButton::clicked, [&]() {
			bool ok;
			QString text = QInputDialog::getText(this, tr("Creeaza Cos Random"),
				tr("Nr activitati:"), QLineEdit::Normal, QString::null, &ok);
			if (ok && !text.isEmpty()) {
				int nr = atoi(text.toStdString().c_str());
				serv.createRandom(nr);
				createList(serv.getMyList());
			}
		});
		QObject::connect(golesteCosBtn, &QPushButton::clicked, [&]() {
			serv.emptyMyList();
			CosList->clear();
		});
	}
public:
	CosWnd(Service& serv, Cos& c) :serv{ serv }, cos{ c } {
		initComponentsCos();
		initSignalsAndSlotsCos();
		createList(serv.getMyList());
		cos.addObserver(this);
	}
	void update() override {
		createList(serv.getMyList());
	}
	CosWnd() = default;
};

class CosReadOnlyGUI :public QWidget,public Observer {
	Cos& cos; 
public:
	CosReadOnlyGUI(Cos& cos) :cos{ cos } {
		cos.addObserver(this);
	};
	void update() override {
		repaint();
	}
	void paintEvent(QPaintEvent* ev) override {
		QPainter p{ this };
		int poz = 0;
		int i = 0;
		for (auto& act : cos.getAll()) {
			if (i % 3 == 0) p.drawRect(10 + poz * 60, 10, 60, 40);
			if(i%3==1)p.drawRect(10 + poz * 60, 10, 50, 50);
			if (i % 3== 2) {
				QRectF rectangle(10.0 , 20.0, 80.0, 60.0 );
				p.drawEllipse(rectangle);
			}
			i++;
			poz += 2;
		}
	}
};