from sklearn.datasets import load_iris
def loadIrisData():
    data = load_iris()
    inputs = data['data']
    outputs = data['target']
    outputNames = data['target_names']
    featureNames = list(data['feature_names'])
    inputs = [[feat[featureNames.index('sepal length (cm)')], feat[featureNames.index('sepal width (cm)')],
        feat[featureNames.index('petal length (cm)')],feat[featureNames.index('petal width (cm)')]] for feat in inputs]
    return inputs, outputs,outputNames

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

def getLable(x,c):
    data = []
    for i in range(len(trainInputs)):
        if (c[i] == x):
            data.append(trainInputs[i])
    for j in range(len(inputs)):
        if (inputs[j] == data[0] or inputs[j] == data[1]):
            return outputs[j];

from KMeansAlgo import KMeans
inputs,outputs,outputNames=loadIrisData();
trainInputs, trainOutputs, testInputs, testOutputs=splitData(inputs,outputs);
def kmeansIris():
    unsupervisedClassifier=KMeans(3);
    unsupervisedClassifier.fit(trainInputs);
    c=unsupervisedClassifier.getc()
    predictedValues=unsupervisedClassifier.predict(testInputs);
    predictedValues=[getLable(predictedValues[i],c) for i in range(len(predictedValues))]
    acc=sum([1 if predictedValues[i]==testOutputs[i] else 0 for i in range(len(predictedValues))])/len(predictedValues)
    print(predictedValues)
    print(testOutputs)
    print('Accuracy: ',acc)


text=["it looks amazing",
      "i hate it at the party",
      "it was horrific",
      "i liked the pizza",
      "i hate your jacket",
      "loved the food there",
      "the food was amazing",
      "the picture was horrific",
      "she loved your dress",
      "he liked the view",
      "i hate icecream",
      "the room looked amazing",
      "i love caramel","it was horrific","that's an amazing jumper","i hate that color","i love pasta"]

def word_extraction(sentence):
    ignore=['it','the','on','at','up','and','or','i','was']
    words = sentence.split(" ")
    cleaned_text = [w.lower() for w in words if w not in ignore]
    return cleaned_text

def tokanize(sentences):
    words={}
    for sentence in sentences:
        w=word_extraction(sentence)
        for i in w:
            if(i in words):
                words[i]+=1;
            else:
                words[i]=1;
    vocab=[w for w in words if words[w]>1]
    return vocab

def bagOfWords(sentences):
    vocab=tokanize(sentences)
    vectors=[]
    for sentence in sentences:
        words=word_extraction(sentence)
        vector=[0 for _ in range(len(vocab))]
        for w in words:
            for i,word in enumerate(vocab):
                if word == w:
                    vector[i]+=1;
        vectors.append(vector)
    return vectors

from sklearn.feature_extraction.text import TfidfVectorizer

def ngrams():
    tfidf=TfidfVectorizer(min_df=2,max_df=0.5,ngram_range=(1,2));
    features=tfidf.fit_transform(text)
    return features,tfidf.get_feature_names()

def kmeanBOW():
    vectors = bagOfWords(text)
    train = vectors[:12]
    test = vectors[12:]
    testoutput=[0,1,0,1,0]
    unsupervisedClassifier = KMeans(2);
    unsupervisedClassifier.fit(train);
    c = unsupervisedClassifier.getc()
    predictedValues = unsupervisedClassifier.predict(test);
    print(c)
    print(predictedValues)
    acc=sum([1 if predictedValues[i]==testoutput[i] else 0 for i in range(len(predictedValues))])/len(predictedValues)
    print('Accuracy: ',acc)

from sklearn import linear_model
def logisticRegression():
    features,names=ngrams()
    inputs=features.toarray()
    outputs = [0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0]
    trainInputs, trainOutputs, testInputs, testOutputs = splitData(inputs, outputs);
    classifier = linear_model.LogisticRegression()
    classifier.fit(trainInputs,trainOutputs)
    predicted=classifier.predict(testInputs)
    acc=sum([1 if predicted[i]==testOutputs[i] else 0 for i in range(len(testOutputs))])/len(testOutputs)
    print('Accuracy: ',acc)

logisticRegression()
#kmeanBOW()
#kmeansIris()