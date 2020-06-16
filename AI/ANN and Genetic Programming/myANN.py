from math import exp,log
from random import randint

def sigmoid(x):
    return 1 / (1 + exp(-x))

class ANN:
    def __init__(self,h,i,r):
        self.i=i;
        self.r=r;
        self.h=h;
        self.w1=[[0 for _ in range(h)] for _ in range(i)]
        self.w2=[[0 for _ in range(r)] for _ in range(h)]
        self.o1=[0 for _ in range(h)]
        self.o2=[0 for _ in range(r)]
        self.loss=0
        self.n=0
        self.e1=[0 for _ in range(h)]
        self.e2=[0 for _ in range(r)]
        self.__initParams()
        
    def __initParams(self):
        for j in range(self.i):
            for k in range(self.h):
                self.w1[j][k]=randint(1,3)/10;
        for j in range(self.h):
            for k in range(self.r):
                self.w2[j][k]=randint(1,3)/10;
    
    def softmax(self,td):
        s=sum([exp(self.o2[i]) for i in range(self.r)])
        q=[exp(self.o2[i])/s for i in range(self.r)] #mapare valori probabilitati
        ce=(-1)*sum([td[i]*log(q[i]) for i in range(len(td))]) #cross-entropy
        self.loss+=ce
    
    def activate(self,xd):
        #calcul neuroni de pe strat ascuns
        for h in range(self.h):
            wx=sum([self.w1[i][h]*xd[i] for i in range(len(xd))])
            self.o1[h]=sigmoid(wx)
            
        #calcul neuroni strat de iesire
        for r in range(self.r):
            wo=sum([self.w2[h][r]*self.o1[h] for h in range(self.h)])
            self.o2[r]=sigmoid(wo)
            
    
    def backpropagation(self,xd,td,eta):
        for r in range(self.r):
            #eroarea de pe stratul de iesire
            self.e2[r]=self.o2[r]*(1-self.o2[r])*(td[r]-self.o2[r])
            #modific ponderile dintre stratul ascuns si stratul de iesire
            for h in range(self.h):
                self.w2[h][r]=self.w2[h][r]+eta*self.e2[r]*self.o1[h]
                
        for h in range(self.h):
            #eroare de pe stratul ascuns
            self.e1[h]=self.o1[h]*(1-self.o1[h])*sum([self.w2[h][r]*self.e2[r] for r in range(self.r)])
            #modific ponderile dintre stratul de intrare si stratul ascuns
            for i in range(self.i):
                self.w1[i][h]=self.w1[i][h]+eta*self.e1[h]*xd[i]
    
    
    def fit(self,trainData,trainOutput,eta):
        self.n=len(trainData)
        self.loss=0
        for xd,td in zip(trainData,trainOutput):
            self.activate(xd)
            self.softmax(td)
            self.backpropagation(xd, td, eta)
    
    def get_loss(self):
        return self.loss/self.n
    
    
    def predictOneSample(self,sample):
        computedValues=[]
        for h in range(self.h):
            computedValue=sum([self.w1[i][h]*sample[i] for i in range(self.i)])
            computedSigValue=sigmoid(computedValue)
            computedValues.append(computedSigValue)
        finalValue=max(computedValues)
        if(finalValue<0.33): return [1,0,0]
        if(finalValue>=0.33 and finalValue<0.66): return [0,1,0]
        else: return [0,0,1]
        
    def predict(self,testInputs):
        return [self.predictOneSample(sample) for sample in testInputs]
    