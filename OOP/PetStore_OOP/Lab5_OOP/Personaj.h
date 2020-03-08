#pragma once
#include<string>
using namespace std;

class Personaj
{
private:
	int id;
	string nume;
	double var;

public:
	Personaj() :id{ -1 }, nume{ " " }, var{ -1 }{};
	const string& get_nume() const;
	Personaj(int _id, string _nume, double _var) :id{ _id }, nume{ _nume }, var{ _var }{};
	bool operator==(const Personaj& other) const {
		return this->id == other.id;
	}
	~Personaj();
};

