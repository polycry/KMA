//package geschirrspueler;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class JNIWrapper 
{

	public static final int INPUT = 1;
	public static final int OUTPUT = 2;
	public static final int ALT0 = 0; // alternate function 0

	static{
		System.load("/home/pi/Projekt/Geschirrspueler/JNIWrapperlib.so");
	}

	public  native boolean loadMemory();

	public  native boolean configPortIO(int portNr, int direction);

	public  native boolean configPortAlt(int portNr, int alt);

	public  native void writeOutput(int portNr, int output);

	public  native int readInput(int portNr);

	public  native void i2c_write_to_slave(char slaveaddress,char howmany);

	public  native void i2c_read_from_slave(char slaveaddress,char howmany);

	public  native char pic_response();

	public  native int getADCValue();

}
