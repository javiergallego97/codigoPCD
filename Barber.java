import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

public class Barber implements Runnable{

	private AtomicInteger spaces;
	private Semaphore bavailable;
	private Semaphore cavailable;
	private Random ran = new Random();


	public Barber(AtomicInteger spaces, Semaphore bavailable, Semaphore cavailable){
		this.spaces = spaces; 
		this.bavailable = bavailable;
		this.cavailable = cavailable;
	}

	@Override
	public void run(){
		while(true){
			try {
				cavailable.acquire(); // Libera uno de los asientos
				System.out.println("Cliente cortándose el pelo");
				Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 10000 + 1000));
				// El hilo se detiene una cantidad aleatoria de tiempo
				System.out.println("El cliente paga y se va");
				bavailable.release(); // El barbero vuelve a estar disponible

			} catch (InterruptedException e){}
		}
	}
}