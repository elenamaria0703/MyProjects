#define _CRTDBG_MAP_ALLOC
#include<crtdbg.h>
#include<stdlib.h>

#include "Expense.h"
#include "MyList.h"
#include "Repository.h"
#include "ServiceExpense.h"


#include<stdio.h>

void AllTests() {
	//Functia apeleaza toate funciile de testare din aplicatie
	//Expense
	testCompareExpenses();
	testCopyExpense();
	testCreateExpense();
	testCompareBySum();
	testCompareByType();

	//MyList
	testCreateList();
	testAppendGetElem();
	testCopyList();
	testSetElem();
	testDeleteElem();

	//Repo
	testCreateRepo();
	testAdd();
	testUpdate();
	testRemove();

	//Service
	testAddExpenses();
	testUpdateExpense();
	testRemoveExpense();
	testValidateExpense();
	testFileterListBySum();
	testMinim();
	testOrderListByKey();
}
void PrintExpenses(Service* service) {
	//printeaza lista cheltuielilor 
	MyList* l = getAllExpenses(service);
	for (int i = 0; i < len(l); i++) {
		Expense* e = (Expense*)getElem(l, i);
		printf("Day:%s, Sum:%d, Type:%s\n", e->day, e->sum, e->type);
	}
}

void AddExpense(Service* service) {
	//transmite datele de la utilizator pentru a fi adaugate in repository
	printf("Day:");
	char day[20];
	scanf("%s", day);
	printf("Sum:");
	int sum;
	scanf("%d", &sum);
	printf("Type:");
	char type[20];
	scanf("%s", type);
	int retur = addExpense(service, day, sum, type);
	if (retur == 0) {
		printf("Succesfully added!\n");
	}
	else if(retur==1){
		printf("Repo Error:Already added!\n");
	}
	else {
		printf("Validation Error: Invalid Data!\n");
	}
}

void RemoveExpense(Service* service) {
	//transmite datele de la utilizator pentru a sterge o cheltuiala
	printf("Day:");
	char day[20];
	scanf("%s", day);
	printf("Sum:");
	int sum;
	scanf("%d", &sum);
	printf("Type:");
	char type[20];
	scanf("%s", type);
	int retur = removeExpense(service, day, sum, type);
	if (retur == 1) {
		printf("Succesffuly removed!\n");
	}
	else {
		printf("Repo Error:Could not found expense!\n");
	}
}

void FilterBySum(Service* service) {
	//transmite date de la utilizator 
	//un intreg ce reprezinta suma dupa care se face filtrarea
	printf("Sum:");
	int sum;
	scanf("%d", &sum);
	MyList* filtered = FilterListBySum(service, sum);
	for (int i = 0; i < len(filtered); i++) {
		Expense* e = (Expense*)getElem(filtered, i);
		printf("Day:%s, Sum:%d, Type:%s\n", e->day, e->sum, e->type);
	}

}

void OrderListBySum(Service* service) {
	//printeaza lista cheltuielilor ordonata dupa suma
	MyList* ordered = orderListByKey(service, compareBySum);
	for (int i = 0; i < len(ordered); i++) {
		Expense* e = (Expense*)getElem(ordered, i);
		printf("Day:%s, Sum:%d, Type:%s\n", e->day, e->sum, e->type);
	}
}

void OrderListByType(Service* service) {
	//printeaza lista cheltuielilor ordonata dupa tip
	MyList* ordered = orderListByKey(service, compareByType);
	for (int i = 0; i < len(ordered); i++) {
		Expense* e = (Expense*)getElem(ordered, i);
		printf("Day:%s, Sum:%d, Type:%s\n", e->day, e->sum, e->type);
	}
}

void UpdateExpense(Service* service) {
	//transmite date de la utilizator pentru modificare
	//primeste cheltuiala curenta si modificarile aferente
	printf("Modifiy expense:\n");
	printf("Day:");
	char day[20];
	scanf("%s", day);
	printf("Sum:");
	int sum;
	scanf("%d", &sum);
	printf("Type:");
	char type[20];
	scanf("%s", type);
	printf("New expense:\n");
	printf("Day:");
	char dayN[20];
	scanf("%s", dayN);
	printf("Sum:");
	int sumN;
	scanf("%d", &sumN);
	printf("Type:");
	char typeN[20];
	scanf("%s", typeN);
	int retur = updateExpense(service, day, sum, type, dayN, sumN, typeN);
	if (retur == 0) {
		printf("Repo Error:Could not find expense\n");
	}
	else {
		printf("Succesfully modified!\n");
	}
}

void addInput(Service* service) {
	addExpense(service, "miercuri", 460, "telefon&internet");
	addExpense(service, "luni", 40, "mancare");
	addExpense(service, "joi", 20, "altele");
	addExpense(service, "luni", 80, "transport");
	addExpense(service, "marti", 40, "telefon&internet");
}
void run() {
	Service* service = createService(destroyExpense,copyExpense,compareExpenses);
	int ruleaza = 1;
	addInput(service);
	while (ruleaza) {
		printf("0 Exit\n1 Add\n2 Print\n3 Update\n4 Delete\n5 FilterBySum-less than a given sum\n6 Order list by sum\n7 Order list by type\nCommand: ");
		int cmd = 0;
		scanf("%d", &cmd);
		switch (cmd) {
		case 1:
			AddExpense(service);
			break;
		case 2:
			PrintExpenses(service);
			break;
		case 3:
			UpdateExpense(service);
			break;
		case 4:
			RemoveExpense(service);
			break;
		case 5:
			FilterBySum(service);
			break;
		case 6:
			OrderListBySum(service);
			break;
		case 7:
			OrderListByType(service);
			break;
		case 0:
			ruleaza = 0;
			break;
		default:
			printf("Comanda invalida!!!\n");
		}
	}
	destroyService(service);
}

int main() {
	AllTests();
	run();
	_CrtDumpMemoryLeaks();
	return 0;
}