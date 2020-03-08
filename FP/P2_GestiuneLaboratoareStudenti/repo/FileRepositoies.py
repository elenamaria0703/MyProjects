from repo.Repository import RepositoryAsign,RepositoryProbls,RepositoryStudents
from model.Entities import Student, ProblLab, AsignNotaDTO

class FileRepositoryAsign(RepositoryAsign):

    def __init__(self,fileName):
        self.__filename = fileName
        self.__loadFromFile()
    
    def __loadFromFile(self):
        f = open(self.__filename,"r")
        RepositoryAsign.__init__(self)
        for line in f:
            if line.strip == "":
                return
            asignNota = self.__createAsignNota(line)
            RepositoryAsign.add(self,asignNota)
        f.close()
    
    def __createAsignNota(self,line):
        params = line.split(" ")
        idstud = params[0]
        nrProblLab = params[1]
        nota = params[2].split("\n")[0]
        asignnotaDTO = AsignNotaDTO(int(idstud),nrProblLab,None,None,nota)
        return asignnotaDTO
    
    def __appendToFile(self,asignNota):
        f = open(self.__filename,"a")
        line = str(asignNota)
        f.write(line)
        f.write('\n')
        f.close()
    
    def __writeAllToFile(self):
        f = open(self.__filename,'w')
        asignNote = RepositoryAsign.getAll(self)
        for asignNota in asignNote:
            self.__appendToFile(asignNota)
    
    def add(self,asignNota):
        idstud = asignNota.get_student().get_idstud()
        nrProblLab = asignNota.get_probl_lab().get_nr_lab_nr_probl()
        asignnota = AsignNotaDTO(idstud,nrProblLab,asignNota.get_student(),asignNota.get_probl_lab(),asignNota.get_nota())
        RepositoryAsign.add(self, asignnota)
        self.__writeAllToFile()
        
    def remove(self,asignNota):
        RepositoryAsign.remove(self, asignNota)
        self.__writeAllToFile()  
        
    def getAll(self):
        self.__loadFromFile()
        return RepositoryAsign.getAll(self)
    
class FileRepositoryProbls(RepositoryProbls):
    
    def __init__(self,fileName):
        self.__filename = fileName
        self.__loadFromFileProbls()
    
    
    def __loadFromFileProbls(self):
        f = open(self.__filename,'r')
        RepositoryProbls.__init__(self)
        for line in f:
            if line.strip == "":
                return
            problLab = self.__createProblLabFromLine(line)
            RepositoryProbls.add(self,problLab)
        f.close()
        
    def __createProblLabFromLine(self, line):
        params = line.split(" ")
        nrlab_nrprobl = params[0]
        descriere = params[1]
        deadline = params[2].split("\n")[0]
        problLab = ProblLab(nrlab_nrprobl,descriere,deadline)
        return problLab
    
    def __appendToFile(self,problLab):
        f = open(self.__filename,'a')
        line = str(problLab)
        f.write(line)
        f.write("\n")
        f.close()
        
    def __writeAllToFile(self):
        f = open(self.__filename,"w")
        problLabs = RepositoryProbls.getAll(self)
        for problLab in problLabs:
            self.__appendToFile(problLab)
    
    def add(self,problLab):
        RepositoryProbls.add(self,problLab)
        self.__writeAllToFile()
        
    def remove(self,problLab):
        RepositoryProbls.remove(self, problLab)
        self.__writeAllToFile()
        
    def update(self,problLab):
        RepositoryProbls.update(self, problLab)
        self.__writeAllToFile()
    
    def search(self,problLab):
        return RepositoryProbls.search(self,problLab)
        
    def getAll(self):
        self.__loadFromFileProbls()
        return RepositoryProbls.getAll(self)
    
    

class FileRepositoryStudents(RepositoryStudents):

    
    def __init__(self,fileName):
        self.__filename = fileName
        self.__loadFromFileStudents()

    def __loadFromFileStudents(self):
        f = open(self.__filename, 'r')
        RepositoryStudents.__init__(self)
        for line in f:
            if line.strip == "":
                return
            student = self.__createStudentFromLine(line)
            RepositoryStudents.add(self,student)
        f.close()
        
    def __createStudentFromLine(self,line):
        params = line.split(" ")
        idstud = params[0]
        nume = params[1]
        grupa = params[2].split("\n")[0]
        student = Student(int(idstud),nume,grupa)
        return student
    
    def __writeAllToFile(self):
        f = open(self.__filename,"w")
        students = RepositoryStudents.getAll(self)
        for student in students:
            self.__appendToFile(student)
        
    def __appendToFile(self,student):
        f = open(self.__filename,"a")
        line = str(student)
        f.write(line)
        f.write("\n")
        f.close()

    def add(self,student):
        RepositoryStudents.add(self,student)
        self.__writeAllToFile()
        
    def remove(self,student):
        students = RepositoryStudents.getAll(self)
        RepositoryStudents.removeRecursive(self,len(students)-1,student)
        self.__writeAllToFile()
            
    def update(self,student):
        RepositoryStudents.update(self, student)
        self.__writeAllToFile()
    
    def search(self,student):
        return RepositoryStudents.search(self, student)
        
    def getAll(self):
        self.__loadFromFileStudents()
        return RepositoryStudents.getAll(self)

