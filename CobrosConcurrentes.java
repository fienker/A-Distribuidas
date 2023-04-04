import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CobrosConcurrentes {
    private static int saldo = 2000;
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        // Crear tres hilos de ejecución para realizar los cobros.
        Thread t1 = new Thread(new Cobro(550));
        Thread t2 = new Thread(new Cobro(150));
        Thread t3 = new Thread(new Cobro(600));

        // Iniciar los hilos de ejecución.
        t1.start();
        t2.start();
        t3.start();

        // Esperar a que los hilos terminen.
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Cobro implements Runnable {
        private int cantidad;

        public Cobro(int cantidad) {
            this.cantidad = cantidad;
        }

        public void run() {
            lock.lock();
            try {
                if (saldo >= cantidad) {
                    saldo -= cantidad;
                    System.out.println("Cobrado " + cantidad + " soles. Saldo actual: " + saldo + " soles.");
                } else {
                    System.out.println("No se pudo cobrar " + cantidad + " soles. Saldo insuficiente.");
                }
            } finally {
                lock.unlock();
            }
        }
    }
}