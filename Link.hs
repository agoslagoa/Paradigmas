module Link ( Link, newLink, linksCities, linkIncludesCity, linkCapacity, linkDelay, isInFirstLink, isInLastLink )
   where

import City 
import Point
import Quality

data Link = Lin City City Quality deriving (Eq, Show)

newLink :: City -> City -> Quality -> Link -- genera un link entre dos ciudades distintas
newLink cityA cityB quality = Lin cityA cityB quality

linkIncludesCity :: City -> Link -> Bool   -- indica si esta ciudad es parte de este link
linkIncludesCity cityA (Lin city1 city2 _) = cityA == city1 || cityA == city2

linksCities :: City -> City -> Link -> Bool -- indica si estas dos ciudades distintas estan conectadas mediante este link
linksCities cityA cityB (Lin city1 city2 _) = (cityA == city1 && cityB == city2) || (cityB == city1 && cityA == city2)

linkCapacity :: Link -> Int
linkCapacity (Lin _ _ quality) = qualityTunnelCapacity quality -- en velocidad (distancia entre dos ciudades / delay)

linkDelay :: Link -> Float     -- la demora que sufre una conexion en este canal (en tiempo)   
linkDelay (Lin _ _ delay) = qualityDelay delay 

isInFirstLink :: City -> [Link] -> Bool
isInFirstLink city (firstLink : secondLink : _) =
    linkIncludesCity city firstLink && not (linkIncludesCity city secondLink)
isInFirstLink _ _ = False

isInLastLink :: City -> [Link] -> Bool
isInLastLink city links =
    case reverse links of
        (lastLink : secondLastLink : _) ->
            linkIncludesCity city lastLink && not (linkIncludesCity city secondLastLink)
        _ -> False