package rovcontrol;

import net.java.games.input.Component;
import net.java.games.input.Controller;

public class ControllerInput implements Runnable{

	private boolean polling = false;
	private JInputJoystick controller;
	static private int xValuePercentageLeftJoystick = 0;
	static private int yValuePercentageLeftJoystick = 0;
	static private int xValuePercentageRightJoystick = 0;
	static private int yValuePercentageRightJoystick = 0;
	static private int zAxisValuePercentage = 0;
	static private int numberOfButtons = 0;
	static private boolean buttonX = false;
	static private boolean buttonA = false;
	static private boolean buttonB = false;
	static private boolean buttonY = false;
	

	
	public ControllerInput(){
		
		
	}
	
	public void run()
	{
		while(polling)
		{
			//System.out.println("\"controller\" in thread: " + Thread.currentThread().toString());
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!controller.pollController() ) 
			{
				   System.out.println("Controller disconnected!");
			}
			
			xValuePercentageLeftJoystick = controller.getXAxisPercentage();
			yValuePercentageLeftJoystick = controller.getYAxisPercentage();
			xValuePercentageRightJoystick = controller.getXRotationPercentage();
			yValuePercentageRightJoystick = controller.getYRotationPercentage();
			
			 
			/*buttonA = controller.getButtonValue(0);
			buttonB = controller.getButtonValue(1);
			buttonX = controller.getButtonValue(2);
			buttonY = controller.getButtonValue(3);*/
			
			if(controller.componentExists(Component.Identifier.Axis.Z))
			{
			      zAxisValuePercentage = controller.getZAxisPercentage();
			}
			
			
			
			/*
			//Data.setVoltage(Data.getVoltage()+0.1);
			Data.setSpeed(Data.getSpeed()+0.11);
			Data.setDepth(Data.getDepth()+0.11);
			Data.setTemperature(Data.getTemperature()+0.11);
			Data.setPressure(Data.getPressure()+0.11);
			
			Data.setValve1Open(false);
			Data.setValve2Open(false);
			//Data.setRoll(Data.getRoll()+1);
			//Data.setPitch(Data.getPitch()+1);
			*/
			
			
			Data.setLeftX(ControllerInput.getXPercentageLeft());
			Data.setLeftY(ControllerInput.getYPercentageLeft());
			Data.setRightX(ControllerInput.getXPercentageRight());
			Data.setRightY(ControllerInput.getYPercentageRight());
			Data.setLtRt(ControllerInput.getLtRt());
			
			Data.setHoriztontalMotors();
			Data.setVerticalMotors();
			
			sendMotorValues(Data.getLeftMotor(),
					Data.getRightMotor(),
					Data.getFrontMotor(),
					Data.getBackMotor());
			
		}
	}
	
	public void sendMotorValues(int m1, int m2, int m3, int m4)
	{
		rovStarter.coms.sendUnsigned(127);
		rovStarter.coms.sendUnsigned(m1+50);
		rovStarter.coms.sendUnsigned(m2+50);
		rovStarter.coms.sendUnsigned(m3+50);
		rovStarter.coms.sendUnsigned(m4+50);
	}
	
	public void startPolling()
	{
		polling=true;
		controller = new JInputJoystick(Controller.Type.STICK, Controller.Type.GAMEPAD);
		// Check if the controller was found.
		if( !controller.isControllerConnected() )
		{
		   System.out.println("No controller found!");
		}
		numberOfButtons = controller.getNumberOfButtons();
		
		new Thread(this).start();
			
	}
	
	public void stopPolling()
	{
		polling=false;
	}
	
	public static int getXPercentageLeft()
	{
		return xValuePercentageLeftJoystick;
	}
	
	public static int getXPercentageRight()
	{
		return xValuePercentageRightJoystick;
	}
	
	public static int getYPercentageLeft()
	{
		return yValuePercentageLeftJoystick;
	}
	
	public static int getYPercentageRight()
	{
		return yValuePercentageRightJoystick;
	}
	
	public static int getLtRt()
	{
		return zAxisValuePercentage;
	}


}