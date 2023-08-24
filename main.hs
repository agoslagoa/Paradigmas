import Point
import Quality
import City
import Link
import Tunel
import Region


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
                                rawson puertoMadryn comodoroRawsonQuality) comodoro rawson comodoroRawsonQuality)
                            radaTilly comodoro radaComodoroQuality) [radaTilly, comodoro]) [radaTilly, comodoro, rawson]) [comodoro, rawson, puertoMadryn]

testRegionConnection = [
                  availableCapacityForRegion chubut radaTilly comodoro == 1,
                  availableCapacityForRegion chubut comodoro rawson == 4,
                  availableCapacityForRegion chubut comodoro puertoMadryn == 0,
                  calculateConnectionDelay chubut radaTilly comodoro == 0.17937532,
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
                  linkDelay linkRadaComodoro == 0.17937532,
                  linkIncludesCity radaTilly linkRadaComodoro,
                  linksCities radaTilly comodoro linkRadaComodoro,
                --   isInFirstLink radaTilly [linkRadaComodoro, linkComodoroRawson],
                --   not (isInFirstLink radaTilly [linkComodoroRawson, linkRawsonPuertoMadryn]),
                --   isInLastLink puertoMadryn [linkComodoroRawson, linkRawsonPuertoMadryn],
                --   not (isInLastLink rawson [linkComodoroRawson, linkRawsonPuertoMadryn]),
                  True
                  ]

testConnectionByTunnel = [
                  tunnelDelay tunnelRadaRawson == 0.28475955,
                  tunnelConnectsCities radaTilly rawson tunnelRadaRawson,
                  not (tunnelConnectsCities radaTilly comodoro tunnelRadaRawson),
                  tunnelThroughLink linkRadaComodoro tunnelRadaRawson,
                  not (tunnelThroughLink linkRawsonPuertoMadryn tunnelRadaRawson),
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

main :: IO ()
main = do
    putStrLn "Running Tests..."
    putStrLn $ "Region Connection Tests: " ++ show (and testRegionConnection)
    putStrLn $ "Connection by link Tests: " ++ show (and testConnectionByLink)
    putStrLn $ "Connection by tunnel Tests: " ++ show (and testConnectionByTunnel)
    putStrLn $ "Cities Tests: " ++ show (and testCities)
    putStrLn "All tests finished."