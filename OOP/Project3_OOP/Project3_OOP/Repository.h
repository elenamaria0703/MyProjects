#pragma once
#include "MyList.h"

typedef struct {
	MyList* list;
	void(*destroyE)(TElem);
	TElem(*copyE)(TElem);
	int(*compare)(TElem, TElem);
}Repo;

Repo* createRepo(void(*destroyE)(TElem),TElem(*copyE)(TElem),int(*compare)(TElem, TElem));
int add(Repo*, TElem*, int(*compare)(TElem, TElem));
int update(Repo*, TElem*, TElem*);
int removee(Repo*, TElem*);
void destroyRepo(Repo*);
MyList* getAll(Repo* );

void testCreateRepo();
void testAdd();
void testUpdate();
void testRemove();