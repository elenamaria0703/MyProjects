/*S? se implementeze în C++ un anumit container de date (TAD) folosind o anumit?
reprezentare (indicat?) ?i o tabel? de dispersie (TD) ca structur? de date, cu o anumit? metod?
(indicat?) pentru rezolvarea coliziunilor:
? Liste independente
? Liste întrep?trunse
? Adresare deschis? 

19. TAD MultiDic?ionar – memorarea tuturor perechilor de forma (cheie, valoare), reprezentare
sub forma unei TD cu rezolvare coliziuni prin liste independente.
*/

#include"Teste.h"
#include"MD.h"
#include<assert.h>
int main() {
	testAllExtins();
	MD md;
	TCheie c1 = md.cheieMaxima();
	assert(c1 == NULL_TCHEIE);
	md.adauga(23, 41);
	md.adauga(34, 45);
	md.adauga(20, 67);
	TCheie c2 = md.cheieMaxima();
	assert(c2 == 34);
	md.sterge(34, 45);
	TCheie c3 = md.cheieMaxima();
	assert(c3 == 23);
	return 0;
}