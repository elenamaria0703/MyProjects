from errors.Errors import ValidErrors,RepoErrors

class ProblLabValidator(object):
    
    
    def __init__(self):
        pass
    def ValidateProblLab(self,problLab):
        errors = ""
        params = problLab.get_nr_lab_nr_probl().split("_")
        if len(params)==1:
            errors+="Date invalide!"
        else:
            nrLab=int(params[0])
            nrProbl=int(params[1])
            if nrLab < 0 or nrProbl < 0:
                errors+="Date invalide!"
        if problLab.get_descriere() == "":
            errors+="Nu ati introdus descrierea!"
        params1=problLab.get_deadline().split(".")
        if len(params1)<=2:
            errors+="Date invalide!"
        ziua = int(params1[0])
        luna = int(params1[1])
        anul = int(params1[2])
        if ziua > 31 or luna > 12 or anul > 2020 or anul < 2018:
            errors+="Data invalida!"
        if problLab.get_descriere().isalpha() == False:
            raise ValueError("Descrierea nu contine cifre!")
        if len(errors)>0:
            raise ValidErrors(errors)

    



class StudentiValidator(object):
    
    
    def __init__(self):
        pass
    
    def ValidateStudent(self,student):
        errors=""
        if student.get_idstud() < 0 or student.get_idstud() > 1000:
            errors+="ID incorect!\n"
        if student.get_nume() == "":
            errors+="Campul nume este gol!\n"
        if student.get_grupa() < 0 or student.get_grupa() > 1000:
            errors+="Grupa incorecta!\n"
        if len(errors) > 0:
            raise ValidErrors(errors)
        if student.get_nume().isalpha() == False:
            raise ValueError


class AsignareValidator(object):
    
    
    def __init__(self):
        pass
    



