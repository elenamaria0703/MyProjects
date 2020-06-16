import csv

#load data from file considering feature freedom and economy 
def loadData(fileName, inputVariabName, outputVariabName):
    data = []
    dataNames = []
    with open(fileName) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        line_count = 0
        for row in csv_reader:
            if line_count == 0:
                dataNames = row
            else:
                data.append(row)
            line_count += 1
    selectedVariable = dataNames.index(inputVariabName)
    inputs = [float(data[i][selectedVariable]) for i in range(len(data))]
    selectedOutput = dataNames.index(outputVariabName)
    outputs = [float(data[i][selectedOutput]) for i in range(len(data))]
    
    return inputs, outputs

import os

crtDir =  os.getcwd()
filePath = os.path.join(crtDir,  '2017.csv')

inputsGDP, outputs = loadData(filePath, 'Economy..GDP.per.Capita.', 'Happiness.Score')
inputsFree, outputs = loadData(filePath, 'Freedom', 'Happiness.Score')

#data diagrams
import matplotlib.pyplot as plt 
def plotDataHistogram(x, variableName):
    n, bins, patches = plt.hist(x, 10)
    plt.title('Histogram of ' + variableName)
    plt.show()
   
plotDataHistogram(inputsGDP, 'capita GDP')
plotDataHistogram(inputsFree, 'Freedom')
plotDataHistogram(outputs, 'Happiness score')

#check the liniarity of the data
import matplotlib as mpl
   
mpl.rcParams['legend.fontsize'] = 10
   
fig3 = plt.figure()
ax3 = fig3.gca(projection='3d')
ax3.plot(inputsGDP, inputsFree, outputs, 'ro')
ax3.legend()
plt.xlabel('GDP capita')
plt.ylabel('Freedom')
plt.show()


#split data into training data and test data
import numpy as np 

np.random.seed(5)
indexes = [i for i in range(len(inputsGDP))]
trainSample = np.random.choice(indexes, int(0.8 * len(inputsGDP)), replace = False)
testSample = [i for i in indexes  if not i in trainSample]

trainInputsGDP = [inputsGDP[i] for i in trainSample]
trainInputsFree=[inputsFree[i] for i in trainSample]
trainOutputs = [outputs[i] for i in trainSample]

testInputsGDP = [inputsGDP[i] for i in testSample]
testInputsFree=[inputsFree[i] for i in testSample]
testOutputs = [outputs[i] for i in testSample]

  
mpl.rcParams['legend.fontsize'] = 10  
fig = plt.figure()
ax = fig.gca(projection='3d')
ax.plot(trainInputsGDP,trainInputsFree, trainOutputs, 'ro', label = 'training data')
ax.plot(testInputsGDP,testInputsFree, testOutputs, 'g^', label = 'testing data')     
ax.legend()
plt.xlabel('GDP capita')
plt.ylabel('Freedom')
plt.show()

#train a linear regression model
#with tool
from sklearn import linear_model
xy=[[x,y] for x, y in zip(trainInputsGDP,trainInputsFree)]
regressor=linear_model.LinearRegression()
regressor.fit(xy,trainOutputs)
w0,w1,w2=regressor.intercept_, regressor.coef_[0], regressor.coef_[1]
print('the learnt model: f(x) = ', w0, ' + ', w1, ' * x1',' + ', w2, ' * x2')

#with my code
from myRegression import MyLinearBivariateRegression
myregressor=MyLinearBivariateRegression()
xy=[[1,x,y] for x, y in zip(trainInputsGDP,trainInputsFree)]
myregressor.fit(xy,trainOutputs)
w0,w1,w2=myregressor.intercept_, regressor.coef_[0], regressor.coef_[1]
print('the learnt model: f(x) = ', w0, ' + ', w1, ' * x1',' + ', w2, ' * x2')

noOfPoints = 1000
xref = []
yref = []
valGDP = min(trainInputsGDP)
valFree= min(trainInputsFree)
stepGDP = (max(trainInputsGDP) - min(trainInputsGDP)) / noOfPoints
stepFree=(max(trainInputsFree)-min(trainInputsFree))/noOfPoints
for i in range(1, noOfPoints):
    xref.append(valGDP)
    valGDP += stepGDP
    yref.append(valFree)
    valFree+=stepFree
zref = [w0 + w1 * x + w2 * y  for x, y in zip(xref,yref)] 
 
#plot the learnt model
fig1 = plt.figure()
ax1=fig1.gca(projection='3d')
ax1.plot(trainInputsGDP,trainInputsFree, trainOutputs, 'ro', label = 'training data')
ax1.plot(xref, yref,zref, 'b-', label = 'learnt model')          
plt.title('train data and the learnt model')
plt.xlabel('GDP capita')
plt.ylabel('Freedom')
plt.legend()
plt.show()
 
 
# use the trained model to predict new inputs

#with tool
computedTestOutputs = regressor.predict([[x,y] for x,y in zip(testInputsGDP,testInputsFree)])

fig2 = plt.figure()
ax2 = fig2.gca(projection='3d')
ax2.plot(testInputsGDP,testInputsFree ,computedTestOutputs, 'yo', label = 'computed test data')  
ax2.plot(testInputsGDP,testInputsFree, testOutputs, 'g^', label = 'real test data')  
plt.title('computed test and real test data')
plt.xlabel('GDP capita')
plt.ylabel('Freedom')
plt.legend()
plt.show()

#my code
mycomputedTestOutputs = myregressor.predict(testInputsGDP, testInputsFree)

fig4 = plt.figure()
ax4 = fig4.gca(projection='3d')
ax4.plot(testInputsGDP,testInputsFree ,mycomputedTestOutputs, 'yo', label = 'computed test data')  
ax4.plot(testInputsGDP,testInputsFree, testOutputs, 'g^', label = 'real test data')  
plt.title('computed test and real test data')
plt.xlabel('GDP capita')
plt.ylabel('Freedom')
plt.legend()
plt.show()
 
# compute the prediction error
from sklearn.metrics import mean_squared_error
error = 0.0
for t1, t2 in zip(computedTestOutputs, testOutputs):
    error += (t1 - t2) ** 2
error = error / len(testOutputs)
print("prediction error (manual): ", error)
error = mean_squared_error(testOutputs, computedTestOutputs)
print('prediction error (tool):  ', error)

