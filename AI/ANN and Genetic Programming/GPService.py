from myTree import Node,Tree
from Chrom import Chromosome
from GeneticProgAlg import GP
from math import exp

def getOutput(repres,i):
    n = Node(repres.get_data())
    n=Tree().copy(repres)
    Tree().setDataTree(n, i)
    val = Tree().eval(n)
    finalValue = sigmoid(val)
    if (finalValue < 0.33): return [1, 0, 0]
    if (finalValue >= 0.33 and finalValue < 0.66):
        return [0, 1, 0]
    else:
        return [0, 0, 1]

def sigmoid(x):
    return 1 / (1 + exp(-x))

def evalFc(repres, trainInput,trainOutput):
    fitness = 0
    for i,o in zip(trainInput,trainOutput):
        rez=getOutput(repres,i)
        if(rez!=o):
            fitness+=1;
    return fitness/len(trainInput)


def applyGP(trainInput,trainOutput):
    # initialise de GP parameters
    gpParam = {'popSize': 20, 'noGen': 50, 'pc': 0.5, 'pm': 0.1}
    # problem parameters

    gp = GP(gpParam, evalFc,trainInput,trainOutput)
    gp.initialisation()
    gp.evaluation()

    bestOverAll = Chromosome([0,1,2,3])
    bestOverAll.fitness = 100000
    for g in range(gpParam['noGen']):

        gp.oneGenerationSteadyState()

        bestChromo = gp.bestChromosome()

        if (bestChromo.fitness < bestOverAll.fitness):
            bestOverAll.fitness = bestChromo.fitness
            bestOverAll.repres = bestChromo.repres
            print('Loss ' + ' f(x) = ' + str(bestOverAll.fitness))
