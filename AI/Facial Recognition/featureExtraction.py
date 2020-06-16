from sklearn import neural_network
import numpy as np
def getData(filname):
    # images are 48x48
    # N = 35887
    Y = []
    X = []
    first = True
    i=0;
    for line in open(filname):
        if i==101:
            break;
        if first:
            first = False
        else:
            row = line.split(',')
            Y.append(int(row[0]))
            X.append([int(p) for p in row[1].split()])
        i+=1;
    X, Y = np.array(X), np.array(Y)
    return X, Y

X, Y = getData('fer2013.csv')
label_map = ['Angry', 'Disgust', 'Fear', 'Happy', 'Sad', 'Surprise', 'Neutral']
outputs=[]
for i in range(0,100):
    outputs.append(label_map[Y[i]])

from PIL import Image;
from skimage.feature import hog
def extractFeature():
    inputs=[]
    for i in range(100):
        image = Image.open('images/im'+str(i))
        fd = hog(image)
        inputs.append(fd)
    return inputs


from main import splitData
def classify():
    trainInputs, trainOutputs, testInputs, testOutputs = splitData(extractFeature(), outputs)
    classifier = neural_network.MLPClassifier(hidden_layer_sizes=(6,), activation='relu', max_iter=100, solver='sgd',
                                              verbose=10, random_state=1, learning_rate_init=.1)
    classifier.fit(trainInputs, trainOutputs)
    predictedLables = classifier.predict(testInputs)
    acc = sum([1 if predictedLables[i] == testOutputs[i] else 0 for i in range(len(testOutputs))]) / len(testOutputs)
    print(acc)
    print(testOutputs)
    print(predictedLables)

classify()


# for i in range(20,100):
#     arr=X[i];
#     arr.resize((48, 48))
#     im = Image.fromarray(arr)
#     im.save('images/im'+str(i), 'GIF')