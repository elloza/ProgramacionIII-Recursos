# Ejercicio: Web2Markdown con Firecrawl4j

## 📋 Objetivo del Ejercicio

Implementar una aplicación de consola en Java que permita convertir URLs (páginas web y PDFs) a Markdown usando la biblioteca **firecrawl4j** proporcionada. La aplicación debe incluir funcionalidad CRUD completa y búsqueda de texto.

## 🎯 Funcionalidades a Implementar

### Menu Principal
La aplicación debe mostrar un menú con las siguientes opciones:

```
=== Web2Markdown - Ejercicio CRUD ===
1) Crear/Actualizar desde URL
2) Leer (mostrar) por URL  
3) Listar todos
4) Borrar por URL
5) Búsqueda por palabra (en título/markdown)
0) Salir
```

### 1. Crear/Actualizar desde URL
- Pedir al usuario una URL
- Usar `FirecrawlWrapper.fetchMarkdown(url)` para obtener el contenido
- Usar `FirecrawlWrapper.guessTitle(markdown, url)` para generar un título
- Crear un `PageDoc` y almacenarlo en el HashMap
- Manejar excepciones apropiadamente

### 2. Leer (mostrar) por URL
- Pedir URL al usuario
- Buscar el documento en el HashMap
- Mostrar: título, número de palabras, fecha de obtención, preview del markdown (primeros 500 caracteres)

### 3. Listar todos
- Mostrar todos los documentos en formato tabla
- Usar `TablePrinter.print()` para el formato
- Columnas: URL, Título, Número de palabras

### 4. Borrar por URL
- Pedir URL al usuario
- Eliminar del HashMap
- Mostrar mensaje de confirmación

### 5. Búsqueda por palabra
- Pedir palabra clave al usuario  
- Buscar en título y contenido (case-insensitive)
- Mostrar resultados encontrados


## 📚 Clases de la Biblioteca Disponibles

### `FirecrawlWrapper`
```java

// Crear cliente Firecrawl (usa la API key de la variable de entorno FIRECRAWL_API_KEY)
FirecrawlClient client = FirecrawlClientFactory.createDefault();

// Crear instancia de FirecrawlWrapper (clase envoltorio que simplifica el uso del cliente para el ejercicio)
FirecrawlWrapper wrapper = new FirecrawlWrapper(client);

// Obtener markdown de una URL
String markdown = wrapper.fetchMarkdown("https://example.com");

// Generar título a partir del contenido
String title = wrapper.guessTitle(markdown, "https://example.com");
```

### `PageDoc`
```java
// Crear documento
PageDoc doc = new PageDoc(url, title, markdown);

// Métodos disponibles
doc.getUrl();
doc.getTitle();  
doc.getMarkdown();
doc.getFetchedAt();
doc.wordCount();
```

## ⚙️ Configuración previa
### 1. Obtener API Key
Regístrate en [firecrawl.dev](https://www.firecrawl.dev/) y obtén tu API key.

### 2. Configurar Variable de Entorno
**Windows:**
```cmd
set FIRECRAWL_API_KEY=fc-xxxxxxxx
```

**Linux/Mac:**
```bash
export FIRECRAWL_API_KEY=fc-xxxxxxxx
```

## 🧪 URLs de Prueba

- **Página web**: `https://example.com`
- **Documentación**: `https://firecrawl.dev` 
- **PDF público**: Cualquier URL que termine en `.pdf`

## ✅ Criterios de Evaluación

1. **Funcionalidad completa**: Todos los métodos implementados correctamente
2. **Manejo de errores**: Excepciones capturadas con mensajes informativos
3. **Búsqueda**: Implementación case-insensitive correcta
4. **Interfaz**: Menú claro y fácil de usar
5. **Código**: Limpio, comentado y bien estructurado

## 💡 Consejos

- Implementa los métodos de uno en uno y pruébalos
- Maneja siempre las excepciones con try-catch
- Para la búsqueda, usa `String.toLowerCase()` y `contains()`
- Recuerda que `HashMap.put()` actualiza si la clave ya existe

¡Buena suerte! 🚀