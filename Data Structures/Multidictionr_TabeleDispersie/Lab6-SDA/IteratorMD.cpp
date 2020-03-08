#include "IteratorMD.h"
#include "MD.h"


IteratorMD::IteratorMD(const MD& md) : md(md) {
	// initializare curent si alte atribute specifice
	pozCurenta = 0;
	curent = md.l[pozCurenta];
	while (curent == NULL) {
		pozCurenta++;
		curent = md.l[pozCurenta];
	}
	lg = 1;
}

TElem IteratorMD::element() { //theta(1)
	//TBA
	return curent->e;
}

bool IteratorMD::valid() { //theta(1)
	//TBA
	if (md.vid()) return false;
	return lg <= md.size;
}

void IteratorMD::urmator() {//O(m)
	//TBA
	if (md.vid()) return;
	curent = curent->urm;
	while (curent == NULL) {
		pozCurenta++;
		curent = md.l[pozCurenta];
	}
	lg++;
}

void IteratorMD::prim() {//O(m)
	//TBA
	pozCurenta = 0;
	curent = md.l[pozCurenta];
	while (curent == NULL) {
		pozCurenta++;
		curent = md.l[pozCurenta];
	}
	lg = 1;
}