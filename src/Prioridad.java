public enum Prioridad {
    ALTA(1),
    MEDIA(2),
    BAJA(3);
    private final int valor;
    Prioridad(int valor) {
        this.valor = valor;
    }
    public int getValor() {
        return valor;
    }
}
