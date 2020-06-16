from locale import atoi


class ReadDynamicFile:
    def __init__(self,__filename):
        self.__filename=__filename;
        
    def __loadFromFile(self):
        f=open(self.__filename+".txt","r")
        dictModif={}
        lines = [line.rstrip() for line in f]
        nrLines=len(lines)
        nrCities=0
        for i in range(nrLines):
            params=lines[i].split(" ")
            node1=atoi(params[0])
            node2=atoi(params[1])
            weight=atoi(params[2])
            nodes=[node1-1,node2-1,weight]
            it=atoi(params[3])
            if(dictModif.get(it)!=None):
                dictModif[it].append(nodes)
            else:
                dictModif[it]=[nodes]
            if(node1>nrCities):
                nrCities=node1
            if(node2>nrCities):
                nrCities=node2

        return (nrCities,dictModif)
    
    def getCities(self):
        return self.__loadFromFile()
    