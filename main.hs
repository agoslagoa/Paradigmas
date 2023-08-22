import Point
import Quality
import City
import Link
import Tunel
import Region 

chubut = newRegion [radaTilly, comodoro, rawson, puertoMadryn] [linkRadaComodoro, linkComodoroRawson, linkRawsonPuertoMadryn] [tunnelRadaComodoro, tunnelRadaRawson, tunnelRawsonPuertoMadryn]

radaTillyLocation = newPoint 1 0
comodoroLocation = newPoint 7 2 
rawsonLocation = newPoint 10 5
puertoMadrynLocation = newPoint 12 4

radaTilly = newCity "Rada Tilly" radaTillyLocation
comodoro = newCity "Comodoro Rivadavia" comodoroLocation
rawson = newCity "Rawson" rawsonLocation
puertoMadryn = newCity "Puerto Madryn" puertoMadrynLocation

radaComodoroQuality = newQuality "cobre" 3 35.25878 --delay en microsegundos
comodoroRawsonQuality = newQuality "fibra" 6 40.25878 --delay en microsegundos

linkRadaComodoro = newLink radaTilly comodoro radaComodoroQuality
linkComodoroRawson = newLink comodoro rawson comodoroRawsonQuality
linkRawsonPuertoMadryn = newLink rawson puertoMadryn comodoroRawsonQuality

tunnelRadaComodoro = newTunnel [linkRadaComodoro]
tunnelRadaRawson = newTunnel [linkRadaComodoro, linkComodoroRawson] 
tunnelRawsonPuertoMadryn = newTunnel [linkComodoroRawson, linkRawsonPuertoMadryn]

testRegionConnection = [
                  availableCapacityForRegion chubut radaTilly comodoro == 1,
                  availableCapacityForRegion chubut comodoro rawson == 4,
                  availableCapacityForRegion chubut comodoro puertoMadryn == 0,
                  calculateConnectionDelay chubut radaTilly comodoro == Just 35.25878,
                  calculateConnectionDelay chubut radaTilly puertoMadryn == Nothing,
                  verifyConnectionByLink chubut radaTilly comodoro == True,
                  verifyConnectionByLink chubut radaTilly puertoMadryn == False,
                  verifyConnectionByTunnel chubut radaTilly rawson == True, 
                  verifyConnectionByTunnel chubut radaTilly puertoMadryn == False, 
                  verifyConnectionByTunnel chubut comodoro puertoMadryn == True, 
                  verifyConnectionByTunnel chubut rawson puertoMadryn == False,
                  True
                  ]

testConnectionByLink = [
                  linkCapacity linkRadaComodoro == 3, 
                  linkDelay linkRadaComodoro == 35.25878,
                  linkIncludesCity radaTilly linkRadaComodoro == True, 
                  linksCities radaTilly comodoro linkRadaComodoro == True, 
                  isInFirstLink radaTilly [linkRadaComodoro, linkComodoroRawson] == True,
                  isInFirstLink radaTilly [linkComodoroRawson, linkRawsonPuertoMadryn] == False,
                  isInLastLink puertoMadryn [linkComodoroRawson, linkRawsonPuertoMadryn] == True,
                  isInLastLink rawson [linkComodoroRawson, linkRawsonPuertoMadryn] == False,
                  True
                  ]

testConnectionByTunnel = [ 
                  tunnelDelay tunnelRadaRawson == 75.51756,
                  tunnelConnectsCities radaTilly rawson tunnelRadaRawson == True,
                  tunnelConnectsCities radaTilly comodoro tunnelRadaRawson == False,
                  tunnelThroughLink linkRadaComodoro tunnelRadaRawson == True,
                  tunnelThroughLink linkRawsonPuertoMadryn tunnelRadaRawson == False,
                  True
                  ]

testCities = [ 
                  cityName radaTilly == "Rada Tilly", 
                  cityName comodoro == "Comodoro Rivadavia", 
                  cityCoordinates radaTilly == newPoint 1 0,
                  cityCoordinates comodoro == newPoint 7 2,
                  distanceBetweenCities radaTilly comodoro == 6.3245554,
                  qualityTunnelCapacity radaComodoroQuality == 3,
                  True
                  ]