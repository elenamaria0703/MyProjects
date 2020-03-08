#pragma once
template <typename TElem>
class Vector
{
	class iterator {
	private:
		Vector<TElem>& v;
		int pos;

	public:
		iterator(Vector<TElem>& _v, int _pos) : v{ _v }, pos{ _pos }{};
		TElem element() {
			return v.elems[pos];
		}
		void urmator() {
			pos++;
		}
		~iterator() {}
	};
private:
	TElem* elems;
	int size;
	int capac;
	void resize();

public:
	Vector() :size{ 0 }, capac{ 2 }, elems{ new TElem[2] }{};
	void adauga(const TElem& el);
	Vector<TElem>::iterator begin();
	Vector<TElem>::iterator end();
	TElem& operator*() {
		return element();
	}
	Vector<TElem>::iterator& operator++() {
		urmator();
		return *this;
	}
	~Vector() {
		delete[] this->elems;
	}
};

template <typename TElem>
inline void Vector<TElem>::resize() {
	TElem* n_elems = new TElem[this->capac * 2];
	for (int i = 0; i < this->size; i++) {
		n_elems[i] = this->elems[i];
	}
	delete[] this->elems;
	this->elems = n_elems;
	this->capac *= 2;
}

template <typename TElem>
inline void Vector<TElem>::adauga(const TElem& el) {
	if (this->size == this->capac) {
		this->resize();
	}
	this->elems[this->size] = el;
	this->size++;
}

template <typename TElem>
inline typename Vector<TElem>::iterator Vector<TElem> ::begin() {
	return Vector<TElem>::iterator(*this, 0);
}

template <typename TElem>
inline typename Vector<TElem>::iterator Vector<TElem> ::end() {
	return Vector<TElem>::iterator(*this, size);
}


