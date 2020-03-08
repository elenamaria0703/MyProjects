/*1.Găsiți fluxul maxim într-un graf folosind:
Ford-Fulkerson sau Edmonds-Karp;
pompare-preflux sau pompare topologică.
2.Colorați un graf.*/



#include<iostream>
#include<vector>
#include<algorithm>
#include<list>
#include<string>
#include<fstream>
#include<utility>
using namespace std;

ifstream k("lab6.txt");
ifstream fin("pomp_pre.txt");

#define Ve 5
int cap[Ve][Ve];
int flux[Ve][Ve];
list<int> *adj = new list<int>[Ve];
vector<int> h(Ve, 0);
vector<int> e(Ve, 0);

void Initializare_Preflux(int s, int t) {
	h[s] = Ve;
	for (auto v : adj[s]) {
		flux[s][v] = cap[s][v];
		e[v] = cap[s][v];
		e[s] = e[s] - cap[s][v];
	}

}

void Inaltare(int u) {
	int minh = INT_MAX;
	for (auto v : adj[u]) {
		if (h[u] < h[v] + 1) {
			minh = min(minh, h[v]);
		}
	}
	h[u] = minh + 1;
}

void Pompare(int u, int v) {
	int dif = min(e[u], cap[u][v]);
	if (cap[u][v] > 0) {
		cap[u][v] = cap[u][v] - dif;
		flux[u][v] = flux[u][v] + dif;
	}

	flux[v][u] = flux[v][u] - dif;
	cap[v][u] = cap[v][u] + dif;
	e[v] = e[v] + dif;
	e[u] = e[u] - dif;
}

void Pompare_Preflux(int s, int t) {
	Initializare_Preflux(s,t);
	while (true) {
		bool ok = true;
		for (int u = 0; u < Ve; u++) {
			if (u != s && u != t) {
				if (e[u] > 0) {
					for (auto v : adj[u]) {
						if (cap[u][v] > 0 && h[u] == h[v] + 1) {
							Pompare(u, v);
							ok = false;
						}
						else if (h[u] <= h[v]) {
							Inaltare(u);
							ok = false;
						}
					}
				}
			}
		}
		if (ok) break;
	}
	cout << e[t];
}
class Graph {
public:
	int V;
	class edge {
	public:
		int u;
		int v;
		int cap;
		edge(int u, int v, int cap) :u{ u }, v{ v }, cap{ cap }{};
	};
	vector<edge> edges;
	Graph(int V) :V{ V } {};
	void addEdge(int u, int v, int cap) {
		edge e{ u,v,cap };
		edges.push_back(e);
	}
};

void dfs(Graph g, int n, vector<int>& parent,bool visited[]) {
	visited[n] = true;
	for (auto it = g.edges.begin(); it < g.edges.end(); it++) {
		if ((*it).u == n) {
			if (!visited[(*it).v] && (*it).cap>0) {
				parent[(*it).v] = n;
				dfs(g, (*it).v, parent,visited);
			}
		}
	}

}
int Ford_Fulkerson(Graph g, int s, int t) {
	bool* visited = new bool[g.V]{ false };
	vector<int> parent(g.V, 0);
	int max_flow = 0;
	parent[s] = -1;
	Graph rg{ g.V };
	rg.edges = g.edges;
	while (1) {
		for (int i = 0; i < g.V; i++) {
			visited[i] = false;
		}
		dfs(rg, s, parent, visited);
		if (!visited[t]) break;
		int path_flow = INT_MAX;
		int v = t;
		while (v != s) {
			int u = parent[v];
			for (auto e : rg.edges) {
				if (e.u == u && e.v == v) {
					path_flow = min(path_flow, e.cap);
				}
			}
			v = u;
		}
		v = t;
		while (v != s) {
			bool ok = true;
			int u = parent[v];
			for (auto& e : rg.edges) {
				if (e.u == u && e.v == v) {
					e.cap = e.cap - path_flow;
				}
				else if (e.u == v && e.v == u) {
					e.cap = e.cap + path_flow;
					ok = false;
				}
			}
			if (ok == true) {
				rg.addEdge(v, u, path_flow);
			}
			v = u;
		}
		max_flow += path_flow;
	}
	return max_flow;
}

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

typedef std::pair<int, int> degree;
bool compare(degree& d1, degree& d2) {
	return d1.second > d2.second;
}
void Welsh_Powell(graph gr) {
	vector<int> color(gr.V, -1);
	vector<degree> list;
	int col = 0;
	for (int u = 0; u < gr.V; u++) {
		list.push_back(make_pair(u,gr.adj[u].size()));
	}
	sort(list.begin(), list.end(), compare);

	degree d = list[0];
	color[d.first] = col;

	while (1) {
		int k = 1;
		for (auto it = list.begin()+1; it < list.end(); it++) {
			vector<int> colored;
			int de = (*it).first;
			for (int u = 0; u < gr.V; u++) {
				if (color[u] == col) {
					colored.push_back(u);//nodurile colorate cu col
				}
			}
			bool ok = true;
			for (int i = 0; i < colored.size(); i++) {
				int u = colored[i];
				for (int v : gr.adj[u]) {
					if (v == de) ok = false;
				}
			}
			if (ok == true) {
				color[de] = col;
				k++;
			}
		}
		col++;
		for (int i = 0; i < k; i++) {
			for (auto ite = list.begin(); ite < list.end(); ite++) {
				if (color[(*ite).first] != -1) {
					list.erase(ite);
					break;
				}
			}
		}
		if (list.empty()) break;
		degree d = list[0];
		color[d.first] = col;
	}
	for (int i = 0; i < color.size(); i++) {
		cout << "Vertex:" << i << ' ' << "color:" << color[i]<< endl;
	}
}

int main() {
	
	Graph g{ 5 };
	g.addEdge(0, 1, 3);
	g.addEdge(0, 2, 5);
	g.addEdge(0, 4, 6);
	g.addEdge(2, 1, 3);
	g.addEdge(4, 2, 2);
	g.addEdge(4, 3, 4);
	g.addEdge(1, 3, 6);
	g.addEdge(2, 3, 4);
	int rez = Ford_Fulkerson(g, 0, 3);
	cout << rez << endl;

	graph g1{ 11 };
	int u, v;
	while (k >> u >> v) {
		g1.addEdge(u, v);
	}
	Welsh_Powell(g1);

	int x, y, c;
	while (fin >> x >> y >> c) {
		cap[x][y] = c;
		adj[x].push_back(y);
	}
	Pompare_Preflux(0, 4);
	return 0;
}