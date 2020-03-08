#pragma once
#include "Activity.h"
#include<string>
#include<vector>
using namespace std;

class MemoryRepository
{
	vector<Activity> activities;


public:
	MemoryRepository() = default;
	MemoryRepository(const MemoryRepository& oth) = delete;
	bool exist(const Activity& act);

	virtual void store(const Activity& act);
	virtual void remove(Activity& act);
	virtual void update(Activity& act);
	Activity search(const Activity& act);

	const vector<Activity>& getAll() const noexcept;



};

class RepoException {
	string msg;
public:
	RepoException(string m) :msg{ m } {}
	//functie friend (vreau sa folosesc membru privat msg)
	friend ostream& operator<<(ostream& out, const RepoException& ex);
	string getmsg() {
		return msg;
	}
};

class FileRepository :public MemoryRepository {
private:
	string fileName;
	void loadFromFile();
	void writeToFile();
public:
	FileRepository(const string& fileName) :MemoryRepository(), fileName{ fileName }{
		loadFromFile();
	};
	void store(const Activity& act) override {
		MemoryRepository::store(act);
		writeToFile();
	}
	void remove(Activity& act) override {
		MemoryRepository::remove(act);
		writeToFile();
	}
	void update(Activity& act) override {
		MemoryRepository::update(act);
		writeToFile();
	}
};
