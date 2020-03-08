from model.Entities import Player
import random
class ServiceBaschet(object):
    
    
    def __init__(self, __repoBaschet, __validBaschet):
        self.__repoBaschet = __repoBaschet
        self.__validBaschet = __validBaschet
    
    def AddPlayer(self,nume,prenume,inaltime,post):
        ''' creaza un player, valideaza player 
        apeleaza functia de adaugare din repo daca este valid
        pusca erori altfel'''
        player = Player(nume,prenume,inaltime,post)
        self.__validBaschet.validate(player)
        self.__repoBaschet.store(player)
    
    def UpdatePlayer(self,nume,prenume,inaltime):
        ''' creaza un player cu nume, prenume, valideaza player 
        apeleaza functia de modificare din repo daca este valid
        pusca erori altfel'''
        player = Player(nume,prenume,inaltime,None)
        self.__validBaschet.validate(player)
        self.__repoBaschet.update(player)
    
    def PrintEchipa(self):
        '''primeste lista cu toti jucatorii
        creaza liste separate pentru fiecare post
        determina inaltimile maxime 
        creaza echipa cu media de inaltime maxima formata din doi fundasi ,doi extreme si un picot'''
        players = self.__repoBaschet.getAll()
        fundasi = []
        extreme = []
        pivoti = []
        for player in players:
            if player.get_post() == "Fundas":
                fundasi.append(player)
            elif player.get_post() == "Pivot":
                pivoti.append(player)
            else:
                extreme.append(player)
        Players = self.determinareInaltimiMax(fundasi)+[self.determinareInaltimiMax(pivoti)[0]]+self.determinareInaltimiMax(extreme)
        return Players
    
    def determinareInaltimiMax(self,listPlayers):
        '''primeste o lista de jucatori
        determina si returneaza primii doi jucatori cu inaltime maxima'''
        max1 = Player(None,None,0,None)
        max2 = Player(None,None,0,None)
        maximi = []
        for player in listPlayers:
            if player.get_inaltime() > max1.get_inaltime():
                max2 = max1
                max1 = player
            elif player.get_inaltime() > max2.get_inaltime():
                max2 = player
        maximi.append(max1)
        maximi.append(max2)
        return maximi
    
    def ImportFile(self,filename):
        f = open(filename,"r")
        numar = 0
        posturi = ["Fundas","Pivot","Extrema"]
        for line in f:
            params = line.split(" ")
            nume = params[0]
            prenume = params[1].split('\n')[0]
            inaltime = random.randint(100,250)
            poz = random.randint(0,2)
            post = posturi[poz]
            player = Player(nume,prenume,inaltime,post)
            players = self.__repoBaschet.getAll()
            if player not in players:
                self.__repoBaschet.store(player)
                numar+=1
        return numar