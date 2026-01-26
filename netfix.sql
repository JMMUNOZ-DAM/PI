-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 26-01-2026 a las 12:28:25
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `netfix`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `agenda`
--

CREATE TABLE `agenda` (
  `id_cita` int(11) NOT NULL,
  `id_tecnico` int(11) NOT NULL,
  `id_incidencia` int(11) NOT NULL,
  `fecha_cita` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `agenda`
--

INSERT INTO `agenda` (`id_cita`, `id_tecnico`, `id_incidencia`, `fecha_cita`) VALUES
(3, 1, 3001, '2026-01-21 14:30:00'),
(4, 4, 3005, '2026-01-22 08:30:00'),
(5, 5, 9341, '2026-01-22 18:00:00'),
(6, 5, 9335, '2026-01-22 18:30:00'),
(7, 1, 9340, '2026-01-23 13:30:00'),
(8, 5, 9352, '2026-01-23 12:00:00'),
(9, 1, 9302, '2026-01-23 12:00:00'),
(10, 1, 9319, '2026-01-23 12:30:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `aparatos`
--

CREATE TABLE `aparatos` (
  `id_aparato` int(11) NOT NULL,
  `tipo_aparato` enum('5G','FTTH') NOT NULL,
  `marca` varchar(50) DEFAULT NULL,
  `modelo` varchar(50) DEFAULT NULL,
  `numero_serie` varchar(50) DEFAULT NULL,
  `mac` varchar(12) DEFAULT NULL,
  `id_contrato` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `aparatos`
--

INSERT INTO `aparatos` (`id_aparato`, `tipo_aparato`, `marca`, `modelo`, `numero_serie`, `mac`, `id_contrato`) VALUES
(2001, 'FTTH', 'Huawei', 'HG8245', 'FTTH-2001', 'A1B2C3D4E5F1', 72),
(2002, 'FTTH', 'ZTE', 'F680', 'FTTH-2002', 'A1B2C3D4E5F2', 1003),
(2003, 'FTTH', 'Nokia', 'G-2425', 'FTTH-2003', 'A1B2C3D4E5F3', 1005),
(2004, 'FTTH', 'ZTE', 'F680', 'FTTH-2004', 'A1B2C3D4E5F4', 1006),
(2005, '5G', 'Vodafone', 'SIM Voz', 'SIM-2005', '000000000005', 1002),
(2006, '5G', 'Movistar', 'SIM Datos', 'SIM-2006', '000000000006', NULL),
(2007, '5G', 'Orange', 'SIM Voz', 'SIM-2007', '000000000007', 1003),
(2008, '5G', 'Yoigo', 'SIM Datos', 'SIM-2008', '000000000008', 1004),
(2009, '5G', 'Vodafone', 'SIM Voz', 'SIM-2009', '000000000009', 1006),
(2010, '5G', 'Vodafone', 'SIM Datos', 'SIM-2010', '000000000010', 1006),
(2011, '5G', 'Orange', 'SIM Voz', 'SIM-2011', '000000000011', 1007),
(9201, 'FTTH', 'Huawei', 'HG8245', 'FTTH-9201', 'A1B2C3D4E501', 9101),
(9202, 'FTTH', 'ZTE', 'F680', 'FTTH-9202', 'A1B2C3D4E502', 9103),
(9203, 'FTTH', 'Nokia', 'G-2425', 'FTTH-9203', 'A1B2C3D4E503', 9105),
(9204, 'FTTH', 'ZTE', 'F680', 'FTTH-9204', 'A1B2C3D4E504', 9106),
(9205, 'FTTH', 'Huawei', 'HG8245', 'FTTH-9205', 'A1B2C3D4E505', 9108),
(9206, 'FTTH', 'Nokia', 'G-2425', 'FTTH-9206', 'A1B2C3D4E506', 9109),
(9207, 'FTTH', 'ZTE', 'F680', 'FTTH-9207', 'A1B2C3D4E507', 9111),
(9208, 'FTTH', 'Huawei', 'HG8245', 'FTTH-9208', 'A1B2C3D4E508', 9112),
(9209, 'FTTH', 'Nokia', 'G-2425', 'FTTH-9209', 'A1B2C3D4E509', 9115),
(9210, 'FTTH', 'ZTE', 'F680', 'FTTH-9210', 'A1B2C3D4E510', 9116),
(9211, 'FTTH', 'Huawei', 'HG8245', 'FTTH-9211', 'A1B2C3D4E511', 9118),
(9212, 'FTTH', 'Nokia', 'G-2425', 'FTTH-9212', 'A1B2C3D4E512', 9121),
(9213, 'FTTH', 'ZTE', 'F680', 'FTTH-9213', 'A1B2C3D4E513', 9122),
(9214, 'FTTH', 'Huawei', 'HG8245', 'FTTH-9214', 'A1B2C3D4E514', 9124),
(9215, '5G', 'NETFIX', 'SIM Voz', '9215000000000000', '000000009215', 9102),
(9216, '5G', 'NETFIX', 'SIM Datos', '9216000000000000', '000000009216', 9102),
(9217, '5G', 'NETFIX', 'SIM Voz', '9217000000000000', '000000009217', 9103),
(9218, '5G', 'NETFIX', 'SIM Datos', '9218000000000000', '000000009218', 9104),
(9219, '5G', 'NETFIX', 'SIM Voz', '9219000000000000', '000000009219', 9106),
(9220, '5G', 'NETFIX', 'SIM Datos', '9220000000000000', '000000009220', 9106),
(9221, '5G', 'NETFIX', 'SIM Voz', '9221000000000000', '000000009221', 9107),
(9222, '5G', 'NETFIX', 'SIM Datos', '9222000000000000', '000000009222', 9109),
(9223, '5G', 'NETFIX', 'SIM Voz', '9223000000000000', '000000009223', 9110),
(9224, '5G', 'NETFIX', 'SIM Datos', '9224000000000000', '000000009224', 9111),
(9225, '5G', 'NETFIX', 'SIM Voz', '9225000000000000', '000000009225', 9113),
(9226, '5G', 'NETFIX', 'SIM Datos', '9226000000000000', '000000009226', 9114),
(9227, '5G', 'NETFIX', 'SIM Voz', '9227000000000000', '000000009227', 9115),
(9228, '5G', 'NETFIX', 'SIM Datos', '9228000000000000', '000000009228', 9115),
(9229, '5G', 'NETFIX', 'SIM Voz', '9229000000000000', '000000009229', 9117),
(9230, '5G', 'NETFIX', 'SIM Datos', '9230000000000000', '000000009230', 9118),
(9231, '5G', 'NETFIX', 'SIM Voz', '9231000000000000', '000000009231', 9118),
(9232, '5G', 'NETFIX', 'SIM Voz', '9232000000000000', '000000009232', 9119),
(9233, '5G', 'NETFIX', 'SIM Datos', '9233000000000000', '000000009233', 9120),
(9234, '5G', 'NETFIX', 'SIM Voz', '9234000000000000', '000000009234', 9121),
(9235, '5G', 'NETFIX', 'SIM Datos', '9235000000000000', '000000009235', 9121),
(9236, '5G', 'NETFIX', 'SIM Voz', '9236000000000000', '000000009236', 9123),
(9237, '5G', 'NETFIX', 'SIM Datos', '9237000000000000', '000000009237', 9124),
(9238, '5G', 'NETFIX', 'SIM Voz', '9238000000000000', '000000009238', 9125),
(9239, '5G', 'NETFIX', 'SIM Datos', '9239000000000000', '000000009239', 9125),
(9501, 'FTTH', 'Huawei', 'HG8245', 'FTTH-L-9501', 'F1A1B1C1D1E1', 1001),
(9502, 'FTTH', 'Huawei', 'HG8245', 'FTTH-L-9502', 'F1A1B1C1D1E2', 74),
(9503, 'FTTH', 'ZTE', 'F680', 'FTTH-L-9503', 'F1A1B1C1D1E3', NULL),
(9504, 'FTTH', 'ZTE', 'F680', 'FTTH-L-9504', 'F1A1B1C1D1E4', NULL),
(9505, 'FTTH', 'Nokia', 'G-2425', 'FTTH-L-9505', 'F1A1B1C1D1E5', NULL),
(9506, 'FTTH', 'Nokia', 'G-2425', 'FTTH-L-9506', 'F1A1B1C1D1E6', NULL),
(9507, 'FTTH', 'Huawei', 'HG8245', 'FTTH-L-9507', 'F1A1B1C1D1E7', NULL),
(9508, 'FTTH', 'ZTE', 'F680', 'FTTH-L-9508', 'F1A1B1C1D1E8', NULL),
(9509, 'FTTH', 'Nokia', 'G-2425', 'FTTH-L-9509', 'F1A1B1C1D1E9', NULL),
(9510, 'FTTH', 'Huawei', 'HG8245', 'FTTH-L-9510', 'F1A1B1C1D1EA', NULL),
(9511, '5G', 'Movistar', 'SIM Voz', '9511000000000000', '900000009511', 73),
(9512, '5G', 'Movistar', 'SIM Datos', '9512000000000000', '900000009512', 61),
(9513, '5G', 'Vodafone', 'SIM Voz', '9513000000000000', '900000009513', NULL),
(9514, '5G', 'Vodafone', 'SIM Datos', '9514000000000000', '900000009514', NULL),
(9515, '5G', 'Orange', 'SIM Voz', '9515000000000000', '900000009515', NULL),
(9516, '5G', 'Orange', 'SIM Datos', '9516000000000000', '900000009516', NULL),
(9517, '5G', 'Yoigo', 'SIM Voz', '9517000000000000', '900000009517', NULL),
(9518, '5G', 'Yoigo', 'SIM Datos', '9518000000000000', '900000009518', NULL),
(9519, '5G', 'Movistar', 'SIM Voz', '9519000000000000', '900000009519', NULL),
(9520, '5G', 'Vodafone', 'SIM Datos', '9520000000000000', '900000009520', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `dni` char(9) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`dni`, `nombre`, `telefono`, `email`) VALUES
('10101010G', 'David Romero', '600000010', 'david.romero@mail.com'),
('11111111H', 'Ana Ruiz', '600000001', 'ana.ruiz@mail.com'),
('12121212L', 'Sara Núñez', '600000011', 'sara.nunez@mail.com'),
('13131313M', 'Iván Ortiz', '600000012', 'ivan.ortiz@mail.com'),
('14141414N', 'Cliente 1414', '611000001', 'c1414@mail.com'),
('15151515S', 'Cliente 1515', '611000002', 'c1515@mail.com'),
('16161616Q', 'Cliente 1616', '611000003', 'c1616@mail.com'),
('17171717W', 'Cliente 1717', '611000004', 'c1717@mail.com'),
('18181818D', 'Cliente 1818', '611000005', 'c1818@mail.com'),
('19191919B', 'Cliente 1919', '611000006', 'c1919@mail.com'),
('20202020C', 'Cliente 2020', '611000007', 'c2020@mail.com'),
('21212121L', 'Cliente 2121', '611000008', 'c2121@mail.com'),
('22222222J', 'Luis Martín', '600000002', 'luis.martin@mail.com'),
('22223333K', 'Cliente 2233', '611000009', 'c2233@mail.com'),
('23334444M', 'Cliente 2333', '611000010', 'c2333@mail.com'),
('24445555P', 'Cliente 2444', '611000011', 'c2444@mail.com'),
('25556666R', 'Cliente 2555', '611000012', 'c2555@mail.com'),
('26667777T', 'Cliente 2666', '611000013', 'c2666@mail.com'),
('27778888V', 'Cliente 2777', '611000014', 'c2777@mail.com'),
('28889999Y', 'Cliente 2888', '611000015', 'c2888@mail.com'),
('33333333P', 'Laura Díaz', '600000003', 'laura.diaz@mail.com'),
('44444444A', 'Pedro Gómez', '600000004', 'pedro.gomez@mail.com'),
('55555555B', 'Marta Sánchez', '600000005', 'marta.sanchez@mail.com'),
('66666666C', 'Jorge Molina', '600000006', 'jorge.molina@mail.com'),
('77777777D', 'Lucía Torres', '600000007', 'lucia.torres@mail.com'),
('88888888E', 'Alberto Cano', '600000008', 'alberto.cano@mail.com'),
('99999999F', 'Carmen Vega', '600000009', 'carmen.vega@mail.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comentarios`
--

CREATE TABLE `comentarios` (
  `id_incidencia` int(11) NOT NULL,
  `agente` varchar(50) NOT NULL,
  `comentario` varchar(250) NOT NULL,
  `fecha` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `comentarios`
--

INSERT INTO `comentarios` (`id_incidencia`, `agente`, `comentario`, `fecha`) VALUES
(1, 'Tecnico 1', 'Se agenda visita técnica', '2024-01-10 00:00:00'),
(2, 'Tecnico 1', 'Pendiente respuesta operador', '2024-01-12 00:00:00'),
(3, 'Admin Sistemas', 'Perfil ajustado correctamente', '2024-01-15 00:00:00'),
(4, 'Supervisor 1', 'Escalado correctamente', '2024-01-18 00:00:00'),
(5, 'Tecnico 1', 'Monitorizando estabilidad', '2024-01-20 00:00:00'),
(6, 'Admin Sistemas', 'SIM sustituida', '2024-01-22 00:00:00'),
(3001, 'Tecnico 1', 'Se agenda visita técnica', '2024-01-10 00:00:00'),
(3002, 'Tecnico 1', 'Pendiente confirmar cobertura con operador', '2024-01-12 00:00:00'),
(3003, 'Admin Sistemas', 'Perfil ajustado; mejora confirmada', '2024-01-15 00:00:00'),
(3004, 'Supervisor 1', 'Escalado a nivel 2', '2024-01-18 00:00:00'),
(3005, 'Tecnico 1', 'Se revisa estabilidad de enlace', '2024-01-20 00:00:00'),
(3002, 'pruebas', 'Se comrueba cobertura, está ok', '2026-01-21 00:00:00'),
(9311, 'pruebas', 'Prueba', '2026-01-21 00:00:00'),
(9313, 'pruebas', 'Prueba de log en comentarios', '2026-01-22 00:00:00'),
(9311, 'pruebas', 'Probando mensajes', '2026-01-22 00:00:00'),
(9311, 'admin', 'prueba arreglo', '2026-01-22 00:00:00'),
(9311, 'admin', 'Prueba fechas', '2026-01-22 00:00:00'),
(9381, 'admin', 'Problema de corriente en casa del cliente', '2026-01-22 15:18:58'),
(9311, 'admin', 'test', '2026-01-22 15:39:01'),
(9343, 'admin', 'Cliente no está en domicilio, se le llama a las 17.00', '2026-01-22 15:39:21'),
(9307, 'admin', 'Mentira', '2026-01-22 20:12:07'),
(9302, 'admin', 'Prueba tras comunicacion de incidencia', '2026-01-23 10:37:35');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contratos`
--

CREATE TABLE `contratos` (
  `id_contrato` int(11) NOT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_fin` date DEFAULT NULL,
  `tipo_servicio` enum('fibra_optica','movil','combo') NOT NULL,
  `direccion` varchar(150) DEFAULT NULL,
  `dni_cliente` char(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `contratos`
--

INSERT INTO `contratos` (`id_contrato`, `fecha_inicio`, `fecha_fin`, `tipo_servicio`, `direccion`, `dni_cliente`) VALUES
(61, '2023-02-15', NULL, 'movil', 'C/ Luna 5, Madrid', '11111111H'),
(62, '2022-06-01', NULL, 'combo', 'Av. Mar 10, Valencia', '22222222J'),
(63, '2023-02-20', NULL, 'movil', 'C/ Norte 7, Bilbao', '33333333P'),
(64, '2021-09-10', NULL, 'fibra_optica', 'C/ Sur 3, Sevilla', '33333333P'),
(65, '2022-11-05', NULL, 'combo', 'Av. Centro 99, Málaga', '44444444A'),
(66, '2023-04-01', NULL, 'movil', 'C/ Este 12, Murcia', '55555555B'),
(67, '2021-01-01', NULL, 'fibra_optica', 'C/ Oeste 8, León', '66666666C'),
(68, '2023-05-10', NULL, 'combo', 'C/ Real 4, Burgos', '77777777D'),
(69, '2022-07-07', NULL, 'movil', 'C/ Alta 6, Cádiz', '88888888E'),
(70, '2023-06-01', NULL, 'fibra_optica', 'C/ Baja 2, Zamora', '99999999F'),
(71, '2022-03-03', NULL, 'combo', 'Av. Libertad 15, Gijón', '10101010G'),
(72, '2023-07-01', NULL, 'movil', 'C/ Nueva 1, Madrid', '12121212L'),
(73, '2022-08-15', NULL, 'combo', 'C/ Antigua 9, Toledo', '12121212L'),
(74, '2023-03-12', NULL, 'fibra_optica', 'C/ Sierra 6, Huesca', '13131313M'),
(75, '2023-04-20', NULL, 'movil', 'C/ Valle 2, Huesca', '13131313M'),
(1001, '2023-01-10', NULL, 'fibra_optica', 'C/ Sol 1, Madrid', '11111111H'),
(1002, '2023-02-15', NULL, 'movil', 'C/ Luna 5, Madrid', '11111111H'),
(1003, '2022-06-01', NULL, 'combo', 'Av. Mar 10, Valencia', '22222222J'),
(1004, '2023-02-20', NULL, 'movil', 'C/ Norte 7, Bilbao', '33333333P'),
(1005, '2021-09-10', NULL, 'fibra_optica', 'C/ Sur 3, Sevilla', '33333333P'),
(1006, '2022-11-05', NULL, 'combo', 'Av. Centro 99, Málaga', '44444444A'),
(1007, '2023-04-01', NULL, 'movil', 'C/ Este 12, Murcia', '55555555B'),
(1008, '2021-01-01', NULL, 'fibra_optica', 'C/ Oeste 8, León', '66666666C'),
(9010, '2023-01-10', NULL, 'fibra_optica', 'C/ Sol 1, Madrid', '11111111H'),
(9101, '2024-01-10', NULL, 'fibra_optica', 'C/ A 1, Madrid', '14141414N'),
(9102, '2024-01-12', NULL, 'movil', 'C/ A 1, Madrid', '14141414N'),
(9103, '2023-11-01', NULL, 'combo', 'Av. B 2, Valencia', '15151515S'),
(9104, '2023-10-05', NULL, 'movil', 'C/ C 3, Sevilla', '16161616Q'),
(9105, '2023-09-20', NULL, 'fibra_optica', 'C/ D 4, Bilbao', '16161616Q'),
(9106, '2024-02-02', NULL, 'combo', 'Av. E 5, Málaga', '17171717W'),
(9107, '2024-02-10', NULL, 'movil', 'C/ F 6, Murcia', '18181818D'),
(9108, '2023-08-15', NULL, 'fibra_optica', 'C/ G 7, León', '19191919B'),
(9109, '2024-03-01', NULL, 'combo', 'C/ H 8, Burgos', '20202020C'),
(9110, '2023-12-12', NULL, 'movil', 'C/ I 9, Cádiz', '21212121L'),
(9111, '2024-03-05', NULL, 'combo', 'Av. J 10, Gijón', '22223333K'),
(9112, '2024-03-08', NULL, 'fibra_optica', 'C/ K 11, Zaragoza', '23334444M'),
(9113, '2024-03-10', NULL, 'movil', 'C/ K 11, Zaragoza', '23334444M'),
(9114, '2024-03-12', NULL, 'movil', 'C/ L 12, Palma', '24445555P'),
(9115, '2024-03-15', NULL, 'combo', 'Av. M 13, Granada', '25556666R'),
(9116, '2024-03-18', NULL, 'fibra_optica', 'C/ N 14, Oviedo', '26667777T'),
(9117, '2024-03-20', NULL, 'movil', 'C/ O 15, Vigo', '27778888V'),
(9118, '2024-03-21', NULL, 'combo', 'Av. P 16, Salamanca', '28889999Y'),
(9119, '2024-03-22', NULL, 'movil', 'C/ Q 17, Madrid', '15151515S'),
(9120, '2024-03-23', NULL, 'movil', 'C/ R 18, Valencia', '17171717W'),
(9121, '2024-03-24', NULL, 'combo', 'Av. S 19, Sevilla', '18181818D'),
(9122, '2024-03-25', NULL, 'fibra_optica', 'C/ T 20, Bilbao', '20202020C'),
(9123, '2024-03-26', NULL, 'movil', 'C/ U 21, Málaga', '21212121L'),
(9124, '2024-03-27', NULL, 'combo', 'Av. V 22, Murcia', '24445555P'),
(9125, '2024-03-28', NULL, 'movil', 'C/ W 23, León', '26667777T');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `diagnostico`
--

CREATE TABLE `diagnostico` (
  `id_diagnostico` int(11) NOT NULL,
  `fecha_actualizacion` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `estado_general` enum('correcto','advertencia','fallo') DEFAULT 'correcto',
  `velocidad_internet` decimal(6,2) DEFAULT NULL,
  `niveles_opticos` varchar(100) DEFAULT NULL,
  `cobertura` varchar(100) DEFAULT NULL,
  `ping` decimal(6,2) DEFAULT NULL,
  `observaciones` text DEFAULT NULL,
  `id_aparato` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `diagnostico`
--

INSERT INTO `diagnostico` (`id_diagnostico`, `fecha_actualizacion`, `estado_general`, `velocidad_internet`, `niveles_opticos`, `cobertura`, `ping`, `observaciones`, `id_aparato`) VALUES
(57, '2026-01-21 15:40:03', 'correcto', 988.80, '-24,2 dBm', 'N/A (FTTH)', 8.67, 'Funcionamiento óptimo.', 2001),
(58, '2026-01-21 15:40:20', 'correcto', 705.30, 'N/A (5G)', 'Buena', 85.65, 'Sin incidencias detectadas.', 2009),
(59, '2026-01-22 20:12:32', 'advertencia', 20.96, '-26,8 dBm', 'N/A (FTTH)', 106.27, 'Nivel óptico bajo. Posible problema en fibra.', 9208),
(60, '2026-01-21 15:52:53', 'advertencia', 26.67, '-27,6 dBm', 'N/A (FTTH)', 106.39, 'Nivel óptico bajo. Posible problema en fibra.', 9203),
(62, '2026-01-22 20:12:56', 'correcto', 427.90, 'N/A (5G)', 'Buena', 76.14, 'Sin incidencias detectadas.', 9233),
(63, '2026-01-21 15:59:22', 'correcto', 717.36, '-23,9 dBm', 'N/A (FTTH)', 8.32, 'Funcionamiento óptimo.', 9209),
(64, '2026-01-22 13:35:38', 'correcto', 629.89, '-24,8 dBm', 'N/A (FTTH)', 5.94, 'Funcionamiento óptimo.', 9210),
(65, '2026-01-21 16:52:02', 'correcto', 205.65, 'N/A (5G)', 'Buena', 76.07, 'Sin incidencias detectadas.', 9221),
(66, '2026-01-22 13:34:33', 'correcto', 743.14, 'N/A (5G)', 'Excelente', 82.07, 'Sin incidencias detectadas.', 9223),
(68, '2026-01-22 09:27:44', 'correcto', 506.97, 'N/A (5G)', 'Buena', 72.14, 'Sin incidencias detectadas.', 9238),
(69, '2026-01-22 13:42:49', 'correcto', 539.58, 'N/A (5G)', 'Excelente', 77.29, 'Sin incidencias detectadas.', 2005),
(70, '2026-01-22 09:33:43', 'advertencia', 10.26, '-25,1 dBm', 'N/A (FTTH)', 103.10, 'Nivel óptico bajo. Posible problema en fibra.', 9201),
(71, '2026-01-22 13:25:17', 'correcto', 835.60, '-24,6 dBm', 'N/A (FTTH)', 10.91, 'Funcionamiento óptimo.', 9214),
(74, '2026-01-22 13:29:50', 'advertencia', 1.07, 'N/A (5G)', 'Irregular', 272.95, 'Cobertura 5G inestable.', 2006),
(80, '2026-01-23 11:16:39', 'correcto', 968.09, '-24,3 dBm', 'N/A (FTTH)', 9.99, 'Funcionamiento óptimo.', 9212),
(82, '2026-01-22 15:39:53', 'correcto', 481.70, 'N/A (5G)', 'Buena', 79.92, 'Sin incidencias detectadas.', 9225),
(83, '2026-01-22 16:36:03', 'correcto', 800.07, '-19,2 dBm', 'N/A (FTTH)', 14.16, 'Funcionamiento óptimo.', 9207);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `horarios`
--

CREATE TABLE `horarios` (
  `id_horario` int(11) NOT NULL,
  `id_tecnico` int(11) NOT NULL,
  `dia_semana` int(11) NOT NULL,
  `hora_entrada` time NOT NULL,
  `hora_salida` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `horarios`
--

INSERT INTO `horarios` (`id_horario`, `id_tecnico`, `dia_semana`, `hora_entrada`, `hora_salida`) VALUES
(1, 1, 2, '08:00:00', '17:00:00'),
(2, 2, 2, '08:00:00', '15:00:00'),
(3, 1, 3, '08:00:00', '15:00:00'),
(4, 2, 3, '08:00:00', '15:00:00'),
(5, 1, 4, '08:00:00', '15:00:00'),
(6, 2, 4, '08:00:00', '15:00:00'),
(7, 1, 5, '08:00:00', '15:00:00'),
(8, 2, 5, '08:00:00', '15:00:00'),
(9, 1, 6, '08:00:00', '15:00:00'),
(10, 2, 6, '08:00:00', '15:00:00'),
(16, 4, 2, '08:00:00', '13:00:00'),
(17, 5, 2, '08:00:00', '13:00:00'),
(18, 4, 3, '08:00:00', '13:00:00'),
(19, 5, 3, '08:00:00', '13:00:00'),
(20, 4, 4, '08:00:00', '13:00:00'),
(21, 5, 4, '08:00:00', '13:00:00'),
(22, 4, 5, '08:00:00', '13:00:00'),
(23, 5, 5, '08:00:00', '13:00:00'),
(24, 4, 6, '08:00:00', '13:00:00'),
(25, 5, 6, '08:00:00', '13:00:00'),
(26, 4, 2, '16:00:00', '19:00:00'),
(27, 5, 2, '16:00:00', '19:00:00'),
(28, 4, 3, '16:00:00', '19:00:00'),
(29, 5, 3, '16:00:00', '19:00:00'),
(30, 4, 4, '16:00:00', '19:00:00'),
(31, 5, 4, '16:00:00', '19:00:00'),
(32, 4, 5, '16:00:00', '19:00:00'),
(33, 5, 5, '16:00:00', '19:00:00'),
(34, 4, 6, '16:00:00', '19:00:00'),
(35, 5, 6, '16:00:00', '19:00:00'),
(36, 6, 2, '08:00:00', '13:00:00'),
(37, 6, 3, '08:00:00', '13:00:00'),
(38, 6, 4, '08:00:00', '13:00:00'),
(39, 6, 5, '08:00:00', '13:00:00'),
(40, 6, 6, '08:00:00', '13:00:00'),
(41, 6, 2, '16:00:00', '19:00:00'),
(42, 6, 3, '16:00:00', '19:00:00'),
(43, 6, 4, '16:00:00', '19:00:00'),
(44, 6, 5, '16:00:00', '19:00:00'),
(45, 6, 6, '16:00:00', '19:00:00'),
(51, 7, 2, '08:00:00', '13:00:00'),
(52, 7, 3, '08:00:00', '13:00:00'),
(53, 7, 4, '08:00:00', '13:00:00'),
(54, 7, 5, '08:00:00', '13:00:00'),
(55, 7, 6, '08:00:00', '13:00:00'),
(56, 7, 2, '16:00:00', '19:00:00'),
(57, 7, 3, '16:00:00', '19:00:00'),
(58, 7, 4, '16:00:00', '19:00:00'),
(59, 7, 5, '16:00:00', '19:00:00'),
(60, 7, 6, '16:00:00', '19:00:00'),
(71, 9, 2, '08:00:00', '13:00:00'),
(72, 9, 3, '08:00:00', '13:00:00'),
(73, 9, 4, '08:00:00', '13:00:00'),
(74, 9, 5, '08:00:00', '13:00:00'),
(75, 9, 6, '08:00:00', '13:00:00'),
(76, 9, 2, '16:00:00', '19:00:00'),
(77, 9, 3, '16:00:00', '19:00:00'),
(78, 9, 4, '16:00:00', '19:00:00'),
(79, 9, 5, '16:00:00', '19:00:00'),
(80, 9, 6, '16:00:00', '19:00:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `incidencias`
--

CREATE TABLE `incidencias` (
  `id_incidencia` int(11) NOT NULL,
  `fecha_reporte` datetime NOT NULL DEFAULT current_timestamp(),
  `descripcion` text DEFAULT NULL,
  `estado` enum('abierta','sin_comunicar','solucionada','derivada') DEFAULT 'abierta',
  `id_contrato` int(11) NOT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `solucion` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `incidencias`
--

INSERT INTO `incidencias` (`id_incidencia`, `fecha_reporte`, `descripcion`, `estado`, `id_contrato`, `id_usuario`, `solucion`) VALUES
(3001, '2026-01-21 15:37:41', 'Router sin sincronizar', 'derivada', 1001, 3, 'Cita programada: 2026-01-21 14:30:00'),
(3002, '2026-01-21 15:37:41', 'SIM sin cobertura', 'abierta', 1002, 2, 'Pendiente comunicación al cliente'),
(3003, '2026-01-21 15:37:41', 'Baja velocidad fibra', 'sin_comunicar', 1003, 7, 'Ajuste de perfil aplicado'),
(3004, '2026-01-21 15:37:41', 'SIM no registra red', 'derivada', 1004, 5, 'Derivado a soporte nivel 2'),
(3005, '2026-01-21 15:37:41', 'Cortes intermitentes', 'derivada', 1006, 9, 'Cita programada: 2026-01-22 08:30:00'),
(9301, '2026-01-20 15:47:37', 'Router sin sincronizar', 'abierta', 9101, 2, 'Pendiente revisión'),
(9302, '2026-01-19 15:47:37', 'SIM sin cobertura', 'derivada', 9102, 3, 'Cita programada: 2026-01-23 12:00:00'),
(9303, '2026-01-18 15:47:37', 'Latencia alta', 'abierta', 9103, 7, 'Pendiente pruebas'),
(9304, '2026-01-17 15:47:37', 'Cortes intermitentes', 'abierta', 9104, 5, 'Pendiente monitorización'),
(9305, '2026-01-16 15:47:37', 'Baja velocidad fibra', 'solucionada', 9105, 2, 'Se resetea equipo y queda solucionada'),
(9306, '2026-01-20 15:47:37', 'SIM no registra red', 'abierta', 9106, 8, 'Pendiente reprovision'),
(9307, '2026-01-19 15:47:37', 'Microcortes FTTH', 'abierta', 9106, 2, 'Pendiente revisión onsite'),
(9308, '2026-01-18 15:47:37', 'Activación SIM fallida', 'abierta', 9107, 8, 'Pendiente activación manual'),
(9309, '2026-01-17 15:47:37', 'ONT reinicia solo', 'abierta', 9108, 7, 'Pendiente actualización firmware'),
(9310, '2026-01-16 15:47:37', 'SIM con baja cobertura', 'solucionada', 9109, 8, 'Repetida'),
(9311, '2026-01-20 15:47:37', 'Sin servicio total', 'solucionada', 9110, 8, 'solucionada'),
(9312, '2026-01-19 15:47:37', 'Cortes nocturnos', 'abierta', 9111, 8, 'Pendiente trazas'),
(9313, '2026-01-18 15:47:37', 'Baja velocidad (picos)', 'abierta', 9112, 2, 'Pendiente comunicar al cliente'),
(9314, '2026-01-17 15:47:37', 'SIM sin datos', 'abierta', 9113, 6, 'Pendiente reset APN'),
(9315, '2026-01-16 15:47:37', 'Router no entrega DHCP', 'abierta', 9112, 7, 'Pendiente reprovision'),
(9316, '2026-01-20 15:47:37', 'SIM no llama', 'abierta', 9114, 8, 'Pendiente comprobar IMS'),
(9317, '2026-01-19 15:47:37', 'Fibra con potencia baja', 'solucionada', 9115, 2, 'CLiente indica que ya funciona'),
(9318, '2026-01-18 15:47:37', 'SIM duplicada reportada', 'abierta', 9115, 8, 'Pendiente verificación'),
(9319, '2026-01-17 15:47:37', 'ONT sin luz LOS', 'derivada', 9116, 3, 'Cita programada: 2026-01-23 12:30:00'),
(9320, '2026-01-16 15:47:37', 'SIM no registra 5G', 'abierta', 9117, 6, 'Pendiente forzar red'),
(9321, '2026-01-20 15:47:37', 'Caídas de velocidad', 'solucionada', 9118, 8, 'Arreglado'),
(9322, '2026-01-19 15:47:37', 'SIM sin SMS', 'abierta', 9118, 8, 'Pendiente reset de servicios'),
(9323, '2026-01-18 15:47:37', 'Problema NAT/CGNAT', 'solucionada', 9119, 8, 'prueba'),
(9324, '2026-01-17 15:47:37', 'SIM sin señal interior', 'solucionada', 9120, 8, 'no hay mentira'),
(9325, '2026-01-16 15:47:37', 'Cortes en llamadas', 'abierta', 9121, 2, 'Pendiente análisis QoS'),
(9326, '2026-01-20 15:47:37', 'Router se calienta', 'abierta', 9121, 6, 'Pendiente revisión'),
(9327, '2026-01-19 15:47:37', 'FTTH sin PPPoE', 'sin_comunicar', 9122, 7, 'Pendiente comunicación y reprovision'),
(9328, '2026-01-18 15:47:37', 'SIM con datos lentos', 'abierta', 9123, 5, 'Pendiente pruebas en campo'),
(9329, '2026-01-17 15:47:37', 'SIM no activa', 'abierta', 9124, 2, 'Pendiente activación'),
(9330, '2026-01-16 15:47:37', 'Cortes intermitentes 5G', 'abierta', 9124, 8, 'Pendiente trazas radio'),
(9331, '2026-01-20 15:47:37', 'DNS lento', 'abierta', 9103, 2, 'Pendiente cambiar DNS'),
(9332, '2026-01-19 15:47:37', 'Ping alto en juegos', 'sin_comunicar', 9109, 6, 'Pendiente comunicar optimización'),
(9333, '2026-01-18 15:47:37', 'Velocidad baja por WiFi', 'abierta', 9101, 7, 'Pendiente separar bandas'),
(9334, '2026-01-17 15:47:37', 'SIM sin roaming', 'abierta', 9113, 8, 'Pendiente activar roaming'),
(9335, '2026-01-16 15:47:37', 'Microcortes fibra', 'derivada', 9116, 10, 'Cita programada: 2026-01-22 18:30:00'),
(9336, '2026-01-20 15:47:37', 'SIM sin VoLTE', 'abierta', 9125, 8, 'Pendiente habilitar perfil'),
(9337, '2026-01-19 15:47:37', 'Baja cobertura en sótano', 'abierta', 9125, 2, 'Pendiente comunicar limitación zona'),
(9338, '2026-01-18 15:47:37', 'Router pierde configuración', 'abierta', 9122, 6, 'Pendiente revisar backup'),
(9339, '2026-01-17 15:47:37', 'SIM sin datos tras cambio', 'sin_comunicar', 9107, 7, 'Pendiente reprovision'),
(9340, '2026-01-16 15:47:37', 'Latencia variable', 'derivada', 9111, 3, 'Cita programada: 2026-01-23 13:30:00'),
(9341, '2026-01-01 15:05:00', 'Cambio de domicilio pendiente', 'derivada', 9124, 10, 'Cita programada: 2026-01-22 18:00:00'),
(9342, '2026-01-01 09:34:00', 'Router no enciende', 'abierta', 9117, 2, ''),
(9343, '2026-01-01 15:18:00', 'Configuración de puertos', 'abierta', 9111, 2, ''),
(9344, '2026-01-02 04:26:00', 'WIFI no cubre toda la casa', 'abierta', 9121, 2, ''),
(9345, '2026-01-02 11:41:00', 'Configuración de puertos', 'abierta', 9121, 5, ''),
(9346, '2026-01-03 18:56:00', 'Microcortes constantes', 'abierta', 63, 8, ''),
(9347, '2026-01-03 05:06:00', 'Internet lento en zona norte', 'abierta', 9115, 6, ''),
(9348, '2026-01-03 13:45:00', 'Microcortes constantes', 'abierta', 9115, 9, ''),
(9349, '2026-01-04 23:39:00', 'DNS no responde', 'abierta', 1001, 2, ''),
(9350, '2026-01-04 06:59:00', 'DNS no responde', 'abierta', 9108, 8, ''),
(9351, '2026-01-05 23:50:00', 'WIFI no cubre toda la casa', 'abierta', 9117, 2, ''),
(9352, '2026-01-05 16:25:00', 'Cambio de domicilio pendiente', 'derivada', 69, 10, 'Cita programada: 2026-01-23 12:00:00'),
(9353, '2026-01-05 15:52:00', 'Router no enciende', 'sin_comunicar', 9102, 6, ''),
(9354, '2026-01-05 23:38:00', 'DNS no responde', 'abierta', 9123, 9, ''),
(9355, '2026-01-06 17:56:00', 'DNS no responde', 'abierta', 65, 3, ''),
(9356, '2026-01-06 06:12:00', 'Internet lento en zona norte', 'abierta', 74, 2, ''),
(9357, '2026-01-06 11:18:00', 'Router no enciende', 'abierta', 9115, 7, ''),
(9358, '2026-01-07 10:49:00', 'Internet lento en zona norte', 'sin_comunicar', 9123, 6, ''),
(9359, '2026-01-08 21:51:00', 'Cambio de domicilio pendiente', 'abierta', 9103, 6, ''),
(9360, '2026-01-09 00:52:00', 'WIFI no cubre toda la casa', 'abierta', 67, 2, ''),
(9361, '2026-01-09 09:39:00', 'Internet lento en zona norte', 'abierta', 68, 7, ''),
(9362, '2026-01-09 20:39:00', 'WIFI no cubre toda la casa', 'abierta', 9108, 9, ''),
(9363, '2026-01-09 01:23:00', 'Router no enciende', 'abierta', 9122, 7, ''),
(9364, '2026-01-10 22:39:00', 'Configuración de puertos', 'abierta', 66, 2, ''),
(9365, '2026-01-11 20:24:00', 'Error en factura y servicio', 'abierta', 9111, 2, ''),
(9366, '2026-01-11 14:58:00', 'Internet lento en zona norte', 'abierta', 70, 2, ''),
(9367, '2026-01-12 20:51:00', 'Cambio de domicilio pendiente', 'abierta', 9124, 2, ''),
(9368, '2026-01-12 23:08:00', 'WIFI no cubre toda la casa', 'abierta', 9106, 9, ''),
(9369, '2026-01-12 23:14:00', 'WIFI no cubre toda la casa', 'abierta', 9117, 8, ''),
(9370, '2026-01-13 00:58:00', 'Internet lento en zona norte', 'sin_comunicar', 62, 8, ''),
(9371, '2026-01-13 07:22:00', 'Cambio de domicilio pendiente', 'abierta', 1003, 2, ''),
(9372, '2026-01-13 08:55:00', 'Microcortes constantes', 'sin_comunicar', 63, 8, ''),
(9373, '2026-01-14 16:16:00', 'Sin señal 5G', 'abierta', 9109, 10, ''),
(9374, '2026-01-14 09:30:00', 'Error en factura y servicio', 'sin_comunicar', 9113, 6, ''),
(9375, '2026-01-15 13:20:00', 'Cambio de domicilio pendiente', 'abierta', 9111, 2, ''),
(9376, '2026-01-17 14:57:00', 'Configuración de puertos', 'abierta', 9116, 2, ''),
(9377, '2026-01-17 17:05:00', 'Microcortes constantes', 'sin_comunicar', 9124, 6, ''),
(9378, '2026-01-18 18:22:00', 'WIFI no cubre toda la casa', 'sin_comunicar', 1005, 6, ''),
(9379, '2026-01-19 20:29:00', 'Internet lento en zona norte', 'abierta', 9118, 5, ''),
(9380, '2026-01-20 16:49:00', 'Cambio de domicilio pendiente', 'sin_comunicar', 65, 9, ''),
(9381, '2026-01-20 11:29:00', 'Router no enciende', 'abierta', 9121, 1, ''),
(9382, '2026-01-20 13:14:00', 'Internet lento en zona norte', 'abierta', 9110, 1, ''),
(9383, '2026-01-21 11:21:00', 'DNS no responde', 'abierta', 68, 7, ''),
(9384, '2026-01-21 06:03:00', 'Router no enciende', 'abierta', 9118, 2, ''),
(9385, '2026-01-22 13:53:00', 'Sin señal 5G', 'abierta', 9106, 9, '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `logs`
--

CREATE TABLE `logs` (
  `id_log` int(11) NOT NULL,
  `estado` varchar(10) NOT NULL,
  `panel` varchar(50) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `fecha_hora` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `logs`
--

INSERT INTO `logs` (`id_log`, `estado`, `panel`, `descripcion`, `fecha_hora`) VALUES
(1, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 09:04:15'),
(2, 'ERROR', 'IncidPanel', 'Intento de solucionar sin seleccionar fila.', '2026-01-22 09:04:22'),
(3, 'ERROR', 'IncidPanel', 'Intento de solucionar sin seleccionar fila.', '2026-01-22 09:04:23'),
(4, 'ERROR', 'IncidPanel', 'Intento de solucionar sin seleccionar fila.', '2026-01-22 09:04:24'),
(5, 'ERROR', 'IncidPanel', 'Intento de solucionar sin seleccionar fila.', '2026-01-22 09:04:27'),
(6, 'OK', 'IncidPanel', 'Incidencia 9337 comunicada.', '2026-01-22 09:06:34'),
(7, 'OK', 'IncidPanel', 'Incidencia 9313 comunicada.', '2026-01-22 09:06:39'),
(8, 'OK', 'IncidPanel', 'Comentario añadido a incidencia 9313', '2026-01-22 09:06:50'),
(9, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 09:11:36'),
(10, 'ERROR', 'IncidPanel', 'Intento de solucionar sin seleccionar fila.', '2026-01-22 09:11:57'),
(11, 'ERROR', 'IncidPanel', 'Intento de solucionar sin seleccionar fila.', '2026-01-22 09:12:05'),
(12, 'ERROR', 'IncidPanel', 'Intento de solucionar sin seleccionar fila.', '2026-01-22 09:12:09'),
(13, 'OK', 'IncidPanel', 'Comentario añadido a incidencia 9311', '2026-01-22 09:12:39'),
(14, 'OK', 'IncidPanel', 'Incidencia 9317 marcada como solucionada.', '2026-01-22 09:12:49'),
(15, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 09:15:50'),
(16, 'ERROR', 'IncidPanel', 'Intento de solucionar sin seleccionar fila.', '2026-01-22 09:15:58'),
(17, 'ERROR', 'IncidPanel', 'Intento de solucionar sin seleccionar fila.', '2026-01-22 09:16:16'),
(18, 'OK', 'AdminPanel', 'Dispositivo liberado del contrato 1001', '2026-01-22 09:16:33'),
(19, 'OK', 'AdminPanel', 'Asignado dispositivo 2006 a contrato 1002', '2026-01-22 09:16:47'),
(20, 'ERROR', 'AdminPanel', 'Error añadiendo número 695955959 a dispositivo 2006', '2026-01-22 09:16:50'),
(21, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 09:19:48'),
(22, 'ERROR', 'IncidPanel', 'Intento de solucionar sin seleccionar fila.', '2026-01-22 09:19:51'),
(23, 'OK', 'AdminPanel', 'Asignado dispositivo 2001 a contrato 72', '2026-01-22 09:20:10'),
(24, 'OK', 'AdminPanel', 'Asignado dispositivo 9511 a contrato 73', '2026-01-22 09:20:38'),
(25, 'OK', 'AdminPanel', 'Añadido número 744744744 a dispositivo 9511', '2026-01-22 09:20:39'),
(26, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 09:25:46'),
(27, 'ERROR', 'IncidPanel', 'Intento de solucionar sin seleccionar fila.', '2026-01-22 09:25:55'),
(28, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 09:32:07'),
(29, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 10:22:10'),
(30, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 10:22:10'),
(31, 'OK', 'EstadisPanel', 'Estadísticas actualizadas. Pendientes: 29, Resueltas: 2', '2026-01-22 10:22:10'),
(32, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 10:22:10'),
(33, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (40 registros)', '2026-01-22 10:22:12'),
(34, 'ERROR', 'IncidPanel', 'Intento de solucionar sin seleccionar fila.', '2026-01-22 10:22:26'),
(35, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (40 registros)', '2026-01-22 10:24:25'),
(36, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (40 registros)', '2026-01-22 10:24:27'),
(37, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 11:14:38'),
(38, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 11:14:38'),
(39, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 11:14:38'),
(40, 'OK', 'EstadisPanel', 'Estadísticas actualizadas. Pendientes: 29, Resueltas: 2', '2026-01-22 11:14:38'),
(41, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 11:14:38'),
(42, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (40 registros)', '2026-01-22 11:15:18'),
(43, 'OK', 'EstadisPanel', 'Estadísticas actualizadas. Pendientes: 29, Resueltas: 2', '2026-01-22 11:15:29'),
(44, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 11:18:02'),
(45, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 11:18:02'),
(46, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 11:18:02'),
(47, 'OK', 'EstadisPanel', 'Estadísticas actualizadas. Pendientes: 29, Resueltas: 2', '2026-01-22 11:18:02'),
(48, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 11:18:02'),
(49, 'WARNING', 'AdminPanel', 'Intento de liberar dispositivo sin selección válida.', '2026-01-22 11:18:05'),
(50, 'WARNING', 'AdminPanel', 'Intento de asignar dispositivo sin contrato seleccionado.', '2026-01-22 11:18:17'),
(51, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 11:19:34'),
(52, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 11:19:34'),
(53, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 11:19:34'),
(54, 'OK', 'EstadisPanel', 'Estadísticas actualizadas. Pendientes: 29, Resueltas: 2', '2026-01-22 11:19:34'),
(55, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 11:19:34'),
(56, 'WARNING', 'AdminPanel', 'Intento de liberar dispositivo sin selección válida.', '2026-01-22 11:19:39'),
(57, 'WARNING', 'AdminPanel', 'Intento de asignar dispositivo sin contrato seleccionado.', '2026-01-22 11:19:58'),
(58, 'WARNING', 'AparatosPanel', 'Intento de diagnóstico sin aparato seleccionado.', '2026-01-22 11:20:01'),
(59, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (40 registros)', '2026-01-22 11:20:07'),
(60, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9223', '2026-01-22 11:20:31'),
(61, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9223', '2026-01-22 11:20:39'),
(62, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 11:25:41'),
(63, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 11:25:41'),
(64, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 11:25:41'),
(65, 'OK', 'EstadisPanel', 'Estadísticas actualizadas. Pendientes: 29, Resueltas: 2', '2026-01-22 11:25:41'),
(66, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 11:25:41'),
(67, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 11:28:33'),
(68, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 11:28:33'),
(69, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 11:28:33'),
(70, 'OK', 'EstadisPanel', 'Estadísticas actualizadas. Pendientes: 29, Resueltas: 2', '2026-01-22 11:28:33'),
(71, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 11:28:33'),
(72, 'WARNING', 'AdminPanel', 'Intento de asignar dispositivo sin contrato seleccionado.', '2026-01-22 11:29:06'),
(73, 'WARNING', 'AdminPanel', 'Intento de liberar dispositivo sin selección válida.', '2026-01-22 11:29:21'),
(74, 'OK', 'EstadisPanel', 'Estadísticas actualizadas. Pendientes: 29, Resueltas: 2', '2026-01-22 11:29:37'),
(75, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 11:33:57'),
(76, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 11:33:57'),
(77, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 11:33:57'),
(78, 'OK', 'EstadisPanel', 'Estadísticas actualizadas. Pendientes: 29, Resueltas: 2', '2026-01-22 11:33:57'),
(79, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 11:33:57'),
(80, 'OK', 'EstadisPanel', 'Estadísticas actualizadas. Pendientes: 29, Resueltas: 2', '2026-01-22 11:34:03'),
(81, 'OK', 'EstadisPanel', 'Estadísticas actualizadas. Pendientes: 29, Resueltas: 2', '2026-01-22 11:34:06'),
(82, 'OK', 'EstadisPanel', 'Estadísticas actualizadas. Pendientes: 29, Resueltas: 2', '2026-01-22 11:34:07'),
(83, 'OK', 'EstadisPanel', 'Estadísticas actualizadas. Pendientes: 29, Resueltas: 2', '2026-01-22 11:34:08'),
(84, 'OK', 'EstadisPanel', 'Estadísticas actualizadas. Pendientes: 29, Resueltas: 2', '2026-01-22 11:34:10'),
(85, 'OK', 'EstadisPanel', 'Estadísticas actualizadas. Pendientes: 29, Resueltas: 2', '2026-01-22 11:34:11'),
(86, 'OK', 'EstadisPanel', 'Estadísticas actualizadas. Pendientes: 29, Resueltas: 2', '2026-01-22 11:34:12'),
(87, 'OK', 'EstadisPanel', 'Estadísticas actualizadas. Pendientes: 29, Resueltas: 2', '2026-01-22 11:34:15'),
(88, 'OK', 'EstadisPanel', 'Estadísticas actualizadas. Pendientes: 29, Resueltas: 2', '2026-01-22 11:34:16'),
(89, 'OK', 'EstadisPanel', 'Estadísticas actualizadas. Pendientes: 29, Resueltas: 2', '2026-01-22 11:34:21'),
(90, 'OK', 'EstadisPanel', 'Estadísticas actualizadas. Pendientes: 29, Resueltas: 2', '2026-01-22 11:34:31'),
(91, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 11:36:26'),
(92, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 11:36:26'),
(93, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 11:36:26'),
(94, 'OK', 'EstadisPanel', 'Estadísticas actualizadas. Pendientes: 29, Resueltas: 2', '2026-01-22 11:36:26'),
(95, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 11:36:27'),
(96, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (40 registros)', '2026-01-22 11:36:35'),
(97, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 11:39:00'),
(98, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 11:39:01'),
(99, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 11:39:01'),
(100, 'OK', 'EstadisPanel', 'Estadísticas actualizadas. Pendientes: 29, Resueltas: 2', '2026-01-22 11:39:01'),
(101, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 11:39:01'),
(102, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (40 registros)', '2026-01-22 11:39:04'),
(103, 'OK', 'EstadisPanel', 'Estadísticas actualizadas. Pendientes: 29, Resueltas: 2', '2026-01-22 11:39:16'),
(104, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 11:52:36'),
(105, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 11:52:36'),
(106, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 11:52:36'),
(107, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 11:52:36'),
(108, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 11:52:36'),
(109, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (40 registros)', '2026-01-22 11:52:48'),
(110, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 11:52:50'),
(111, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 11:53:06'),
(112, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 11:53:08'),
(113, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (40 registros)', '2026-01-22 11:53:12'),
(114, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 11:53:14'),
(115, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (40 registros)', '2026-01-22 11:53:16'),
(116, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 11:53:24'),
(117, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 12:09:18'),
(118, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 12:09:18'),
(119, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 12:09:18'),
(120, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 12:09:19'),
(121, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 12:09:19'),
(122, 'ERROR', 'utilities.ejecutarConsulta', 'Error ejecutando consulta: Unknown column \'c.plan\' in \'field list\'', '2026-01-22 12:09:22'),
(123, 'ERROR', 'utilities.ejecutarConsulta', 'Error ejecutando consulta: Unknown column \'c.plan\' in \'field list\'', '2026-01-22 12:09:32'),
(124, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (40 registros)', '2026-01-22 12:09:36'),
(125, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 12:09:42'),
(126, 'ERROR', 'utilities.ejecutarConsulta', 'Error ejecutando consulta: Unknown column \'c.plan\' in \'field list\'', '2026-01-22 12:09:43'),
(127, 'ERROR', 'utilities.ejecutarConsulta', 'Error ejecutando consulta: Unknown column \'c.plan\' in \'field list\'', '2026-01-22 12:10:38'),
(128, 'ERROR', 'utilities.ejecutarConsulta', 'Error ejecutando consulta: Unknown column \'c.plan\' in \'field list\'', '2026-01-22 12:11:16'),
(129, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 12:16:29'),
(130, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 12:16:29'),
(131, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 12:16:29'),
(132, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 12:16:30'),
(133, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 12:16:30'),
(134, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 12:22:41'),
(135, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 12:22:41'),
(136, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 12:22:41'),
(137, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 12:22:41'),
(138, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 12:22:41'),
(139, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 12:59:51'),
(140, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 12:59:51'),
(141, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 12:59:52'),
(142, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 12:59:52'),
(143, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 12:59:52'),
(144, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (40 registros)', '2026-01-22 12:59:57'),
(145, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 13:00:44'),
(146, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (40 registros)', '2026-01-22 13:00:52'),
(147, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 13:00:52'),
(148, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 13:01:31'),
(149, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 13:01:32'),
(150, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 13:01:32'),
(151, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 13:01:32'),
(152, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 13:01:32'),
(153, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (85 registros)', '2026-01-22 13:02:16'),
(154, 'OK', 'IncidPanel', 'Incidencia 9342 comunicada.', '2026-01-22 13:02:22'),
(155, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (85 registros)', '2026-01-22 13:02:22'),
(156, 'OK', 'IncidPanel', 'Incidencia 9349 comunicada.', '2026-01-22 13:02:38'),
(157, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (85 registros)', '2026-01-22 13:02:38'),
(158, 'OK', 'IncidPanel', 'Incidencia 9341 comunicada.', '2026-01-22 13:02:48'),
(159, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (85 registros)', '2026-01-22 13:02:48'),
(160, 'OK', 'IncidPanel', 'Cita agendada para incidencia 9341', '2026-01-22 13:03:05'),
(161, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (85 registros)', '2026-01-22 13:03:05'),
(162, 'WARNING', 'AparatosPanel', 'Intento de diagnóstico sin aparato seleccionado.', '2026-01-22 13:03:21'),
(163, 'WARNING', 'AparatosPanel', 'Intento de diagnóstico sin aparato seleccionado.', '2026-01-22 13:03:26'),
(164, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:03:29'),
(165, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 13:23:28'),
(166, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 13:23:29'),
(167, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 13:23:29'),
(168, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 13:23:29'),
(169, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 13:23:29'),
(170, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:23:37'),
(171, 'WARNING', 'AparatosPanel', 'Intento de diagnóstico sin aparato seleccionado.', '2026-01-22 13:23:54'),
(172, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:23:55'),
(173, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9214', '2026-01-22 13:23:57'),
(174, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9214', '2026-01-22 13:24:08'),
(175, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:24:09'),
(176, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:24:13'),
(177, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:25:03'),
(178, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 13:28:55'),
(179, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 13:28:55'),
(180, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 13:28:55'),
(181, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 13:28:55'),
(182, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 13:28:55'),
(183, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:28:58'),
(184, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 2005', '2026-01-22 13:29:01'),
(185, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 2005', '2026-01-22 13:29:11'),
(186, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:29:25'),
(187, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 2006', '2026-01-22 13:29:42'),
(188, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 2006', '2026-01-22 13:29:54'),
(189, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:30:02'),
(190, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9210', '2026-01-22 13:30:06'),
(191, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9210', '2026-01-22 13:30:23'),
(192, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 2005', '2026-01-22 13:30:44'),
(193, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 2005', '2026-01-22 13:30:55'),
(194, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:30:58'),
(195, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9210', '2026-01-22 13:30:59'),
(196, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 13:34:17'),
(197, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 13:34:17'),
(198, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 13:34:17'),
(199, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 13:34:18'),
(200, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 13:34:18'),
(201, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:34:21'),
(202, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9223', '2026-01-22 13:34:24'),
(203, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9223', '2026-01-22 13:34:35'),
(204, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:34:39'),
(205, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9210', '2026-01-22 13:34:41'),
(206, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9210', '2026-01-22 13:34:50'),
(207, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9210', '2026-01-22 13:35:16'),
(208, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9210', '2026-01-22 13:35:16'),
(209, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9210', '2026-01-22 13:35:29'),
(210, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:35:31'),
(211, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9210', '2026-01-22 13:35:32'),
(212, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9210', '2026-01-22 13:37:16'),
(213, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 13:42:05'),
(214, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 13:42:05'),
(215, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 13:42:05'),
(216, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 13:42:06'),
(217, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 13:42:06'),
(218, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:42:09'),
(219, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9212', '2026-01-22 13:42:12'),
(220, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9212', '2026-01-22 13:42:24'),
(221, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:42:37'),
(222, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 2005', '2026-01-22 13:42:41'),
(223, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 2005', '2026-01-22 13:42:55'),
(224, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 13:43:09'),
(225, 'OK', 'AdminPanel', 'Asignado dispositivo 9502 a contrato 74', '2026-01-22 13:43:32'),
(226, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 13:43:32'),
(227, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 13:43:39'),
(228, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:43:41'),
(229, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:44:54'),
(230, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 13:49:12'),
(231, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 13:49:12'),
(232, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 13:49:12'),
(233, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 13:49:12'),
(234, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 13:49:12'),
(235, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:49:14'),
(236, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:49:23'),
(237, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 13:49:24'),
(238, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:49:45'),
(239, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:50:01'),
(240, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 13:50:02'),
(241, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:50:04'),
(242, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:50:36'),
(243, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:51:31'),
(244, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 13:51:32'),
(245, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:51:54'),
(246, 'OK', 'IncidPanel', 'Incidencia 9366 comunicada.', '2026-01-22 13:52:05'),
(247, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:52:05'),
(248, 'OK', 'IncidPanel', 'Incidencia 9319 comunicada.', '2026-01-22 13:52:12'),
(249, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:52:12'),
(250, 'OK', 'IncidPanel', 'Búsqueda realizada con filtro: sin_comunicar', '2026-01-22 13:52:23'),
(251, 'OK', 'IncidPanel', 'Incidencia 9367 comunicada.', '2026-01-22 13:52:26'),
(252, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (25 registros)', '2026-01-22 13:52:27'),
(253, 'OK', 'IncidPanel', 'Incidencia 9376 comunicada.', '2026-01-22 13:52:29'),
(254, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (24 registros)', '2026-01-22 13:52:29'),
(255, 'OK', 'IncidPanel', 'Incidencia 9343 comunicada.', '2026-01-22 13:52:31'),
(256, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (23 registros)', '2026-01-22 13:52:31'),
(257, 'OK', 'IncidPanel', 'Incidencia 9344 comunicada.', '2026-01-22 13:52:34'),
(258, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (22 registros)', '2026-01-22 13:52:34'),
(259, 'OK', 'IncidPanel', 'Incidencia 9360 comunicada.', '2026-01-22 13:52:36'),
(260, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (21 registros)', '2026-01-22 13:52:36'),
(261, 'OK', 'IncidPanel', 'Incidencia 9364 comunicada.', '2026-01-22 13:52:37'),
(262, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (20 registros)', '2026-01-22 13:52:37'),
(263, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 13:52:43'),
(264, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (19 registros)', '2026-01-22 13:52:44'),
(265, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (19 registros)', '2026-01-22 13:53:02'),
(266, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 13:55:13'),
(267, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 13:55:13'),
(268, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 13:55:13'),
(269, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 13:55:13'),
(270, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 13:55:13'),
(271, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:55:15'),
(272, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:55:22'),
(273, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 13:55:23'),
(274, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:55:23'),
(275, 'WARNING', 'AparatosPanel', 'Intento de búsqueda con campo vacío.', '2026-01-22 13:55:33'),
(276, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:55:36'),
(277, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 13:56:32'),
(278, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 13:56:32'),
(279, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 13:56:33'),
(280, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 13:56:33'),
(281, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 13:56:33'),
(282, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:56:35'),
(283, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 13:57:14'),
(284, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 14:57:24'),
(285, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 14:57:24'),
(286, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 14:57:24'),
(287, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 14:57:24'),
(288, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 14:57:24'),
(289, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 14:57:28'),
(290, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 15:00:57'),
(291, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 15:00:57'),
(292, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 15:00:58'),
(293, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 15:00:58'),
(294, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 15:00:58'),
(295, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 15:01:02'),
(296, 'OK', 'IncidPanel', 'Comentario añadido a incidencia 9311', '2026-01-22 15:01:09'),
(297, 'OK', 'IncidPanel', 'Incidencia 9321 marcada como solucionada.', '2026-01-22 15:01:31'),
(298, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (84 registros)', '2026-01-22 15:01:31'),
(299, 'OK', 'IncidPanel', 'Cita agendada para incidencia 9335', '2026-01-22 15:01:40'),
(300, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (83 registros)', '2026-01-22 15:01:40'),
(301, 'OK', 'IncidPanel', 'Incidencia 9310 marcada como solucionada.', '2026-01-22 15:01:51'),
(302, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (82 registros)', '2026-01-22 15:01:51'),
(303, 'OK', 'IncidPanel', 'Incidencia 9316 comunicada.', '2026-01-22 15:01:55'),
(304, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (81 registros)', '2026-01-22 15:01:55'),
(305, 'OK', 'IncidPanel', 'Búsqueda realizada con filtro: sim no llama', '2026-01-22 15:02:11'),
(306, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (1 registros)', '2026-01-22 15:03:07'),
(307, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 15:07:30'),
(308, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 15:07:30'),
(309, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 15:07:30'),
(310, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 15:07:30'),
(311, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 15:07:30'),
(312, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (81 registros)', '2026-01-22 15:07:34'),
(313, 'OK', 'IncidPanel', 'Búsqueda realizada con filtro: prueba', '2026-01-22 15:07:37'),
(314, 'OK', 'IncidPanel', 'Búsqueda realizada con filtro: 9', '2026-01-22 15:07:41'),
(315, 'OK', 'IncidPanel', 'Búsqueda realizada con filtro: sim', '2026-01-22 15:07:44'),
(316, 'OK', 'IncidPanel', 'Filtro de búsqueda limpiado.', '2026-01-22 15:07:53'),
(317, 'OK', 'IncidPanel', 'Comentario añadido a incidencia 9311', '2026-01-22 15:08:03'),
(318, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 15:17:41'),
(319, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 15:18:46'),
(320, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 15:18:46'),
(321, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 15:18:46'),
(322, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 15:18:46'),
(323, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 15:18:46'),
(324, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (81 registros)', '2026-01-22 15:18:49'),
(325, 'OK', 'IncidPanel', 'Comentario añadido a incidencia 9381', '2026-01-22 15:18:58'),
(326, 'OK', 'IncidPanel', 'Búsqueda realizada con filtro: 78', '2026-01-22 15:21:27'),
(327, 'OK', 'IncidPanel', 'Filtro de búsqueda limpiado.', '2026-01-22 15:21:28'),
(328, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 15:21:38'),
(329, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (81 registros)', '2026-01-22 15:21:39'),
(330, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (81 registros)', '2026-01-22 15:21:48'),
(331, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 15:21:48'),
(332, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (81 registros)', '2026-01-22 15:21:49'),
(333, 'OK', 'MainFrame', 'Usuario admin cerró sesión.', '2026-01-22 15:22:06'),
(334, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 15:22:18'),
(335, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: palitos', '2026-01-22 15:22:18'),
(336, 'OK', 'Login', 'Usuario palitos@netfix.es ha iniciado sesión.', '2026-01-22 15:22:18'),
(337, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 15:22:18'),
(338, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 15:22:18'),
(339, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (81 registros)', '2026-01-22 15:22:24'),
(340, 'OK', 'MainFrame', 'Usuario palitos cerró sesión.', '2026-01-22 15:22:26'),
(341, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 15:38:41'),
(342, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 15:38:41'),
(343, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 15:38:41'),
(344, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 15:38:42'),
(345, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 15:38:42'),
(346, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (81 registros)', '2026-01-22 15:38:56'),
(347, 'OK', 'IncidPanel', 'Comentario añadido a incidencia 9311', '2026-01-22 15:39:01'),
(348, 'OK', 'IncidPanel', 'Incidencia 9311 marcada como solucionada.', '2026-01-22 15:39:07'),
(349, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (81 registros)', '2026-01-22 15:39:07'),
(350, 'OK', 'IncidPanel', 'Comentario añadido a incidencia 9343', '2026-01-22 15:39:21'),
(351, 'OK', 'IncidPanel', 'Incidencia 9322 comunicada.', '2026-01-22 15:39:25'),
(352, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (80 registros)', '2026-01-22 15:39:25'),
(353, 'OK', 'IncidPanel', 'Incidencia 9322 comunicada.', '2026-01-22 15:39:30'),
(354, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (80 registros)', '2026-01-22 15:39:30'),
(355, 'WARNING', 'AparatosPanel', 'Intento de búsqueda con campo vacío.', '2026-01-22 15:39:41'),
(356, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (80 registros)', '2026-01-22 15:39:41'),
(357, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9225', '2026-01-22 15:39:45'),
(358, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9225', '2026-01-22 15:39:54'),
(359, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 16:35:10'),
(360, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 16:35:10'),
(361, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 16:35:10'),
(362, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 16:35:11'),
(363, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 16:35:11'),
(364, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (80 registros)', '2026-01-22 16:35:16'),
(365, 'OK', 'IncidPanel', 'Incidencia 9323 marcada como solucionada.', '2026-01-22 16:35:21'),
(366, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (80 registros)', '2026-01-22 16:35:21'),
(367, 'OK', 'IncidPanel', 'Incidencia 9334 comunicada.', '2026-01-22 16:35:25'),
(368, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (79 registros)', '2026-01-22 16:35:25'),
(369, 'OK', 'IncidPanel', 'Cita agendada para incidencia 9340', '2026-01-22 16:35:48'),
(370, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (79 registros)', '2026-01-22 16:35:48'),
(371, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (78 registros)', '2026-01-22 16:35:54'),
(372, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9207', '2026-01-22 16:35:55'),
(373, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9207', '2026-01-22 16:36:05'),
(374, 'OK', 'AdminPanel', 'Asignado dispositivo 9503 a contrato 71', '2026-01-22 16:36:43'),
(375, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 16:36:43'),
(376, 'OK', 'AdminPanel', 'Asignado dispositivo 9512 a contrato 61', '2026-01-22 16:36:55'),
(377, 'OK', 'AdminPanel', 'Añadido número 626626626 a dispositivo 9512', '2026-01-22 16:36:57'),
(378, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 16:36:57'),
(379, 'WARNING', 'AparatosPanel', 'Intento de búsqueda con campo vacío.', '2026-01-22 16:37:34'),
(380, 'WARNING', 'AparatosPanel', 'Intento de búsqueda con campo vacío.', '2026-01-22 16:37:47'),
(381, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 16:41:59'),
(382, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 16:41:59'),
(383, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 16:41:59'),
(384, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 16:41:59'),
(385, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 16:41:59'),
(386, 'WARNING', 'AparatosPanel', 'Intento de búsqueda con campo vacío.', '2026-01-22 16:42:13'),
(387, 'WARNING', 'AparatosPanel', 'Intento de búsqueda con campo vacío.', '2026-01-22 16:42:15'),
(388, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 16:42:23'),
(389, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 16:59:53'),
(390, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 16:59:53'),
(391, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 16:59:53'),
(392, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 16:59:53'),
(393, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 16:59:53'),
(394, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 17:06:21'),
(395, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 17:06:21'),
(396, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 17:06:22'),
(397, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 17:06:22'),
(398, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 17:06:22'),
(399, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (78 registros)', '2026-01-22 17:06:23'),
(400, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 17:06:25'),
(401, 'OK', 'MainFrame', 'Usuario admin cerró sesión.', '2026-01-22 17:06:27'),
(402, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 17:39:40'),
(403, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 17:39:40'),
(404, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 17:39:41'),
(405, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 17:39:41'),
(406, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 17:39:41'),
(407, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (78 registros)', '2026-01-22 17:39:47'),
(408, 'ERROR', 'IncidPanel', 'Intento de solucionar sin seleccionar fila.', '2026-01-22 17:39:51'),
(409, 'WARNING', 'AparatosPanel', 'Intento de búsqueda con campo vacío.', '2026-01-22 17:39:54'),
(410, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 20:11:04'),
(411, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 20:11:04'),
(412, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 20:11:04'),
(413, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 20:11:04'),
(414, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 20:11:04'),
(415, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (78 registros)', '2026-01-22 20:11:32'),
(416, 'OK', 'IncidPanel', 'Búsqueda realizada con filtro: 950', '2026-01-22 20:11:39'),
(417, 'OK', 'IncidPanel', 'Filtro de búsqueda limpiado.', '2026-01-22 20:11:41'),
(418, 'OK', 'IncidPanel', 'Cita agendada para incidencia 9352', '2026-01-22 20:12:02'),
(419, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (78 registros)', '2026-01-22 20:12:02'),
(420, 'OK', 'IncidPanel', 'Comentario añadido a incidencia 9307', '2026-01-22 20:12:07'),
(421, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9208', '2026-01-22 20:12:12'),
(422, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9208', '2026-01-22 20:12:34'),
(423, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (77 registros)', '2026-01-22 20:12:46'),
(424, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9233', '2026-01-22 20:12:49'),
(425, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9233', '2026-01-22 20:12:58'),
(426, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (77 registros)', '2026-01-22 20:13:06'),
(427, 'OK', 'IncidPanel', 'Incidencia 9324 marcada como solucionada.', '2026-01-22 20:13:11'),
(428, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (77 registros)', '2026-01-22 20:13:11'),
(429, 'OK', 'AdminPanel', 'Asignado dispositivo 9505 a contrato 1001', '2026-01-22 20:14:26'),
(430, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 20:14:26'),
(431, 'OK', 'AdminPanel', 'Dispositivo liberado del contrato 1001', '2026-01-22 20:14:31'),
(432, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 20:14:31'),
(433, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 20:15:18'),
(434, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 20:15:21'),
(435, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 20:15:23'),
(436, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-22 20:15:42'),
(437, 'OK', 'MainFrame', 'Usuario admin cerró sesión.', '2026-01-22 20:15:48'),
(438, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 20:16:19'),
(439, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: Juan Manuel Torronteras', '2026-01-22 20:16:19'),
(440, 'OK', 'Login', 'Usuario jmtorronteras@netfix.es ha iniciado sesión.', '2026-01-22 20:16:19'),
(441, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 20:16:19'),
(442, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 20:16:19'),
(443, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-22 20:16:24'),
(444, 'OK', 'MainFrame', 'Usuario Juan Manuel Torronteras cerró sesión.', '2026-01-22 20:16:41'),
(445, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 20:16:55'),
(446, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: Juan Manuel Torronteras', '2026-01-22 20:16:55'),
(447, 'OK', 'Login', 'Usuario jmtorronteras@netfix.es ha iniciado sesión.', '2026-01-22 20:16:55'),
(448, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 20:16:55'),
(449, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 20:16:56'),
(450, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-22 20:17:05'),
(451, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 20:17:06'),
(452, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-22 20:19:46'),
(453, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-22 20:19:54'),
(454, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 20:19:55'),
(455, 'OK', 'MainFrame', 'Usuario Juan Manuel Torronteras cerró sesión.', '2026-01-22 20:19:57'),
(456, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 20:20:24'),
(457, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-22 20:20:24'),
(458, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-22 20:20:24'),
(459, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 20:20:24'),
(460, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 20:20:24'),
(461, 'OK', 'MainFrame', 'Usuario admin cerró sesión.', '2026-01-22 20:21:10'),
(462, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-22 20:21:58'),
(463, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: Juan Manuel Torrenteras', '2026-01-22 20:21:58'),
(464, 'OK', 'Login', 'Usuario jmtorronteras@netfix.es ha iniciado sesión.', '2026-01-22 20:21:58'),
(465, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 20:21:58'),
(466, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-22 20:21:58'),
(467, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-22 20:22:12'),
(468, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-22 20:23:01'),
(469, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-23 09:15:53'),
(470, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-23 09:15:53'),
(471, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-23 09:15:53'),
(472, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 09:15:53'),
(473, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-23 09:15:53'),
(474, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 09:15:56'),
(475, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 09:16:05'),
(476, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 09:16:05'),
(477, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 09:16:06'),
(478, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-23 09:18:26'),
(479, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-23 09:18:26'),
(480, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-23 09:18:26'),
(481, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 09:18:27'),
(482, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-23 09:18:27'),
(483, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-23 09:20:30'),
(484, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-23 09:20:30'),
(485, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-23 09:20:30'),
(486, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 09:20:31'),
(487, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-23 09:20:31'),
(488, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 09:21:42'),
(489, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 09:21:49'),
(490, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 09:21:50'),
(491, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 09:21:53'),
(492, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 09:22:08'),
(493, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 09:22:08'),
(494, 'WARNING', 'AparatosPanel', 'Intento de diagnóstico sin aparato seleccionado.', '2026-01-23 09:22:23'),
(495, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 09:22:24'),
(496, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9212', '2026-01-23 09:22:26'),
(497, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9212', '2026-01-23 09:22:41'),
(498, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 09:22:45'),
(499, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 09:22:45'),
(500, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 09:22:46'),
(501, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 09:22:48'),
(502, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 09:22:52'),
(503, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 09:22:53'),
(504, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 09:22:53'),
(505, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-23 09:23:42'),
(506, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-23 09:23:42'),
(507, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-23 09:23:42'),
(508, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 09:23:43');
INSERT INTO `logs` (`id_log`, `estado`, `panel`, `descripcion`, `fecha_hora`) VALUES
(509, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-23 09:23:43'),
(510, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 09:23:44'),
(511, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-23 09:24:39'),
(512, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-23 09:24:40'),
(513, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-23 09:24:40'),
(514, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 09:24:40'),
(515, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-23 09:24:40'),
(516, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 09:24:51'),
(517, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-23 09:29:44'),
(518, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-23 09:29:44'),
(519, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-23 09:29:44'),
(520, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 09:29:45'),
(521, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-23 09:29:45'),
(522, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 09:29:50'),
(523, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-23 09:33:20'),
(524, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-23 09:33:20'),
(525, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-23 09:33:20'),
(526, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 09:33:21'),
(527, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-23 09:33:21'),
(528, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 09:33:23'),
(529, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 09:33:59'),
(530, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 09:34:00'),
(531, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 09:34:02'),
(532, 'OK', 'AdminPanel', 'Contratos cargados correctamente (67 registros)', '2026-01-23 10:21:09'),
(533, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-23 10:21:09'),
(534, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-23 10:21:09'),
(535, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 10:21:09'),
(536, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-23 10:21:09'),
(537, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 10:21:11'),
(538, 'WARNING', 'AparatosPanel', 'Intento de búsqueda con campo vacío.', '2026-01-23 10:21:17'),
(539, 'OK', 'AdminPanel', 'Eliminado número 5G: 600100002', '2026-01-23 10:21:50'),
(540, 'OK', 'AdminPanel', 'Dispositivo liberado del contrato 1002', '2026-01-23 10:21:54'),
(541, 'OK', 'AdminPanel', 'Contratos cargados correctamente (66 registros)', '2026-01-23 10:21:55'),
(542, 'OK', 'AdminPanel', 'Contratos cargados correctamente (66 registros)', '2026-01-23 10:25:48'),
(543, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-23 10:25:48'),
(544, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-23 10:25:48'),
(545, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 10:25:49'),
(546, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-23 10:25:49'),
(547, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 10:29:11'),
(548, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 10:29:12'),
(549, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 10:29:39'),
(550, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 10:29:53'),
(551, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 10:30:03'),
(552, 'OK', 'AdminPanel', 'Contratos cargados correctamente (66 registros)', '2026-01-23 10:31:02'),
(553, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-23 10:31:02'),
(554, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-23 10:31:02'),
(555, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 10:31:03'),
(556, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-23 10:31:03'),
(557, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 10:31:06'),
(558, 'OK', 'IncidPanel', 'Incidencia 9346 comunicada.', '2026-01-23 10:31:10'),
(559, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 10:31:10'),
(560, 'OK', 'IncidPanel', 'Incidencia 9346 comunicada.', '2026-01-23 10:31:13'),
(561, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 10:31:13'),
(562, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 10:31:49'),
(563, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 10:32:24'),
(564, 'OK', 'AdminPanel', 'Contratos cargados correctamente (66 registros)', '2026-01-23 10:37:14'),
(565, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-23 10:37:14'),
(566, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-23 10:37:14'),
(567, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 10:37:15'),
(568, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-23 10:37:15'),
(569, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 10:37:17'),
(570, 'OK', 'IncidPanel', 'Incidencia 9302 comunicada.', '2026-01-23 10:37:26'),
(571, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 10:37:27'),
(572, 'OK', 'IncidPanel', 'Comentario añadido a incidencia 9302', '2026-01-23 10:37:35'),
(573, 'OK', 'IncidPanel', 'Filtro de búsqueda limpiado.', '2026-01-23 10:37:45'),
(574, 'OK', 'IncidPanel', 'Cita agendada para incidencia 9302', '2026-01-23 10:37:53'),
(575, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (76 registros)', '2026-01-23 10:37:53'),
(576, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 10:38:13'),
(577, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (75 registros)', '2026-01-23 10:38:13'),
(578, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (75 registros)', '2026-01-23 10:38:23'),
(579, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 10:38:24'),
(580, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (75 registros)', '2026-01-23 10:38:24'),
(581, 'OK', 'MainFrame', 'Usuario admin cerró sesión.', '2026-01-23 10:38:36'),
(582, 'OK', 'AdminPanel', 'Contratos cargados correctamente (66 registros)', '2026-01-23 10:38:51'),
(583, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: Lucía Gómez', '2026-01-23 10:38:51'),
(584, 'OK', 'Login', 'Usuario lucia.gomez@netfix.com ha iniciado sesión.', '2026-01-23 10:38:51'),
(585, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 10:38:51'),
(586, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-23 10:38:51'),
(587, 'OK', 'MainFrame', 'Usuario Lucía Gómez cerró sesión.', '2026-01-23 10:39:02'),
(588, 'OK', 'AdminPanel', 'Contratos cargados correctamente (66 registros)', '2026-01-23 10:39:19'),
(589, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: Lucía Gómez', '2026-01-23 10:39:19'),
(590, 'OK', 'Login', 'Usuario lucia.gomez@netfix.com ha iniciado sesión.', '2026-01-23 10:39:19'),
(591, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 10:39:20'),
(592, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-23 10:39:20'),
(593, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (75 registros)', '2026-01-23 10:39:23'),
(594, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 10:39:23'),
(595, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (75 registros)', '2026-01-23 10:39:24'),
(596, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 10:41:11'),
(597, 'OK', 'MainFrame', 'Usuario Lucía Gómez cerró sesión.', '2026-01-23 10:41:13'),
(598, 'OK', 'AdminPanel', 'Contratos cargados correctamente (66 registros)', '2026-01-23 10:41:26'),
(599, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: Lucía Gómez', '2026-01-23 10:41:26'),
(600, 'OK', 'Login', 'Usuario lucia.gomez@netfix.com ha iniciado sesión.', '2026-01-23 10:41:26'),
(601, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 10:41:26'),
(602, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-23 10:41:26'),
(603, 'OK', 'MainFrame', 'Usuario Lucía Gómez cerró sesión.', '2026-01-23 10:41:31'),
(604, 'OK', 'AdminPanel', 'Contratos cargados correctamente (66 registros)', '2026-01-23 10:41:38'),
(605, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-23 10:41:38'),
(606, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-23 10:41:38'),
(607, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 10:41:38'),
(608, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-23 10:41:38'),
(609, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (75 registros)', '2026-01-23 10:46:43'),
(610, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 10:46:43'),
(611, 'OK', 'AdminPanel', 'Asignado dispositivo 9501 a contrato 1001', '2026-01-23 10:46:59'),
(612, 'OK', 'AdminPanel', 'Contratos cargados correctamente (66 registros)', '2026-01-23 10:46:59'),
(613, 'OK', 'AdminPanel', 'Contratos cargados correctamente (66 registros)', '2026-01-23 10:48:50'),
(614, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: Lucía Gómez', '2026-01-23 10:48:50'),
(615, 'OK', 'Login', 'Usuario lucia.gomez@netfix.com ha iniciado sesión.', '2026-01-23 10:48:50'),
(616, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 10:48:50'),
(617, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-23 10:48:50'),
(618, 'OK', 'AdminPanel', 'Contratos cargados correctamente (66 registros)', '2026-01-23 10:52:53'),
(619, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-23 10:52:53'),
(620, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-23 10:52:53'),
(621, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 10:52:54'),
(622, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-23 10:52:54'),
(623, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (75 registros)', '2026-01-23 10:52:57'),
(624, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (75 registros)', '2026-01-23 10:53:11'),
(625, 'OK', 'AdminPanel', 'Contratos cargados correctamente (66 registros)', '2026-01-23 10:58:46'),
(626, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-23 10:58:46'),
(627, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-23 10:58:46'),
(628, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 10:58:46'),
(629, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-23 10:58:46'),
(630, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (75 registros)', '2026-01-23 10:58:48'),
(631, 'WARNING', 'AparatosPanel', 'Intento de diagnóstico sin aparato seleccionado.', '2026-01-23 10:59:13'),
(632, 'WARNING', 'AparatosPanel', 'Intento de búsqueda con campo vacío.', '2026-01-23 10:59:23'),
(633, 'WARNING', 'AparatosPanel', 'Intento de diagnóstico sin aparato seleccionado.', '2026-01-23 10:59:25'),
(634, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (75 registros)', '2026-01-23 10:59:26'),
(635, 'OK', 'AdminPanel', 'Contratos cargados correctamente (66 registros)', '2026-01-23 11:04:19'),
(636, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: Lucía Gómez', '2026-01-23 11:04:19'),
(637, 'OK', 'Login', 'Usuario lucia.gomez@netfix.com ha iniciado sesión.', '2026-01-23 11:04:19'),
(638, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 11:04:19'),
(639, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-23 11:04:19'),
(640, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (75 registros)', '2026-01-23 11:04:24'),
(641, 'OK', 'IncidPanel', 'Cita agendada para incidencia 9319', '2026-01-23 11:04:40'),
(642, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (75 registros)', '2026-01-23 11:04:40'),
(643, 'OK', 'AdminPanel', 'Contratos cargados correctamente (66 registros)', '2026-01-23 11:16:07'),
(644, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-23 11:16:07'),
(645, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-23 11:16:07'),
(646, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 11:16:07'),
(647, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-23 11:16:07'),
(648, 'INFO', 'IncidenciaDetalleDialog', 'Iniciando vista detalle ID=9308', '2026-01-23 11:16:10'),
(649, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (74 registros)', '2026-01-23 11:16:13'),
(650, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9212', '2026-01-23 11:16:32'),
(651, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9212', '2026-01-23 11:16:43'),
(652, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9212', '2026-01-23 11:16:44'),
(653, 'WARNING', 'AdminPanel', 'Intento de liberar dispositivo sin selección válida.', '2026-01-23 11:16:56'),
(654, 'OK', 'AdminPanel', 'Contratos cargados correctamente (66 registros)', '2026-01-23 11:22:01'),
(655, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-23 11:22:01'),
(656, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-23 11:22:01'),
(657, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 11:22:01'),
(658, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-23 11:22:01'),
(659, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (74 registros)', '2026-01-23 11:22:03'),
(660, 'WARNING', 'AparatosPanel', 'Intento de búsqueda con campo vacío.', '2026-01-23 11:22:21'),
(661, 'WARNING', 'AparatosPanel', 'Intento de diagnóstico sin aparato seleccionado.', '2026-01-23 11:22:23'),
(662, 'WARNING', 'AdminPanel', 'Intento de asignar dispositivo sin contrato seleccionado.', '2026-01-23 11:22:38'),
(663, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 11:23:07'),
(664, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 11:23:16'),
(665, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 11:23:17'),
(666, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 11:23:21'),
(667, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 11:23:22'),
(668, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 11:23:24'),
(669, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 11:23:25'),
(670, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 11:23:26'),
(671, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (74 registros)', '2026-01-23 11:23:31'),
(672, 'OK', 'IncidPanel', 'Filtro de búsqueda limpiado.', '2026-01-23 11:23:33'),
(673, 'WARNING', 'AparatosPanel', 'Intento de búsqueda con campo vacío.', '2026-01-23 11:23:39'),
(674, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (74 registros)', '2026-01-23 11:23:48'),
(675, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 11:23:48'),
(676, 'OK', 'MainFrame', 'Usuario admin cerró sesión.', '2026-01-23 11:23:50'),
(677, 'OK', 'AdminPanel', 'Contratos cargados correctamente (66 registros)', '2026-01-23 11:43:04'),
(678, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-23 11:43:04'),
(679, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-23 11:43:04'),
(680, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-23 11:43:05'),
(681, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-23 11:43:05'),
(682, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (74 registros)', '2026-01-23 11:43:08'),
(683, 'OK', 'MainFrame', 'Usuario admin cerró sesión.', '2026-01-23 11:43:10'),
(684, 'OK', 'AdminPanel', 'Contratos cargados correctamente (66 registros)', '2026-01-26 09:49:08'),
(685, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-26 09:49:08'),
(686, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-26 09:49:08'),
(687, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-26 09:49:08'),
(688, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-26 09:49:08'),
(689, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (74 registros)', '2026-01-26 09:49:14'),
(690, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-26 09:49:18'),
(691, 'OK', 'AdminPanel', 'Contratos cargados correctamente (66 registros)', '2026-01-26 09:51:56'),
(692, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-26 09:51:56'),
(693, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-26 09:51:56'),
(694, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-26 09:51:56'),
(695, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-26 09:51:57'),
(696, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (74 registros)', '2026-01-26 09:52:12'),
(697, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (74 registros)', '2026-01-26 09:52:14'),
(698, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-26 09:52:14'),
(699, 'OK', 'AdminPanel', 'Contratos cargados correctamente (66 registros)', '2026-01-26 10:08:29'),
(700, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-26 10:08:29'),
(701, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-26 10:08:29'),
(702, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-26 10:08:30'),
(703, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-26 10:08:30'),
(704, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (74 registros)', '2026-01-26 10:08:31'),
(705, 'OK', 'IncidPanel', 'Incidencia 9308 comunicada.', '2026-01-26 10:08:37'),
(706, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (74 registros)', '2026-01-26 10:08:37'),
(707, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9221', '2026-01-26 10:09:06'),
(708, 'OK', 'AparatosPanel', 'Diagnóstico cargado correctamente para ID: 9221', '2026-01-26 10:09:14'),
(709, 'OK', 'AdminPanel', 'Dispositivo liberado del contrato 71', '2026-01-26 10:09:43'),
(710, 'OK', 'AdminPanel', 'Contratos cargados correctamente (66 registros)', '2026-01-26 10:09:44'),
(711, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (74 registros)', '2026-01-26 10:10:10'),
(712, 'OK', 'AdminPanel', 'Contratos cargados correctamente (66 registros)', '2026-01-26 11:46:47'),
(713, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-26 11:46:47'),
(714, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-26 11:46:47'),
(715, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-26 11:46:47'),
(716, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-26 11:46:48'),
(717, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (74 registros)', '2026-01-26 11:46:52'),
(718, 'OK', 'AdminPanel', 'Contratos cargados correctamente (66 registros)', '2026-01-26 12:20:59'),
(719, 'OK', 'MainFrame', 'Aplicación iniciada correctamente para usuario: admin', '2026-01-26 12:20:59'),
(720, 'OK', 'Login', 'Usuario admin@netfix.es ha iniciado sesión.', '2026-01-26 12:20:59'),
(721, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-26 12:21:00'),
(722, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (4 registros)', '2026-01-26 12:21:00'),
(723, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (74 registros)', '2026-01-26 12:21:01'),
(724, 'OK', 'IncidPanel', 'Datos de incidencias cargados correctamente (74 registros)', '2026-01-26 12:21:05'),
(725, 'OK', 'EstadisPanel', 'Dashboard Actualizado.', '2026-01-26 12:21:05');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `numeros`
--

CREATE TABLE `numeros` (
  `numero` int(9) NOT NULL,
  `id_contrato` int(11) NOT NULL,
  `id_aparato` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `numeros`
--

INSERT INTO `numeros` (`numero`, `id_contrato`, `id_aparato`) VALUES
(600100001, 1002, 2005),
(600200001, 1003, 2007),
(600300001, 1004, 2008),
(600400001, 1006, 2009),
(600400002, 1006, 2010),
(600500001, 1007, 2011),
(610000002, 9102, 9216),
(610000003, 9103, 9217),
(610000004, 9104, 9218),
(610000005, 9106, 9219),
(610000006, 9106, 9220),
(610000007, 9107, 9221),
(610000008, 9109, 9222),
(610000009, 9110, 9223),
(610000010, 9111, 9224),
(610000011, 9113, 9225),
(610000012, 9114, 9226),
(610000013, 9115, 9227),
(610000014, 9115, 9228),
(610000015, 9117, 9229),
(610000016, 9118, 9230),
(610000017, 9118, 9231),
(610000018, 9119, 9232),
(610000019, 9120, 9233),
(610000020, 9121, 9234),
(610000021, 9121, 9235),
(610000022, 9123, 9236),
(610000023, 9124, 9237),
(610000024, 9125, 9238),
(610000025, 9125, 9239),
(626626626, 61, 9512),
(659659659, 73, 9511);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `id_rol` int(4) NOT NULL,
  `descripcion` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`id_rol`, `descripcion`) VALUES
(1, 'Sistemas'),
(2, 'Supervisor'),
(3, 'Tecnico');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tecnicos`
--

CREATE TABLE `tecnicos` (
  `id_tecnico` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `especialidad` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tecnicos`
--

INSERT INTO `tecnicos` (`id_tecnico`, `id_usuario`, `especialidad`) VALUES
(1, 3, 'General'),
(2, 7, 'General'),
(4, 9, 'General'),
(5, 10, 'General'),
(6, 11, 'General'),
(7, 12, 'General'),
(9, 16, 'General');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_usuario` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `rol` enum('Sistemas','Tecnico','Supervisor') DEFAULT 'Tecnico',
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `nombre`, `rol`, `email`, `password`) VALUES
(1, 'Ana Torres', 'Supervisor', 'ana.torres@netfix.com', '$2a$10$XSOCqPhOq88h63Bm9RKJbupkO3OX7WljHjku2FdRXuLIpM6amCCp6'),
(2, 'Pedro Ruiz', 'Sistemas', 'pedro.ruiz@netfix.com', '$2a$10$zdqD0/oaeGWMwjHz3G/wm.KVFBhbeyqwkhyf4Wdj9ySKPGzf.Wyzi'),
(3, 'Lucía Gómez', 'Tecnico', 'lucia.gomez@netfix.com', '$2a$10$4OpnRjDJB5MTTxcxOGvZ/eu9NQQpSBbZyOXg/SIWSlMStSgi8x1ha'),
(5, 'Jose Luis Rodriguez', 'Sistemas', 'jrodriguez@netfix.es', '$2a$10$4RPjcwR2eudKYBIno6tZGObxvHCkb1qfZdFjZrsf5uGcOvzr8.AY6'),
(6, 'Prueba pruebas', 'Sistemas', 'pruebas@netfix.es', '$2a$10$WGTJniUQ/FVScooMu4AN1.b31R2JDmneHq6XZB8ZA9G/7EzEzLDNy'),
(7, 'palitos', 'Tecnico', 'palitos@netfix.es', '$2a$10$wxKDvIVeCzqTZVmBMyEUNeiTHtE3Oeq5XIjMevBtkFZtsslqwX14W'),
(8, 'admin', 'Sistemas', 'admin@netfix.es', '$2a$10$CaZyblBYc9lipFDmUhjH8e7bx.h6DQEv/FY88a9Qp5H.cY2GrJgcC'),
(9, 'Técnico Test 1', 'Tecnico', 'tecnico1@netfix.com', '$2a$10$/Ptlqtc1LvesoyPluOB4iec5cuUsio669WvPkpNFqeUUZw80pAiC2'),
(10, 'Técnico Test 2', 'Tecnico', 'tecnico2@netfix.com', '$2a$10$7wBmAlzqvPYWrFffTwHxduVnms4AhNicRxSkD9ulINuLYEGU5ibAO'),
(11, 'Usuario Tecnico', 'Tecnico', 'tecnico@netfix.es', '$2a$10$Cao1saZY8pMdUBYxCKU9HeohTVMiXUmQFPwbvXtaPUEnDuHi4Odsu'),
(12, 'ALta', 'Tecnico', 'alta@netfix.es', '$2a$10$Pj0kSeIvx10Rtwcj/PSvzehETAacM0flCh02a6DyZN.DA8GjDoFhi'),
(14, 'sistemas', 'Sistemas', 'sistemas@netfix,es', '$2a$10$sFbXSKBAkMohn/h1qYXnYu7V6TNyw5VEaAw8n/3vNrWi9FM9kO8MG'),
(15, 'Juan Manuel Torrenteras', 'Sistemas', 'jmtorronteras@netfix.es', '$2a$10$ZZmLoNfBTh5KNVowC6dtzeagPXMs9jVZQCgdo5vXibpVCEi8Jobnu'),
(16, 'Pruebas correo', 'Tecnico', 'pruebas@pruebas.es', '$2a$10$/X73x.1d7486KjKQNTAL3.mfdDllhnWvhnB34jsnqFiPd37P66SE2');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `agenda`
--
ALTER TABLE `agenda`
  ADD PRIMARY KEY (`id_cita`),
  ADD KEY `id_tecnico` (`id_tecnico`),
  ADD KEY `id_incidencia` (`id_incidencia`);

--
-- Indices de la tabla `aparatos`
--
ALTER TABLE `aparatos`
  ADD PRIMARY KEY (`id_aparato`),
  ADD UNIQUE KEY `numero_serie` (`numero_serie`),
  ADD UNIQUE KEY `mac` (`mac`),
  ADD KEY `id_contrato` (`id_contrato`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`dni`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indices de la tabla `contratos`
--
ALTER TABLE `contratos`
  ADD PRIMARY KEY (`id_contrato`),
  ADD KEY `dni_cliente` (`dni_cliente`);

--
-- Indices de la tabla `diagnostico`
--
ALTER TABLE `diagnostico`
  ADD PRIMARY KEY (`id_diagnostico`),
  ADD UNIQUE KEY `id_aparato` (`id_aparato`);

--
-- Indices de la tabla `horarios`
--
ALTER TABLE `horarios`
  ADD PRIMARY KEY (`id_horario`),
  ADD KEY `id_tecnico` (`id_tecnico`);

--
-- Indices de la tabla `incidencias`
--
ALTER TABLE `incidencias`
  ADD PRIMARY KEY (`id_incidencia`),
  ADD KEY `id_contrato` (`id_contrato`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- Indices de la tabla `logs`
--
ALTER TABLE `logs`
  ADD PRIMARY KEY (`id_log`);

--
-- Indices de la tabla `numeros`
--
ALTER TABLE `numeros`
  ADD PRIMARY KEY (`numero`),
  ADD UNIQUE KEY `id_aparato` (`id_aparato`),
  ADD KEY `fk_numeros_contratos` (`id_contrato`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id_rol`);

--
-- Indices de la tabla `tecnicos`
--
ALTER TABLE `tecnicos`
  ADD PRIMARY KEY (`id_tecnico`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `agenda`
--
ALTER TABLE `agenda`
  MODIFY `id_cita` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `aparatos`
--
ALTER TABLE `aparatos`
  MODIFY `id_aparato` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9521;

--
-- AUTO_INCREMENT de la tabla `contratos`
--
ALTER TABLE `contratos`
  MODIFY `id_contrato` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9126;

--
-- AUTO_INCREMENT de la tabla `diagnostico`
--
ALTER TABLE `diagnostico`
  MODIFY `id_diagnostico` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=88;

--
-- AUTO_INCREMENT de la tabla `horarios`
--
ALTER TABLE `horarios`
  MODIFY `id_horario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=81;

--
-- AUTO_INCREMENT de la tabla `incidencias`
--
ALTER TABLE `incidencias`
  MODIFY `id_incidencia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9386;

--
-- AUTO_INCREMENT de la tabla `logs`
--
ALTER TABLE `logs`
  MODIFY `id_log` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=726;

--
-- AUTO_INCREMENT de la tabla `tecnicos`
--
ALTER TABLE `tecnicos`
  MODIFY `id_tecnico` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `agenda`
--
ALTER TABLE `agenda`
  ADD CONSTRAINT `agenda_ibfk_1` FOREIGN KEY (`id_tecnico`) REFERENCES `tecnicos` (`id_tecnico`),
  ADD CONSTRAINT `agenda_ibfk_2` FOREIGN KEY (`id_incidencia`) REFERENCES `incidencias` (`id_incidencia`);

--
-- Filtros para la tabla `aparatos`
--
ALTER TABLE `aparatos`
  ADD CONSTRAINT `aparatos_ibfk_1` FOREIGN KEY (`id_contrato`) REFERENCES `contratos` (`id_contrato`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `contratos`
--
ALTER TABLE `contratos`
  ADD CONSTRAINT `contratos_ibfk_1` FOREIGN KEY (`dni_cliente`) REFERENCES `clientes` (`dni`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `diagnostico`
--
ALTER TABLE `diagnostico`
  ADD CONSTRAINT `diagnostico_ibfk_1` FOREIGN KEY (`id_aparato`) REFERENCES `aparatos` (`id_aparato`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `horarios`
--
ALTER TABLE `horarios`
  ADD CONSTRAINT `horarios_ibfk_1` FOREIGN KEY (`id_tecnico`) REFERENCES `tecnicos` (`id_tecnico`) ON DELETE CASCADE;

--
-- Filtros para la tabla `incidencias`
--
ALTER TABLE `incidencias`
  ADD CONSTRAINT `incidencias_ibfk_1` FOREIGN KEY (`id_contrato`) REFERENCES `contratos` (`id_contrato`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `incidencias_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Filtros para la tabla `numeros`
--
ALTER TABLE `numeros`
  ADD CONSTRAINT `fk_numeros_contratos` FOREIGN KEY (`id_contrato`) REFERENCES `contratos` (`id_contrato`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `numeros_ibfk_1` FOREIGN KEY (`id_aparato`) REFERENCES `aparatos` (`id_aparato`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `tecnicos`
--
ALTER TABLE `tecnicos`
  ADD CONSTRAINT `tecnicos_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
