module Point ( Point, newPoint, distanceBetweenPoints)
   where

--Este constructor toma dos argumentos Int, que representan las coordenadas x e y del punto en el plano cartesiano.
data Point = Poi Int Int deriving (Eq, Show) --Las derivaciones Eq y Show permiten realizar comparaciones de igualdad y mostrar puntos en forma de cadena.

newPoint :: Int -> Int -> Point
newPoint = Poi
distanceBetweenPoints :: Point -> Point -> Float  -- distancia absoluta
distanceBetweenPoints (Poi x1 y1) (Poi x2 y2) = sqrt (fromIntegral (x1- x2)^2 + fromIntegral (y1 - y2)^2)