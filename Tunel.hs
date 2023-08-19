module Tunel ( Tunel, newTunnel, tunnelConnectsCities, tunnelThroughLink, tunnelDelay)
   where

import City 
import Link 

data Tunel = Tun [Link] deriving (Eq, Show)


newTunnel :: [Link] -> Tunel
newTunnel variosLinks = Tun variosLinks 

tunnelConnectsCities :: City -> City -> Tunel -> Bool -- indica si éste túnel conecta estas dos ciudades distintas
tunnelConnectsCities cityA cityB (Tun links) =
    isInFirstLink cityA links && isInLastLink cityB links

tunnelThroughLink :: Link -> Tunel -> Bool  -- indica si este tunel atraviesa ese link
tunnelThroughLink link (Tun variosLinks) = link `elem` variosLinks 

tunnelDelay :: Tunel -> Float -- la demora que sufre una conexion en este tunel
tunnelDelay (Tun variosLinks) = sum [linkDelay link | link <- variosLinks]