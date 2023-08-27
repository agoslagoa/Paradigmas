module Region ( Region, newRegion, addCityToRegion, createLinkBetweenCities, createTunnelBetweenCities, verifyConnectionByTunnel, verifyConnectionByLink, calculateConnectionDelay, availableCapacityForRegion)
   where
import City
import Link
import Quality
import Tunel
import Data.List
import Data.Maybe

data Region = Reg [City] [Link] [Tunel] deriving (Show)

newRegion :: Region
newRegion = Reg [] [] []

addCityToRegion :: Region -> City -> Region -- agrega una nueva ciudad a la región
addCityToRegion (Reg cities links tunnels) newCity
    | coordinatesInUse newCity cities = error "No es posible crear dos ciudades con la misma ubicación"
    | cityExists newCity cities = error "No es posible crear dos ciudades con el mismo nombre"
    | otherwise = Reg (newCity : cities) links tunnels
  where
    cityExists :: City -> [City] -> Bool
    cityExists _ [] = False
    cityExists city (c : cs) = (cityName city == cityName c) || cityExists city cs

    coordinatesInUse :: City -> [City] -> Bool
    coordinatesInUse _ [] = False
    coordinatesInUse city (c : cs) = distanceBetweenCities city c == 0.0 || coordinatesInUse city cs

createLinkBetweenCities :: Region -> City -> City -> Quality -> Region -- enlaza dos ciudades de la región con un enlace de la calidad indicada
createLinkBetweenCities (Reg ciudades links tuneles) cityA cityB quality
    | cityA `elem` ciudades && cityB `elem` ciudades = Reg ciudades newLinks tuneles
    | cityA == cityB = error "No es posible crear un enlace entre la misma ciudad"
    | otherwise = Reg ciudades links tuneles
  where
    link = newLink cityA cityB quality
    newLinks = if link `elem` links then links else link : links

createTunnelBetweenCities :: Region -> [City] -> Region
createTunnelBetweenCities region [] = error "No es posible crear un túnel si no hay ciudades en la región"
createTunnelBetweenCities region [_] = error "No es posible crear un túnel con una sola ciudad"
createTunnelBetweenCities region cities@(cityA:cityB:rest)
    | not (all areLinked pairsOfCities) = error "No es posible crear un túnel entre ciudades no linkeadas"
    | not (all canCreateTunnel validLinks) = error "No hay suficiente capacidad en algún enlace para crear el túnel"
    | otherwise = updatedRegion
  where
    pairsOfCities = zip cities (tail cities)
    areLinked (c1, c2) = isJust $ findLink region c1 c2
    validLinks = mapMaybe (uncurry $ findLink region) pairsOfCities
    canCreateTunnel link = availableCapacityForLink (tunnels region) link > 0
    tunnel = newTunnel validLinks
    updatedRegion = addTunnelsToRegion region [tunnel]


addTunnelsToRegion :: Region -> [Tunel] -> Region -- (auxiliar)
addTunnelsToRegion (Reg cities links existingTunnels) tunnelsToAdd =
    Reg cities links updatedTunnels
  where
    newTunnels = filter (`notElem` existingTunnels) tunnelsToAdd
    updatedTunnels = existingTunnels ++ newTunnels

findLink :: Region -> City -> City -> Maybe Link -- (auxiliar)
findLink (Reg _ links _) cityA cityB = find (linksCities cityA cityB) links

verifyConnectionByTunnel :: Region -> City -> City -> Bool -- indica si estas dos ciudades están conectadas por un tunel
verifyConnectionByTunnel (Reg _ _ tunnels) cityA cityB =
    any (tunnelConnectsCities cityA cityB) tunnels

verifyConnectionByLink :: Region -> City -> City -> Bool -- indica si estas dos ciudades estan enlazadas
verifyConnectionByLink (Reg _ links _) cityA cityB = any (linksCities cityA cityB) links

calculateConnectionDelay :: Region -> City -> City -> Float -- dadas dos ciudades conectadas, indica la demora
calculateConnectionDelay (Reg _ links _) startCity endCity
  | startCity == endCity = 0.0
  | otherwise = case [linkDelay link | link <- links, linkConnectsCities link] of
                  [] -> 0.0
                  delays -> minimum delays
  where
    linkConnectsCities :: Link -> Bool
    linkConnectsCities link = linkIncludesCity startCity link && linkIncludesCity endCity link

usedCapacity :: [Tunel] -> Link -> Int -- (auxiliar)
usedCapacity tunnels link = length [t | t <- tunnels, tunnelThroughLink link t]

availableCapacityForLink :: [Tunel] -> Link -> Int -- (auxiliar)
availableCapacityForLink tunnels link = linkCapacity link - usedCapacity tunnels link

availableCapacityForRegion :: Region -> City -> City -> Int -- indica la capacidad disponible entre dos ciudades
availableCapacityForRegion (Reg _ links tunnels) cityA cityB =
    sum [availableCapacityForLink tunnels link | link <- links, linksCities cityA cityB link]
