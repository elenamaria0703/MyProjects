#include "Tests.h"
#include<assert.h>
#include<iostream>

void Tests::testDomain() {
	Activity a = { "dans","aerobic","sport",1.30 };
	Activity a1 = { "dans","step","sport",2 };
	assert(a.get_title() == "dans");
	assert(a.get_descript() == "aerobic");
	assert(a.get_type() == "sport");
	assert(a.get_time() == 1.30);
	assert(a == a1);
	assert(cmpTime(a, a1) == 1);
	assert(cmpDescript(a, a1) == 1);
	assert(cmpTitle(a, a1) == 0);
	assert(cmpType(a, a1) == 0);
}

void Tests::testRepository()
{
	Repository repo;
	Activity a = { "dans","aerobic","sport",3 };
	Activity a1 = { "dans","step","sport",2 };
	Activity a2 = { "sala","hiit","sport",2 };
	Activity a3 = { "clasa","tudor","sport",5 };
	assert(repo.getAll().size() == 0);
	repo.store(a);
	assert(repo.getAll().size() == 1);
	try {
		repo.store(a1);
		assert(false);
	}
	catch (RepoException&) {
		assert(true);
	}
	repo.update(a1);
	List<Activity> l{ repo.getAll() };
	auto it = l.begin();
	Activity updated = *it;
	assert(updated.get_descript() == "step");
	assert(updated.get_time() == 2);
	try {
		repo.update(a2);
		assert(false);
	}
	catch (RepoException&) {
		assert(true);
	}
	repo.store(a2);
	assert(repo.getAll().size() == 2);
	repo.remove(a);
	assert(repo.getAll().size() == 1);
	a = repo.search(a2);
	assert(a.get_descript() == "hiit");
	try {
		repo.search(a3);
		assert(false);
	}
	catch (RepoException&) {
		assert(true);
	}
	repo.remove(a2);
	assert(repo.getAll().size() == 0);

}

void Tests::testService() {
	Repository repo;
	Service serv(repo);
	Activity a1 = { "dans","aerobic","sport",2 };
	Activity a2 = { "dans","step","sport",3 };
	Activity a3 = { "cumparaturi","utilitati","altele",3 };

	assert(serv.getAll().size() == 0);
	serv.addActivity("dans", "aerobic", "sport", 2);
	assert(serv.getAll().size() == 1);
	try {
		serv.addActivity("dans", "step", "sport", 3);
		assert(false);
	}
	catch (RepoException&) {
		assert(true);
	}
	try {
		serv.removeActivity("tort");
		assert(false);
	}
	catch (RepoException&) {
		assert(true);
	}
	serv.removeActivity("dans");
	assert(serv.getAll().size() == 0);
	serv.addActivity("dans", "aerobic", "sport", 2);
	serv.updateActivity("dans", "step", "sport", 3);
	assert(serv.searchActivity("dans").get_descript() == "step");
	serv.addActivity("sala", "step", "sport", 3);
	serv.addActivity("cumparaturi", "utilitati", "altele", 3);
	List<Activity> filter = serv.filterByString("step", getDescript);
	assert(filter.size() == 2);

	List<Activity> sorted = sortByKey(serv.getAll(), cmpTitle);
	auto it = sorted.begin();
	auto act = *it;
	assert(act.get_title() == "cumparaturi");
	assert(getType(act) == "altele"); *
	++it;
	act = *it;
	assert(act.get_title() == "dans");
}

void Tests::AllTests() {
	testDomain();
	testRepository();
	testService();
}