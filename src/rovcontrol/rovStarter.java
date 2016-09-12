/**
 * 
 */
package rovcontrol;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



/**
 * @author Tom
 *
 */
public class rovStarter {

	public static WebcamManager camera;
	public static ArduinoComs coms;
	
	public static rovWindow window = new rovWindow();
	
	ActionListener actionListener;
	
	
	public static void main(String[] args)
	{
		try {
			camera = new WebcamManager();
			camera.initWebcam();
			System.out.println("initializing camera...");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("no camera detected");
		}
		
		coms = new ArduinoComs();
		//coms.init();
		initWindow();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ControllerInput input = new ControllerInput();
		input.startPolling();
		try {
			window.initWebcamPanel();
		} catch (Exception e1) {
			System.out.println("error with camera. sux4u lol");
		}
		
		//System.out.println(" > Welcome to Sub Control! \nCurrent version -- " + Config.VERSION);

		coms.getPortsList();

		
		
		while(true)
		{
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	private static void initWindow()
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	static void echoTest()
	{
		int test1 = 255;
		int test2 = 12;
		int test3 = 24;
		int test4 = 36;
		int test5 = 48;
		int test6 = 60;
		
		for(int i =0;i<60;i++)
		{
			coms.sendUnsigned(test1);
			coms.sendUnsigned(test2);
			coms.sendUnsigned(test3);
			coms.sendUnsigned(test4);
			coms.sendUnsigned(test5);
			coms.sendUnsigned(test6);
		}
	}
	
	/*public void control(){
		actionListener = new ActionListener() {
			@SuppressWarnings("unused")
			public void ActionPerformed(ActionEvent actionEvent) {
				;
			}
			
		};
	}*/
}
