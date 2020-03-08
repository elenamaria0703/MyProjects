#pragma once
#include "MD.h"

class MD;

class IteratorMD
{
	friend class MD;

private:
	//iteratorul memoreaza o referinta catre container
	const MD& md;
	//aici alte atribute specifice: curent, etc
	int pozCurenta;
	Nod* curent;
	int lg;

	IteratorMD(const MD& md);
	IteratorMD() = default;

public:
	TElem element();
	bool valid();
	void urmator();
	void prim();
};
