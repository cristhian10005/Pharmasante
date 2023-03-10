-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 10-03-2023 a las 23:25:37
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
        update pedidos set fecha_solicitud = fecha where id_pedido = pedido_id;
end$$

DELIMITER ;

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
  `id` int(11) NOT NULL,
  `reportes` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
(33, 15, 1, 1, 3200),
(34, 15, 1, 2, 9900);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `domicilios`
--

CREATE TABLE `domicilios` (
  `id_domicilio` int(11) NOT NULL,
  `nombre_destinatario` varchar(40) NOT NULL,
  `numero_contacto` varchar(15) NOT NULL,
  `direccion` varchar(15) NOT NULL,
  `hora_envio` time NOT NULL,
  `tiempo_estimado` time NOT NULL
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
-- Estructura de tabla para la tabla `metodopago`
--

CREATE TABLE `metodopago` (
  `id_metodo_pago` int(11) NOT NULL,
  `descripsion_metodo_pago` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
(15, 13100, 1, 1, '2023-02-25', 1);

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
  `stock` int(11) DEFAULT NULL,
  `fecha_vencimiento` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id_producto`, `nombre`, `id_categoria`, `id_proveedor`, `imagen`, `precio_compra`, `precio_venta`, `calificacion`, `unidades_vendidas`, `stock`, `fecha_vencimiento`) VALUES
(1, 'Talco mexsana lady 300 Gramos + 85 Gramos', 3, 1, 'img/upload/producto14.png', 2500, 3200, 0, 0, 50, '2023-02-15'),
(2, 'Jabón Protex Pro-Hidrata olive 90 Gramos', 3, 1, 'img/upload/producto15.png', 8200, 9900, 0, 0, 30, '2023-02-28'),
(34, 'Jabón Protex Men sport 110 Gramos', 3, 3, 'img/upload/producto15.png', 5000, 9000, 0, 0, 0, '2024-03-14'),
(35, 'Jabón Protex Men sport 110 Gramos', 3, 3, 'img/upload/cos1.png', 5000, 9000, 0, 0, 0, '2024-03-14'),
(36, 'Jabón Protex Men sport 110 Gramos', 3, 3, 'img/upload/cos2.png', 5000, 9000, 0, 0, 0, '2024-03-14'),
(37, 'Jabón Protex Men sport 110 Gramos', 3, 3, 'img/upload/cos4.png', 5000, 9000, 0, 0, 0, '2024-03-14'),
(38, 'Jabón Protex Men sport 110 Gramos', 2, 3, 'img/upload/cos5.png', 5000, 9000, 0, 0, 0, '2024-03-14'),
(39, 'Jabón Protex Men sport 110 Gramos', 2, 3, 'img/upload/producto1.png', 5000, 9000, 0, 0, 0, '2024-03-14'),
(40, 'Jabón Protex Men sport 110 Gramos', 2, 3, 'img/upload/producto2.png', 5000, 9000, 0, 0, 0, '2024-03-14'),
(41, 'Jabón Protex Men sport 110 Gramos', 4, 3, 'img/upload/producto3.png', 5000, 9000, 0, 0, 0, '2024-03-14'),
(42, 'Jabón Protex Men sport 110 Gramos', 4, 3, 'img/upload/producto4.png', 5000, 9000, 0, 0, 0, '2024-03-14'),
(43, 'Jabón Protex Men sport 110 Gramos', 3, 3, 'img/upload/producto5.png', 5000, 9000, 0, 0, 0, '2024-03-14'),
(44, 'Jabón Protex Men sport 110 Gramos', 4, 3, 'img/upload/producto7.png', 5000, 9000, 0, 0, 0, '2024-03-14'),
(45, 'Jabón Protex Men sport 110 Gramos', 3, 3, 'img/upload/producto9.png', 5000, 9000, 0, 0, 0, '2024-03-14');

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
(5, 'colgate palmolive', '180005208', 'colgateprofesional@colgateprofesional.net');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `recoger_tienda`
--

CREATE TABLE `recoger_tienda` (
  `id` int(11) NOT NULL,
  `fecha_limite` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
(2, 'Domicilio');

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
  `id_rol` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `nombre_usuario`, `Nombres`, `Apellidos`, `Correo`, `password`, `numero_identificacion`, `fecha_nacimiento`, `numero_contacto`, `id_rol`) VALUES
(1, 'Carlos123', 'Carlos', 'Perez', 'carlosp@gmail.com', '123456', '52195631', '1974-05-26', '3105526996', 1),
(2, 'Julieth ', 'Julieth', 'Pinto', 'viviana123@gmail.com', '123456', '1010785365', '2002-10-23', '3202005698', 2),
(3, 'Cristhian1005', 'Cristhian', 'Mora', 'cristhian1005@misena.edu.co', '123456', '1020785206', '1993-08-26', '3005499229', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `id_venta` int(11) NOT NULL,
  `id_pedido` int(11) NOT NULL,
  `cod_metodo_pago` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `fecha_pago` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoriaproducto`
--
ALTER TABLE `categoriaproducto`
  ADD PRIMARY KEY (`id_categoria`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD KEY `id` (`id`);

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
  ADD PRIMARY KEY (`id_domicilio`);

--
-- Indices de la tabla `estadopedidio`
--
ALTER TABLE `estadopedidio`
  ADD PRIMARY KEY (`id_estado_pedido`);

--
-- Indices de la tabla `metodopago`
--
ALTER TABLE `metodopago`
  ADD PRIMARY KEY (`id_metodo_pago`);

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
  ADD KEY `id` (`id`);

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
  ADD KEY `pagos_ibfk_2` (`cod_metodo_pago`),
  ADD KEY `pagos_ibfk_3` (`id_pedido`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoriaproducto`
--
ALTER TABLE `categoriaproducto`
  MODIFY `id_categoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `detallepedido`
--
ALTER TABLE `detallepedido`
  MODIFY `id_registro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT de la tabla `domicilios`
--
ALTER TABLE `domicilios`
  MODIFY `id_domicilio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `estadopedidio`
--
ALTER TABLE `estadopedidio`
  MODIFY `id_estado_pedido` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `metodopago`
--
ALTER TABLE `metodopago`
  MODIFY `id_metodo_pago` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `id_pedido` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id_producto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- AUTO_INCREMENT de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  MODIFY `id_proveedor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

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
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `id_venta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`id`) REFERENCES `usuarios` (`id_usuario`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `detallepedido`
--
ALTER TABLE `detallepedido`
  ADD CONSTRAINT `compras_ibfk_3` FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id_pedido`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detallepedido_ibfk_1` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id_producto`);

--
-- Filtros para la tabla `domicilios`
--
ALTER TABLE `domicilios`
  ADD CONSTRAINT `domicilios_ibfk_1` FOREIGN KEY (`id_domicilio`) REFERENCES `pedidos` (`id_pedido`) ON UPDATE CASCADE;

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
  ADD CONSTRAINT `recoger_tienda_ibfk_1` FOREIGN KEY (`id`) REFERENCES `pedidos` (`id_pedido`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `usuarios_ibfk_1` FOREIGN KEY (`id_rol`) REFERENCES `roles` (`id_rol`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `ventas_ibfk_2` FOREIGN KEY (`cod_metodo_pago`) REFERENCES `metodopago` (`id_metodo_pago`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ventas_ibfk_3` FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id_pedido`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ventas_ibfk_4` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
