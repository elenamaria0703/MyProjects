#pragma once

typedef struct {
	char* day;
	int sum;
	char* type;
}Expense;

Expense* createExpense(char* day, int sum, char* type);
Expense* copyExpense(Expense*);
void destroyExpense(Expense*);
int compareExpenses(Expense*, Expense*);
int compareBySum(Expense*,Expense*);
int compareByType(Expense*, Expense*);

void testCreateExpense();
void testCopyExpense();
void testCompareExpenses();
void testCompareBySum();
void testCompareByType();
