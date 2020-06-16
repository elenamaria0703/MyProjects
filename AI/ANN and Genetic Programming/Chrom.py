from myTree import Tree
import random
class Chromosome:
    def __init__(self, features):
        self.__repres = Tree().generate(features)
        self.__fitness = 0.0

    @property
    def repres(self):
        return self.__repres

    @property
    def fitness(self):
        return self.__fitness

    @repres.setter
    def repres(self, l):
        self.__repres = l

    @fitness.setter
    def fitness(self, fit=0.0):
        self.__fitness = fit

    def crossover(self, c):
        #k=random.randint(1,4);
        k=1;
        s=Tree().getSubTreeRight(c.repres,k)
        n=Tree().copy(self.repres)
        Tree().setSubTreeRight(n,k,s);
        c=Chromosome([0,1,2,3])
        c.repres=n
        return c

    def __str__(self):
        return "\nChromo: " + str(self.__repres) + " has fit: " + str(self.__fitness)

    def __repr__(self):
        return self.__str__()

    def __eq__(self, c):
        return self.__repres == c.__repres and self.__fitness == c.__fitness