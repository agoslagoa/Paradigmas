module Tunel ( Tunel, newTunnel, tunnelConnectsCities, tunnelThroughLink, tunnelDelay)
   where

import City
import Link

data Tunel = Tun [Link] deriving (Eq, Show)


newTunnel :: [Link] -> Tunel
newTunnel = Tun

tunnelConnectsCities :: City -> City -> Tunel -> Bool -- indica si este tunel conecta estas dos ciudades distintas
tunnelConnectsCities cityA cityB (Tun links) =
    (isInFirstLinkTunnel cityA links && isInLastLinkTunnel cityB links) ||
    (isInFirstLinkTunnel cityB links && isInLastLinkTunnel cityA links)

tunnelThroughLink :: Link -> Tunel -> Bool  -- indica si este tunel atraviesa ese link (un túnel recorre una serie de uno o más links, esta función indica si el link consultado es parte de esa serie)
tunnelThroughLink link (Tun variosLinks) = link `elem` variosLinks

tunnelDelay :: Tunel -> Float -- la demora que sufre una conexion en este tunel, esta demora es en unidades de tiempo, cuanto demora la información en recorrer el túnel 
tunnelDelay (Tun variosLinks) = sum [linkDelay link | link <- variosLinks]

isInFirstLinkTunnel :: City -> [Link] -> Bool -- (auxiliar)
isInFirstLinkTunnel _ [] = False
isInFirstLinkTunnel city [firstLink] = linkIncludesCity city firstLink
isInFirstLinkTunnel city (firstLink : secondLink : _) =
    linkIncludesCity city firstLink && not (linkIncludesCity city secondLink)


isInLastLinkTunnel :: City -> [Link] -> Bool -- (auxiliar)
isInLastLinkTunnel _ [] = False
isInLastLinkTunnel city [lastLink] = linkIncludesCity city lastLink
isInLastLinkTunnel city links =
    case reverse links of
        (lastLink : secondLastLink : _) ->
            linkIncludesCity city lastLink && not (linkIncludesCity city secondLastLink)
        _ -> False