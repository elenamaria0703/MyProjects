#include<iostream>
#include<algorithm>
#include<fstream>
#include<list>
#include<vector>
#include<utility>
#include<queue>

using namespace std;


class graph {
public:
	int V;
	list<int>* adj;
	graph(int V) :V{ V }, adj{ new list<int>[V] }{};
	void addEdge(int u, int v) {
		adj[u].push_back(v);
		adj[v].push_back(u);
	}
};

typedef pair<int, int> degree;
bool compare(degree& d1, degree& d2) {
	return d1.second > d2.second;
}

void Welsh_Powell(graph g) {
	vector<int> color(g.V, -1);
	vector<degree> list;
	for (int i = 0; i < g.V; i++) {
		list.push_back(make_pair(i, g.adj[i].size()));
	}
}