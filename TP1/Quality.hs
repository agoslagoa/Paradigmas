module Quality ( Quality, newQuality, qualityTunnelCapacity, qualityDelay )
   where

data Quality = Qua String Int Float deriving (Eq, Show)

newQuality :: String -> Int -> Float -> Quality
newQuality = Qua 

qualityTunnelCapacity :: Quality -> Int -- cuantos túneles puede tolerar esta conexión
qualityTunnelCapacity (Qua _ capacity _)
    | capacity < 1 = error "La capacidad de tuneles que pueden atravesar el link no puede ser menor a 1"
    | otherwise = capacity

qualityDelay :: Quality -> Float  -- la demora por unidad de distancia que sucede en las conexiones de este canal
qualityDelay (Qua _ _ delay) 
    | delay <= 0 = error "El delay no puede ser igual o menor a 0"
    | otherwise = delay 