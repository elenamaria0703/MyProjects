/*#include<new>
#include<vector>
#include<iostream>
#include<fstream>
#include<queue>
#include<utility>

using namespace std;

ifstream f("bf.txt");
ifstream g("dj.txt");
ifstream k("john.txt");

typedef std::pair<int, int> nod;


priority_queue <nod, vector<nod>, greater<nod>>pq;


typedef struct {
	int u;
	int v;
	int w;
}Edge;

typedef struct {
	int V ;
	int E;
	vector<Edge> edges;
}Graph;

bool Bellman_Ford(Graph graph, int s, int dist[10]) {
	int V = graph.V;
	int E = graph.E;
	for (int i = 0; i < V; i++)
		dist[i] = INT_MAX;
	dist[s] = 0;
	for (int i = 1; i <= V - 1; i++)
	{
		for (int j = 0; j < E; j++)
		{
			int u = graph.edges[j].u;
			int v = graph.edges[j].v;
			int w = graph.edges[j].w;
			if (dist[u] != INT_MAX && dist[u] + w < dist[v])
				dist[v] = dist[u] + w;
				
		}
	}
	for (int i = 0; i < E; i++)
	{
		int u = graph.edges[i].u;
		int v = graph.edges[i].v;
		int w = graph.edges[i].w;
		if (dist[u] != INT_MAX && dist[u] + w < dist[v])
		{
			return false;
		}
	}
	return true;
}

void Dijkstra_queue(Graph graph,int s, vector<int>& dist) {
	int V = graph.V;
	int E = graph.E;

	pq.push(make_pair(0, s));
	dist[s] = 0;

	while (!pq.empty()) {
		int u = pq.top().second;
		pq.pop();
		for (Edge edge : graph.edges) {
			if (edge.u == u) {
				int v = edge.v;
				int w = edge.w;
				if (dist[v] > dist[u] + w) {
					dist[v] = dist[u] + w;
					pq.push(make_pair(dist[v], v));
				}
			}
		}
	}
}

void Johnson_algorithm(Graph graph) {
	int V = graph.V;
	int E = graph.E;
	int s = V;
	int h[10];
	vector<int>Dist(V, INT_MAX);

	for (int i = 0; i < V; i++) {
		Edge edge;
		edge.u = s;
		edge.v = i;
		edge.w = 0;
		graph.edges.push_back(edge);
		graph.E++;
	}
	graph.V++;
	bool cod = Bellman_Ford(graph, s, h);
	if (cod == false) {
		cout << "cicluri negative";
		return;
	}
	else {
		Graph new_graph;
		for (Edge edge : graph.edges) {
			int x = edge.w + h[edge.u] - h[edge.v];
			Edge new_edge;
			new_edge.u = edge.u;
			new_edge.v = edge.v;
			new_edge.w = x;
			new_graph.edges.push_back(new_edge);
		}
		new_graph.V = V;
		new_graph.E = E;
		std::vector<Edge>::iterator it;
		it = new_graph.edges.end() - 1;
		int nr = s;
		while (nr != 0) {
			new_graph.edges.erase(it);
			nr--;
			new_graph.E--;
			it = new_graph.edges.end() - 1;
		}
		new_graph.V--;
		int D[20][20];
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				D[i][j] = 0;
			}
		}
		for (int u = 0; u < V; u++) {
			Dijkstra_queue(new_graph, u, Dist);
			for (int v = 0; v < V; v++) {
				if (Dist[v] == INT_MAX) {
					break;
				}
				D[u][v] = Dist[v] + h[v] - h[u];
			}
		}
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				cout << D[i][j] << ' ';
			}
			cout << endl;
		}
	}


}

int main() {
	int V, E, u, v, w;
	int dist[20];
	vector<int>vect(20,INT_MAX);
	f >> V >> E;
	Graph graph;
	graph.E = E;
	graph.V = V;
	while (f >> u >> v >> w) {
		Edge edge;
		edge.u = u;
		edge.v = v;
		edge.w = w;
		graph.edges.push_back(edge);
	}
	Bellman_Ford(graph, 3,dist);
	for (int i = 0; i < V; i++) {
		cout << i << ' ' << dist[i] << endl;
	}
	cout << endl;
	g >> V >> E;
	Graph graph1;
	graph1.E = E;
	graph1.V = V;
	while (g >> u >> v >> w) {
		Edge edge;
		edge.u = u;
		edge.v = v;
		edge.w = w;
		graph1.edges.push_back(edge);
	}
	Dijkstra_queue(graph1, 0,vect);
	for (int i = 0; i < V; i++) {
		cout << i << ' ' << vect[i] << endl;
	}
	cout << endl;
	k >> V >> E;
	Graph graph2;
	graph2.E = E;
	graph2.V = V;
	while (k >> u >> v >> w) {
		Edge edge;
		edge.u = u;
		edge.v = v;
		edge.w = w;
		graph2.edges.push_back(edge);
	}
	Johnson_algorithm(graph2);
	return 0;
}*/