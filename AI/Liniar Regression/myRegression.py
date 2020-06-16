import numpy
class MyLinearBivariateRegression:
    def __init__(self):
        self.intercept_=0.0
        self.coef_=[]
    
    def fit(self,x,y):
#         x=numpy.array(x)
#         y=numpy.array(y)
#         xtrans=x.T
#         mat=numpy.dot(xtrans,x)
#         inverse=numpy.linalg.inv(mat)
#         mat2=numpy.dot(inverse,x.T)
#         w=numpy.dot(mat2,y)
#         self.intercept_=w[0]
#         self.coef_.append(w[1])
#         self.coef_.append(w[2])
        
        transx=self.__transpose(x)
        mat1=self.__multiply(transx, x)
        inv=self.__inverse(mat1)        
        mat=self.__multiply(inv, transx)
        w=self.__multiplyArr(mat, y)
        self.intercept_=w[0]
        self.coef_.append(w[1])
        self.coef_.append(w[2])
 
        
        
    def predict(self,inputs1,inputs2):
        return [self.intercept_ + self.coef_[0] * x + self.coef_[1] * y for x,y in zip(inputs1,inputs2)]
    
    def __transpose(self,x):
        transpose=[[0 for _ in range(len(x))] for _ in range(len(x[0]))]
        for i in range(len(x)):
            for j in range(len(x[0])):
                transpose[j][i]=x[i][j]
        return transpose
    
    def __multiply(self,x,y):
        rows=len(x)
        cols=len(y[0])
        colsx=len(x[0])
        mat=[[0 for _ in range(cols)] for _ in range(rows)]
        for i in range(rows):
            for j in range(cols):
                for k in range(colsx):
                    mat[i][j]+=x[i][k]*y[k][j]
        return mat
    
    def __multiplyArr(self,x,y):
        mat=[0 for _ in range(len(y))]
        for i in range(len(x)):
            for j in range(len(y)):
                mat[i]+=y[j]*x[i][j]
        return mat
    
    def __inverse(self,x):
        minor=self.__findMinor(x)
        trans=self.__transpose(minor)
        p=0
        det=0
        for i in range(len(x[0])):
            det+=x[0][i]*pow(-1,p)*self.__detMinor(0, i, x)
            p=p+1
        inverse=[[trans[i][j]/det for j in range(len(trans[0]))] for i in range(len(trans)) ]
        return inverse
    
        
    def __findMinor(self,x):
        p=0
        minor=[[0 for _ in range(len(x[0]))] for _ in range(len(x))]
        for i in range(len(minor)):
            for j in range(len(minor[0])):
                minor[i][j]=pow(-1,p)*self.__detMinor(i, j, x)
                p=p+1
        return minor
    
    def __detMinor(self,l,c,x):
        minor=[]
        for i in range(len(x)):
            if(i!=l):
                el=[]
                for j in range(len(x[0])):
                    if(j!=c):
                        el.append(x[i][j])
                minor.append(el)
        return minor[0][0]*minor[1][1]+(-1)*minor[0][1]*minor[1][0]
                
                
                