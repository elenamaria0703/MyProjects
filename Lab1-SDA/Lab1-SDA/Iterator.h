#pragma once
#include "DO.h"

class Iterator
{
	friend class DO;

private:
	//iteratorul memoreaza o referinta catre container
	const DO& Dictionar;
	int pozCurent;
	//aici alte atribute specifice: curent, etc

	Iterator(const DO& dictionar);
	// initializare curent si alte atribute specifice

public:
	TElem element();
	bool valid();
	void urmator();
	void prim();
};
