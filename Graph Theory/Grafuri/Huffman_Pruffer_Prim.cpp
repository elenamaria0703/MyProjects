/*#include<iostream>
#include<algorithm>
#include<fstream>
#include<list>
#include<vector>
#include<utility>
#include<queue>

using namespace std;
typedef std::pair<int, int> ramura;
ifstream f("lab5.txt");
ifstream g("input_file.txt");
ifstream h("prim.txt");

class graph {
	int V;
	list<int> *adj;
public:
	graph(int V) :V{ V }, adj{ new list<int>[V] }{};
	void add(int u, int v) {
		adj[u].push_back(v);
		adj[v].push_back(u);
	}
	void remove(int u, int v);
	void printEulerCycle();
	void printEuler(int s);

	int DFS(int v, bool visited[]);

	bool valid(int u, int v);
};
void graph::printEulerCycle() {
	int u = 0;
	for (int i = 0; i < V; i++) {
		if (adj[i].size() % 2 != 0) {
			u = i;
			break;
		}
	}
	printEuler(u);
}
void graph::printEuler(int u) {
	for (auto it = adj[u].begin(); it != adj[u].end(); it++) {
		int v = *it;
		if (v != -1 && valid(u, v)) {
			cout << u << ' ' << v << ' ';
			remove(u, v);
			printEuler(v);
		}
	}
}
bool graph::valid(int u, int v) {
	int count = 0;
	for (auto it = adj[u].begin(); it != adj[u].end(); it++) {
		if (*it != -1) {
			count++;
		}
	}
	if (count == 1) return true;
	bool* visited=new bool[V];
	memset(visited, false, V);
	int count1 = DFS(u, visited);
	remove(u, v);
	memset(visited, false, V);
	int count2 = DFS(u, visited);
	add(u, v);
	return count1 == count2;
}

void graph::remove(int u, int v) {
	auto it = find(adj[u].begin(), adj[u].end(), v);
	*it = -1;
	auto it1 = find(adj[v].begin(), adj[v].end(), u);
	*it1 = -1;
}

int graph::DFS(int v, bool visited[]) {
	visited[v] = true;
	int count = 1;
	for (auto it = adj[v].begin(); it != adj[v].end(); it++) {
		if (*it != -1 && !visited[*it]) {
			count += DFS(*it, visited);
		}
	}
	return count;
}

typedef struct {
	int u;
	int v;
	int w;
}edge;

typedef std::pair<int, int> key;

typedef struct {
	int V;
	vector<edge> edges;
}Graph;              

key extract_min(vector<key>& vect) {
	key min = make_pair(INT_MAX, 0);
	std::vector<key>::iterator pos;
	for (auto it = vect.begin(); it != vect.end(); it++) {
		if ((*it).first < min.first) {
			min = *it;
			pos = it;
		}
	}
	vect.erase(pos);
	return min;
}
int det_pos(vector<key>& vect, int v) {
	int i;
	for (i = 0; i < vect.size(); i++) {
		if (vect[i].second == v) break;
	}
	return i;
}

void Alg_Prim(Graph g, int r) {
	vector<int> parent(g.V, 0);
	vector<key> pq;
	vector<int> aux(g.V, 0);
	for (int i = 0; i < g.V; i++) {
		pq.push_back(make_pair(INT_MAX, i));
	}
	pq[r].first = 0;
	while (pq.begin() != pq.end()) {
		key k = extract_min(pq);
		for (auto e : g.edges) {
			if (e.u == k.second) {
				if (find_if(pq.begin(), pq.end(), [e](key k) {return k.second == e.v; }) < pq.end() && e.w < pq[det_pos(pq,e.v)].first) {
					parent[e.v] = e.u;
					pq[det_pos(pq, e.v)].first = e.w;
					aux[e.v] = e.w;
				}
			}
		}
	}
	for (int i = 1; i < parent.size(); i++) {
		cout << parent[i] << ' ' << i << ' ' << aux[i] << endl;
	}
}

class nod {
public:
	char c;
	nod* st;
	nod* dr;
	int fr;
	nod(char c, int fr) :c{ c }, st{ nullptr }, dr{ nullptr }, fr{ fr }{};
};

auto compare = [](nod* nod1, nod* nod2) {
	return nod1->fr > nod2->fr;
};

priority_queue<nod*, vector<nod*>, decltype(compare)> pq(compare);
nod* tree_huffman(vector<int>& frec) {
	int i, m = 0;
	for (int i = 0; i < frec.size(); i++) {
		if (frec[i] != 0) {
			char c;
			c = (char)(i + 'a');
			nod* n = new nod{ c,frec[i] };
			pq.push(n);
			m++;
		}
	}
	for (i = 1; i < m; i++) {
		nod* z = new nod{ '#',0 };
		nod* st = pq.top();
		pq.pop();
		nod* dr = pq.top();
		pq.pop();
		z->fr = st->fr + dr->fr;
		z->st = st;
		z->dr = dr;
		pq.push(z);
	}
	return pq.top();
}
void print_Codes(nod* r, vector<int>& arr, int top) {
	if (r->st != nullptr) {
		arr[top] = 0;
		print_Codes(r->st, arr, top + 1);
	}
	if (r->dr != nullptr) {
		arr[top] = 1;
		print_Codes(r->dr, arr, top + 1);
	}
	if (r->dr == nullptr && r->st == nullptr) {
		cout << r->c << ' ';
		for (int i = 0; i < top; i++) {
			cout << arr[i];
		}
		cout << endl;
	}

}
string decodeHuffman(nod* r, string s) {
	string aux = "";
	nod* crt = r;
	for (int i = 0; i < s.size(); i++) {
		if (s[i] == '0')
			crt = crt->st;
		else
			crt = crt->dr;
		if (crt->st == nullptr && crt->dr == nullptr) {
			aux += crt->c;
			crt = r;
		}
	}
	return aux + '\0';
}

void HuffmanCodes(vector<int>& frec) {
	nod* r = tree_huffman(frec);
	int top = 0;
	vector<int> arr(10, 0);
	string s = "01011";
	cout<<decodeHuffman(r, s);
	print_Codes(r, arr, top);
}

vector<int> make_parent(int n, vector<ramura>& arbore) {
	vector<int> parent(n + 1, 0);
	for (auto r : arbore) {
		parent[r.second] = r.first;
	}
	return parent;
}
vector<int> cod_Prufer(int n, vector<ramura>& arbore) {
	
	vector<int> marcat(n + 1, 0);
	vector<int> prufer;
	
	int poz;
	while (!arbore.empty()) {
		vector<int> parent = make_parent(n, arbore);
		vector<int> frecv(n + 1, 0);
		for (auto r : parent) {
			frecv[r] = 1;
		}
		int poz;
		for (int i = 1; i <= n;i++) {
			if (frecv[i] == 0 && marcat[i] != 1) {
				poz = i;
				break;
			}
		}
		marcat[poz] = 1;
		prufer.push_back(parent[poz]);
		std::vector<ramura>::iterator it;
		for (it = arbore.begin(); it != arbore.end(); it++) {
			if ((*it).second==poz) {
				break;
			}
		}
		arbore.erase(it);
		parent = make_parent(n, arbore);
	}
	for (int i : prufer) {
		cout << i << ' ';
	}
	return prufer;
}

void decod_prufer(int n,vector<int>& cod) {
	queue<int> K;
	vector<ramura> arbore;
	
	for (int i = 1; i < n; i++) {
		for (int c : cod) {
			K.push(c);
		}
		vector<int> frecv(n + 1, 0);
		for (int i : cod) {
			frecv[i] = 1;
		}
		cod.clear();
		int x = K.front();
		int j = 1;
		for (j = 1; j <= n; j++) {
			if (frecv[j] == 0) break;
		}
		int y = j;
		ramura r = make_pair(x, y);
		arbore.push_back(r);
		K.pop();
		K.push(y);
		while (!K.empty()) {
			int z = K.front();
			cod.push_back(z);
			K.pop();
		}
	}
	for (auto r : arbore) {
		cout << r.first << ' ' << r.second << endl;
	}

}

int main() {
	int n, x, y, m = 0, u, v, w, V;
	Graph gr;
	vector<int> frec(30, 0);
	f >> n;
	vector<ramura> arbore;
	while (f >> x >> y) {
		ramura r = make_pair(x, y);
		arbore.push_back(r);
	}
	vector<int> cod=cod_Prufer(n, arbore);
	cout << endl;
	decod_prufer(n, cod);

	cout << endl;
	char c;
	while (g >> c) {
		frec[c - 'a']++;
	}
	HuffmanCodes(frec);
	h >> V;
	gr.V = V;
	while (h >> u >> v>>w) {
		edge e;
		e.u = u;
		e.v = v;
		e.w = w;
		gr.edges.push_back(e);
	}

	cout << endl;
	Alg_Prim(gr, 0);

	cout << endl;
	//Kruskal(gr);

	cout << endl;
	graph g1(5);
	g1.add(1, 0);
	g1.add(0, 2);
	g1.add(2, 1);
	g1.add(0, 3);
	g1.add(3, 4);
	g1.add(3, 2);
	g1.add(3, 1);
	g1.add(2, 4);
	g1.printEulerCycle();
	return 0;
}*/