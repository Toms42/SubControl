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
	
	JButton led;
	JButton motor;
	AnglePanel angle;
	ControllerPanel controller;
	MotorPanel motors;
	private JTextField comport;
	
	JTextPane packetsout;
	JTextPane packetsin;
	
	

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
		txtrThrust_1.setBounds(0, 60, 50, 22);
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
		connection.setBackground(new Color(139, 0, 0));
		left_centered.add(connection);
		connection.setLayout(null);
		
		JTextPane txtpnCommsStats = new JTextPane();
		txtpnCommsStats.setBackground(new Color(139, 0, 0));
		txtpnCommsStats.setForeground(Color.LIGHT_GRAY);
		txtpnCommsStats.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtpnCommsStats.setText("Comms Stats");
		txtpnCommsStats.setBounds(51, 11, 123, 35);
		connection.add(txtpnCommsStats);
		
		JTextPane txtpnPort = new JTextPane();
		txtpnPort.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtpnPort.setForeground(Color.LIGHT_GRAY);
		txtpnPort.setBackground(new Color(139, 0, 0));
		txtpnPort.setText("Port:");
		txtpnPort.setBounds(10, 54, 44, 35);
		connection.add(txtpnPort);
		
		comport = new JTextField();
		comport.setText(Config.COMS_PORT);
		comport.setBounds(61, 54, 74, 23);
		connection.add(comport);
		comport.setColumns(10);
		
		JButton refresh = new JButton("connect");
		refresh.setFont(new Font("Tahoma", Font.PLAIN, 11));
		refresh.setBounds(145, 54, 75, 23);
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rovStarter.coms.close();
				Config.COMS_PORT=comport.getText();
				System.out.println("Refresh pressed!\tPort = " + Config.COMS_PORT);
				rovStarter.coms.init();
			}
		});
		connection.add(refresh);
		
		JTextPane txtpnPacketsSent = new JTextPane();
		txtpnPacketsSent.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtpnPacketsSent.setForeground(Color.LIGHT_GRAY);
		txtpnPacketsSent.setBackground(new Color(139, 0, 0));
		txtpnPacketsSent.setText("packets sent:");
		txtpnPacketsSent.setBounds(10, 114, 85, 23);
		connection.add(txtpnPacketsSent);
		
		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textPane.setForeground(Color.LIGHT_GRAY);
		textPane.setBackground(new Color(139, 0, 0));
		textPane.setText("packets sent:");
		textPane.setBounds(10, 148, 85, 23);
		connection.add(textPane);
		
		packetsout = new JTextPane();
		packetsout.setFont(new Font("Tahoma", Font.PLAIN, 12));
		packetsout.setBackground(Color.DARK_GRAY);
		packetsout.setForeground(new Color(255, 69, 0));
		packetsout.setText("0");
		packetsout.setBounds(105, 114, 102, 23);
		connection.add(packetsout);
		
		packetsin = new JTextPane();
		packetsin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		packetsin.setBackground(Color.DARK_GRAY);
		packetsin.setForeground(new Color(255, 69, 0));
		packetsin.setText("0");
		packetsin.setBounds(105, 148, 102, 23);
		connection.add(packetsin);
		
		JTextPane txtpnSignalQualityAvg = new JTextPane();
		txtpnSignalQualityAvg.setForeground(Color.LIGHT_GRAY);
		txtpnSignalQualityAvg.setBackground(new Color(139, 0, 0));
		txtpnSignalQualityAvg.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtpnSignalQualityAvg.setText("Signal Quality Avg:");
		txtpnSignalQualityAvg.setBounds(35, 182, 172, 35);
		connection.add(txtpnSignalQualityAvg);
		
		JTextPane signalQual = new JTextPane();
		signalQual.setForeground(new Color(255, 69, 0));
		signalQual.setBackground(Color.DARK_GRAY);
		signalQual.setFont(new Font("Tahoma", Font.PLAIN, 21));
		signalQual.setText("0");
		signalQual.setBounds(79, 216, 67, 35);
		connection.add(signalQual);
		
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
		
		JTextArea txtrCurrent = new JTextArea();
		txtrCurrent.setText("Current:");
		txtrCurrent.setBackground(Color.LIGHT_GRAY);
		txtrCurrent.setBounds(10, 84, 70, 22);
		panel_5.add(txtrCurrent);
		
		JTextArea currentAll = new JTextArea();
		currentAll.setForeground(Color.WHITE);
		currentAll.setBackground(Color.DARK_GRAY);
		currentAll.setBounds(90, 84, 97, 22);
		panel_5.add(currentAll);
		
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
		txtrTemperature.setBounds(10, 44, 106, 29);
		panel_4.add(txtrTemperature);
		
		temperature = new JTextArea();
		temperature.setForeground(Color.WHITE);
		temperature.setBackground(Color.DARK_GRAY);
		temperature.setBounds(123, 44, 97, 22);
		panel_4.add(temperature);
		
		JTextArea txtrCurrent_1 = new JTextArea();
		txtrCurrent_1.setText("Current 1:");
		txtrCurrent_1.setBackground(Color.LIGHT_GRAY);
		txtrCurrent_1.setBounds(10, 98, 84, 22);
		panel_4.add(txtrCurrent_1);
		
		JTextArea current1 = new JTextArea();
		current1.setForeground(Color.WHITE);
		current1.setBackground(Color.DARK_GRAY);
		current1.setBounds(123, 98, 97, 22);
		panel_4.add(current1);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(192, 192, 192));
		right_centered.add(panel_3);
		panel_3.setLayout(null);
		
		JTextArea txtrCurrent_2 = new JTextArea();
		txtrCurrent_2.setBounds(10, 11, 89, 22);
		panel_3.add(txtrCurrent_2);
		txtrCurrent_2.setText("Current 2:");
		txtrCurrent_2.setBackground(Color.LIGHT_GRAY);
		
		JTextArea txtrCurrent_3 = new JTextArea();
		txtrCurrent_3.setText("Current 3:");
		txtrCurrent_3.setBackground(Color.LIGHT_GRAY);
		txtrCurrent_3.setBounds(10, 44, 89, 22);
		panel_3.add(txtrCurrent_3);
		
		JTextArea txtrCurrent_4 = new JTextArea();
		txtrCurrent_4.setText("Current 4:");
		txtrCurrent_4.setBackground(Color.LIGHT_GRAY);
		txtrCurrent_4.setBounds(10, 72, 89, 22);
		panel_3.add(txtrCurrent_4);
		
		JTextArea current2 = new JTextArea();
		current2.setForeground(Color.WHITE);
		current2.setBackground(Color.DARK_GRAY);
		current2.setBounds(123, 11, 97, 22);
		panel_3.add(current2);
		
		JTextArea current3 = new JTextArea();
		current3.setForeground(Color.WHITE);
		current3.setBackground(Color.DARK_GRAY);
		current3.setBounds(123, 44, 97, 22);
		panel_3.add(current3);
		
		JTextArea current4 = new JTextArea();
		current4.setForeground(Color.WHITE);
		current4.setBackground(Color.DARK_GRAY);
		current4.setBounds(123, 72, 97, 22);
		panel_3.add(current4);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		right_centered.add(panel);
		panel.setLayout(null);
		
		motor = new JButton("Motors");
		motor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Data.setMotors(!Data.getMotors());
				if(Data.getMotors())
				{
					System.out.println("Motors turned on!");
				}
				else
				{
					System.out.println("Motors turned off!");
				}
			}
		});
		motor.setBackground(Color.RED);
		motor.setBounds(131, 11, 89, 82);
		panel.add(motor);
		
		led = new JButton("LED's");
		led.setBackground(Color.RED);
		led.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Data.setLed(!Data.getLed());
				
				if(Data.getLed())
				{
					System.out.println("LED's turned on!");
				}
				else
				{
					System.out.println("LED's turned off!");
				}
			}
		});
		
		led.setBounds(10, 11, 89, 82);
		panel.add(led);

		TextAreaOutputStream taOutputStream = new TextAreaOutputStream(consolePn, "SubControl");
		System.setOut(new PrintStream(taOutputStream));
		
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
	
	public synchronized void refreshLed(boolean leds)
	{
		if (leds)
			led.setBackground(Color.GREEN);
		else 
			led.setBackground(Color.RED);
	}
	
	public synchronized void refreshMotor(boolean motors)
	{
		if (motors)
			motor.setBackground(Color.GREEN);
		else 
			motor.setBackground(Color.RED);
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
