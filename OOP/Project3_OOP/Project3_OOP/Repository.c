#include "Repository.h"
#include "Expense.h"

#include<stdlib.h>
#include<assert.h>
#include<string.h>

Repo* createRepo(void(*destroyE)(TElem), TElem(*copyE)(TElem), int(*compare)(TElem, TElem)) {
	Repo* repo = malloc(sizeof(Repo));
	repo->list = createList();
	repo->copyE = copyE;
	repo->destroyE = destroyE;
	repo->compare = compare;
	return repo;

}
int add(Repo* repo, TElem* el, int(*compare)(TElem, TElem)) {
	if (len(repo->list) == 0) {
		appendList(repo->list, el, repo->copyE);
		return 0;
	}
	int retur,ok=1;
	for (int i = 0; i < len(repo->list); i++) {
		TElem* El = getElem(repo->list, i);
		retur = compare(el, El);
		if (retur == 1) ok = 0;
	}
	if (ok==0) {
		return 1;
	}
	else {
		appendList(repo->list, el, repo->copyE);
		return 0;
	}
}

int update(Repo* repo, TElem* old, TElem* new) {
	MyList* l = getAll(repo);
	int retur = 0;
	for (int i = 0; i < len(l) && retur ==0; i++) {
		TElem* el = getElem(l, i);
		if (repo->compare(el, old)==1) {
			retur = 1;
		}
	}
	if (retur == 0) {
		return 0;
	}
	else {
		setElem(repo->list, old, new, repo->destroyE, repo->copyE, repo->compare);
		return 1;
	}
}

int removee(Repo* repo, TElem* el) {
	MyList* l = getAll(repo);
	int retur = 0;
	for (int i = 0; i < len(l); i++) {
		TElem* e = getElem(l, i);
		if (repo->compare(el, e) == 1){
			retur = 1;
		}
	}
	if (retur == 0) {
		return 0;
	}
	else {
		deleteElem(repo->list, el, repo->destroyE, repo->compare);
		return 1;
	}
}


MyList* getAll(Repo* repo) {
	return repo->list;
}
void destroyRepo(Repo* repo) {
	destroyList(repo->list, repo->destroyE);
	free(repo);
}


void testCreateRepo() {
	Repo* repo = createRepo(destroyExpense,copyExpense,compareExpenses);
	assert(len(repo->list) == 0);
	destroyRepo(repo);
}
void testAdd() {
	Repo* repo = createRepo(destroyExpense, copyExpense, compareExpenses);
	assert(len(repo->list) == 0);
	Expense* el = createExpense("miercuri", 90, "transport");
	int retur = add(repo, (TElem)el, compareExpenses);
	int retur1 = add(repo, (TElem)el, compareExpenses);
	destroyExpense(el);
	assert(retur == 0);
	assert(retur1 == 1);
	assert(len(repo->list) == 1);
	el = (Expense*)getElem(repo->list,0);
	assert(strcmp(el->type, "transport") == 0);
	destroyRepo(repo);
}

void testUpdate() {
	Repo* repo = createRepo(destroyExpense, copyExpense, compareExpenses);
	assert(len(repo->list) == 0);
	Expense* el1 = createExpense("miercuri", 450, "altele");
	Expense* el2 = createExpense("luni", 80, "imbracaminte");
	add(repo, (TElem)el1, compareExpenses);
	assert(len(repo->list) == 1);
	Expense* el = (Expense*)getElem(repo->list, 0);
	assert(strcmp(el->type, "altele") == 0);
	update(repo, (TElem)el1, (TElem)el2);
	el = (Expense*)getElem(repo->list, 0);
	assert(strcmp(el->type, "imbracaminte") == 0);
	assert(el->sum == 80);
	destroyRepo(repo);
	destroyExpense(el1);
	destroyExpense(el2);
}

void testRemove() {
	Repo* repo = createRepo(destroyExpense, copyExpense, compareExpenses);
	assert(len(repo->list) == 0);
	Expense* el1 = createExpense("miercuri", 450, "altele");
	Expense* el2 = createExpense("luni", 80, "imbracaminte");
	add(repo, (TElem)el1, compareExpenses);
	assert(len(repo->list) == 1);
	add(repo, (TElem)el2, compareExpenses);
	assert(len(repo->list) == 2);
	int retur = removee(repo, (TElem)el1);
	assert(retur == 1);
	assert(len(repo->list) == 1);
	int retur1 = removee(repo, (TElem)el1);
	assert(retur1 == 0);
	assert(len(repo->list) == 1);
	removee(repo, (TElem)el2);
	assert(len(repo->list) == 0);
	destroyExpense(el1);
	destroyExpense(el2);
	destroyRepo(repo);
}