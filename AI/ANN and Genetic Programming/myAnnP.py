from math import exp, log
from random import randint


def sigmoid(x):
    return 1 / (1 + exp(-x))


class ANN2:
    def __init__(self, h, i, r):
        self.i = i;
        self.r = r;
        self.h = h;
        self.w1 = [[0 for _ in range(h)] for _ in range(i)]
        self.w2=[[0 for _ in range(h)] for _ in range(h)]
        self.w3 = [[0 for _ in range(r)] for _ in range(h)]
        self.o1 = [0 for _ in range(h)]
        self.o2 = [0 for _ in range(h)]
        self.o3 = [0 for _ in range(r)]
        self.loss = 0
        self.n = 0
        self.e1 = [0 for _ in range(h)]
        self.e2 = [0 for _ in range(h)]
        self.e3 = [0 for _ in range(r)]
        self.__initParams()

    def __initParams(self):
        for j in range(self.i):
            for k in range(self.h):
                self.w1[j][k] = randint(1, 3) / 10;
        for j in range(self.h):
            for k in range(self.r):
                self.w2[j][k] = randint(1, 3) / 10;
        for j in range(self.h):
            for h in range(self.h):
                self.w2[j][h] = randint(1, 3) / 10;


    def softmax(self, td):
        s = sum([exp(self.o3[i]) for i in range(self.r)])
        q = [exp(self.o3[i]) / s for i in range(self.r)]  # mapare valori probabilitati
        ce = (-1) * sum([td[i] * log(q[i]) for i in range(len(td))])  # cross-entropy
        self.loss += ce

    def activate(self, xd):
        # calcul neuroni de pe strat ascuns
        for h in range(self.h):
            wx = sum([self.w1[i][h] * xd[i] for i in range(len(xd))])
            self.o1[h] = sigmoid(wx)

        # calcul neuroni de pe strat ascuns
        for h in range(self.h):
            wx = sum([self.w2[i][h] * self.o1[i] for i in range(self.h)])
            self.o2[h] = sigmoid(wx)

        # calcul neuroni strat de iesire
        for r in range(self.r):
            wo = sum([self.w3[h][r] * self.o2[h] for h in range(self.h)])
            self.o3[r] = sigmoid(wo)

    def backpropagation(self, xd, td, eta):
        for r in range(self.r):
            # eroarea de pe stratul de iesire
            self.e3[r] = self.o3[r] * (1 - self.o3[r]) * (td[r] - self.o3[r])
            # modific ponderile dintre stratul ascuns si stratul de iesire
            for h in range(self.h):
                self.w3[h][r] = self.w3[h][r] + eta * self.e3[r] * self.o2[h]

        for h in range(self.h):
            # eroare de pe stratul ascuns
            self.e2[h] = self.o2[h] * (1 - self.o2[h]) * sum([self.w3[h][r] * self.e3[r] for r in range(self.r)])
            # modific ponderile dintre stratul de intrare si stratul ascuns
            for i in range(self.h):
                self.w2[i][h] = self.w2[i][h] + eta * self.e2[h] * self.o1[i]

        for h in range(self.h):
            # eroare de pe stratul ascuns
            self.e1[h] = self.o1[h] * (1 - self.o1[h]) * sum([self.w2[h][r] * self.e2[r] for r in range(self.r)])
            # modific ponderile dintre stratul de intrare si stratul ascuns
            for i in range(self.i):
                self.w1[i][h] = self.w1[i][h] + eta * self.e1[h] * xd[i]

    def fit(self, trainData, trainOutput, eta):
        self.n = len(trainData)
        self.loss = 0
        for xd, td in zip(trainData, trainOutput):
            self.activate(xd)
            self.softmax(td)
            self.backpropagation(xd, td, eta)

    def get_loss(self):
        return self.loss / self.n

    def predictOneSample(self, sample):
        computedValues = []
        for h in range(self.h):
            computedValue = sum([self.w1[i][h] * sample[i] for i in range(self.i)])
            computedSigValue = sigmoid(computedValue)
            computedValues.append(computedSigValue)
        finalValue = max(computedValues)
        if (finalValue < 0.33): return [1, 0, 0]
        if (finalValue >= 0.33 and finalValue < 0.66):
            return [0, 1, 0]
        else:
            return [0, 0, 1]

    def predict(self, testInputs):
        return [self.predictOneSample(sample) for sample in testInputs]

# from math import exp, log
# from random import randint
#
#
# def sigmoid(x):
#     return 1 / (1 + exp(-x))
#
#
# class ANN2:
#     def __init__(self, h,p, i, r):
#         self.i = i;
#         self.r = r;
#         self.h = h;
#         self.p=p;
#         self.wi = [[0 for _ in range(h)] for _ in range(i)]
#         self.w=[[[0 for _ in range(h)] for _ in range(h)] for _ in range(p-1)]
#         self.wo = [[0 for _ in range(r)] for _ in range(h)]
#         self.o = [[0 for _ in range(h)] for _ in range(p)]
#         self.of = [0 for _ in range(r)]
#         self.loss = 0
#         self.n = 0
#         self.e = [[0 for _ in range(h)] for _ in range(p)]
#         self.eo = [0 for _ in range(r)]
#         self.__initParams()
#
#     def __initParams(self):
#         for j in range(self.i):
#             for k in range(self.h):
#                 self.wi[j][k] = randint(1, 3) / 10;
#         for j in range(self.h):
#             for k in range(self.r):
#                 self.wo[j][k] = randint(1, 3) / 10;
#         for p in range(self.p-1):
#             for h1 in range(self.h):
#                 for h2 in range(self.h):
#                     self.w[p][h1][h2]=randint(1, 3) / 10;
#
#     def softmax(self, td):
#         s = sum([exp(self.of[i]) for i in range(self.r)])
#         q = [exp(self.of[i]) / s for i in range(self.r)]  # mapare valori probabilitati
#         ce = (-1) * sum([td[i] * log(q[i]) for i in range(len(td))])  # cross-entropy
#         self.loss += ce
#
#     def activate(self, xd):
#         # calcul neuroni de pe strat ascuns
#         for h in range(self.h):
#             wx = sum([self.wi[i][h] * xd[i] for i in range(len(xd))])
#             self.o[0][h] = sigmoid(wx)
#
#         for p in range(1,self.p):
#             for h in range(self.h):
#                 wx = sum([self.w[p-1][i][h] * self.o[p-1][i] for i in range(self.h)])
#                 self.o[p][h] = sigmoid(wx)
#         # calcul neuroni strat de iesire
#
#         for r in range(self.r):
#             wo = sum([self.w[-1][h][r] * self.o[-1][h] for h in range(self.h)])
#             self.of[r] = sigmoid(wo)
#
#     def backpropagation(self, xd, td, eta):
#         for r in range(self.r):
#             # eroarea de pe stratul de iesire
#             self.eo[r] = self.of[r] * (1 - self.of[r]) * (td[r] - self.of[r])
#             # modific ponderile dintre stratul ascuns si stratul de iesire
#             for h in range(self.h):
#                 self.wo[h][r] = self.wo[h][r] + eta * self.eo[r] * self.o[-1][h]
#
#         for p in range(self.p-2,-1,-1):
#             for h in range(self.h):
#                 # eroare de pe stratul ascuns
#                 if(p==self.p-2):
#                     self.e[p+1][h]=self.o[-1][h] * (1 - self.o[-1][h]) * sum([self.wo[h][r] * self.eo[r] for r in range(self.r)])
#                 else:
#                     self.e[p+1][h] = self.o[-p][h] * (1 - self.o[-(p+1)][h]) * sum([self.w[p][h][r] * self.e[p+2][r] for r in range(self.r)])
#                 # modific ponderile dintre stratul de intrare si stratul ascuns
#                 for h1 in range(self.h):
#                     self.w[p][h1][h] = self.w[p][h1][h] + eta * self.e[p+1][h] * self.o[-(p+1)][h1]
#
#         for h in range(self.h):
#             #eroare de pe stratul ascuns
#             self.e[0][h]=self.o[0][h]*(1-self.o[0][h])*sum([self.w[0][h][r]*self.e[1][r] for r in range(self.r)])
#             #modific ponderile dintre stratul de intrare si stratul ascuns
#             for i in range(self.i):
#                 self.wi[i][h]=self.wi[i][h]+eta*self.e[0][h]*xd[i]
#
#     def fit(self, trainData, trainOutput, eta):
#         self.n = len(trainData)
#         self.loss = 0
#         for xd, td in zip(trainData, trainOutput):
#             self.activate(xd)
#             self.softmax(td)
#             self.backpropagation(xd, td, eta)
#
#     def get_loss(self):
#         return self.loss / self.n
#
#     def predictOneSample(self, sample):
#         computedValues = []
#         for h in range(self.h):
#             computedValue = sum([self.w1[i][h] * sample[i] for i in range(self.i)])
#             computedSigValue = sigmoid(computedValue)
#             computedValues.append(computedSigValue)
#         finalValue = max(computedValues)
#         if (finalValue < 0.33): return [1, 0, 0]
#         if (finalValue >= 0.33 and finalValue < 0.66):
#             return [0, 1, 0]
#         else:
#             return [0, 0, 1]
#
#     def predict(self, testInputs):
#         pass
#         #return [self.predictOneSample(sample) for sample in testInputs]
