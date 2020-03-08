#pragma once
#include<utility>
typedef int TCheie;
typedef int TValoare;

typedef std::pair<TCheie, TValoare> TElem;

#include "Iterator.h"

#define NULL_TVALOARE -1

typedef bool(*Relatie)(TCheie, TCheie);

class DO {
	friend class Vector;
private:
	TElem* elems;
	int size;
	int capacity;
	void resize();
	Relatie Rel;
	/* aici e reprezentarea */
public:
	friend class Iterator;
	// constructorul implicit al dictionarului
	DO(Relatie Rel);
	DO() = default;


	//adauga o pereche(cheie, valoare) in dictionar
	//daca exista deja cheia in dictionar, inlocuieste valoarea asociata cheii si returneaza vechea valoare
	// daca nu exista cheia, adauga perechea si returneaza null: NULL_TVALOARE
	TValoare adauga(TCheie c, TValoare v);

	//cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null: NULL_TVALOARE
	TValoare cauta(TCheie c) const;


	//sterge o cheie si returneaza valoarea asociata (daca exista) sau null: NULL_TVALOARE
	TValoare sterge(TCheie c);

	//returneaza numarul de perechi (cheie, valoare) din dictionar 
	int dim() const;

	//verifica daca dictionarul e vid 
	bool vid() const;

	// se returneaza iterator pe dictionar
	// iteratorul va returna perechile in ordine dupa relatia de ordine (pe cheie)
	Iterator iterator() const;


	// destructorul dictionarului	
	~DO();

	void adaugaSfarsit(TElem el);
	void inserare(int i, TElem el);
	void stergeEl(int i);
};
