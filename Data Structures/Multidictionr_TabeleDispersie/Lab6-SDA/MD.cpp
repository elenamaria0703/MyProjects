#include "MD.h"
MD::MD()
{
	m = 13;
	size = 0;
	l = new Nod* [m];
	for (int i = 0; i < m; i++) {
		l[i] = NULL;
	}
}

void MD::adauga(TCheie c, TValoare v)//theta(1)
{
	int poz = Hashing(c);
	TElem e = std::make_pair(c, v);
	Nod* nod = alocaNod(e);
	nod->urm = l[poz];
	l[poz] = nod;
	size++;
}

vector<TValoare> MD::cauta(TCheie c) const//O(n)
{
	vector<TValoare> valori;
	if (vid()) return valori;
	int poz = Hashing(c);
	Nod* curent = l[poz];
	while (curent != NULL) {
		if (curent->e.first == c) valori.push_back(curent->e.second);
		curent = curent->urm;
	}
	return valori;
}

bool MD::sterge(TCheie c, TValoare v)//O(n)
{
	if (vid()) return false;
	int poz = Hashing(c);
	Nod* curent = l[poz];
	Nod* prec = NULL;
	while (curent != NULL) {
		if (curent->e.first == c && curent->e.second == v) break;
		prec = curent;
		curent = curent->urm;
	}
	if (curent == NULL) return false;
	if (prec == NULL) {
		l[poz] = curent->urm;
		delete curent;
		size--;
		return true;
	}
	prec->urm = curent->urm;
	delete curent;
	size--;
	return true;
}

int MD::dim() const //theta(1)
{
	return size;
}

bool MD::vid() const //theta(1)
{
	return size == 0;
}

IteratorMD MD::iterator() const
{
	return IteratorMD(*this);
}


TCheie MD::cheieMaxima() const//theta(n)
{
	TCheie max = INT_MIN;
	if (vid()) return NULL_TCHEIE;
	auto it = iterator();
	while (it.valid()) {
		TElem el = it.element();
		TCheie cheie = el.first;
		if (cheie > max) max = cheie;
		it.urmator();
	}
	return max;
}

MD::~MD()
{
	if (vid()) return;
	for (int i = 0; i < m; i++) {
		delete l[i];
	}
	delete[] l;
}
