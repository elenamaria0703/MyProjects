import glob;
from PIL import Image;

def convert_rgb(values):
    return values[0]*(256**2)+values[1]*256+values[2];

def loadImages(file):
    images=[];
    for filename in glob.glob('sepia_db' + '/'+file+'/*.jpg'):
        img=Image.open(filename);
        pixel_values=[convert_rgb(values) for values in img.getdata()];
        images.append(pixel_values);
    return images;

def loadIrisData():
    from sklearn.datasets import load_iris

    data = load_iris()
    inputs = data['data']
    outputs = data['target']
    outputNames = data['target_names']
    featureNames = list(data['feature_names'])
    inputs = [[feat[featureNames.index('sepal length (cm)')], feat[featureNames.index('sepal width (cm)')],
        feat[featureNames.index('petal length (cm)')],feat[featureNames.index('petal width (cm)')]] for feat in inputs]
    outputs=[[0 if i!= el else 1 for i in range(3)] for el in outputs]
    return inputs, outputs, outputNames

import numpy as np

def splitData(inputs, outputs):
    np.random.seed(5)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace = False)
    testSample = [i for i in indexes  if not i in trainSample]

    trainInputs = [inputs[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]
    testInputs = [inputs[i] for i in testSample]
    testOutputs = [outputs[i] for i in testSample]
    
    return trainInputs, trainOutputs, testInputs, testOutputs

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

def training(classifier,trainData,trainOutput):
    for i in range(100):
        classifier.fit(trainData,trainOutput,0.1)
        loss=classifier.get_loss()
        print('Iteration: ',i,', loss= ',loss)

from myANN import ANN
from myAnnP import ANN2
def solveAnn():
    inputs, outputs, outputNames = loadIrisData()
    trainInputs, trainOutputs, testInputs, testOutputs = splitData(inputs, outputs)
    trainInputs, testInputs = normalisation(trainInputs, testInputs)
    trainInputs=[el.tolist() for el in trainInputs]
    trainInputs=[[1]+el for el in trainInputs]
    testInputs=[el.tolist() for el in testInputs]
    testInputs=[[1]+el for el in testInputs]
    classifier=ANN(25,5,3)
    training(classifier, trainInputs, trainOutputs)
    computedLables=classifier.predict(testInputs)
    right=sum([1 if testOutputs[i]==computedLables[i] else 0 for i in range(len(computedLables))])
    print('Acuratete: ', right/len(computedLables))

#solveAnn()

def handleImageData():
    filter_images=loadImages('yes');
    nofilter_images=loadImages('no');
    inputs=filter_images+nofilter_images;
    outputs=[[1,0] for _ in range(len(filter_images))]+[[0,1] for _ in range(len(nofilter_images))];
    return inputs,outputs;

def solveAnnImages():
    inputs, outputs = handleImageData();
    trainInputs, trainOutputs, testInputs, testOutputs = splitData(inputs, outputs)
    trainInputs = [[1] + el for el in trainInputs]
    testInputs = [[1] + el for el in testInputs]
    classifier = ANN(5, 101, 2)
    training(classifier, trainInputs, trainOutputs)
    computedLables = classifier.predict(testInputs)
    right = sum([1 if testOutputs[i] == computedLables[i] else 0 for i in range(len(computedLables))])

#solveAnnImages()
import GPService
inputs, outputs, outputNames = loadIrisData()
GPService.applyGP(inputs,outputs)