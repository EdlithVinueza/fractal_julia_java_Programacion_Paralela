# Fractal Julia Set - Implementación en Java

## **📋 Descripción del Proyecto**

Este proyecto implementa la visualización del **Conjunto de Julia** utilizando Java y OpenGL (LWJGL). El conjunto de Julia es un fractal que genera patrones complejos a partir de iteraciones en el plano complejo.

## **🏗️ Estructura del Proyecto**

### **Clases Principales**

### **1. `FractalMain` - Clase Principal**

- Maneja la ventana y renderizado usando LWJGL
- Configuración OpenGL y gestión de texturas
- Control de entrada (teclado) para interactuar con el fractal

### **2. `FractalCpu` - Cálculo del Fractal**

- Implementa el algoritmo iterativo del conjunto de Julia
- Genera el buffer de píxeles con los colores del fractal
- Método `julia_serial_2()` para cálculo secuencial

### **3. `FractalParam` - Parámetros y Configuración**

- Define constantes y parámetros del fractal
- Paleta de colores para la visualización
- Función de conversión de formato de color

## **🔬 Algoritmo del Conjunto de Julia**

### **Fórmula Principal**

java

```
zₙ₊₁ = zₙ² + c
```

Donde:

- `z` es un número complejo (z = x + yi)
- `c` es una constante compleja (definida en `FractalParam`)

### **Implementación en `divergente_2()`**

java

```
double dr = zr * zr - zi * zi + FractalParam.c_real;// Parte realdouble di = 2.0 * zr * zi + FractalParam.c_imag;// Parte imaginaria
```

## **🎨 Sistema de Colores**

### **Formato de Color**

El proyecto utiliza conversión entre formatos de color:

### **Función `bswap32()`**

java

```
public static int bswap32(int a) {
    return ((a & 0x000000FF) << 24) |// Byte 0 → 24((a & 0x0000FF00) << 8)  |// Byte 1 → 16((a & 0x00FF0000) >>> 8) |// Byte 2 → 8((a & 0xFF000000) >>> 24);// Byte 3 → 0}
```

### **Paleta de Colores (`COLOR_RAMP`)**

La paleta contiene 16 colores en formato **BGRA** que se convierten a **ARGB**:

| **Color Original (BGRA)** | **Color Convertido (ARGB)** | **Visualización** |
| --- | --- | --- |
| `0x0FFF8BFF` | `0xFF8BFF0F` | Azul-Verde |
| `0x0FF38CFF` | `0xFF8CFF0F` | Verde-Azul |
| `0x10E88DFF` | `0xFF8DE810` | Verde |
| ... | ... | ... |

### **Explicación del Formato de Color**

### **Formato BGRA (Original)**

- **B**: Blue (Azul) - 0xFF
- **G**: Green (Verde) - 0x8B
- **R**: Red (Rojo) - 0xFF
- **A**: Alpha (Transparencia) - 0x0F

### **Formato ARGB (OpenGL)**

- **A**: Alpha - 0xFF (completamente opaco)
- **R**: Red - 0x8B
- **G**: Green - 0xFF
- **B**: Blue - 0x0F

**¿Por qué es necesaria la conversión?**

- Los colores originales están en formato **little-endian** (BGRA)
- OpenGL espera colores en formato **ARGB** o **RGBA**
- Sin la conversión, los canales de color se intercambian incorrectamente

## **🎮 Controles**

- **ESC**: Cerrar la aplicación
- **UP Arrow**: Aumentar iteraciones (+10)
- **DOWN Arrow**: Disminuir iteraciones (-10, mínimo 10)

## **⚙️ Parámetros Configurables**

java

```
// En FractalParam.javapublic static double c_real = -0.7;// Parte real de cpublic static double c_imag = 0.27015;// Parte imaginaria de cpublic static int max_iterations = 10;// Iteraciones máximaspublic static final int WIDTH = 1600;// Ancho de ventanapublic static final int HEIGHT = 900;// Alto de ventana
```

## **🚀 Compilación y Ejecución**

### **Requisitos**

- Java JDK 8+
- LWJGL 3
- OpenGL

### **Ejecución**

bash

```
java -cp .:lwjgl.jar com.programacion.paralela.FractalMain
```

## **🔍 Características Técnicas**

- **Resolución**: 1600×900 píxeles
- **Renderizado**: Textura OpenGL
- **Algoritmo**: Cálculo iterativo en CPU
- **Paleta**: 16 colores con degradado
- **Rango**: Plano complejo desde (-1.5, -1.0) hasta (1.5, 1.0)


