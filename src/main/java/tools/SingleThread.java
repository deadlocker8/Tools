package tools;

public class SingleThread extends Thread
{
	public static boolean running;
		
	@Override
	public void run() 
	{				
			 while (running) 
			 {
				 try 
				 {		
					 //Code goes here


					 Thread.sleep(1000);
				 }				 
				 catch (InterruptedException e) 
				 {
					 running = false;
				 }			
			}	
	}
}