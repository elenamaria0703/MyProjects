from error.Errors import ValidErrors
class Console(object):
    
    
    def __init__(self, __serviceBaschet):
        self.__serviceBaschet = __serviceBaschet
        
    def _GetMeniu(self):
        '''functia printeaza meniul cu instructiunile'''
        print(" addPlayer\n updatePlayer\n printEchipa\n importFile\n")

    def __getUserInput(self, cmd):
        '''functia preia date de la utilizator
        returneaza lista cu date pentru prelucrare'''
        commandsInput = {"addPlayer":["Dati nume:","Dati prenume:","Dati inaltime:","Dati post:"]}
        commandsInput3 = {"updatePlayer":["Dati nume:","Dati prenume:","Dati inaltime:"]}
        params = []
        if cmd in commandsInput:
            for i in range(4):
                params.append(input(commandsInput[cmd][i]))
        if cmd in commandsInput3:
            for i in range(3):
                params.append(input(commandsInput3[cmd][i]))       
        return params       
    
    def __uiAddPlayer(self,params):
        '''prelucreaza datele
        apeleaza functia de adaugare din service'''
        nume = params[0]
        prenume = params[1]
        inaltime = int(params[2])
        post = params[3]
        self.__serviceBaschet.AddPlayer(nume,prenume,inaltime,post)
    
    
    def __uiUpdatePlayer(self,params):
        '''prelucreaza datele
        apeleaza functia de modificare din service'''
        nume = params[0]
        prenume = params[1]
        inaltime = int(params[2])
        self.__serviceBaschet.UpdatePlayer(nume,prenume,inaltime)
    
    
    def __uiPrintEchipa(self,params):
        '''functia primeste lista cu echipa formata in service
        printeaza jucatorii din echpa'''
        players = self.__serviceBaschet.PrintEchipa()
        for player in players:
            print(str(player))
        print('\n')
    

    def __uiImportFile(self,params):
        '''apeleaza functia din service
        trimite ca parametru numele unui fisier
        primeste nr de jucatori importati
        '''
        filename = "C:\\Users\\Maria\\eclipse-workspace\\Practic_Elena_Maria\\import.txt"
        numar = self.__serviceBaschet.ImportFile(filename)
        print(numar)
    
    
    def run(self):
        '''preia instructiunea de la utilizator
        apeleaza functia corespunzatoare instructiunii'''
        commands = {"addPlayer":self.__uiAddPlayer,
                    "updatePlayer":self.__uiUpdatePlayer,
                    "printEchipa":self.__uiPrintEchipa,
                    "importFile":self.__uiImportFile}
        while True:
            self._GetMeniu()
            cmd = input(">>")
            if cmd == "exit":
                return 
            if cmd in commands:
                try:
                    params = self.__getUserInput(cmd)
                    commands[cmd](params)
                except ValueError as ve:
                    print("Tip de data invalid")
                except ValidErrors as Ve:
                    print("ValidationError")
                    print(Ve)


    
    



