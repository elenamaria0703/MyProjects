#include "DO.h"
#include <iostream>

// aici implementarea operatiilor din Container.h


DO::DO(Relatie r) : Rel{ r }, size{ 0 }, capacity{ 3 }, elems{ new TElem[3] }{
	// TBA 
}


// restul operatiilor

TValoare DO::adauga(TCheie c, TValoare v) {
	//O(n)
	for (int i = 0; i < dim();i++) {
		TElem pair = elems[i];
		if (pair.first == c) {
			TValoare value = pair.second;
			pair.second = v;
			return value;
		}
	}
	if (dim()==0) {
		TElem Pair = std::make_pair(c, v);
		adaugaSfarsit(Pair);
		return -1;
	}
	int it = 0;
	TElem pair = elems[it];
	while (Rel((TCheie)pair.first, c)) {
		it++;
		if (it == dim())
		{
			break;
		}
		pair = elems[it];
	}
	TElem Pair = std::make_pair(c, v);
	inserare(it, Pair);
	return -1;

}

TValoare DO::cauta(TCheie c) const{
	//O(n)
	for (int i = 0; i < dim(); i++) {
		TElem pair = elems[i];
		if (pair.first == c) {
			return pair.second;
		}
	}
	return -1;
}

TValoare DO:: sterge(TCheie c) {
	//O(n)
	int poz = 0;
	for (int i = 0; i < dim(); i++) {
		TElem pair = elems[i];
		if (pair.first == c) {
			TValoare value = pair.second;
			stergeEl(poz);
			return value;
		}
		poz++;
	}
	return -1;
}

int DO:: dim() const{
	//theta(1)
	return this->size;
}

bool DO::vid() const {
	//theta(1)
	return this->size == 0;
}
Iterator DO::iterator() const {
	return Iterator(*this);
}

DO::~DO() {
	delete[] elems;
	// TBA
}
//functii vector
void DO::resize() {
	//theta(n)
	TElem* nelems = new TElem[this->capacity * 2];
	for (int i = 0; i < this->size; i++) {
		nelems[i] = this->elems[i];
	}
	this->elems = nelems;
	this->capacity *= 2;
}

void DO::adaugaSfarsit(TElem el) {
	//theta(1) amortizat
	if (this->size == this->capacity) {
		this->resize();
	}
	this->elems[this->size] = el;
	this->size++;
}

void DO::inserare(int i, TElem el) {
	//O(n)
	if (this->size == this->capacity) {
		this->resize();
	}
	for (int j = dim(); j >= i; j--) {
		this->elems[j+1] = this->elems[j];
	}
	this->elems[i] = el;
	this->size++;
}

void DO::stergeEl(int i) {
	//theta(n)
	for (int j = i; j <= dim(); j++) {
		this->elems[j] = this->elems[j + 1];
	}
	size--;
}
