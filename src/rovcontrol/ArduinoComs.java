package rovcontrol;

// import javax.comm.*; -- superseded by gnu.io
// http://www.java2s.com/Code/Jar/c/Downloadcomm20jar.htm
import gnu.io.*;
// http://www.java2s.com/Code/Jar/r/Downloadrxtx217jar.htm

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
// https://docs.oracle.com/javase/7/docs/api/index.html?java/util/concurrent/ConcurrentLinkedQueue.html


public class ArduinoComs implements SerialPortEventListener{

	private boolean comsRunning = true;
	
	SerialPort serialPort;
    /** The port we're normally going to use. */
	private static final String PORT_NAMES[] = {Config.COMS_PORT };
	/**
	* A BufferedReader which will be fed by a InputStreamReader 
	* converting the bytes into characters 
	* making the displayed results codepage independent
	*/
	//private BufferedReader input;
	/** The output stream to the port */
	private InputStream inputStream;
	private OutputStream outputStream;
	/** Milliseconds to block while waiting for port open */
	
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;
	private static final int READ_BUF_SIZE = 200; // seems to be about 32
	private static final int ARDUINO_PACKET_SIZE = 5; // packet size not including 255
	
	public ConcurrentLinkedQueue<Integer> byteQueue;
	// https://docs.oracle.com/javase/7/docs/api/index.html?java/util/concurrent/ConcurrentLinkedQueue.html
	
	// public void ArduinoComs(){}
	
	//init the coms on the port specified in Config.java
	public void init()
	{
        // the next line is for Raspberry Pi and 
        // gets us into the while loop and was suggested here was suggested http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186

		// NEW CODE:
		
		byteQueue = new ConcurrentLinkedQueue<Integer>();

		CommPortIdentifier portId = null;
		@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
		
		//First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}
		
		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);
		
			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
		
			// open the streams
			// input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			inputStream = serialPort.getInputStream();
			
			outputStream = serialPort.getOutputStream();
		
			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			System.out.println("coms started");
		} catch (Exception e) {
			System.err.println(e.toString());
		}

		
	}
	
	public synchronized void sendUnsigned(int b)
	{
		if(b>127)
		{
			write("send overflow: " + b);
			b=126;
		}
		else if(b<0)
		{
			write("send underflow: " + b);
			b=0;
		}
		write("sending value: " + b);
		try {
			outputStream.write(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Serial Transmission error!");
		}
	}
	
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}
	public void write (String s) {
		System.out.println(s);
	}
	
	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try{
				// NEW CODE
				byte[] readBuffer = new byte[READ_BUF_SIZE];
				// write("Event caught: " + oEvent.toString());
				
				Integer[] intBuf = new Integer[READ_BUF_SIZE];

	            try {
	            	int numBytes = inputStream.read(readBuffer);
	            	// write("read bytes: " + numBytes);
	            	for(int i = 0; i < numBytes; i++) {
	            		int ibyte = readBuffer[i];
	            		if (ibyte<0) ibyte+=256; 
	            		byteQueue.add(new Integer(ibyte));
	            		write("byte: " + ibyte);
	            	}
	            } catch (IOException e) {System.out.println(e);}
	           	
	            int qvalue = 0;
	            if (byteQueue.size() > 2*ARDUINO_PACKET_SIZE) {
	            	do {
	            		qvalue = (int)byteQueue.remove();
	            		write("popping...");
	            	}
	            	while (qvalue != 255 && byteQueue.size()>ARDUINO_PACKET_SIZE);
	            	write("found a 255");
	            	updateData(byteQueue);
	            	byteQueue.clear();
	            }				
			} catch (Exception e)
			{
				System.err.println("Badness: " + e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other ones.
	}
	
	private void updateData(ConcurrentLinkedQueue bq)
	{
		write("updating values..."); 
		try { // Order is important -- check Arduino code
			Data.setTemperature((double)(int)bq.remove());
			Data.setPressure((int)bq.remove());
			Data.setRoll((int)(bq.remove()) - 128);
			Data.setPitch(-((int)(bq.remove()) - 128));
			Data.setVoltage((double)(int) bq.remove());
		} catch (Exception e) {
			System.out.println("error receiving data" + e.toString());
			e.printStackTrace();
		}
	}

	
	public CommPortIdentifier[] getPortsList()
	{
		ArrayList ports = new ArrayList();
		
		java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
		
		System.out.println("scanning ports...");
		
        while ( portEnum.hasMoreElements() ) 
        {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            if(portIdentifier.getPortType()==CommPortIdentifier.PORT_SERIAL)
            {
            	ports.add(portIdentifier);
                System.out.println("found serial port: " + portIdentifier.getName());
            }
        }  
        System.out.println("done scanning for ports!");
        
        CommPortIdentifier[] result = new CommPortIdentifier[ports.size()];
        for(int i=0; i<ports.size();i++)
        	result[i]=(CommPortIdentifier) ports.get(i);
        return result;
	}
}