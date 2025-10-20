package lectoresuy.biblioteca.excepciones;

public class PrestamoRepetidoExcepcion extends RuntimeException {
    public PrestamoRepetidoExcepcion(String mensaje) {
        super(mensaje);
    }
}
