module Quality ( Quality, newQuality, qualityTunnelCapacity, qualityDelay )
   where

data Quality = Qua String Int Float deriving (Eq, Show)

newQuality :: String -> Int -> Float -> Quality
newQuality = Qua --NewQ name capacity delay = Qua name capacity delay 

qualityTunnelCapacity :: Quality -> Int -- cuantos túneles puede tolerar esta conexión
qualityTunnelCapacity (Qua _ capacity _) = capacity 

qualityDelay :: Quality -> Float  -- la demora por unidad de distancia que sucede en las conexiones de este canal
qualityDelay (Qua _ _ delay) = delay 

calidadComodoro = newQuality "cobre" 5 35.25878 
--Retraso (en segundos) = Distancia / Velocidad de propagación
--Velocidad de propagación del cobre ≈ 0.6 * Velocidad de la luz
--Retraso = 6324.5554 / (0.6 * 299,792,458)

t = [qualityTunnelCapacity calidadComodoro == 5,
     qualityDelay calidadComodoro == 35.25878,
     True]