CREATE DATABASE tienda;
USE tienda;

CREATE TABLE marca(
		id_marca int auto_increment primary key,
		nombre_marca varchar(50) not null);

INSERT INTO `marca` VALUES (1,'Gucci'),(2,'Chanel'),(3,'Louis Vuitton'),(4,'Armani'),(5,'Prada'),(6,'Dior'),
(7,'Valentino'),(8,'Marc Jacobs'),(9,'Versace'),(10,'Fendi'),(11,'Banana Republik'),(12,'Mango'),(13,'Cato'),
(14,'Carolina Herrera'),(15,'Gyvenchi'),(16,'Ferragamo'),(17,'Ermenegido Zegna'),(18,'Calvin Klein'),(19,'Nike'),
(20,'H&M'),(21,'Dolce & Gabbana');

CREATE TABLE talla(
		id_talla int auto_increment primary key,
		talla varchar(50) not null);
        
INSERT INTO `talla` VALUES (1,'xxs'),(2,'xs'),(3,'s'),(4,'m'),(5,'l'),(6,'xl'),(7,'xxl'),(8,'xxxl');


CREATE TABLE tipo_ropa(
		id_ropa int auto_increment primary key,
		nombre_ropa varchar(50) not null);
        
INSERT INTO `tipo_ropa` VALUES (1,'camisa mujer'),(5,'pantalon hombre'),(6,'falda'),(7,'vestido'),(8,'short mujer'),(9,'short infantil unisex'),(10,'short hombre'),(11,'pantalon infantil unisex'),(12,'camisa hombre'),(13,'pantalon mujer'),(14,'camisa infantil unisex'),(15,'Blusas'),(16,'Top'),(17,'Pantalon'),(18,'Falda'),(19,'Vestido'),(20,'Camisas Sport'),(21,'Camisas casuales'),(22,'Camisas formales'),(23,'Jeans'),(24,'Pantalon de vestir'),(25,'Short'),(26,'Camisas'),(27,'Pijama'),(28,'Faldas'),(29,'pantalon'),(30,'pantalon');
select * from tipo_ropa;

CREATE TABLE categoria(
		id_categoria int auto_increment primary key,
		nombre_categoria varchar(50) not null);
        
insert into categoria(nombre_categoria)values('Hombre'),('Mujer'),('Niño');

CREATE TABLE producto(
		id_producto int auto_increment primary key,
		nombre_producto varchar(50) not null,
		stock int not null,
		precio_venta double(8,2) not null,
		precio_compra double(8,2) not null,
		id_marca int not null,
		id_talla int not null,
		id_tipo int not null,
		id_categoria int not null,
		img varchar(500) null);

ALTER TABLE producto ADD CONSTRAINT FOREIGN KEY fk_producto_marca(id_marca) REFERENCES marca(id_marca);

ALTER TABLE producto ADD CONSTRAINT FOREIGN KEY fk_producto_talla(id_talla) REFERENCES talla(id_talla);

ALTER TABLE producto ADD CONSTRAINT FOREIGN KEY fk_producto_tipo(id_tipo) REFERENCES tipo_ropa(id_ropa);

ALTER TABLE producto ADD CONSTRAINT FOREIGN KEY fk_producto_categoria(id_categoria) REFERENCES categoria(id_categoria);

INSERT INTO `producto` VALUES (6,'Kidol Momoiro Shirt',50,15.00,17.00,2,1,15,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/camisa1.jpg'),
(7,'Red Spring shirt 2020',120,120.00,185.00,2,1,15,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/camisa4.jpg'),
(8,'Kidol Momoiro Shirt',50,16.00,20.00,2,1,15,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/camisa2.jpg?w=700&h='),
(9,'Kidol Momoiro Shirt',50,120.00,70.00,2,1,15,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/camisa3.jpg?w=700&h='),
(10,'Kidol Momoiro Shirt',50,17.00,11.00,2,1,15,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/camisa5.jpg?w=700&h='),
(11,'Kidol Momoiro Shirt',50,15.00,17.00,2,1,15,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/camisa6.jpg?w=700&h='),
(12,'Kidol Momoiro Shirt',100,15.00,50.00,2,1,15,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/camisa7.jpg?w=700&h='),
(13,'Kidol Momoiro Shirt',100,15.00,50.00,2,1,15,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/camisa8.jpg?w=700&h='),
(14,'Kidol Momoiro',20,15.00,25.50,6,2,1,2,'https://y4j9z9c2.stackpathcdn.com/media/catalog/product/cache/1/small_image/9df78eab33525d08d6e5fb8d27136e95/1/0/102679544_4_.jpg'),
(15,'Camiseta formal diseños ',120,100.00,50.00,6,2,1,2,'https://y4j9z9c2.stackpathcdn.com/media/catalog/product/cache/1/small_image/9df78eab33525d08d6e5fb8d27136e95/1/0/102679671_4_.jpg'),
(16,'Red Spring shirt 2020',120,50.00,50.00,6,2,1,2,'https://y4j9z9c2.stackpathcdn.com/media/catalog/product/cache/1/small_image/9df78eab33525d08d6e5fb8d27136e95/1/0/102602590_4_.jpg'),
(17,'Red Spring shirt 2020',120,50.00,50.00,6,2,1,2,'https://y4j9z9c2.stackpathcdn.com/media/catalog/product/cache/1/small_image/9df78eab33525d08d6e5fb8d27136e95/1/0/102665735_4_.jpg'),
(18,'Red Spring shirt 2020',120,85.00,20.00,2,1,15,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/camisa9.jpg?w=700&h='),
(19,'Red Spring shirt 2020',120,85.00,20.00,2,1,15,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/camisa10.jpg?w=700&h='),
(20,'Red Spring shirt 2020',120,56.00,20.00,2,1,15,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/camisa11.jpg?w=700&h='),
(21,'Red Spring Shirt 2020',120,50.00,50.00,2,1,15,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/camisa12.jpg?w=700&h='),
(22,'Strawberry girl Shirt',500,10.00,15.00,2,1,15,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/camisa14.jpg?w=700&h='),
(23,'Top pant ',60,50.00,60.00,2,1,15,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/pantalon3-1.jpg?w=700&h='),
(24,'Top pant',60,60.50,50.00,2,1,15,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/pantalon1-1.jpg'),
(25,'Square Pants',80,25.12,30.00,6,2,1,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/pantalon2-1.jpg'),
(26,'Top Pant',80,52.60,60.00,2,1,15,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/pantalon4-1.jpg?w=700&h='),
(27,'Top pant',60,150.23,160.89,2,1,15,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/pantalon5-1.jpg?w=700&h='),
(28,'Top pant',80,60.00,150.00,2,1,15,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/pantalon6-1.jpg?w=700&h='),
(29,'Top pant',50,120.00,300.00,2,1,15,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/pantalon7-1.jpg?w=370&h='),
(30,'Top pant',80,120.00,150.00,2,1,15,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/pantalon8-1.jpg?w=700&h='),
(31,'Top pant',80,202.30,33.00,2,1,15,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/pantalon9-1.jpg?w=700&h='),
(32,'Strawberry girl shirt',40,80.00,16.20,2,1,15,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/camisa16.jpg'),
(33,'Strawberry girl shirt',12,45.12,45.90,2,1,15,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/camisa16.jpg'),
(34,'Square Pants',45,120.00,150.00,6,2,1,2,'https://y4j9z9c2.stackpathcdn.com/media/catalog/product/cache/1/small_image/9df78eab33525d08d6e5fb8d27136e95/1/0/101862662_2.jpg'),(35,'Falda  niña',12,12.00,2.00,6,2,1,2,'https://y4j9z9c2.stackpathcdn.com/media/catalog/product/cache/1/small_image/300x/9df78eab33525d08d6e5fb8d27136e95/1/0/102267836_1.jpg'),
(36,'Short Niño',12,11.00,3.00,6,2,1,2,'https://y4j9z9c2.stackpathcdn.com/media/catalog/product/cache/1/small_image/300x/9df78eab33525d08d6e5fb8d27136e95/1/0/102217657_1.jpg'),(37,'Camisa niña',12,2.00,1.00,6,2,1,2,'https://y4j9z9c2.stackpathcdn.com/media/catalog/product/cache/1/small_image/300x/9df78eab33525d08d6e5fb8d27136e95/5/8/58550746521.jpg'),
(38,'Falda-mujer',22,2.00,33.00,1,1,18,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/camisa1.jpg'),
(39,'Vestido-mujer',44,2.00,2.00,3,1,19,2,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/camisa1.jpg'),
(40,'CamisaSport-hombre',44,4.00,5.00,2,2,20,1,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/camisa1.jpg'),
(41,'Camisas casuales-hombre',545,4.00,3.00,1,1,21,1,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/camisa1.jpg'),
(42,'Camisas formales-hombre',656,4.00,3.00,1,1,22,1,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/camisa1.jpg'),
(43,'Jeans-hombre',323,22.00,2.00,1,1,23,1,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/camisa1.jpg'),
(44,'Pantalon de vestir-hombre',5534,33.00,3.00,1,1,24,1,''),(45,'Short-hombre',657,55.00,6.00,1,1,25,3,'s'),
(46,'Camisas-niños',54,44.00,44.00,1,1,26,3,'54'),(47,'Pijama-niños',45,4.00,3.00,1,1,27,3,''),
(48,'Falda-niñs',33,2.00,2.00,1,1,28,3,'2'),(52,'blusa blanca',5,45.00,10.00,1,1,1,1,NULL),
(53,'Short',323,222.00,100.00,1,1,22,1,'https://mitiendaenlineaisla4.files.wordpress.com/2020/01/pantalonh2.jpg'),
(54,'Pantalón para niños',55,5.00,4.00,1,1,22,1,'https://y4j9z9c2.stackpathcdn.com/media/catalog/product/cache/1/image/500x500/9df78eab33525d08d6e5fb8d27136e95/1/0/102117634_1.jpg'),
(55,'Pantalon infantil',34,3.00,2.00,1,1,22,1,'s'),
(56,'jkj',9,7.00,6.00,1,1,1,1,'u');
CREATE TABLE pais(
		id_pais int auto_increment primary key,
		nombre_pais varchar(50) not null);
        
INSERT INTO `pais` VALUES(1, 'Afganistán');
INSERT INTO `pais` VALUES(2, 'Islas Gland');
INSERT INTO `pais` VALUES(3, 'Albania');
INSERT INTO `pais` VALUES(4, 'Alemania');
INSERT INTO `pais` VALUES(5, 'Andorra');
INSERT INTO `pais` VALUES(6, 'Angola');
INSERT INTO `pais` VALUES(7, 'Anguilla');
INSERT INTO `pais` VALUES(8, 'Antártida');
INSERT INTO `pais` VALUES(9, 'Antigua y Barbuda');
INSERT INTO `pais` VALUES(10, 'Antillas Holandesas');
INSERT INTO `pais` VALUES(11, 'Arabia Saudí');
INSERT INTO `pais` VALUES(12, 'Argelia');
INSERT INTO `pais` VALUES(13, 'Argentina');
INSERT INTO `pais` VALUES(14, 'Armenia');
INSERT INTO `pais` VALUES(15, 'Aruba');
INSERT INTO `pais` VALUES(16, 'Australia');
INSERT INTO `pais` VALUES(17, 'Austria');
INSERT INTO `pais` VALUES(18, 'Azerbaiyán');
INSERT INTO `pais` VALUES(19, 'Bahamas');
INSERT INTO `pais` VALUES(20, 'Bahréin');
INSERT INTO `pais` VALUES(21, 'Bangladesh');
INSERT INTO `pais` VALUES(22, 'Barbados');
INSERT INTO `pais` VALUES(23, 'Bielorrusia');
INSERT INTO `pais` VALUES(24, 'Bélgica');
INSERT INTO `pais` VALUES(25, 'Belice');
INSERT INTO `pais` VALUES(26, 'Benin');
INSERT INTO `pais` VALUES(27, 'Bermudas');
INSERT INTO `pais` VALUES(28, 'Bhután');
INSERT INTO `pais` VALUES(29, 'Bolivia');
INSERT INTO `pais` VALUES(30, 'Bosnia y Herzegovina');
INSERT INTO `pais` VALUES(31, 'Botsuana');
INSERT INTO `pais` VALUES(32, 'Isla Bouvet');
INSERT INTO `pais` VALUES(33, 'Brasil');
INSERT INTO `pais` VALUES(34, 'Brunéi');
INSERT INTO `pais` VALUES(35, 'Bulgaria');
INSERT INTO `pais` VALUES(36, 'Burkina Faso');
INSERT INTO `pais` VALUES(37, 'Burundi');
INSERT INTO `pais` VALUES(38, 'Cabo Verde');
INSERT INTO `pais` VALUES(39, 'Islas Caimán');
INSERT INTO `pais` VALUES(40, 'Camboya');
INSERT INTO `pais` VALUES(41, 'Camerún');
INSERT INTO `pais` VALUES(42, 'Canadá');
INSERT INTO `pais` VALUES(43, 'República Centroafricana');
INSERT INTO `pais` VALUES(44, 'Chad');
INSERT INTO `pais` VALUES(45, 'República Checa');
INSERT INTO `pais` VALUES(46, 'Chile');
INSERT INTO `pais` VALUES(47, 'China');
INSERT INTO `pais` VALUES(48, 'Chipre');
INSERT INTO `pais` VALUES(49, 'Isla de Navidad');
INSERT INTO `pais` VALUES(50, 'Ciudad del Vaticano');
INSERT INTO `pais` VALUES(51, 'Islas Cocos');
INSERT INTO `pais` VALUES(52, 'Colombia');
INSERT INTO `pais` VALUES(53, 'Comoras');
INSERT INTO `pais` VALUES(54, 'República Democrática del Congo');
INSERT INTO `pais` VALUES(55, 'Congo');
INSERT INTO `pais` VALUES(56, 'Islas Cook');
INSERT INTO `pais` VALUES(57, 'Corea del Norte');
INSERT INTO `pais` VALUES(58, 'Corea del Sur');
INSERT INTO `pais` VALUES(59, 'Costa de Marfil');
INSERT INTO `pais` VALUES(60, 'Costa Rica');
INSERT INTO `pais` VALUES(61, 'Croacia');
INSERT INTO `pais` VALUES(62, 'Cuba');
INSERT INTO `pais` VALUES(63, 'Dinamarca');
INSERT INTO `pais` VALUES(64, 'Dominica');
INSERT INTO `pais` VALUES(65, 'República Dominicana');
INSERT INTO `pais` VALUES(66, 'Ecuador');
INSERT INTO `pais` VALUES(67, 'Egipto');
INSERT INTO `pais` VALUES(68, 'El Salvador');
INSERT INTO `pais` VALUES(69, 'Emiratos Árabes Unidos');
INSERT INTO `pais` VALUES(70, 'Eritrea');
INSERT INTO `pais` VALUES(71, 'Eslovaquia');
INSERT INTO `pais` VALUES(72, 'Eslovenia');
INSERT INTO `pais` VALUES(73, 'España');
INSERT INTO `pais` VALUES(74, 'Islas ultramarinas de Estados Unidos');
INSERT INTO `pais` VALUES(75, 'Estados Unidos');
INSERT INTO `pais` VALUES(76, 'Estonia');
INSERT INTO `pais` VALUES(77, 'Etiopía');
INSERT INTO `pais` VALUES(78, 'Islas Feroe');
INSERT INTO `pais` VALUES(79, 'Filipinas');
INSERT INTO `pais` VALUES(80, 'Finlandia');
INSERT INTO `pais` VALUES(81, 'Fiyi');
INSERT INTO `pais` VALUES(82, 'Francia');
INSERT INTO `pais` VALUES(83, 'Gabón');
INSERT INTO `pais` VALUES(84, 'Gambia');
INSERT INTO `pais` VALUES(85, 'Georgia');
INSERT INTO `pais` VALUES(86, 'Islas Georgias del Sur y Sandwich del Sur');
INSERT INTO `pais` VALUES(87, 'Ghana');
INSERT INTO `pais` VALUES(88, 'Gibraltar');
INSERT INTO `pais` VALUES(89, 'Granada');
INSERT INTO `pais` VALUES(90, 'Grecia');
INSERT INTO `pais` VALUES(91, 'Groenlandia');
INSERT INTO `pais` VALUES(92, 'Guadalupe');
INSERT INTO `pais` VALUES(93, 'Guam');
INSERT INTO `pais` VALUES(94, 'Guatemala');
INSERT INTO `pais` VALUES(95, 'Guayana Francesa');
INSERT INTO `pais` VALUES(96, 'Guinea');
INSERT INTO `pais` VALUES(97, 'Guinea Ecuatorial');
INSERT INTO `pais` VALUES(98, 'Guinea-Bissau');
INSERT INTO `pais` VALUES(99, 'Guyana');
INSERT INTO `pais` VALUES(100,'Haití');
INSERT INTO `pais` VALUES(101, 'Islas Heard y McDonald');
INSERT INTO `pais` VALUES(102, 'Honduras');
INSERT INTO `pais` VALUES(103, 'Hong Kong');
INSERT INTO `pais` VALUES(104, 'Hungría');
INSERT INTO `pais` VALUES(105, 'India');
INSERT INTO `pais` VALUES(106, 'Indonesia');
INSERT INTO `pais` VALUES(107, 'Irán');
INSERT INTO `pais` VALUES(108, 'Iraq');
INSERT INTO `pais` VALUES(109, 'Irlanda');
INSERT INTO `pais` VALUES(110, 'Islandia');
INSERT INTO `pais` VALUES(111, 'Israel');
INSERT INTO `pais` VALUES(112, 'Italia');
INSERT INTO `pais` VALUES(113, 'Jamaica');
INSERT INTO `pais` VALUES(114, 'Japón');
INSERT INTO `pais` VALUES(115, 'Jordania');
INSERT INTO `pais` VALUES(116, 'Kazajstán');
INSERT INTO `pais` VALUES(117, 'Kenia');
INSERT INTO `pais` VALUES(118, 'Kirguistán');
INSERT INTO `pais` VALUES(119, 'Kiribati');
INSERT INTO `pais` VALUES(120, 'Kuwait');
INSERT INTO `pais` VALUES(121, 'Laos');
INSERT INTO `pais` VALUES(122, 'Lesotho');
INSERT INTO `pais` VALUES(123, 'Letonia');
INSERT INTO `pais` VALUES(124, 'Líbano');
INSERT INTO `pais` VALUES(125, 'Liberia');
INSERT INTO `pais` VALUES(126, 'Libia');
INSERT INTO `pais` VALUES(127, 'Liechtenstein');
INSERT INTO `pais` VALUES(128, 'Lituania');
INSERT INTO `pais` VALUES(129, 'Luxemburgo');
INSERT INTO `pais` VALUES(130, 'Macao');
INSERT INTO `pais` VALUES(131, 'ARY Macedonia');
INSERT INTO `pais` VALUES(132, 'Madagascar');
INSERT INTO `pais` VALUES(133, 'Malasia');
INSERT INTO `pais` VALUES(134, 'Malawi');
INSERT INTO `pais` VALUES(135, 'Maldivas');
INSERT INTO `pais` VALUES(136, 'Malí');
INSERT INTO `pais` VALUES(137, 'Malta');
INSERT INTO `pais` VALUES(138, 'Islas Malvinas');
INSERT INTO `pais` VALUES(139, 'Islas Marianas del Norte');
INSERT INTO `pais` VALUES(140, 'Marruecos');
INSERT INTO `pais` VALUES(141, 'Islas Marshall');
INSERT INTO `pais` VALUES(142, 'Martinica');
INSERT INTO `pais` VALUES(143, 'Mauricio');
INSERT INTO `pais` VALUES(144, 'Mauritania');
INSERT INTO `pais` VALUES(145, 'Mayotte');
INSERT INTO `pais` VALUES(146, 'México');
INSERT INTO `pais` VALUES(147, 'Micronesia');
INSERT INTO `pais` VALUES(148, 'Moldavia');
INSERT INTO `pais` VALUES(149, 'Mónaco');
INSERT INTO `pais` VALUES(150, 'Mongolia');
INSERT INTO `pais` VALUES(151, 'Montserrat');
INSERT INTO `pais` VALUES(152, 'Mozambique');
INSERT INTO `pais` VALUES(153, 'Myanmar');
INSERT INTO `pais` VALUES(154, 'Namibia');
INSERT INTO `pais` VALUES(155, 'Nauru');
INSERT INTO `pais` VALUES(156, 'Nepal');
INSERT INTO `pais` VALUES(157, 'Nicaragua');
INSERT INTO `pais` VALUES(158, 'Níger');
INSERT INTO `pais` VALUES(159, 'Nigeria');
INSERT INTO `pais` VALUES(160, 'Niue');
INSERT INTO `pais` VALUES(161, 'Isla Norfolk');
INSERT INTO `pais` VALUES(162, 'Noruega');
INSERT INTO `pais` VALUES(163, 'Nueva Caledonia');
INSERT INTO `pais` VALUES(164, 'Nueva Zelanda');
INSERT INTO `pais` VALUES(165, 'Omán');
INSERT INTO `pais` VALUES(166, 'Países Bajos');
INSERT INTO `pais` VALUES(167, 'Pakistán');
INSERT INTO `pais` VALUES(168, 'Palau');
INSERT INTO `pais` VALUES(169, 'Palestina');
INSERT INTO `pais` VALUES(170, 'Panamá');
INSERT INTO `pais` VALUES(171, 'Papúa Nueva Guinea');
INSERT INTO `pais` VALUES(172, 'Paraguay');
INSERT INTO `pais` VALUES(173, 'Perú');
INSERT INTO `pais` VALUES(174, 'Islas Pitcairn');
INSERT INTO `pais` VALUES(175, 'Polinesia Francesa');
INSERT INTO `pais` VALUES(176, 'Polonia');
INSERT INTO `pais` VALUES(177, 'Portugal');
INSERT INTO `pais` VALUES(178, 'Puerto Rico');
INSERT INTO `pais` VALUES(179, 'Qatar');
INSERT INTO `pais` VALUES(180, 'Reino Unido');
INSERT INTO `pais` VALUES(181, 'Reunión');
INSERT INTO `pais` VALUES(182, 'Ruanda');
INSERT INTO `pais` VALUES(183, 'Rumania');
INSERT INTO `pais` VALUES(184, 'Rusia');
INSERT INTO `pais` VALUES(185, 'Sahara Occidental');
INSERT INTO `pais` VALUES(186, 'Islas Salomón');
INSERT INTO `pais` VALUES(187, 'Samoa');
INSERT INTO `pais` VALUES(188, 'Samoa Americana');
INSERT INTO `pais` VALUES(189, 'San Cristóbal y Nevis');
INSERT INTO `pais` VALUES(190, 'San Marino');
INSERT INTO `pais` VALUES(191, 'San Pedro y Miquelón');
INSERT INTO `pais` VALUES(192, 'San Vicente y las Granadinas');
INSERT INTO `pais` VALUES(193, 'Santa Helena');
INSERT INTO `pais` VALUES(194, 'Santa Lucía');
INSERT INTO `pais` VALUES(195, 'Santo Tomé y Príncipe');
INSERT INTO `pais` VALUES(196, 'Senegal');
INSERT INTO `pais` VALUES(197, 'Serbia y Montenegro');
INSERT INTO `pais` VALUES(198, 'Seychelles');
INSERT INTO `pais` VALUES(199, 'Sierra Leona');
INSERT INTO `pais` VALUES(200, 'Singapur');
INSERT INTO `pais` VALUES(201, 'Siria');
INSERT INTO `pais` VALUES(202, 'Somalia');
INSERT INTO `pais` VALUES(203, 'Sri Lanka');
INSERT INTO `pais` VALUES(204, 'Suazilandia');
INSERT INTO `pais` VALUES(205, 'Sudáfrica');
INSERT INTO `pais` VALUES(206, 'Sudán');
INSERT INTO `pais` VALUES(207, 'Suecia');
INSERT INTO `pais` VALUES(208, 'Suiza');
INSERT INTO `pais` VALUES(209, 'Surinam');
INSERT INTO `pais` VALUES(210, 'Svalbard y Jan Mayen');
INSERT INTO `pais` VALUES(211, 'Tailandia');
INSERT INTO `pais` VALUES(212, 'Taiwán');
INSERT INTO `pais` VALUES(213, 'Tanzania');
INSERT INTO `pais` VALUES(214, 'Tayikistán');
INSERT INTO `pais` VALUES(215, 'Territorio Británico del Océano Índico');
INSERT INTO `pais` VALUES(216, 'Territorios Australes Franceses');
INSERT INTO `pais` VALUES(217, 'Timor Oriental');
INSERT INTO `pais` VALUES(218, 'Togo');
INSERT INTO `pais` VALUES(219, 'Tokelau');
INSERT INTO `pais` VALUES(220, 'Tonga');
INSERT INTO `pais` VALUES(221, 'Trinidad y Tobago');
INSERT INTO `pais` VALUES(222, 'Túnez');
INSERT INTO `pais` VALUES(223, 'Islas Turcas y Caicos');
INSERT INTO `pais` VALUES(224, 'Turkmenistán');
INSERT INTO `pais` VALUES(225, 'Turquía');
INSERT INTO `pais` VALUES(226, 'Tuvalu');
INSERT INTO `pais` VALUES(227, 'Ucrania');
INSERT INTO `pais` VALUES(228, 'Uganda');
INSERT INTO `pais` VALUES(229, 'Uruguay');
INSERT INTO `pais` VALUES(230, 'Uzbekistán');
INSERT INTO `pais` VALUES(231, 'Vanuatu');
INSERT INTO `pais` VALUES(232, 'Venezuela');
INSERT INTO `pais` VALUES(233, 'Vietnam');
INSERT INTO `pais` VALUES(234, 'Islas Vírgenes Británicas');
INSERT INTO `pais` VALUES(235, 'Islas Vírgenes de los Estados Unidos');
INSERT INTO `pais` VALUES(236, 'Wallis y Futuna');
INSERT INTO `pais` VALUES(237, 'Yemen');
INSERT INTO `pais` VALUES(238, 'Yibuti');
INSERT INTO `pais` VALUES(239, 'Zambia');
INSERT INTO `pais` VALUES(240, 'Zimbabue');

CREATE TABLE cliente(
		id_cliente int auto_increment primary key,
		nombre_cliente varchar(50) not null,
		apellido_cliente varchar(50) not null,
		correo varchar(50) not null unique,
		direccion varchar(100) not null,
		id_pais int not null);

ALTER TABLE cliente ADD CONSTRAINT FOREIGN KEY fk_cliente_pais(id_pais) REFERENCES pais(id_pais);

insert into cliente(nombre_cliente,apellido_cliente,correo,direccion,id_pais) 
values('Jocelyn','Castellanos','zhentsuwo@gmail.com','San Salvador',68);

CREATE TABLE forma_pago(
		id_forma_pago int auto_increment primary key,
		nombre_forma_pago varchar(50) not null);

insert into forma_pago(nombre_forma_pago) values('Crédito'),('Debito'),('Paypal');

CREATE TABLE detalle_tarjeta(
		id_detalle_tarjeta int auto_increment primary key,
		numero varchar(19) not null unique,
		id_cliente int not null,
		ping int not null,
		expiracion date not null,
		estado boolean not null,
        id_forma_pago int not null);

ALTER TABLE detalle_tarjeta ADD CONSTRAINT FOREIGN KEY fk_tarjeta_pago(id_forma_pago) REFERENCES forma_pago(id_forma_pago);

ALTER TABLE detalle_tarjeta ADD CONSTRAINT FOREIGN KEY fk_tarjeta_cliente(id_cliente) REFERENCES cliente(id_cliente);

CREATE TABLE rol(
		id_rol int auto_increment primary key,
		nombre_rol varchar(50) not null);
        
        insert into rol(nombre_rol)values('admin'),('cliente');

CREATE TABLE user(
		id_user int auto_increment primary key,
		nombre_usuario varchar(50) not null unique,
		id_cliente int not null,
		clave varchar(20) not null,
		id_rol int not null,
		estado boolean not null);

insert into user(nombre_usuario,id_cliente,clave,id_rol,estado) 
values('zen',1,'123',1,true);
select * from user;
select * from cliente;

ALTER TABLE user ADD CONSTRAINT FOREIGN KEY fk_user_cliente(id_cliente) REFERENCES cliente(id_cliente);

ALTER TABLE user ADD CONSTRAINT FOREIGN KEY fk_user_rol(id_rol) REFERENCES rol(id_rol);

CREATE TABLE venta(
		id_venta int auto_increment primary key,
		id_cliente int not null,
		id_detalle_tarjeta int not null,
		fecha date not null,
		estado varchar(15) not null);
        
ALTER TABLE venta ADD CONSTRAINT FOREIGN KEY fk_venta_cliente(id_cliente) REFERENCES cliente(id_cliente);

ALTER TABLE venta ADD CONSTRAINT FOREIGN KEY fk_venta_tarjeta(id_detalle_tarjeta) REFERENCES detalle_tarjeta(id_detalle_tarjeta);


CREATE TABLE descuento(
		id_descuento int auto_increment primary key,
		descuento double(8,2) not null);
        
        insert into descuento(descuento)values(0.2);

CREATE TABLE detalle_venta(
		id_detalle_venta int auto_increment primary key,
		id_producto int not null,
		cantidad int not null,
		id_descuento int not null,
		id_venta int not null,
        total double not null);
        
ALTER TABLE detalle_venta ADD CONSTRAINT FOREIGN KEY fk_detalle_producto(id_producto) REFERENCES producto(id_producto);

ALTER TABLE detalle_venta ADD CONSTRAINT FOREIGN KEY fk_detalle_descuento(id_descuento) REFERENCES descuento(id_descuento);

ALTER TABLE detalle_venta ADD CONSTRAINT FOREIGN KEY fk_detalle_venta(id_venta) REFERENCES venta(id_venta);

CREATE TABLE factura(
		id_factura int auto_increment primary key,
		id_venta int not null);

ALTER TABLE factura ADD CONSTRAINT FOREIGN KEY fk_factura_venta(id_venta) REFERENCES venta(id_venta);

-- drop database tienda;