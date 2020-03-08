#include "Personaj.h"
#include "Vector.h"
#include<vector>
#define _CRTDBG_MAP_ALLOC
#include<crtdbg.h>
//#include<crtdbg>
#include<iostream>
using namespace std;

int main() {
	{
		Personaj p{ 23,"cersei",23.45 };
		Personaj p1{ 24,"daenerys",23.45 };
		Personaj p2{ 25,"khal drogo",23.45 };
		Personaj cheie{ 23,"",NULL };

		auto x = p2;
		/*auto xx = { 1,2,3,4 };
		for (auto x : xx) {
			cout << x << ' ';
		}*/
		cout << p.get_nume() << endl;

		vector<Personaj> v;
		Vector<Personaj> V;
		Vector<int> V1;

		v.push_back(p);
		v.push_back(p1);
		v.push_back(p2);
		auto it = find(v.begin(), v.end(), cheie);
		cout << (*it).get_nume();

		V.adauga(p1);
		V.adauga(p2);
		auto it1 = V.begin();
	}
	
	_CrtDumpMemoryLeaks();

	return 0;
}