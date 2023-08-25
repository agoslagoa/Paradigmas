module Link ( Link, newLink, linksCities, linkIncludesCity, linkCapacity, linkDelay )
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
linkCapacity (Lin _ _ quality) = qualityTunnelCapacity quality 

linkDelay :: Link -> Float     -- la demora que sufre una conexion en este canal 
linkDelay (Lin city1 city2 quality)
    | qualityDelay quality == 0 = error "Quality delay cannot be zero"
    | otherwise = distanceBetweenCities city1 city2 / qualityDelay quality