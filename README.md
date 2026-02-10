# Servidor Web Multihilo en Java

Este proyecto implementa un servidor web básico en Java como parte del curso **Computación en Internet II**.  
El servidor utiliza sockets TCP, maneja múltiples solicitudes concurrentes mediante hilos y soporta solicitudes HTTP usando el método GET.

---

## Funcionalidades Implementadas

- Escucha conexiones TCP en un **puerto configurable mayor a 1024**.
- Opera de forma continua.
- Maneja **múltiples clientes simultáneamente** (un hilo por solicitud).
- Interpreta solicitudes **HTTP/1.0 y HTTP/1.1** usando el método **GET**.
- Extrae correctamente el **recurso solicitado**.
- Muestra por consola:
  - La línea de solicitud HTTP.
  - Los encabezados HTTP recibidos.
- Responde con una **estructura HTTP válida** (línea de estado, headers y cuerpo) usando CRLF.
- Sirve:
  - Archivos HTML.
  - Imágenes JPG, JPEG y GIF.
- Determina correctamente el **tipo MIME** del recurso solicitado.
- Maneja recursos inexistentes devolviendo **HTTP 404** con un archivo de error personalizado.
- Cierra correctamente sockets y streams sin afectar la concurrencia.

---


- `ServidorWeb.java`:  
  Clase principal que crea el `ServerSocket`, solicita el puerto y acepta conexiones.
- `SolicitudHttp.java`:  
  Clase que maneja una solicitud HTTP por hilo.
- `www/`:  
  Carpeta raíz del servidor web donde se alojan los recursos.

---

## Requisitos de Ejecución

- Java JDK 17 o superior.
- Puerto disponible mayor a 1024.

---

## Cómo Ejecutar el Servidor

1. Compilar el proyecto:
   ```bash
   javac ServidorWeb.java SolicitudHttp.java
Ejecutar el servidor:

## java ServidorWeb


Ingresar un puerto válido cuando se solicite (por ejemplo, 5000).

## Acceder desde el navegador:

http://localhost:5000/


## Acceso a la página principal:

http://localhost:PUERTO/


## → Devuelve index.html.

Solicitud de archivo HTML:

## http://localhost:PUERTO/index.html


## → Devuelve el archivo correctamente.

Carga de imágenes:

Imágenes JPG y GIF referenciadas desde HTML.

El navegador realiza solicitudes GET independientes por cada imagen.

Recurso inexistente:

http://localhost:PUERTO/noexiste.html


## → Devuelve 404 Not Found con el archivo 404.html.

Método HTTP no soportado:

Solicitudes distintas de GET reciben:

501 Not Implemented


## Concurrencia:

Múltiples pestañas o clientes accediendo simultáneamente.

Cada solicitud se maneja en un hilo independiente.

## Autor

Juan Diego Balanta Molina
