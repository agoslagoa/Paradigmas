-- module Main where

-- import Point
-- import Quality
-- import City
-- import Link
-- import Tunel
-- import Region 


-- main :: IO ()
-- main = do
--     let initialRegion = newR [] [] []
--     mainMenu initialRegion

-- -- Función auxiliar para manejar la verificación de ciudades y ejecutar acciones
-- performActionWithCities :: String -> Region -> String -> String -> (City -> City -> IO ()) -> IO ()
-- performActionWithCities option region cityAName cityBName action = do
--     let maybeCityA = findCityByName region cityAName
--         maybeCityB = findCityByName region cityBName

--     case (maybeCityA, maybeCityB) of
--         (Just cityA, Just cityB) -> do
--             action cityA cityB
--             putStrLn ""
--             mainMenu region
--         _ -> do
--             putStrLn "Ciudades no encontradas. Intente nuevamente."
--             mainMenu region

-- mainMenu :: Region -> IO ()
-- mainMenu region = do
--     putStrLn "¡Bienvenido! Estás ingresando al programa de la empresa Telco. ¿Qué desea hacer?"
--     putStrLn "Menú Principal:"
--     putStrLn "1. Agregar Ciudad"
--     putStrLn "2. Enlazar Ciudades"
--     putStrLn "3. Conectar Ciudades con Túnel"
--     putStrLn "4. Verificar Conexión por Túnel"
--     putStrLn "5. Verificar Enlace de Ciudades"
--     putStrLn "6. Calcular Demora"
--     putStrLn "7. Calcular Capacidad Disponible"
--     putStrLn "8. Salir"
--     putStrLn "Ingrese el número de la opción deseada:"
--     option <- getLine
--     handleMenuOption option region

-- handleMenuOption :: String -> Region -> IO ()
-- handleMenuOption "1" region = do
--     putStrLn "Ingrese el nombre de la nueva ciudad:"
--     cityName <- getLine
--     putStrLn "Ingrese la posición x de la nueva ciudad:"
--     cityPosX <- readLn :: IO Int
--     putStrLn "Ingrese la posición y de la nueva ciudad:"
--     cityPosY <- readLn :: IO Int
--     let newCity = newC cityName (newP cityPosX cityPosY)

--     if cityExistsWithNameOrCoordinates newCity region
--         then do
--             putStrLn "Ya existe una ciudad con el mismo nombre o en las mismas coordenadas. Intente nuevamente."
--             putStrLn ""
--             mainMenu region
--         else do
--             let newRegion = foundR region newCity
--             putStrLn $ "Ciudad '" ++ cityName ++ "' agregada."
--             putStrLn ""
--             mainMenu newRegion

-- handleMenuOption "2" region = do
--     putStrLn "Ingrese el nombre de la primera ciudad:"
--     cityAName <- getLine
--     putStrLn "Ingrese el nombre de la segunda ciudad:"
--     cityBName <- getLine
--     putStrLn "Ingrese la capacidad del enlace:"
--     capacity <- readLn :: IO Int
--     putStrLn "Ingrese el retraso del enlace:"
--     delay <- readLn :: IO Float

--     let maybeCityA = findCityByName region cityAName
--         maybeCityB = findCityByName region cityBName

--     case (maybeCityA, maybeCityB) of
--         (Just cityA, Just cityB) -> do
--             let newLink = newL cityA cityB (newQ "" capacity delay)
--                 newRegion = linkR region cityA cityB (newQ "" capacity delay)
--             putStrLn $ "Enlace entre " ++ cityAName ++ " y " ++ cityBName ++ " agregado."
--             putStrLn ""
--             mainMenu newRegion
            
--         _ -> do
--             putStrLn "Ciudades no encontradas. Intente nuevamente."
--             putStrLn ""
--             mainMenu region

-- handleMenuOption "3" region = do
--     putStrLn "Ingrese el nombre de la primera ciudad:"
--     cityAName <- getLine
--     putStrLn "Ingrese el nombre de la segunda ciudad:"
--     cityBName <- getLine

--     let action cityA cityB = do
--             let newTunnel = newT [newL cityA cityB (newQ "" 0 0)]
--             let newRegion = tunelR region cityA cityB (newQ "" 0 0)
--             putStrLn $ "Túnel entre " ++ cityAName ++ " y " ++ cityBName ++ " agregado."
--             putStrLn ""
           

--     performActionWithCities "3" region cityAName cityBName action


-- handleMenuOption "4" region = do
--     putStrLn "Ingrese el nombre de la primera ciudad:"
--     cityAName <- getLine
--     putStrLn "Ingrese el nombre de la segunda ciudad:"
--     cityBName <- getLine

--     let action cityA cityB = do
--             let isConnected = connectedR region cityA cityB
--             if isConnected
--                 then putStrLn $ "Las ciudades " ++ cityAName ++ " y " ++ cityBName ++ " están conectadas por un túnel."
--                 else putStrLn $ "Las ciudades " ++ cityAName ++ " y " ++ cityBName ++ " no están conectadas por un túnel."
--             putStrLn ""

--     performActionWithCities "4" region cityAName cityBName action


-- handleMenuOption "5" region = do
--     putStrLn "Ingrese el nombre de la primera ciudad:"
--     cityAName <- getLine
--     putStrLn "Ingrese el nombre de la segunda ciudad:"
--     cityBName <- getLine

--     let action cityA cityB = do
--             let isLinked = linkedR region cityA cityB
--             if isLinked
--                 then putStrLn $ "Las ciudades " ++ cityAName ++ " y " ++ cityBName ++ " están enlazadas."
--                 else putStrLn $ "Las ciudades " ++ cityAName ++ " y " ++ cityBName ++ " no están enlazadas."
--             putStrLn ""

--     performActionWithCities "5" region cityAName cityBName action


-- handleMenuOption "6" region = do
--     putStrLn "Ingrese el nombre de la primera ciudad:"
--     cityAName <- getLine
--     putStrLn "Ingrese el nombre de la segunda ciudad:"
--     cityBName <- getLine

--     let action cityA cityB = do
--             let distance = distanceC cityA cityB  
--             putStrLn $ "La distancia entre " ++ cityAName ++ " y " ++ cityBName ++ " es: " ++ show distance
--             putStrLn ""

--     performActionWithCities "6" region cityAName cityBName action


-- handleMenuOption "7" region = do
--     putStrLn "Ingrese el nombre de la primera ciudad:"
--     cityAName <- getLine
--     putStrLn "Ingrese el nombre de la segunda ciudad:"
--     cityBName <- getLine

--     let action cityA cityB = do
--             let capacity = availableCapacityForR region cityA cityB
--             putStrLn $ "La capacidad disponible entre " ++ cityAName ++ " y " ++ cityBName ++ " es: " ++ show capacity
--             putStrLn ""

--     performActionWithCities "7" region cityAName cityBName action


-- handleMenuOption "8" _ = putStrLn "Saliendo..."

-- handleMenuOption _ region = do
--     putStrLn "Opción inválida. Intente nuevamente."
--     putStrLn ""
--     mainMenu region