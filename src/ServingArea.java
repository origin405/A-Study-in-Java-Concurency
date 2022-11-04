import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
public class ServingArea {
	
//	Create glass and cup inventory
	private AtomicInteger glasses = new AtomicInteger(2);
	private AtomicInteger cups = new AtomicInteger(2);

//	Fill those inventory with respective items
	
	
	public boolean juice(Server server) throws InterruptedException {
		if (glasses.get()>0){
			Thread.sleep(500);
			System.out.println(server.name + " acquired a glass.");
			glasses.decrementAndGet();
			juiceTap(server);
			System.out.println(server.name + ": Juice is done making! Here's your juice dear customer!");
			return true;
		}
		else {
			System.out.println(server.name + ": There is no more glass!");
			return false;
		}
	}
	
	public boolean cappuchino(Server server) throws InterruptedException{
		if (cups.get()>0){
			Thread.sleep(500);
			System.out.println(server.name + " acquired a cup.");
			cups.decrementAndGet();
			coffee(server);
			milk(server);
			mix(server);
			System.out.println(server.name + ": Coffee is done making! Here's your coffee dear customer!");
			return true;
			
		}
		else {
			System.out.println(server.name + ": There is no more cups!");	
			return false;
		}
		
	}
	
	public void coffee(Server server) throws InterruptedException{
		Thread.sleep(500);
		System.out.println(server.name + ": Acquiring coffee!");
	}
	
	public void milk(Server server) throws InterruptedException{
		Thread.sleep(500);
		System.out.println(server.name + ": Acquiring milk!");
	}
	
	public void mix(Server server) throws InterruptedException{
		Thread.sleep(500);
		System.out.println(server.name + ": Mixing coffee and milk!");
	}
	
	public void juiceTap(Server server) throws InterruptedException{
		Thread.sleep(500);
		System.out.println(server.name + ": Pouring juice from Juice Tap!");
	}
	
	public void addCup() {
		cups.incrementAndGet();
	}
	
	public void addGlass() {
		glasses.incrementAndGet();
	}
	
}



