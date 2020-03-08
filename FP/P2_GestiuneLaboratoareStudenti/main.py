from repo.Repository import RepositoryStudents,RepositoryProbls,RepositoryAsign
from repo.FileRepositoies import FileRepositoryStudents,FileRepositoryProbls,FileRepositoryAsign
from valid.Validators import StudentiValidator, ProblLabValidator,AsignareValidator
from business.Controllers import StudentiService, ProblLabService,AsignareService
from ui.Console import Console
from business import Controllers


#repoStudents = RepositoryStudents()
#repoProbls = RepositoryProbls()
repoStudents = FileRepositoryStudents("C:\\Users\\Maria\\eclipse-workspace\\P2_GestiuneLaboratoareStudenti.zip_expanded\\P2_GestiuneLaboratoareStudenti\\studentFile.txt")
repoProblLab = FileRepositoryProbls("C:\\Users\\Maria\\eclipse-workspace\\P2_GestiuneLaboratoareStudenti.zip_expanded\\P2_GestiuneLaboratoareStudenti\\problsFile.txt")
#repoAsignare = RepositoryAsign()
repoAsignare = FileRepositoryAsign("C:\\Users\\Maria\\eclipse-workspace\\P2_GestiuneLaboratoareStudenti.zip_expanded\\P2_GestiuneLaboratoareStudenti\\asignareFile.txt")

validatorStudenti = StudentiValidator()
validatorProblLab = ProblLabValidator()
validatorAsignare = AsignareValidator()
serviceProblLab = ProblLabService(repoProblLab,validatorProblLab)
serviceStudenti = StudentiService(repoStudents,validatorStudenti)
serviceAsignare = AsignareService(repoStudents,repoProblLab,repoAsignare,validatorAsignare)
console = Console(serviceStudenti,serviceProblLab,serviceAsignare)
#serviceStudenti.RandomStudent()
#serviceProblLab.RandomProblLab()
console.run()
