#include "MyList.h"
#include "Expense.h"

#include<stdlib.h>
#include<string.h>
#include<assert.h>

MyList* createList() {
	MyList* list = malloc(sizeof(MyList));
	list->elems = malloc(sizeof(TElem) * 3);
	list->size = 0;
	list->capacity = 3;
	return list;
}
void ensureCapacity(MyList* l) {
	if (l->capacity == l->size) {
		TElem* Elems = malloc(sizeof(TElem)*(l->capacity + 3));
		for (int i = 0; i < l->size; i++) {
			Elems[i] = l->elems[i];
		}
		free(l->elems);
		l->elems = Elems;
		l->capacity = l->capacity + 3;
	}
	else return;
}

void appendList(MyList* l, TElem* el, TElem(*copyE)(TElem)) {
	ensureCapacity(l);
	l->elems[l->size] = copyE(el);
	l->size++;
}

TElem* getElem(MyList* l, int poz) {
	return l->elems[poz];
}

void setElem(MyList* l, TElem* old, TElem* new, void(*destroyE)(TElem *), TElem(*copyE)(TElem), int(*compare)(TElem, TElem)){
	for (int i = 0; i < len(l); i++) {
		TElem* el = getElem(l, i);
		if (compare(el, old) == 1) {
			destroyE(el);
			l->elems[i] = copyE(new);
		}
	}
}

void deleteElem(MyList* l, TElem* el, void(*destroyE)(TElem *), int(*compare)(TElem, TElem)){
	int i = 0;
	int pos = -1;
	for(; i < len(l); i++) {
		if (compare(el, l->elems[i]) == 1) {
			pos = i;
			break;
		}
	}
	if (pos != -1) {
		i = pos;
		destroyE(l->elems[i]);
		for (; i<len(l) - 1; i++) {
			l->elems[i] = l->elems[i + 1];
		}
		l->size--;
	}
}

int len(MyList* l) {
	return l->size;
}

void destroyList(MyList* l, void(*destroyE)(TElem*)) {
	for (int i = 0; i < len(l); i++) {
		destroyE(l->elems[i]);
	}
	free(l->elems);
	free(l);
}

MyList* copyList(MyList* l, TElem(*copyE)(TElem)) {
	MyList* copy = createList();
	for (int i = 0; i < len(l); i++) {
		TElem* el = getElem(l, i);
		appendList(copy, el, copyE);
	}
	return copy;
}


void testCreateList() {
	MyList* l = createList();
	assert(len(l) == 0);
	assert(l->capacity == 3);
	destroyList(l, (void(*)(TElem*))destroyExpense);
}

void testAppendGetElem() {
	MyList* l = createList();
	Expense* el1 = createExpense("miercuri", 450, "altele");
	Expense* el2 = createExpense("luni", 80, "imbracaminte");
	Expense* el3 = createExpense("marti", 450, "altele");
	Expense* el4 = createExpense("joi", 80, "imbracaminte");
	
	appendList(l,(TElem*)el1, copyExpense);
	destroyExpense(el1);
	assert(len(l) == 1);
	appendList(l, (TElem*)el2, copyExpense);
	destroyExpense(el2);
	assert(len(l) == 2);
	appendList(l, (TElem*)el3, copyExpense);
	destroyExpense(el3);
	assert(len(l) == 3);
	appendList(l, (TElem*)el4, copyExpense);
	destroyExpense(el4);
	assert(len(l) == 4);
	
	el1 = (Expense*)getElem(l, 1);
	assert(strcmp(el1->day, "luni") == 0);
	el2 = (Expense*)getElem(l, 0);
	assert(strcmp(el2->day, "miercuri") == 0);
	destroyList(l, (void(*)(TElem*))destroyExpense);
	
}

void testCopyList() {
	MyList* l = createList();
	Expense* el1 = createExpense("miercuri", 450, "altele");
	Expense* el2 = createExpense("luni", 80, "imbracaminte");
	appendList(l, (TElem)el1, (TElem(*)(TElem))copyExpense);
	destroyExpense(el1);
	appendList(l, (TElem)el2, (TElem(*)(TElem))copyExpense);
	destroyExpense(el2);
	MyList* copy = copyList(l, (TElem(*)(TElem))copyExpense);
	assert(len(copy) == 2);
	el1 = (Expense*)getElem(copy, 0);
	assert(strcmp(el1->day, "miercuri") == 0);
	el2 = (Expense*)getElem(copy, 1);
	assert(strcmp(el2->day, "luni") == 0);
	destroyList(l, (void(*)(TElem*))destroyExpense);
	destroyList(copy, (void(*)(TElem*))destroyExpense);
}

void testSetElem() {
	MyList* l = createList();
	Expense* el1 = createExpense("miercuri", 450, "altele");
	Expense* el2 = createExpense("luni", 80, "imbracaminte");
	appendList(l, (TElem)el1, (TElem(*)(TElem))copyExpense);
	assert(len(l) == 1);
	Expense* el = (Expense*)getElem(l, 0);
	assert(strcmp(el->day, "miercuri") == 0);
	assert(el->sum == 450);
	setElem(l, (TElem)el1, (TElem)el2, (void(*)(TElem*))destroyExpense, (TElem(*)(TElem))copyExpense, compareExpenses);
	destroyExpense(el1);
	destroyExpense(el2);
	assert(len(l) == 1);
	el = (Expense*)getElem(l, 0);
	assert(strcmp(el->day, "luni") == 0);
	assert(strcmp(el->type, "imbracaminte") == 0);
	assert(el->sum == 80);
	destroyList(l, (void(*)(TElem*))destroyExpense);
}

void testDeleteElem() {
	MyList* l = createList();
	Expense* el1 = createExpense("miercuri", 450, "altele");
	Expense* el2 = createExpense("luni", 80, "imbracaminte");
	appendList(l, (TElem)el1, copyExpense);
	assert(len(l) == 1);
	appendList(l, (TElem)el2, copyExpense);
	assert(len(l) == 2);
	deleteElem(l, (TElem)el2, (void(*)(TElem*))destroyExpense, compareExpenses);
	assert(len(l) == 1);
	deleteElem(l, (TElem)el1, (void(*)(TElem*))destroyExpense, compareExpenses);
	assert(len(l) == 0);
	destroyExpense(el2);
	destroyExpense(el1);
	destroyList(l, (void(*)(TElem*))destroyExpense);

}