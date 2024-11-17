import java.util.List;


/**
 * Esta clase es un ejemplo de uso de la clase NotionRepository para interactuar
 * con la API de Notion.
 * 
 * Se crean 10 registros de ejemplo, se actualizan, se obtienen todos los
 * registros y se eliminan.
 * 
 * Utilice puntos de interrupción para depurar el código y sus propias credenciales
 * de la API de Notion.
 * 
 * API de Notion: https://developers.notion.com/
 * 
 */
public class Main {
    public static void main(String[] argas) {

        // CAMBIE ESTOS VALORES POR SUS PROPIAS CREDENCIALES DE LA API DE NOTION
        String apiToken = "API_TOKEN";
        String databaseId = "DATABASE_ID";

        // Crear repositorio con cliente HTTP personalizado
        NotionRepository repository = new NotionRepository(apiToken, databaseId);

        // Crear 10 registros de ejemplo
        for (int i = 0; i < 10; i++) {
            Persona persona = new Persona();
            persona.setIdentifier(String.valueOf(i));
            persona.setNombre("Persona " + i);
            persona.setApellidos("Apellido " + i);
            persona.setEdad(20 + i);
            // Año de nacimiento en formato ISO 8601
            persona.setAnoNacimiento(2000+i+"-01-01");
            persona.setCarnetDeConducir(i % 2 == 0);
            repository.createRecord(persona);
        }

        // Obtener todos los registros y mostrarlos
        List<Persona> personas = repository.getAllRecords();
         // Cabecera
         System.out.println(Persona.getTableHeader());
        for (Persona p : personas) {
            System.out.println(p);
        }

        // Actualizar los registros anteriores por identificador
        for (Persona p : personas) {
            p.setNombre("Nombre actualizado");
            p.setApellidos("Apellidos actualizados");
            repository.updateRecordByIdentifier(p);
        }

        // Obtener todos los registros y mostrarlos
        personas = repository.getAllRecords();
         // Cabecera
         System.out.println(Persona.getTableHeader());
        for (Persona p : personas) {
            System.out.println(p);
        }

        // Eliminar todos los registros por identificador
        for (Persona p : personas) {
            repository.deleteRecordByIdentifier(p);
        }

    }
}
