from model.GeneticAlg import GA
from model.Chrom import Chromosome
class Service:
    def __init__(self,__repo):
        self.__repo=__repo
   
    def evalFc(self,repres,cities):
        fitness=0
        for i in range(len(repres)):
            if(i==len(repres)-1):
                fitness+=cities[0][repres[i]]
            else:
                fitness+=cities[repres[i]][repres[i+1]]
        return fitness
    
    def evalFc1(self,repres,cities):
        fitness=0
        for i in range(len(repres)):
            if(i==len(repres)-1):
                fitness+=cities.item(0,repres[i])
            else:
                fitness+=cities.item(repres[i],repres[i+1])
        return fitness
    
    
    def applyGA(self):
        # initialise de GA parameters
        gaParam = {'popSize' : 200, 'noGen' : 500, 'pc' : 0.5, 'pm' : 0.1}
        # problem parameters
       
        
        nrCities=self.__repo.getCities()[0];
        listCities=self.__repo.getCities()[1];
        problParam = {'function' : self.evalFc, 'noNodes' : nrCities, 'cities':listCities}
           
        
#         nrCities=self.__repo.read_tsp_file()[0];
#         listCities=self.__repo.read_tsp_file()[1];
#         problParam = {'function' : self.evalFc1, 'noNodes' : nrCities, 'cities':listCities}
          
        ga = GA(gaParam,problParam)
        ga.initialisation()
        ga.evaluation()
            
        bestOverAll=Chromosome(problParam)
        bestOverAll.fitness=100000
        for g in range(gaParam['noGen']):
           
            ga.oneGenerationSteadyState()
            
            bestChromo = ga.bestChromosome()
            
            if(bestChromo.fitness<bestOverAll.fitness):
                bestOverAll.fitness=bestChromo.fitness
                bestOverAll.repres=bestChromo.repres
            
            print('Best solution in generation '+ ' f(x) = ' + str(bestChromo.fitness))
            print(bestChromo.repres)
            
        print(bestOverAll.fitness)
        print(bestOverAll.repres)