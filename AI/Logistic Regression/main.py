from sklearn.datasets import load_iris

data = load_iris()
inputs = data['data']
outputs = data['target']
outputNames = data['target_names']
featureNames = list(data['feature_names'])
feature1 = [feat[featureNames.index('sepal length (cm)')] for feat in inputs]
feature2 = [feat[featureNames.index('sepal width (cm)')] for feat in inputs]
feature3 = [feat[featureNames.index('petal length (cm)')] for feat in inputs]
feature4 = [feat[featureNames.index('petal width (cm)')] for feat in inputs]

inputs = [[feat[featureNames.index('sepal length (cm)')], feat[featureNames.index('sepal width (cm)')],
        feat[featureNames.index('petal length (cm)')],feat[featureNames.index('petal width (cm)')]] for feat in inputs]

import matplotlib.pyplot as plt
labels = set(outputs)
noData = len(inputs)
for crtLabel in labels:
    x = [feature1[i] for i in range(noData) if outputs[i] == crtLabel ]
    y = [feature2[i] for i in range(noData) if outputs[i] == crtLabel ]
    z = [feature3[i] for i in range(noData) if outputs[i] == crtLabel ]
    v = [feature4[i] for i in range(noData) if outputs[i] == crtLabel ]
    plt.scatter(x, y, label = outputNames[crtLabel])
plt.legend()
plt.show()

def plotDataHistogram(x, variableName):
    n, bins, patches = plt.hist(x, 10)
    plt.title('Histogram of ' + variableName)
    plt.show()

# plot the data distribution
plotDataHistogram(feature1, 'sepal length (cm)')
plotDataHistogram(feature2, 'sepal width (cm)')
plotDataHistogram(feature3, 'petal length (cm)')
plotDataHistogram(feature4, 'petal width (cm)')
plotDataHistogram(outputs, 'iris class')


from sklearn.preprocessing import StandardScaler

def normalisation(trainData, testData):
    scaler = StandardScaler()
    if not isinstance(trainData[0], list):
        #encode each sample into a list
        trainData = [[d] for d in trainData]
        testData = [[d] for d in testData]
        
        scaler.fit(trainData)  #  fit only on training data
        normalisedTrainData = scaler.transform(trainData) # apply same transformation to train data
        normalisedTestData = scaler.transform(testData)  # apply same transformation to test data
        
        #decode from list to raw values
        normalisedTrainData = [el[0] for el in normalisedTrainData]
        normalisedTestData = [el[0] for el in normalisedTestData]
    else:
        scaler.fit(trainData)  #  fit only on training data
        normalisedTrainData = scaler.transform(trainData) # apply same transformation to train data
        normalisedTestData = scaler.transform(testData)  # apply same transformation to test data
    return normalisedTrainData, normalisedTestData


def plotClassificationData(feature1, feature2, outputs, title = None):
    labels = set(outputs)
    noData = len(feature1)
    for crtLabel in labels:
        x = [feature1[i] for i in range(noData) if outputs[i] == crtLabel ]
        y = [feature2[i] for i in range(noData) if outputs[i] == crtLabel ]
        plt.scatter(x, y, label = outputNames[crtLabel])
    plt.xlabel('sepal length (cm)')
    plt.ylabel('sepal width (cm)')
    plt.legend()
    plt.title(title)
    plt.show()
import numpy as np

# split data into train and test subsets
np.random.seed(5)
indexes = [i for i in range(len(inputs))]
trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace = False)
testSample = [i for i in indexes  if not i in trainSample]

trainInputs = [inputs[i] for i in trainSample]
trainOutputs = [outputs[i] for i in trainSample]
testInputs = [inputs[i] for i in testSample]
testOutputs = [outputs[i] for i in testSample]

#normalise the features
trainInputs, testInputs = normalisation(trainInputs, testInputs)
 
#plot the normalised data
feature1train = [ex[0] for ex in trainInputs]
feature2train = [ex[1] for ex in trainInputs]
feature3train = [ex[2] for ex in trainInputs]
feature4train = [ex[3] for ex in trainInputs]
 
feature1test = [ex[0] for ex in testInputs]
feature2test = [ex[1] for ex in testInputs]  
feature3test = [ex[2] for ex in testInputs]  
feature4test = [ex[3] for ex in testInputs]  
 
plotClassificationData(feature1train, feature2train, trainOutputs, 'normalised train data')
 
def plotPredictions(feature1, feature2, realOutputs, computedOutputs, title, labelNames):
    labels = list(set(outputs))
    noData = len(feature1)
    for crtLabel in labels:
        x = [feature1[i] for i in range(noData) if realOutputs[i] == crtLabel and computedOutputs[i] == crtLabel ]
        y = [feature2[i] for i in range(noData) if realOutputs[i] == crtLabel and computedOutputs[i] == crtLabel]
        plt.scatter(x, y, label = labelNames[crtLabel] + ' (correct)')
    for crtLabel in labels:
        x = [feature1[i] for i in range(noData) if realOutputs[i] == crtLabel and computedOutputs[i] != crtLabel ]
        y = [feature2[i] for i in range(noData) if realOutputs[i] == crtLabel and computedOutputs[i] != crtLabel]
        plt.scatter(x, y, label = labelNames[crtLabel] + ' (incorrect)')
    plt.xlabel('sepal length (cm)')
    plt.ylabel('sepal width (cm)')
    plt.legend()
    plt.title(title)
    plt.show()
 
 
# using sklearn
from sklearn import linear_model
classifier = linear_model.LogisticRegression()
classifier.fit(trainInputs,trainOutputs)
w0=max(classifier.intercept_)
w1=max(classifier.coef_[0][0],classifier.coef_[1][0],classifier.coef_[2][0])
w2=max(classifier.coef_[0][1],classifier.coef_[1][1],classifier.coef_[2][1])
w3=max(classifier.coef_[0][2],classifier.coef_[1][2],classifier.coef_[2][2])
w4=max(classifier.coef_[0][3],classifier.coef_[1][3],classifier.coef_[2][3])
 
print('classification model: y(feat1, feat2) = ', w0, ' + ', w1, ' * feat1 + ', w2, ' * feat2', ' + ', w3, ' * feat3 + ', w4, ' * feat4')
computedTestOutputs = classifier.predict(testInputs)
plotPredictions(feature1test, feature2test, testOutputs, computedTestOutputs, "real test data", outputNames)
from sklearn.metrics import accuracy_score
error = 1 - accuracy_score(testOutputs, computedTestOutputs)
print("classification error (tool): ", error)
 
#using my code
from myRegression import MyLogisticRegression
classifier=MyLogisticRegression()
classifier.fit(trainInputs,trainOutputs)
w0=max(classifier.intercept_)
w1=max(classifier.coef_[0][0],classifier.coef_[1][0],classifier.coef_[2][0])
w2=max(classifier.coef_[0][1],classifier.coef_[1][1],classifier.coef_[2][1])
w3=max(classifier.coef_[0][2],classifier.coef_[1][2],classifier.coef_[2][2])
w4=max(classifier.coef_[0][3],classifier.coef_[1][3],classifier.coef_[2][3])
 
print('classification model: y(feat1, feat2) = ', w0, ' + ', w1, ' * feat1 + ', w2, ' * feat2', ' + ', w3, ' * feat3 + ', w4, ' * feat4')
 
 
computedTestOutputs = classifier.predict(testInputs)
computedOutputs=[computedTestOutputs[i][0] for i in range(len(computedTestOutputs))]
  
plotPredictions(feature1test, feature2test, testOutputs, computedOutputs, "real test data", outputNames)
  
  
error = 0.0
for t1, t2 in zip(computedOutputs, testOutputs):
    if (t1 != t2):
        error += 1
error = error / len(testOutputs)
print("classification error (manual): ", error)

#loss function log
from math import log
loss=-1*sum([log(computedTestOutputs[i][1]) if testOutputs[i]==computedOutputs[i] 
             else log(1-computedTestOutputs[i][1]) for i in range(len(computedOutputs))])/len(computedOutputs);
print("loss: ",loss)
#cross validation
i=0
errorSum=0
for _ in range(10):
    testInputs=[]
    testOutputs=[]
    trainInputs=[]
    trainOutputs=[]
    for j in range(i*15,(1+i)*15):
        testInputs.append(inputs[j])
        testOutputs.append(outputs[j])
    for j in range(0,i*15):
        trainInputs.append(inputs[j])
        trainOutputs.append(outputs[j])
    for j in range((i+1)*15,len(inputs)):
        trainInputs.append(inputs[j])
        trainOutputs.append(outputs[j])
    classifier=MyLogisticRegression()
    classifier.fit(trainInputs,trainOutputs)
    computedTestOutputs = classifier.predict(testInputs)
    computedOutputs=[computedTestOutputs[i][0] for i in range(len(computedTestOutputs))]
    errorSum += 1 - accuracy_score(testOutputs, computedOutputs)
    i+=1
    
print("overall classification error(cross validation): ",errorSum/10)
    
 
