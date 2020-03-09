#pragma once
#include "Activity.h"
#include "Repository.h"
#include "Validator.h"
#include"Cos.h"
#include"Undo.h"


typedef string(*get_string)(const Activity&);
typedef bool(*cmp_func)(const Activity&, const Activity&);

class Service
{
	MemoryRepository& repo;
	Cos& myList;
	Validator& valid;
	vector<UndoAction*> undoActions;

public:
	Service(MemoryRepository& repository, Cos& list, Validator& valid) :repo{ repository }, myList{ list }, valid{ valid } {};
	Service(const Service& other) = delete;


	void addActivity(const string& title, const string& descript, const string& type, const double time);
	void updateActivity(const string& title, const string& descript, const string& type, const double time);
	void removeActivity(const string& title);
	Activity searchActivity(const string& title);

	vector<Activity> filterByString(const string& string, get_string getString);

	const vector<Activity>& getAll() const {
		return repo.getAll();
	}

	const vector<Activity>& getMyList() const {
		return myList.getAll();
	}
	Cos& getCos() {
		return myList;
	}
	void addMyList(const string& title);
	void emptyMyList();
	void createRandom(int nrAct);

	void undo();

};

string getDescript(const Activity& act);
string getType(const Activity& act);
vector<Activity> sortByKey(vector<Activity> activities, cmp_func compare);