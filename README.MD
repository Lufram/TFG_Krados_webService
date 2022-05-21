# TFG_Krados_webService <img src="https://media.giphy.com/media/hvRJCLFzcasrR4ia7z/giphy.gif" width="25px">

<img src="https://res.cloudinary.com/dpdob4mxw/image/upload/v1653066493/krados/krados_icon_sq_yukpin.svg" width="150px">

Krados webService se ha desarrollado con el fin de ofrecer un microservicio escalable y compatible con diferentes aplicaciones que puedan consumir sus recursos.  
Para mostrar el funcionamiento de este servicio se ha desarrollado una aplicación Android que simula una pequeña tienda que esta empezando a digitalizarse.   
La applicación Android se encuentra en el siguiente repositorio: https://github.com/Lufram/TFG_Krados_androidAPP.git.

También encontrarás el ejecutable para android en el repositorio
general del proyecto: https://github.com/jabaron56/TFG_Krados.git

***

## Tabla de contenido 🔖

- [Construido con](#construido-con) 🛠️
- [Instalación](#instalación) 📦
- [Autores](#autores) ✒️

***

### Construido con


### Herramientas 🔧

Una lista de las herramientas utilizadas en el proyecto:
* [XAMPP](https://www.apachefriends.org/es/index.html) - sistema de gestión de bases de datos MySQL, el servidor web Apache, PHP y Perl.
* [Intellij IDEA](https://www.jetbrains.com/es-es/idea/) - El IDE utilizado para el desarrollo del servicio REST.

## Instalación
_Estas instrucciones te permitirán obtener una copia del proyecto en funcionamiento en tu máquina local para propósitos de desarrollo y pruebas._

Mira **Deployment** para conocer como desplegar el proyecto.


### Pre-requisitos 📋

_Software necesario para obtener una copia del proyecto el proyecto_

-Xampp  
-Intellij IDEA  

### Instalación y Despliegue🔧

_Estas instrucciones te permitirán obtener una copia del proyecto en funcionamiento en tu máquina local para propósitos de desarrollo y pruebas._

1. _Lo primero es ejecutar el gestor XAMPP y arrancar apache y mysql._
2. _Después tendras que ingresar en la opción de admin de mysql disponible en el gestor XAMPP y crear la base de datos **krados_database**._
3. _Una vez creada la base de datos tendremos que dirigirnos al proyecto webService y mediante Intellij ejecutar el servicio **KradosApplication** que se encuentra en la ruta **\src\main\java\com\edix\krados**._
4. _Ahora tendremos que dirigirnos al proyecto webService y ejecutar mediante Intellij las clases que se encuentran en la ruta **\src\test\java\com\edix\krados** en el siguiente orden:_
   - KradosApplicationRoles.
   - KradosApplicationCategory.
   - KradosApplicationProduct.
5. _Con las clases anteriormente mencionadas se llena la base de datos con unos datos de prueba y ya estaría disponible el servicio._
6. _Por último, se podría comprobar el funcionamiento del servicio mediante Postman o ejecutando el proyecto de Android mencionado en la introducción_

## Autores

* **Javier Barón Pérez** - (https://github.com/jabaron56)
* **Ismael De Gregorio López** - (https://github.com/Lufram)
