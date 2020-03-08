/*#include<iostream>
#include<fstream>
#include<algorithm>
using namespace std;
ifstream f("in.txt");
void fisier_adiacenta(int a[10][10],int d[10][10],int n)
{
	int  x, y,z;
	while (f >> x >> y>>z) {
		a[x - 1][y - 1] = 1;
		a[y - 1][x - 1] = 1;
		d[x - 1][y - 1] = z;
		d[y - 1][x - 1] = z;
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
void incidenta_adiacenta(int a[10][10],int b[10][10], int n) {
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			if (a[i][j] == 1 && a[i + 1][j] == 1)
				b[i][i + 1] = 1;
		}
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
void adiacenta_incidenta(int a[10][10],int b[10][10],int n) {
	int  k=0;
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++)
		{
			if (a[i][j] == 1)
			{
				b[i][k] = 1;
				b[j][k] = 1;
			}
		}
		k++;
	}
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < k; j++) {
			if (b[i][j] != 1) {
				b[i][j] = 0;
			}
		}
	}
	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < n; j++)
		{
			cout << b[i][j];
			cout << " ";
		}
		cout << endl;
	}
}
void vf_izolat(int a[10][10], int n) {
	int i,j, ok;
	for (i = 0; i < n; i++)
	{		
		ok = 1;
		for (j = 0; j < n; j++)
		{
			if (a[i][j] != 0) {
				ok = 0;
			}
		}
		if (ok == 1) {
			cout << i+1 << ' ';
		}
	}
}
void graf_regular(int a[10][10], int n) {
	int v[10], k, i, j;
	for (i = 0; i < n; i++)
	{
		k = 0;
		for (j = 0; j < n; j++)
		{
			if (a[i][j] == 1) {
				k++;
			}
		}
		v[i] = k;
	}
	int x = v[0], ok = 1;
	for (i = 1; i < n; i++)
	{
		if (v[i] != v[0]) {
			ok = 0;
		}
	}
	if (ok == 1) {
		cout << "Graful este regular.";
	}
	else{
		cout << "Graful nu este regular.";
	}
} 
void matrice_distante(int a[10][10], int d[10][10],int n) {
	int i, j, k;
	for (k = 0; k < n; k++) {
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++)
			{
					d[i][j] = min(d[i][j], d[i][k] + d[k][j]);
			}
		}
	}
	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < n; j++)
		{
			cout << d[i][j];
			cout << " ";
		}
		cout << endl;
	}
}
//DFS
void dfs(int x,int a[10][10],int viz[10] , int n) {
	int i;
	viz[x] = 1;
	for (i = 1; i <= n; i++)
	{
		if (a[x][i] == 1 && viz[i] == 0) dfs(i,a,viz,n);
	}
}
void conex(int n, int a[10][10],int viz[10]) {
	int i, ok=1;
	dfs(1,a,viz,n);
	for (i = 1; i <= n; i++) {
		if (viz[i] == 0) ok=0;
	}
	if (ok == 1) {
		cout << "Graf conex.";
	}
	else cout << "Graful neconex.";
}
int main()
{	
	int n;
	f >> n;
	int a[10][10],b[10][10],viz[10],d[10][10];
	for (int i = 0; i < n; i++)
	{
		viz[i] = 0;
		for (int j = 0; j < n; j++)
		{
			a[i][j] = 0;
			if (i == j) {
				d[i][j] = 0;
			}
			else {
				d[i][j] = INT_MAX/2;
			}

		}
	}
	fisier_adiacenta(a, d, n);
	cout << endl;
	adiacenta_incidenta(a,b,n);
	cout << endl;
	incidenta_adiacenta(a, b, n);
	cout << endl;
	vf_izolat(a, n);
	cout << endl;
	matrice_distante(a, d, n);
	cout << endl;
	graf_regular(a, n);
	cout << endl;
	conex(n, a, viz);
	cout << endl;
	return 0;
}*/
