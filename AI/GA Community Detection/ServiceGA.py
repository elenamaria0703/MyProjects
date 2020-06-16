import networkx as nx
from model.GAlgo import GA
import matplotlib.pyplot as plt

class GAService:
    def __init__(self,__filename):
        self.__filename=__filename
        self.__gr = None
        
    def __modularity(self,communities,param):
        noNodes = param['noNodes']
        mat = param['mat']
        degrees = param['degrees']
        noEdges = param['noEdges']  
        M = 2 * noEdges
        Q = 0.0
        for i in range(0, noNodes):
            for j in range(0, noNodes):
                if (communities[i] == communities[j]):
                    Q += (mat[i][j] - degrees[i] * degrees[j] / M)
        return Q * 1 / M
    
    def __getMatrix(self,n):
        mat=[]
        for _ in range(n):
            el=[0 for _ in range(n)]
            mat.append(el)
        return mat
    
    def matAd(self):
        mat=self.__getMatrix(len(list(self.__gr.nodes)))
        for edge in list(self.__gr.edges):
            mat[edge[0]][edge[1]] = 1
            mat[edge[1]][edge[0]] = 1
        return mat

    def evalFc(self,repres):
        gr=nx.Graph()
        for node in range(len(repres)):
            gr.add_node(node)
            gr.add_edge(node, repres[node])
            gr.add_edge(repres[node], node)
        param={'noNodes':len(repres),'noEdges':len(list(self.__gr.edges)),'degrees':self.__gr.degree,'mat':self.matAd()}
        communities=[0 for _ in range(len(repres))]
        viz=[0 for _ in range(len(repres))]
        compNr=1
        for node in range(len(viz)):
            if viz[node]==0:
                self.__conexComponent(node, gr, viz, compNr, communities)
                compNr+=1
        
        return self.__modularity(communities, param), communities
    
    def __conexComponent(self,node,gr,viz,compNr,communities):
        viz[node]=1
        communities[node]=compNr
        adjnodes=list(gr.adj[node])
        for adjNode in adjnodes:
            if viz[adjNode]==0:
                self.__conexComponent(adjNode, gr, viz, compNr, communities)
        
        
    def applyGA(self):
        # initialise de GA parameters
        gaParam = {'popSize' : 50, 'noGen' : 100, 'pc' : 0.5, 'pm' : 0.1}
        # problem parameters
        g=nx.read_gml(self.__filename)
        
            
            
        self.__gr = nx.convert_node_labels_to_integers(g,first_label=0, ordering='default', label_attribute=None)
        gr = self.__gr
        
            
        noDim=gr.number_of_nodes()
        problParam = {'function' : self.evalFc, 'noDim' : noDim, 'graph':gr}
        
        # store the best/average solution of each iteration (for a final plot used to anlyse the GA's convergence)
#         allBestFitnesses = []
#         allAvgFitnesses = []
#         generations = []
        
        
        ga = GA(gaParam,problParam)
        ga.initialisation()
        ga.evaluation()
            
        bestOverAll=ga.bestChromosome()
        for g in range(gaParam['noGen']):
            #plotting preparation
#             allPotentialSolutionsX = [c.repres for c in ga.population]
#             allPotentialSolutionsY = [c.fitness for c in ga.population]
#             bestSolX = ga.bestChromosome().repres
#             bestSolY = ga.bestChromosome().fitness
#             
#             allBestFitnesses.append(bestSolY)
#             allAvgFitnesses.append(sum(allPotentialSolutionsY) / len(allPotentialSolutionsY))
#             generations.append(g)
           
           
            ga.oneGenerationSteadyState()
            
            bestChromo = ga.bestChromosome()
            
            if(max(bestChromo.get_communities())<max(bestOverAll.get_communities())):
                bestOverAll.set_communities(bestChromo.get_communities())
            
            print('Best solution in generation '+ ' f(x) = ' + str(bestChromo.fitness))
            print(max(bestChromo.get_communities()))
            print(bestChromo.get_communities())
        
        
        pos = nx.spring_layout(gr)
        plt.figure(figsize=(8, 8))
        #nx.draw_networkx_nodes(graf, pos, node_size=600, cmap=plt.cm.RdYlBu, node_color=communities)
        #nx.draw_networkx_edges(graf, pos, alpha=0.3)
        nx.draw_networkx(gr, pos, node_size=600, node_color=bestOverAll.get_communities())
        plt.show()
            
            
        
             
            