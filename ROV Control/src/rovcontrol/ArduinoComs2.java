package rovcontrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener;

import java.util.ArrayList;
import java.util.Enumeration;

public class ArduinoComs2 implements SerialPortEventListener{

	private boolean comsRunning = true;
	
	SerialPort serialPort;
    /** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { 
		Config.COMS_PORT
	};
	/**
	* A BufferedReader which will be fed by a InputStreamReader 
	* converting the bytes into characters 
	* making the displayed results codepage independent
	*/
	private BufferedReader input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	
	private static final int TIME_OUT = 20000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;
	
	public void ArduinoComs(){}
	
	public void init()
	{
        // the next line is for Raspberry Pi and 
        // gets us into the while loop and was suggested here was suggested http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186

		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
		
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
			System.out.println("serial port opened: " + portId);
		
			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			System.out.println("Port Parameters: " + SerialPort.DATABITS_8 + " " + SerialPort.STOPBITS_1 + " " + SerialPort.PARITY_NONE);
		
			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();
			System.out.println("input and output streams opened");
		
			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			System.out.println("coms started");
		} catch (Exception e) {
			System.err.println(e.toString());
		}

		
	}
	
	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}
	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				int inputLine=input.read();
				//parseInputLine(inputLine);
				//updateData();
				if(inputLine==255)
					System.out.println();
			} catch (Exception e) 
			{
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other ones.
	}

	public synchronized void sendUnsigned(int b)
	{
		try {
			output.write(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Serial Transmission error!");
		}
	}
	
	public void shutdownComs()
	{
		comsRunning = false;
		close();
	}
	
	
	int numBytesReceived=0;
	int numBytesToRead=4;
	int[] msg = new int[4];
	int[] tmsg = new int[4];
	
	public void parseInputLine(int num)
	{
		if(num==255)
		{
			numBytesReceived=0;
		}

		else
		{
			System.out.println(numBytesReceived);
			if(numBytesReceived<numBytesToRead)
				tmsg[numBytesReceived] = num;
			if(numBytesReceived==numBytesToRead-1)
			{
				for(int i = 0; i<tmsg.length;i++)
					msg[i]=tmsg[i];
			}
			numBytesReceived++;
		}
	}
	
	private void updateData()
	{
		try {
			Data.setVoltage(msg[0]);
			Data.setPitch(msg[1]-128);
			Data.setRoll(msg[2]-128);
			Data.setTemperature(msg[3]);
			for(int i = 0; i<4;i++)
			{
				System.out.print(msg[i]+" ");
			}
		} catch (Exception e) {
			System.out.println("error receiving data");
		}
	}

	@SuppressWarnings("unchecked")
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
