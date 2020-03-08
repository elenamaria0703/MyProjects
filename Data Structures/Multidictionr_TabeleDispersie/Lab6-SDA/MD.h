#pragma once
#define NULL_TCHEIE 0

#include<utility>
#include<vector>
typedef int TCheie;
typedef int TValoare;
using std::vector;

typedef std::pair<TCheie, TValoare> TElem;
struct Nod {
	TElem e;
	Nod* urm;
};

#include"IteratorMD.h"
class IteratorMD;

class MD {
	friend class IteratorMD;
private:
	/* aici e reprezentarea */
	int m;
	Nod** l;
	int Hashing(TCheie ch) const{
		return abs(ch) % m;
	}
	int size;

	Nod* alocaNod(TElem e) {
		Nod* nod = new Nod;
		nod->e = e;
		nod->urm = NULL;
		return nod;
	}
public:

	// constructorul implicit al MultiDictionarului
	MD();

	// adauga o pereche (cheie, valoare) in MD	
	void adauga(TCheie c, TValoare v);

	//cauta o cheie si returneaza vectorul de valori asociate
	vector<TValoare> cauta(TCheie c) const;

	//sterge o cheie si o valoare 
	//returneaza adevarat daca s-a gasit cheia si valoarea de sters
	bool sterge(TCheie c, TValoare v);

	//returneaza numarul de perechi (cheie, valoare) din MD 
	int dim() const;

	//verifica daca MultiDictionarul e vid 
	bool vid() const;

	// se returneaza iterator pe MD
	IteratorMD iterator() const;

	//gaseste si returneaza cheia maxima a multidictionarului
	//daca multidictionarul este vid, se returneaza NULL_TCHEIE

	TCheie cheieMaxima() const;
	// destructorul MultiDictionarului	
	~MD();



};