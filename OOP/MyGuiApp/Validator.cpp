#include "Validator.h"

ostream & operator<<(ostream & out, const ValidException & ex)
{
	// TODO: insert return statement here
	out << ex.msg;
	return out;
}

void Validator::validate(Activity & act)
{
	if (act.get_title() == "") throw ValidException("Titlu nevalid!");
	if (act.get_descript() == "") throw ValidException("Descriere nevalida!");
	if (act.get_type() == "") throw ValidException("Tip nevalid!");
	if (act.get_time() < 0) throw ValidException("Timp nevalid!");
}
