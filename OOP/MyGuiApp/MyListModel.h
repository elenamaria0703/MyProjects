#pragma once
#include<QAbstractListModel>
#include"Activity.h"
#include<vector>

class MyListModel :public QAbstractListModel {
	std::vector<Activity> acts;
	string type;
public:
	MyListModel(const std::vector<Activity>& acts) :acts{ acts } {};
	int rowCount(const QModelIndex& parent = QModelIndex()) const override {
		return acts.size();
	}
	QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override {
		if (role == Qt::DisplayRole) {
			auto title = acts[index.row()].get_title();
			auto descript = acts[index.row()].get_descript();
			auto type = acts[index.row()].get_type();
			auto time = to_string(acts[index.row()].get_time());
			return QString::fromStdString(title+' '+descript+' '+type+' '+time);
		}
		if (role == Qt::BackgroundRole) {
			if (!type.empty() && acts[index.row()].get_type() == type) {
				return QColor(Qt::darkCyan);
			}
		}
		return QVariant{};
	}
	void setFilterType(string txt) {
		type = txt;
		auto topLeft = createIndex(0, 0);
		auto bottomR = createIndex(rowCount(), 0);
		emit dataChanged(topLeft, bottomR);
	}
	void setActivity(const vector<Activity> acts) {
		this->acts = acts;
		auto topLeft = createIndex(0, 0);
		auto bottomR = createIndex(rowCount(), 0);
		emit dataChanged(topLeft, bottomR);
	}
};