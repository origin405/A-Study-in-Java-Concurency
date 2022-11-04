import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

class Server implements Runnable {
	Serve shop;
    public volatile boolean closingTime = false;
    public String name;
    
    long time = 50;
    
    public Server(String name, Serve shop)
    {
    	this.name = name;
        this.shop = shop;
        
    }

    public void run()
    {
        if(this.name=="Owner") {
        	try {
				owner();
				Thread.sleep(3000);
				shop.cleanTable(this);
				System.out.println("Table capacity: " + shop.table.size() + "\nOnwer cleaned and closed the shop. See you tomorrow!");
				Thread.sleep(500);
				System.exit(0);
			} 
        	catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        try {
			server();
			Thread.sleep(500);
			return;
	    } 
        catch (InterruptedException e) {
				e.printStackTrace();
		}     
    }
    
    public void owner() throws InterruptedException {
//    	Thread.sleep(0);
        System.out.println("Owner started..");
        
        while(!closingTime) {
        	while(shop.queue_block.size()!=0) {
//        		boolean isLockAcquired = orderLock.tryLock(1, TimeUnit.MILLISECONDS);
//        		System.out.println("Owner acquire lock = " + isLockAcquired);
        		
        			if (shop.orderLock.tryLock(500, TimeUnit.MILLISECONDS)) {
                        shop.order(this);
                        
                     }
                     else {
                 		shop.cleanTable(this);
                 		Thread.sleep(1500);
                 	}	
        	} 

        }
        
           
   }
    
    
    public void server() throws InterruptedException {
//    	Thread.sleep(50);
    	System.out.println(this.name + " started..");
        while(!closingTime) {
        	
        	if (shop.orderLock.tryLock(500, TimeUnit.MILLISECONDS)) {
                shop.order(this);	
                shop.orderLock.unlock();
            }
            else {
                shop.cleanTable(this);
                Thread.sleep(1500);
            }
        }
        	
            
        

        System.out.println(this.name + ": Cafe Closed. I'm outta here bitches!");
        Thread.sleep(500);
    }
    
    
    
    public void setclosingTime()
    { 
        this.closingTime = true;
            System.out.println(this.name + " : We're closing now!");
    }
    

}

