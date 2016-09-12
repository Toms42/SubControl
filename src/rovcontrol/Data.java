package rovcontrol;

public class Data {
	//Data fields for GUI
	private static double voltage=0;
	private static double depth=0;
	private static double speed=0;
	private static double pressure=0;
	private static double temperature=0;
	private static int roll=0;
	private static int pitch=0;
	private static boolean ledState=false;
	private static boolean motorState=false;
	
	//Data fields for XBOX
	private static int leftX=0;
	private static int leftY=0;
	private static int rightX=0;
	private static int rightY=0;
	private static int ltRt=0;
	private static boolean buttonA=false;
	private static boolean buttonB=false;
	private static boolean buttonX=false;
	private static boolean buttonY=false;
	
	
	
	//Data fields for the Motor Powers
	private static int leftMotor;
	private static int rightMotor;
	private static int frontMotor;
	private static int backMotor;
	
	private static float current1;
	private static float current2;
	private static float current3;
	private static float current4;
	
	private static float total_current;
	
	public void Data(){}
	
	synchronized public static void setCurrent1(float f)
	{
		current1 = f;
		total_current = current1 + current2 + current3 + current4;
		rovStarter.window.updateCurrent(current1,current2,current3,current4,total_current);
	}
	synchronized public static void setCurrent2(float f)
	{
		current2 = f;
		total_current = current1 + current2 + current3 + current4;
	}
	synchronized public static void setCurrent3(float f)
	{
		current3 = f;
		total_current = current1 + current2 + current3 + current4;
	}
	synchronized public static void setCurrent4(float f)
	{
		current4 = f;
		total_current = current1 + current2 + current3 + current4;
	}
	
	//Accessor and mutator for voltage
	synchronized public static void setVoltage(double v)
	{
		voltage = v;
		//rovStarter.window is null. We have to fix this
		rovStarter.window.refreshVoltage(v);
	}
	
	public static double getVoltage()
	{
		return voltage;
	}
	
	//Accessor and mutator for voltage
	synchronized public static void setDepth(double d)
	{
		depth = d;
		rovStarter.window.refreshDepth(d);
	}
		
	public static double getDepth()
	{
		return depth;
	}
	
	//Accessor and mutator for voltage
	synchronized public static void setSpeed(double s)
	{
		speed = s;
		rovStarter.window.refreshSpeed(s);
	}
			
	public static double getSpeed()
	{
		return speed;
	}	
	
	//Accessor and mutator for Pressure
	synchronized public static void setPressure(float p)
	{
		pressure = (double) p;  // THIS NEEDS FIXED
		rovStarter.window.refreshPressure(p);
	}
			
	public static double getPressure()
	{
		return pressure;
	}
	
	//Accessor and mutator for Temperature
	synchronized public static void setTemperature(double t)
	{
		temperature = t;
		rovStarter.window.refreshTemperature(t);
	}
			
	public static double getTemperature()
	{
		return temperature;
	}
	
	//Accessor and mutator for led
	synchronized public static void setLed(boolean led)
	{
		ledState = led;
		rovStarter.window.refreshLed(led);
	}
			
	public static boolean getLed()
	{
		return ledState;
	}
	
	//Accessor and mutator for motors
	synchronized public static void setMotors(boolean m)
	{
		motorState = m;
		rovStarter.window.refreshMotor(m);
	}
			
	public static boolean getMotors()
	{
		return motorState;
	}
	
	//Accessor and mutator for Roll
	synchronized public static void setRoll(int r)
	{
		roll = r;
		rovStarter.window.angle.updateGraphics(roll, pitch);
		rovStarter.window.refreshRoll(r);
	}
			
	public static int getRoll()
	{
		return roll;
	}
	
	//Accessor and mutator for Pitch
	synchronized public static void setPitch(int p)
	{
		pitch = p;
		rovStarter.window.angle.updateGraphics(roll, pitch);
		rovStarter.window.refreshPitch(p);
	}
			
	public static int getPitch()
	{
		return pitch;
		
	}
	
	//Accessor and mutator for leftX
	synchronized public static void setLeftX(int lx)
	{
		leftX = lx;
		rovStarter.window.controller.updateGraphics(leftX, leftY, rightX, rightY, ltRt);
		
	}
			
	public static int getLeftX()
	{
		return leftX;
	}
	
	//Accessor and mutator for leftY
	synchronized public static void setLeftY(int ly)
	{
		leftY = ly;
		rovStarter.window.controller.updateGraphics(leftX, leftY, rightX, rightY, ltRt);
		
	}
			
	public static int getLeftY()
	{
		return leftY;
		
	}
	
	//Accessor and mutator for rightX
	synchronized public static void setRightX(int rx)
	{
		rightX = rx;
		rovStarter.window.controller.updateGraphics(leftX, leftY, rightX, rightY, ltRt);
		
	}
			
	public static int getRightX()
	{
		return rightX;	
	}
	
	//Accessor and mutator for rightY
	synchronized public static void setRightY(int ry)
	{
		rightY = ry;
		rovStarter.window.controller.updateGraphics(leftX, leftY, rightX, rightY, ltRt);
		
	}
			
	public static int getRightY()
	{
		return rightY;	
	}
	
	//Accessor and mutator for LT/RT
	synchronized public static void setLtRt(int input)
	{
		ltRt = input;
		rovStarter.window.controller.updateGraphics(leftX, leftY, rightX, rightY, ltRt);
		
	}
	public static int getLtRt()
	{
		return ltRt;	
	}
	
	//Accessor and mutator for button A
	synchronized public static void setButtonA(boolean A)
	{
		buttonA = A;
		
	}
			
	public static boolean getButtonA()
	{
		return buttonA;	
	}
	
	//Accessor and mutator for button B
	synchronized public static void setButtonB(boolean B)
	{
		buttonB = B;
		
	}
			
	public static boolean getButtonB()
	{
		return buttonB;	
	}
	
	//Accessor and mutator for button X
	synchronized public static void setButtonX(boolean X)
	{
		buttonX = X;
		
	}
			
	public static boolean getButtonX()
	{
		return buttonX;	
	}
	
	//Accessor and mutator for button Y
	synchronized public static void setButtonY(boolean Y)
	{
		buttonY = Y;
		
	}
			
	public static boolean getButtonY()
	{
		return buttonY;	
	}
	
	//Accessor and mutator for error text
	public static void setStatus(boolean connection)
	{
		if(connection)
		{
			rovStarter.window.refreshConnection("CONNECTED");
		}
		else
		{
			rovStarter.window.refreshConnection("DISCONNECTED");
		}
	}
	
	//Accessor and mutator for leftMotor
	synchronized public static void setLeftMotor(int l)
	{
		leftMotor = l;
		rovStarter.window.motors.updateGraphics(leftMotor, rightMotor, frontMotor, backMotor);
		
	}
	
	public static int getLeftMotor()
	{
		return leftMotor;
	}
	
	//Accessor and mutator for rightMotor
	synchronized public static void setRightMotor(int r)
	{
		rightMotor = r;
		rovStarter.window.motors.updateGraphics(leftMotor, rightMotor, frontMotor, backMotor);
	}
	
	public static int getRightMotor()
	{
		return rightMotor;
	}
	
	//Accessor and mutator for frontMotor
	synchronized public static void setFrontMotor(int f)
	{
		frontMotor = f;
		rovStarter.window.motors.updateGraphics(leftMotor, rightMotor, frontMotor, backMotor);
	}
	
	public static int getFrontMotor()
	{
		return frontMotor;
	}
	
	//Accessor and mutator for rightMotor
	synchronized public static void setBackMotor(int b)
	{
		backMotor = b;
		rovStarter.window.motors.updateGraphics(leftMotor, rightMotor, frontMotor, backMotor);
	}
	
	public static int getBackMotor()
	{
		return backMotor;
	}
	
	synchronized public static void setHoriztontalMotors()
	{
		
		int thrust = (100-leftY) - 50;
		int rotation = leftX - 50; 
		double thrustPercentageUnscaled = thrust / 50.0;
		double rotationPercentageUnscaled = rotation / 50.0;
		double thrustPercentageScaled = Math.abs(Math.pow(thrustPercentageUnscaled, Config.driveExpo)) * Math.signum(thrustPercentageUnscaled);
		double rotationPercentageScaled = Math.abs(Math.pow(rotationPercentageUnscaled, Config.turnExpo)) * Math.signum(rotationPercentageUnscaled);
		int thrustFinal = (int) (thrustPercentageScaled * 50);
		int rotationFinal = (int) (rotationPercentageScaled * 50);
		int leftMotorSpeed;
		int rightMotorSpeed;
		
		leftMotorSpeed = thrustFinal + rotationFinal;
		rightMotorSpeed = thrustFinal - rotationFinal;
		
		if (Math.abs(leftMotorSpeed) > 50 )
			leftMotorSpeed =  (int)(Math.signum(leftMotorSpeed) * 50);
		if (Math.abs(rightMotorSpeed) > 50 )
			rightMotorSpeed = (int)(Math.signum(rightMotorSpeed) * 50);
		
		setLeftMotor(leftMotorSpeed);
		setRightMotor(rightMotorSpeed);
	}
	
	synchronized public static void setVerticalMotors()
	{
		
		int ascend = (100-ltRt) - 50;
		int pitch = (100-rightY) - 50; 
		double ascendPercentageUnscaled = ascend / 50.0;
		double pitchPercentageUnscaled = pitch / 50.0;
		double ascendPercentageScaled = Math.abs(Math.pow(ascendPercentageUnscaled, Config.elevExpo)) * Math.signum(ascendPercentageUnscaled);
		double pitchPercentageScaled = Math.abs(Math.pow(pitchPercentageUnscaled, Config.tiltExpo)) * Math.signum(pitchPercentageUnscaled);
		int ascendFinal = (int) (ascendPercentageScaled * 50);
		int pitchFinal = (int) (pitchPercentageScaled * 50);
		
		int frontMotorSpeed;
		int backMotorSpeed;
		
		frontMotorSpeed = ascendFinal + pitchFinal;
		backMotorSpeed = ascendFinal - pitchFinal;
		
		if (Math.abs(frontMotorSpeed) > 50 )
			frontMotorSpeed =  (int)(Math.signum(frontMotorSpeed) * 50);
		if (Math.abs(backMotorSpeed) > 50 )
			backMotorSpeed = (int)(Math.signum(backMotorSpeed) * 50);
		
		setFrontMotor(-frontMotorSpeed);
		setBackMotor(-backMotorSpeed);
	}
	
		
}