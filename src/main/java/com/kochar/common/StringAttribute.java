package com.kochar.common;

public class StringAttribute extends Attribute {
	
	public StringAttribute()
	{
		super();
		value = new String("");
	}
	
	public StringAttribute(String nm)
	{
		super(nm);
		value = new String("");
	}
	
	public StringAttribute(String nm, Object val)
	{
		super(nm,val);
	}
	
	public StringAttribute(Object val)
	{
		super();
		value = new String((String)val);
	}

	public short compareTo(Attribute obj)
	{
		short r = 0;
		
		if (obj == null)
		{
			return 1;
		}
		
		r = super.compareTo(obj);
		
		if (r == 0)
		{
			r = (short) ((String)value).compareTo((String)obj.value);
		}
		
		return r;  
	}
	
	public String toString()
	{
		return (super.toString() + " = " + value.toString());
	}
	
	
}
