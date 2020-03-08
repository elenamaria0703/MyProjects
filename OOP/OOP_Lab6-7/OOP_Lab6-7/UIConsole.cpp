#include "UIConsole.h"
#include<iostream>
using std::cin;
using std::cout;

void UIConsole::printActivities(const List<Activity>& activities)
{
	List<Activity> l{ activities };
	for (auto act:l) {
		cout << act << endl;
	}
}
void UIConsole::addUI()
{
	string title, type, descript;
	double time;
	cout << "Dati titlu:";
	cin >> title;
	cout << "Dati descrirea:";
	cin >> descript;
	cout << "Dati tip:";
	cin >> type;
	cout << "Dati timp:";
	cin >> time;
	serv.addActivity(title, descript, type, time);
	cout << "Adaugat cu succes!" << endl;

}

void UIConsole::updateUI()
{
	string title, type, descript;
	double time;
	cout << "Dati titlu:";
	cin >> title;
	cout << "Dati descrirea:";
	cin >> descript;
	cout << "Dati tip:";
	cin >> type;
	cout << "Dati timp:";
	cin >> time;
	serv.updateActivity(title, descript, type, time);
	cout << "Modificat cu succes!" << endl;
}

void UIConsole::removeUI()
{
	string title;
	cout << "Dati titlu:";
	cin >> title;
	serv.removeActivity(title);
	cout << "Sters cu succes!" << endl;
}

void UIConsole::searchUI()
{
	string title;
	cout << "Dati titlu:";
	cin >> title;
	Activity act = serv.searchActivity(title);
	cout << act << endl;

}

void UIConsole::filterByType()
{
	string type;
	cout << "Dati tipul:";
	cin >> type;
	List<Activity> filter = serv.filterByString(type, getType);
	for (const Activity& act : filter) {
		cout << act << endl;
	}
}

void UIConsole::filterByDescript()
{
	string descript;
	cout << "Dati descrierea:";
	cin >> descript;
	List<Activity> filter = serv.filterByString(descript, getDescript);
	for (const Activity& act : filter) {
		cout << act << endl;
	}
}

void UIConsole::sortByTitle()
{
	printActivities(sortByKey(serv.getAll(), cmpTitle));
}

void UIConsole::sortByDescript()
{
	printActivities(sortByKey(serv.getAll(), cmpDescript));
}

void UIConsole::sortByTypeTime()
{
	List<Activity> sort = sortByKey(serv.getAll(), cmpType);
	printActivities(sortByKey(sort, cmpTime));

}

void UIConsole::start()
{
	int ruleaza = 1;
	cout << "Meniu:\n";
	cout << "1 adauga\n2 sterge\n3 modifica\n4 cauta\n5 filtrare dupa tip\n6 filtrare dupa descriere\n7 print\n8 sortare dupa descriere\n9 sortare dupa titlu\n10 sortare dupa tip si timp\n";
	while (ruleaza) {
		cout << ">>";
		int cmd;
		cin >> cmd;
		try {
			switch (cmd) {
			case 1:
				addUI();
				break;
			case 2:
				removeUI();
				break;
			case 3:
				updateUI();
				break;
			case 4:
				searchUI();
				break;
			case 5:
				filterByType();
				break;
			case 6:
				filterByDescript();
				break;
			case 7:
				printActivities(serv.getAll());
				break;
			case 8:
				sortByDescript();
				break;
			case 9:
				sortByTitle();
				break;
			case 10:
				sortByTypeTime();
				break;
			case 0:
				ruleaza = 0;
				break;
			}
		}
		catch (RepoException& ex) {
			cout << ex << endl;
		}
	}
}