package com.kochar.common;


	/// <summary>
	///    Summary description for ServiceMessage.
	/// </summary>
	public class ServiceMessage
	{
		public enum RunState {
			Zero(0), Started(1),Stoped(2),Paused(3);
			
			private int sCode;
			 
			private RunState(int s) {
				sCode = s;
			}
	 
			public int getCode() {
				return sCode;
			}
		}

		
		RunState state = RunState.Zero;
		Object o = null;

		public ServiceMessage(RunState State, Object args)
		{
			state = State;
			o = args;
		}

		public ServiceMessage(RunState State)
		{
			state = State;			
		}

		public ServiceMessage(Object args)
		{			
			o = args;
		}
                
                public void dtorServiceMessage() {
                    o = null;
                }

		/// <summary>
		/// If not zero this property indicates the InprocessAsynchronousService should change handle a run state change
		/// </summary>
		public RunState ChangeToRunState()
		{
			return state;
		}

		/// <summary>
		/// Args to be passed on to the Heartbeat handlers
		/// </summary>
		public Object Args()
		{
			return o;
		}
		
		public void setArgs(Object obj)
		{
			this.o = obj;
		}
	}
