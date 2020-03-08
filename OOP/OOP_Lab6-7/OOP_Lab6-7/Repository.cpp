#include "Repository.h"

ostream & operator<<(ostream & out, const RepoException & ex)
{
	// TODO: insert return statement here
	out << ex.msg;
	return out;
}

bool Repository::exist(const Activity & act)
{
	for (const Activity& activity : activities) {
		if (activity == act) return true;
	}
	return false;
}

void Repository::store(const Activity & act)
{
	if (exist(act)) {
		throw RepoException("Activiatea exista!");
	}
	activities.push_back(act);
}

void Repository::remove(Activity & act)
{
	if (!exist(act)) {
		throw RepoException("Activitatea nu a fost gasita!");
	}
	for (auto it = activities.begin(); it != activities.end(); ++it)
	{
		if (*it == act) {
			activities.erase(it);
			return;
		}
	}
}

void Repository::update(Activity & act)
{
	if (!exist(act)) {
		throw RepoException("Activitatea nu a fost gasita!");
	}
	for (auto it = activities.begin(); it != activities.end(); ++it) {
		if (*it == act) {
			*it = act;
		}
	}
}

Activity Repository::search(const Activity& act)
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

const List<Activity>& Repository::getAll() const noexcept
{
	// TODO: insert return statement here
	return activities;
}
