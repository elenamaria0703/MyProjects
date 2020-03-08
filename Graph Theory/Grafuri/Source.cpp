
#include<iostream>
#include<fstream>
#include<vector>
#include<list>
#include<string>
#include<utility>
#include<algorithm>
#include<queue>
using namespace std;
ifstream f("test2.txt");

#define V 5
int cap[V][V];
int flux[V][V];
vector<int> e(V,0);
vector<int> h(V, 0);
list<int> *adj = new list<int>[V];


int main() {
	int x, y, c;
	while (f >> x >> y >> c) {
		cap[x][y] = c;
		adj[x].push_back(y);
	}
	Pompare_Preflux(0, 4);
	return 0;
}
