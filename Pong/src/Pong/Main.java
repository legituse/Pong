package Pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
	JFrame frame = new JFrame("Pong");
	JButton start = new JButton("Start");
	JTextPane P1score = new JTextPane();
	JTextPane P2score = new JTextPane();
	
	int ypaddle1; //paddle 1 y value
	int ypaddle2; //paddle 2 y value
	
	int P1=0;//p1 score
	int P2=0;//p2 score

	int ballx=794/2-20; // ball x position in the middle of the screen
	int bally=565/2-20; // ball y position in the middle of the screen
	int[] randomvalue =  {-12,-11,-10,10,11,12};
	int ballxvelocity=randomvalue[ThreadLocalRandom.current().nextInt(0, 5 + 1)]; //random value from array pixels in x direction
	int ballyvelocity=randomvalue[ThreadLocalRandom.current().nextInt(0, 5 + 1)]; //random value from array pixels in y direction

	public class DrawPaddle extends JPanel {
		int framesizex;
		int framesizey;
		DrawPaddle(){
			framesizex=794;
			framesizey=565;
			ypaddle1=framesizey/2-framesizey/10;
			ypaddle2=framesizey/2-framesizey/10;
			ballx=framesizex/2-20;
			bally=framesizey/2-20;
		}
	        public void paintComponent(Graphics g) { // painting the ball and paddle
	            super.paintComponent(g);
	            g.setColor(Color.black);
	            g.fillRect(0, 0, getWidth(), getHeight());
	            g.setColor(Color.white);
	            g.fillRect(0, ypaddle1, 10, 90);
	            g.fillRect(784, ypaddle2, 10, 90);
	            g.fillOval(ballx, bally, 20, 20);
	        }
	        
	    }

	
	public Rectangle ballgetBounds() { //getting ball bounds used for collision detection 
		return new Rectangle(ballx, bally, 20, 20);
	}
	public Rectangle paddle1getBounds() { //getting paddle1 bounds  used for collision detection 
		return new Rectangle(0, ypaddle1, 10, 90);
	}
	public Rectangle paddle2getBounds() { //getting paddle2 bounds  used for collision detection 
		return new Rectangle(784, ypaddle2, 10, 90);
	}
	
	private boolean collision() { //collision detection of the paddle and ball using .intersects method by getting the bounds of each ball and paddle
		boolean coll=false;
		if (paddle1getBounds().intersects(ballgetBounds())){
			coll = true;
		}
		else if (paddle2getBounds().intersects(ballgetBounds())) {
			coll = true;
		}
		return coll;
	}
	
	public void updateball() {
		
		if (bally>=531) {
			ballyvelocity=ThreadLocalRandom.current().nextInt(-12, -10 + 1); //checking if the ball hit the bottom border to deflect the ball velocity
			
		}
		else if (bally<=1) {
			ballyvelocity=ThreadLocalRandom.current().nextInt(10, 12 + 1);//checking if the ball hit the top border to deflect the ball velocity
			
		}
		else if (ballx>=764) {
			if (collision()) {// checking if ball hit the paddle to reverse the velocity
			ballxvelocity=ThreadLocalRandom.current().nextInt(-12, -10 + 1);
			}
			else if (ballx>=800) { // seeing if the ball crossed the screen on the east side
				ballx=794/2-20;
				bally=565/2-20;
				ballxvelocity=randomvalue[ThreadLocalRandom.current().nextInt(0, 5 + 1)];
				ballyvelocity=randomvalue[ThreadLocalRandom.current().nextInt(0, 5 + 1)];
				P1=P1+1;
				P1score.setText("P1 Score: "+P1);
			}
		}
		else if (ballx<=10) {
			if (collision()) { // checking if ball hit the paddle to reverse the velocity
				ballxvelocity=ThreadLocalRandom.current().nextInt(10, 12 + 1);
			}
			else if (ballx<=-20) { // seeing if the ball crossed the screen on the west side
				ballx=794/2-20;
				bally=565/2-20;
				ballxvelocity=randomvalue[ThreadLocalRandom.current().nextInt(0, 5 + 1)];
				ballyvelocity=randomvalue[ThreadLocalRandom.current().nextInt(0, 5 + 1)];
				P2=P2+1;
				P2score.setText("P2 Score: "+P2);
			}
		}

		ballx=ballx+ballxvelocity;
		bally=bally+ballyvelocity;
	}
	
	
	
	Main(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLocation(400, 100);
		JPanel panel = new JPanel(new BorderLayout());
		frame.add(panel);
		DrawPaddle Paddles = new DrawPaddle();
		frame.setVisible(true);
		frame.setResizable(false);
		JPanel toppanel = new JPanel(new GridLayout(0,5));
		// creating the main frame of the gui
		toppanel.add(P1score);
		toppanel.add(Box.createHorizontalBox());
		toppanel.add(start);
		toppanel.add(Box.createHorizontalBox());
		toppanel.add(P2score);
		// adding the player score and start button to the top bar
		P1score.setEditable(false);
		P2score.setEditable(false);
		
		P1score.setText("P1 Score: "+P1);
		P2score.setText("P2 Score: "+P1);
		// setting initial player scores
		
		
		panel.add(BorderLayout.NORTH, toppanel);
		panel.add(Paddles);
		
		Paddles.setFocusable(true);
        Paddles.requestFocusInWindow();
        //focusing on the paddle frame
        
        JOptionPane.showMessageDialog(null, "Hello Welcome To the Game Pong\nTo Play this game Simply Press the start Button\nUse the arrows keys up and down to Control the East Paddle\nand Use Letter W and S to control the West Paddle\nFirst player to 7 Points wins!");
		//basic instructions at the start
        
        start.addActionListener(new ActionListener() { //start button to start the game
			boolean actionlistenerstopper = false; // boolean used to force the code to create key listeners only once
					public void actionPerformed(ActionEvent e) { // action listener to begin code when button pressed
						Paddles.requestFocusInWindow();
						Timer timer = new Timer(40, new ActionListener() { // timer to render ball properly with a delay
							

			                @Override
			                public void actionPerformed(ActionEvent e) { // action events for the timer
			                	if (actionlistenerstopper==false){ // creating key listeners only once
			                	Paddles.addKeyListener(new KeyAdapter() {
			            			public void keyPressed(KeyEvent e) {
			            				if (e.getKeyCode()==KeyEvent.VK_W){ // up for west paddle
			            					if (ypaddle1>=15) {
			            						ypaddle1=ypaddle1-35;
			            						Paddles.repaint();
			            						
			            					}
			            				}
			            				if (e.getKeyCode()==KeyEvent.VK_S){ // down for west paddle
			            					if (ypaddle1<=440)  {
			            						ypaddle1=ypaddle1+35;
			            						Paddles.repaint();
			            					}
			            				}
			            				if (e.getKeyCode()==KeyEvent.VK_UP){ // up for east paddle
			            					if (ypaddle2>=15) {
			            						ypaddle2=ypaddle2-35;
			            						Paddles.repaint();
			            						
			            					}
			            				}
			            				if (e.getKeyCode()==KeyEvent.VK_DOWN){ // down for east paddle
			            					if (ypaddle2<=440) {
			            						ypaddle2=ypaddle2+35;
			            						Paddles.repaint();
			            						
			            					}
			            					
			            				}
			            			}
			            		});
			                	}
			                	
			                	updateball(); // method used to recalculate ball velocity and collisions
			                	actionlistenerstopper = true;
			                    if ((P1>=7)||(P2>=7)) {//ending the game
			                    	if (P1>=7) { // when player one wins
										JOptionPane.showMessageDialog(null, "Player 1 Wins");// win message
						        		System.exit(0);
						            }
									if (P2>=7) { // when player two wins
										JOptionPane.showMessageDialog(null, "Player 2 Wins"); // win message
						        		System.exit(0);
						            }
			                    	((Timer)e.getSource()).stop(); // stopping the timer when a player wins the game
			                    } 
			                    panel.repaint(); // repainting the whole panel at a timer to move the ball smoothly
			                }
			            });
						
			            timer.start();  //starting the timer
			            
					}
				});
		
		
	}
	
	
		
	 
	
	
	public static void main(String[] args) {
		new Main(); // creating the whole gui
	}

}
