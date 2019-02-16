# School data manager (Android)

El objeto de este proyecto ha sido la creación de una aplicación para Android que permita gestionar los datos de un centro de estudios. Entre otras funcionalidades permite a estudiantes y profesores acceder a los horarios de asignaturas, tutorías e información sobre las aulas disponibles. Incluye además un sistema de autentificación de usuarios mediante PHP. Implementa también la sincronización entre la base de datos local y la del servidor. Para los dos puntos anterores se hace uso de un servidor Tomcat, el cual se puede descargar accediendo desde el siguiente enlace: https://github.com/Alp3sa/tomcat-server-sdm. A lo largo de este documento se muestra gran parte de la documentación relativa al diseño final de la aplicación. A continuación se incluyen los enlaces la plataforma SCRUM utilizada para la realización del proyecto, junto con los enlaces a tres demos que muestran el funcionamiento de la aplicación.

Enlace de la plataforma SCRUM: https://tree.taiga.io/project/alp3sa-school-data-manager/timeline

Demos del sistema de autentificación: https://tree.taiga.io/project/alp3sa-school-data-manager/issue/6

Demo de la sincronización: https://tree.taiga.io/project/alp3sa-school-data-manager/issue/5

![alt text](https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/screenshots/portada.jpg)

# 1. Introducción.

En la primera fase del proyecto diseñamos un plan previo para enfrentar el desarrollo de la aplicación que pretendíamos crear. Este diseño preliminar es orientativo y sirve como ayuda para hacernos una idea de lo que queremos crear. Igualmente surgen dificultades de todo tipo que debemos superar o bien variar el rumbo con respecto a nuestros objetivos previos. En este sentido se hace necesario una vez ya avanzado el proyecto hacer una segunda revisión de lo que hemos hecho antes de enfrentar el desarrollo del prototipo final.

En este documento se pretende realizar una revisión del desarrollo del proyecto. Analizando su estado actual tendremos la oportunidad de comprobar qué cosas han variado con respecto a lo que en un principio estimamos. También podremos ver, en general, cuales son los principales problemas que se presentan, qué cosas se podrían mejorar y cuáles son los aspectos más reseñables del proceso de desarrollo. En resumen, veremos la correspondencia de nuestro prototipo con el diseño preliminar.

# 2. Tipos de usuarios.

Uno de los apartados que ha variado con respecto al diseño previo ha sido la tipología de usuarios. En un principio se estimo que harían uso de la aplicación tres tipos diferentes: alumnos, profesores y el administrador o los administradores de la aplicación. Este punto ha variado pues los profesores pueden ejercer el rol de administrador sin mayor problema. Además el rol de administrador es algo redundante en una aplicación relativamente pequeña como esta. Por ello se ha estimado oportuno valorar sólo dos tipos: alumnos y profesores. 

Por un lado están los alumnos que sólo pueden consultar información y realizar todo tipo de subconsultas. Por otro lado están los profesores que pueden no sólo consultar sino también insertar, modificar y borrar información. Lo ideal en este punto sería añadir una tabla de auditoría para asegurarnos de que no se produzcan anomalías. O, en caso de que se produzcan, saber qué o quién ha sido la causa del problema. Debido a todo lo anterior se ha decidido que el cuadro de permisos quede como se muestra a continuación.

| Permisos  | Profesores | Alumnos |
| --- | --- | --- |
| Acceder a la información sobre el centro y sus aulas. | X | X |
| Cambiar datos del centro y alterar el listado de aulas disponibles. | X |  |
| Ver datos de las asignaturas que imparte la escuela. | X | X |
| Modificar los datos de las asignaturas que se imparten. | X |  |
| Consultar las tutorías que imparte cada profesor. | X | X |
| Modificar el horario de tutorías. | X |  |

Se han eliminado de este cuadro dos elementos. Uno de ellos es la capacidad de los usuarios de disponer de los datos personales que le son propios. El otro hacía referencia a la capacidad de modificar los datos personales. Esto último es algo que se estima se añadirá en un futuro, si se permite la posibilidad de añadir datos muy variables como puede ser el email del usuario. De cualquier forma estos dos puntos se ha preferido omitirlos por el momento, puesto que no son prioritarios. E incluso se podría decir que son innecesarios en cierta medida. Antes cabría mejor otorgar la posibilidad al usuario de recuperar su contraseña de forma segura.

# 3.	Diagrama de casos de uso.

A continuación, sirviendo como desarrollo del apartado anterior y en base a las modificaciones realizadas, se presenta el diagrama de casos de uso. En él veremos de manera más gráfica y simplificada los tipos de usuarios que pueden ya hacer uso de nuestra aplicación:

![alt text](https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/diagrams/casos%20de%20uso.jpg)

# 4. Diseño final y mockups.

La creación de mockups, aun de forma rudimentaria, es una herramienta fundamental para el diseño de nuestras aplicaciones. Aunque sea una aproximación nos permite crear un diseño bastante básico de lo que deseamos para nuestra aplicación. En la primera fase creamos una vista de cada actividad que recorrería el usuario al hacer uso de nuestra app. En este punto nos limitaremos a comprobar la correspondencia de esta aproximación con el diseño actual. En relación a esto prácticamente no ha variado salvo en algunos apartados. Como veremos en las capturas, en la comparativa con los mockups que se presenta a continuación, se han añadido además nuevas opciones.

| <img src="https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/screenshots/1.jpg" width="258" height="430" /> | <img src="https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/mockups/mockup-login.jpg" width="258" height="430" /> |
| --- | --- |

Comenzamos con el inicio de la aplicación, que nos invita a registrarnos o loguearnos. Como podemos ver se han añadido dos opciones nuevas en la parte superior.

| <img src="https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/screenshots/2.jpg" width="258" height="430" /> | <img src="https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/mockups/mockup-signup1.jpg" width="258" height="430" /> |
| --- | --- |

La primera de las opciones nos permite descargarnos las normas del centro. Si elegimos registrarnos primero deberemos introducir un nombre único de usuario.

| <img src="https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/screenshots/3.jpg" width="258" height="430" /> | <img src="https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/mockups/mockup-signup2.jpg" width="258" height="430" /> |
| --- | --- |

La segunda opción muestra un texto de ayuda. Para ello se ha utilizado clase AlertDialog. Al continuar deberemos introducir nuestros datos personales que el sistema validará.

| <img src="https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/screenshots/4.jpg" width="258" height="430" /> | <img src="https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/mockups/mockup-signup3.jpg" width="258" height="430" /> |
| --- | --- |

Introducimos los datos que sean necesarios.

| <img src="https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/screenshots/5.jpg" width="258" height="430" /> | <img src="https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/mockups/mockup-signup4.jpg" width="258" height="430" /> |
| --- | --- |

Y una vez lleguemos a este punto elegiremos nuestro rol como usuarios.

| <img src="https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/screenshots/6.jpg" width="258" height="430" /> | <img src="https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/mockups/mockup-signup5.jpg" width="258" height="430" /> |
| --- | --- |

Para terminar elegiremos nuestra contraseña e introduciremos un código de validación que deberá facilitarnos el centro. El código de validación por defecto es 1234. Cabe señalar que la contraseña se almacena en forma de hash, de tal forma que sólo el usuario tenga conocimiento de ella.

| <img src="https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/screenshots/7.jpg" width="258" height="430" /> | <img src="https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/mockups/mockup-list.jpg" width="258" height="430" /> |
| --- | --- |

Iniciada la sesión nos aparecerá un menú de 4 pestañas. Tres pestañas serán listados y la última será un mapa con la localización del centro. En el NavigationView de la aplicación se han añadido varias opciones, entre ellas se encuentra la opción de cerrar sesión.

| <img src="https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/screenshots/11.jpg" width="258" height="430" /> | <img src="https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/mockups/mockup-modClassrooms.jpg" width="258" height="430" /> |
| --- | --- |

Desde el ActionBar de las pestañas-listado podremos modificar los datos de las aulas si somos administradores. Como podemos ver es posible obtener un resultado muy parecido al que diseñamos en los mockups.

| <img src="https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/screenshots/12.jpg" width="258" height="430" /> | <img src="https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/mockups/mockup-modSubjects.jpg" width="258" height="430" /> |
| --- | --- |

En algunos formularios de consulta aun no se ha añadido la opción de añadir imágenes. Como vimos en el apartado anterior, en el diagrama de casos de uso, podremos modificar los listados de asignaturas si somos administradores o docentes.

| <img src="https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/screenshots/13.jpg" width="258" height="430" /> | <img src="https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/mockups/mockup-modTutorships.jpg" width="258" height="430" /> |
| --- | --- |

En los dos últimos formularios se ha añadido un campo que hace referencia a los registros de la tabla de clases disponibles.

Cabría añadir algunas cosas más a lo mencionado:

* A parte de poder insertar, modificar y borrar registros, es posible realizar subconsultas. Si pulsáramos en el primer icono que aparece en la parte superior de la captura situada a la izquierda, una vez hayamos mantenido seleccionado dos segundos un registro, nos llevará al aula al que está referenciada. 

* Además en el apartado de tutorías disponemos de un cuadro de búsqueda para si lo deseamos filtrar registros.

* También existe la posibilidad, utilizando el último icono que aparece en el ActionBar, de descargar como imagen la lista que aparece en pantalla.

<img src="https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/screenshots/10.jpg" width="258" height="430" />

Todas las opciones que se refieren a insertar, modificar y borrar registros sólo aparecen si somos parte del profesorado. En otro caso sólo aparecerían el resto de opciones que se han mencionado. Aunque todo es mejorable hay que señalar que, como ya se ha mencionado, la creación de mockups constituye una herramienta fundamental. Esta nos permite valorar diferentes diseños y quedarnos con el que mejor se adapte a nuestras necesidades. 

# 5.	Modelo E/R, UML y modelo relacional.

En un principio determinamos que nuestra aplicación dispondría de una base de datos con cinco (5) tablas: AULAS, ASIGNATURAS, TUTORÍAS, USUARIOS y ASIGNACIONES. Está última tabla de asignaciones correspondería a las matriculaciones de los alumnos y al reparto de las asignaturas entre los profesores. Este punto se ha preferido omitir por el momento por varias razones. Para la realización del primer prototipo no es prioritario ni necesario. Además los alumnos deben poder disponer de toda información referente a cualquier asignatura. Por razones similares también se ha estimado oportuno desvincular la tabla de usuarios.

Teniendo en cuenta los cambios señalados el diagrama E/R de la base de datos, al menos la parte operativa, quedaría como sigue a continuación:

![alt text](https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/diagrams/DiagramaER-Prototipo.png)

Para la implementación de nuestra base de datos ha bastando con seguir el modelo entidad/relación, que se realizó en la primera fase. De hecho la estructura de la base de datos prácticamente es la misma, salvo por las relaciones de dos tablas: Enrollment y User. Caso contrario es el modelo UML que se ha trasladado a la aplicación en su totalidad. Aunque actualmente la clase Enrollment no está en uso y la clase User no tiene ninguna relación con las demás, al menos por ahora.

Por último se presenta a continuación el modelo relacional de la base de datos donde se muestran únicamente las tablas que se encuentran en uso en el prototipo inicial.

![alt text](https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/diagrams/diagrama%20relacional.jpg)

# 6.	Diagrama Gantt.

El diagrama de Gantt nos permite planificar el tiempo del que disponemos para la consecución del proyecto. Este no ha variado en nada desde el principio, pues las fechas principales no han cambiado. Si bien en las últimas semanas la carga de trabajo ha sido mayor.

![alt text](https://raw.githubusercontent.com/Alp3sa/school-data-manager-android/master/images/diagrams/diagrama%20de%20gantt.jpg)
