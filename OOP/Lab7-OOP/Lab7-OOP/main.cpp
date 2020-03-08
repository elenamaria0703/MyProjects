#include "MyList.h"
#define _CRTDBG_MAP_ALLOC
#include<crtdbg.h>
#include <assert.h>


int main() {
	{
		MyList<Pet> l;
		

		l.addBegin(Pet{ 1,"z","s" });
		l.addBegin(Pet{ 2,"r","i" });

		assert(l.size() == 2);
		MyList<Pet> l1{ l };
		assert(l1.size() == 2);
		MyList<Pet> l2;
		l2.addBegin(Pet{ 1,"z","s" });
		l2 = l;
		assert(l2.size() == 2);
	}
	_CrtDumpMemoryLeaks();
	return 0;
}

//Rule of 3
//copy constroctor
//asignement
//detructor
//Rule of 5
//move constructor
//move asignement