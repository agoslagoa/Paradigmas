module City ( City, newCity, cityName, distanceBetweenCities )
   where

import Point
import Data.Char (isAlpha, isSpace)

data City = Cit String Point deriving (Eq, Show)

newCity :: String -> Point -> City
newCity = Cit

cityName :: City -> String
cityName (Cit name _)
    | not (isValidName name) || all isSpace name = "Nombre inválido"
    | otherwise = name

isValidName :: String -> Bool -- (auxiliar)
isValidName = any isAlpha

distanceBetweenCities :: City -> City -> Float -- proyecta las distancia que se obtiene de las coordenadas de cada ciudad 
distanceBetweenCities (Cit _ pointA) (Cit _ pointB) = distanceBetweenPoints pointA pointB