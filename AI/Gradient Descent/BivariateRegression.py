import csv 
import os
from sklearn import linear_model
import matplotlib.pyplot as plt
import numpy as np 
from myRegression import MyBGDRegression

class GDBBivariate:
    def solve(self):
        crtDir =  os.getcwd()
        filePath = os.path.join(crtDir,  '2017.csv')
        
        inputs, outputs = self.loadDataMoreInputs(filePath, ['Economy..GDP.per.Capita.', 'Freedom'], 'Happiness.Score')
        
        feature1 = [ex[0] for ex in inputs]
        feature2 = [ex[1] for ex in inputs]
        
        # plot the data histograms
        self.plotDataHistogram(feature1, 'capita GDP')
        self.plotDataHistogram(feature2, 'freedom')
        self.plotDataHistogram(outputs, 'Happiness score')
        
        import matplotlib as mpl
   
        mpl.rcParams['legend.fontsize'] = 6
           
        fig3 = plt.figure()
        ax3 = fig3.gca(projection='3d')
        ax3.plot(feature1, feature2, outputs, 'ro')
        ax3.legend()
        plt.xlabel('GDP capita')
        plt.ylabel('Freedom')
        plt.show()
        # check the liniarity (to check that a linear relationship exists between the dependent variable (y = happiness) and the independent variables (x1 = capita, x2 = freedom).)
       

        np.random.seed(5)
        indexes = [i for i in range(len(inputs))]
        trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace = False)
        testSample = [i for i in indexes  if not i in trainSample]
        
        trainInputs = [inputs[i] for i in trainSample]
        trainOutputs = [outputs[i] for i in trainSample]
        testInputs = [inputs[i] for i in testSample]
        testOutputs = [outputs[i] for i in testSample]
        
        
#         trainInputs, testInputs = self.dataNormalisation(trainInputs, testInputs)
#         trainOutputs, testOutputs = self.dataOutputNorm(trainOutputs, testOutputs)
        
        feature1train = [ex[0] for ex in trainInputs]
        feature2train = [ex[1] for ex in trainInputs]
        
        feature1test = [ex[0] for ex in testInputs]
        feature2test = [ex[1] for ex in testInputs]
        mpl.rcParams['legend.fontsize'] = 10  
        fig = plt.figure()
        ax = fig.gca(projection='3d')
        ax.plot(feature1train,feature2train, trainOutputs, 'ro', label = 'training data')
        ax.plot(feature1test,feature2test, testOutputs, 'g^', label = 'testing data')     
        ax.legend()
        plt.xlabel('GDP capita')
        plt.ylabel('Freedom')
        plt.show()
        
                
        #using tool
        xx = [el for el in trainInputs]
        regressor = linear_model.SGDRegressor(alpha = 0.01,warm_start=True)
        for _ in range(300):
            regressor.partial_fit(xx, trainOutputs)
        w0, w1, w2 = regressor.intercept_[0], regressor.coef_[0],regressor.coef_[1]
        print('the learnt model: f(x) = ', w0, ' + ', w1, ' * x1',' + ', w2, ' * x2')
        
        #using my code
        learning_rate=0.01
        noEpochs=300
        regressor=MyBGDRegression()
        xx = [[1,f1,f2] for f1,f2 in zip(feature1train,feature2train)]
        regressor.fit(xx, trainOutputs, learning_rate, noEpochs)
        w0, w1,w2 = regressor.intercept_, regressor.coef_[0],regressor.coef_[1]
        print('the learnt model: f(x) = ', w0, ' + ', w1, ' * x1',' + ', w2, ' * x2')
        
        noOfPoints = 50
        xref1 = []
        val = min(feature1)
        step1 = (max(feature1) - min(feature1)) / noOfPoints
        for _ in range(1, noOfPoints):
            for _ in range(1, noOfPoints):
                xref1.append(val)
            val += step1
        
        xref2 = []
        val = min(feature2)
        step2 = (max(feature2) - min(feature2)) / noOfPoints
        for _ in range(1, noOfPoints):
            aux = val
            for _ in range(1, noOfPoints):
                xref2.append(aux)
                aux += step2
        yref = [w0 + w1 * el1 + w2 * el2 for el1, el2 in zip(xref1, xref2)]
        
        fig1 = plt.figure()
        ax1=fig1.gca(projection='3d')
        ax1.plot(feature1train,feature2train, trainOutputs, 'ro', label = 'training data')
        ax1.plot(xref1, xref2,yref, 'b-', label = 'learnt model')          
        plt.title('train data and the learnt model')
        plt.xlabel('GDP capita')
        plt.ylabel('Freedom')
        plt.legend()
        plt.show()

        
        computedTestOutputs = regressor.predictBi(testInputs)

        fig2 = plt.figure()
        ax2 = fig2.gca(projection='3d')
        ax2.plot(feature1test,feature2test,computedTestOutputs, 'yo', label = 'computed test data')  
        ax2.plot(feature1test,feature2test, testOutputs, 'g^', label = 'real test data')  
        plt.title('computed test and real test data')
        plt.xlabel('GDP capita')
        plt.ylabel('Freedom')
        plt.legend()
        plt.show()
      
        error = 0.0
        for t1, t2 in zip(computedTestOutputs, testOutputs):
            error += (t1 - t2) ** 2
        error = error / len(testOutputs)
        print('prediction error (manual): ', error)
        
        from sklearn.metrics import mean_squared_error
        
        error = mean_squared_error(testOutputs, computedTestOutputs)
        print('prediction error (tool):   ', error)
        pass
   

    def loadDataMoreInputs(self,fileName, inputVariabNames, outputVariabName):
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
        selectedVariable1 = dataNames.index(inputVariabNames[0])
        selectedVariable2 = dataNames.index(inputVariabNames[1])
        inputs = [[float(data[i][selectedVariable1]), float(data[i][selectedVariable2])] for i in range(len(data))]
        selectedOutput = dataNames.index(outputVariabName)
        outputs = [float(data[i][selectedOutput]) for i in range(len(data))]
        
        return inputs, outputs



    def plotDataHistogram(self,x, variableName):
        n, bins, patches = plt.hist(x, 10)
        plt.title('Histogram of ' + variableName)
        plt.show()
        
    def plot3Ddata(self,x1Train, x2Train, yTrain, x1Model = None, x2Model = None, yModel = None, x1Test = None, x2Test = None, yTest = None, title = None):
        ax = plt.axes(projection = '3d')
        if (x1Train):
            plt.scatter(x1Train, x2Train, yTrain, c = 'r', marker = 'o', label = 'train data') 
        if (x1Model):
            plt.scatter(x1Model, x2Model, yModel, c = 'b', marker = '_', label = 'learnt model') 
        if (x1Test):
            plt.scatter(x1Test, x2Test, yTest, c = 'g', marker = '^', label = 'test data')  
        plt.title(title)
        ax.set_xlabel("capita")
        ax.set_ylabel("freedom")
        ax.set_zlabel("happiness")
        plt.legend()
        plt.show()
    
    
    def dataNormalisation(self,trainData,testData):
        feature1=[trainData[i][0] for i in range(len(trainData))]
        feature2=[trainData[i][1] for i in range(len(trainData))]
        
        meanValue1 = sum(feature1) / len(feature1)
        stdDevValue1 = (1 / len(feature1) * sum([ (feat - meanValue1) ** 2 for feat in feature1])) ** 0.5 
        normalisedFeature1 = [(feat - meanValue1) / stdDevValue1 for feat in feature1]
        
        meanValue2 = sum(feature2) / len(feature2)
        stdDevValue2 = (1 / len(feature2) * sum([ (feat - meanValue2) ** 2 for feat in feature2])) ** 0.5 
        normalisedFeature2 = [(feat - meanValue2) / stdDevValue2 for feat in feature2]
        trainDataNorm=[[f1,f2] for f1,f2 in zip(normalisedFeature1,normalisedFeature2)]
        
        testDataNorm=[[(testData[i][0]-meanValue1)/stdDevValue1,(testData[i][0]-meanValue1)/stdDevValue1] for i in range(len(testData))]
        return trainDataNorm,testDataNorm
    
    def dataOutputNorm(self,trainOutput,testOutput):
        meanValue1 = sum(trainOutput) / len(trainOutput)
        stdDevValue1 = (1 / len(trainOutput) * sum([ (feat - meanValue1) ** 2 for feat in trainOutput])) ** 0.5 
        normalisedFeature1 = [(feat - meanValue1) / stdDevValue1 for feat in trainOutput]
        normalisedFeature2 = [(feat - meanValue1) / stdDevValue1 for feat in testOutput]
        return normalisedFeature1,normalisedFeature2
