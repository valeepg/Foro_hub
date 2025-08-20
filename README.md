# Foro_hub
Este es un proyecto de API RESTful desarrollado con Spring Boot para un foro de discusión. La API permite a los usuarios registrarse, autenticarse y gestionar tópicos, respuestas y perfiles.

## Visión General del Proyecto
- Forohub simula las funcionalidades esenciales de un foro online, permitiendo a los usuarios interactuar de manera segura y eficiente. A través de sus diversos endpoints, la API maneja:

- Autenticación y Seguridad: Implementada con Spring Security y JWT (JSON Web Tokens) para proteger los datos y garantizar que solo los usuarios autorizados puedan acceder a los recursos.

- Gestión de Usuarios y Tópicos: La API permite a los usuarios registrarse, iniciar sesión, crear nuevos tópicos de discusión, actualizarlos y eliminarlos de forma lógica, manteniendo la integridad de la base de datos.

- Interacción con la Base de Datos: Utiliza Spring Data JPA y Hibernate para la persistencia de datos en una base de datos MySQL, garantizando una comunicación eficiente y escalable.

- Migraciones de Base de Datos: Flyway se encarga de gestionar el esquema de la base de datos de manera automatizada, aplicando scripts SQL en orden para mantener el control de versiones de la estructura de tu base de datos.
## Requisitos del Sistema
- Java 17 o superior

- Maven (para la gestión de dependencias)

- MySQL 8.0 o superior

- Postman/Insomnia (o cualquier cliente HTTP para probar los endpoints)

