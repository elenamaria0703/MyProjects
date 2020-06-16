from random import randint

class Chromosome:
    def __init__(self, problParam):
        self.__problParam = problParam
        self.__repres = []
        self.__fitness = 0.0
        self.__communities=[]
    
    def initRepres(self):
        repres=[]
        for node in range(self.__problParam['noDim']):
            adjNodes=list(self.__problParam['graph'].adj[node])
            r=randint(0,len(adjNodes)-1)
            repres.append(adjNodes[r])
        return repres
    
    def set_communities(self,com):
        self.__communities=com
        
    def get_communities(self):
        return self.__communities
    
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
    def fitness(self, fit = 0.0):
        self.__fitness = fit 
    
    def crossover(self, c):
        mask=[]
        for _ in range(len(self.__repres)):
            r=randint(0,1);
            mask.append(r)
        newrepres=[]
        for i in range(len(mask)):
            if mask[i]==1:
                newrepres.append(c.__repres[i])  
            else:
                newrepres.append(self.__repres[i])
        offspring=Chromosome(c.__problParam)
        offspring.repres=newrepres
        return offspring    
    
    
    def mutation(self):
        marginalNode=0
        gr=self.__problParam['graph']
        for node in range(self.__problParam['noDim']):
            if node not in self.__repres:
                marginalNode=node
                break
        adjNodes=list(gr.adj[marginalNode])
        r=randint(0,len(adjNodes)-1)
        self.__repres[marginalNode]=adjNodes[r]
    
        
#     def __str__(self):
#         return '\nChromo: ' + str(self.__repres) + ' has fit: ' + str(self.__fitness)
#     
#     def __repr__(self):
#         return self.__str__()
    
    def __eq__(self, c):
        return self.__repres == c.__repres and self.__fitness == c.__fitness