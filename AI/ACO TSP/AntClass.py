
class Ant:
    def __init__(self,graph,nrNodes):
        self.__route=[]
        self.__graph=graph
        self.__visited=[False for _ in range(nrNodes)]
        
    def distance(self):
        dist=0
        for i in range(len(self.__route)):
            if(i==len(self.__route)-1):
                dist+=self.__graph[self.__route[i]][self.__route[0]]
            else:
                dist+=self.__graph[self.__route[i]][self.__route[i+1]]
        return dist
    
    def visited(self):
        return self.__visited
    
    def route(self):
        return self.__route
    
    def addNode(self,node):
        self.__route.append(node)
        
    def markVisited(self,node):
        self.__visited[node]=True