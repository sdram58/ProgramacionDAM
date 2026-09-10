create table alumnos(
  expediente int not null primary key,
  nombre varchar(50),
  localidad varchar(50),
  fecha_nac string,
  direccion varchar(50),
  curso int,
  nivel varchar(10),
  faltas int
)

insert into alumnos values(123456,'Juan Miguel Soler Bakero','Murcia','1995-10-10','Gran Vía, 2, 4A',1,'ESO',15);
insert into alumnos values(654321,'Laura Gómez Fernández','Lorca','1994-05-10','Junterones, 10, 5B',2,'ESO',25);
insert into alumnos values(765432,'Beatriz Martínez Hernández','Murcia','1993-05-05','Plaza Mayor, 6, 3B',3,'ESO',5);
insert into alumnos values(987654,'Diego Martín Llorente','Alhama de Murcia','1990-06-03','Diego de la Cierva, 5, 7A',1,'BACHILLER',34);
insert into alumnos values(445544,'Juan Francisco Cano Riquelme','Murcia','1992-07-01','Plaza de Belluga, 3, 4A',4,'ESO',13);
insert into alumnos values(223322,'Raquel Riquelme Rubio','Lorca','1990-11-23','San Juan, 14, 3B',1,'BACHILLER',7);
insert into alumnos values(9988877,'Cristina Sánchez Bermejo','Murcia','1995-03-19','Torre de Romo, 7',1,'ESO',1);
insert into alumnos values(334455,'Pedro Jesús Rodríguez Soler','Alhama de Murcia','1994-03-10','Camino de Badel, 4',2,'ESO',11);
insert into alumnos values(334400,'Javier Ramánez Rodríguez','Murcia','1993-05-27','Gran Vía, 4, 3A',3,'ESO',NULL);
insert into alumnos values(993322,'Gema Rubio Colero','Lorca','1992-09-09','Plaza Fuensanta, 5, 7A',1,'BACHILLER',19);
insert into alumnos values(554411,'Joaquín Hernández González','Lorca','1991-12-12','Junterones, 4, 5A',2,'BACHILLER',14);