/*typedef std::pair<int, int> subset; //subset.first=parent, subset.second=rank
int find(vector<subset>& subs, int i) {
	if (subs[i].first != i) {
		subs[i].first = find(subs, subs[i].first);
	}
	return subs[i].first;
}
void Union(vector<subset>& subs, int x, int y) {
	int xroot = find(subs, x);
	int yroot = find(subs, y);

	if (subs[xroot].second < subs[yroot].second)
		subs[xroot].first = yroot;
	else if (subs[xroot].second > subs[yroot].second)
		subs[yroot].first = xroot;
	else {
		subs[yroot].first = xroot;
		subs[xroot].second++;
	}
}
bool comp(edge e1, edge e2) {
	return e1.w > e2.w;
}
void Kruskal(Graph g) {
	int i = 0, j = 0;
	vector<edge> rez;
	vector<subset> sub(g.V,make_pair(0,0));
	sort(g.edges.begin(), g.edges.end(), comp);

	for (int v = 0; v < g.V; v++) {
		sub[v].first = v;
		sub[v].second = 0;
	}

	while (j < g.V - 1) {
		edge e = g.edges[i++];
		int x = find(sub, e.u);
		int y = find(sub, e.v);
		if (x != y) {
			rez.push_back(e);
			Union(sub, x, y);
			j++;
		}
	}
	for (i = 0; i < j; i++) {
		cout << rez[i].u << ' ' << rez[i].v << ' ' << rez[i].w << endl;;
	}
}*/