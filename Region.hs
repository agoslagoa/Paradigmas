module Region ( Region, newRegion, addCityToRegion, addCitiesToRegion, createLinkBetweenCities, addLinksToRegion, createTunnelBetweenCities, addTunnelsToRegion, verifyConnectionByTunnel, verifyConnectionByLink, calculateConnectionDelay, availableCapacityForRegion)
   where
import City
import Link
import Quality
import Tunel
import Data.List

data Region = Reg [City] [Link] [Tunel] deriving (Show)

newRegion :: Region 
newRegion = Reg [] [] []  

addCityToRegion :: Region -> City -> Region  -- agrega una nueva ciudad a la región
addCityToRegion (Reg cities links tunnels) newCity
    | cityExists newCity cities || coordinatesInUse newCity cities =
        Reg cities links tunnels
    | otherwise =
        Reg (newCity : cities) links tunnels
  where
    cityExists :: City -> [City] -> Bool
    cityExists _ [] = False
    cityExists c (c':cs) = (cityName c == cityName c') || cityExists c cs

    coordinatesInUse :: City -> [City] -> Bool
    coordinatesInUse _ [] = False
    coordinatesInUse c (c':cs) = cityCoordinates c == cityCoordinates c' || coordinatesInUse c cs

addCitiesToRegion :: Region -> [City] -> Region -- agrega una lista de ciudades a la región
addCitiesToRegion = foldl addCityToRegion

createLinkBetweenCities :: Region -> City -> City -> Quality -> Region -- enlaza dos ciudades de la región con un enlace de la calidad indicada
createLinkBetweenCities (Reg ciudades links tuneles) cityA cityB quality
    | cityA `elem` ciudades && cityB `elem` ciudades = Reg ciudades newLinks tuneles
    | otherwise = Reg ciudades links tuneles
  where
    link = newLink cityA cityB quality
    newLinks = link : links

-- addLinksToRegion :: Region -> [Link] -> Region
-- addLinksToRegion region linksToAdd = region { links = updatedLinks }
--   where
--     existingLinks = links region
--     newLinks = filter (\link -> not $ link `elem` existingLinks) linksToAdd
--     updatedLinks = existingLinks ++ newLinks

addLinksToRegion :: Region -> [Link] -> Region
addLinksToRegion (Reg cities existingLinks tunnels) linksToAdd =
    Reg cities updatedLinks tunnels
  where
    newLinks = filter (\link -> not $ link `elem` existingLinks) linksToAdd
    updatedLinks = existingLinks ++ newLinks

-- createTunnelBetweenCities :: Region -> [City] -> Region -- genera una comunicación entre dos ciudades distintas de la región
-- createTunnelBetweenCities region [] = region
-- createTunnelBetweenCities region (_:[]) = region
-- createTunnelBetweenCities region (cityA:cityB:rest) =
--     case (findLink region cityA cityB) of
--         (Just link) -> createTunnelBetweenCities (Reg (cities region) (links region) (newTunel : tunnels region)) (cityB:rest)
--             where
--                 newTunel = newTunnel [link]
--         Nothing -> createTunnelBetweenCities region (cityB:rest)

createTunnelBetweenCities :: Region -> [City] -> Region -- genera una comunicación entre dos ciudades distintas de la región
createTunnelBetweenCities region [] = region
createTunnelBetweenCities region (_:[]) = region
createTunnelBetweenCities region (cityA:cityB:rest) =
    case (findLink region cityA cityB) of
        (Just link) -> createTunnelBetweenCities updatedRegion (cityB:rest)
            where
                newTunel = newTunnel [link]
                updatedRegion = addTunnelsToRegion region [newTunel]
        Nothing -> createTunnelBetweenCities region (cityB:rest)

-- addTunnelsToRegion :: Region -> [Tunel] -> Region
-- addTunnelsToRegion region tunnelsToAdd = region { tunnels = updatedTunnels }
--   where
--     existingTunnels = tunnels region
--     newTunnels = filter (\tunnel -> not $ tunnel `elem` existingTunnels) tunnelsToAdd
--     updatedTunnels = existingTunnels ++ newTunnels

addTunnelsToRegion :: Region -> [Tunel] -> Region
addTunnelsToRegion (Reg cities links existingTunnels) tunnelsToAdd =
    Reg cities links updatedTunnels
  where
    newTunnels = filter (\tunnel -> not $ tunnel `elem` existingTunnels) tunnelsToAdd
    updatedTunnels = existingTunnels ++ newTunnels

findLink :: Region -> City -> City -> Maybe Link
findLink (Reg _ links _) cityA cityB = find (linksCities cityA cityB) links

-- verifyConnectionByTunnel :: Region -> City -> City -> Bool -- indica si estas dos ciudades estan conectadas por un tunel
-- verifyConnectionByTunnel region cityA cityB = any (tunnelConnectsCities cityA cityB) (tunnels region)

verifyConnectionByTunnel :: Region -> City -> City -> Bool
verifyConnectionByTunnel (Reg _ _ tunnels) cityA cityB =
    any (tunnelConnectsCities cityA cityB) tunnels

verifyConnectionByLink :: Region -> City -> City -> Bool -- indica si estas dos ciudades estan enlazadas
verifyConnectionByLink (Reg _ links _) cityA cityB = any (linksCities cityA cityB) links

calculateConnectionDelay :: Region -> City -> City -> Maybe Float -- dadas dos ciudades conectadas, indica la demora
calculateConnectionDelay (Reg _ links tunnels) startCity endCity
  | startCity == endCity = Just 0.0
  | otherwise = case [linkDelay link | link <- links, linkIncludesCity startCity link, linkIncludesCity endCity link] of
                  [] -> Nothing
                  delays -> Just (minimum delays)

usedCapacity :: [Tunel] -> Link -> Int
usedCapacity tunnels link = length [t | t <- tunnels, tunnelThroughLink link t]

availableCapacityForLink :: [Tunel] -> Link -> Int
availableCapacityForLink tunnels link = linkCapacity link - usedCapacity tunnels link

availableCapacityForRegion :: Region -> City -> City -> Int -- indica la capacidad disponible entre dos ciudades
availableCapacityForRegion (Reg _ links tunnels) cityA cityB =
    sum [availableCapacityForLink tunnels link | link <- links, linksCities cityA cityB link]