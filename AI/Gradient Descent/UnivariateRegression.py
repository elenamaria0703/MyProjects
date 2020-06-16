import csv
import os
import matplotlib.pyplot as plt
import numpy as np 
from sklearn import linear_model
from myRegression import MyBGDRegression

class GDBUnivariate:
#     def __init__(self):
#         pass
    def solve(self):
        crtDir =  os.getcwd()
        filePath = os.path.join(crtDir,  '2017.csv')
        
        inputs, outputs = self.loadData(filePath, 'Economy..GDP.per.Capita.', 'Happiness.Score')
        
        self.plotDataHistogram(inputs, "capita GDP")
        self.plotDataHistogram(outputs, "Happiness score")
        
        #split data in train data and test data
        np.random.seed(5)
        indexes = [i for i in range(len(inputs))]
        trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace = False)
        testSample = [i for i in indexes  if not i in trainSample]
        
        trainInputs = [inputs[i] for i in trainSample]
        trainOutputs = [outputs[i] for i in trainSample]
        
        testInputs = [inputs[i] for i in testSample]
        testOutputs = [outputs[i] for i in testSample]
        
        plt.plot(trainInputs, trainOutputs, 'ro', label = 'training data')   
        plt.plot(testInputs, testOutputs, 'g^', label = 'testing data')     
        plt.title('train and test data')
        plt.xlabel('GDP capita')
        plt.ylabel('happiness')
        plt.legend()
        plt.show()
        
        # training the model by using the training inputs and known training outputs
        #using tool
        xx = [[el] for el in trainInputs]
        regressor = linear_model.SGDRegressor(alpha = 0.01,warm_start=True)
        for _ in range(300):
            regressor.partial_fit(xx, trainOutputs)
        w0, w1 = regressor.intercept_[0], regressor.coef_[0]
        print('the learnt model: f(x) = ', w0, ' + ', w1, ' * x')
        
        #using my code
        learning_rate=0.01
        noEpochs=300
        regressor=MyBGDRegression()
        xx = [[1,el] for el in trainInputs]
        regressor.fit(xx, trainOutputs, learning_rate, noEpochs)
        w0, w1 = regressor.intercept_, regressor.coef_[0]
        print('the learnt model: f(x) = ', w0, ' + ', w1, ' * x')
        
        #plot the learnt model
        noOfPoints = 1000
        xref = []
        val = min(trainInputs)
        step = (max(trainInputs) - min(trainInputs)) / noOfPoints
        for i in range(1, noOfPoints):
            xref.append(val)
            val += step
        yref = [w0 + w1 * el for el in xref] 
        
        plt.plot(trainInputs, trainOutputs, 'ro', label = 'training data')  
        plt.plot(xref, yref, 'b-', label = 'learnt model')                 
        plt.title('train data and the learnt model')
        plt.xlabel('GDP capita')
        plt.ylabel('happiness')
        plt.legend()
        plt.show()
        
        # use the trained model to predict new inputs
        computedTestOutputs = regressor.predictUni(testInputs)

        # plot the computed outputs (see how far they are from the real outputs)
        plt.plot(testInputs, computedTestOutputs, 'yo', label = 'computed test data')  #computed test data are plotted yellow red and circle sign
        plt.plot(testInputs, testOutputs, 'g^', label = 'real test data')  #real test data are plotted by green triangles
        plt.title('computed test and real test data')
        plt.xlabel('GDP capita')
        plt.ylabel('happiness')
        plt.legend()
        plt.show()
        
        #prediction error
        error = 0.0
        for t1, t2 in zip(computedTestOutputs, testOutputs):
            error += (t1 - t2) ** 2
        error = error / len(testOutputs)
        print('prediction error (manual): ', error)
        
        # by using sklearn 
        from sklearn.metrics import mean_squared_error
        
        error = mean_squared_error(testOutputs, computedTestOutputs)
        print('prediction error (tool):  ', error)

            
    def loadData(self,fileName, inputVariabName, outputVariabName):
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

    def plotDataHistogram(self,x, variableName):
        n, bins, patches = plt.hist(x, 10)
        plt.title('Histogram of ' + variableName)
        plt.show()

