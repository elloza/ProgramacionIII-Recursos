# GenAI Wrapper - Biblioteca Simplificada para Gemini API

## 📖 Descripción

Este proyecto es un **wrapper simplificado** de la biblioteca oficial de Google para Gemini API, empaquetado como **fat-jar** con todas las dependencias incluidas. Está diseñado específicamente para **asignaturas de programación** donde se necesita integrar IA generativa sin la complejidad de herramientas de build como Maven o Gradle.

### Biblioteca Original
- **Fuente**: [googleapis/java-genai](https://github.com/googleapis/java-genai) - SDK oficial de Google
- **Ventajas del wrapper**: 
  - ✅ Un solo archivo JAR con todas las dependencias
  - ✅ API simplificada para casos de uso educativos
  - ✅ Configuración mínima requerida
  - ✅ Ejemplos completos incluidos


Al final de esta asignatura deberíais ser capaces de usar la API oficial directamente con Maven, pero este wrapper os permite empezar a experimentar con IA generativa en Java de forma rápida y sencilla.

## 🎯 Casos de Uso Implementados

El archivo `App.java` demuestra **6 casos de uso principales**:

### 1. 📝 Generación de Texto Simple
```java
String respuesta = genai.generateText("Explica qué es la POO en Java");
```
- Prompt → Respuesta en texto natural
- Ideal para: Explicaciones, resúmenes, generación de contenido

### 2. 🖼️ Análisis Multimodal
```java
String resultado = genai.generateWithFiles(prompt, List.of(Path.of("imagen.png")));
```
- Texto + Archivos → Análisis combinado
- Soporta: Imágenes, PDFs, documentos
- Ideal para: Análisis de contenido, OCR, descripción de imágenes

### 3. 🎯 Objetos Java Estructurados
```java
MovieInfo pelicula = genai.generateJson(prompt, schema, MovieInfo.class);
```
- Prompt → Objeto Java tipado
- Usa SimpleSchemas para conversión automática
- Ideal para: Extracción de datos, parsing inteligente

### 4. 📚 Objetos Anidados Complejos
```java
Libro libro = genai.generateJson(prompt, schema, Libro.class);
// Maneja automáticamente Editorial y List<Reseña>
```
- Detecta y maneja sub-objetos automáticamente
- Ideal para: Estructuras de datos complejas

### 5. 📁 Análisis de Archivos con Metadatos
```java
FileMetadata metadata = genai.generateJson(prompt, archivos, schema, FileMetadata.class);
```
- Archivos → Metadatos estructurados
- Ideal para: Catalogación, indexación automática

### 6. 🔢 Conteo de Tokens
```java
int tokens = genai.countTokens(texto);
```
- Útil para: Control de costes, límites de API

## 🚀 Configuración e Instalación

### Requisitos Previos
- **Java 11+**
- **API Key de Google Gemini** ([Obtener aquí](https://aistudio.google.com/app/apikey))
- **Conexión a Internet**

### Paso 1: Configurar API Key

**Windows (PowerShell):**
```powershell
setx GOOGLE_API_KEY "tu-api-key-aqui"
```

**Windows (CMD):**
```cmd
set GOOGLE_API_KEY=tu-api-key-aqui
```

**Linux/Mac:**
```bash
export GOOGLE_API_KEY="tu-api-key-aqui"
```

### Paso 2: Descargar el Fat-JAR
1. Descarga `genai-fatjar-1.0.0.jar` desde releases
2. Colócalo en tu directorio de proyecto

### Paso 3: Incluir en tu proyecto de VS Code en Java

Asegúrate de añadir este JAR a tu proyecto y que aparezca e Referenced Libraries.

### Paso 4: Prueba los ejemplos proporcionados

Si detectas errores en tiempo de ejecución activa el logging con:
```java
GenAiConfig.setDevelopmentMode();
```

Esto cambiará el logger a nivel WARNING para ver posibles errores.


## 📁 Estructura del Proyecto

```
gemini-wrapper-example/
├── src/
│   └── App.java                 # Ejemplos completos
├── resources/
│   ├── java.png                 # Imagen de ejemplo
│   └── attention_is_all_you_need.pdf  # PDF de ejemplo
├── genai-fatjar-1.0.0.jar      # Biblioteca wrapper
└── README.md                    # Esta documentación
```

## 💡 Usar en Tus Proyectos

### Configuración Básica
```java
import es.usal.genai.*;

// Configuración desde variable de entorno
GenAiConfig config = GenAiConfig.fromEnv("gemini-2.0-flash");

try (GenAiFacade genai = new GenAiFacade(config)) {
    // Tu código aquí
}
```

### Ejemplo Mínimo
```java
public class MiProyecto {
    public static void main(String[] args) {
        GenAiConfig.setSilentMode(); // Sin logs
        GenAiConfig config = GenAiConfig.fromEnv("gemini-2.0-flash");
        
        try (GenAiFacade genai = new GenAiFacade(config)) {
            String respuesta = genai.generateText("¿Qué es Java?");
            System.out.println(respuesta);
        }
    }
}
```

### Crear Tus Propias Clases
```java
public class MiClase {
    public String nombre;
    public int edad;
    public List<String> hobbies;
    public String opcionalComentario; // Campos "opcional*" son opcionales
}

// Uso
Schema schema = SimpleSchemas.from(MiClase.class);
MiClase objeto = genai.generateJson(prompt, schema, MiClase.class);
```

## 🔧 Configuración Avanzada

### Modos de Logging
```java
GenAiConfig.setSilentMode();        // Sin logs (recomendado)
GenAiConfig.setDevelopmentMode();   // Solo warnings/errores
```

### Modelos Disponibles

Consultadlos aquí: [Modelos Gemini](https://ai.google.dev/gemini-api/docs/models?hl=es-419)
- `gemini-2.5-flash` (recomendado)
- `gemini-flash-latest`

## 🎓 Uso Educativo

### Para Profesores
- Integración simple en ejercicios prácticos
- Sin dependencias complejas que configurar
- Ejemplos listos para usar
- Casos de uso progresivos (simple → complejo)

### Para Estudiantes
- Enfócate en la lógica, no en la configuración
- Experimenta con IA generativa fácilmente
- Aprende patrones de programación modernos
- Practica con APIs reales

## ❓ Solución de Problemas

### Error: "API Key no configurada"
- Verifica que `GOOGLE_API_KEY` esté configurada
- Reinicia el terminal después de configurar
- En Windows, usa `echo %GOOGLE_API_KEY%` para verificar
- En Linux/Mac, usa `echo $GOOGLE_API_KEY`

### Error: "ClassPath"
- Asegúrate de incluir el JAR en el classpath
- En Windows usa `;` y en Linux/Mac usa `:`

### Error: "No internet"
- Verifica tu conexión
- Algunos firewalls corporativos bloquean APIs

## 📄 Licencia

Este proyecto es un wrapper educativo de la biblioteca oficial de Google. Consulta las licencias originales para uso comercial.

---

**🌟 ¡Perfecto para aprender IA generativa en Java sin complicaciones!**



