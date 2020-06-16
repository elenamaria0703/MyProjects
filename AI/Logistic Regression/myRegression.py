from math import exp

def sigmoid(x):
    return 1 / (1 + exp(-x))

class MyLogisticRegression:
    def __init__(self):
        self.intercept_=[]
        self.coef_=[]
    
    
    def fit(self,x,y,learning_rate=0.001,noEpochs=500):
        ybinary0=[1 if y[i]==0 else 0 for i in range(len(y))]
        ybinary1=[1 if y[i]==1 else 0 for i in range(len(y))]
        ybinary2=[1 if y[i]==2 else 0 for i in range(len(y))]
        result=[]
        result.append(self.train(x, ybinary0, learning_rate, noEpochs))
        result.append(self.train(x, ybinary1, learning_rate, noEpochs))
        result.append(self.train(x, ybinary2, learning_rate, noEpochs))
        for i in range(3):
            self.intercept_.append(result[i][0])
            self.coef_.append(result[i][1:])
        
    
    def train(self,x,y,learning_rate,noEpochs):
        currentCoef=[0 for _ in range(1+len(x[0]))]
        for _ in range(noEpochs):
            for i in range(len(x)):
                ycomputed=sigmoid(self.eval(x[i], currentCoef))
                crtError=ycomputed-y[i]
                for j in range(len(x[0])):
                    currentCoef[j+1]=currentCoef[j+1]-learning_rate*crtError*x[i][j]
                currentCoef[0]=currentCoef[0]-learning_rate*crtError
        return currentCoef
    
    
    def eval(self, xi, coef):
        yi = coef[0]
        for j in range(len(xi)):
            yi += coef[j + 1] * xi[j]
        return yi
    
    def predictOneSample(self,sample):
        computedValues=[]
        for i in range(3):
            coefficients=[self.intercept_[i]]+self.coef_[i]
            computedValue=self.eval(sample, coefficients)
            computedSigValue=sigmoid(computedValue)
            computedValues.append(computedSigValue)
        finalValue=max(computedValues)
        if(finalValue<0.33): return [0,finalValue]
        if(finalValue>=0.33 and finalValue<0.66): return [1,finalValue]
        else: return [2,finalValue]

    
    def predict(self,testInputs):
        computedLabels = [self.predictOneSample(sample) for sample in testInputs]
        return computedLabels