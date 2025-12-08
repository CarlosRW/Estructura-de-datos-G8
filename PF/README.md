# Proyecto Final - The Sandwich Guy

| Curso    | Estructuras de Datos    |
| :------- | :---------------------- |
| Código   | SC-304                  |
| Periodo  | III Cuatrimestre 2025   |
| Profesor | Luis Andrés Rojas Matey |
| Valor    | 50 %                    |

<br />

## Integrantes

| Nombre | Carné | Usuario Git | Correo Git |
|---|---|---|---|
| Carlos Eduardo Ramírez Wong | FI24037073 | CarlosRW | carlos.ramirezw23@gmail.com |
| Allan David Soto Suárez | FI24036133 | AllanSS213 | allansotosuarez070@gmail.com |
| Yirlania Córdoba Muñoz | Fl23031490 | Yirlaniam | yirlania55@gmail.com |
| Jimena Hernández Martínez | FH23013963 | Hernandezj1204 |Jimenahm1204@gmail.com |

---

## Objetivo

Aplicar los conocimientos adquiridos en el curso de Estructuras de Datos para desarrollar una aplicación de escritorio del juego **_The Sandwich Guy_** utilizando la versión **Java SE 21** y las estructuras de datos específicas solicitadas (Lista Doble, Pila, Lista Circular, Cola).

---

## Componentes y Estructuras de Datos

| Componente | Clase | Estructura de Datos Solicitada | Razón de la Estructura |
| :--- | :--- | :--- | :--- |
| **Carta** | `Carta.java` | Objeto/Clase propia | Almacena propiedades y valor numérico circular (1-13). |
| **Caja** | `Caja.java` | **Lista Doble** | Permite agregar y retirar eficientemente **todas** las cartas del juego para el proceso de barajado. |
| **Mazo** | `Mazo.java` | **Pila (LIFO)** | Las cartas se toman siempre de la **parte superior** (última en entrar) del mazo. |
| **Mano** | `Mano.java` | **Lista Circular** | Representa las cartas del jugador de forma continua, facilitando el recorrido y selección visual. |
| **Pozo** | `Pozo.java` | **Cola (FIFO)** | Las cartas se descartan al final y, si se recuperaran, se tomaría la carta más antigua (primera en entrar). |
| **Permutaciones** | `PermutationTree.java` | **Árbol/Listado de Nodos** | Evalúa todas las posibles permutaciones de 3 cartas para determinar si cumplen la condición de sándwich. |
|Interfaz |	`Interfaz.java` |	**JFrame (Swing)** | Componente principal que gestiona la interacción gráfica (GUI) y el flujo del juego. |
|Persistencia |	`GameStateXML.java` | **API de XML** | Clase auxiliar para la serialización y deserialización de los estados de las estructuras del juego (Guardar/Cargar). |
---

## Especificaciones Técnicas
| Componente | Detalle |
|---|---|
| Versión Java | Java SE 21 & JDK 21 (LTS) |
| IDE/Editor Utilizado | [Apache NetBeans IDE](https://netbeans.apache.org/front/main/index.html) |
| Interfaz Gráfica | [JFrame](https://docs.oracle.com/javase/8/docs/api/javax/swing/JFrame.html) |
| **Persistencia** | **GameStateXML (XML)** |
---

## Instructivo de Instalación, Compilación y Ejecución

### 1. Instalación

* Asegúrate de tener instalado el **JDK 21** o superior.
* Utiliza **Apache NetBeans IDE** (versión recomendada) o configura tu IDE/Editor preferido con el JDK 21.

### 2. Compilación

#### Usando NetBeans:

1. Abre NetBeans y selecciona **"Open Project"**.

2. Navega hasta la carpeta raíz del proyecto (`TheSandwichGuy`).

3. NetBeans configurará automáticamente la compilación usando el JDK 21.

4. Para compilar, haz clic derecho en el proyecto y selecciona **Clean and Build**.

### 3. Ejecución

#### Usando NetBeans:

1. Haz clic derecho en la `Interfaz.java` (la interfaz principal) o la clase que contiene el método `main` dentro del paquete `com.sandwichguy`.

2. Selecciona **"Run File"**.

* Esto abrirá la interfaz gráfica del juego y lo preparará para iniciar.

<br />

## Pasos de la Partida

Al ejecutar la aplicación, se debe seleccionar la opción **"Crear una partida nueva"** y seguir los siguientes pasos:

1- El juego se inicializa con el mensaje: “Juego listo. Haz click en **BARAJAR**”.

2- Haz clic en el botón **“BARAJAR”**. El sistema moverá todas las cartas de la **Caja** al **Mazo** y las mezclará.

3- Después de barajar, presiona **“REPARTIR MANO”**. El sistema reparte las cartas iniciales a la sección “TU MANO”.

4- Con tus cartas ya visibles en “TU MANO”, selecciona **exactamente 3 cartas**.

5- Haz clic en **“VALIDAR”**. El sistema usará la lógica de **`PermutationTree`** para encontrar si alguna de las 6 posibles secuencias forma un sándwich válido (distancia equidistante circular).

6- **Si el sándwich es válido:** Se te pedirá elegir la permutación. Luego, las 3 cartas jugadas se descartan al **Pozo**, y el jugador roba la cantidad de cartas correspondiente del **Mazo** (2, 3 o 4, según las bonificaciones de Palo o Color).

7- **Si el sándwich no es válido** (o no se encuentra una combinación válida): El jugador roba **1 carta de penalización** del **Mazo** y la añade a “TU MANO”, pero **sin descartar** las cartas seleccionadas.

El juego continúa hasta que el Mazo quede vacío (victoria) o no se pueda formar un sándwich válido y el jugador no pueda realizar más jugadas (derrota).

---

## Referencias y Herramientas

* **Repositorio del profesor:** https://github.com/larmcr/2025-III-SC-304
