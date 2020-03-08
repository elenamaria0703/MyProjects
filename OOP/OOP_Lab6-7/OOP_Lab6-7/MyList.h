#pragma once
template <typename Element>
class Nod {
public:
	Element val;
	Nod* urm;
	Nod(const Element& el) :val{ el }, urm{ nullptr }{};
	Nod() = default;
	Nod(const Element& el, Nod* urm) :val{ el }, urm{ urm }{};

};

template<typename Element>
class IteratorList;

template <typename Element>
class List {
private:
	Nod<Element>* prim;
	Nod<Element>* copie(Nod<Element>* nod);
	void dealoca();

public:
	List();
	List(const List& ot);
	~List();
	List& operator=(const List& ot);

	int size() const;
	Element& get(int poz) const;
	void push_back(const Element& el);
	void erase(const IteratorList<Element>& it);

	friend class IteratorList<Element>;
	IteratorList<Element> begin();
	IteratorList<Element> end();
};

template<typename Element>
Nod<Element>* List <Element>::copie(Nod<Element>* nod) {
	if (nod == nullptr) return nullptr;
	return new Nod<Element>{ nod->val,copie(nod->urm) };
}

template<typename Element>
void List<Element>::dealoca() {
	Nod<Element>* curent = prim;
	while (curent != nullptr) {
		auto aux = curent;
		curent = curent->urm;
		delete aux;
	}
}

template<typename Element>
List<Element>::List() :prim{ nullptr } {};

template<typename Element>
List<Element>::List(const List<Element>& ot) {
	prim = copie(ot.prim);
}

template<typename Element>
List<Element>::~List() {
	dealoca();
}

template <typename Element>
List<Element>& List<Element>::operator=(const List<Element>& ot) {
	if (this == &ot) {
		return *this;
	}
	dealoca();
	prim = copie(ot.prim);
	return *this;
}

template <typename Element>
void List<Element>::push_back(const Element& el) {
	Nod<Element>* curent = prim;
	if (curent == nullptr) {
		Nod<Element>* nod = new Nod<Element>(el);
		prim = nod;
		return;
	}
	while (curent->urm != nullptr) {
		curent = curent->urm;
	}
	Nod<Element>* nod = new Nod<Element>(el);
	curent->urm = nod;
}

template <typename Element>
void List<Element>::erase(const IteratorList<Element>& it) {
	int poz = it.poz;
	if (poz == 0 && prim->urm!=nullptr) {
		auto aux = prim;
		prim = prim->urm;
		delete aux;
	}
	else if (poz == 0 && prim->urm == nullptr) {
		auto aux = prim;
		prim = nullptr;
		delete aux;
	}
	else {
		Nod<Element>* curent = prim;
		int p = 0;
		while (curent->urm != nullptr && poz-1 != p) {
			curent = curent->urm;
			p++;
		}
		auto aux = curent->urm;
		curent->urm = aux->urm;
		delete aux;
	}
}
template <typename Element>
int List<Element>::size() const {
	int lg = 0;
	if (prim == nullptr) return 0;
	Nod<Element>* curent = prim;
	while (curent != nullptr) {
		curent = curent->urm;
		lg++;
	}
	return lg;
}

template<typename Element>
Element& List<Element>::get(int poz) const {
	Nod<Element>* curent = prim;
	int p = 0;
	while (curent->urm != nullptr && poz != p) {
		curent = curent->urm;
		p++;
	}
	return curent->val;
}
template<typename Element>
IteratorList<Element> List<Element>::begin() {
	return IteratorList<Element>(*this);
}
template<typename Element>
IteratorList<Element> List<Element>::end() {
	return IteratorList<Element>(*this, size());
}

template<typename Element>
class IteratorList {
public:
	const List<Element>& l;
	int poz = 0;

	IteratorList(const List<Element>& l);
	IteratorList(const List<Element>& l, int poz);
	bool valid() const;
	void next();
	Element& element() const;
	Element& operator*();
	IteratorList& operator++();
	bool operator==(const IteratorList& ot) noexcept;
	bool operator!=(const IteratorList& ot)noexcept;
};

template<typename Element>
IteratorList<Element>::IteratorList(const List<Element>& l) :l{ l } {};
template<typename Element>
IteratorList<Element>::IteratorList(const List<Element>& l,int poz) :l{ l }, poz{ poz }{};

template<typename Element>
bool IteratorList<Element>::valid() const {
	return poz < l.size();
}

template <typename Element>
void IteratorList<Element>::next() {
	poz++;
}

template<typename Element>
Element& IteratorList<Element>::element() const {
	return l.get(poz);
}

template<typename Element>
Element& IteratorList<Element>::operator*() {
	return element();
}
template<typename Element>
IteratorList<Element>& IteratorList<Element>::operator++() {
	next();
	return *this;
}
template<typename Element>
bool IteratorList<Element>::operator==(const IteratorList<Element>& ot) noexcept {
	return poz == ot.poz;
}

template<typename Element>
bool IteratorList<Element>::operator!=(const IteratorList<Element>& ot)noexcept {
	return valid();
}



