#include "Validator.h"
#include "Tests.h"
#include"Repository.h"
#include"Service.h"
#include <QtWidgets/QApplication>
//#include "MyGui.h"
#include"MyGuiModelView.h"
int main(int argc, char *argv[])
{
	Tests test;
	test.AllTests();
	Validator valid;
	FileRepository repo{ "FileRepo.txt" };
	Cos list;
	Service service(repo, list, valid);
	QApplication app(argc, argv);
	MyGui wind{ service };
	wind.show();
	return app.exec();
}
