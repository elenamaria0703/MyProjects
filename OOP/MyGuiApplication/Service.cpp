#include "Service.h"
#include<algorithm>
#include<random>
using std::sort;
using std::copy_if;



void Service::addActivity(const string& title, const string& descript, const string& type, const double time) {
	Activity act = { title,descript,type,time };
	valid.validate(act);
	repo.store(act);
	undoActions.push_back(new UndoAdd{ repo,act });
}

void Service::updateActivity(const string & title, const string & descript, const string & type, const double time) {
	Activity act = { title,descript,type,time };
	valid.validate(act);
	if (repo.exist(act)) {
		auto it = find_if(repo.getAll().begin(), repo.getAll().end(), [act](const Activity& activity) {return activity == act; });
		undoActions.push_back(new UndoUpdate{ repo,*it });
	}
	repo.update(act);
	myList.updateCos(act);
}

void Service::removeActivity(const string & title) {
	Activity act{ title,"","",0 };
	if (repo.exist(act)) {
		auto it = find_if(repo.getAll().begin(), repo.getAll().end(), [act](const Activity& activity) {return activity == act; });
		undoActions.push_back(new UndoRemove{ repo,*it });
	}
	repo.remove(act);
	myList.removeCos(act);

}

Activity Service::searchActivity(const string & title) {
	Activity act{ title,"","",0 };
	Activity rez;
	rez = repo.search(act);
	return rez;
}

vector<Activity> Service::filterByString(const string & string, get_string getString)
{
	vector<Activity> filter(repo.getAll().size());
	auto it = copy_if(repo.getAll().begin(), repo.getAll().end(), filter.begin(), [string, getString](const Activity& act) {return getString(act) == string; });
	filter.resize(std::distance(filter.begin(), it));
	return filter;
}

void Service::addMyList(const string & title)
{
	auto it = std::find_if(repo.getAll().begin(), repo.getAll().end(), [title](const Activity& act) {return act.get_title() == title; });
	if (it == repo.getAll().end()) throw RepoException("Titlul nu exista!");
	Activity act = *it;
	myList.appendCos(act);
}

void Service::emptyMyList()
{
	myList.clearCos();
}

void Service::createRandom(int nrAct)
{
	std::mt19937 mt{ std::random_device{}() };
	std::uniform_int_distribution<> dist(0, repo.getAll().size() - 1);
	int nr = 0;
	emptyMyList();
	while (nr < nrAct) {
		int rndNr = dist(mt);
		Activity act = repo.getAll()[rndNr];
		myList.appendCos(act);
		nr++;
	}
}

void Service::undo()
{
	if (undoActions.empty()) {
		throw RepoException("Nu se mai poate face undo!");
	}
	UndoAction* undoAct = undoActions.back();
	undoAct->doUndo();
	undoActions.pop_back();
	delete undoAct;
}

vector<Activity> sortByKey(vector<Activity> activities, cmp_func compare)
{
	vector<Activity> sorted{ activities };
	sort(sorted.begin(), sorted.end(), compare);
	return sorted;
}

string getDescript(const Activity& act) {
	return act.get_descript();
}

string getType(const Activity& act) {
	return act.get_type();
}