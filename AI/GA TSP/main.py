from service.GAService import Service
from repository.CityRepository import FileRepository

fileString="C:\\Users\\Maria\\eclipse-workspace\\Lab4_AI\\"

file=input(">>")
fileString+=file
repo=FileRepository(fileString)
service=Service(repo)
service.applyGA()

