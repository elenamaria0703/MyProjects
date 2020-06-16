from AntClass import Ant
from numpy.random.mtrand import randint
import random
from _testcapi import INT_MAX

class ACO:
    def __init__(self,params,graph):
        self.__params=params
        self.__ants=[]
        self.__pheromone=[[0 for _ in range(params['noNodes'])] for _ in range(params['noNodes'] )]
        self.__graph=graph
    
    def initialization(self):
        self.__ants=[]
        for _ in range(self.__params['noNodes']-1):
            node=randint(0,self.__params['noNodes']-1)
            ant=Ant(self.__graph,self.__params['noNodes'])
            ant.addNode(node)
            ant.markVisited(node)
            self.__ants.append(ant)
            
            
    def solveACO(self):
        for _ in range(self.__params['noNodes']-1):
            for ant in self.__ants:
                currentNode=ant.route()[-1]
                self.__moveAnt(ant)
                nextNode=ant.route()[-1]
                self.__updatePheromone(currentNode, nextNode)
        
    def markBestRoute(self):
        bestAnt=self.getBestAnt()
        pheromone=round(1/bestAnt[1],2)
        route=bestAnt[0].route()
        degradation=self.__params['degradation']
        
        for i in range(len(route)):
            if(i!=len(route)-1):
                node=route[i]
                nextNode=route[i+1]
                self.__pheromone[node][nextNode]=(1-degradation)*self.__pheromone[node][nextNode]+degradation*pheromone
                self.__pheromone[nextNode][node]=self.__pheromone[node][nextNode]
        self.__pheromone[route[i]][route[0]]=(1-degradation)*self.__pheromone[route[i]][route[0]]+degradation*pheromone
        self.__pheromone[route[0]][route[i]]=self.__pheromone[route[i]][route[0]]
        
        
        
    def getBestAnt(self):
        distance=INT_MAX
        bestAnt=self.__ants[0]
        for ant in self.__ants:
            if(ant.distance()<distance):
                distance=ant.distance()
                bestAnt=ant
        return (bestAnt,distance)
    
    
     
    def __moveAnt(self,ant):
        q=randint(1,9)/10
        nextNode=0
        
        if(q<=self.__params['p']):
            nextNode= self.__getOptimalNode(ant)
        else:
            nextNode= self.__getMostProbableNode(ant)
        ant.addNode(nextNode)
        ant.markVisited(nextNode)
    
    
    def __getOptimalNode(self,ant):
        currentNode=ant.route()[-1]
        bestNode=0
        bestFitness=0
        for i in range(self.__params['noNodes']):
            if(ant.visited()[i]==False):
                if(self.__pheromone[currentNode][i]==0):
                    fitness=1/self.__graph[currentNode][i]
                else:
                    fitness=self.__pheromone[currentNode][i]*(1/self.__graph[currentNode][i])
                if(fitness>bestFitness):
                    bestFitness=fitness
                    bestNode=i
        return bestNode
    
    def __getMostProbableNode(self,ant):
        currentNode=ant.route()[-1]
        
        unvisited=[]
        for i in range(self.__params['noNodes']):
            if(ant.visited()[i]==False):
                unvisited.append(i)
                
        sumF=0
        fitnesses=[]
        for el in unvisited:
            if(self.__pheromone[currentNode][el]==0):
                fitness=1/self.__graph[currentNode][el]
            else:
                fitness=self.__pheromone[currentNode][el]*(1/self.__graph[currentNode][el])
            fitnesses.append(fitness)
            sumF+=fitness
            
        roulet=[]
        for i in range(len(fitnesses)):
            fitnesses[i]=fitnesses[i]/sumF
            roulet.append(round(fitnesses[i],2))
        
        for i in range(len(roulet)):
            for j in range(i+1,len(roulet)):
                roulet[i]+=roulet[j]
        
        r=round(random.uniform(0, 1),2)
        i=0
        while(i<len(roulet) and r<roulet[i] ):
            i+=1
        return unvisited[i-1]
            
    def __updatePheromone(self,currentNode,nextNode):
        currentPheromone=self.__pheromone[currentNode][nextNode]
        degradation=self.__params['degradation']
        pheromoneInit=self.__params['initPheromone']
        self.__pheromone[currentNode][nextNode]=(1-degradation)*currentPheromone+degradation*pheromoneInit
        self.__pheromone[nextNode][currentNode]=self.__pheromone[currentNode][nextNode]
        
        
    #for dynamic graph pheromone updates
    
    
    def getGraph(self):
        return self.__graph
    
    def setGraph(self,node1,node2,weight):
        self.__graph[node1][node2]=weight  
        self.__graph[node2][node1]=weight  
        
    def updatePheromoneDecrease(self,node1,node2):
        currentPheromone=self.__pheromone[node1][node2]
        bestAnt=self.getBestAnt()
        pheromone=round(1/bestAnt[1],2)
        degradation=self.__params['degradation']
        self.__pheromone[node1][node2]=(1-degradation)*currentPheromone+degradation*pheromone
        self.__pheromone[node2][node1]=self.__pheromone[node1][node2]
        
    def updatePheromoneIncrease(self,node1,node2):
        currentPheromone=self.__pheromone[node1][node2]
        degradation=self.__params['degradation']
        self.__pheromone[node1][node2]=(1-degradation)*currentPheromone
        self.__pheromone[node2][node1]=self.__pheromone[node1][node2]
     
       