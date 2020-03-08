/*#include<iostream>
#include<fstream>
#include<queue>

using namespace std;

ifstream f("in.txt");
ifstream f1("in1.txt");
ifstream g("labirint.txt");
queue <int> Q;

void fisier_adiacenta(int a[10][10], int n)
{
	int  x, y;
	while (f >> x >> y) {
		a[x - 1][y - 1] = 1;
		a[y - 1][x - 1] = 1;
	}
	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < n; j++)
		{
			cout << a[i][j];
			cout << " ";
		}
		cout << endl;
	}
}
void fisier_adiacenta_orientat(int b[10][10], int m) {
	int  x, y;
	while (f1 >> x >> y) {
		b[x - 1][y - 1] = 1;
	}
	for (int i = 0; i < m; i++)
	{
		for (int j = 0; j < m; j++)
		{
			cout << b[i][j] << ' ';
		}
		cout << endl;
	}
}
//BFS
void Moore(int u, int a[10][10], int n, int l[10], int p[10]) {
	int i, x, j;
	for (i = 0; i < n; i++) {
		l[i] = INT_MAX;
		p[i] = 0;
	}
	l[u - 1] = 0;
	Q.push(u);
	while (!Q.empty()) {
		x = Q.front();
		Q.pop();
		for (j = 0; j < n; j++) {
			if (a[x - 1][j] == 1) {
				if (l[j] == INT_MAX) {
					p[j] = x;
					l[j] = l[x - 1] + 1;
					Q.push(j + 1);
				}
			}
		}
	}
}

void drum_Moore(int l[10], int p[10], int v) {
	int k, u[10], i;
	k = l[v - 1];
	if (k == INT_MAX) {
		cout << "Nu exista drum";
		return;
	}
	for (i = 0; i <= k; i++) {
		u[i] = 0;
	}
	u[k] = v;
	while (k != 0) {
		u[k - 1] = p[u[k] - 1];
		k--;
	}
	k = l[v - 1];
	for (i = 0; i <= k; i++) {
		cout << u[i] << ' ';
	}
	cout << endl;
}

void inchiderea_tranzitiva(int b[10][10], int m) {
	int i, p[10], l[10], rez[10][10], j, k;
	for (i = 0; i < m; i++) {
		p[i] = 0;
		l[i] = INT_MAX;
	}
	for (i = 0; i < m; i++) {
		Moore(i + 1, b, m, l, p);
		for (j = 0; j < m; j++) {
			if (l[j] >= 2 && l[j] != INT_MAX) {
				rez[i][j] = 1;
			}
		}
		for (k = 0; k < m; k++) {
			p[k] = 0;
			l[k] = INT_MAX;
		}
	}
	for (int i = 0; i < m; i++)
	{
		for (int j = 0; j < m; j++)
		{
			if (rez[i][j] == 1) {
				b[i][j] = 1;
			}
			cout << b[i][j] << ' ';
		}
		cout << endl;
	}
}

int main() {
	int a[10][10], m, n, l[10], p[10], y, v, b[10][10];
	f >> n;
	f1 >> m;
	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < n; j++) {
			a[i][j] = 0;

		}
	}
	for (int i = 0; i < m; i++)
	{
		for (int j = 0; j < m; j++) {
			b[i][j] = 0;
			if (i == j) {
				b[i][j] = 1;
			}

		}
	}
	fisier_adiacenta(a, n);
	cout << endl;
	fisier_adiacenta_orientat(b, m);
	cout << endl;
	cout << "Dati vf sursa:";
	cin >> y;
	Moore(y, a, n, l, p);
	cout << "Dati vf destinatie:";
	cin >> v;
	drum_Moore(l, p, v);
	cout << endl;
	inchiderea_tranzitiva(b, m);
	return 0;
}*/