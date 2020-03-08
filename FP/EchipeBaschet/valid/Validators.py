from error.Errors import ValidErrors
class ValidatorPlayer(object):
    
    
    def __init__(self):
        pass
    
    def validate(self,player):
        '''primeste un jucator
        valideaza jucatortul:
        nume nevid, prenume nevid, inaltime nr pozitiv, post apartine ["Fundas","Extrema","Pivot"]'''
        posturi = ["Fundas","Extrema","Pivot"]
        if player.get_nume() == "":
            raise ValidErrors("Nume nu poate fi vid")
        if player.get_prenume() == "":
            raise ValidErrors("Prenume nu poate fi vid")
        if player.get_inaltime() < 0:
            raise ValidErrors("Inaltimea nu poate fi negativa")
        if player.get_post() not in posturi and player.get_post()!=None:
            raise ValidErrors("Post invalid")
