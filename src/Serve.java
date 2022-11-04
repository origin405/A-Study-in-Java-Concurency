import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.Math;

public class Serve {
	BlockingQueue<Customer> queue_block = new LinkedBlockingDeque<Customer>();
	ServingArea workStation = new ServingArea();
	ArrayBlockingQueue<Customer> table= new ArrayBlockingQueue<Customer>(10); 
	double impatience = 0.25; //impatience = 0.2 (99% leave probability for customer 22 in queue) patience = 1 (99% for customer 5 in queue)
	ReentrantLock orderLock = new ReentrantLock();
	Serve(int patience){
		this.impatience = patience;
	}
	
	Serve(){
		
	}
	
	// Order Queue
	public void queue(Customer customer) {
		double leaveProb = prob(queue_block.size());
		double queueUpProb = Math.random(); //uniform distribution between 0 - 1
		if (leaveProb < queueUpProb) {
			synchronized(queue_block) {
				try {
					
					queue_block.put(customer);
					System.out.println(customer.getName() + " visits cafe. Queue: " + queue_block.size() );	
					if (queue_block.size()==1) {
						queue_block.notify();
					}
					
								
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
			
			
					
		}
				
		else {
			System.out.println(customer.getName() + " visits cafe. " + "Customer refuse to wait and left!");
		}    
				
	}
		
//	}
	
	/* Probability function of customer leaving as queue block_size increase. Probability increases slowly at first, 
       then increases exponentially, and finally slowly approaching but never reaching 1 */
	public double prob(int block_size) {
		return Math.exp(-5 * Math.exp(-impatience * block_size));
	}
	
	
	public void order(Server server) throws InterruptedException{
		synchronized(queue_block) {
			while(queue_block.size()==0)
	        {
	            System.out.println(server.name + " is waiting for customer.");
	            System.out.println("No customer currently in queue!");
	            try
	            {
	            	queue_block.wait();
	            }
	            catch(InterruptedException iex)
	            {
	                iex.printStackTrace();
	            }
	        }
			
			System.out.println("There is a customer!");	
			Customer customer;
			customer = queue_block.poll();
			System.out.println(server.name + ": What would you like to order?");
			
			if (customer.getBeverage()=="glass") { 
				Thread.sleep(500);
				System.out.println(customer.getName() + ": Juice please.");
				System.out.println(server.name + ": making Juice now!");
				boolean temp = workStation.juice(server);	
				if (!temp) {
					cleanTable(server);
					workStation.juice(server);
				}
				
			}

			else if (customer.getBeverage()=="cup"){
				Thread.sleep(500);
				System.out.println(customer.getName() + ": Coffee please.");
				System.out.println(server.name + ": making Coffee now!");
				boolean temp = workStation.cappuchino(server);
				if (!temp) {
					cleanTable(server);
					workStation.cappuchino(server);
				}
				
			}
//			No need to check full table because table will never be full. shop only have 2 cups and 2 glasses.	
//			while (table.size()==10) {
//				System.out.println(customer.getName() + " is waiting for a seat at the table.");
//				table.wait();
//			}
			customer.setOrderTrue();
			table.put(customer);
			
			System.out.println(customer.getName() + " took a seat at the table. "
					+ "Table capacity is at " + table.size() + "/10");		
		}
		
	}
			
			
		    
			
//			if (coffeeOrJuice) {
//				System.out.println( customer.getName() + ": I would like to purchase a glass of Juice please."  );	
//			}
	

	
	public void cleanTable(Server server) throws InterruptedException {
		int leftoverCups = 0;
		int leftoverGlasses = 0;
        while (table.size()==0) {
        	System.out.println(server.name + " WENT to table but there is nothing to clean.");
        	return;
        }
        for(Customer customer: table) {
            if (customer.getLeave() == true) {
            	
            	if (customer.getBeverage()=="glass") {
            		leftoverGlasses++;
            		workStation.addGlass();
            	}
            	else if (customer.getBeverage()=="cup") {
            		leftoverCups++;
            		workStation.addCup();
            	}
            	table.remove(customer);
            }
        }
        if(leftoverCups + leftoverGlasses > 0) {
        	System.out.println(server.name + " is washing " + leftoverCups +" cup(s) and " + leftoverGlasses + " glass(es).");
        	Thread.sleep(100);
        	System.out.println(server.name + ": finish washing. Added " + leftoverCups +" cup(s) and " + leftoverGlasses + " glass(es).");
        }
        else {
        	System.out.println(server.name + " went to table but there is nothing to clean.");
        }
	}

	public void leaveTable() throws InterruptedException{
//        while (table.size()==10) wait();
        Customer customer = table.poll();
		System.out.println(customer.getName() + " took a seat at the table. ");
//        notify();
//        notifyAll();
     }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
