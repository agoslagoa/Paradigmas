module Point ( Point, newP, difP)
   where

import City 

--Este constructor toma dos argumentos Int, que representan las coordenadas x e y del punto en el plano cartesiano.
data Point = Poi Int Int deriving (Eq, Show) --Las derivaciones Eq y Show permiten realizar comparaciones de igualdad y mostrar puntos en forma de cadena.

newP :: Int -> Int -> Point
difP :: Point -> Point -> Float  -- distancia absoluta