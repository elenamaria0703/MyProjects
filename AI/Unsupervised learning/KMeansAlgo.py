import random
import math
import sys
class KMeans:
    def __init__(self,k):
        self.__k=k;
        self.__centroids=[];
        self.__c=[];
        self.__finalCentroids=[];
        self.__finalc=[];
        self.__loss=sys.maxsize;

    def fit(self,trainData):
        for _ in range(50):
            self.__c = [0 for _ in range(len(trainData))]
            self.__centroids=[];
            #generate k random centroids
            for i in range(self.__k):
                r=random.randint(0,len(trainData)-1)
                self.__centroids.append(trainData[r])

            for _ in range(100):
                # re-cluster the data
                for j  in range(len(trainData)):
                    d=trainData[j]
                    dist=sys.maxsize
                    poz=0
                    for i in range (self.__k):
                        cur_dist=self.__dist(d,self.__centroids[i])
                        if(cur_dist<dist):
                            dist=cur_dist
                            poz=i
                    self.__c[j]=poz

                # update centroids
                for i in range(self.__k):
                    self.__centroids[i]=self.__mean(i,trainData);

            # compute loss
            loss=sum([self.__dist(trainData[i],self.__centroids[self.__c[i]]) for i in range(len(trainData))])
            loss=loss/len(trainData);
            if(loss<self.__loss):
                self.__loss=loss;
                self.__finalc=self.__c;
                self.__finalCentroids=self.__centroids;



    def __mean(self,c,trainData):
        cluster_data=[]
        for i in range(len(self.__c)):
            if(self.__c[i]==c):
                cluster_data.append(trainData[i]);
        if(len(cluster_data)==0):
            return self.__centroids[c]
        nr_features=len(trainData[0]);
        cluster_feature=[0 for _ in range(nr_features)]
        for i in range(nr_features):
            for d in cluster_data:
                cluster_feature[i]+=d[i];
        cluster_feature=[cluster_feature[i]/len(cluster_data) for i in range(len(cluster_feature))]
        return cluster_feature;

    def __dist(self,d,c):
        s=sum([(x - y)**2 for x,y in zip(d,c)]);
        return math.sqrt(s);

    def getc(self):
        return self.__finalc;

    def getLoss(self):
        return self.__loss

    def predictOneInput(self,input):
        dist=sys.maxsize
        poz=0;
        for i in range(self.__k):
            cur_dist = self.__dist(input, self.__finalCentroids[i])
            if (cur_dist < dist):
                dist = cur_dist
                poz = i
        return poz;

    def predict(self,testInputs):
        return [self.predictOneInput(input) for input in testInputs]