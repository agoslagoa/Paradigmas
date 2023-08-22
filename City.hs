module City ( City, newCity, cityName, distanceBetweenCities, cityCoordinates )
   where

-- import Control.Exception
import Point

data City = Cit String Point deriving (Eq, Show)

newCity :: String -> Point -> City
newCity name newPoint = Cit name newPoint


-- -- Verifica si una ciudad con el mismo nombre ya existe en la lista de ciudades
-- cityExists :: String -> [City] -> Bool
-- cityExists _ [] = False
-- cityExists newName (Cit name _ : rest)
--     | newName == name = True
--     | otherwise = cityExists newName rest

-- ---   --- 

cityName :: City -> String
cityName (Cit name _ ) = name 

cityCoordinates :: City -> Point
cityCoordinates (Cit _ point) = point

distanceBetweenCities :: City -> City -> Float
distanceBetweenCities (Cit _ pointA) (Cit _ pointB) = distanceBetweenPoints pointA pointB 