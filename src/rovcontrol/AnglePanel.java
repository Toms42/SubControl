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
import java.awt.event.ActionEvent;

public class AnglePanel extends JPanel {
	
	private final double circleX1 = 50;
	private final double circleY1 = 20;
	private final double circleX2 = 200;
	private final double circleY2 = 20;
	private final double outerDiameter = 100 ;
	private final double outerRadius = 50;
	
	private double roll, pitch;
	private final double centerX1 = 100;
	private final double centerY1 = 70;
	private final double centerX2 = 250;
	private final double centerY2 = 70;
	Graphics2D angleImage;

    public AnglePanel() {
    	roll = 0;
    	pitch = 0;
    }

    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        angleImage = (Graphics2D) g;  
        angleImage.setColor(Color.BLACK);
        angleImage.setPaint(Color.GRAY);
        final float dash1[] = {3.0f};
        final BasicStroke dashed = new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
        
        angleImage.setColor(Color.GRAY);
        angleImage.fill(new Ellipse2D.Double(circleX1, circleY1, outerDiameter, outerDiameter));
        angleImage.fill(new Ellipse2D.Double(circleX2, circleY2, outerDiameter, outerDiameter));
        
        angleImage.setColor(Color.DARK_GRAY);
        angleImage.setStroke(dashed);
        angleImage.draw(new Line2D.Double(this.centerX1 - 50, this.centerY1, this.centerX1 + 50, this.centerY1));
        angleImage.draw(new Line2D.Double(this.centerX2 - 50, this.centerY2, this.centerX2 + 50, this.centerY2));

        
        
        final BasicStroke solid = new BasicStroke();
        angleImage.setStroke(solid);
        angleImage.setColor(Color.CYAN);
        this.drawRoll();
        angleImage.setColor(Color.YELLOW);
        this.drawPitch();
        
    }

    public void updateGraphics(int roll, int pitch) 
    {
        this.roll = (double) roll;
        this.pitch = (double) pitch;
        repaint();
    }
    
    private static double getRadian(double degree)
    {
    	double radian;
    	radian = degree / 180.0 * Math.PI;
    	return radian;
    }
    
    private void drawRoll()
    {
    	double radian;
    	radian = getRadian(this.roll);
    	double x1, y1, x2, y2;
    	x1 = this.centerX2 - this.outerRadius * Math.cos(radian);
    	y1 = this.centerY2 + this.outerRadius * Math.sin(radian);
    	x2 = this.centerX2 + this.outerRadius * Math.cos(radian);
    	y2 = this.centerY2 - this.outerRadius * Math.sin(radian);
    	angleImage.setStroke(new BasicStroke(4.0f));
    	angleImage.setColor(Color.RED);
    	angleImage.draw(new Line2D.Double(x1, y1, this.centerX2, centerY2));
    	angleImage.setColor(Color.GREEN);
    	angleImage.draw(new Line2D.Double(this.centerX2, centerY2, x2, y2));
    }
    
    private void drawPitch()
    {
    	double radian;
    	radian = getRadian(this.pitch);
    	double x1, y1, x2, y2;
    	x1 = this.centerX1 - this.outerRadius* Math.cos(radian);
    	y1 = this.centerY1 - this.outerRadius* Math.sin(radian);
    	x2 = this.centerX1;
    	y2 = this.centerY1;
    	angleImage.setStroke(new BasicStroke(4.0f));
    	angleImage.draw(new Line2D.Double(x1, y1, x2, y2));
    }
    
}
