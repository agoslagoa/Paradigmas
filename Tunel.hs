module Tunel ( Tunel, newT, connectsT, usesT, delayT )
   where

data Tunel = Tun [Link] deriving (Eq, Show)

newT :: [Link] -> Tunel
connectsT :: City -> City -> Tunel -> Bool -- inidca si este tunel conceta estas dos ciudades distintas
usesT :: Link -> Tunel -> Bool  -- indica si este tunel atraviesa ese link
delayT :: Tunel -> Float -- la demora que sufre una conexion en este tunel