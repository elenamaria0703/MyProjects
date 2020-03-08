#pragma once
#include "Pet.h"
template <typename T>

class Nod {
public:
	T value;
	Nod* urm;

	Nod(const T& p) :value{ p }, urm{ nullptr }{};
	Nod(const T& p, Nod* urm) :value{ p }, urm{ urm }{};

	auto getVal() {
		return value;
	}
	Nod* next() {
		return urm;
	}
};

template <typename T>
class MyList
{
	Nod<T>* prim;
	Nod<T>* copie(Nod<T>* nodC) {
		if (nodC == nullptr) return nullptr;
		return new Nod<T>{ nodC->value,copie(nodC->urm) };
	}
	void dealoca() {
		Nod<T>* current = prim;
		while (current != nullptr) {
			auto aux = current;
			current = current->urm;
			delete aux;
		}
	}

public:
	MyList() :prim{ nullptr } {};

	MyList(const MyList<T>& ot) {
		prim = copie(ot.prim);
	}

	MyList& operator=(const MyList<T>& ot) {
		dealoca();
		prim = copie(ot.prim);
		return *this;
	}

	void addBegin(const T& p) {
		prim = new Nod<T>{ p,prim };

	};

	void push_back(const T& p);

	int size() const{
		int lg = 0;
		Nod<T>* current = prim;
		while (current != nullptr) {
			current = current->urm;
			lg++;
		}
		return lg;
	}

	~MyList() {
		dealoca();
	}
};

