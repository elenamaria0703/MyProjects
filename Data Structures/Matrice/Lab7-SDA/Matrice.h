//Matrice.h
#pragma once
typedef int TElem;



#define NULL_TELEMENT 0
#include"IteratroMat.h"
struct el{
	int lin;
	int col;
	TElem val;
};

class IteratorMat;
class Matrice {
	friend class IteratorMat;
private:
	/* aici e reprezentarea */

	el* elems;
	int* stang;
	int* drept;
	int* parent;
	int nrL;
	int nrC;
	int primE;
	int urmLiber;

	el nul_el;
	int cauta_rec(int prim, int i,int j) const ;
	void adauga_rec(int prim, int i, int j, TElem e);
	int sterge_rec(int prim, int i, int j);
	el minim_val(int poz);
public:
	// constructor
	//arunca exceptie daca nrLinii<=0 sau nrColoane<=0
	Matrice(int nrLinii, int nrColoane);

	// returnare numar linii
	int nrLinii() const;

	// returnare numar coloane
	int nrColoane() const;

	// returnare element de pe o linie si o coloana
	//arunca exceptie daca (i,j) nu e pozitie valida in Matrice
	//indicii ii consideram de la 0
	TElem element(int i, int j) const;

	// modifica elementul de pe o linie si o coloana si returneaza vechea valoare
	//arunca exceptie daca (i,j) nu e o pozitie valida in Matrice
	TElem modifica(int i, int j, TElem e);

	IteratorMat iterator(int col) const;
	// destructor
	~Matrice();

};
