#pragma once
#include"Expense.h"
#include "Repository.h"

typedef struct {
	Repo* repo;
	//MyList* undo;
	void(*destroyE)(TElem);
	TElem(*copyE)(TElem);
	int(*compare)(TElem, TElem);
}Service;

Service* createService(void(*destroyE)(TElem), TElem(*copyE)(TElem), int(*compare)(TElem, TElem));
int addExpense(Service*,char*, int, char*);
int updateExpense(Service*, char*, int, char*, char*, int, char*);
int removeExpense(Service*, char*, int, char*);
MyList* FilterListBySum(Service*, int);
MyList* getAllExpenses(Service*);
Expense* Minim(MyList*,int(*compareByKey)(TElem,TElem));
void destroyService(Service*);
MyList* orderListByKey(Service*, int(*compareByKey)(TElem, TElem));

void testAddExpenses();
void testUpdateExpense();
void testRemoveExpense();
void testValidateExpense();
void testFileterListBySum();
void testMinim();
void testOrderListByKey();