-- Insertar Perfiles
INSERT INTO perfiles (nombre, activo) VALUES
('Estudiante', TRUE),
('Profesor', TRUE),
('Moderador', TRUE),
('Administrador', TRUE),
('Invitado', TRUE);

-- Insertar Usuarios
INSERT INTO usuarios (nombre, email, contrasena, perfil_id, activo) VALUES
('Juan Pérez', 'juan@example.com', '$2a$10$xsyPyqqhlSHre9DrHepNDOXSXSWWOsIcF4UscecPw5N7a8SFKj6MG', 1, TRUE),
('Ana Gómez', 'ana@example.com', '$2a$10$xsyPyqqhlSHre9DrHepNDOXSXSWWOsIcF4UscecPw5N7a8SFKj6MG', 2, TRUE),
('Carlos Ruiz', 'carlos@example.com', '$2a$10$xsyPyqqhlSHre9DrHepNDOXSXSWWOsIcF4UscecPw5N7a8SFKj6MG', 3, TRUE),
('Lucía Torres', 'lucia@example.com', '$2a$10$xsyPyqqhlSHre9DrHepNDOXSXSWWOsIcF4UscecPw5N7a8SFKj6MG', 4, TRUE),
('Pedro Martínez', 'pedro@example.com', '$2a$10$xsyPyqqhlSHre9DrHepNDOXSXSWWOsIcF4UscecPw5N7a8SFKj6MG', 1, TRUE);

-- Insertar Cursos
INSERT INTO cursos (nombre, categoria, activo) VALUES
('Java Backend', 'Programación', TRUE),
('HTML y CSS', 'Diseño Web', TRUE),
('Spring Boot Avanzado', 'Frameworks', TRUE),
('Bases de Datos MySQL', 'Bases de Datos', TRUE),
('Fundamentos de Git', 'Control de Versiones', TRUE);

-- Insertar Tópicos
INSERT INTO topicos (titulo, mensaje, fecha_creacion, estatus, autor_id, curso_id, activo) VALUES
('¿Cómo configurar Spring Boot?', 'Tengo dudas sobre la configuración inicial.', NOW(), 'NO_RESPONDIDO', 1, 3, TRUE),
('Problemas con relaciones en MySQL', 'Me da error al usar claves foráneas.', NOW(), 'NO_RESPONDIDO', 2, 4, TRUE),
('Mejor editor para HTML y CSS', '¿Cuál recomiendan para empezar?', NOW(), 'NO_RESPONDIDO', 3, 2, TRUE),
('Errores comunes en Git', '¿Qué errores debo evitar cuando hago merge?', NOW(), 'NO_RESPONDIDO', 4, 5, TRUE),
('Consejos para aprender Java rápido', '¿Cómo mejorar mi lógica con Java?', NOW(), 'NO_RESPONDIDO', 5, 1, TRUE);

-- Insertar Respuestas
INSERT INTO respuestas (mensaje, fecha_creacion, topico_id, autor_id, solucion, activo) VALUES
('Puedes usar Spring Initializr para empezar.', NOW(), 1, 2, FALSE, TRUE),
('Revisa que las columnas tengan el mismo tipo de dato.', NOW(), 2, 3, FALSE, TRUE),
('VS Code es excelente para empezar.', NOW(), 3, 4, FALSE, TRUE),
('Evita hacer merge sin revisar los conflictos.', NOW(), 4, 5, FALSE, TRUE),
('Practica haciendo ejercicios pequeños primero.', NOW(), 5, 1, FALSE, TRUE);
