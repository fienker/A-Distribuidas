import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CobrosParalelos {
    private static int saldo = 3500;

    public static void main(String[] args) {
        // Crear un pool de hilos para realizar los cobros.
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Realizar los cobros en paralelo.
        executor.submit(new Cobro(800));
        executor.submit(new Cobro(200));
        executor.submit(new Cobro(1200));

        // Cerrar el pool de hilos.
        executor.shutdown();
    }

    static class Cobro implements Runnable {
        private int cantidad;

        public Cobro(int cantidad) {
            this.cantidad = cantidad;
        }

        public void run() {
            synchronized (CobrosParalelos.class) {
                if (saldo >= cantidad) {
                    saldo -= cantidad;
                    System.out.println("Cobrado " + cantidad + " soles. Saldo actual: " + saldo + " soles.");
                } else {
                    System.out.println("No se pudo cobrar " + cantidad + " soles. Saldo insuficiente.");
                }
            }
        }
    }
}