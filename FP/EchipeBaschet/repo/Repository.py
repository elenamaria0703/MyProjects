from model.Entities import Player


class FileRepository(object):
    
    
    def __init__(self, fileName):
        self.__filename = fileName
        self.__loadFromFile()
    def __loadFromFile(self):
        '''preia datele din fisier
        le adauga intro lista'''
        f = open(self.__filename,"r")
        listPlayers = []
        for line in f:
            if line.strip() == " ":
                continue
            player = self.__createPlayerFromLine(line)
            listPlayers.append(player)
        f.close()
        return listPlayers
    
    def __createPlayerFromLine(self,line):
        '''preia o linie din fisier
        creaza un jucator cu datele'''
        params = line.split(" ")
        nume = params[0]
        prenume = params[1]
        inaltime = int(params[2])
        post = params[3].split("\n")[0]
        player = Player(nume,prenume,inaltime,post)
        return player

    def __appendToFile(self,player):
        '''primeste un jucator
        printeaza in fisier un jucator'''
        f = open(self.__filename,"a")
        line= str(player)
        f.write(line)
        f.write('\n')
        f.close()
    
    def __writeAlltoFile(self,players):
        '''primeste o lista cu jucatori
        printeaza toti jucatorii in fisier'''
        f = open(self.__filename,"w")
        for player in players:
            self.__appendToFile(player)
            
    def getAll(self):
        ''' returneaza toti jucatorii din fisier'''
        return self.__loadFromFile()
    
    def store(self,player):
        ''' primeste un jucator
        salveaza jucatorul in fisier'''
        players = self.__loadFromFile()
        if player in players:
            return
        players.append(player)
        self.__writeAlltoFile(players)
    
    def update(self,player):
        ''' primeste nume ,prenume,inaltime
        modifica inaltiema jucatorului cu nume, prenume'''
        players = self.__loadFromFile()
        for Player in players:
            if Player == player:
                Player.set_inaltime(player.get_inaltime())
        self.__writeAlltoFile(players)