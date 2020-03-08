#pragma once
#include"Activity.h"
#include"Repository.h"
class UndoAction {
public:
	virtual void doUndo() = 0;
	virtual ~UndoAction() {};
};

class UndoAdd : public UndoAction {
	Activity actAdd;
	MemoryRepository& repo;
public:
	UndoAdd(MemoryRepository& repo, const Activity& act) :repo{ repo }, actAdd{ act }{};
	void doUndo() override {
		repo.remove(actAdd);
	}
};

class UndoRemove :public UndoAction {
	Activity actRemove;
	MemoryRepository& repo;
public:
	UndoRemove(MemoryRepository& repo, const Activity& act) :repo{ repo }, actRemove{ act }{};
	void doUndo() override {
		repo.store(actRemove);
	}
};

class UndoUpdate :public UndoAction {
	Activity actUpdate;
	MemoryRepository& repo;
public:
	UndoUpdate(MemoryRepository& repo, const Activity& act) :repo{ repo }, actUpdate{ act }{};
	void doUndo() override {
		repo.update(actUpdate);
	}
};