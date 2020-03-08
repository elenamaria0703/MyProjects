class Student(object):
    
    
    def __init__(self, __idstud, __nume, __grupa):
        self.__idstud = __idstud
        self.__nume = __nume
        self.__grupa = __grupa

    def get_idstud(self):
        return self.__idstud


    def get_nume(self):
        return self.__nume


    def get_grupa(self):
        return self.__grupa


    def set_nume(self, value):
        self.__nume = value


    def set_grupa(self, value):
        self.__grupa = value

    
    def __str__(self):
        return str(self.__idstud)+" "+str(self.__nume)+" "+str(self.__grupa)
    
    def __eq__(self,value):
        if type(value)==int:
            return self.__idstud == value
        return self.__idstud == value.__idstud
  
    



class ProblLab(object):
    
    
    def __init__(self, __nrLab_nrProbl, __descriere, __deadline):
        self.__nrLab_nrProbl = __nrLab_nrProbl
        self.__descriere = __descriere
        self.__deadline = __deadline

    def get_nr_lab_nr_probl(self):
        return self.__nrLab_nrProbl


    def get_descriere(self):
        return self.__descriere


    def get_deadline(self):
        return self.__deadline


    def set_descriere(self, value):
        self.__descriere = value


    def set_deadline(self, value):
        self.__deadline = value

    
    def __str__(self):
        return str(self.__nrLab_nrProbl) + " " +str(self.__descriere) +" " + str(self.__deadline)
    
    def __eq__(self, value):  
        if type(value) == str:
            return self.__nrLab_nrProbl == value
        return str(self.__nrLab_nrProbl) == str(value.__nrLab_nrProbl)
    
    
    




class AsignProblNota(object):
    
    
    def __init__(self, __student, __problLab,__nota):
        self.__student = __student
        self.__problLab = __problLab
        self.__nota = __nota

    def get_nota(self):
        return self.__nota


    def set_nota(self, value):
        self.__nota = value


    def get_student(self):
        return self.__student


    def get_probl_lab(self):
        return self.__problLab


    def set_probl_lab(self, value):
        self.__problLab = value
        
        
        
    def __eq__(self, value):
        if value.__problLab is None:
            return  self.__student.get_idstud() == value.__student.get_idstud()
        else:
            return  self.__student.get_idstud() == value.__student.get_idstud() and self.__problLab.get_nr_lab_nr_probl() == value.__problLab.get_nr_lab_nr_probl()
    
    def __str__(self):
        return str(self.__student.get_idstud()) + " "+str(self.__problLab.get_nr_lab_nr_probl() + " " +  str(self.__nota))
    
    
class StudentByProblDTO(object):
    
    
    def __init__(self, __name, __nota, __probl):
        self.__name = __name
        self.__nota = __nota
        self.__probl = __probl

    def get_name(self):
        return self.__name

    def __str__(self):
        return str(self.__name) + " are nota " + str(self.__nota) + " la problema " + str(self.__probl)
    


class StudentByAverageDTO(object):
    
    
    def __init__(self, __nume, __average):
        self.__nume = __nume
        self.__average = __average

    def get_average(self):
        return self.__average

    def __str__(self):
        return str(self.__nume) + " are media "+ str(self.__average)
    

class AsignNotaDTO(object):
    def __init__(self,__idstud,__nrProblLab,__student,__problLab,__nota):
        self.__idstud = __idstud
        self.__nrProblLab = __nrProblLab
        self.__student = __student
        self.__problLab = __problLab
        self.__nota = __nota


    def get_idstud(self):
        return self.__idstud

    def get_nrProbLab(self):
        return self.__nrProblLab
 
    def get_student(self):
        return self.__student

    def get_problLab(self):
        return self.__problLab
    def get_nota(self):
        return self.__nota
    
    def set_student(self, value):
        self.__student = value

    def set_problLab(self, value):
        self.__problLab = value
    
    def __eq__(self, value):
        if type(value) == int:
            return  self.__student.get_idstud() == value
        elif value.__student is None:
            return self.get_idstud() == value.get_idstud() and self.get_nrProbLab() == value.get_nrProbLab()
        return  self.__student.get_idstud() == value.__student.get_idstud() and self.__problLab.get_nr_lab_nr_probl() == value.__problLab.get_nr_lab_nr_probl()
    
    def __str__(self):
        return str(self.__student.get_idstud()) + " "+str(self.__problLab.get_nr_lab_nr_probl() + " " +  str(self.__nota))