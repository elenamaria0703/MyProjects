#pragma once
#include"Activity.h"
#include<observer.h>
#include<vector>
class Cos:public Observable{
private:
	vector<Activity> list;
public:
	Cos() = default;
	void appendCos(const Activity& act);
	void updateCos(const Activity& act);
	void removeCos(const Activity& act);
	void clearCos();
	vector<Activity>& getAll();
};

