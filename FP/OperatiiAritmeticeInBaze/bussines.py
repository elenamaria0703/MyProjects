'''Proiect LC Elena Maria'''
class ServiceOperatii(object):
    
    
    def __init__(self, __conversii):
        self.__conversii = __conversii
    
    def  _Conversie(self,numar,bazaNr,baza):
        '''functia indica ce forma de conversie trbuie folosita
        in functie de baza numarului si baza destinatie'''
        baze = [4,8,16]
        if int(bazaNr) == 2 and int(baza) in baze:
            Numar = self.__conversii.conversiiRapide(numar,baza)
        elif baza == 10:
            Numar = self.__conversii.conversiiSubstitutie(numar,int(bazaNr))
        elif bazaNr == 10:
            Numar = self.__conversii.conversiiImpartiriRep(numar,baza)
        else:
            Numar = self.__conversii.conversiiBazaInter(numar,bazaNr,baza)
        return Numar 
    
    def Scadere(self,numar1,baza1,numar2,baza2,baza):
        '''scade doua numere initial in doua baze diferite 
        mai intai se aduc numerele in aceeasi baaza specificata'''
        rezultat = []
        Numar1 = self._Conversie(numar1, baza1, baza)
        Numar2 = self._Conversie(numar2, baza2, baza)
        rezultat.append(Numar1)
        rezultat.append(Numar2)
        if baza != 16:
            Numar1 = int(Numar1)
            Numar2 = int(Numar2)
            Numar = 0
            p = 1
            tr = 0
            while Numar1 != 0 and Numar2 != 0:
                cif1 = Numar1 %10
                cif2 = Numar2 %10
                Numar1 = Numar1 //10
                Numar2 = Numar2 //10
                if cif1 + tr >= cif2:
                    cif = cif1 + tr - cif2
                    tr = 0
                else:
                    cif = cif1 - tr - cif2 + baza
                    tr = 1
                Numar = cif*p + Numar
                p = p*10
            while Numar1 != 0:
                cif1 = Numar1%10
                if tr !=0 :
                    cif = cif1 - tr
                    tr = 0
                else:
                    cif = cif1
                Numar = cif*p + Numar
                p=p*10
                Numar1 = Numar1 //10
        else:
            baza16 = {"A":10,"B":11,"C":12,"D":13,"E":14,"F":15}
            tr = 0
            Numar = ''
            while Numar1 != '' and Numar2 != '':
                cif1 =  Numar1[len(Numar1)-1:]
                Numar1 = Numar1[:len(Numar1)-1]
                if cif1 in baza16:
                    cif1 = int(baza16[cif1])
                else:
                    cif1 = int(cif1)
                cif2 =  Numar2[len(Numar2)-1:]
                Numar2 = Numar2[:len(Numar2)-1]
                if cif2 in baza16:
                    cif2 = int(baza16[cif2])
                else:
                    cif2 = int(cif2)
                if cif1 + tr >= cif2:
                    cif = cif1 + tr - cif2
                    tr = 0
                else:
                    cif = cif1 - tr - cif2 + baza
                    tr = 1
                aux = ''
                for key in baza16:
                    if cif == baza16[key]:
                        aux = key
                if aux != '':
                    Numar = aux + Numar
                else:
                    Numar = str(cif) + Numar
            while Numar1 != '':
                cif1 =  Numar1[len(Numar1)-1:]
                Numar1 = Numar1[:len(Numar1)-1]
                if cif1 in baza16:
                    cif1 = int(baza16[cif1])
                else:
                    cif1 = int(cif1)
                if tr !=0 :
                    cif = cif1 - tr
                    tr = 0
                else:
                    cif = cif1
                aux = ''
                for key in baza16:
                    if cif == baza16[key]:
                        aux = key
                if aux != '':
                    Numar = aux + Numar
                else:
                    Numar = str(cif) + Numar
        rezultat.append(Numar)
        return rezultat
    def Adunare(self,numar1,baza1,numar2,baza2,baza):
        '''aduna doua numere initial in doua baze diferite 
        mai intai se aduc numerele in aceeasi baaza specificata'''
        rezultat = []
        Numar1 = self._Conversie(numar1, baza1, baza)
        Numar2 = self._Conversie(numar2, baza2, baza)
        rezultat.append(Numar1)
        rezultat.append(Numar2)
        if baza != 16:
            Numar1 = int(Numar1)
            Numar2 = int(Numar2)
            cat = 0
            Numar = 0
            p = 1
            while Numar1 != 0 and Numar2 != 0:
                cif1 = Numar1 %10
                cif2 = Numar2 %10
                Numar1 = Numar1 //10
                Numar2 = Numar2 //10
                sum = cif1 + cif2 + cat
                rez = self.__conversii.impartire(sum,baza)
                cat = rez[0]
                rest = rez[1]
                Numar = rest*p + Numar
                p = p*10
            while Numar1 != 0:
                cif = Numar1%10
                sum = cif + cat
                rez = self.__conversii.impartire(sum,baza)
                Numar = rez[1]* p + Numar
                cat = rez[0]
                p=p*10
                Numar1 = Numar1 //10
            while Numar2 != 0:
                cif = Numar2%10
                sum = cif + cat
                rez = self.__conversii.impartire(sum,baza)
                Numar = rez[1]* p + Numar
                cat = rez[0]
                p = p*10
                Numar2 = Numar2 //10  
            if cat != 0:
                Numar = cat* p + Numar
        else:
            baza16 = {"A":10,"B":11,"C":12,"D":13,"E":14,"F":15}
            cat = 0
            Numar = ''
            while Numar1 != '' and Numar2 != '':
                cif1 =  Numar1[len(Numar1)-1:]
                Numar1 = Numar1[:len(Numar1)-1]
                if cif1 in baza16:
                    cif1 = int(baza16[cif1])
                else:
                    cif1 = int(cif1)
                cif2 =  Numar2[len(Numar2)-1:]
                Numar2 = Numar2[:len(Numar2)-1]
                if cif2 in baza16:
                    cif2 = int(baza16[cif2])
                else:
                    cif2 = int(cif2)
                sum = cif1 + cif2 + cat
                rez = self.__conversii.impartire(sum,baza)
                cat = rez[0]
                rest = rez[1]
                aux = ''
                for key in baza16:
                    if rest == baza16[key]:
                        aux = key
                if aux != '':
                    Numar = aux + Numar
                else:
                    Numar = str(rest) + Numar
            while Numar1 != '':
                cif1 =  Numar1[len(Numar1)-1:]
                Numar1 = Numar1[:len(Numar1)-1]
                if cif1 in baza16:
                    cif1 = int(baza16[cif1])
                else:
                    cif1 = int(cif1)
                sum = cif1 + cat
                rez = self.__conversii.impartire(sum,baza)
                cat = rez[0]
                rest = rez[1]
                aux = ''
                for key in baza16:
                    if rest == baza16[key]:
                        aux = key
                if aux != '':
                    Numar = aux + Numar
                else:
                    Numar = str(rest) + Numar
            while Numar2 != '':
                cif2 =  Numar2[len(Numar2)-1:]
                Numar2 = Numar2[:len(Numar2)-1]
                if cif2 in baza16:
                    cif2 = int(baza16[cif2])
                else:
                    cif2 = int(cif2)
                sum = cif2 + cat
                rez = self.__conversii.impartire(sum,baza)
                cat = rez[0]
                rest = rez[1]
                aux = ''
                for key in baza16:
                    if rest == baza16[key]:
                        aux = key
                if aux != '':
                    Numar = aux + Numar
                else:
                    Numar = str(rest) + Numar
            if cat != 0:
                aux = ''
                for key in baza16:
                    if rest == baza16[key]:
                        aux = key
                if aux != '':
                    Numar = aux + Numar
                else:
                    Numar = str(rest) + Numar

        rezultat.append(Numar)       
        return rezultat
        
    def Impartire(self,numar,baza,cifra):
        '''efectueaza impartirea unui numar intr-o baza oarecare la o cifra'''
        listaCaturi = []
        rest = 0
        Numar = 0
        if int(baza)!=16:
            while numar != '':
                cif = int(numar[0])
                numar = numar[1:]
                nr = rest*int(baza)+cif
                rez = self.__conversii.impartire(nr,int(cifra))
                rest = rez[1]
                listaCaturi.append(rez)
            for l in listaCaturi:
                Numar = Numar*10 +l[0]
            rest = listaCaturi[len(listaCaturi)-1][1]
        else:
            baza16 = {"A":10,"B":11,"C":12,"D":13,"E":14,"F":15}
            while numar != '':
                cif = numar[0]
                numar = numar[1:]
                if cif in baza16:
                    cif = int(baza16[cif])
                else:
                    cif = int(cif)
                nr = rest*int(baza)+cif
                rez = self.__conversii.impartire(nr,int(cifra))
                rest = rez[1]
                listaCaturi.append(rez)
            Numar = ''
            for l in listaCaturi:
                aux = ''
                for key in baza16:
                    if l[0] == baza16[key]:
                        aux = key
                if aux != '':
                    Numar = Numar + aux
                else:
                    Numar = Numar + str(l[0])
            for i in Numar:
                if int(i) != 0:
                    break
                else:
                    Numar = Numar[1:]
            rest = listaCaturi[len(listaCaturi)-1][1]
        return Numar,rest
            
                
    def Inmultire(self,numar,baza,cifra):
        '''efectueaza inmultirea unui numar intr-o baza oarecare la o cifra'''
        Numar = 0
        puteri10 = 1
        cat = 0
        if int(baza)!=16:
            while numar != '':
                cif = int(numar[len(numar)-1:])
                numar = numar[:len(numar)-1]
                inmul = cif*int(cifra) + cat
                rez = self.__conversii.impartire(inmul,int(baza))
                cat = rez[0]
                rest = rez[1]
                Numar = rest * puteri10 + Numar
                puteri10 = puteri10*10
            if cat != 0:
                Numar = cat * puteri10 + Numar
        else:
            baza16 = {"A":10,"B":11,"C":12,"D":13,"E":14,"F":15}
            if cifra in baza16:
                cifra = baza16[cifra]
            Numar = ''
            while numar != '':
                cif =  numar[len(numar)-1:]
                numar = numar[:len(numar)-1]
                if cif in baza16:
                    cif = int(baza16[cif])
                else:
                    cif = int(cif)
                inmul = cif*int(cifra) + cat
                rez = self.__conversii.impartire(inmul,int(baza))
                cat = rez[0]
                rest = rez[1]
                for key in baza16:
                    if baza16[key] == rest:
                        rest = key
                Numar = str(rest) + Numar
            if cat != 0:
                Numar = str(cat)+Numar
        return Numar
 
    
class Conversii():
    
    def conversiiRapide(self,numar,baza):
        '''  ajuta la conversii intre bazele puteri ale lui 2'''

        Baza16 = {"0000":'0',"0001":'1',"0010":'2',"0011":'3',"0100":'4',"0101":'5',"0110":'6',"0111":'7',"1000":'8',"1001":'9',"1010":'A',"1011":'B',"1100":'C',"1101":'D',"1110":'E',"1111":'F'}
        Baza8 = {"000":0,"001":1,"010":2,"011":3,"100":4,"101":5,"110":6,"111":7}
        Baza4 = {"00":0,"01":1,"10":2,"11":3}
      
        if baza == 4:
            p = 1
            Numar = 0
            while numar != '':
                cif =  numar[len(numar)-2:]
                numar = numar[:len(numar)-2]
                Numar = Baza4[cif]*p + Numar
                p = p *10
            return Numar
        elif baza == 8:
            p = 1
            Numar = 0
            while numar != '':
                cif =  numar[len(numar)-3:]
                numar = numar[:len(numar)-3]
                Numar = Baza8[cif]*p + Numar
                p = p *10
            return Numar
        elif baza == 16:
            Numar = ''
            while numar != '':
                cif =  numar[len(numar)-4:]
                numar = numar[:len(numar)-4]
                Numar = Baza16[cif] + Numar
            return Numar
        
    def conversiiImpartiriRep(self,numar,baza):
        '''impartim succesiv la baza in care se opereaza
        numarul este format din resturile impartirilor in ordine inversa '''
        if baza != 16:
            Numar = 0
            p = 1
            rez = self.impartire(int(numar),baza)
            Numar = rez[1]*p +Numar
            cat = rez[0]
            p = p*10
            while cat != 0:
                rez = self.impartire(cat,baza)
                Numar = rez[1]*p +Numar
                cat = rez[0]
                p = p*10
            return Numar
        else:
            Numar = ''
            baza16 = {"A":10,"B":11,"C":12,"D":13,"E":14,"F":15}
            rest = ''
            rez = self.impartire(int(numar),baza)
            for key in baza16:
                if baza16[key] == rez[1]:
                    rest = key
            if rest != '':
                Numar = str(rest) + Numar
            else:
                Numar = str(rez[1]) + Numar
            cat = rez[0]
            while cat != 0:
                rest = ''
                rez = self.impartire(cat,baza)
                for key in baza16:
                    if baza16[key] == rez[1]:
                        rest = key
                if rest != '':
                    Numar = str(rest) + Numar
                else:
                    Numar = str(rez[1]) + Numar
                cat = rez[0]
            return Numar
            
    def conversiiSubstitutie(self,numar,baza):
        '''ajuta la trecera dintr-o baza mai mica intr-una mai mare
        mai exact dintr-o baza diferita de 10 in baza 10 '''
        if baza != 16:
            Numar = 0
            pow = 0
            numar = int(numar)
            while numar != 0:
                cif = numar%10
                Numar = Numar + self.power(baza,pow)*cif
                pow += 1
                numar = numar//10
            return Numar
        else:
            baza16 = {"A":10,"B":11,"C":12,"D":13,"E":14,"F":15}
            Numar = 0
            pow = 0 
            while numar != '':
                cif = numar[len(numar)-1:]
                numar = numar[:len(numar)-1]
                if cif in baza16:
                    cif = int(baza16[cif])
                else:
                    cif = int(cif)
                Numar = Numar + self.power(baza,pow)*cif
                pow += 1
            return Numar
        
    def conversiiBazaInter(self,numar,bazaNr,baza):
        ''' folosim baza intermediara 10
        din baza initiala trecem numarul in baza 10
        prin impartiri succesive la baza in care se opereaza'''
        Numar = self.conversiiSubstitutie(numar,int(bazaNr))
        return self.conversiiImpartiriRep(Numar, baza)
    
    
    def impartire(self,nr,baza):
        '''impartirea uzuaala a doua numere in baza 10'''
        cat = nr//baza
        rest = nr - cat*baza
        return cat,rest
    
    def power(self,numar,putere):
        '''ridica un nr la o putere'''
        if putere == 0:
            return 1
        if putere == 1:
            return numar
        mij = putere//2
        pow = self.power(numar,mij)
        if putere%2 == 0:
            return pow*pow
        else:
            return pow*pow*numar