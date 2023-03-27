-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 27-03-2023 a las 23:57:30
-- Versión del servidor: 10.4.24-MariaDB
-- Versión de PHP: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `drogueriav4`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `Registro_pedido` (IN `id_producto_v` INT, IN `id_usuario_v` INT, IN `fecha` DATE, IN `tipo_pedido` INT)   begin
		declare pedido_id int;
        declare precio int;
        declare total int;
		declare msg varchar(30);

		set pedido_id = (select MAX(id_pedido) from pedidos where id_estado_pedido = 1 and id_usuario = id_usuario_v);
        set precio = (select precio_venta from producto where id_producto = id_producto_v);
 
		if pedido_id is null then
			insert into `pedidos` (`id_pedido`, `precio_pedido`, `id_estado_pedido`, `id_tipo_pedido`, `fecha_solicitud`, `id_usuario`) 
            values (NULL,precio, '1',tipo_pedido, fecha, id_usuario_v);
            set pedido_id = (select MAX(id_pedido) from pedidos where id_estado_pedido = 1 and id_usuario = id_usuario_v);
		end if;
        
		insert into `detallepedido` (`id_registro`, `id_pedido`, `cantidad`, `id_producto`, `subtotal`) 
		values (NULL, pedido_id, '1', id_producto_v, precio);
		set total = (select sum(subtotal) from detallepedido where id_pedido = pedido_id);
        update pedidos set precio_pedido = total where id_pedido = pedido_id;
end$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `calificacion_individual`
--

CREATE TABLE `calificacion_individual` (
  `id_calificacion` int(11) NOT NULL,
  `id_producto` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `valor` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `calificacion_individual`
--

INSERT INTO `calificacion_individual` (`id_calificacion`, `id_producto`, `id_usuario`, `valor`) VALUES
(16, 67, 12, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoriaproducto`
--

CREATE TABLE `categoriaproducto` (
  `id_categoria` int(11) NOT NULL,
  `descripsion_categoria` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `categoriaproducto`
--

INSERT INTO `categoriaproducto` (`id_categoria`, `descripsion_categoria`) VALUES
(1, 'Medicamentos'),
(2, 'Cosméticos'),
(3, 'Cuidado personal'),
(4, 'Alimentos y bebidas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `id_cliente` int(11) NOT NULL,
  `reportes` tinyint(4) NOT NULL,
  `id_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`id_cliente`, `reportes`, `id_usuario`) VALUES
(2, 0, 1),
(3, 0, 11),
(4, 2, 12);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detallepedido`
--

CREATE TABLE `detallepedido` (
  `id_registro` int(11) NOT NULL,
  `id_pedido` int(11) NOT NULL,
  `cantidad` tinyint(3) NOT NULL,
  `id_producto` int(11) NOT NULL,
  `subtotal` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `detallepedido`
--

INSERT INTO `detallepedido` (`id_registro`, `id_pedido`, `cantidad`, `id_producto`, `subtotal`) VALUES
(189, 76, 1, 68, 22400),
(190, 76, 2, 69, 18600),
(192, 79, 1, 85, 12100),
(193, 79, 3, 68, 67200),
(194, 79, 1, 70, 24000),
(195, 80, 1, 70, 24000),
(196, 80, 1, 71, 4100),
(197, 80, 1, 83, 4000),
(198, 81, 1, 67, 13100),
(199, 81, 1, 73, 2200),
(200, 82, 4, 70, 96000),
(201, 82, 1, 68, 22400),
(202, 83, 1, 67, 13100),
(203, 83, 2, 77, 8400);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `domicilios`
--

CREATE TABLE `domicilios` (
  `id_domicilio` int(11) NOT NULL,
  `nombre_destinatario` varchar(40) NOT NULL,
  `numero_contacto` varchar(15) NOT NULL,
  `direccion` varchar(15) NOT NULL,
  `hora_llegada` time NOT NULL,
  `fecha_llegada` date NOT NULL,
  `id_pedido` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estadopedidio`
--

CREATE TABLE `estadopedidio` (
  `id_estado_pedido` int(11) NOT NULL,
  `descripsion_estado_pedido` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `estadopedidio`
--

INSERT INTO `estadopedidio` (`id_estado_pedido`, `descripsion_estado_pedido`) VALUES
(1, 'pre_solicitado'),
(2, 'Solicitado'),
(3, 'Alistado'),
(4, 'Enviado'),
(5, 'Rechazado'),
(6, 'Cancelado'),
(7, 'Entregado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos`
--

CREATE TABLE `pedidos` (
  `id_pedido` int(11) NOT NULL,
  `precio_pedido` int(10) NOT NULL,
  `id_estado_pedido` int(11) NOT NULL,
  `id_tipo_pedido` int(11) NOT NULL,
  `fecha_solicitud` date NOT NULL,
  `id_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `pedidos`
--

INSERT INTO `pedidos` (`id_pedido`, `precio_pedido`, `id_estado_pedido`, `id_tipo_pedido`, `fecha_solicitud`, `id_usuario`) VALUES
(76, 41000, 7, 1, '2023-03-27', 12),
(79, 103300, 7, 1, '2023-03-27', 12),
(80, 32100, 7, 1, '2023-03-27', 12),
(81, 15300, 7, 1, '2023-03-27', 12),
(82, 118400, 7, 1, '2023-03-27', 12),
(83, 21500, 7, 3, '2023-03-27', 13);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `id_producto` int(11) NOT NULL,
  `nombre` varchar(70) NOT NULL,
  `id_categoria` int(11) NOT NULL,
  `id_proveedor` int(11) NOT NULL,
  `imagen` varchar(100) NOT NULL,
  `precio_compra` mediumint(8) NOT NULL,
  `precio_venta` mediumint(8) NOT NULL,
  `calificacion` tinyint(2) NOT NULL,
  `unidades_vendidas` int(11) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id_producto`, `nombre`, `id_categoria`, `id_proveedor`, `imagen`, `precio_compra`, `precio_venta`, `calificacion`, `unidades_vendidas`, `stock`) VALUES
(67, 'Aspirina Tabletas Efervescente', 1, 17, 'img/upload/medi2.png', 11600, 13100, 4, 2, 0),
(68, 'CEFALEXINA 500 MG ', 1, 1, 'img/upload/medi9.png', 19300, 22400, 0, 5, 0),
(69, 'Ditopax 500mg polvo', 1, 4, 'img/upload/ditopax.png', 6800, 9300, 0, 2, 0),
(70, 'Aciclovir 250mg', 1, 2, 'img/upload/medi5.png', 21200, 24000, 0, 6, 0),
(71, 'Domeboro 350mg', 1, 3, 'img/upload/domeboro.jpg', 3200, 4100, 0, 1, 0),
(72, 'Pestañina 2 X 1', 2, 4, 'img/upload/cos4.png', 2000, 3000, 0, 0, 0),
(73, 'Esmalte rosado 90ml', 2, 4, 'img/upload/cos2.png', 1500, 2200, 0, 1, 0),
(74, 'Removedor Lander', 2, 4, 'img/upload/cos5.png', 3200, 5000, 0, 0, 0),
(75, 'Base marron', 2, 4, 'img/upload/cos1.png', 2200, 3000, 0, 0, 0),
(76, 'Jabón protex prohidrata', 3, 5, 'img/upload/producto14.png', 3500, 4900, 0, 0, 0),
(77, 'Jabón protex formem', 3, 5, 'img/upload/producto15.png', 2900, 4200, 0, 2, 0),
(78, 'Crema dental triple acción', 3, 5, 'img/upload/producto9.png', 2200, 3100, 0, 0, 0),
(79, 'Talco mexana 350ml', 3, 3, 'img/upload/producto1.png', 12000, 14500, 0, 0, 0),
(80, 'Crema dental minions', 3, 5, 'img/upload/producto5.png', 6800, 8400, 0, 0, 0),
(81, 'Galletas cocossette ', 4, 18, 'img/upload/cocosete.jpg', 1900, 2600, 0, 0, 0),
(82, 'Chicles Trident X 12 Sandia', 4, 3, 'img/upload/trident.jpg', 1200, 1800, 0, 0, 0),
(83, 'Gomas trululu', 4, 18, 'img/upload/gomas.jpg', 3100, 4000, 0, 1, 0),
(84, 'Choclolatina jet ', 4, 18, 'img/upload/choco.jpg', 600, 1000, 0, 0, 0),
(85, 'Listerine cuidado total', 3, 3, 'img/upload/producto6.png', 11000, 12100, 0, 1, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedor`
--

CREATE TABLE `proveedor` (
  `id_proveedor` int(11) NOT NULL,
  `nombre_proveedor` varchar(50) NOT NULL,
  `numero_contacto` varchar(15) NOT NULL,
  `Correo` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `proveedor`
--

INSERT INTO `proveedor` (`id_proveedor`, `nombre_proveedor`, `numero_contacto`, `Correo`) VALUES
(1, 'La sante', '6013647500', 'servicioalcliente@lasante.com.co'),
(2, 'TQ', '3208007208', 'serviciosalconsumidor@tecnoquimicas.com'),
(3, 'JOHNSON & JOHNSON', '3156984583', 'consumidor@conco.jnj.com'),
(4, 'ICOM', '3164492367', 'r.bermudez@coopidrogas.com.co'),
(5, 'colgate palmolive', '180005208', 'colgateprofesional@colgateprofesional.net'),
(17, 'Bayer S.A', '3152469875', 'servicioalcliente@bayer.com'),
(18, 'Nestle', '3005693546', 'ventasnacionales@nestle.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `recoger_tienda`
--

CREATE TABLE `recoger_tienda` (
  `id_recoger_tienda` int(11) NOT NULL,
  `id_pedido` int(11) NOT NULL,
  `fecha_limite` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `recoger_tienda`
--

INSERT INTO `recoger_tienda` (`id_recoger_tienda`, `id_pedido`, `fecha_limite`) VALUES
(33, 76, '2023-03-28'),
(34, 79, '2023-03-28'),
(35, 80, '2023-03-28'),
(36, 81, '2023-03-28'),
(37, 82, '2023-03-28');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `id_rol` tinyint(4) NOT NULL,
  `descripsion_rol` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`id_rol`, `descripsion_rol`) VALUES
(1, 'Cliente'),
(2, 'Vendedor'),
(3, 'Administrador');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipopedido`
--

CREATE TABLE `tipopedido` (
  `id_tipo_pedido` int(11) NOT NULL,
  `descripsion_tipo_pedido` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `tipopedido`
--

INSERT INTO `tipopedido` (`id_tipo_pedido`, `descripsion_tipo_pedido`) VALUES
(1, 'Recoger en tienda'),
(2, 'Domicilio'),
(3, 'Pago directo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_usuario` int(11) NOT NULL,
  `nombre_usuario` varchar(20) NOT NULL,
  `Nombres` varchar(30) NOT NULL,
  `Apellidos` varchar(30) NOT NULL,
  `Correo` varchar(40) NOT NULL,
  `password` varchar(30) NOT NULL,
  `numero_identificacion` varchar(15) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `numero_contacto` varchar(15) NOT NULL,
  `id_rol` tinyint(4) NOT NULL,
  `estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `nombre_usuario`, `Nombres`, `Apellidos`, `Correo`, `password`, `numero_identificacion`, `fecha_nacimiento`, `numero_contacto`, `id_rol`, `estado`) VALUES
(1, 'Carlos123', 'Carlos', 'Perez', 'carlosp@gmail.com', 'Sena2022', '52195631', '1974-05-26', '3105526996', 1, 0),
(2, 'Admin123', 'Sara', 'Dias', 'sara123@gmail.com', 'Pharma1234', '1010785365', '2002-10-23', '3202005698', 3, 1),
(3, 'Cristhian1005', 'Cristhian', 'Mora', 'cristhian1005@misena.edu.co', 'Sena2023', '1020785206', '1993-08-26', '3005499229', 2, 1),
(11, 'carlos456', 'dasdasddsa', 'dasdasdas', 'jose@gmail.com', 'Sena2022', '1234567890', '2023-03-08', '1234567890', 1, 0),
(12, 'camilo123', 'camilo', 'Paez', 'camilo@gmail.com', 'ClienteFiel123', '12556699', '2022-10-30', '1234567890', 1, 1),
(13, 'juan123', 'Juan', 'Perez', 'juan@gmail.com', 'Ventas1234', '4565565', '2022-11-16', '1234567890', 2, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `id_venta` int(11) NOT NULL,
  `id_pedido` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `fecha_pago` date NOT NULL,
  `ganancias` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `ventas`
--

INSERT INTO `ventas` (`id_venta`, `id_pedido`, `id_usuario`, `fecha_pago`, `ganancias`) VALUES
(13, 82, 13, '2023-03-28', 14300),
(14, 81, 13, '2023-03-26', 2200),
(15, 80, 13, '2023-03-27', 4600),
(16, 79, 13, '2023-03-25', 13200),
(17, 76, 13, '2023-03-24', 8100),
(18, 83, 13, '2023-03-27', 4100);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `calificacion_individual`
--
ALTER TABLE `calificacion_individual`
  ADD PRIMARY KEY (`id_calificacion`),
  ADD KEY `id_usuario` (`id_usuario`),
  ADD KEY `calificacion_individual_ibfk_1` (`id_producto`);

--
-- Indices de la tabla `categoriaproducto`
--
ALTER TABLE `categoriaproducto`
  ADD PRIMARY KEY (`id_categoria`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id_cliente`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- Indices de la tabla `detallepedido`
--
ALTER TABLE `detallepedido`
  ADD PRIMARY KEY (`id_registro`),
  ADD KEY `compras_ibfk_3` (`id_pedido`),
  ADD KEY `codProducto` (`id_producto`);

--
-- Indices de la tabla `domicilios`
--
ALTER TABLE `domicilios`
  ADD PRIMARY KEY (`id_domicilio`),
  ADD KEY `id_pedido` (`id_pedido`);

--
-- Indices de la tabla `estadopedidio`
--
ALTER TABLE `estadopedidio`
  ADD PRIMARY KEY (`id_estado_pedido`);

--
-- Indices de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD PRIMARY KEY (`id_pedido`),
  ADD KEY `pedidos_ibfk_1` (`id_estado_pedido`),
  ADD KEY `pedidos_ibfk_5` (`id_tipo_pedido`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id_producto`),
  ADD KEY `producto_ibfk_2` (`id_proveedor`),
  ADD KEY `CodCategoria` (`id_categoria`);

--
-- Indices de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  ADD PRIMARY KEY (`id_proveedor`);

--
-- Indices de la tabla `recoger_tienda`
--
ALTER TABLE `recoger_tienda`
  ADD PRIMARY KEY (`id_recoger_tienda`),
  ADD KEY `recoger_tienda_ibfk_1` (`id_pedido`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id_rol`);

--
-- Indices de la tabla `tipopedido`
--
ALTER TABLE `tipopedido`
  ADD PRIMARY KEY (`id_tipo_pedido`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_usuario`),
  ADD KEY `id_rol` (`id_rol`);

--
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`id_venta`),
  ADD KEY `pagos_ibfk_3` (`id_pedido`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `calificacion_individual`
--
ALTER TABLE `calificacion_individual`
  MODIFY `id_calificacion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `categoriaproducto`
--
ALTER TABLE `categoriaproducto`
  MODIFY `id_categoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id_cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `detallepedido`
--
ALTER TABLE `detallepedido`
  MODIFY `id_registro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=204;

--
-- AUTO_INCREMENT de la tabla `domicilios`
--
ALTER TABLE `domicilios`
  MODIFY `id_domicilio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT de la tabla `estadopedidio`
--
ALTER TABLE `estadopedidio`
  MODIFY `id_estado_pedido` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `id_pedido` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=84;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id_producto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=86;

--
-- AUTO_INCREMENT de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  MODIFY `id_proveedor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT de la tabla `recoger_tienda`
--
ALTER TABLE `recoger_tienda`
  MODIFY `id_recoger_tienda` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `id_rol` tinyint(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `tipopedido`
--
ALTER TABLE `tipopedido`
  MODIFY `id_tipo_pedido` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `id_venta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `calificacion_individual`
--
ALTER TABLE `calificacion_individual`
  ADD CONSTRAINT `calificacion_individual_ibfk_1` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id_producto`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `calificacion_individual_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `detallepedido`
--
ALTER TABLE `detallepedido`
  ADD CONSTRAINT `compras_ibfk_3` FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id_pedido`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detallepedido_ibfk_1` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id_producto`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `domicilios`
--
ALTER TABLE `domicilios`
  ADD CONSTRAINT `domicilios_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id_pedido`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD CONSTRAINT `pedidos_ibfk_1` FOREIGN KEY (`id_estado_pedido`) REFERENCES `estadopedidio` (`id_estado_pedido`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `pedidos_ibfk_5` FOREIGN KEY (`id_tipo_pedido`) REFERENCES `tipopedido` (`id_tipo_pedido`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `pedidos_ibfk_6` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `producto_ibfk_2` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedor` (`id_proveedor`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `producto_ibfk_3` FOREIGN KEY (`id_categoria`) REFERENCES `categoriaproducto` (`id_categoria`);

--
-- Filtros para la tabla `recoger_tienda`
--
ALTER TABLE `recoger_tienda`
  ADD CONSTRAINT `recoger_tienda_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id_pedido`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `usuarios_ibfk_1` FOREIGN KEY (`id_rol`) REFERENCES `roles` (`id_rol`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `ventas_ibfk_3` FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id_pedido`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ventas_ibfk_4` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
