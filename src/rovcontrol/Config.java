package rovcontrol;

import java.awt.Dimension;

import com.github.sarxos.webcam.WebcamResolution;



public class Config {

	final public static String VIDEO_PATH = "video.wmv"; // was .TS
	final public static Dimension RESOLUTION = WebcamResolution.VGA.getSize();
	
	final public static String VERSION = "1.0";
	
	public static String COMS_PORT = "COM0";
	
	//-----------//
	//---DRIVE---//
	//-----------//
	
	final public static int turnFactor=1;
	final public static int turnExpo=3;
	
	final public static int driveFactor=1;
	final public static int driveExpo=3;
	
	final public static int tiltFactor=1;
	final public static int tiltExpo=3;

	final public static int elevFactor=1;
	final public static int elevExpo=3;
}
