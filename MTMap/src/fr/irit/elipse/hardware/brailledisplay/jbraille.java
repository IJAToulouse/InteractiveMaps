package fr.irit.elipse.hardware.brailledisplay;

/**
 * @author Philippe Truillet
 * 
 */
 
public class jbraille 
{
	public native int init();
	public native int init(String port);
	public native int close();
	
	public native int size();
	public native int display(String s);
	
	public native void info();
	
  private static final long serialVersionUID = 1L;
	
	static
	{
		try {
			System.loadLibrary("jbraille");
		}
		catch (Exception e) {
         e.printStackTrace();
		}	
	}
}