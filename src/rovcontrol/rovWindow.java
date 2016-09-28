package rovcontrol;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JTextPane;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.io.PrintStream;


import rovcontrol.TextAreaOutputStream;

import javax.swing.JPanel;
import com.github.sarxos.webcam.WebcamPanel;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.WindowConstants;

import java.awt.Toolkit;

public class rovWindow {

	JFrame frmSubControl;
	
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
	public JTextField comport;
	
	JTextPane packetsout;
	JTextPane packetsin;
	JTextPane txtpnDisconnected;
	
	JTextArea currentAll;
	JTextArea current1;
	JTextArea current2;
	JTextArea current3;
	JTextArea current4;
	

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
		frmSubControl = new JFrame();
		frmSubControl.setIconImage(Toolkit.getDefaultToolkit().getImage("Submarine-icon.png"));
		frmSubControl.setForeground(new Color(139, 0, 0));
		frmSubControl.setTitle("Sub Control 2.0");
		frmSubControl.setAlwaysOnTop(true);
		frmSubControl.setBackground(Color.BLACK);
		frmSubControl.getContentPane().setBackground(Color.DARK_GRAY);
		frmSubControl.setMinimumSize(new Dimension(1020,861));
		frmSubControl.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//Setting up each individual panel
		
		try {
			initWebcamPanel();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("webcam cannot be null. Leaving panel empty.");
		}
		
		JPanel lowerPanel = new JPanel();
		lowerPanel.setBackground(Color.DARK_GRAY);
		frmSubControl.getContentPane().add(lowerPanel, BorderLayout.SOUTH);
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
		txtrPitch.setEditable(false);
		txtrPitch.setFont(new Font("Monospaced", Font.PLAIN, 16));
		txtrPitch.setForeground(Color.LIGHT_GRAY);
		txtrPitch.setText("PITCH:");
		txtrPitch.setBackground(new Color(139, 0, 0));
		txtrPitch.setBounds(50, 130, 100, 30);
		angle.add(txtrPitch);
		
		JTextArea txtrRoll = new JTextArea();
		txtrRoll.setEditable(false);
		txtrRoll.setFont(new Font("Monospaced", Font.PLAIN, 16));
		txtrRoll.setText("ROLL:");
		txtrRoll.setForeground(Color.LIGHT_GRAY);
		txtrRoll.setBackground(new Color(139, 0, 0));
		txtrRoll.setBounds(200, 130, 100, 30);
		angle.add(txtrRoll);
		
		pitch = new JTextArea();
		pitch.setEditable(false);
		pitch.setForeground(new Color(255, 69, 0));
		pitch.setBackground(Color.DARK_GRAY);
		pitch.setBounds(50, 160, 100, 22);
		angle.add(pitch);
		
		roll = new JTextArea();
		roll.setEditable(false);
		roll.setForeground(new Color(255, 69, 0));
		roll.setBackground(Color.DARK_GRAY);
		roll.setBounds(200, 160, 100, 22);
		angle.add(roll);
		
		controller = new ControllerPanel();
		controller.setBackground(new Color(139, 0, 0));
		controller.setBounds(666, 0, 333, 200);
		lower_centered.add(controller);
		controller.setLayout(null);
		
		JTextArea txtrThrust = new JTextArea();
		txtrThrust.setEditable(false);
		txtrThrust.setForeground(Color.LIGHT_GRAY);
		txtrThrust.setBackground(new Color(139, 0, 0));
		txtrThrust.setText("Pitch");
		txtrThrust.setBounds(151, 60, 44, 22);
		controller.add(txtrThrust);
		
		JTextArea txtrThrust_1 = new JTextArea();
		txtrThrust_1.setEditable(false);
		txtrThrust_1.setForeground(Color.LIGHT_GRAY);
		txtrThrust_1.setBackground(new Color(139, 0, 0));
		txtrThrust_1.setText("Thrust");
		txtrThrust_1.setBounds(0, 60, 50, 22);
		controller.add(txtrThrust_1);
		
		JTextArea txtrAscension = new JTextArea();
		txtrAscension.setEditable(false);
		txtrAscension.setText("Ascension");
		txtrAscension.setForeground(Color.LIGHT_GRAY);
		txtrAscension.setBackground(new Color(139, 0, 0));
		txtrAscension.setBounds(52, 167, 97, 22);
		controller.add(txtrAscension);
		
		JTextArea txtrYaw = new JTextArea();
		txtrYaw.setEditable(false);
		txtrYaw.setText("Yaw");
		txtrYaw.setForeground(Color.LIGHT_GRAY);
		txtrYaw.setBackground(new Color(139, 0, 0));
		txtrYaw.setBounds(87, 122, 28, 22);
		controller.add(txtrYaw);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(Color.DARK_GRAY);
		frmSubControl.getContentPane().add(leftPanel, BorderLayout.WEST);
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
		txtpnCommsStats.setEditable(false);
		txtpnCommsStats.setBackground(new Color(139, 0, 0));
		txtpnCommsStats.setForeground(Color.LIGHT_GRAY);
		txtpnCommsStats.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtpnCommsStats.setText("Comms Stats");
		txtpnCommsStats.setBounds(51, 11, 123, 35);
		connection.add(txtpnCommsStats);
		
		JTextPane txtpnPort = new JTextPane();
		txtpnPort.setEditable(false);
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
		
		JButton refresh = new JButton("Connect");
		refresh.setBackground(new Color(50, 205, 50));
		refresh.setFont(new Font("Tahoma", Font.PLAIN, 11));
		refresh.setBounds(145, 54, 75, 23);
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rovStarter.coms.close();
				Config.COMS_PORT=comport.getText();
				Config.COMS_PORT=Config.COMS_PORT.toUpperCase();
				comport.setText(Config.COMS_PORT);
				rovStarter.coms.PORT_NAMES[0]=Config.COMS_PORT;
				System.out.println("Refresh pressed!\tPort = " + Config.COMS_PORT);
				rovStarter.coms.init();
			}
		});
		connection.add(refresh);
		
		JTextPane txtpnPacketsSent = new JTextPane();
		txtpnPacketsSent.setEditable(false);
		txtpnPacketsSent.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtpnPacketsSent.setForeground(Color.LIGHT_GRAY);
		txtpnPacketsSent.setBackground(new Color(139, 0, 0));
		txtpnPacketsSent.setText("packets sent:");
		txtpnPacketsSent.setBounds(10, 114, 85, 23);
		connection.add(txtpnPacketsSent);
		
		JTextPane txtpnPacketsReceived = new JTextPane();
		txtpnPacketsReceived.setEditable(false);
		txtpnPacketsReceived.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtpnPacketsReceived.setForeground(Color.LIGHT_GRAY);
		txtpnPacketsReceived.setBackground(new Color(139, 0, 0));
		txtpnPacketsReceived.setText("packets received:");
		txtpnPacketsReceived.setBounds(10, 148, 102, 23);
		connection.add(txtpnPacketsReceived);
		
		packetsout = new JTextPane();
		packetsout.setEditable(false);
		packetsout.setFont(new Font("Tahoma", Font.PLAIN, 12));
		packetsout.setBackground(Color.DARK_GRAY);
		packetsout.setForeground(new Color(255, 69, 0));
		packetsout.setText("0");
		packetsout.setBounds(118, 114, 102, 23);
		connection.add(packetsout);
		
		packetsin = new JTextPane();
		packetsin.setEditable(false);
		packetsin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		packetsin.setBackground(Color.DARK_GRAY);
		packetsin.setForeground(new Color(255, 69, 0));
		packetsin.setText("0");
		packetsin.setBounds(118, 148, 102, 23);
		connection.add(packetsin);
		
		JTextPane txtpnSignalQualityAvg = new JTextPane();
		txtpnSignalQualityAvg.setEditable(false);
		txtpnSignalQualityAvg.setForeground(Color.LIGHT_GRAY);
		txtpnSignalQualityAvg.setBackground(new Color(139, 0, 0));
		txtpnSignalQualityAvg.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtpnSignalQualityAvg.setText("Connection Status:");
		txtpnSignalQualityAvg.setBounds(35, 182, 172, 29);
		connection.add(txtpnSignalQualityAvg);
		
		JButton disconnect = new JButton("Stop");
		disconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rovStarter.coms.close();
			}
		});
		disconnect.setBackground(new Color(255, 0, 0));
		disconnect.setBounds(145, 80, 75, 23);
		connection.add(disconnect);
		
		txtpnDisconnected = new JTextPane();
		txtpnDisconnected.setEditable(false);
		txtpnDisconnected.setText("DISCONNECTED");
		txtpnDisconnected.setForeground(new Color(255, 69, 0));
		txtpnDisconnected.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtpnDisconnected.setBackground(Color.DARK_GRAY);
		txtpnDisconnected.setBounds(45, 209, 134, 29);
		connection.add(txtpnDisconnected);
		
		JButton search = new JButton("Search");
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rovStarter.coms.getPortsList();
			}
		});
		search.setFont(new Font("Tahoma", Font.PLAIN, 11));
		search.setBackground(new Color(0, 255, 255));
		search.setBounds(60, 80, 75, 23);
		connection.add(search);
		
		motors = new MotorPanel();
		motors.setBackground(new Color(139, 0, 0));
		left_centered.add(motors);
		motors.setLayout(null);
		
		JTextArea txtrLeft = new JTextArea();
		txtrLeft.setEditable(false);
		txtrLeft.setFont(new Font("Monospaced", Font.PLAIN, 18));
		txtrLeft.setForeground(Color.LIGHT_GRAY);
		txtrLeft.setBackground(new Color(139, 0, 0));
		txtrLeft.setText("Left");
		txtrLeft.setBounds(10, 128, 48, 36);
		motors.add(txtrLeft);
		
		JTextArea txtrFront = new JTextArea();
		txtrFront.setEditable(false);
		txtrFront.setText("Front");
		txtrFront.setForeground(Color.LIGHT_GRAY);
		txtrFront.setFont(new Font("Monospaced", Font.PLAIN, 18));
		txtrFront.setBackground(new Color(139, 0, 0));
		txtrFront.setBounds(33, 11, 59, 36);
		motors.add(txtrFront);
		
		JTextArea txtrBack = new JTextArea();
		txtrBack.setEditable(false);
		txtrBack.setText("Back");
		txtrBack.setForeground(Color.LIGHT_GRAY);
		txtrBack.setFont(new Font("Monospaced", Font.PLAIN, 18));
		txtrBack.setBackground(new Color(139, 0, 0));
		txtrBack.setBounds(33, 253, 59, 36);
		motors.add(txtrBack);
		
		JTextArea txtrRight = new JTextArea();
		txtrRight.setEditable(false);
		txtrRight.setText("Right");
		txtrRight.setForeground(Color.LIGHT_GRAY);
		txtrRight.setFont(new Font("Monospaced", Font.PLAIN, 18));
		txtrRight.setBackground(new Color(139, 0, 0));
		txtrRight.setBounds(171, 128, 59, 36);
		motors.add(txtrRight);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(Color.DARK_GRAY);
		frmSubControl.getContentPane().add(rightPanel, BorderLayout.EAST);
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
		panel_6.setBackground(new Color(139, 0, 0));
		right_centered.add(panel_6);
		panel_6.setLayout(null);
		
		JTextPane voltageLabel = new JTextPane();
		voltageLabel.setEditable(false);
		voltageLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		voltageLabel.setForeground(Color.LIGHT_GRAY);
		voltageLabel.setBackground(new Color(139, 0, 0));
		voltageLabel.setText("Battery Voltage:");
		voltageLabel.setBounds(26, 11, 178, 35);
		panel_6.add(voltageLabel);
		
		txtpnv = new JTextPane();
		txtpnv.setEditable(false);
		txtpnv.setForeground(Color.CYAN);
		txtpnv.setBackground(new Color(0, 0, 0));
		txtpnv.setFont(new Font("Tahoma", Font.PLAIN, 40));
		txtpnv.setText(Data.getVoltage()+"v");
		txtpnv.setBounds(50, 50, 132, 59);
		panel_6.add(txtpnv);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(139, 0, 0));
		right_centered.add(panel_5);
		panel_5.setLayout(null);
		
		JTextArea txtrDepth = new JTextArea();
		txtrDepth.setForeground(Color.LIGHT_GRAY);
		txtrDepth.setEditable(false);
		txtrDepth.setBackground(new Color(139, 0, 0));
		txtrDepth.setText("Depth:");
		txtrDepth.setBounds(10, 11, 70, 22);
		panel_5.add(txtrDepth);
		
		JTextArea txtrSpeed = new JTextArea();
		txtrSpeed.setForeground(Color.LIGHT_GRAY);
		txtrSpeed.setEditable(false);
		txtrSpeed.setText("Speed:");
		txtrSpeed.setBackground(new Color(139, 0, 0));
		txtrSpeed.setBounds(10, 44, 70, 29);
		panel_5.add(txtrSpeed);
		
		depth = new JTextArea();
		depth.setEditable(false);
		depth.setForeground(new Color(255, 69, 0));
		depth.setBackground(Color.DARK_GRAY);
		depth.setBounds(90, 11, 95, 22);
		panel_5.add(depth);
		
		speed = new JTextArea();
		speed.setEditable(false);
		speed.setForeground(new Color(255, 69, 0));
		speed.setBackground(Color.DARK_GRAY);
		speed.setBounds(90, 44, 95, 22);
		panel_5.add(speed);
		
		JTextArea txtrCurrent = new JTextArea();
		txtrCurrent.setForeground(Color.LIGHT_GRAY);
		txtrCurrent.setEditable(false);
		txtrCurrent.setText("Current:");
		txtrCurrent.setBackground(new Color(139, 0, 0));
		txtrCurrent.setBounds(10, 84, 70, 22);
		panel_5.add(txtrCurrent);
		
		currentAll = new JTextArea();
		currentAll.setEditable(false);
		currentAll.setForeground(new Color(255, 69, 0));
		currentAll.setBackground(Color.DARK_GRAY);
		currentAll.setBounds(90, 84, 97, 22);
		panel_5.add(currentAll);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(139, 0, 0));
		right_centered.add(panel_4);
		panel_4.setLayout(null);
		
		JTextArea txtrPressure = new JTextArea();
		txtrPressure.setForeground(Color.LIGHT_GRAY);
		txtrPressure.setEditable(false);
		txtrPressure.setBounds(10, 11, 70, 22);
		panel_4.add(txtrPressure);
		txtrPressure.setText("Pressure:");
		txtrPressure.setBackground(new Color(139, 0, 0));
		
		pressure = new JTextArea();
		pressure.setEditable(false);
		pressure.setForeground(new Color(255, 69, 0));
		pressure.setBackground(Color.DARK_GRAY);
		pressure.setBounds(123, 11, 97, 22);
		panel_4.add(pressure);
		
		JTextArea txtrTemperature = new JTextArea();
		txtrTemperature.setForeground(Color.LIGHT_GRAY);
		txtrTemperature.setEditable(false);
		txtrTemperature.setText("Temperature:");
		txtrTemperature.setBackground(new Color(139, 0, 0));
		txtrTemperature.setBounds(10, 44, 106, 29);
		panel_4.add(txtrTemperature);
		
		temperature = new JTextArea();
		temperature.setForeground(new Color(255, 69, 0));
		temperature.setBackground(Color.DARK_GRAY);
		temperature.setBounds(123, 44, 97, 22);
		panel_4.add(temperature);
		
		JTextArea txtrCurrent_1 = new JTextArea();
		txtrCurrent_1.setForeground(Color.LIGHT_GRAY);
		txtrCurrent_1.setEditable(false);
		txtrCurrent_1.setText("Current 1:");
		txtrCurrent_1.setBackground(new Color(139, 0, 0));
		txtrCurrent_1.setBounds(10, 98, 84, 22);
		panel_4.add(txtrCurrent_1);
		
		current1 = new JTextArea();
		current1.setEditable(false);
		current1.setForeground(new Color(255, 69, 0));
		current1.setBackground(Color.DARK_GRAY);
		current1.setBounds(123, 98, 97, 22);
		panel_4.add(current1);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(139, 0, 0));
		right_centered.add(panel_3);
		panel_3.setLayout(null);
		
		JTextArea txtrCurrent_2 = new JTextArea();
		txtrCurrent_2.setForeground(Color.LIGHT_GRAY);
		txtrCurrent_2.setEditable(false);
		txtrCurrent_2.setBounds(10, 11, 89, 22);
		panel_3.add(txtrCurrent_2);
		txtrCurrent_2.setText("Current 2:");
		txtrCurrent_2.setBackground(new Color(139, 0, 0));
		
		JTextArea txtrCurrent_3 = new JTextArea();
		txtrCurrent_3.setForeground(Color.LIGHT_GRAY);
		txtrCurrent_3.setEditable(false);
		txtrCurrent_3.setText("Current 3:");
		txtrCurrent_3.setBackground(new Color(139, 0, 0));
		txtrCurrent_3.setBounds(10, 42, 89, 22);
		panel_3.add(txtrCurrent_3);
		
		JTextArea txtrCurrent_4 = new JTextArea();
		txtrCurrent_4.setForeground(Color.LIGHT_GRAY);
		txtrCurrent_4.setEditable(false);
		txtrCurrent_4.setText("Current 4:");
		txtrCurrent_4.setBackground(new Color(139, 0, 0));
		txtrCurrent_4.setBounds(10, 72, 89, 22);
		panel_3.add(txtrCurrent_4);
		
		current2 = new JTextArea();
		current2.setEditable(false);
		current2.setForeground(new Color(255, 69, 0));
		current2.setBackground(Color.DARK_GRAY);
		current2.setBounds(123, 11, 97, 22);
		panel_3.add(current2);
		
		current3 = new JTextArea();
		current3.setEditable(false);
		current3.setForeground(new Color(255, 69, 0));
		current3.setBackground(Color.DARK_GRAY);
		current3.setBounds(123, 42, 97, 22);
		panel_3.add(current3);
		
		current4 = new JTextArea();
		current4.setEditable(false);
		current4.setForeground(new Color(255, 69, 0));
		current4.setBackground(Color.DARK_GRAY);
		current4.setBounds(123, 72, 97, 22);
		panel_3.add(current4);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(139, 0, 0));
		right_centered.add(panel);
		panel.setLayout(null);
		
		motor = new JButton("Motors");
		motor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Data.setMotors(!Data.getMotors());
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
			}
		});
		
		led.setBounds(10, 11, 89, 82);
		panel.add(led);

		TextAreaOutputStream taOutputStream = new TextAreaOutputStream(consolePn, "SubControl");
		System.setOut(new PrintStream(taOutputStream));
	}
	
	//Methods to refresh each data field, starting from voltage
	public synchronized void refreshVoltage(double v)
	{
		txtpnv.setText(Math.round(100*v)/100.0 +"v");
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
		frmSubControl.getContentPane().add(cameraPanel, BorderLayout.CENTER);
		frmSubControl.setBounds(100, 100, 1241, 915);
		frmSubControl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cameraPanel.setFPSDisplayed(true);
		cameraPanel.setDisplayDebugInfo(true);
		cameraPanel.setImageSizeDisplayed(true);
		cameraPanel.setMirrored(false);
		cameraPanel.setFitArea(true);
	}
	
	public synchronized void refreshConnection(String s)
	{
		txtpnDisconnected.setText(s);
	}
	
	public synchronized void updateCurrent(float c1, float c2, float c3, float c4, float ctot)
	{
		currentAll.setText(""+ctot);
		current1.setText(""+c1);
		current2.setText(""+c2);
		current3.setText(""+c3);
		current4.setText(""+c4);
	}
	
	public synchronized void refreshNumPackets(long in, long out)
	{
		packetsin.setText(""+in);
		packetsout.setText(""+out);
	}
}
