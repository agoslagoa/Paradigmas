module Point ( Point, newPoint, distanceBetweenPoints)
   where

data Point = Poi Int Int deriving (Eq, Show) 

newPoint :: Int -> Int -> Point
newPoint = Poi

distanceBetweenPoints :: Point -> Point -> Float  -- distancia absoluta
distanceBetweenPoints (Poi x1 y1) (Poi x2 y2) = sqrt (fromIntegral (x1- x2)^2 + fromIntegral (y1 - y2)^2)
