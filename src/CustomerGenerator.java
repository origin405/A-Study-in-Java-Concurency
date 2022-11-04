import java.util.Date;
import java.util.concurrent.TimeUnit;

class CustomerGenerator implements Runnable{

		Serve shop = new Serve();
		private boolean closingTime = false;
		
		public CustomerGenerator(Serve shop) {
			this.shop = shop;
		}
		
		public void run() {
			while(!closingTime) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Customer customer = new Customer(shop);
				customer.setEntryTime(new Date());
				Thread thcustomer = new Thread(customer);
				customer.setName("Customer "+thcustomer.getId());
				thcustomer.start();
				
				try {
					TimeUnit.SECONDS.sleep((long)(Math.random()*2));
				}
				catch(InterruptedException iex){
					iex.printStackTrace();
				}
			}
	
			System.out.println("-------Closed, finishing remaining orders-------");
				
		} 
			
		public synchronized void setClosingTime() {
			closingTime = true;
			System.out.println("Closing? Stop Generating Customers!");
		}
		
}
		
