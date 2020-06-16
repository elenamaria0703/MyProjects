
class MyBGDRegression:
    
    def __init__(self):
        self.intercept_=0
        self.coef_=[]
    
    def fit(self,x,y,learning_rate,noEpochs):
        self.coef_ = [0 for _ in range(len(x[0]))] 
        
        for _ in range(noEpochs):
            batchError=[0 for _ in range(len(x[0]))]
            
            for i in range(len(x)):
                guess=self.eval(x[i])
                for j in range(len(x[0])):
                    batchError[j]+=(guess-y[i])*x[i][j]
                    
            batchError=[batchError[i]/len(x) for i in range(len(batchError))]
            
            for j in range(len(x[0])):
                self.coef_[j]=self.coef_[j]-learning_rate*batchError[j]
                
        self.intercept_=self.coef_[0]
        self.coef_=self.coef_[1:]
 
    def eval(self,x):
        y=0;
        for i in range(len(x)):
            y+=self.coef_[i]*x[i]
        return y
 
            
    def predictUni(self,inputs):
        return [self.intercept_ + self.coef_[0] * el for el in inputs]
    
    def predictBi(self,inputs):
        return [self.intercept_ + self.coef_[0] * el[0]+self.coef_[1]*el[1] for el in inputs]