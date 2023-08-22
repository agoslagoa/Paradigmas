module Quality ( Quality, newQuality, qualityTunnelCapacity, qualityDelay )
   where

data Quality = Qua String Int Float deriving (Eq, Show)

newQuality :: String -> Int -> Float -> Quality
newQuality = Qua --NewQ name capacity delay = Qua name capacity delay 

qualityTunnelCapacity :: Quality -> Int -- cuantos túneles puede tolerar esta conexión
qualityTunnelCapacity (Qua _ capacity _) = capacity 

qualityDelay :: Quality -> Float  -- la demora por unidad de distancia que sucede en las conexiones de este canal
qualityDelay (Qua _ _ delay) = delay 