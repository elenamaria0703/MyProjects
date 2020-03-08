#include "ServiceExpense.h"
#include "Expense.h"

#include<stdlib.h>
#include<assert.h>
#include<string.h>

Service* createService(void(*destroyE)(TElem), TElem(*copyE)(TElem), int(*compare)(TElem, TElem)) {
	Service* service = malloc(sizeof(Service));
	service->copyE = copyE;
	service->destroyE = destroyE;
	service->compare = compare;
	service->repo = createRepo(service->destroyE,service->copyE,service->compare);
	//service->undo = createList();
	return service; 
}

/*void undo(Service* service) {
	MyList* popList = removeLast(service->undo);
	destroyList(service->repo,service->destroyE);
	service->repo = popList;
}
MyList.c
TElem removeLast(MyList* l) {
	TElem last = l->elems[len(l) - 1];
	l->size--;
	return last;
}*/

int validateExpense(Expense* el) {
	int dayOk = 0, typeOk = 0;
	if (strcmp(el->day, "luni") == 0 || strcmp(el->day, "marti") == 0 || strcmp(el->day, "miercuri") == 0 || strcmp(el->day, "joi") == 0 || strcmp(el->day, "vineri") == 0
		|| strcmp(el->day, "sambata") == 0 || strcmp(el->day, "duminica") == 0) {
		dayOk = 1;
	}
	if (strcmp(el->type, "altele") == 0 || strcmp(el->type, "imbracaminte") == 0 || strcmp(el->type, "mancare") == 0 || strcmp(el->type, "transport") == 0 || strcmp(el->type, "telefon&internet") == 0) {
		typeOk = 1;
	}
	if (dayOk == 1 && typeOk == 1) {
		return 2;
	}
	else {
		return 3;
	}
}
int addExpense(Service* service,char* day, int sum, char* type) {
	Expense* el = createExpense(day, sum, type);
	int valid = validateExpense(el);
	if (valid == 3) {
		service->destroyE(el);
		return valid;
	}
	//appendList(service->undo, service->repo, service->copyE);
	int retur = add(service->repo, (TElem)el, compareExpenses);
	service->destroyE(el);
	return retur;
}

int updateExpense(Service* service, char* dayOld, int sumOld, char* typeOld, char* dayNew, int sumNew, char* typeNew) {
	Expense* old = createExpense(dayOld, sumOld, typeOld);
	Expense* new = createExpense(dayNew, sumNew, typeNew);
	int retur = update(service->repo, (TElem)old, (TElem)new);
	service->destroyE(old);
	service->destroyE(new);
	return retur;


}

int removeExpense(Service* service, char* day, int sum, char* type) {
	Expense* el = createExpense(day, sum, type);
	int retur = removee(service->repo, (TElem)el);
	service->destroyE(el);
	return retur;
}

MyList* getAllExpenses(Service* service) {
	return getAll(service->repo);
}

MyList* FilterListBySum(Service* service, int sum) {
	MyList* filtered = createList();
	MyList* list = getAll(service->repo);
	for (int i = 0; i < len(list); i++) {
		Expense* el = (Expense*)getElem(list, i);
		if (el->sum < sum) {
			appendList(filtered, (TElem)el, (TElem(*)(TElem))copyExpense);
		}
	}
	return filtered;
}

Expense* Minim(MyList* l, int(*compareByKey)(TElem, TElem)) {
	Expense* min = createExpense("", 10000000, "zzzzzzzzzz");
	for (int i = 0; i < len(l); i++) {
		Expense* e = (Expense*)getElem(l, i);
		if (compareByKey(e,min)) {
			destroyExpense(min);
			min = copyExpense(e);
		}
	}
	return min;

}

MyList* orderListByKey(Service* service,int(*compareByKey)(TElem, TElem)) {
	MyList* list = copyList(service->repo->list,service->copyE);
	MyList* ordered = createList();
	int lg = len(list);
	for (int i = 0; i < lg; i++) {
		Expense* el = Minim(list, compareByKey);
		appendList(ordered, (TElem)el, copyExpense);
		deleteElem(list, (TElem)el, (void(*)(TElem))destroyExpense, compareExpenses);
		destroyExpense(el);
	}
	destroyList(list, (void(*)(TElem))destroyExpense);
	return ordered;
}

void destroyService(Service* service) {
	destroyRepo(service->repo);
	//destroyList(service->undo,destroyList);
	free(service);
}

void testAddExpenses() {
	Service* service = createService(destroyExpense,copyExpense,compareExpenses);
	assert(len(service->repo->list) == 0);
	int retur = addExpense(service, "miercuri", 460, "telefon&internet");
	assert(retur == 0);
	MyList* l = getAll(service->repo);
	Expense* el = (Expense*)getElem(l, 0);
	assert(strcmp(el->type, "telefon&internet") == 0);
	int retur1 = addExpense(service, "miercuri", 460, "telefon&internet");
	assert(retur1 == 1);
	assert(len(l) == 1);
	destroyService(service);
}

void testUpdateExpense() {
	Service* service = createService(destroyExpense, copyExpense, compareExpenses);
	assert(len(getAllExpenses(service)) == 0);
	addExpense(service, "miercuri", 460, "telefon&internet");
	assert(len(service->repo->list) == 1);
	int retur = updateExpense(service, "luni", 460, "telefon&internet", "miercuri", 450, "transport");
	int retur1 = updateExpense(service, "miercuri", 460, "telefon&internet", "miercuri", 450, "transport");
	assert(retur == 0);
	assert(retur1 == 1);
	destroyService(service);
}

void testRemoveExpense() {
	Service* service = createService(destroyExpense, copyExpense, compareExpenses);
	assert(len(getAllExpenses(service)) == 0);
	addExpense(service, "miercuri", 460, "telefon&internet");
	int retur = removeExpense(service, "miercuri", 460, "telefon&internet");
	assert(retur == 1);
	destroyService(service);
}

void testValidateExpense() {
	Expense* el = createExpense("joie", 90, "altele");
	int valid1 = validateExpense(el);
	assert(valid1 == 3);
	destroyExpense(el);
}

void testFileterListBySum(){
	Service* service = createService(destroyExpense, copyExpense, compareExpenses);
	assert(len(service->repo->list) == 0);
	addExpense(service, "miercuri", 60, "telefon&internet");
	addExpense(service, "luni", 460, "telefon&internet");
	addExpense(service, "vineri", 40, "telefon&internet");
	addExpense(service, "joi", 40, "telefon&internet");
	assert(len(service->repo->list) == 4);
	MyList* filtered = FilterListBySum(service, 100);
	assert(len(filtered) == 3);
	destroyList(filtered, (void(*)(TElem))destroyExpense);
	destroyService(service);
}
void testMinim() {
	Service* service = createService(destroyExpense, copyExpense, compareExpenses);
	MyList* l = service->repo->list;
	addExpense(service, "miercuri", 60, "mancare");
	addExpense(service, "luni", 460, "altele");
	addExpense(service, "vineri", 40, "telefon&internet");
	assert(len(l) == 3);
	Expense* el = Minim(l, compareBySum);
	Expense* el1 = Minim(l, compareByType);
	assert(el->sum == 40);
	assert(strcmp(el->day,"vineri")==0);
	assert(strcmp(el->type,"telefon&internet")==0);

	assert(el1->sum == 460);
	assert(strcmp(el1->day, "luni") == 0);
	assert(strcmp(el1->type, "altele") == 0);

	destroyExpense(el);
	destroyExpense(el1);
	destroyService(service);

}
void testOrderListByKey() {
	Service* service = createService(destroyExpense, copyExpense, compareExpenses);
	addExpense(service, "miercuri", 60, "mancare");
	addExpense(service, "vineri", 40, "telefon&internet");
	addExpense(service, "luni", 460, "altele");
	addExpense(service, "marti", 30, "telefon&internet");
	assert(len(service->repo->list) == 4);
	MyList* ordered = orderListByKey(service,compareBySum);
	assert(len(ordered) == 4);
	Expense* el = (Expense*)getElem(ordered, 0);
	assert(el->sum == 30);
	el = (Expense*)getElem(ordered, 1);
	assert(el->sum == 40);
	el = (Expense*)getElem(ordered, 2);
	assert(el->sum == 60);
	el = (Expense*)getElem(ordered, 3);
	assert(el->sum == 460);
	MyList* orderedType = orderListByKey(service, compareByType);
	el = (Expense*)getElem(orderedType, 0);
	assert(strcmp(el->type, "altele") == 0);
	el = (Expense*)getElem(orderedType, 1);
	assert(strcmp(el->type, "mancare") == 0);

	destroyList(orderedType, (void(*)(TElem))destroyExpense);
	destroyList(ordered, (void(*)(TElem))destroyExpense);
	destroyService(service);
}
