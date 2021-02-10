import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

public class Customer implements Runnable{

	private AtomicInteger spaces;
	private Semaphore bavailable;
	private Semaphore cavailable;
	private Random ran = new Random();


	public Customer(AtomicInteger spaces, Semaphore bavailable, Semaphore cavailable){
		this.spaces = spaces; 
		this.bavailable = bavailable;
		this.cavailable = cavailable;
	}

	@Override
	public void run(){
			
			try {
				cavailable.release(); // Ocupa uno de los asientos
				if(bavailable.hasQueuedThreads()){ // Comprueba que los barberos no están ocupados
					// y esta porción se ejecuta si lo están
					spaces.decrementAndGet(); // Los asientos libres se decrementan en 1
					System.out.println("Cliente en espera");
					bavailable.acquire(); // Le atiende el barbero
					spaces.incrementAndGet(); // Los asientos libres se incrementan en 1
				}
				else{
					bavailable.acquire(); // Le atiende el barbero sin necesidad de esperar
				}

			} catch (InterruptedException e){}
	}
}