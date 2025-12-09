import es.usal.genai.*;
import es.usal.genai.examples.*;

import com.google.genai.types.Schema;

import java.nio.file.Path;
import java.util.List;

/**
 * 🚀 DEMOSTRACIÓN COMPLETA DEL GENAI FAT-JAR
 * 
 * Este archivo contiene TODOS los ejemplos disponibles en la biblioteca GenAI Fat-JAR.
 * Puedes copiarlo a tu proyecto y ejecutarlo directamente.
 * 
 * Requisitos:
 * 1. Configurar variable de entorno GOOGLE_API_KEY=tu-api-key-aqui
 * Revisa como establecer variables de entorno en tu sistema operativo.
 * 2. Descargar el JAR incluido en este repositorio.
 * 
 * Incluye los 6 casos de uso principales:
 * 1. Generación de texto simple
 * 2. Análisis multimodal (texto + archivos)
 * 3. Objetos Java estructurados (SimpleSchemas)
 * 4. Objetos anidados complejos
 * 5. Análisis de archivos con metadatos
 * 6. Control de tokens gastados
 */
public class App {
    
    public static void main(String[] args) {

        System.out.println("🚀 GENAI FAT-JAR - DEMOSTRACIÓN COMPLETA");
        System.out.println("═".repeat(70));
        System.out.println("Esta demostración incluye TODOS los casos de uso disponibles");
        System.out.println();

        // OPCIONES PARA CREAR EL CLIENTE GENAI

        // Opción A: Usar API Key directamente (no recomendado para producción)
        // Nunca dejar la API Key hardcodeada en el código fuente en proyectos reales
        String apiKey = "tu-api-key-aqui"; // Reemplaza con tu API Key real
        GenAiConfig config = GenAiConfig.forGemini("gemini-2.5-flash", apiKey);

        // Opción B: Usar variable de entorno 

        // Debemos tener la variable de entorno GOOGLE_API_KEY configurada
        // Ejemplo PS: setx GOOGLE_API_KEY "tu-api-key-aqui"
        // Ejemplo CMD: env GOOGLE_API_KEY="tu-api-key-aqui"G
        // Ejemplo Linux/Mac: export GOOGLE_API_KEY="tu-api-key-aqui"

        // Obtener la API Key --> https://aistudio.google.com/app/apikey

        // Leer desde java la variable de entorno y mostrarla
        // String apiKey = System.getenv("GOOGLE_API_KEY");
        // GenAiConfig config = GenAiConfig.fromEnv("gemini-2.0-flash");
        // System.out.println("API Key: " + (apiKey != null ? "Configurada" : "No configurada"));
        
        // Configurar logging (opcional - descomenta lo que prefieras)
        GenAiConfig.setSilentMode();        // Sin logs (recomendado)
        // GenAiConfig.setDevelopmentMode();   // Solo warnings/errores
        
        try {
            // Configuración usando variable de entorno
            System.out.println("✅ Configuración cargada - Modelo: " + config.model());
            
            try (GenAiFacade genai = new GenAiFacade(config)) {
                
                // ===== EJEMPLO 1: TEXTO SIMPLE =====
                ejemplo1_TextoSimple(genai);
                
                // ===== EJEMPLO 2: ANÁLISIS MULTIMODAL =====
                ejemplo2_AnalisisMultimodal(genai);
                
                // ===== EJEMPLO 3: OBJETOS ESTRUCTURADOS =====
                ejemplo3_ObjetosEstructurados(genai);
                
                // ===== EJEMPLO 4: OBJETOS ANIDADOS COMPLEJOS =====
                ejemplo4_ObjetosAnidados(genai);
                
                // ===== EJEMPLO 5: ANÁLISIS DE ARCHIVOS =====
                ejemplo5_AnalisisArchivos(genai);
                
                // ===== EJEMPLO 6: CONTEO DE TOKENS =====
                ejemplo6_ConteoTokens(genai);
                
                System.out.println("\n🎉 ¡TODOS LOS EJEMPLOS COMPLETADOS EXITOSAMENTE!");
                System.out.println("═".repeat(70));
                System.out.println("✨ Ya puedes usar estas técnicas en tus propios proyectos");
                
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error durante la ejecución:");
            System.err.println("   " + e.getMessage());
            System.err.println();
            System.err.println("💡 Soluciones comunes:");
            System.err.println("   • Configura GOOGLE_API_KEY: setx GOOGLE_API_KEY \"tu-api-key\"");
            System.err.println("   • Reinicia el terminal después de configurar la variable");
            System.err.println("   • Verifica tu conexión a internet");
            e.printStackTrace();
        }
    }
    
    // ═══════════════════════════════════════════════════════════════
    // EJEMPLO 1: GENERACIÓN DE TEXTO SIMPLE
    // ═══════════════════════════════════════════════════════════════
    
    private static void ejemplo1_TextoSimple(GenAiFacade genai) {
        System.out.println("\n📝 EJEMPLO 1: Generación de Texto Simple");
        System.out.println("─".repeat(50));
        System.out.println("Prompt → Respuesta en texto natural");
        
        String prompt = "Explica en 3 puntos qué es la Programación Orientada a Objetos en Java";
        String respuesta = genai.generateText(prompt);
        
        System.out.println("Pregunta: " + prompt);
        System.out.println("\n🤖 Respuesta:");
        System.out.println(respuesta);
    }
    
    // ═══════════════════════════════════════════════════════════════
    // EJEMPLO 2: ANÁLISIS MULTIMODAL (TEXTO + ARCHIVOS)
    // ═══════════════════════════════════════════════════════════════
    
    private static void ejemplo2_AnalisisMultimodal(GenAiFacade genai) {
        System.out.println("\n🖼️ EJEMPLO 2: Análisis Multimodal");
        System.out.println("─".repeat(50));
        System.out.println("Prompt + Archivos → Análisis completo");
        
        // Lista de archivos a analizar
        List<Path> archivos = List.of(
            Path.of("resources/java.png")
        );
        
        String prompt = "Analiza estos archivos y proporciona un resumen detallado del contenido";
        String resultado = genai.generateWithFiles(prompt, archivos);
        
        System.out.println("Análisis de archivos:");
        System.out.println("▼".repeat(30));
        System.out.println(resultado);
        System.out.println("▲".repeat(30));
    }
    
    // ═══════════════════════════════════════════════════════════════
    // EJEMPLO 3: OBJETOS JAVA ESTRUCTURADOS (SIMPLESCHEMAS)
    // ═══════════════════════════════════════════════════════════════
    
    private static void ejemplo3_ObjetosEstructurados(GenAiFacade genai) {
        System.out.println("\n🎯 EJEMPLO 3: Objetos Java Estructurados");
        System.out.println("─".repeat(50));
        System.out.println("Prompt → Objeto Java (usando SimpleSchemas)");
        
        // 3A: Usando MovieInfo (clase predefinida)
        ejemplo3a_MovieInfo(genai);
        
        // 3B: Usando PersonaBasica (clase de ejemplo)
        ejemplo3b_PersonaBasica(genai);
        
        // 3C: Usando Receta (clase compleja)
        ejemplo3c_Receta(genai);
    }
    
    private static void ejemplo3a_MovieInfo(GenAiFacade genai) {
        System.out.println("\n🎬 Sub-ejemplo 3A: Información de Películas");
        
        String prompt = "The Matrix (1999), ciencia ficción cyberpunk dirigida por las Wachowski";
        
        // ¡Una sola línea para crear el esquema!
        Schema schema = SimpleSchemas.from(MovieInfo.class);
        
        MovieInfo pelicula = genai.generateJson(prompt, schema, MovieInfo.class);
        
        System.out.println("Información extraída:");
        System.out.println("• Título: " + pelicula.title);
        System.out.println("• Año: " + pelicula.year);
        System.out.println("• Géneros: " + pelicula.genres);
        System.out.println("• Director: " + pelicula.director);
        System.out.println("• Rating: " + pelicula.opcionalRating);
    }
    
    private static void ejemplo3b_PersonaBasica(GenAiFacade genai) {
        System.out.println("\n👤 Sub-ejemplo 3B: Información de Personas");
        
        String prompt = "Albert Einstein, físico alemán nacido en 1879, conocido por la teoría de la relatividad";
        
        Schema schema = SimpleSchemas.from(PersonaBasica.class);
        
        PersonaBasica persona = genai.generateJson(prompt, schema, PersonaBasica.class);
        
        System.out.println("Información extraída:");
        System.out.println("• Nombre: " + persona.nombre);
        System.out.println("• Edad: " + persona.edad);
        System.out.println("• Profesión: " + persona.profesion);
    }
    
    private static void ejemplo3c_Receta(GenAiFacade genai) {
        System.out.println("\n🍽️ Sub-ejemplo 3C: Recetas de Cocina");
        
        String prompt = "Tortilla española: huevos, patatas, aceite, sal. Freír patatas, batir huevos, mezclar, cocinar en sartén";
        
        Schema schema = SimpleSchemas.from(Receta.class);
        
        Receta receta = genai.generateJson(prompt, schema, Receta.class);
        
        System.out.println("Receta extraída:");
        System.out.println("• Nombre: " + receta.nombre);
        System.out.println("• Ingredientes: " + receta.ingredientes);
        System.out.println("• Pasos: " + receta.pasos);
        System.out.println("• Dificultad: " + receta.dificultad);
    }
    
    // ═══════════════════════════════════════════════════════════════
    // EJEMPLO 4: OBJETOS ANIDADOS COMPLEJOS
    // ═══════════════════════════════════════════════════════════════
    
    private static void ejemplo4_ObjetosAnidados(GenAiFacade genai) {
        System.out.println("\n📚 EJEMPLO 4: Objetos Anidados Complejos");
        System.out.println("─".repeat(50));
        System.out.println("Prompt → Objeto con sub-objetos anidados automáticos");
        
        String prompt = "El Quijote por Cervantes, publicado por Cátedra en España, " +
                       "tiene excelentes reseñas: María dice 'obra maestra' (5 estrellas), " +
                       "Juan comenta 'lectura imprescindible' (4 estrellas)";
        
        // SimpleSchemas detecta automáticamente Editorial y List<Reseña>
        Schema schema = SimpleSchemas.from(Libro.class);
        
        Libro libro = genai.generateJson(prompt, schema, Libro.class);
        
        System.out.println("📖 Libro extraído:");
        System.out.println("• Título: " + libro.titulo);
        System.out.println("• Autor: " + libro.autor);
        System.out.println("• Año: " + libro.año);
        
        System.out.println("\n🏢 Editorial (objeto anidado):");
        if (libro.editorial != null) {
            System.out.println("• Nombre: " + libro.editorial.nombre);
            System.out.println("• País: " + libro.editorial.pais);
        }
        
        System.out.println("\n⭐ Reseñas (lista de objetos):");
        if (libro.reseñas != null) {
            for (int i = 0; i < libro.reseñas.size(); i++) {
                Libro.Reseña reseña = libro.reseñas.get(i);
                System.out.println("• Reseña " + (i+1) + ": " + reseña.autor + 
                                 " (" + reseña.puntuacion + "★) - " + reseña.comentario);
            }
        }
        
        System.out.println("\n💡 SimpleSchemas detectó automáticamente:");
        System.out.println("   ✓ Objeto Editorial anidado");
        System.out.println("   ✓ Lista de objetos Reseña");
        System.out.println("   ✓ Esquemas recursivos sin configuración");
    }
    
    // ═══════════════════════════════════════════════════════════════
    // EJEMPLO 5: ANÁLISIS DE ARCHIVOS CON METADATOS
    // ═══════════════════════════════════════════════════════════════
    
    private static void ejemplo5_AnalisisArchivos(GenAiFacade genai) {
        System.out.println("\n📁 EJEMPLO 5: Análisis de Archivos con Metadatos");
        System.out.println("─".repeat(50));
        System.out.println("Archivos → Metadatos estructurados automáticos");
        
        // Simulamos análisis de archivo porque no tenemos archivos reales
        String prompt = "Analiza un documento PDF académico adjunto y extrae metadatos clave: " +
                                "nombre del archivo, tipo, descripción del contenido, palabras clave, calidad, " +
                                "y detalles técnicos como formato, resolución, número de páginas y duración si aplica.";
        
        Schema schema = SimpleSchemas.from(FileMetadata.class);
        // Archivos
        List<Path> archivos = List.of(
            Path.of("resources/attention_is_all_you_need.pdf")
        );
        
        FileMetadata metadata = genai.generateJson(prompt, archivos, schema, FileMetadata.class);

        System.out.println("📋 Metadatos extraídos:");
        System.out.println("• Nombre: " + metadata.nombreArchivo);
        System.out.println("• Tipo: " + metadata.tipoArchivo);
        System.out.println("• Descripción: " + metadata.descripcionContenido);
        System.out.println("• Palabras clave: " + metadata.palabrasClave);
        System.out.println("• Calidad: " + metadata.opcionalCalidad);
        
        System.out.println("\n🔧 Información técnica (objeto anidado):");
        if (metadata.infoTecnica != null) {
            System.out.println("• Formato: " + metadata.infoTecnica.formato);
            System.out.println("• Resolución: " + metadata.infoTecnica.opcionalResolucion);
            System.out.println("• Páginas: " + metadata.infoTecnica.opcionalNumeroPaginas);
            System.out.println("• Duración: " + metadata.infoTecnica.opcionalDuracion);
        }
        
    }
    
    // ═══════════════════════════════════════════════════════════════
    // EJEMPLO 6: CONTEO DE TOKENS
    // ═══════════════════════════════════════════════════════════════
    
    private static void ejemplo6_ConteoTokens(GenAiFacade genai) {
        System.out.println("\n🔢 EJEMPLO 6: Conteo de Tokens");
        System.out.println("─".repeat(50));
        System.out.println("Útil para calcular costes y límites de API");
        
        String[] textosPrueba = {
            "Hola mundo",
            "La inteligencia artificial está transformando el mundo de la programación",
            "En un lugar de la Mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero, adarga antigua, rocín flaco y galgo corredor."
        };
        
        System.out.println("Análisis de tokens para diferentes textos:");
        
        for (int i = 0; i < textosPrueba.length; i++) {
            String texto = textosPrueba[i];
            int tokens = genai.countTokens(texto);
            double ratio = (double) texto.length() / tokens;
            
            System.out.println("\n" + (i+1) + ". \"" + 
                             (texto.length() > 50 ? texto.substring(0, 50) + "..." : texto) + "\"");
            System.out.println("   • Caracteres: " + texto.length());
            System.out.println("   • Tokens: " + tokens);
            System.out.println("   • Ratio: " + String.format("%.2f", ratio) + " chars/token");
        }
    }
    
    // ═══════════════════════════════════════════════════════════════
    // CLASES DE EJEMPLO PERSONALIZADAS (para copiar a tu proyecto)
    // ═══════════════════════════════════════════════════════════════
    
    /**
     * Ejemplo de clase personalizada para tus proyectos.
     * Solo necesitas campos públicos y SimpleSchemas hace el resto.
     */
    public static class MiClasePersonalizada {
        public String nombre;
        public int numero;
        public List<String> lista;
        public boolean activo;
        public String opcionalComentario;  // Los campos "opcional*" son opcionales
    }
    
    /**
     * Ejemplo de clase con objetos anidados.
     * SimpleSchemas maneja la complejidad automáticamente.
     */
    public static class ProyectoSoftware {
        public String nombre;
        public String lenguaje;
        public Desarrollador lider;           // ← Objeto anidado
        public List<Desarrollador> equipo;    // ← Lista de objetos
        public List<String> tecnologias;      // ← Lista simple
        
        public static class Desarrollador {
            public String nombre;
            public String rol;
            public int experienciaAños;
        }
    }
}