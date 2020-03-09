#include "Activity.h"

bool cmpTitle(const Activity& act, const Activity& oth) {
	return act.get_title() < oth.get_title();
}

bool cmpType(const Activity& act, const Activity& oth) {
	return act.get_type() < oth.get_type();
}

bool cmpTime(const Activity& act, const Activity& oth) {
	return act.get_time() < oth.get_time();
}

bool cmpDescript(const Activity& act, const Activity& oth) {
	return act.get_descript() < oth.get_descript();
}

ostream& operator<<(ostream& out, const Activity& act) {
	auto time = std::to_string(act.get_time());
	out << act.get_title() + ' ' + act.get_descript() + ' ' + act.get_type() + ' ' + time;
	return out;
}
