#include "Matrice.h"
#include<exception>
using std::exception;
class myexception : public exception {
	virtual const char* what() const throw()
	{
		return what();
	}
} myexc;



Matrice::Matrice(int nrLinii, int nrColoane) :nrL{ nrLinii }, nrC{ nrColoane }{
	nul_el.val = NULL_TELEMENT;
	nul_el.col = -1;
	nul_el.lin = -1;
	urmLiber = 1;
	parent = new int[nrLinii*nrColoane];
	elems = new el[nrLinii*nrColoane];
	stang = new int[nrLinii*nrColoane];
	drept = new int[nrLinii*nrColoane];
	for (int i = 0; i < nrLinii*nrColoane; i++) {
		parent[i] = stang[i] = drept[i] = -1;
	}
	primE = -1;
}

int Matrice::cauta_rec(int prim, int i, int j) const {
	if (prim == -1)
		return -1;

	if (elems[prim].lin == i && elems[prim].col == j) {
		return prim;
	}
	else if (i < elems[prim].lin || i == elems[prim].lin && j < elems[prim].col) {
		cauta_rec(stang[prim], i, j);
	}
	else cauta_rec(drept[prim], i, j);
}

void Matrice::adauga_rec(int prim,int i, int j, TElem e)
{
	if (i < elems[prim].lin || i == elems[prim].lin && j < elems[prim].col) {
		if (stang[prim] == -1) {
			el elem;
			elem.val = e;
			elem.lin = i;
			elem.col = j;
			stang[prim] = urmLiber;
			parent[urmLiber] = prim;
			elems[urmLiber] = elem;
			this->urmLiber++;
		}
		else
			adauga_rec(stang[prim], i, j, e);
	}
	else {
		if (drept[prim] == -1) {
			el elem;
			elem.val = e;
			elem.lin = i;
			elem.col = j;
			drept[prim] = urmLiber;
			parent[urmLiber] = prim;
			elems[urmLiber] = elem;
			this->urmLiber++;
		}
		else
			adauga_rec(drept[prim], i, j, e);
	}
}

el Matrice::minim_val(int poz) {
	if (stang[poz] == -1) {
		return elems[poz];
	}
	return minim_val(stang[poz]);
}

int Matrice::sterge_rec(int prim, int i, int j)
{
	/*if (prim == -1) {
		return -1;
	}*/
	if (elems[prim].lin == i and elems[prim].col == j) {
		if (stang[prim] != -1 and drept[prim] != -1) {
			auto temp = minim_val(drept[prim]);
			elems[prim].val = temp.val;
			drept[prim] = sterge_rec(drept[prim], i, j);
			return prim;
		}
		else {
			auto temp = prim;
			int repl;
			if (stang[prim] == -1) {
				repl = drept[prim];
			}
			else {
				repl = stang[prim];
			}
			return repl;
		}
	}
	else if (i < elems[prim].lin || i == elems[prim].lin && j < elems[prim].col) {
		stang[prim] = sterge_rec(stang[prim], i, j);
		return prim;
	}
	else {
		drept[prim] = sterge_rec(drept[prim], i, j);
		return prim;
	}
}

int Matrice::nrLinii() const //theta(1)
{
	return nrL;
}

int Matrice::nrColoane() const //theta(1)
{
	return nrC;
}

TElem Matrice::element(int i, int j) const //O(n)
{
	if (i >= nrL || j >= nrC) throw myexc;
	int poz = cauta_rec(primE,i, j);
	if (poz == -1) {
		return NULL_TELEMENT;
	}
	return elems[poz].val;
}

TElem Matrice::modifica(int i, int j, TElem e)//O(n)
{
	TElem val;
	if (i >= nrL || j >= nrC) throw myexc;
	if (e != 0 && primE == -1) {
		primE = 0;
		elems[primE].val = e;
		elems[primE].lin = i;
		elems[primE].col = j;
		return NULL_TELEMENT;
	}
	if(e!=0){
		int poz = cauta_rec(primE, i, j);
		if (poz == -1) {
			adauga_rec(primE,i, j, e);
			return NULL_TELEMENT;
		}
		else {
			val = elems[poz].val;
			elems[poz].val = e;
			return val;
		}
		
	}
	if (e == 0) {
		int poz = cauta_rec(primE, i, j);
		if (poz == -1)
			return NULL_TELEMENT;
		return sterge_rec(primE, i, j);
	}

}
IteratorMat Matrice::iterator(int col) const
{
	return IteratorMat(*this,col);
}

Matrice::~Matrice()
{
}
