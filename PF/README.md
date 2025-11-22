# Projecto Final - The Sandwich Guy

## Integrantes

| Nombre | Carné | Usuario Git | Correo Git |
|---|---|---|---|
| Carlos Eduardo Ramírez Wong | FI24037073 | CarlosRW | carlos.ramirezw23@gmail.com |
| Allan David Soto Suárez | FI24036133 | AllanSS213 | allansotosuarez070@gmail.com |
| Yirlania Córdoba Muñoz | Fl23031490 | Yirlaniam | yirlania55@gmail.com |
| Jimena Hernández Martínez | FH23013963 | Hernandezj1204 |Jimenahm1204@gmail.com |

## Especificaciones Técnicas
| Componente | Detalle |
|---|---|
| Versión Java | Java SE 21 & JDK 21 (LTS) |
| IDE/Editor Utilizado | [Apache NetBeans IDE](https://netbeans.apache.org/front/main/index.html) |
| Interaz Gráfica | [JFrame](https://docs.oracle.com/javase/8/docs/api/javax/swing/JFrame.html) |

## Instructivo de Instalación, Compilación y Ejecución

### 1. Instalación

- Asegúrate de tener instalado el JDK 21 o superior.
- Utiliza NetBeans IDE (versión recomendada) o configura tu IDE/Editor preferido con el JDK 21.

### 2. Compilación
Usando NetBeans:

- Abre NetBeans y selecciona "Open Project".

- Navega hasta la carpeta raíz del proyecto (TheSandwichGuy).

- NetBeans configurará automáticamente la compilación usando el JDK 21.

- Para compilar, haz clic derecho en el proyecto y selecciona Clean and Build.

### 3. Ejecución
Usando NetBeans:

- Haz clic derecho en la clase Main.java dentro del paquete com.sandwichguy.

- Selecciona "Run File".
  
Esto abrirá la interfaz gráfica del juego y lo preparará para iniciar.

- La interfaz mostrará el mensaje:
  
“Juego listo. Haz click en BARAJAR”.

En este punto el mazo está preparado, pero aún no barajado.

- Haz clic en el botón “BARAJAR”.
  
El mazo se mezclará completamente y el juego quedará listo para repartir.

- Después de barajar, presiona “REPARTIR MANO”.
  
El sistema repartirá automáticamente las cartas iniciales.
Las cartas repartidas aparecerán en la sección “TU MANO”.

- Con tus cartas ya visibles en “TU MANO”, selecciona 3 cartas.
  
Estas 3 cartas se usarán para validar si juntas forman un posible sándwich (combinación válida según las reglas internas del juego).

- Al elegir las 3 cartas, el juego evaluará si forman un sándwich.
  
Si la combinación es válida, el juego te lo indicará.
Si no lo es, podrás intentar otra combinación mientras sigas teniendo cartas disponibles.

- Si deseas continuar la partida, presiona “DESCARTAR”.

Esto permitirá deshacerte de la combinación actual y seguir jugando con las cartas restantes o solicitar nuevas acciones si el juego lo permite.

- Repite el proceso de seleccionar cartas, validar combinaciones y descartar hasta completar todos los sándwiches posibles o quedarte sin cartas útiles.

- El juego termina cuando ya no puedes formar más sándwiches o cuando el mazo se ha agotado.





