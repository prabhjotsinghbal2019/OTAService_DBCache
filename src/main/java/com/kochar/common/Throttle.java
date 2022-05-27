package com.kochar.common;


    /// <summary>
	/// Summary description for Throttle.
	/// </summary>
	public class Throttle
	{

		public enum RunSpeed {
			Fast(1),Faster(2),Fastest(3),Slow(4),Slower(5),Slowest(6);
		
			private int sCode;
		 
			private RunSpeed(int s) {
				sCode = s;
			}
	 
			public int getCode() {
				return sCode;
			}
		}
	
		
		int speed = 0;

		int fast = 100;
		int faster = 50;
		int fastest = 10;
		int slow = 1000;
		int slower = 2000;
		int slowest = 3000;

		/// <summary>
		/// Define your speeds
		/// </summary>
		/// <param name="Fast">milliseconds</param>
		/// <param name="Faster">milliseconds</param>
		/// <param name="Fastest">milliseconds</param>
		/// <param name="Slow">milliseconds</param>
		/// <param name="Slower">milliseconds</param>
		/// <param name="Slowest">milliseconds</param>
		public Throttle(int Fast, int Faster, int Fastest, int Slow, int Slower, int Slowest)
		{
			fast = Fast;
			faster = Faster;
			fastest = Fastest;
			slow = Slow;
			slower = Slower;
			slowest = Slowest;
		}

		/// <summary>
		/// Speeds are set to defaults
		/// </summary>
		public Throttle()
		{			
		}

		public synchronized void Fast()
		{
			speed = fast;
		}

		public synchronized void Faster()
		{
			speed = faster;
		}

		public synchronized void Fastest()
		{
			speed = fastest;
		}

		public synchronized void Slow()
		{
			speed = slow;
		}

		public synchronized void Slower()
		{
			speed = slower;
		}

		public synchronized void Slowest()
		{
			speed = slowest;
		}

		public synchronized void GoThisFast(int milliseconds)
		{
			speed = milliseconds;
		}

		/// <summary>
		/// The speed of the throttle
		/// </summary>		
		public int Speed()
		{
			return speed;
		}
		

	}
