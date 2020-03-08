#pragma once
#include <vector>
#include <iostream>
#include<exception>
#include <assert.h>
#include <numeric>
#include <functional>
#include <algorithm>
#include "Heap.h"

using namespace std;

typedef int TElem;



//In cazul in care k<0, se arunca exceptie, iar in cazul in care k>nr (dimensiunea vectorului), se va returna produsul tuturor celor nr elemente
//n>0 este numarul de descendenti pentru ansamblu
long calculeazaProdus(vector<TElem> vector, int k, int n) {//O(knlog(m))
	Heap h{ n };
	if (k < 0) throw exception();
	for (auto x : vector) {
		h.push(x);
	}
	long long produs = 1;
	for (int i = 0; !h.empty() && i < k; i++) {
		produs *= h.pop();
	}
	return produs;
}

// k elemente intregi, in ordine aletorie, din intervalul [min, max]
vector<int> subintervalOrdineAleatorie(int min, int max, int k, int n) {
	vector<int> valori;
	for (int v = min; v <= max; v++) {
		valori.push_back(v);
	}
	int dim = valori.size();
	for (int i = 0; i < dim - 1; i++) {
		int j = i + rand() % (dim - i);
		swap(valori[i], valori[j]);
	}
	for (int i = valori.size() - 1; i >= k; i--) {
		valori.pop_back();
	}
	return valori;
}


void testException(int n) {
	vector<int> v;
	v.push_back(1);
	v.push_back(2);
	v.push_back(3);
	v.push_back(4);
	v.push_back(5);
	vector<int> kNegativ = subintervalOrdineAleatorie(-50, -1, 10, n);
	for (int i = 0; i < (int)kNegativ.size(); i++) {
		try {
			calculeazaProdus(v, kNegativ[i], n);
			assert(false);
		}
		catch (exception& ex) {
			assert(true);
		}
	}
}

void testProdus(int k, int n) {
	vector<int> v;

	int min1 = -500;
	int max1 = 500;

	int min2 = max1 + 1;
	int max2 = min2 + k - 1;

	vector<int> valoriI1 = subintervalOrdineAleatorie(min1, max1, max1 - min1 + 1, n);
	for (int i = 0; i < (int)valoriI1.size() / 2; i++) {
		v.push_back(valoriI1[i]);
	}

	vector<int> valoriI2 = subintervalOrdineAleatorie(min2, max2, k, n);
	for (int i = 0; i < k; i++) {
		v.push_back(valoriI2[i]);
	}

	for (int i = valoriI1.size() / 2; i < (int)valoriI1.size(); i++) {
		v.push_back(valoriI1[i]);
	}

	long produs = 1;
	for (int i = 0; i < valoriI2.size(); i++) {
		produs *= valoriI2[i];
	}

	try {
		assert(calculeazaProdus(v, k, n) == produs);
	}
	catch (exception& e) {
		assert(false);
	}
}

void testProdusToate(int n) {
	int min = -500;
	int max = 500;
	int kMin = 100;
	vector<int> v = subintervalOrdineAleatorie(min, max, kMin, n);
	long suma = std::accumulate(v.begin(), v.end(), 1, std::multiplies<int>());
	for (int k = kMin; k <= kMin + 100; k++) {
		try {
			assert(calculeazaProdus(v, k, n) == suma);
		}
		catch (exception& e) {
			assert(false);
		}
	}

}



void testProblema14() {
	for (int n = 3; n <= 5; n++) {
		testException(n);
		testProdus(10, n);
		testProdus(20, n);
		testProdus(100, n);
		testProdus(1000, n);
		testProdusToate(n);
	}
}

int main() {

	testProblema14();
	
	return 0;
}