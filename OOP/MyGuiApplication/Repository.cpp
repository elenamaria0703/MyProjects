#include "Repository.h"
#include<fstream>

ostream & operator<<(ostream & out, const RepoException & ex)
{
	// TODO: insert return statement here
	out << ex.msg;
	return out;
}

bool MemoryRepository::exist(const Activity & act)
{
	for (const Activity& activity : activities) {
		if (activity == act) return true;
	}
	return false;
}

void MemoryRepository::store(const Activity & act)
{
	if (exist(act)) {
		throw RepoException("Activiatea exista!");
	}
	activities.push_back(act);
}

void MemoryRepository::remove(Activity & act)
{
	if (!exist(act)) {
		throw RepoException("Activitatea nu a fost gasita!");
	}
	std::vector<Activity>::iterator it;
	for (it = activities.begin(); it < activities.end(); it++)
	{
		Activity activity = *it;
		if (activity == act) {
			activities.erase(it);
			return;
		}
	}
}

void MemoryRepository::update(Activity & act)
{
	if (!exist(act)) {
		throw RepoException("Activitatea nu a fost gasita!");
	}
	for (Activity& activity : activities) {
		if (activity == act) {
			activity = act;
		}
	}
}

Activity MemoryRepository::search(const Activity& act)
{
	if (!exist(act)) {
		throw RepoException("Activitatea nu a fost gasita!");
	}
	for (Activity activity : activities) {
		if (activity == act) {
			return activity;
		}
	}
}

const vector<Activity>& MemoryRepository::getAll() const noexcept
{
	// TODO: insert return statement here
	return activities;
}



void FileRepository::loadFromFile()
{
	ifstream in(fileName);
	while (!in.eof())
	{
		string type, title, descript;
		double time;
		in >> title >> descript >> type >> time;
		if (in.eof()) break;
		Activity act{ title,descript,type, time };
		MemoryRepository::store(act);
	}
	in.close();
}

void FileRepository::writeToFile()
{
	ofstream out(fileName);
	for (auto& act : getAll()) {
		out << act << endl;
	}
	out.close();
}
