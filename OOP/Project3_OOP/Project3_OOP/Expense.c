#include "Expense.h"

#include<stdlib.h>
#include<assert.h>
#include<string.h>

Expense* createExpense(char* day, int sum, char* type) {
	Expense* e = malloc(sizeof(Expense));
	e->day = malloc(strlen(day) + 1);
	e->sum = sum;
	e->type = malloc(strlen(type) + 1);
	strcpy(e->day, day);
	strcpy(e->type, type);
	return e;
}

void destroyExpense(Expense* e) {
	free(e->day);
	free(e->type);
	free(e);
}

Expense* copyExpense(Expense* e) {
	Expense* copyE = malloc(sizeof(Expense));
	copyE->day = malloc(strlen(e->day) + 1);
	copyE->type = malloc(strlen(e->type) + 1);
	copyE->sum = e->sum;
	strcpy(copyE->day, e->day);
	strcpy(copyE->type, e->type);
	return copyE;
}
int compareExpenses(Expense* e, Expense* e1) {
	return strcmp(e->day,e1->day)==0 && e->sum == e1->sum && strcmp(e->type, e1->type) == 0;
}
int compareBySum(Expense* e1, Expense* e2) {
	return e1->sum < e2->sum;
}
int compareByType(Expense* e1, Expense* e2) {
	return strcmp(e1->type, e2->type) < 0;
}


void testCreateExpense() {
	Expense* e = createExpense("marti", 12, "imbracaminte");
	assert(strcmp(e->day, "marti") == 0);
	assert(strcmp(e->type, "imbracaminte") == 0);
	assert(e->sum == 12);
	destroyExpense(e);
}
void testCopyExpense() {
	Expense* e = createExpense("marti", 12, "imbracaminte");
	Expense* copy = copyExpense(e);
	assert(strcmp(copy->day, "marti") == 0);
	assert(strcmp(copy->type, "imbracaminte") == 0);
	assert(copy->sum == 12);
	destroyExpense(e);
	destroyExpense(copy);

}
void testCompareExpenses() {
	Expense* e1 = createExpense("luni", 78, "mancare");
	Expense* e2 = createExpense("luni", 78, "mancare");
	Expense* e3 = createExpense("miercuri", 78, "mancare");
	assert(compareExpenses(e1, e2) == 1);
	assert(compareExpenses(e1, e3) == 0);
	destroyExpense(e1);
	destroyExpense(e2);
	destroyExpense(e3);

}

void testCompareBySum() {
	Expense* e1 = createExpense("luni", 30, "mancare");
	Expense* e2 = createExpense("luni", 78, "mancare");
	assert(compareBySum(e1, e2) == 1);
	assert(compareBySum(e2, e1) == 0);
	destroyExpense(e1);
	destroyExpense(e2);
}
void testCompareByType() {
	Expense* e1 = createExpense("luni", 30, "mancare");
	Expense* e2 = createExpense("luni", 78, "altele");
	assert(compareByType(e1, e2) == 0);
	assert(compareByType(e2, e1) == 1);
	destroyExpense(e1);
	destroyExpense(e2);
}