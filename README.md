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
