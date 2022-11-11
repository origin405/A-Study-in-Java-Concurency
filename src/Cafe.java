
public class Cafe {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Java Cafe Event Log");
		System.out.println("--------------------------------------------------");
		Serve shop = new Serve();
		Server owner = new Server("Owner", shop);
		Server waiter = new Server("Waiter", shop);
		
		CustomerGenerator cg = new CustomerGenerator(shop);
		
		Thread thcg = new Thread(cg);
		Thread ownerT = new Thread(owner);
		Thread waiterT = new Thread(waiter);
		
		waiterT.start();
		ownerT.start();
		thcg.start();
		
		Clock clock = new Clock(cg, owner, waiter);
		clock.run();
		
	}
	
	
	
}
