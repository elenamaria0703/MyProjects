#include"Activity.h"
#include"Tests.h"
#include"MyList.h"
#include "UIConsole.h"
#define _CRTDBG_MAP_ALLOC
#include<crtdbg.h>
#include<iostream>

int main() {
	{
		Tests test;
		test.AllTests();

		Repository repo;
		Service service(repo);
		UIConsole ui(service);

		ui.start();
	}
	_CrtDumpMemoryLeaks();
	return 0;
}