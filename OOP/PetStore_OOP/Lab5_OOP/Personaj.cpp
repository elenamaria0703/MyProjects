#include "Personaj.h"




Personaj::~Personaj()
{
}

const string& Personaj::get_nume() const{
	return this->nume;
}
//this pointeer la obiectul curent