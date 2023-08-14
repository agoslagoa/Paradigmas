module Point ( Point, newP, difP)
   where

--Este constructor toma dos argumentos Int, que representan las coordenadas x e y del punto en el plano cartesiano.
data Point = Poi Int Int deriving (Eq, Show) --Las derivaciones Eq y Show permiten realizar comparaciones de igualdad y mostrar puntos en forma de cadena.

newP :: Int -> Int -> Point
newP x y = Poi x y  
difP :: Point -> Point -> Float  -- distancia absoluta
difP (Poi x1 y1) (Poi x2 y2) =
    let dx = fromIntegral (x2 - x1)
        dy = fromIntegral (y2 - y1)
      in sqrt (dx * dx + dy * dy)