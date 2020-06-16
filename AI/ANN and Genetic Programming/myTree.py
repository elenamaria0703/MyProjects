class Node:
    def __init__(self,data):
        self.right=None;
        self.left=None;
        self.data=data;
    def setRight(self,data):
        self.right=data;
    def setLeft(self,data):
        self.left=data;
    def get_data(self):
        return self.data;
    def set_data(self,data):
        self.data=data;
    def get_right(self):
        return self.right;
    def get_left(self):
        return self.left;

import random


class Tree:
    def __init__(self):
        self.operations=['+','-','*','/']
        self.features=[];
        pass;
    def generate(self,features):
        r=random.randint(0,3);
        data=self.operations[r];
        root=Node(data);
        k=0;
        self.__setFeatures(features);
        return self.createTree(root,k);

    def __setFeatures(self,features):
        self.features=features;

    def createTree(self,tree,k):
         r1 = random.randint(0, len(self.features)-1);
         r2 = random.randint(0, len(self.features) - 1);
         if(k==1):
             tree.setRight(Node(self.features[r1]));
             tree.setLeft(Node(self.features[r2]));
             return tree;
         r = random.randint(0, 3);
         data = self.operations[r];
         tree.setRight(self.createTree(Node(data),k+1));
         r = random.randint(0, 3);
         data = self.operations[r];
         tree.setLeft(self.createTree(Node(data), k + 1));
         return tree;

    def inorder(self,tree,str):
        if(tree==None):
            return;
        rigth=self.inorder(tree.get_right(),str);
        if(rigth!=None):
            str.append(rigth);
        str.append(tree.get_data());
        left = self.inorder(tree.get_left(), str);
        if (left != None):
            str.append(left);

    def value(self,d1, d2, op):
        if (op == '+'):
            return d1 + d2;
        if (op == '-'):
            return d1 - d2;
        if (op == '*'):
            return d1 * d2;
        if (op == '/'):
            return d1 / d2;

    def eval(self,tree):
        if(tree.get_right()==None and tree.get_left()==None):
            return tree.get_data();
        rigth=self.eval(tree.get_right());
        left = self.eval(tree.get_left());
        return self.value(rigth,left,tree.get_data());

    def getSubTreeRight(self,tree,k):
        if(k==0):
            return tree;
        else:
            return self.getSubTreeRight(tree.get_right(),k-1);

    def setSubTreeRight(self,tree,k,subtree):
        if(k==0):
            return subtree;
        else:
            tree.setRight(self.setSubTreeRight(tree,k-1,subtree))
            return tree;

    def setDataTree(self,tree,features):
        if(tree.get_right()==None and tree.get_left()==None):
            tree.set_data(features[tree.get_data()]);
        else:
            self.setDataTree(tree.get_right(),features);
            self.setDataTree(tree.get_left(),features);

    def copy(self,tree):
        left=None;
        right=None;
        if(tree.get_left()!=None):
            left=self.copy(tree.get_left())
        if (tree.get_right() != None):
            right= self.copy(tree.get_right())
        n=Node(tree.get_data())
        n.setRight(right)
        n.setLeft(left)
        return n