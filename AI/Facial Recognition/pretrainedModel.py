import numpy as np
def getData(filname):
    # images are 48x48
    # N = 35887
    Y = []
    X = []
    first = True
    i=0;
    for line in open(filname):
        if i==20:
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
output=[]
for i in range(0,10):
    output.append(label_map[Y[i]])


print(output)
import Algorithmia

client = Algorithmia.client("simE94G4BJ2Au4kWdVBJukO170E1")
outputTrained=[]
for i in range(0,10):
    input = {
        "image": "data://mariaelena/images/im"+str(i),
        "numResults": 7
    }

    algo = client.algo('deeplearning/EmotionRecognitionCNNMBP/0.1.2')

    result = algo.pipe(input).result
    outputTrained.append(max(result['results'])[1])

print(outputTrained)
acc=sum([1 if output[i]==outputTrained[i] else 0 for i in range(len(output))])/len(output);
print('Accuracy: ',acc);


