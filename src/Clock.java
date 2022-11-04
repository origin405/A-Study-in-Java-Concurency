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
			NotifyClosed();
			
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	public void NotifyClosed() {
		System.out.println("Clock: It's closing time bitches!");
		cg.setClosingTime();
		owner.setclosingTime();
		waiter.setclosingTime();
		
	}
	
}
