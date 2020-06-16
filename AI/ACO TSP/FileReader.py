from locale import atoi
import tsplib95 as tsp
import networkx as nx
class ReadFile:
    def __init__(self,__filename):
        self.__filename=__filename
    
    def __loadFromFile(self):
        f=open(self.__filename+".txt","r")
        listCities=[]
        lines = [line.rstrip() for line in f]
        nrCities=atoi(lines[0])
        for nrCity in range(nrCities):
            params=lines[nrCity+1].split(",")
            distances=[]
            for dist in params:
                distances.append(atoi(dist))
            listCities.append(distances)
    
        return (nrCities,listCities)
    
    def getCities(self):
        return self.__loadFromFile()
    
    def read_tsp_file(self):
        tsp_problem = tsp.load_problem(self.__filename+".txt")
        G = tsp_problem.get_graph()
        n=len(G.nodes())
        matrix=nx.to_numpy_matrix(G)
        return (n,matrix)