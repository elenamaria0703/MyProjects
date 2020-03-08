from ui.Console import Console
from repo.Repository import FileRepository
from valid.Validators import ValidatorPlayer
from bussines.Controllers import ServiceBaschet
repoBaschet = FileRepository("C:\\Users\\Maria\\eclipse-workspace\\Practic_Elena_Maria\\repoFile.txt")

validBaschet = ValidatorPlayer()
serviceBaschet = ServiceBaschet(repoBaschet,validBaschet)
c = Console(serviceBaschet)
c.run()