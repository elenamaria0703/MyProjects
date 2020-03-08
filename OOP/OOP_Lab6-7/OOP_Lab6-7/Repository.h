#pragma once
#include "Activity.h"
#include<string>
#include "MyList.h"
using namespace std;

class Repository
{
	List<Activity> activities;

	bool exist(const Activity& act);
public:
	Repository() = default;
	Repository(const Repository& oth) = delete;

	void store(const Activity& act);
	void remove(Activity& act);
	void update(Activity& act);
	Activity search(const Activity& act);

	const List<Activity>& getAll() const noexcept;
	
	//~Repository();
};

class RepoException {
	string msg;
public:
	RepoException(string m) :msg{ m } {}
	//functie friend (vreau sa folosesc membru privat msg)
	friend ostream& operator<<(ostream& out, const RepoException& ex);
};
