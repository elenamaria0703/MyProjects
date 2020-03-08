/*#include<iostream>
#include<fstream>
#include<list>
#include<stack>
using namespace std;

ifstream f("kosaraju.txt");

class Graph {
	int V;
	list <int> *adj;

	void fillOrder(int v, bool visited[], stack<int> &Stack);
	void DFS(int v, bool visited[]);
public:

	Graph(int V);
	void addEdge(int u, int v);
	void Kosaraju();
	Graph Transpose();
};

Graph::Graph(int V) {
	this->V = V;
	adj = new list<int>[V];
}

void Graph::DFS(int v, bool visited[]) {
	visited[v] = true;
	cout << v << ' ';
	list<int>::iterator it;
	for (it = adj[v].begin(); it != adj[v].end(); it++) {
		if (!visited[*it]) {
			DFS(*it, visited);
		}
	}
}

Graph Graph::Transpose() {
	Graph G(V);
	for (int v = 0; v < V; v++) {
		list<int>::iterator it;
		for (it = adj[v].begin(); it != adj[v].end(); it++) {
			G.adj[*it].push_back(v);
		}
	}
	return G;
}

void Graph::addEdge(int u, int v) {
	adj[u].push_back(v);
}

void Graph::fillOrder(int v, bool visited[], stack<int> & Stack) {
	visited[v] = true;
	list<int>::iterator it;
	for (it = adj[v].begin(); it != adj[v].end(); it++) {
		if (!visited[*it]) {
			fillOrder(*it, visited, Stack);
		}
	}
	Stack.push(v);
}

void Graph::Kosaraju() {
	stack <int> Stack;
	bool * visited = new bool[V];
	for (int i = 0; i < V; i++) {
		visited[i] = false;
	}
	for (int i = 0; i < V; i++) {
		if (visited[i] == false) {
			fillOrder(i, visited, Stack);
		}
	}
	Graph gr = Transpose();

	for (int i = 0; i < V; i++) {
		visited[i] = false;
	}

	while (!Stack.empty()) {

		int v = Stack.top();
		Stack.pop();

		if (visited[v] == false) {
			gr.DFS(v, visited);
			cout << endl;
		}
	}
}

int main() {
	int V, u, v;
	f >> V;
	Graph G(V);
	while (f >> u >> v) {
		G.addEdge(u, v);
	}
	G.Kosaraju();
	return 0;
}*/