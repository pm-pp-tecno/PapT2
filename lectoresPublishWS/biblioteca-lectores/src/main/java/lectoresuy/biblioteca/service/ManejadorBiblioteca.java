package lectoresuy.biblioteca.service;

public class ManejadorBiblioteca {

    private ManejadorLector manejadorLector;
    private ManejadorMaterial manejadorMaterial;
    private ManejadorPrestamo manejadorPrestamo;
    private ManejadorBibliotecario manejadorBibliotecario;

    public ManejadorBiblioteca() {
        //this.manejadorLector = new ManejadorLector();
        this.manejadorMaterial = ManejadorMaterial.getInstancia();
        this.manejadorPrestamo = ManejadorPrestamo.getInstancia();
        this.manejadorBibliotecario = ManejadorBibliotecario.getInstancia();
    }

    public ManejadorLector getManejadorLector() {
        return manejadorLector;
    }

    public ManejadorMaterial getManejadorMaterial() {
        return manejadorMaterial;
    }

    public ManejadorPrestamo getManejadorPrestamo() {
        return manejadorPrestamo;
    }

    public ManejadorBibliotecario getManejadorBibliotecario() {
        return manejadorBibliotecario;
    }
}