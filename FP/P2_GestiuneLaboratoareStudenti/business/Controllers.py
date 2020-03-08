from model.Entities import Student,ProblLab,AsignProblNota,StudentByProblDTO,StudentByAverageDTO
from _ast import Compare


class ProblLabService(object):
    
    
    def __init__(self, __repoProblLab, __validatorProblLab):
        self.__repoProblLab = __repoProblLab
        self.__validatorProblLab = __validatorProblLab
        
    def AddProblLab(self,nrLab_nrProbl,descriere,deadline):
        problLab = ProblLab(nrLab_nrProbl,descriere,deadline)
        self.__validatorProblLab.ValidateProblLab(problLab)
        self.__repoProblLab.add(problLab)
    def RemoveProblLab(self,nrLab_nrProbl):
        problLab = ProblLab(nrLab_nrProbl,None,None)
        self.__repoProblLab.remove(problLab) 
    def UpdateProblLab(self,nrLab_nrProbl,descriere,deadline):
        problLab = ProblLab(nrLab_nrProbl,descriere,deadline)
        self.__validatorProblLab.ValidateProblLab(problLab)
        self.__repoProblLab.update(problLab)
    def SearchProblLab(self,nrLab_nrProbl):
        problLab = ProblLab(nrLab_nrProbl,None,None)
        return self.__repoProblLab.search(problLab)
    def getAllProblLab(self):
        return self.__repoProblLab.getAll()
    def RandomProblLab(self):
        import random
        nrlab = ["2_4","3_4","6_7"]
        des = ["bio","chimie","fizica"]
        deadl = ["12.02.2019","23.12.2018","12.12.2018"]
        for i in range(3):
            problLab = random.choice(nrlab)
            descriere = random.choice(des)
            deadline = random.choice(deadl)
            self.AddProblLab(problLab,descriere,deadline)
 



class StudentiService(object):
    
    
    def __init__(self, __repoStudenti, __validatorStudenti):
        self.__repoStudenti = __repoStudenti
        self.__validatorStudenti = __validatorStudenti
        
        
    def AddStudent(self,idstud,nume,grupa):
        student = Student(idstud,nume,grupa)
        self.__validatorStudenti.ValidateStudent(student)
        self.__repoStudenti.add(student)
        
    def getAllStudents(self):
        return self.__repoStudenti.getAll()
    
    def UpdateStudent(self,idstud,nume,grupa):
        student = Student(idstud,nume,grupa)
        self.__validatorStudenti.ValidateStudent(student)
        self.__repoStudenti.update(student)
        
    def SearchStudent(self,nume):
        return self.__repoStudenti.searchByName(nume,self.matchStudent)
    
    def matchStudent(self,student,key):
        return key in student.get_nume()
    
    def getRandomString(self):
        import random
        nume = ["sofia","andrei","sebi", "serban","ionut","franci","lau","andra","gabi"]
        s = random.choice(nume)
        return s
    def RandomStudent(self):
        import random
        for i in range(10):
            idstud = random.randint(1,100) 
            nume = self.getRandomString()
            grupa = random.randint(100,500)
            self.AddStudent(idstud,nume,grupa)
    
    def partition(self,lst, start, end, cmp):
        pos = start                           
        for i in range(start, end):           
            if cmp(lst[i], lst[end]):
                lst[i],lst[pos] = lst[pos],lst[i]
                pos += 1
        lst[pos],lst[end] = lst[end],lst[pos]
        return pos
    
    def quick_sort(self, lst, start, end):
        if start < end:
            pos = self.partition(lst, start, end, self.compare)
            self.quick_sort(lst, start, pos - 1)
            self.quick_sort(lst, pos + 1, end)
    
    def compare(self,obj1,obj2):
        return obj1.get_nume() < obj2.get_nume()
    
    def gnomeSort(self,arr,n): 
        index = 0
        while index < n: 
            if index == 0: 
                index = index + 1
            if self.compare1(arr[index],arr[index - 1]): #>=
                index = index + 1
            else: 
                arr[index], arr[index-1] = arr[index-1], arr[index] 
                index = index - 1
        return arr 
    
    def compare1(self,obj1,obj2):
        return obj1.get_nume() >= obj2.get_nume()


class AsignareService(object):
    
    
    def __init__(self, __repoStudenti, __repoProblLab, __repoAsignare, __validatorAsignare):
        self.__repoStudenti = __repoStudenti
        self.__repoProblLab = __repoProblLab
        self.__repoAsignare = __repoAsignare
        self.__validatorAsignare = __validatorAsignare
    
    def SetStudentProblNota(self,idstud,problLab,nota):
        createStudent = Student(idstud,None,None)
        student = self.__repoStudenti.search(createStudent)
        createProblLab =  ProblLab(problLab,None,None)
        problLab = self.__repoProblLab.search(createProblLab)
        StudentProbl = AsignProblNota(student,problLab,nota)
        self.__repoAsignare.add(StudentProbl)
        
    def RemoveStudent(self,idstud):
        student =  Student(idstud,None,None)
        #studentProbl = AsignProblNota(student,None,None)
        StudentsProbl = self.getAllStudentLabs()
        for StudentProbl in StudentsProbl:
            if StudentProbl == idstud:
                self.__repoAsignare.remove(StudentProbl)
        self.__repoStudenti.remove(student)
        
    '''def getAllStudentLabs(self):
        return self.__repoAsignare.getAll()'''
        
    def getAllStudentLabs(self):
        asignNote = self.__repoAsignare.getAll()
        for asignNota in asignNote:
            asignNota.set_student(self.__repoStudenti.search(asignNota.get_idstud()))
            asignNota.set_problLab(self.__repoProblLab.search(asignNota.get_nrProbLab()))
        return asignNote
    
    def partition(self,lst, start, end, cmp):
        pos = start                           
        for i in range(start, end):           
            if cmp(lst[i], lst[end]):
                lst[i],lst[pos] = lst[pos],lst[i]
                pos += 1
        lst[pos],lst[end] = lst[end],lst[pos]
        return pos
    
    def quick_sort(self, lst, start, end):
        if start < end:
            pos = self.partition(lst, start, end, self.compare)
            self.quick_sort(lst, start, pos - 1)
            self.quick_sort(lst, pos + 1, end)
    
    def compare(self,obj1,obj2):
        return obj1.get_name() < obj2.get_name()
    
    def gnomeSort(self,arr,n,cmp): 
        index = 0
        while index < n: 
            if index == 0: 
                index = index + 1
            if cmp(arr[index],arr[index - 1]): #>=
                index = index + 1
            else: 
                arr[index], arr[index-1] = arr[index-1], arr[index] 
                index = index - 1
        return arr 
    
    def getStudentByProbl(self,probl):
        students = {}
        problLab = ProblLab(probl,None,None)
        studentsProbl = self.getAllStudentLabs()
        for student in studentsProbl:
            idstud = student.get_student().get_idstud()
            name = student.get_student().get_nume()
            nota = student.get_nota()
            if student.get_problLab() == problLab:
                students[idstud] = [name,nota,probl]
                
        getStudents = []
        for stud in students:
            student = students[stud]
            getStudents.append(StudentByProblDTO(student[0],student[1],student[2]))
        self.quick_sort(getStudents,0,len(getStudents)-1)
        #self.gnomeSort(getStudents,len(getStudents)-1,self.compare)
        #getStudents.sort(key = lambda x : x.get_name())
        return getStudents   

    def getStudentByAverage(self):
        students = {}
        studentsProbl = self.getAllStudentLabs()
        for student in studentsProbl:
            idstudent = student.get_student().get_idstud()
            if idstudent not in students:
                students[idstudent]=[student.get_student().get_nume(),1,student.get_nota()]
            else:
                students[idstudent][1]+=1
                students[idstudent][2]+=student.get_nota()
        getStudents = []
        for stud in students:
            student = students[stud]
            studentAvg = StudentByAverageDTO(student[0],student[2]/student[1])
            if studentAvg.get_average() < 5:
                getStudents.append(studentAvg)
        return getStudents
    
    def getStudentByProp(self):
        allProp = {}
        students = []
        AllProbl = self.__repoProblLab.getAll()
        nrProbl = len(AllProbl)
        studentsProbl = self.getAllStudentLabs()
        AllStudents = self.__repoStudenti.getAll()
        nrStudents = len(AllStudents)
        for student in AllStudents:
            studentExist = 0
            for i in range(len(studentsProbl)):
                if studentsProbl[i].get_student() == student:
                    studentExist = 1
            if studentExist == 0:
                students.append(student)
        allProp[1] = students
        allProp[2] = nrStudents
        allProp[3] = nrProbl
        return allProp   
            
