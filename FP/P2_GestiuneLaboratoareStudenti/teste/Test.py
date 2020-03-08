import unittest
from model.Entities import Student,ProblLab,AsignProblNota
from valid.Validators import StudentiValidator,ProblLabValidator
from errors.Errors import ValidErrors,RepoErrors
from repo.FileRepositoies import FileRepositoryStudents
from business.Controllers import StudentiService,ProblLabService,AsignareService

class TestCaseClassStudent(unittest.TestCase):
    def setUp(self):
        self.student1 = Student(12,"maria",215)
        self.student2 = Student(23,"tudor",314)
    
    def testGetId(self):
        self.assertTrue(self.student1.get_idstud() == 12)
        self.assertTrue(self.student2.get_idstud() == 23)
    
    def testGetName(self):
        self.assertTrue(self.student1.get_nume() == "maria")
        self.assertTrue(self.student2.get_nume() == "tudor")
    def testGetGrupa(self):
        self.assertTrue(self.student1.get_grupa() == 215)
        self.assertTrue(self.student2.get_grupa() == 314)

class TestCaseClassProblLab(unittest.TestCase):
    def setUp(self):
        self.problLab1 = ProblLab("6_3","info","12.03.2019")    
        self.problLab2 = ProblLab("2_3","mate","08.02.2019")
        
    def testGetProblLab(self):
        self.assertTrue(self.problLab1.get_nr_lab_nr_probl() == "6_3")    
        self.assertTrue(self.problLab2.get_nr_lab_nr_probl() == "2_3")
        
    def testGetDescriere(self):
        self.assertTrue(self.problLab1.get_descriere() == "info")
        self.assertTrue(self.problLab2.get_descriere() == "mate")
    
    def testGetDeadline(self):
        self.assertTrue(self.problLab1.get_deadline() == "12.03.2019")
        self.assertTrue(self.problLab2.get_deadline() == "08.02.2019")
        
class TestCaseClassStudentFileRepository(unittest.TestCase):
    def setUp(self):
        self.student1 = Student(12,"maria",215)
        self.student2 = Student(23,"tudor",314)
        
        self.studentsRepo = FileRepositoryStudents("C:\\Users\\Maria\\eclipse-workspace\\P2_GestiuneLaboratoareStudenti.zip_expanded\\P2_GestiuneLaboratoareStudenti\\teste\\studentFileTest.txt")
        
    def tearDown(self):
        unittest.TestCase.tearDown(self)
    
    def testAddStudent(self):
        self.assertTrue(len(self.studentsRepo.getAll()) == 0)
        self.studentsRepo.add(self.student1)
        self.assertTrue(len(self.studentsRepo.getAll()) == 1)
        self.studentsRepo.add(self.student2)
        self.assertTrue(len(self.studentsRepo.getAll()) == 2)
        
class TestCaseStudentServicee(unittest.TestCase):
    def setUp(self): 
        self.studentsRepo = FileRepositoryStudents("C:\\Users\\Maria\\eclipse-workspace\\P2_GestiuneLaboratoareStudenti.zip_expanded\\P2_GestiuneLaboratoareStudenti\\teste\\studentFileTest.txt")
        self.validator = StudentiValidator()
        self.service = StudentiService(self.studentsRepo,self.validator)
        
        self.service.AddStudent(29,"elena",213)
        self.service.AddStudent(31,"patric",711)
        
      
    def tearDown(self):
        unittest.TestCase.tearDown(self)
    
    def testUpdateStudent(self):
        self.service.UpdateStudent(31,"raluca", 314)
        self.assertTrue(self.studentsRepo.search(31).get_grupa() == 314)
        
        self.assertRaises(RepoErrors, self.service.UpdateStudent,5,"elogia",213)
        self.assertRaises(ValidErrors,self.service.UpdateStudent,-41,"ilie",112)
        self.assertRaises(ValueError,self.service.UpdateStudent,89,"234opi",513)
        
        students = self.studentsRepo.getAll()
        self.service.quick_sort(students, 0, len(students)-1)
        self.assertTrue(students[0].get_nume() == "tudor")
        self.assertTrue(students[1].get_nume() == "maria")
        self.assertTrue(students[2].get_nume() == "raluca")
        self.assertTrue(students[3].get_nume() == "tudor")
        
        students1 = self.studentsRepo.getAll()
        studentsSorted = self.service.gnomeSort(students1, len(students1))
        self.assertTrue(studentsSorted == students)
    
if __name__ == '__main__':
    unittest.main()