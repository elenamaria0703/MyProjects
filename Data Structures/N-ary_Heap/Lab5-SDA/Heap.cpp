#include "Heap.h"


void Heap::realoca() 
{
	TElem* aux = new TElem[cp * 2];
	for (int i = 0; i < size; i++) {
		aux[i] = elems[i];
	}
	cp = cp * 2;
	delete[] elems;
	elems = aux;
}

int Heap::detParinte(int poz)
{
	return (poz-1)/n;
}

int Heap::detCopil(int p, int c)
{
	return p*n+c;
}

int Heap::detCelMaiMareCopil(int poz) //O(n)
{
	int copil = detCopil(poz, 1);
	int maxim = -0x3f3f3f3f;
	int poz_max = -1;
	int kcopil = 1;
	while (copil < size && kcopil <= n)
	{
		if (elems[copil] > maxim)
		{
			maxim = elems[copil];
			poz_max = copil;
		}
		kcopil++;
		copil = detCopil(poz, kcopil);
	}
	return poz_max;
}

bool Heap::maiAreCopii(int poz) //theta(1)
{
	if (n*poz + 1 > size - 1)return false;
	return true;
}

void Heap::urca() //O(log(m)) 
{
	int pozCurent = size-1;
	int pozParinte = detParinte(pozCurent);
	while (pozParinte >= 0 && elems[pozParinte] < elems[pozCurent]) {
		TElem aux = elems[pozParinte];
		elems[pozParinte] = elems[pozCurent];
		elems[pozCurent] = aux;
		pozCurent = pozParinte;
		pozParinte = detParinte(pozParinte);
	}
}

void Heap::coboara()//O(nlog(m)) m-nr elem
{
	TElem el = elems[0];
	int pozCurent = 0;
	while (maiAreCopii(pozCurent) && detCelMaiMareCopil(pozCurent) != -1)
	{
		int pozCopil = detCelMaiMareCopil(pozCurent);
		TElem aux = elems[pozCurent];
		elems[pozCurent] = elems[pozCopil];
		elems[pozCopil] = aux;
		pozCurent = pozCopil;
	}
}

Heap::~Heap()
{
	delete[] elems;
}

void Heap::push(TElem el)//O(log(m)) amortizat
{
	if (size == cp) {
		realoca();
	}
	elems[size] = el;
	size++;
	urca();
}

TElem Heap::pop()//O(nlog(m))
{
	TElem el = elems[0];
	elems[0] = elems[size - 1];
	size--;
	coboara();
	return el;
}

TElem Heap::top() //theta(1)
{
	return elems[0];
}

bool Heap::empty()//theta(1)
{
	
	return (size == 0);
}
