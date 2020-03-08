class Player(object):
    
    
    def __init__(self, __nume, __prenume, __inaltime, __post):
        self.__nume = __nume
        self.__prenume = __prenume
        self.__inaltime = __inaltime
        self.__post = __post

    def get_nume(self):
        return self.__nume


    def get_prenume(self):
        return self.__prenume


    def get_inaltime(self):
        return self.__inaltime


    def get_post(self):
        return self.__post


    def set_inaltime(self, value):
        self.__inaltime = value

    def __str__(self):
        return str(self.__nume)+" " +str(self.__prenume) + " " +str(self.__inaltime)+ " " +str(self.__post)
    def __eq__(self, val):
        return self.__nume == val.__nume and self.__prenume == val.__prenume



