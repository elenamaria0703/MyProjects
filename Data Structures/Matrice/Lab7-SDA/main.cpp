/*Să se implementeze în C++ un anumit container de date (TAD) folosind o anumită
reprezentare (indicată) și un arbore binar de căutare (ABC) ca structură de date. 

TAD Matrice - reprezentare sub forma unei matrice rare (triplete de forma <linie, coloană,
valoare> (valoare0)), memorate folosind un ABC reprezentat înlănțuit, cu înlănțuiri
reprezentate pe tablou
*/
#include"Teste.h"
#include"Matrice.h"
#include<assert.h>

int main() {
	//testAllExtins();
	Matrice m{ 2,2 };
	IteratorMat it = m.iterator(1);
	TElem e = it.element();
	assert(e == 0);
	m.modifica(1, 1, 2);
	it.urmator();
	TElem e1 = it.element();
	assert(e1 == 2);
	it.anterior();
	TElem e2 = it.element();
	assert(e2 == 0);
	return 0;
}