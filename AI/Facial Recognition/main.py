import glob;
from PIL import Image;

def convert_rgb(values):
    return values[0]*(256**2)+values[1]*256+values[2];

def loadImages(file):
    images=[];
    for filename in glob.glob(file+'/*.jpg'):
        img=Image.open(filename);
        pixel_values=[convert_rgb(values) for values in img.getdata()];
        images.append(pixel_values);
    return images;

def handleImageData():
    happy_emoticons=loadImages('happy');
    sad_emoticons=loadImages('sad');
    happy_sad_emoticons=loadImages('hapy_sad');
    #inputs=happy_emoticons+sad_emoticons;
    #outputs = ['happy' for _ in range(len(happy_emoticons))] + ['sad' for _ in range(len(sad_emoticons))];
    inputs = happy_emoticons + sad_emoticons+happy_sad_emoticons
    outputs = [[1,0] for _ in range(len(happy_emoticons))] + [[0,1] for _ in range(len(sad_emoticons))]+[[1,1] for _ in range(len(happy_sad_emoticons))];
    return inputs,outputs;


from sklearn.preprocessing import StandardScaler


def normalisation(trainData, testData):
    scaler = StandardScaler()
    if not isinstance(trainData[0], list):
        # encode each sample into a list
        trainData = [[d] for d in trainData]
        testData = [[d] for d in testData]

        scaler.fit(trainData)  # fit only on training data
        normalisedTrainData = scaler.transform(trainData)  # apply same transformation to train data
        normalisedTestData = scaler.transform(testData)  # apply same transformation to test data

        # decode from list to raw values
        normalisedTrainData = [el[0] for el in normalisedTrainData]
        normalisedTestData = [el[0] for el in normalisedTestData]
    else:
        scaler.fit(trainData)  # fit only on training data
        normalisedTrainData = scaler.transform(trainData)  # apply same transformation to train data
        normalisedTestData = scaler.transform(testData)  # apply same transformation to test data
    return normalisedTrainData, normalisedTestData

import numpy as np


def splitData(inputs, outputs):
    np.random.seed(5)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace=False)
    testSample = [i for i in indexes if not i in trainSample]

    trainInputs = [inputs[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]
    testInputs = [inputs[i] for i in testSample]
    testOutputs = [outputs[i] for i in testSample]

    return trainInputs, trainOutputs, testInputs, testOutputs

from sklearn import neural_network
def classifyEmoticons():
    inputs,outputs=handleImageData();
    trainInputs, trainOutputs, testInputs, testOutputs = splitData(inputs, outputs)
    trainInputs, testInputs = normalisation(trainInputs, testInputs)
    classifier = neural_network.MLPClassifier(hidden_layer_sizes=(6,), activation='relu', max_iter=100, solver='sgd',
                verbose=10, random_state=1, learning_rate_init=.1)
    classifier.fit(trainInputs,trainOutputs)
    predictedLables=classifier.predict(testInputs);
    print(testOutputs)
    pred=[list(predictedLables[i]) for i in range(len(predictedLables))]
    print(pred)
    acc=sum([1 if pred[i]==testOutputs[i] else 0 for i in range(len(testOutputs))])/len(testOutputs);
    print('Accuracy: ',acc);

classifyEmoticons()


