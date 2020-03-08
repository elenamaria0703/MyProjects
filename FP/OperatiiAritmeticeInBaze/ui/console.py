'''Proiect LC Elena Maria'''
class Console(object):
    
    
    def __init__(self, __serviceOperatii):
        self.__serviceOperatii = __serviceOperatii
    
    def _getInstructions(self):
        print("adunare\nscadere\ninmultire (cu o cifra)\nimpartire (la o cifra)")
    
    def _getUserInput(self,cmd):
        commands = {"adunare":["Dati nr:","Dati nr:","Dati baza:"],"scadere":["Dati nr:","Dati nr:","Dati baza:"]}
        commands1 = {"impartire":["Dati numar:","Dati cifra:"],"inmultire":["Dati numar:","Dati cifra:"]}
        params =[]
        if cmd in commands:
            for i in range(3):
                params.append(input(commands[cmd][i]))
        if cmd in commands1:
            for i in range(2):
                params.append(input(commands1[cmd][i]))
        return params

    def __uiImpartire(self,params):
        numar =params[0].split(" ")[0]
        baza = params[0].split(" ")[1]
        cifra = params[1]
        rezultat = self.__serviceOperatii.Impartire(numar,baza,cifra)
        print(numar,':',cifra,'=',rezultat[0],',',rezultat[1])
    
    
    def __uiInmultire(self,params):
        numar =params[0].split(" ")[0]
        baza = params[0].split(" ")[1]
        cifra = params[1]
        rezultat = self.__serviceOperatii.Inmultire(numar,baza,cifra)
        print(numar,'*')
        print(cifra)
        print("______")
        print(rezultat)
    
    
    def __uiScadere(self,params):
        numar1 = params[0].split(" ")[0]
        baza1 = params[0].split(" ")[1]
        numar2 = params[1].split(" ")[0]
        baza2 = params[1].split(" ")[1]
        baza = int(params[2])
        rezultat = self.__serviceOperatii.Scadere(numar1,baza1,numar2,baza2,baza)
        print(rezultat[0],'-')
        print(rezultat[1])
        print("_______")
        print(rezultat[2])
    
    
    def __uiAdunare(self,params):
        numar1 = params[0].split(" ")[0]
        baza1 = params[0].split(" ")[1]
        numar2 = params[1].split(" ")[0]
        baza2 = params[1].split(" ")[1]
        baza = int(params[2])
        rezultat = self.__serviceOperatii.Adunare(numar1,baza1,numar2,baza2,baza)
        print(rezultat[0],'+')
        print(rezultat[1])
        print("_______")
        print(rezultat[2])
    
    def run(self):
        self._getInstructions()
        commands = {"adunare":self.__uiAdunare,
                    "scadere":self.__uiScadere,
                    "inmultire":self.__uiInmultire,
                    "impartire":self.__uiImpartire}

        while True:
            cmd = input(">>")
            if cmd in commands:
                params = self._getUserInput(cmd)
                commands[cmd](params)


