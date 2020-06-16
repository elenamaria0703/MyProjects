import matplotlib.pyplot as plt



#eroarea de predictie in cazul unei regresii multi-target
realOutputs=[[1,5],[3,5.5],[6,2.4],[7,2],[5,4],[2,3.5],[5,6]];
computedOutputs=[[1.5,5],[3,4],[6.3,2],[6.5,2],[5.5,5],[2.3,3.5],[5,6]];



realOutputsX=[1,3,6,7,5,2,5]
realOutputsY=[5,5.5,2.4,2,4,3.5,6]
computedOutputsX=[1.5,3,6.3,6.5,5.5,2.3,5]
computedOutputsY=[5,4,2,2,5,3.5,6]

real, = plt.plot(realOutputsX, realOutputsY, 'ro', label = 'real')
computed, = plt.plot(computedOutputsX,computedOutputsY, 'bo', label = 'computed')
plt.xlim(0,8)
plt.ylim(0,8)
plt.legend([real, (real, computed)], ["Real", "Computed"])
plt.show()

eroare=sum(abs(x-y) for x, y in zip (realOutputsX,computedOutputsX))+sum(abs(x-y) for x, y in zip (realOutputsY,computedOutputsY))/len(realOutputs)
print("Eroarea de predictie: ",eroare)
#acuratetea, precizia, rapelul în cazul unei clasificări multi-class
def evaluateML(realLabels,computedLabels):
    acuratete=sum([1 if realLabels[i]==computedLabels[i] else 0 for i in range(len(realLabels))])/len(realLabels);

    TAm=sum([1 if (realLabels[i] == 'american' and computedLabels[i] == 'american') else 0 for i in range(len(realLabels))])
    FAm=sum([1 if (realLabels[i] != 'american' and computedLabels[i] == 'american') else 0 for i in range(len(realLabels))])
    
    TLi=sum([1 if (realLabels[i] == 'libanese' and computedLabels[i] == 'libanese') else 0 for i in range(len(realLabels))])
    FLi=sum([1 if (realLabels[i] != 'libanese' and computedLabels[i] == 'libanese') else 0 for i in range(len(realLabels))])
    
    TCh=sum([1 if (realLabels[i] == 'chinese' and computedLabels[i] == 'chinese') else 0 for i in range(len(realLabels))])
    FCh=sum([1 if (realLabels[i] !='chinese'and computedLabels[i] == 'chinese') else 0 for i in range(len(realLabels))])
    
    totalAm=sum([1 if realLabels[i]=='american'else 0 for i in range(len(realLabels))])
    totalCh=sum([1 if realLabels[i]=='chinese'else 0 for i in range(len(realLabels))])
    totalLi=sum([1 if realLabels[i]=='libanese'else 0 for i in range(len(realLabels))])
    
    precizieAm=TAm/(TAm+FAm)
    rapelAm=TAm/totalAm
    
    precizieLi=TLi/(TLi+FLi)
    rapelLi=TLi/totalLi
    
    precizieCh=TCh/(TCh+FCh)
    rapelCh=TCh/totalCh
    
    
    print("Acuratete: ",acuratete)
    print("Precizie si rapel clasa american: ",precizieAm,rapelAm)
    print("Precizie si rapel clasa libanese: ",precizieLi,rapelLi)
    print("Precizie si rapel clasa chinese: ",precizieCh,rapelCh)

#date echilibrate
realLabels= ['american','american','libanese','chinese','libanese','american','chinese','libanese','chinese']
computedLabels=['american','libanese','libanese','chinese','american','american','chinese','libanese','chinese']
print("")
print("")
print("Date distribuite uniform")
evaluateML(realLabels, computedLabels)

realLabels= ['american','american','libanese','libanese','libanese','libanese','chinese','american','chinese']
computedLabels=['american','american','libanese','chinese','libanese','libanese','libanese','libanese','chinese']
print("")
print("")
print("Date distribuite neuniform")
evaluateML(realLabels, computedLabels)
#cu probabilitati
realLabels=['american','libanese','libanese','chinese','american','chinese','libanese']
computedProbabilities=[[0.4,0.3,0.3],[0.2,0.3,0.5],[0.3,0.4,0.3],[0.2,0.2,0.6],[0.4,0.3,0.3],[0.7,0.2,0.1],[0.1,0.8,0.1]]

import numpy as np
labelNames = ['american','libanese','chinese']
computedLabels = [labelNames[np.argmax(p)] for p in computedProbabilities]

print("")
print("")
print("Output de tip probabilistic")
evaluateML(realLabels, computedLabels)

#loss problema de regresie
from math import sqrt

realOutputs=[2,3.4,7,5.4,6.3,1,9,8.2,4]
computedOutputs=[2.1,3.5,6.9,4.5,6.4,2,8.7,8.1,4.1]

indexes = [i for i in range(len(realOutputs))]
real, = plt.plot(indexes, realOutputs, 'ro', label = 'real')
computed, = plt.plot(indexes,computedOutputs, 'bo', label = 'computed')
plt.xlim(0,10)
plt.ylim(0,10)
plt.legend([real, (real, computed)], ["Real", "Computed"])
plt.show()
errorL2 = sqrt(sum((r - c) ** 2 for r, c in zip(realOutputs, computedOutputs)) / len(realOutputs))
print("")
print("")
print('Loss regresie: ', errorL2)

#loss problema de clasificare cu output probabilistic
from math import log
realLabels=['sugar','sugar','sugar-free','sugar','sugar-free','sugar-free','sugar-free']
computedProbabilities=[[0.2,0.8],[0.7,0.3],[0.1,0.9],[0.6,0.4],[0.7,0.3],[0.1,0.9],[0.4,0.6]]
#computedProbabilities=[[0.8,0.2],[0.7,0.3],[0.1,0.9],[0.6,0.4],[0.3,0.7],[0.1,0.9],[0.4,0.6]]

loss=-1*sum([log(computedProbabilities[i][0]) if realLabels[i]=='sugar' 
             else log(computedProbabilities[i][1]) for i in range(len(realLabels))])/len(realLabels);
print("")
print("")
print("Output de tip probabilistic")
print("Loss clasificare: ",loss)

labelNames = ['sugar','sugar-free']
computedLabels = [labelNames[np.argmax(p)] for p in computedProbabilities]

acuratete=sum([1 if realLabels[i]==computedLabels[i] else 0 for i in range(len(realLabels))])/len(realLabels);
print("Acuratete:",acuratete)