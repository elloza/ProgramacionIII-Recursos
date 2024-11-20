# ProgramacionIII - Recursos
En este repositorio se ofrecen recursos para las prácticas de la asignatura Programación del Grado Ingeniería Informática USAL.

Se incluyen bibliotecas y ejemplos de código como apoyo para los ejercicios de la asignatura.

# Bibliotecas (JARs)

Ya que en la asignatura se trabaja sin build tools (Maven, Gradle, etc.), se ofrecen las siguientes bibliotecas en formato JAR para su uso en los ejercicios.

## Biblioteca.jar

Biblioteca con clases de utilidad para la E/S, trabajo con Arrays de tipos primitivos, ficheros y rutas, etc.

Autor: Profesor José R. García-Bermejo Giner

Sitio web: http://maxus.fis.usal.es/HOTHOUSE/p3/index.html

[Versión java 11 Descarga](http://maxus.fis.usal.es/HOTHOUSE/p3/biblioteca11.jar)

Documentación: [Javadoc](http://maxus.fis.usal.es/HOTHOUSE/p3/javadoc_com_coti_tools/com/coti/tools/package-summary.html)

Clases interesantes para los ejercicios: Esdia, Rutas, OpMat y DiaUtil.

## YoutubeMetadata.jar

Esta es una biblioteca que es simplemente un wrapper de la siguiente biblioteca y de todas sus dependencias:

[sealedtx/java-youtube-downloader](https://github.com/sealedtx/java-youtube-downloader)

Se ha incluido toda la biblioteca en un solo JAR para facilitar su uso en los ejercicios. Se trata de una biblioteca para usos educativos, que permite obtener metadatos de vídeos de YouTube. No se debe emplear para usos que vayan en contra de los [Terms of Service - II. Prohibitions](https://developers.google.com/youtube/terms/api-services-terms-of-service) de Youtube. Desde la asignatura se recomienda el uso de esta biblioteca para obtener metadatos de vídeos de YouTube con fines educativos.

Descarga: [YoutubeMetadata.jar](https://github.com/elloza/ProgramacionIII-Recursos/raw/main/jars/YoutubeMetadata.jar)

**Ejemplo de uso**:

```java
import com.elloza.youtubemetadata.VideoInfoExtractor;
import com.github.kiulian.downloader.model.videos.VideoInfo;

public class App {
    public static void main(String[] args) throws Exception {
        // URL del vídeo de YouTube
        String url = "https://www.youtube.com/watch?v=6iRLDa0LQNM";
        try {
            VideoInfo videoInfo = VideoInfoExtractor.getVideoInfoFromUrl(url);
            System.out.println("Title: " + videoInfo.details().title());
            System.out.println("Views: " + videoInfo.details().viewCount());
            System.out.println("Author: " + videoInfo.details().author());
            System.out.println("Keywords: " + videoInfo.details().keywords());
            System.out.println("Description: " + videoInfo.details().description());
            System.out.println("Duration: " + videoInfo.details().lengthSeconds() + " seconds");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## Notion SDK Java

Biblioteca para acceder a la API de Notion desde Java. Se ha incluido toda la biblioteca en un solo JAR para facilitar su uso en los ejercicios.

Biblioteca Original: [Notion SDK](https://github.com/seratch/notion-sdk-jvm/tree/main)

Revise el proyecto [notion-sdk-example](https://github.com/elloza/ProgramacionIII-Recursos/tree/main/notionsdkexample) de este repositorio para ver un ejemplo de uso y todos los requisitos necesarios para poder utilizar la API de Notion.

Descarga: [notion-sdk-jvm-core-1.11.1.jar](https://github.com/elloza/ProgramacionIII-Recursos/raw/main/jars/notion-sdk-jvm-core-1.11.1.jar)

Este jar se ha generado con fines educativos y para facilitar el uso de la API de Notion en los ejercicios de la asignatura. En un proyecto real, se recomienda añadir la dependencia a través de Maven o Gradle del repositorio original.

# Otros recursos

## Java Source Code 2 PlantUML

Utilidad para generar diagramas de clases PlantUML (puml) a partir de código fuente Java.

Los creadores de la utilidad y el proyecto original se encuentran en:

[feitosa-daniel/java2umltext](https://github.com/sealedtx/java-youtube-downloader)

Dejad una estrella a su repositorio si os ha sido útil.

Descarga del JAR: [java2umltext-0.1.0.jar](https://github.com/feitosa-daniel/java2umltext/releases/download/v0.1.0/java2umltext-0.1.0.jar)

Ejemplo de uso por línea de comandos:

```bash
java -jar java2umltext-0.1.0.jar -m private -m public -m protected -m default -f private -f public -f protected -f default --package -o="./output.puml" PLANTUML "./Ejemplo/src/"
```

Siendo `./Ejemplo/src/` la ruta al directorio que contiene el código fuente Java.
Siendo `./output.puml` la ruta al fichero de salida con el codigo PlantUML del diagrama de clases.

Para ver más información sobre los parámetros utilizados consultad la documentación [aquí](https://github.com/feitosa-daniel/java2umltext?tab=readme-ov-file#usage)

El código generado puede ser pegado en el [Editor Online de PlantUML](https://www.plantuml.com/plantuml/uml/) para visualizar el diagrama de clases.

