package com.kochar.common;


import com.kochar.services.KSimpleLogger;
//import com.kochar.lordjoe.Delegator;
import com.kochar.common.LogItem;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;


    //public delegate void PulseHandler(object args);
	//public delegate void SpeedChangeHandler(int milliseconds);
	//public delegate void RunStateChangeHandler(RunState state);
	/// <summary>
	/// Summary description for InprocessAsynchronousService.
	/// </summary>
	public class ServiceAttribute extends Attribute implements Runnable
	{

//	    public static interface IHeartbeat {
//	        public void heartbeat(Object args);
//	    }
		
		 /*protected class ManualResetEvent {
		      private final Object monitor = new Object();
		      private volatile boolean open = false;

		      public ManualResetEvent(boolean open) 
		      {
		        this.open = open;   
		      }

		      public void waitOne() throws InterruptedException {
		        synchronized (monitor) {
		          while (open==false) {
		              monitor.wait();
		          }
		        }
		      }
		      
		      public void waitTimeout(long m) throws InterruptedException {
			        synchronized (monitor) {
			          while (open==false) {
			              monitor.wait(m);
			          }
			        }
		      }

		      public void set() {//open start
		        synchronized (monitor) {
		          open = true;
		          monitor.notifyAll();
		        }
		      }

		      public void reset() {//close stop
		        synchronized(monitor) {
		           open = false;
		        }
		      }
		   }
*/		 
		 public class ManualResetEvent {
			 private final static int MAX_WAIT = 1;  // was 1000
			 private final static String TAG = "ManualEvent"; 
			 private Semaphore semaphore = new Semaphore(MAX_WAIT, false);
			 private volatile boolean signaled = false;
			 
			 public ManualResetEvent(boolean signaled) {
			 	this.signaled = signaled; 
			 	if (!signaled) {
			 	  count = semaphore.drainPermits();
			 	}
			 }

			 public boolean WaitOne() {
			 	return WaitOne(Long.MAX_VALUE);
			 }

			 private volatile int count = 0;
			 public boolean WaitOne(long millis) {
			 	boolean bRc = true;
			 	if (signaled)
			 		return true;

			 	try {
			 		++count;
			 		if (count > MAX_WAIT) {
			 			//KSimpleLogger.Instance().LogMessage(TAG, "ManualResetEvent::WaitOne", "More requests than waits: " + String.valueOf(count));
			 		}

		 			//KSimpleLogger.Instance().LogMessage(TAG, "ManualResetEvent::WaitOne", "ManualEvent WaitOne Entered");
			 		bRc = semaphore.tryAcquire(millis, TimeUnit.MILLISECONDS);
		 			//KSimpleLogger.Instance().LogMessage(TAG, "ManualResetEvent::WaitOne", "ManualEvent WaitOne=" + String.valueOf(bRc));
			 	}
			 	catch (InterruptedException e) {
			 		bRc = false;
			 	}
			 	finally {
			 		--count;
			 	}

	 			//KSimpleLogger.Instance().LogMessage(TAG, "ManualResetEvent::WaitOne", "ManualEvent WaitOne Exit");
			 	return bRc;
			 }

			 public void Set() {
				//KSimpleLogger.Instance().LogMessage(TAG, "ManualResetEvent::Set", "ManualEvent Set");
			 	signaled = true;
			 	semaphore.release(MAX_WAIT);
			 }

			 public void Reset() {
			 	signaled = false;
			 	//stop any new requests
			 	count = semaphore.drainPermits();
				//KSimpleLogger.Instance().LogMessage(TAG, "ManualResetEvent::Set",  "ManualEvent Reset: Permits drained=" + String.valueOf(count));
			 }
		 }
		 
		//Used to put the thread into a paused state and to unpause that thread when
		//the Start method is called 
		//The thread is paused when the Pause method is called or temporarily paused
		//as part of the throttle mechanism
		//Suggested by yfoulon http://www.codeproject.com/csharp/InprocessAsynServicesInCS.asp#xx1276684xx
		private ManualResetEvent PauseTrigger = new ManualResetEvent(false);

		protected Queue<ServiceMessage> synchQ = null;
		
	        //protected final Delegator PULSE_HANDLER = new Delegator(IHeartbeat.class);

		//Delegate used to signal that it is time to process
		//protected IHeartbeat Heartbeat = null;
		//Used to adjust run speed of this threaded service
		protected Throttle SpeedControl = null;
                protected long ticksCounter = 0;
		//Event that is fired off whenever the throttle speed gets changed
		//public event SpeedChangeHandler SpeedChanged;
		//Event that is fired off whenever the runstate gets changed
		//public event RunStateChangeHandler RunStateChanged;
		//Allows us to remember what run state we are in
		private ServiceMessage.RunState runstate = ServiceMessage.RunState.Stoped;
                private ExecutorService executor = Executors.newSingleThreadExecutor();
                
		public ServiceAttribute()
		{
			//Create the queue to hold messages
			//q = new Queue();
			//Get a synchronized version so we can do multithreaded access to the queue
			synchQ = new ConcurrentLinkedQueue<>();
			value = null; // value holds handle to current thread ...
		}

		public synchronized void SendMessage(ServiceMessage Message)
		{
			synchQ.add(Message);			
		}

		public synchronized void SetSpeed(Throttle.RunSpeed speed)
		{
			switch(speed)
			{
				case Fast:
					SpeedControl.Fast();
					break;

				case Faster:
					SpeedControl.Faster();
					break;

				case Fastest:
					SpeedControl.Fastest();
					break;

				case Slow:
					SpeedControl.Slow();
					break;

				case Slower:
					SpeedControl.Slower();
					break;

				case Slowest:
					SpeedControl.Slowest();
					break;
			}

			
			//Let listeners know we just changed the speed
			//if(SpeedChanged != null)
			//{
			//	SpeedChanged(SpeedControl.Speed);
			//}
		}
		
		public synchronized void SetSpeed(int milliseconds)
		{
			SpeedControl.GoThisFast(milliseconds);

			//Let listeners know we just changed the speed
			//if(SpeedChanged != null)
			//{
			//	SpeedChanged(SpeedControl.Speed);
			//}
		}

		public synchronized void Start()
		{
			if(runstate != ServiceMessage.RunState.Started)
			{
				if(runstate == ServiceMessage.RunState.Paused)
				{
					//Set State
					runstate = ServiceMessage.RunState.Started;

					//Unpause the thread
					PauseTrigger.Set();

					//Let listeners know the state changed
					//if(RunStateChanged != null)
					//{
					//	RunStateChanged((RunState)runstate);
					//}
				}
				else if(runstate == ServiceMessage.RunState.Stoped)
				{
					//Set State
					runstate = ServiceMessage.RunState.Started;

					//Let listeners know the state changed
					//if(RunStateChanged != null)
					//{
					//	RunStateChanged((RunState)runstate);
					//}

					//Delegate execution of this thread to the MainLoop method
					//Thread ts = new Thread(MainLoop);
					//Create the thread
					//Runnable r = Delegator.buildRunnable(this,"MainLoop");
//					value = new Thread(r);
//					//Begin
//					((Thread)value).start();
//                                      
                                        executor.execute(this);
                                        value = executor;
				}
			}
		}

		public synchronized void Stop()
		{
			//Kill the thread
			if(runstate != ServiceMessage.RunState.Stoped)
			{
				//Set the throttle to stop
				SpeedControl.GoThisFast(0);

				//Put Stop message in the queue
				synchQ.add(new ServiceMessage(ServiceMessage.RunState.Stoped));

				//Set State
				runstate = ServiceMessage.RunState.Stoped;

				//Let listeners know the state changed
				//if(RunStateChanged != null)
				//{
				//	RunStateChanged((RunState)runstate);
				//}
			}
			
			// wait for mainloop to complete for 
			try {
				//((Thread)value).join(4000);
                                executor.shutdownNow();
                                executor.awaitTermination(200, TimeUnit.MILLISECONDS);
                                
			} catch (Exception e) {
				// TODO Auto-generated catch block
                                KSimpleLogger.Instance().SendMessage(new ServiceMessage(new LogItem(Level.WARNING, this.getClass().getName(), "Stop()", e.toString(),e )));				
			}
		}

		public synchronized void Pause()
		{
			if(runstate != ServiceMessage.RunState.Paused)
			{
				//Set state
				runstate = ServiceMessage.RunState.Paused;

				//Let listeners know the state changed
				//if(RunStateChanged != null)
				//{
				//	RunStateChanged((RunState)runstate);
				//}
			}
		}

                @Override
		public void run()
		{
                    try {
                        
                        for (;;) {
                            if (runstate == ServiceMessage.RunState.Paused) {
                                PauseTrigger.WaitOne();
                                //Reset the trigger so it can be triggered again
                                PauseTrigger.Reset();
                            }

                            //If there are no messages to pass on then just fire with a null
                            if (synchQ.size() == 0) {
                                //Fire!
                                //Heartbeat.heartbeat(null);
                                this.heartbeat(null);
                            } else {
                                //Get the message from the queue
                                ServiceMessage msg = (ServiceMessage) synchQ.remove();
                                Object args = msg.Args();
                                //If we got a message then see if it contains a run state change
                                ServiceMessage.RunState rState = msg.ChangeToRunState();
                                
                                if (rState != ServiceMessage.RunState.Zero) {
                                    //If so then see if this is ONLY a run state change
                                    if (args != null) {
                                        //If not then fire off the heartbeat along with the args
                                        //Heartbeat.heartbeat(args);
                                        this.heartbeat(args);

                                        //If the requested run state change is to stop then return which kills
                                        //this threads execution loop
                                        if (rState == ServiceMessage.RunState.Stoped) {
                                            return;
                                        }
                                    } else if (rState == ServiceMessage.RunState.Stoped) {
                                        //If the requested run state change is to stop then return which kills
                                        //this threads execution loop
                                        return;
                                    }
                                } else {
                                    //If not then just pass on the args
                                    //Heartbeat.heartbeat(args);
                                    this.heartbeat(args);
                                    
                                }
                                
                                // release the service message
                                msg.dtorServiceMessage();
                                msg = null;
                                
                            }

                            //Pause the thread until the throttle period is over or until
                            //the Start method is called
                            PauseTrigger.WaitOne(SpeedControl.Speed());
                            PauseTrigger.Reset();
                        }
                    } catch (Exception e) {
                        KSimpleLogger.Instance().SendMessage(new ServiceMessage(new LogItem(Level.SEVERE, this.getClass().getName(), "MainLoop()", e.toString(), e)));
                    }
		}
		
		public void heartbeat(Object args)
		{
			if (args != null) 
			{
                            KSimpleLogger.Instance().SendMessage(new ServiceMessage(new LogItem(Level.WARNING, this.getClass().getName(), "HeartBeat()", "Base In Process Service Called.",null )));
			}

		}

	}
		

