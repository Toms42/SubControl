package rovcontrol;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JTextPane;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Shape;
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
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.event.ActionEvent;

public class MotorPanel extends JPanel {
	
	final static int railWidth = 20;
	final static int leftMotorX = 60;
	final static int frontMotorX = leftMotorX + railWidth*2;
	final static int backMotorX = frontMotorX;
	final static int rightMotorX = frontMotorX + railWidth*2;
	final static int leftCenterY = 150;
	final static int frontCenterY = 75;
	final static int backCenterY = 225;
	final static int rightCenterY = 150;
	private static int leftPower;
	private static int rightPower;
	private static int frontPower;
	private static int backPower;

	
	Graphics2D motorImage;

    public MotorPanel() {
    	
    }

    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        motorImage = (Graphics2D) g;  
        motorImage.setColor(Color.BLACK);
        motorImage.draw(new Rectangle2D.Double((double) leftMotorX, leftCenterY - 75, railWidth, 150));
        motorImage.draw(new Rectangle2D.Double((double) rightMotorX, rightCenterY - 75, railWidth, 150));
        motorImage.draw(new Rectangle2D.Double((double) frontMotorX, frontCenterY - 75, railWidth, 150));
        motorImage.draw(new Rectangle2D.Double((double) backMotorX, backCenterY - 75, railWidth, 150));
        
        drawLeftMotorPower();
        drawRightMotorPower();
        drawFrontMotorPower();
        drawBackMotorPower();
        
    }

    public void updateGraphics(int lP, int rP, int fP, int bP) 
    {
        leftPower = lP;
        rightPower = rP;
        frontPower = fP;
        backPower = bP;
        repaint();
    }
    
    private void drawLeftMotorPower()
    {
    	double railLength;
    	railLength = (double) (leftPower * 75 / 50);
    	motorImage.setPaint(Color.RED);
    	if (railLength >= 0)
    		motorImage.fill(new Rectangle2D.Double(leftMotorX, leftCenterY - railLength, railWidth, railLength));
    	else 
    		motorImage.fill(new Rectangle2D.Double(leftMotorX, leftCenterY, railWidth, Math.abs(railLength)));
    }
    
    private void drawRightMotorPower()
    {
    	double railLength;
    	railLength = (double) (rightPower * 75 / 50);
    	motorImage.setPaint(Color.RED);
    	if (railLength >= 0)
    		motorImage.fill(new Rectangle2D.Double(rightMotorX, rightCenterY - railLength, railWidth, railLength));
    	else 
    		motorImage.fill(new Rectangle2D.Double(rightMotorX, rightCenterY, railWidth, Math.abs(railLength)));
    }
    
    private void drawFrontMotorPower()
    {
    	double railLength;
    	railLength = (double) (frontPower * 75 / 50);
    	motorImage.setPaint(Color.RED);
    	if (railLength >= 0)
    		motorImage.fill(new Rectangle2D.Double(frontMotorX, frontCenterY - railLength, railWidth, railLength));
    	else 
    		motorImage.fill(new Rectangle2D.Double(frontMotorX, frontCenterY, railWidth, Math.abs(railLength)));
    }
    
    private void drawBackMotorPower()
    {
    	double railLength;
    	railLength = (double) (backPower * 75 / 50);
    	motorImage.setPaint(Color.RED);
    	if (railLength >= 0)
    		motorImage.fill(new Rectangle2D.Double(backMotorX, backCenterY - railLength, railWidth, railLength));
    	else 
    		motorImage.fill(new Rectangle2D.Double(backMotorX, backCenterY, railWidth, Math.abs(railLength)));
    }
    
}

