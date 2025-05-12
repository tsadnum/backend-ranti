--Insertar Roles
BEGIN;
INSERT INTO roles (nombre)
VALUES
    ('ROL_USUARIO'),
    ('ROL_ADMIN');
COMMIT;

-- Insertar Usuarios
BEGIN;
INSERT INTO Usuario (username, password, nombre, apellido, fecha_nacimiento, telefono, dni, correo, ciudad, pais, estado)
VALUES
    ('franco', '$2a$12$PAFXBZGe1upTHNVdHTH.5e3gxtWgIOu5.RUS6kV4gAj5xkK7KVd2q', 'Franco', 'Ochoa', '2005-05-09', '982666205', '72164314', 'franco.ochoa@gmail.com', 'Lima', 'Perú', 'activo'),
    ('braulio', '$2a$12$PAFXBZGe1upTHNVdHTH.5e3gxtWgIOu5.RUS6kV4gAj5xkK7KVd2q', 'Braulio', 'Jungbluth', '2004-10-06', '942665515', '75664011', 'brauliojunglbuth15@gmail.com', 'Lima', 'Perú', 'activo'),
    ('maria', '$2a$12$PAFXBZGe1upTHNVdHTH.5e3gxtWgIOu5.RUS6kV4gAj5xkK7KVd2q', 'María', 'González', '1998-03-22', '987654321', '87654321', 'maria.gonzalez@hotmail.com', 'Arequipa', 'Perú', 'activo'),
    ('juan', '$2a$12$PAFXBZGe1upTHNVdHTH.5e3gxtWgIOu5.RUS6kV4gAj5xkK7KVd2q', 'Juan', 'Perez', '1990-11-15', '976543210', '65432178', 'juan.perez@yahoo.com', 'Cusco', 'Perú', 'activo');
COMMIT;

-- Insertar relaciones Usuario-Rol
BEGIN;
INSERT INTO user_roles (rol_id, usuario_id)
VALUES
    (1, 1), -- ROL_USUARIO → Franco
    (2, 2), -- ROL_ADMIN → Braulio
    (1, 3), -- ROL_USUARIO → María
    (2, 4); -- ROL_ADMIN  → Juan

COMMIT;

-- Insertar Posts
BEGIN;
INSERT INTO Post (titulo, descripcion, fecha_publicacion, estado, id_usuario)
VALUES
    ('Intercambio de libro', 'Intercambio de un libro de tecnología.', '2025-05-09', 'activo',  1),
    ('Cambio de videojuego', 'Intercambio de un videojuego de acción.', '2025-05-10', 'activo',  3),
    ('Intercambio de bicicleta', 'Ofrezco una bicicleta para intercambio.', '2025-05-08', 'activo', 4),
    ('Venta de curso online', 'Pongo en venta un curso avanzado de programación.', '2025-05-11', 'activo', 1);
COMMIT;

-- Insertar Artículos
BEGIN;
INSERT INTO Articulo (nombre, descripcion, publico, estado, usuario_id, id_post, inapropiado)
VALUES
    ('Libro de Java', 'Un libro para aprender programación en Java.', true, 'disponible', 1, 1, false),
    ('Videojuego FIFA 2025', 'Juego de fútbol para PlayStation.', true, 'disponible', 3, 2, false),
    ('Bicicleta de montaña', 'Bicicleta para deportes de aventura.', true, 'disponible', 4, 3, false),
    ('Curso de Python', 'Curso avanzado para aprender Python.', false, 'disponible', 1, 4, false);
COMMIT;

-- Insertar Imágenes
BEGIN;
INSERT INTO Imagen (url, descripcion, id_articulo)
VALUES
    ('https://example.com/imagen1.jpg', 'Portada del libro de Java.', 1),
    ('https://example.com/imagen2.jpg', 'Portada del videojuego FIFA 2025.', 2),
    ('https://example.com/imagen3.jpg', 'Bicicleta de montaña en color rojo.', 3);
COMMIT;

-- Insertar Solicitudes de Artículo
BEGIN;
INSERT INTO solicitud_articulo (estado, fecha_hora_solicitud, usuario_solicitado_id, usuario_solicitante_id, articulo_solicitado_id, articulo_ofrecido_id)
VALUES
    ('Completada', '2025-05-11', 3, 1, 2, 1),
    ('Pendiente', '2025-05-12', 4, 3, 3, 2),
    ('Completada', '2025-05-10', 1, 4, 1, 3);
COMMIT;

-- Insertar Valoraciones
BEGIN;
INSERT INTO Valoracion (Calificacion, comentario, fecha_hora_valoracion, solicitud_id, usuario_evaluador_id, usuario_evaluado_id)
VALUES
    (5, 'Intercambio excelente, todo en perfecto estado.', '2025-05-15T10:00:00', 1, 1, 3),
    (4, 'Buen intercambio, pero podría mejorar la comunicación.', '2025-05-16T14:00:00', 2, 3, 4),
    (4, 'El artículo es mejor de lo esperado, gracias por el intercambio.', '2025-05-17T16:00:00', 3, 4, 1);
COMMIT;

-- Insertar Etiquetas
BEGIN;
INSERT INTO Etiqueta (nombre)
VALUES
    ('geek'),
    ('gamer'),
    ('tech'),
    ('deportes');
COMMIT;

-- Relacionar etiquetas en artículos
BEGIN;
INSERT INTO articulo_etiqueta (articulo_id, etiqueta_id)
VALUES
    (1, 3),  -- tech
    (1, 1),  -- geek
    (2, 2),  -- gamer
    (3, 4);  -- deportes
COMMIT;