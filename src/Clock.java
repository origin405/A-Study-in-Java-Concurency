public class Clock {
	private CustomerGenerator cg;
	private Server owner;
	private Server waiter;
	public Clock(CustomerGenerator cg, Server owner, Server waiter) {
		this.cg = cg;
		this.owner = owner;
		this.waiter = waiter;
	}
	public void run() {
		try {
			Thread.sleep(20000);
			notifyClosed();
			
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	public void notifyClosed() {
		System.out.println('\n' + "Clock: It's closing time. QUEUE UP NOW FOR LAST CALL!");
		cg.setClosingTime();
		owner.setclosingTime();
		waiter.setclosingTime();
		
	}
	
}
