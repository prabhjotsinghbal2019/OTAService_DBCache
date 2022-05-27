package com.kochar.common;


import java.util.ArrayList;


public class Attribute {
	
	public Attribute() 
	{
		 name = Integer.toString(System.identityHashCode(this));
		 value = null;
	}
	
	public Attribute(String nm) 
	{
		 name = nm;
		 value = null;
	}
	
	public Attribute(String nm, Object val) 
	{
		 name = nm;
		 value = val;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Object getValue()
	{
		return value;
	}
	
	public boolean isComposite()
	{
		return false;
	}
	
	public boolean equals(Attribute obj)
	{
		return (this.compareTo(obj) == 0);
	}
	
	public int hashCode()
	{
		return (this.name.hashCode());
	}
	
	public short compareTo(Attribute obj)
	{
		short r = 0;
		
		if (obj == null)
		{
			return 1;
		}
		
		r = (short) name.compareTo(obj.name);
		
		return r;  
	}
	
	public String toString()
	{
		return name;
	}
	
	void setParent(Attribute p)
	{
		parent = p;
	}
	
	Attribute getParent()
	{
		return parent;
	}
	
	void setId(int id)
	{
		this.id = id;
	}
	
	int getId()
	{
		return this.id;
	}
	
	public boolean addAttrib(Attribute c) { return false; }
	public boolean remAttrib(Attribute c) { return false; }
	public void getAttribByName(String nm, ArrayList<Attribute> sl) 
	{  
		if (this.name.equals(nm))
			sl.add(this);
	}
	public Attribute getAttribByPos(int pos) { return null;  }
	public Attribute getAttribByName(String nm) { return null; }
	public ArrayList<Attribute> getAllAttribs() { return null; }
	public int getAttribCount()  { return 0; }

	
	protected String name;
	protected Object value;
	protected Attribute parent;
	protected Integer id;

}
