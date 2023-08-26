import Point
import Quality
import City
import Link
import Tunel
import Region


radaTillyLocation = newPoint 67 45
comodoroLocation = newPoint 45 67
rawsonLocation = newPoint 65 43
puertoMadrynLocation = newPoint 42 65

radaTilly = newCity "Rada Tilly" radaTillyLocation
comodoro = newCity "Comodoro Rivadavia" comodoroLocation
rawson = newCity "Rawson" rawsonLocation
puertoMadryn = newCity "Puerto Madryn" puertoMadrynLocation

cobreQuality = newQuality "cobre" 3 35.25878 --delay en microsegundos
fibraQuality = newQuality "fibra" 6 10.00157 --delay en microsegundos

linkRadaComodoro = newLink radaTilly comodoro cobreQuality
linkComodoroRawson = newLink comodoro rawson fibraQuality
linkRawsonPuertoMadryn = newLink rawson puertoMadryn fibraQuality

tunnelRadaComodoro = newTunnel [linkRadaComodoro]
tunnelRadaRawson = newTunnel [linkRadaComodoro, linkComodoroRawson]
tunnelRawsonPuertoMadryn = newTunnel [linkComodoroRawson, linkRawsonPuertoMadryn]

chubut = createTunnelBetweenCities
            (createTunnelBetweenCities
                (createTunnelBetweenCities
                    (createLinkBetweenCities
                        (createLinkBetweenCities
                            (createLinkBetweenCities
                                (addCityToRegion
                                    (addCityToRegion
                                        (addCityToRegion
                                            (addCityToRegion newRegion puertoMadryn) rawson) comodoro) radaTilly)
                            rawson puertoMadryn fibraQuality)
                        comodoro rawson fibraQuality)
                    radaTilly comodoro cobreQuality)
                [radaTilly, comodoro])
            [radaTilly, comodoro, rawson])
        [comodoro, rawson, puertoMadryn]


testRegionConnection = [
                  distanceBetweenCities radaTilly comodoro == 31.112698,
                  availableCapacityForRegion chubut radaTilly comodoro == 1,
                  availableCapacityForRegion chubut comodoro rawson == 4,
                  availableCapacityForRegion chubut comodoro puertoMadryn == 0,
                  calculateConnectionDelay chubut radaTilly comodoro == 0.8824099,
                  calculateConnectionDelay chubut radaTilly puertoMadryn == 0.0,
                  verifyConnectionByLink chubut radaTilly comodoro,
                  not (verifyConnectionByLink chubut radaTilly puertoMadryn),
                  verifyConnectionByTunnel chubut radaTilly rawson,
                  not (verifyConnectionByTunnel chubut radaTilly puertoMadryn),
                  verifyConnectionByTunnel chubut comodoro puertoMadryn,
                  not (verifyConnectionByTunnel chubut rawson puertoMadryn),
                  True
                  ]

testConnectionByLink = [
                  linkCapacity linkRadaComodoro == 3,
                  linkDelay linkRadaComodoro == 0.8824099,
                  linkIncludesCity radaTilly linkRadaComodoro,
                  linksCities radaTilly comodoro linkRadaComodoro,
                  True
                  ]

testConnectionByTunnel = [
                  tunnelDelay tunnelRadaRawson == 4.0060196,
                  tunnelConnectsCities radaTilly comodoro tunnelRadaComodoro, -- True pues las ciudades especificadas son los extremos del túnel
                  tunnelConnectsCities radaTilly rawson tunnelRadaRawson,
                  not (tunnelConnectsCities radaTilly comodoro tunnelRadaRawson), -- False pues las ciudades especificadas no son los extremos del túnel
                  tunnelThroughLink linkRadaComodoro tunnelRadaRawson,
                  not (tunnelThroughLink linkRawsonPuertoMadryn tunnelRadaRawson),
                  True
                  ]

main :: IO ()
main = do
    putStrLn "Running Tests..."
    putStrLn $ "Region Connection Tests: " ++ show (and testRegionConnection)
    putStrLn $ "Connection by link Tests: " ++ show (and testConnectionByLink)
    putStrLn $ "Connection by tunnel Tests: " ++ show (and testConnectionByTunnel)
    putStrLn "All tests finished."