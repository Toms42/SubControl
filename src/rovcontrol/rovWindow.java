package rovcontrol;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JTextPane;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.io.PrintStream;

import javax.swing.JRadioButton;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import rovcontrol.TextAreaOutputStream;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class rovWindow {

	JFrame frame;
	
	JTextPane txtpnv;
	JTextArea depth;
	JTextArea speed;
	JTextArea pressure;
	JTextArea temperature;
	JTextArea pitch;
	JTextArea roll;
	
	JButton valve1Sample;
	JButton valve2Sample;
	AnglePanel angle;
	ControllerPanel controller;
	MotorPanel motors;
	
	

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public rovWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	public void update()
	{
		System.out.println("updating gui...");
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBackground(Color.BLACK);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		
		//Setting up each individual panel
		
		try {
			initWebcamPanel();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("webcam cannot be null. Leaving panel empty.");
		}
		
		JPanel lowerPanel = new JPanel();
		lowerPanel.setBackground(Color.DARK_GRAY);
		frame.getContentPane().add(lowerPanel, BorderLayout.SOUTH);
		lowerPanel.setPreferredSize(new Dimension(0,220));
		GridBagLayout gbl_lowerPanel = new GridBagLayout();
		gbl_lowerPanel.columnWidths = new int[]{0, 0};
		gbl_lowerPanel.rowHeights = new int[]{0, 0};
		gbl_lowerPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_lowerPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		lowerPanel.setLayout(gbl_lowerPanel);
		
		JPanel lower_centered = new JPanel();
		lower_centered.setBackground(new Color(139, 0, 0));
		GridBagConstraints gbc_lower_centered = new GridBagConstraints();
		gbc_lower_centered.weighty = 1.0;
		gbc_lower_centered.weightx = 1.0;
		gbc_lower_centered.gridx = 0;
		gbc_lower_centered.gridy = 0;
		lower_centered.setPreferredSize(new Dimension(1000,200));
		lower_centered.setLayout(null);
		lowerPanel.add(lower_centered, gbc_lower_centered);
		
		JPanel consolePanel = new JPanel();
		consolePanel.setBackground(new Color(139, 0, 0));
		consolePanel.setBounds(0, 0, 333, 200);
		lower_centered.add(consolePanel);
		consolePanel.setLayout(null);
		
		JTextArea consolePn = new JTextArea(8,36);
		consolePn.setEditable(false);
		consolePn.setForeground(Color.RED);
		consolePn.setBackground(Color.BLACK);
		consolePanel.add(consolePn);
		
		JScrollPane scrollPane = new JScrollPane(consolePn);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 11, 313, 178);
		consolePanel.add(scrollPane);
		
		angle = new AnglePanel();
		angle.setBackground(new Color(139, 0, 0));
		angle.setBounds(333, 0, 333, 200);
		lower_centered.add(angle);
		angle.setLayout(null);
		
		JTextArea txtrPitch = new JTextArea();
		txtrPitch.setFont(new Font("Monospaced", Font.PLAIN, 16));
		txtrPitch.setForeground(Color.LIGHT_GRAY);
		txtrPitch.setText("PITCH:");
		txtrPitch.setBackground(new Color(139, 0, 0));
		txtrPitch.setBounds(50, 130, 100, 30);
		angle.add(txtrPitch);
		
		JTextArea txtrRoll = new JTextArea();
		txtrRoll.setFont(new Font("Monospaced", Font.PLAIN, 16));
		txtrRoll.setText("ROLL:");
		txtrRoll.setForeground(Color.LIGHT_GRAY);
		txtrRoll.setBackground(new Color(139, 0, 0));
		txtrRoll.setBounds(200, 130, 100, 30);
		angle.add(txtrRoll);
		
		pitch = new JTextArea();
		pitch.setForeground(Color.LIGHT_GRAY);
		pitch.setBackground(Color.DARK_GRAY);
		pitch.setBounds(50, 160, 100, 22);
		angle.add(pitch);
		
		roll = new JTextArea();
		roll.setForeground(Color.LIGHT_GRAY);
		roll.setBackground(Color.DARK_GRAY);
		roll.setBounds(200, 160, 100, 22);
		angle.add(roll);
		
		controller = new ControllerPanel();
		controller.setBackground(new Color(139, 0, 0));
		controller.setBounds(666, 0, 333, 200);
		lower_centered.add(controller);
		controller.setLayout(null);
		
		JTextArea txtrThrust = new JTextArea();
		txtrThrust.setForeground(Color.LIGHT_GRAY);
		txtrThrust.setBackground(new Color(139, 0, 0));
		txtrThrust.setText("Pitch");
		txtrThrust.setBounds(151, 60, 44, 22);
		controller.add(txtrThrust);
		
		JTextArea txtrThrust_1 = new JTextArea();
		txtrThrust_1.setForeground(Color.LIGHT_GRAY);
		txtrThrust_1.setBackground(new Color(139, 0, 0));
		txtrThrust_1.setText("Thrust");
		txtrThrust_1.setBounds(0, 60, 52, 22);
		controller.add(txtrThrust_1);
		
		JTextArea txtrAscension = new JTextArea();
		txtrAscension.setText("Ascension");
		txtrAscension.setForeground(Color.LIGHT_GRAY);
		txtrAscension.setBackground(new Color(139, 0, 0));
		txtrAscension.setBounds(52, 167, 97, 22);
		controller.add(txtrAscension);
		
		JTextArea txtrYaw = new JTextArea();
		txtrYaw.setText("Yaw");
		txtrYaw.setForeground(Color.LIGHT_GRAY);
		txtrYaw.setBackground(new Color(139, 0, 0));
		txtrYaw.setBounds(87, 122, 28, 22);
		controller.add(txtrYaw);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(Color.DARK_GRAY);
		frame.getContentPane().add(leftPanel, BorderLayout.WEST);
		leftPanel.setPreferredSize(new Dimension(250,0));
		GridBagLayout gbl_leftPanel = new GridBagLayout();
		gbl_leftPanel.columnWidths = new int[]{0, 0};
		gbl_leftPanel.rowHeights = new int[]{0, 0};
		gbl_leftPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_leftPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		leftPanel.setLayout(gbl_leftPanel);
		
		JPanel left_centered = new JPanel();
		left_centered.setBackground(new Color(139, 0, 0));
		GridBagConstraints gbc_left_centered = new GridBagConstraints();
		gbc_left_centered.weighty = 1.0;
		gbc_left_centered.weightx = 1.0;
		gbc_left_centered.gridx = 0;
		gbc_left_centered.gridy = 0;
		left_centered.setPreferredSize(new Dimension(230,600));
		leftPanel.add(left_centered, gbc_left_centered);
		left_centered.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel connection = new JPanel();
		left_centered.add(connection);
		
		motors = new MotorPanel();
		motors.setBackground(new Color(139, 0, 0));
		left_centered.add(motors);
		motors.setLayout(null);
		
		JTextArea txtrLeft = new JTextArea();
		txtrLeft.setFont(new Font("Monospaced", Font.PLAIN, 18));
		txtrLeft.setForeground(Color.LIGHT_GRAY);
		txtrLeft.setBackground(new Color(139, 0, 0));
		txtrLeft.setText("Left");
		txtrLeft.setBounds(10, 128, 48, 36);
		motors.add(txtrLeft);
		
		JTextArea txtrFront = new JTextArea();
		txtrFront.setText("Front");
		txtrFront.setForeground(Color.LIGHT_GRAY);
		txtrFront.setFont(new Font("Monospaced", Font.PLAIN, 18));
		txtrFront.setBackground(new Color(139, 0, 0));
		txtrFront.setBounds(33, 11, 59, 36);
		motors.add(txtrFront);
		
		JTextArea txtrBack = new JTextArea();
		txtrBack.setText("Back");
		txtrBack.setForeground(Color.LIGHT_GRAY);
		txtrBack.setFont(new Font("Monospaced", Font.PLAIN, 18));
		txtrBack.setBackground(new Color(139, 0, 0));
		txtrBack.setBounds(33, 253, 59, 36);
		motors.add(txtrBack);
		
		JTextArea txtrRight = new JTextArea();
		txtrRight.setText("Right");
		txtrRight.setForeground(Color.LIGHT_GRAY);
		txtrRight.setFont(new Font("Monospaced", Font.PLAIN, 18));
		txtrRight.setBackground(new Color(139, 0, 0));
		txtrRight.setBounds(171, 128, 59, 36);
		motors.add(txtrRight);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(Color.DARK_GRAY);
		frame.getContentPane().add(rightPanel, BorderLayout.EAST);
		rightPanel.setPreferredSize(new Dimension(250,0));
		GridBagLayout gbl_rightPanel = new GridBagLayout();
		gbl_rightPanel.columnWidths = new int[]{0, 0};
		gbl_rightPanel.rowHeights = new int[]{0, 0};
		gbl_rightPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_rightPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		rightPanel.setLayout(gbl_rightPanel);
		
		JPanel right_centered = new JPanel();
		right_centered.setBackground(new Color(139, 0, 0));
		GridBagConstraints gbc_right_centered = new GridBagConstraints();
		gbc_right_centered.weighty = 1.0;
		gbc_right_centered.weightx = 1.0;
		gbc_right_centered.gridx = 0;
		gbc_right_centered.gridy = 0;
		right_centered.setPreferredSize(new Dimension(230,600));
		rightPanel.add(right_centered, gbc_right_centered);
		right_centered.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(new Color(192, 192, 192));
		right_centered.add(panel_6);
		panel_6.setLayout(null);
		
		JTextPane voltageLabel = new JTextPane();
		voltageLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		voltageLabel.setForeground(new Color(0, 0, 0));
		voltageLabel.setBackground(new Color(192, 192, 192));
		voltageLabel.setText("Battery Voltage:");
		voltageLabel.setBounds(26, 11, 178, 35);
		panel_6.add(voltageLabel);
		
		txtpnv = new JTextPane();
		txtpnv.setForeground(Color.CYAN);
		txtpnv.setBackground(new Color(0, 0, 0));
		txtpnv.setFont(new Font("Tahoma", Font.PLAIN, 40));
		txtpnv.setText(Data.getVoltage()+"v");
		txtpnv.setBounds(50, 50, 132, 59);
		panel_6.add(txtpnv);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(192, 192, 192));
		right_centered.add(panel_5);
		panel_5.setLayout(null);
		
		JTextArea txtrDepth = new JTextArea();
		txtrDepth.setBackground(Color.LIGHT_GRAY);
		txtrDepth.setText("Depth:");
		txtrDepth.setBounds(10, 11, 70, 22);
		panel_5.add(txtrDepth);
		
		JTextArea txtrSpeed = new JTextArea();
		txtrSpeed.setText("Speed:");
		txtrSpeed.setBackground(Color.LIGHT_GRAY);
		txtrSpeed.setBounds(10, 44, 70, 29);
		panel_5.add(txtrSpeed);
		
		depth = new JTextArea();
		depth.setForeground(Color.WHITE);
		depth.setBackground(Color.DARK_GRAY);
		depth.setBounds(90, 11, 95, 22);
		panel_5.add(depth);
		
		speed = new JTextArea();
		speed.setForeground(Color.WHITE);
		speed.setBackground(Color.DARK_GRAY);
		speed.setBounds(90, 44, 95, 22);
		panel_5.add(speed);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(192, 192, 192));
		right_centered.add(panel_4);
		panel_4.setLayout(null);
		
		JTextArea txtrPressure = new JTextArea();
		txtrPressure.setBounds(10, 11, 70, 22);
		panel_4.add(txtrPressure);
		txtrPressure.setText("Pressure:");
		txtrPressure.setBackground(Color.LIGHT_GRAY);
		
		pressure = new JTextArea();
		pressure.setForeground(Color.WHITE);
		pressure.setBackground(Color.DARK_GRAY);
		pressure.setBounds(123, 11, 97, 22);
		panel_4.add(pressure);
		
		JTextArea txtrTemperature = new JTextArea();
		txtrTemperature.setText("Temperature:");
		txtrTemperature.setBackground(Color.LIGHT_GRAY);
		txtrTemperature.setBounds(10, 46, 106, 29);
		panel_4.add(txtrTemperature);
		
		temperature = new JTextArea();
		temperature.setForeground(Color.WHITE);
		temperature.setBackground(Color.DARK_GRAY);
		temperature.setBounds(123, 46, 97, 22);
		panel_4.add(temperature);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(192, 192, 192));
		right_centered.add(panel_3);
		panel_3.setLayout(null);
		
		JTextArea txtrReminder1 = new JTextArea();
		txtrReminder1.setText("Green = Valve Open");
		txtrReminder1.setBackground(Color.LIGHT_GRAY);
		txtrReminder1.setBounds(10, 11, 166, 22);
		panel_3.add(txtrReminder1);
		
		JTextArea txtrReminder2 = new JTextArea();
		txtrReminder2.setText("Red = Valve Closed");
		txtrReminder2.setBackground(Color.LIGHT_GRAY);
		txtrReminder2.setBounds(10, 44, 166, 22);
		panel_3.add(txtrReminder2);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		right_centered.add(panel);
		panel.setLayout(null);
		
		valve2Sample = new JButton("Sample 2");
		valve2Sample.setBounds(131, 11, 89, 52);
		panel.add(valve2Sample);
		
		JButton valve1Reset = new JButton("Reset 1");
		valve1Reset.setBounds(10, 75, 89, 23);
		panel.add(valve1Reset);
		
		valve1Sample = new JButton("Sample 1");
		valve1Sample.setBackground(Color.GREEN);
		valve1Sample.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		valve1Sample.setBounds(10, 11, 89, 52);
		panel.add(valve1Sample);
		
		JButton valve2Reset = new JButton("Reset 2");
		valve2Reset.setBounds(131, 72, 89, 23);
		panel.add(valve2Reset);

		TextAreaOutputStream taOutputStream = new TextAreaOutputStream(consolePn, "SubControl");
		//System.setOut(new PrintStream(taOutputStream));
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(Color.DARK_GRAY);
		menuBar.setBackground(Color.DARK_GRAY);
		frame.setJMenuBar(menuBar);
		
		JMenu mnSubcontrol = new JMenu("SubControl");
		mnSubcontrol.setForeground(Color.LIGHT_GRAY);
		menuBar.add(mnSubcontrol);
		
		JMenuItem mntmInfo = new JMenuItem("Info");
		mnSubcontrol.add(mntmInfo);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mnSubcontrol.add(mntmQuit);
		
		JMenu mnOption = new JMenu("Option");
		mnOption.setForeground(Color.LIGHT_GRAY);
		menuBar.add(mnOption);
		
		JMenuItem mntmConfigureSub = new JMenuItem("Configure Sub");
		mnOption.add(mntmConfigureSub);
		
		JMenuItem mntmConnectToSub = new JMenuItem("Connect to Sub");
		mnOption.add(mntmConnectToSub);
		
		JMenu mnHelp = new JMenu("Help");
		mnHelp.setForeground(Color.LIGHT_GRAY);
		menuBar.add(mnHelp);
		
		JMenuItem mntmTutorial = new JMenuItem("Tutorial");
		mnHelp.add(mntmTutorial);
		
		JMenu mnAbout = new JMenu("About");
		mnAbout.setForeground(Color.LIGHT_GRAY);
		menuBar.add(mnAbout);
		
		JMenuItem mntmAuthors = new JMenuItem("Authors");
		mnAbout.add(mntmAuthors);
	}
	
	//Methods to refresh each data field, starting from voltage
	public synchronized void refreshVoltage(double v)
	{
		txtpnv.setText(Math.round(10*v)/10.0 +"v");
	}
	
	public synchronized void refreshDepth(double d)
	{
		depth.setText(Math.round(100*d)/100.0 +"m");
	}
	
	public synchronized void refreshSpeed(double s)
	{
		speed.setText(Math.round(100*s)/100.0 +"m/s");
	}
	
	public synchronized void refreshPressure(double p)
	{
		pressure.setText(Math.round(100*p)/100.0 +"psi");
	}
	
	public synchronized void refreshTemperature(double t)
	{
		temperature.setText(Math.round(100*t)/100.0 +"C");
	}
	
	public synchronized void refreshValve1Open(boolean v1)
	{
		if (v1)
			valve1Sample.setBackground(Color.GREEN);
		else 
			valve1Sample.setBackground(Color.RED);
	}
	
	public synchronized void refreshValve2Open(boolean v2)
	{
		if (v2)
			valve2Sample.setBackground(Color.GREEN);
		else 
			valve2Sample.setBackground(Color.RED);
	}
	
	public synchronized void refreshRoll(int r)
	{
		roll.setText(r +" Degrees");
	}
	
	public synchronized void refreshPitch(int p)
	{
		pitch.setText(p +" Degrees");
	}
	public synchronized void initWebcamPanel()
	{
		WebcamPanel cameraPanel = new WebcamPanel(rovStarter.camera.webcam);
		frame.getContentPane().add(cameraPanel, BorderLayout.CENTER);
		frame.setBounds(100, 100, 1241, 915);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cameraPanel.setFPSDisplayed(true);
		cameraPanel.setDisplayDebugInfo(true);
		cameraPanel.setImageSizeDisplayed(true);
		cameraPanel.setMirrored(false);
		cameraPanel.setFitArea(true);
	}
}
