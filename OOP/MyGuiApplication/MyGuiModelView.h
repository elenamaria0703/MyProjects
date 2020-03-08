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
#include<qlistview.h>
#include"MyListModel.h"
#include"FilterListModel.h"

class MyGui : public QWidget, public Observer {
	Service& serv;
	QListView* Mylist = new QListView;
	MyListModel* model;

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
		loadInitialState();
		serv.getCos().addObserver(this);
		model = new MyListModel{ serv.getAll() };
		Mylist->setModel(model);
		initSignalsAndSlots();
	};

	void update() override {
		int count = serv.getMyList().size();
		string count_str = to_string(count);
		txtCos->setText(QString::fromStdString(count_str));
	}
	~MyGui() {
		serv.getCos().removeObserver(this);
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

class CosWnd : public QWidget, public Observer {
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
	~CosWnd() {
		cos.removeObserver(this);
	}
};


class CosReadOnlyGUI :public QWidget, public Observer {
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
		int pozx = 0,pozy=0;
		int i = 0;
		int k = 0;
		for (auto& act : cos.getAll()) {
			if (i % 2 == 0) {
				p.drawRect(10 + pozx * 50, 10 + pozy * 10, 60, 40);
				k++;
			}
			if (i % 2 == 1) {
				k++;
				p.drawRect(10 + pozx * 50, 10 + pozy * 10, 50, 50);
			}
			i++; 
			if (k == 5) {
				pozx = 0;
				pozy += 5;
			}
			pozx += 2;
		}
	}
	~CosReadOnlyGUI() {
		cos.removeObserver(this);
	}
};