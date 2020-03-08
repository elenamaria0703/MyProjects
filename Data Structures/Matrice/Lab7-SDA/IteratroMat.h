#pragma once
#include "Matrice.h"

class Matrice;

class IteratorMat
{
	friend class Matrice;

private:
	//iteratorul memoreaza o referinta catre container
	const Matrice& mat;
	int col;
	//aici alte atribute specifice: curent, etc
	int lin = 0;
	IteratorMat(const Matrice& m,int col);

public:
	TElem element();
	bool valid();
	void urmator();
	void anterior();
	void prim();
};
