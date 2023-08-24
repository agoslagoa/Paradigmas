module City ( City, newCity, cityName, distanceBetweenCities, cityCoordinates )
   where

import Point

data City = Cit String Point deriving (Eq, Show)

newCity :: String -> Point -> City
newCity name newPoint = Cit name newPoint

cityName :: City -> String
cityName (Cit name _ ) = name 

distanceBetweenCities :: City -> City -> Float -- proyecta las distancia que se obtiene de las coordenadas de cada ciudad 
distanceBetweenCities (Cit _ pointA) (Cit _ pointB) = distanceBetweenPoints pointA pointB 

cityCoordinates :: City -> Point -- (auxiliar)
cityCoordinates (Cit _ point) = point