#pragma once
#include<vector>
#include<algorithm>
using std::vector;
class Observer {
public:
	virtual void update() = 0;
};

class Observable {
	vector<Observer*> obs;
public:
	void addObserver(Observer* o) {
		obs.push_back(o);
	}
	void removeObserver(Observer* o) {
		obs.erase(std::find(begin(obs),end(obs),o));
	}
protected:
	void notify() {
		for (auto o : obs) {
			o->update();
		}
	}
};