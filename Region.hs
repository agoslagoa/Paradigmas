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
    | cityExists newCity cities || coordinatesInUse newCity cities =
        Reg cities links tunnels
    | otherwise =
        Reg (newCity : cities) links tunnels
  where
    cityExists :: City -> [City] -> Bool -- (auxiliar)
    cityExists _ [] = False
    cityExists city (c : cs) = (cityName city == cityName c) || cityExists city cs

    coordinatesInUse :: City -> [City] -> Bool -- (auxiliar)
    coordinatesInUse _ [] = False
    coordinatesInUse city (c : cs) = distanceBetweenCities city c == 0.0 || coordinatesInUse city cs

createLinkBetweenCities :: Region -> City -> City -> Quality -> Region -- enlaza dos ciudades de la región con un enlace de la calidad indicada
createLinkBetweenCities (Reg ciudades links tuneles) cityA cityB quality
    | cityA `elem` ciudades && cityB `elem` ciudades = Reg ciudades newLinks tuneles
    | otherwise = Reg ciudades links tuneles
  where
    link = newLink cityA cityB quality
    newLinks = if link `elem` links then links else link : links

createTunnelBetweenCities :: Region -> [City] -> Region -- genera una comunicación entre dos ciudades distintas de la región
createTunnelBetweenCities region [] = region -- si la lista de ciudades está vacía o tiene solo un elemento, la función simplemente devuelve la región sin cambios
createTunnelBetweenCities region (_:[]) = region
createTunnelBetweenCities region cities@(cityA:cityB:rest) =
    case findLink region cityA cityB of
        Just link -> updatedRegion
            where
                links = map (\(c1, c2) -> fromJust $ findLink region c1 c2) $ zip cities (tail cities) -- se utiliza zip junto con tail cities para generar pares de ciudades consecutivas en la lista cities
                tunnel = newTunnel links -- Crear un túnel con los enlaces
                updatedRegion = addTunnelsToRegion region [tunnel]
        Nothing -> createTunnelBetweenCities region (cityB:rest)


addTunnelsToRegion :: Region -> [Tunel] -> Region -- (auxiliar)
addTunnelsToRegion (Reg cities links existingTunnels) tunnelsToAdd =
    Reg cities links updatedTunnels
  where
    newTunnels = filter (\tunnel -> not $ tunnel `elem` existingTunnels) tunnelsToAdd
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
    linkConnectsCities :: Link -> Bool -- (auxiliar)
    linkConnectsCities link = linkIncludesCity startCity link && linkIncludesCity endCity link


usedCapacity :: [Tunel] -> Link -> Int -- (auxiliar)
usedCapacity tunnels link = length [t | t <- tunnels, tunnelThroughLink link t]

availableCapacityForLink :: [Tunel] -> Link -> Int -- (auxiliar)
availableCapacityForLink tunnels link = linkCapacity link - usedCapacity tunnels link

availableCapacityForRegion :: Region -> City -> City -> Int -- indica la capacidad disponible entre dos ciudades
availableCapacityForRegion (Reg _ links tunnels) cityA cityB =
    sum [availableCapacityForLink tunnels link | link <- links, linksCities cityA cityB link]