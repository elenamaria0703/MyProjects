#pragma once
#include "Activity.h"
#include "Service.h"

class UIConsole
{
	Service& serv;

	void addUI();
	void updateUI();
	void removeUI();
	void searchUI();

	void filterByType();
	void filterByDescript();

	void sortByTitle();
	void sortByDescript();
	void sortByTypeTime();

	void printActivities(const List<Activity>& activities);
public:
	UIConsole(Service& service) :serv{ service } {};
	UIConsole(const UIConsole& other) = delete;
	//~UIConsole();
	void start();
};