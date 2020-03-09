#pragma once
#include "Activity.h"

class ValidException {
	string msg;
public:
	ValidException(string msg) :msg{ msg } {};
	friend ostream & operator<<(ostream & out, const ValidException& ex);
	string getmsg() {
		return msg;
	}
};

class Validator {
public:
	void validate(Activity& act);
};