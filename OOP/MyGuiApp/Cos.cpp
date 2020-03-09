#include "Cos.h"

void Cos::appendCos(const Activity & act)
{
	list.push_back(act);
	notify();
}

void Cos::updateCos(const Activity & act)
{
	for (auto &activity : list) {
		if (activity == act) {
			activity = act;
		}
	}
}

void Cos::removeCos(const Activity & act)
{
	for (auto it = list.begin(); it != list.end(); ++it) {
		if (*it == act) {
			list.erase(it);
		}
	}
}

void Cos::clearCos()
{
	list.clear();
	notify();
}

vector<Activity>& Cos::getAll()
{
	return list;
}
