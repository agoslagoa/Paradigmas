import Point
import Quality
import City
import Link
import Tunel
import Region 

chubut = newRegion [radaTilly, comodoro, rawson, puertoMadryn] [linkRadaComodoro, linkComodoroRawson, linkRawsonPuertoMadryn] [tunelRadaComodoro, tunelRadaRawson, tunelRawsonPuertoMadryn]

ubicacionRadaTilly = newPoint 1 0
ubicacionComodoro = newPoint 7 2 
ubicacionRawson = newPoint 10 5
ubicacionPuertoMadryn = newPoint 12 4

radaTilly = newCity "Rada Tilly" ubicacionRadaTilly
comodoro = newCity "Comodoro Rivadavia" ubicacionComodoro
rawson = newCity "Rawson" ubicacionRawson
puertoMadryn = newCity "Puerto Madryn" ubicacionPuertoMadryn

calidadRadaComodoro = newQuality "cobre" 2 35.25878 --delay en microsegundos
calidadComodoroRawson = newQuality "fibra" 5 40.25878 --delay en microsegundos

linkRadaComodoro = newLink radaTilly comodoro calidadRadaComodoro
linkComodoroRawson = newLink comodoro rawson calidadComodoroRawson
linkRawsonPuertoMadryn = newLink rawson puertoMadryn calidadComodoroRawson

tunelRadaComodoro = newTunnel [linkRadaComodoro]
tunelRadaRawson = newTunnel [linkRadaComodoro, linkComodoroRawson] --asumimos que esta lista está ordenada
tunelRawsonPuertoMadryn = newTunnel [linkComodoroRawson, linkRawsonPuertoMadryn]

testing = [ linkIncludesCity radaTilly linkRadaComodoro == True, 
      linksCities radaTilly comodoro linkRadaComodoro == True, 
      linkCapacity linkRadaComodoro == 2, 
      linkDelay linkRadaComodoro == 35.25878,
      tunnelConnectsCities radaTilly rawson tunelRadaRawson == True,
      tunnelConnectsCities radaTilly comodoro tunelRadaRawson == False,
      tunnelThroughLink linkRadaComodoro tunelRadaRawson == True,
      tunnelThroughLink linkRawsonPuertoMadryn tunelRadaRawson == False,
      isInFirstLink radaTilly [linkRadaComodoro, linkComodoroRawson] == True,
      isInFirstLink radaTilly [linkComodoroRawson, linkRawsonPuertoMadryn] == False,
      isInLastLink puertoMadryn [linkComodoroRawson, linkRawsonPuertoMadryn] == True,
      isInLastLink rawson [linkComodoroRawson, linkRawsonPuertoMadryn] == False,
      tunnelDelay tunelRadaRawson == 75.51756,
      verifyConnectionByLink chubut radaTilly comodoro == True,
      verifyConnectionByLink chubut radaTilly puertoMadryn == False,
      calculateConnectionDelay chubut radaTilly comodoro == Just 35.25878,
      calculateConnectionDelay chubut radaTilly puertoMadryn == Nothing,
      availableCapacityForRegion chubut radaTilly comodoro == 2,
      availableCapacityForRegion chubut comodoro puertoMadryn == 0,
      True]
      
testConnectionByTunnel = [ verifyConnectionByTunnel chubut radaTilly rawson == True, 
                   verifyConnectionByTunnel chubut radaTilly puertoMadryn == False, 
                   verifyConnectionByTunnel chubut comodoro puertoMadryn == True, 
                   verifyConnectionByTunnel chubut rawson puertoMadryn == False,
                   True]