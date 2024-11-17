public class Persona {
    private String identifier;
    private String nombre;
    private String apellidos;
    private int edad;
    private String anoNacimiento;
    private boolean carnetDeConducir;

    // Getters y setters
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String id) {
        this.identifier = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getAnoNacimiento() {
        return anoNacimiento;
    }

    public void setAnoNacimiento(String anoNacimiento) {
        this.anoNacimiento = anoNacimiento;
    }

    public boolean isCarnetDeConducir() {
        return carnetDeConducir;
    }

    public void setCarnetDeConducir(boolean carnetDeConducir) {
        this.carnetDeConducir = carnetDeConducir;
    }

    // Obtener una representación en cadena de la instancia como una tabla | %10.10s es para limitar el tamaño de la cadena a 10 caracteres
    @Override
    public String toString() {
        return String.format("| %10.10s | %10.10s | %10.10s | %6d | %15.15s | %10b |",
                identifier, nombre, apellidos, edad, anoNacimiento, carnetDeConducir);
    }

    // Obtener cabecera de la tabla
    public static String getTableHeader() {
        return String.format("| %10.10s | %10.10s | %10.10s | %6s | %15.15s | %10s |",
                "Identifier", "Nombre", "Apellidos", "Edad", "Año Nacimiento", "Carnet");
    }


}