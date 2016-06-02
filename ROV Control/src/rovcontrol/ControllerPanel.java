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

public class ControllerPanel extends JPanel {
	
	private final double squareX1 = 50;
	private final double squareX2 = 200;
	private final double squareY1 = 20;
	private final double squareY2 = 20;
	private final double sideLength = 100;
	private final double railXCenter = 175;
	private final double railY = 150;
	private final double railWidth = 15;
	private double leftX;
	private double leftY;
	private double rightX;
	private double rightY;
	private double ltRt;
	
	
	Graphics2D controllerImage;

    public ControllerPanel() {
    	
    }

    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        controllerImage = (Graphics2D) g;  
        
        controllerImage.setColor(Color.BLACK);
        controllerImage.setPaint(Color.GRAY);
        controllerImage.fill(new Rectangle2D.Double(squareX1, squareY1, sideLength, sideLength));
        controllerImage.fill(new Rectangle2D.Double(squareX2, squareY2, sideLength, sideLength));
        
        
        final float dash1[] = {3.0f};
        final BasicStroke dashed = new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
        controllerImage.setStroke(dashed);
        controllerImage.setColor(Color.DARK_GRAY);
        controllerImage.draw(new Line2D.Double(this.squareX1, this.squareY1 + sideLength/2, this.squareX1 + sideLength, this.squareY1 + sideLength/2));
        controllerImage.draw(new Line2D.Double(this.squareX2, this.squareY2 + sideLength/2, this.squareX2 + sideLength, this.squareY2 + sideLength/2));
        controllerImage.draw(new Line2D.Double(this.squareX1 + sideLength/2, this.squareY1, this.squareX1 + sideLength/2, this.squareY1 + sideLength));
        controllerImage.draw(new Line2D.Double(this.squareX2 + sideLength/2, this.squareY2, this.squareX2 + sideLength/2, this.squareY2 + sideLength));
        drawLeftStick();
        drawRightStick();
        drawLtRt();
        
    }

    public void updateGraphics(int lx, int ly, int rx, int ry, int ltRt) 
    {
        this.leftX = lx;
        this.leftY = ly;
        this.rightX = rx;
        this.rightY = ry;
        this.ltRt = ltRt;
        repaint();
    }
   
    
    private void drawLeftStick()
    {
    	double x1, y1;
    	x1 = this.squareX1 + this.sideLength * this.leftX / 100 ;
    	y1 = this.squareY1 + this.sideLength * this.leftY / 100 ;
    	
    	controllerImage.setStroke(new BasicStroke(3.0f));
    	controllerImage.setPaint(Color.RED);
    	controllerImage.fill(new Ellipse2D.Double(x1 - 5, y1 - 5,10, 10));
    }
    
    private void drawRightStick()
    {
    	double x1, y1;
    	x1 = this.squareX2 + this.sideLength * this.rightX / 100 ;
    	y1 = this.squareY2 + this.sideLength * this.rightY / 100 ;
    	
    	controllerImage.setStroke(new BasicStroke(3.0f));
    	controllerImage.setPaint(Color.RED);
    	controllerImage.fill(new Ellipse2D.Double(x1 - 5, y1 - 5, 10,10));
    }
    
    private void drawLtRt()
    {
    	double railLength;
    	railLength = (this.ltRt - 50) * 2.5; 
    	controllerImage.setPaint(Color.RED);
    	if (railLength >= 0)
    		controllerImage.fill(new Rectangle2D.Double(this.railXCenter, this.railY, railLength, this.railWidth));
    	else 
    		controllerImage.fill(new Rectangle2D.Double(this.railXCenter - Math.abs(railLength), this.railY, Math.abs(railLength), this.railWidth));
    }
    
}
