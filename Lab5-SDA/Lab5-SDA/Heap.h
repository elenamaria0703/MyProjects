#pragma once
typedef int TElem;
class Heap
{
private:
	int n;
	int cp;
	TElem* elems;
	int size;
	

	void realoca();
	int detParinte(int poz);
	int detCopil(int p, int c);
	int detCelMaiMareCopil(int poz);
	bool maiAreCopii(int poz);
	void urca();
	void coboara();

public:
	
	Heap(int n) :n{ n },cp { 5 }, size{ 0 }, elems{ new TElem[cp] }{};
	~Heap();

	void push(TElem el);
	TElem pop();//sterge+return
	TElem top();//return
	bool empty();
};

