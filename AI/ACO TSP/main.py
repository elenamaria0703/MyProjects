from ServiceACO import Service
from DynamicServiceACO import ServiceDynamic


fileString="C:\\Users\\Maria\\eclipse-workspace\\Lab5_AI\\"

file=input(">>")
fileString+=file
#service=Service(fileString)
service=ServiceDynamic(fileString)
service.solve()
