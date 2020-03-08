#pragma once

typedef void* TElem;

typedef struct {
	TElem* elems;
	int size;
	int capacity;
}MyList;

MyList* createList();
void appendList(MyList*, TElem*, TElem(*copyE)(TElem));
TElem* getElem(MyList*, int);
void setElem(MyList* l, TElem* old, TElem* new, void(*destroyE)(TElem *), TElem(*copyE)(TElem), int(*compare)(TElem, TElem));
void destroyList(MyList*, void(*destroyE)(TElem*));
void deleteElem(MyList*, TElem*, void(*destroyE)(TElem *), int(*compare)(TElem, TElem));
void ensureCapacity(MyList*);
int len(MyList*);
MyList* copyList(MyList*, TElem(*copyE)(TElem));

void testCreateList();
void testAppendGetElem();
void testCopyList();
void testSetElem();
void testDeleteElem();