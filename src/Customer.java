import java.util.Date;

class Customer implements Runnable {
	String name;
	Date entryTime;
	Serve shop;
	String beverage;
	volatile boolean order = false;
	boolean leave = false;
	
	public Customer(Serve shop) {
		this.shop = shop;
		
	}
	
	public String getBeverage() {
		return beverage;
	}
	public boolean getLeave() {
		return leave;
	}
	public String getName() {
		return name;
	}
	
	public Date getEntryTime() {
		return entryTime;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	public void setOrderTrue() {
		this.order = true;
	}
	public void setLeaveTrue() {
		this.leave = true;
	}
	public void setBeverage() {
		this.beverage = glassOrCup();
	}
	
	
	public void run() {
		try {
			setBeverage();
			go_queue();
			while(!order) {
				Thread.sleep(50);
			}
			Thread.sleep(1000); //random
			setLeaveTrue();
			System.out.println(this.name + " has finished drinking. Customer's " + beverage + " is left on the table. Leaving now. Goodbye!");
			Thread.sleep(500);
			
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void go_queue() throws InterruptedException {
		
		shop.queue(this);
	}
	
	public String glassOrCup() {
		double x = Math.random();
		if (x>0.5) {
			return "glass"; //juice
		}
		else {
			return "cup";  //coffee
		}
	}
}
