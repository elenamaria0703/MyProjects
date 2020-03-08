#pragma once
#include<string>

class Pet
{
	int id;
	std::string type;
	std::string species;

public:
	Pet(int id, std::string type,std:: string specise) :id{ id }, type{ type }, species{ species }{};
	auto getType() { return type; };
	auto getId() { return id; };
	auto getSpecies() { return species; };
};

