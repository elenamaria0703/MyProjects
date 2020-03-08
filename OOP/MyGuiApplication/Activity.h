#pragma once
#include<string>
using std::string;
using namespace std;

class Activity
{
	string title;
	string descript;
	string type;
	double time;

public:
	Activity(const string t, const string d, const string ty, const double ti) :title{ t }, descript{ d }, type{ ty }, time{ ti }{};
	Activity() = default;
	string  get_title() const {
		return title;
	}

	string get_descript() const {
		return descript;
	}

	string get_type() const {
		return  type;
	}

	double get_time() const {
		return time;
	}

	bool operator==(const Activity& other) const {
		return title == other.title;
	}
	friend ostream& operator<<(ostream& out, const Activity& act);
};

ostream& operator<<(ostream& out, const Activity& act);
bool cmpTitle(const Activity& act, const Activity& oth);
bool cmpType(const Activity& act, const Activity& oth);
bool cmpTime(const Activity& act, const Activity& oth);
bool cmpDescript(const Activity& act, const Activity& oth);