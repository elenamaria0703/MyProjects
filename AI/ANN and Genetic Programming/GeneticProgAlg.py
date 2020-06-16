from random import randint
from Chrom import Chromosome


class GP:
    def __init__(self, __param, __evalFc,trainInput,trainOutput):
        self.__param = __param
        self.__evalFC = __evalFc
        self.__population = []
        self.input=trainInput;
        self.output=trainOutput;

    @property
    def population(self):
        return self.__population

    def initialisation(self):
        features=[0,1,2,3]
        for _ in range(0, self.__param['popSize']):
            c = Chromosome(features)
            self.__population.append(c)

    def evaluation(self):
        for c in self.__population:
            c.fitness = self.__evalFC(c.repres, self.input,self.output)

    def bestChromosome(self):
        best = self.__population[0]
        for c in self.__population:
            if (c.fitness < best.fitness):
                best = c
        return best

    def worstChromosome(self):
        worst = self.__population[0]
        poz = 0
        for i in range(len(self.__population)):
            if (self.__population[i].fitness > worst.fitness):
                poz = i
                worst = self.__population[i]

        return (worst, poz)

    def selection(self):
        pos1 = randint(0, self.__param['popSize'] - 1)
        pos2 = randint(0, self.__param['popSize'] - 1)
        r = randint(0, 10) / 10
        if (self.__population[pos1].fitness < self.__population[pos2].fitness):
            bestPos = pos1
            worstPos = pos2
        else:
            bestPos = pos2
            worstPos = pos1
        if (r < self.__param['pc']):
            return bestPos
        else:
            return worstPos

    def oneGenerationSteadyState(self):
        for _ in range(self.__param['popSize']):
            p1 = self.__population[self.selection()]
            p2 = self.__population[self.selection()]
            off1 = p1.crossover(p2)
            off2 = p2.crossover(p1)
            off1.fitness = self.__evalFC(off1.repres, self.input,self.output)
            off2.fitness = self.__evalFC(off2.repres, self.input,self.output)

            poz = self.worstChromosome()[1]
            if (off1.fitness < off2.fitness):
                self.__population[poz] = off1
            else:
                self.__population[poz] = off2

