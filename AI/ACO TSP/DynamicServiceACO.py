from DynamicFileReader import ReadDynamicFile
from numpy.random.mtrand import randint
from ACOAlgo import ACO
from _testcapi import INT_MAX

class ServiceDynamic:
    def __init__(self,__filename):
        self.__filename=__filename
        
    def __initMatrix(self,length):
        matrix=[]
        for _ in range(length):
            l=[]
            for _ in range(length):
                l.append(INT_MAX)
            matrix.append(l)
        return matrix
    
    def solve(self):
        fileReader=ReadDynamicFile(self.__filename)

        
        dataFromFile=fileReader.getCities()
        nrCities=dataFromFile[0]
        dictCities=dataFromFile[1]
        firstIt=dictCities[1]
        nrIt=len(dictCities)
        graph=self.__initMatrix(nrCities)
        for el in firstIt:
            graph[el[0]][el[1]]=el[2]
            graph[el[1]][el[0]]=el[2]
        
        
        fi=randint(1,9)/10
        params={'noNodes':nrCities,'degradation':fi,'initPheromone':1,'p':0.7}
        
        aco=ACO(params,graph)


        distance=INT_MAX
        it=2
        for _ in range(nrCities):
            aco.initialization()
            aco.solveACO()
            aco.markBestRoute()
            
            bestAnt=aco.getBestAnt()
            
            if(it<=nrIt):
                self.__updateGraph(aco, dictCities[it])  
                it+=1     
            if(bestAnt[1]<=distance):
                bestAntOverAll=bestAnt
                distance=bestAnt[1]
                print(bestAntOverAll[0].route())
                print(distance)
                
    def __updateGraph(self,aco,params):
        graph=aco.getGraph()
        for p in params:
            dist=graph[p[0]][p[1]]
            if(dist!=INT_MAX):
                if(dist>p[2]):
                    aco.updatePheromoneDecrease(p[0],p[1])
                else:
                    aco.updatePheromoneIncrease(p[0],p[1])
            aco.setGraph(p[0],p[1],p[2])
            
            
            
            
            