CREATE TABLE users (
    id INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    e_mail VARCHAR(100) NOT NULL,
    contraseña VARCHAR(50) NOT NULL
);

INSERT INTO users VALUES
(1, 'Aitor Nillos', 'aitor@example.com', '1234'),
(2, 'Andrés Trozado', 'andres@example.com', '12345'),
(3, 'Elena Nitodelbosque', 'elena@example.com', '123456'),
(4, 'Pablo Queado', 'pablo@example.com', '1234567');
