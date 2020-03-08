from errors.Errors import ValidErrors,RepoErrors

class Console(object):
    
    
    def __init__(self, __serviceStudenti, __serviceProblLab,__serviceAsignare):
        self.__serviceStudenti = __serviceStudenti
        self.__serviceProblLab = __serviceProblLab
        self.__serviceAsignare = __serviceAsignare

    def __getIstructions(self):
        print(" addStudent-adauga un student\n addProblLab-adauge o problmea de forma nrLab_nrProbl\n printStudents-printeaza lista studentilor\n printProblLab-printeaza list laboratoarelor\n removeStudent-sterge un student\n removeProblLab-sterge problema si laboratorul\n updateStudent - modifica studentul de la Id ul dat cu parametri curenti\n updateProblLab - modifica NrLab_NrProbl dat cu parametri curenti\n searchStudent - cauta un student dupa ID\n searchProblLab - cauta ProblLab dupa NrLab_NrProbl\n asignLabNota - asignare laborator\n getStudNotaByProbl - studenti in ordine alfabetica care au problema data\n getStudByAverage - studentii care au media notelor sub 5")
        
    def __getUiInput(self, cmd):
        commandsInput = {"addStudent":["Dati ID-ul:","Dati numele: ","Dati grupa:"],
                         "addProblLab":["Dati nrLab_nrProbl:","Dati descriere: ","Dati deadline:"],
                         "updateStudent":["Dati ID-ul:","Dati numele: ","Dati grupa:"],
                         "updateProblLab":["Dati nrLab_nrProbl:","Dati descriere: ","Dati deadline:"],
                         "asignLabNota":["Dati ID-ul:","Dati ProblLab: ","Dati nota:"]}
        
        commandsInput1 = {"removeStudent":"Dati ID-ul:",
                          "removeProblLab":"Dati nrLab_nrProbl:",
                          "searchStudent":"Dati numele:",
                          "searchProblLab":"Dati nrLab_nrProbl:",
                          "getStudNotaByProbl":"Dati nrLab_nrProbl:" }
        params=[]
        if cmd in commandsInput:
            for i in range(3):
                params.append(input(commandsInput[cmd][i]))
        elif cmd in commandsInput1:
            params.append(input(commandsInput1[cmd]))
        return params
    

    def __uiAddStudent(self,params):
        idstud = int(params[0])
        nume = params[1]
        grupa = int(params[2])
        self.__serviceStudenti.AddStudent(idstud,nume,grupa)
    

    def __uiAddProblLab(self,params):
        nrLab_nrProbl = params[0]
        descriere = params[1]
        deadline = params[2]
        self.__serviceProblLab.AddProblLab(nrLab_nrProbl,descriere,deadline)
    
    
    def __uiprintStudent(self,params):
        studenti = self.__serviceStudenti.getAllStudents()
        if len(studenti) == 0:
            print("Lista este goala!")
        s=""
        for student in studenti:
            s+=str(student)+"\n"
        print(s)
    
    
    def __uiprintProblLab(self,params):
        problems = self.__serviceProblLab.getAllProblLab()
        if len(problems) == 0:
            print("Lista este goala!")
        s=""
        for problem in problems:
            s+=str(problem)+"\n"
        print(s)
    
    
    def __uiremoveStudent(self,params):
        idstud = int(params[0])
        self.__serviceAsignare.RemoveStudent(idstud)
    
    
    def __uiremoveProblLab(self,params):
        nrLab_nrProbl = params[0]
        self.__serviceProblLab.RemoveProblLab(nrLab_nrProbl)
    
    
    def __uiupdateProblLab(self,params):
        nrLab_nrProbl = params[0]
        descriere = params[1]
        deadline = params[2]
        self.__serviceProblLab.UpdateProblLab(nrLab_nrProbl,descriere,deadline)
    
    
    def __uiupdateStudent(self,params):
        idstud = int(params[0])
        nume = params[1]
        grupa = int(params[2])
        self.__serviceStudenti.UpdateStudent(idstud,nume,grupa)
    
    
    def __uisearchProblLab(self,params):
        nrLab_nrProbl = params[0]
        ProblLab = self.__serviceProblLab.SearchProblLab(nrLab_nrProbl)
        print(str(ProblLab))
    
    
    def __uisearchStudent(self,params):
        nume = params[0]
        students = self.__serviceStudenti.SearchStudent(nume)
        if len(students) < 1:
            raise RepoErrors("Student inexistent!")
        s=""
        for student in students:
            s+=str(student)+"\n"
        print(s)
    
    
    def __uiasignLabNota(self,params):
        idstud = int(params[0])
        problLab = params[1]
        nota = int(params[2])
        self.__serviceAsignare.SetStudentProblNota(idstud,problLab,nota)
 
    def __uiprintasignLab(self,params):
        studentLabs = self.__serviceAsignare.getAllStudentLabs()
        if len(studentLabs) == 0:
            print("Lista este goala!")
        s = ""
        for studentLab in studentLabs:
            s+=str(studentLab)+"\n"
        print(s)
    
    
    def __getStudNotaByProbl(self,params):
        probl = params[0]
        students = self.__serviceAsignare.getStudentByProbl(probl)
        for student in students:
            print(student)
    
    
    def __getStudByAverage(self,params):
        students = self.__serviceAsignare.getStudentByAverage()
        for student in students:
            print(student)
    
    
    def __getStudByProp(self,params):
        props = self.__serviceAsignare.getStudentByProp()
        students = props[1]
        nrStudents = props[2]
        nrProbl = props[3]
        nrStudentiNotati = len(students)
        print("Numarul total de studenti este: "+ str(nrStudents))
        print("Numarul total de probleme este: " + str(nrProbl))
        print("Numarul de studenti fara problema sunt: "+ str(nrStudentiNotati))
        for student in students:
            print(student)
    
    
    def run(self):
        self.__getIstructions()
        commands={"addStudent":self.__uiAddStudent,
                  "addProblLab":self.__uiAddProblLab,
                  "printStudents":self.__uiprintStudent,
                  "printProblLab":self.__uiprintProblLab,
                  "removeStudent":self.__uiremoveStudent,
                  "removeProblLab":self.__uiremoveProblLab,
                  "updateStudent":self.__uiupdateStudent,
                  "updateProblLab":self.__uiupdateProblLab,
                  "searchStudent":self.__uisearchStudent,
                  "searchProblLab":self.__uisearchProblLab,
                  "asignLabNota":self.__uiasignLabNota,
                  "printasignLab":self.__uiprintasignLab,
                  "getStudNotaByProbl":self.__getStudNotaByProbl,
                  "getStudByAverage":self.__getStudByAverage,
                  "getStudByProp":self.__getStudByProp
                  }
        while True:
            cmd = input(">>")
            if cmd == "exit":
                return
            elif cmd in commands:
                try:
                    params = self.__getUiInput(cmd)
                    commands[cmd](params)
                except ValueError as vE:
                    print("Valoare invalida!")
                    print(vE)
                except RepoErrors as re:
                    print("Repository error")
                    print(re)
                except ValidErrors as ve:
                    print("Validation Error")
                    print(ve)
            else:
                print("Comanda invalida!")
            
    



