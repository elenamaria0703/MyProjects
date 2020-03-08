from errors.Errors import RepoErrors


class RepositoryStudents(object):
    
    
    def __init__(self):
        self.__elems = []
    
    def __len__(self):
        return len(self.__elems)
    
    def add(self,elem):
        if elem in self.__elems:
            raise RepoErrors("Student existent!")
        self.__elems.append(elem)
        
    def remove(self,elem):
        ok=0
        for i in range(len(self.__elems)):
            if self.__elems[i] == elem:
                ok=1
        if ok == 1:
            for j in range(len(self.__elems)):
                if self.__elems[j] == elem:
                    del self.__elems[j]
                    return
        else:
            raise RepoErrors("Student inexistent!") 
        
    def removeRecursive(self,len_elems,elem):
        if not len_elems:
            raise RepoErrors("Student inexistent!")
        elif self.__elems[len_elems] == elem:
            del self.__elems[len_elems]
            return
        else:
            self.removeRecursive(len_elems-1,elem)
             
    def search(self,elem):
        if elem not in self.__elems:
            raise RepoErrors("Student inexistent!")
        for i in range(len(self.__elems)):
            if self.__elems[i] == elem:
                return self.__elems[i]
            
    def searchRecursive(self,len_elems,elem):
        if not len_elems:
            raise RepoErrors("Student inexistent!")
        elif self.__elems[len_elems] == elem:
            return self.__elems[len_elems]
        else:
            self.searchRecursive(len_elems-1,elem)
            
    def searchByName(self,key,func):
        rez = []
        for elem in self.__elems:
            if(func(elem,key)):
                rez.append(elem)
        return rez
    
    def update(self,elem):
        if elem not in self.__elems:
            raise RepoErrors("Student inexistent!")
        for i in range(len(self.__elems)):
            if self.__elems[i] == elem:
                self.__elems[i] = elem
                return
    def getAll(self):
        return self.__elems[:]


class RepositoryAsign(object):
    
    def __init__(self):
        self.__elems = []
    
    def __len__(self):
        return len(self.__elems)
    
    def add(self,elem):
        if elem in self.__elems:
            raise RepoErrors("Problema existenta!")
        self.__elems.append(elem)
        
    def remove(self,elem):
        ok=0
        for i in range(len(self.__elems)):
            if self.__elems[i] == elem:
                ok=1
        if ok == 1:
            for j in range(len(self.__elems)):
                if self.__elems[j] == elem:
                    del self.__elems[j]
                    return
        else:
            raise RepoErrors("Problema inexistenta!")      
    def search(self,elem):
        if elem not in self.__elems:
            raise RepoErrors("Problema inexistenta!")
        for i in range(len(self.__elems)):
            if self.__elems[i] == elem:
                return self.__elems[i]

    def update(self,elem):
        if elem not in self.__elems:
            raise RepoErrors("Problema inexistenta!")
        for i in range(len(self.__elems)):
            if self.__elems[i] == elem:
                self.__elems[i] = elem
                return
    def getAll(self):
        return self.__elems[:]



class RepositoryProbls(object):
    
    def __init__(self):
        self.__elems = []
    
    def __len__(self):
        return len(self.__elems)
    
    def add(self,elem):
        if elem in self.__elems:
            raise RepoErrors("Element existent!")
        self.__elems.append(elem)
        
    def remove(self,elem):
        ok=0
        for i in range(len(self.__elems)):
            if self.__elems[i] == elem:
                ok=1
        if ok == 1:
            for j in range(len(self.__elems)):
                if self.__elems[j] == elem:
                    del self.__elems[j]
                    return
        else:
            raise RepoErrors("Element inexistent!")  
            
    def search(self,elem):
        if elem not in self.__elems:
            raise RepoErrors("Element inexistent!")
        for i in range(len(self.__elems)):
            if self.__elems[i] == elem:
                return self.__elems[i]
        
    
    def update(self,elem):
        if elem not in self.__elems:
            raise RepoErrors("Element inexistent!")
        for i in range(len(self.__elems)):
            if self.__elems[i] == elem:
                self.__elems[i] = elem
                return
    def getAll(self):
        return self.__elems[:]



