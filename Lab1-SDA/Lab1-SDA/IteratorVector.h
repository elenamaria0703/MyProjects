#pragma once
#include "Vector.h"
template<typename TElement>

class IteratorVector
{
	friend class Vector;
private:
	const Vector& v;
	int poz = 0;
public:
	IteratorVector(const Vector& v) :v{ v } {};

	bool valid() const {
		return poz < v.dim();
	}
	TElement& element() const {
		return v.elems[poz];
	}
	void next() {
		poz++;
	}
	~IteratorVector();
};
