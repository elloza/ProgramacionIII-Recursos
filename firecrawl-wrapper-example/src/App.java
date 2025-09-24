import dev.firecrawl.client.FirecrawlClient;
import es.usal.domain.PageDoc;
import es.usal.infra.FirecrawlClientFactory;
import es.usal.infra.FirecrawlWrapper;

public class App {
    public static void main(String[] args) throws Exception {

        // Crear cliente Firecrawl (usa la API key de la variable de entorno FIRECRAWL_API_KEY)
        // Deberías haber configurado esta variable en tu entorno en tu terminal
        // Bash:
        // export FIRECRAWL_API_KEY=fc-xxxxxxxx
        // Windows CMD:
        // set FIRECRAWL_API_KEY=fc-xxxxxxxx
        
        FirecrawlClient client = FirecrawlClientFactory.createDefault();

        // Crear instancia de FirecrawlWrapper (clase envoltorio que simplifica el uso
        // del cliente para el ejercicio)
        FirecrawlWrapper wrapper = new FirecrawlWrapper(client);

        // Ejemplo de uso con www.usal.es
        String url = "https://www.usal.es/";

        try {
            // Contenido en markdown
            String markdown = wrapper.fetchMarkdown(url);
            // Título inferido de la página
            String title = wrapper.guessTitle(markdown, url);

            // Clase PageDoc para manejar el documento (Puedes crear tu propia clase similar
            // si lo prefieres)
            PageDoc doc = new PageDoc(url, title, markdown);
            int words = doc.wordCount();
            System.out.println("URL: " + url);
            System.out.println("Título: " + title);
            System.out.println("Número de palabras: " + words);

            System.out.println("Contenido en formato Markdown:");
            System.out.println(markdown);
        } catch (Exception e) {
            // Manejo de errores
            System.err.println("Error al obtener la URL: " + e.getMessage());
            return;
        }

    }
}
