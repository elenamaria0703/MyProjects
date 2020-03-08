#include "Iterator.h"
#include "DO.h"


Iterator::Iterator(const DO& dictionar) : Dictionar(dictionar) {
	// initializare curent si alte atribute specifices
	pozCurent = 0;
}

TElem Iterator::element() {
	//TBA
	return Dictionar.elems[pozCurent];
}

bool Iterator::valid() {
	//TBA
	if (pozCurent<Dictionar.dim()) {
		return true;
	}
	return false;
}

void Iterator::urmator() {
	//TBA
	pozCurent++;;
}

void Iterator::prim() {
	//TBA
	//iteratorVect = Dict.Dictionar.begin();
	pozCurent = 0;
}
