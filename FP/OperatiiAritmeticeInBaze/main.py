'''Proiect LC Elena Maria'''
from ui.console import Console
from bussines import ServiceOperatii
from bussines import Conversii

conversii = Conversii()
serviceOperatii = ServiceOperatii(conversii)
c = Console(serviceOperatii)
c.run()