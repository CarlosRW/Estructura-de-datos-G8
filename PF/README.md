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

| Componente | Clase | Estructura de Datos Solicitada |
| :--- | :--- | :--- |
| Carta | Carta.java | Objeto/Clase propia |
| Caja | Caja.java | Lista Doble |
| Mazo | Mazo.java | Pila |
| Mano | Mano.java | Lista Circular |
| Pozo | Pozo.java | Cola |

---

## Especificaciones Técnicas
| Componente | Detalle |
|---|---|
| Versión Java | Java SE 21 & JDK 21 (LTS) |
| IDE/Editor Utilizado | [Apache NetBeans IDE](https://netbeans.apache.org/front/main/index.html) |
| Interfaz Gráfica | [JFrame](https://docs.oracle.com/javase/8/docs/api/javax/swing/JFrame.html) |

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

### Pasos de la Partida

## Al ejecutar la aplicación, se debe seleccionar la opción **"Crear una partida nueva"** y seguir los siguientes pasos:

1- El juego se inicializa con el mensaje: “Juego listo. Haz click en BARAJAR”. El mazo está preparado en la Caja, pero aún no barajado.

2- Haz clic en el botón “BARAJAR”. El sistema moverá y mezclará las cartas de la Caja al Mazo.

3- Después de barajar, presiona “REPARTIR MANO”. El sistema repartirá automáticamente las ocho cartas iniciales a la sección “TU MANO”.

4- Con tus cartas ya visibles en “TU MANO”, selecciona 3 cartas. (Valores: 2-10 = número; J, Q, K = 10; As = 1 o 11).

5- El juego evaluará si las cartas forman un 21 Perfecto o un sándwich válido, e indicará las permutaciones y la cantidad de cartas a tomar del Mazo.

6- Si la combinación es válida (21 o sándwich), presiona “DESCARTAR” para enviar las tres cartas al Pozo y tomar la cantidad de cartas correspondiente (4 por 21, o 2, 3 o 4 por sándwich).

7- Si la combinación no es válida, el jugador roba 1 carta del Mazo (penalización) y la añade a “TU MANO” sin descartar.

El juego continúa hasta que el Mazo quede vacío (victoria) o no se pueda formar una combinación válida y “TU MANO” exceda un límite de cartas establecido (derrota).
---

## Referencias y Herramientas

* **Repositorio del profesor:** https://github.com/larmcr/2025-III-SC-304

---

