from FileReader import ReadFile
from numpy.random.mtrand import randint
from ACOAlgo import ACO
from _testcapi import INT_MAX
class Service:
    def __init__(self,__filename):
        self.__filename=__filename
    
    def solve(self):
        fileReader=ReadFile(self.__filename)
        
#         dataFromFile=fileReader.getCities()
#         nrCities=dataFromFile[0]
#         cities=dataFromFile[1]
          
        
        dataFromFile=fileReader.read_tsp_file()
        nrCities=dataFromFile[0]
        cities=dataFromFile[1]
        matrix=[]
        for i in range(nrCities):
            l=[]
            for j in range(nrCities):
                l.append(cities.item(i,j))
            matrix.append(l)
        
        fi=randint(1,9)/10
        params={'noNodes':nrCities,'degradation':fi,'initPheromone':1,'p':0.7}
        
        aco=ACO(params,matrix)
#         aco=ACO(params,cities)

        distance=INT_MAX
        for _ in range(nrCities):
            aco.initialization()
            aco.solveACO()
            aco.markBestRoute()
                
            bestAnt=aco.getBestAnt()
      
            if(bestAnt[1]<distance):
                bestAntOverAll=bestAnt
                distance=bestAnt[1]
                print(bestAntOverAll[0].route())
                print(distance)
        