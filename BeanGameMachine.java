import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BeanGameMachine extends JFrame{ 
	final static int NUM_SLOTS = 10;
	final static int NUMBER_ROWS = NUM_SLOTS - 2;
	int left = 0;//Calculate ballDrop(left) 
	int right = 0;//Calculate ballDrop(right)
	int[] ball = new int[9];//create an array calculate point
	
	JLabel Test = new JLabel();//create a Label 
	private int shift = 0;
  	private int[] slots = new int[NUM_SLOTS];
  	private int ballDrop = 0;
 	private int moveCount = 0;
  	private int position = 0;
	//Construct a Panel
 	private BeanMachinePanel Panel = new BeanMachinePanel();
	
  	//Displaying the move message,drop the ball
  	private Timer timer = new Timer(100, new ActionListener(){
   	@Override
   	public void actionPerformed(ActionEvent e){
      		moveCount++;
      		//number of ball and drop to the line
      		if (moveCount <= NUMBER_ROWS){
       	 		if (Math.random() <= 0.5){ 
          			Panel.moveLeft();
					left++;
			}
				else{
					Panel.moveRight();
					position++;
					right++;
				}
				if(left == 15)
				ball[0]++;
				
      		}
				else{
        			slots[position]++;
        			Panel.startRedBall();
        			shift = 0;
        			moveCount = 0;
        			position = 0;
        			ballDrop++;
					//When drop finish,stop the ball
					if (ballDrop == 10){
					timer.stop();
					}					        	
				}
		}
  });
  
  	public BeanGameMachine(){	
		add(Panel);	
    		timer.start();
  	}
	
  	class BeanMachinePanel extends JPanel{
    	final static int LENGHT_OF_1 =25;//length of machine
    	final static int LENGHT_OF_2 =20;//width of machine
    	final static int RADIUS = 5;//ball radius
    	final static int LENGTH_OF_SLOTS = 40;
    	final static int LENGTH_OF_OPENNING = 15;
    	final static int FIRST_NAIL = 1;
    	final static int RED_BALL_START = FIRST_NAIL - RADIUS;
		
    	private int yRed = RED_BALL_START;
    	private boolean hideRedBall = false;
    
    	//Move the red ball down left
		public void moveLeft(){
      		shift -= LENGHT_OF_1 / 2;
      		yRed += LENGHT_OF_2;
     	 	repaint();
		}	

    	// Move the red ball down right
    	public void moveRight(){
      		shift += LENGHT_OF_1 / 2;
      		yRed += LENGHT_OF_2;
      		repaint();
    	}
    
    	//Move the red ball down right
    	public void startRedBall(){
      		yRed = RED_BALL_START;
      		hideRedBall = false;
      		repaint();
    	}
    
    	//Move the red ball down right
    	public void hideRedBall(){
      		hideRedBall = true;
      		repaint();
    	}
    
   	@Override
    	protected void paintComponent(Graphics g){
      		super.paintComponent(g);
      		int y = FIRST_NAIL;
      		int xCenter = getWidth() / 2;

      	// Draw the red ball
      	if (!hideRedBall){
        	g.setColor(Color.BLUE);
        	int xRed = xCenter + shift;
       		g.fillOval(xRed - RADIUS, yRed - RADIUS, 2 * RADIUS, 2 * RADIUS);
      	}
      
      	// Draw pegs in multiple lines
      	g.setColor(Color.GREEN);
      	for (int i = 0; i < NUMBER_ROWS; i++){
        	y += LENGHT_OF_2;
        	for (int k = 0; k <= i; k++){
          	g.fillOval(xCenter - i * LENGHT_OF_1 / 2 + k * LENGHT_OF_1 - RADIUS, y - RADIUS, 2 * RADIUS, 2 * RADIUS);
        	}
      	}
      
      	// Draw vertical lines for slots
      	g.setColor(Color.BLUE);
      	y = y + RADIUS;
      	for (int i = 0; i < NUM_SLOTS; i++){
        	int x = xCenter - (NUMBER_ROWS - 1) * LENGHT_OF_1 / 2 + (i - 1) * LENGHT_OF_1;
        	g.drawLine(x, y, x, y + LENGTH_OF_SLOTS);
      	}
      
      	// Draw a horizontal line for bottom
      	g.drawLine(xCenter - (NUMBER_ROWS - 1) * LENGHT_OF_1 / 2 - LENGHT_OF_1, y + LENGTH_OF_SLOTS, 
        	xCenter - (NUMBER_ROWS - 1) * LENGHT_OF_1 / 2 + NUMBER_ROWS * LENGHT_OF_1, y + LENGTH_OF_SLOTS);
      	// Draw two side lines
      	g.drawLine(xCenter +LENGHT_OF_1 / 2, FIRST_NAIL + RADIUS, xCenter - (NUMBER_ROWS - 1) * LENGHT_OF_1 / 2 + NUMBER_ROWS * LENGHT_OF_1, y);
      	g.drawLine(xCenter -LENGHT_OF_1 / 2, FIRST_NAIL + RADIUS, xCenter - (NUMBER_ROWS - 1) * LENGHT_OF_1 / 2 - LENGHT_OF_1, y);
      	// Draw two vertical lines for the openning
      	g.drawLine(xCenter - LENGHT_OF_1 / 2, FIRST_NAIL + RADIUS, xCenter - LENGHT_OF_1 / 2,FIRST_NAIL - LENGTH_OF_OPENNING);
      	g.drawLine(xCenter + LENGHT_OF_1 / 2, FIRST_NAIL + RADIUS, xCenter + LENGHT_OF_1 / 2,FIRST_NAIL - LENGTH_OF_OPENNING);
      
      	// Paint the balls in the slots
      	g.setColor(Color.RED);      
      	for (int i = 0; i < slots.length; i++){
        	int x = xCenter - (NUMBER_ROWS) * LENGHT_OF_1 / 2 + i * LENGHT_OF_1;
        	for (int j = 0; j < slots[i]; j++){
          	g.fillOval(x - RADIUS, y + LENGTH_OF_SLOTS - 2 * RADIUS - j * 2 * RADIUS, 2 * RADIUS, 2 * RADIUS);
        	}
      	}
    }
  }

public static void main(String[] args){
	JFrame frame = new BeanGameMachine();
  frame.setTitle("BeanMachine");
  frame.setSize(500, 500);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frame.setLocationRelativeTo(null);
  frame.setVisible(true);						
	}
}
