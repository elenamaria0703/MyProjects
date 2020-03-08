#include "IteratroMat.h"
#include<exception>
using std::exception;
class myexception : public exception {
	virtual const char* what() const throw()
	{
		return what();
	}
}itexc;


IteratorMat::IteratorMat(const Matrice& m, int col) : mat{ m }, col{ col }{
	// initializare curent si alte atribute specifice
	if (col >= mat.nrC) throw itexc;
}

TElem IteratorMat::element() {
	//TBA
	return mat.element(lin, col);
}

bool IteratorMat::valid() {
	//TBA
	return lin < mat.nrL && lin>=0;
}

void IteratorMat::urmator() {
	//TBA
	lin++;
}

void IteratorMat::anterior()
{
	lin--;
}

void IteratorMat::prim() {
	//TBA
	lin = 0;
}
