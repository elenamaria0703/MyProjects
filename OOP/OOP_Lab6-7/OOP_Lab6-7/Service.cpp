#include "Service.h"
#include<vector>
#include<algorithm>


void Service::addActivity(const string& title, const string& descript, const string& type, const double time) {
	Activity act = { title,descript,type,time };
	repo.store(act);
}

void Service::updateActivity(const string & title, const string & descript, const string & type, const double time) {
	Activity act = { title,descript,type,time };
	repo.update(act);
}

void Service::removeActivity(const string & title) {
	Activity act{ title,"","",0 };
	repo.remove(act);
}

Activity Service::searchActivity(const string & title) {
	Activity act{ title,"","",0 };
	Activity rez;
	rez = repo.search(act);
	return rez;
}

List<Activity> Service::filterByString(const string & string, get_string getString)
{
	List<Activity> filter;
	List<Activity> l{ getAll() };
	for (Activity act : l) {
		if (getString(act) == string) {
			filter.push_back(act);
		}
	}
	return filter;
}

List<Activity> sortByKey(List<Activity> activities, cmp_func compare)
{
	List<Activity> sorted;
	vector<Activity> Sorted;
	for (auto it = activities.begin(); it != activities.end(); ++it) {
		Sorted.push_back(*it);
	}
	sort(Sorted.begin(), Sorted.end(), compare);
	vector<Activity>::iterator it1;
	for (it1 = Sorted.begin(); it1 != Sorted.end(); ++it1) {
		sorted.push_back(*it1);
	}
	return sorted;
}

string getDescript(const Activity& act) {
	return act.get_descript();
}

string getType(const Activity& act) {
	return act.get_type();
}