import unittest
from model.Entities import Player
from repo.Repository import FileRepository
from bussines.Controllers import ServiceBaschet
from valid.Validators import ValidatorPlayer
from error.Errors import ValidErrors

class TestCaseClassPlayer(unittest.TestCase):
    def setUp(self):
        self.player1 = Player("marian","pop",190,"Fundas")
        self.player2 = Player("andrei","carol",200,"Pivot")
    
    def testGetNume(self):
        self.assertTrue(self.player1.get_nume() == "marian")
        self.assertTrue(self.player2.get_nume() == "andrei")
    def testGetPrenume(self):
        self.assertTrue(self.player1.get_prenume() == "pop")
        self.assertTrue(self.player2.get_prenume() == "carol")
    def testGetInaltime(self):
        self.assertTrue(self.player1.get_inaltime() == 190)
        self.assertTrue(self.player2.get_inaltime() == 200)
    def testGetPost(self):
        self.assertTrue(self.player1.get_post() == "Fundas")
        self.assertTrue(self.player2.get_post() == "Pivot")
        
class TestCaseClassRepository(unittest.TestCase):
    def setUp(self):
        self.repo = FileRepository("C:\\Users\\Maria\\eclipse-workspace\\Practic_Elena_Maria\\test.txt")
        
        self.player1 = Player("marian","pop",190,"Fundas")
        self.player2 = Player("andrei","carol",200,"Pivot")
    
    def testStore(self):
        self.assertTrue(len(self.repo.getAll()) == 0)
        self.repo.store(self.player1)
        self.assertTrue(len(self.repo.getAll()) == 1)
        self.repo.store(self.player2)
        self.assertTrue(len(self.repo.getAll()) == 2)

    def testUpdate(self):     
        player3 = Player("marian","pop",140,None)
        player4 = Player("andrei","carol",180,None)
        self.repo.update(player3)
        self.repo.update(player4)
        players = self.repo.getAll()
        for player in players:
            if player == player3:
                self.assertTrue(player.get_inaltime() == 140)
            if player == player4:
                self.assertTrue(player.get_inaltime() == 180)

class TestCaseClassService(unittest.TestCase):
    def setUp(self):
        self.repo = FileRepository("C:\\Users\\Maria\\eclipse-workspace\\Practic_Elena_Maria\\test.txt")
        self.valid = ValidatorPlayer()
        self.service = ServiceBaschet(self.repo,self.valid)
        self.player5 = Player("marian","pop",189,"Fundas")
        self.player6 = Player("george","judea",230,"Fundas")
        self.player7 = Player("marian","pop",199,"Extrema")
        self.player8 = Player("george","judea",230,"Extrema")
        
        
    def testAddPlayer(self):
        self.assertRaises(ValidErrors,self.service.AddPlayer,"","pop",189,"Fundas")
        self.assertRaises(ValidErrors,self.service.AddPlayer,"george","judea",-23,"Pivot")
        self.assertRaises(ValidErrors,self.service.AddPlayer,"george","judea",199,"fundas")
    
    def testDeterminareMax(self):
        listPlayers = [self.player5,self.player6,self.player7]
        listmax = self.service.determinareInaltimiMax(listPlayers)
        self.assertTrue(listmax[0]==self.player6)
        self.assertTrue(listmax[1] == self.player7)
    
    def testImportFile(self):
        numar = self.service.ImportFile("C:\\Users\\Maria\\eclipse-workspace\\Practic_Elena_Maria\\importTest.txt")
        self.assertTrue(numar == 2)
        