#pragma once
#include "MyList.h"
#include "Activity.h"
#include "Repository.h"

typedef string(*get_string)(const Activity&);
typedef bool(*cmp_func)(const Activity&, const Activity&);

class Service
{
	Repository& repo;
	//validator

public:
	Service(Repository& repository) :repo{ repository } {};
	Service(const Service& other) = delete;
	//~Service();

	void addActivity(const string& title, const string& descript, const string& type, const double time);
	void updateActivity(const string& title, const string& descript, const string& type, const double time);
	void removeActivity(const string& title);
	Activity searchActivity(const string& title);

	List<Activity> filterByString(const string& string, get_string getString);

	const List<Activity>& getAll() const {
		return repo.getAll();
	}


};

string getDescript(const Activity& act);
string getType(const Activity& act);
List<Activity> sortByKey(List<Activity> activities, cmp_func compare);