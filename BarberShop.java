import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.*;


class BarberShop{
    public static void main(String[] args){

        AtomicInteger spaces = new AtomicInteger(15);
        
        final Semaphore barbers = new Semaphore(3, true);
        final Semaphore customers = new Semaphore(0, true);  
        ExecutorService openUp = Executors.newFixedThreadPool(3);

        Barber[]  employees = new Barber[3];
        
        System.out.println("Abriendo la tienda");
        for(int i = 0; i < 3; i++) {
            employees[i]= new Barber(spaces, barbers, customers);
            openUp.execute(employees[i]); // Ejecuta los 3 barberos en sus respectivos hilos
        }

        while(true)
        {
            try { 
                Thread.sleep(ThreadLocalRandom.current().nextInt(100, 1000 + 100)); // Esperar hasta
                // que entre el proximo cliente
            }catch (InterruptedException e){}
            
            System.out.println("Entra un cliente");
            
            if(spaces.get() >= 0){
                new Thread(new Customer(spaces, barbers, customers)).start(); // Los clientes siguen entrando
                // mientras hay asientos libres
            }
            else{
                System.out.println("El cliente se va, al no haber asientos libres");
            }
        }
    }
}
