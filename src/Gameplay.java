import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

// can implement multiple interfaces. KeyListener pays attention to what keys are being pressed
public class Gameplay extends JPanel implements KeyListener, ActionListener{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3914554693713413480L;
	
	private File file;
	private FileWriter writer;
	private Map<String,Integer> hashMap = new HashMap<>();
	int textField = 0;
	public static String name;
	
	private JButton borderlessButton;
	private ImageIcon borderlessImage;
	private JButton classicButton;
	private ImageIcon classicImage;
	private JButton highScoreButton;
	private ImageIcon highScoreImage;
	int n = 230;

	private int highScore = 0;
	
	
	private boolean gameStart = false;
	private String gameMode = "";
	
	private int[] snakexlength = new int[750];
	private int[] snakeylength = new int[750];
	
	// detecting which side the snake is moving, left is true means he's facing left
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	private boolean space = false;
	
	private ImageIcon rightmouth;
	private ImageIcon upmouth;
	private ImageIcon downmouth;
	private ImageIcon leftmouth;
	
	private int lengthofsnake = 3;
	
	// create a Timer class that will determine when the snake moves
	private Timer timer;
	private int delay = 100;
	
	// makes up the snake body
	private ImageIcon snakeimage;
	
	private int [] enemyxpos= {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,
								625,650,675,700,725,750,775,800,825,850};
	private int [] enemyypos= {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
	
	private ImageIcon enemyimage;
	
	private Random random = new Random();
	
	private int xpos = random.nextInt(34);
	private int ypos = random.nextInt(23);
	
	private int score = 0;
	
	private int moves = 0;
	
	private boolean gameover = false;
	
	private ImageIcon titleImage;
	private boolean collision;
	private int counter = 0;
	
	private boolean rightM;
	private boolean leftM;
	private boolean upM;
	private boolean downM;

	private String previousContent = "";

	private int m = 230;

	private File file2;

	private int x = 1;
	
	public Gameplay() {
		addKeyListener(this); //write the object that is implementing this interface, in this case it's Gameplay class so 'this' works
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this); //first is speed of snake, and second is context of this snake
		timer.start();
	}
	
	
	public void paint (Graphics g) {
		
		// detects if the game has just started, sets snake's default position. ignored if game has started
		if(moves ==0) {
			snakexlength[2]=50;
			snakexlength[1]=75;
			snakexlength[0]=100;
			
			snakeylength[2]=100;
			snakeylength[1]=100;
			snakeylength[0]=100;
		}
		
		// fill border background with dark gray to cover button
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, 900, 675);
		
		// draw title image border
		g.setColor(Color.white);
		g.drawRect(24,  10,  851, 55);
		
		// draw the title image
		titleImage = new ImageIcon(Main.class.getResource("/resources/snaketitle.jpg"));
		titleImage.paintIcon(this,  g,  25,  11);
		
		// draw border for gameplay
		g.setColor(Color.WHITE);
		g.drawRect(24, 74, 851, 577);
		
		// draw background for the gameplay. Found the dimensions by figuring out sizes of sprites and multiplying that for the bg
		g.setColor(Color.BLACK);
		g.fillRect(25, 75, 850, 575); //gameplay x is 850 and y is 575
		
		// draw the highscore
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN, 14)); //font, font style, font size
		g.drawString("Highscore: "+highScore, 75, 30);
		
		// draw the score
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN, 14)); //font, font style, font size
		g.drawString("Scores: "+score, 780, 30);
		
		// draw length
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN, 14)); //font, font style, font size
		if (gameover==true && gameMode.matches("classic")) {
			g.drawString("Length: "+lengthofsnake, 780, 50);
		} else {
			g.drawString("Length: "+lengthofsnake, 780, 50);
		}
				
		// reading highscore from file & main menu pop up
		if (gameStart==false && !gameMode.matches("highScore")) {
			borderlessImage = new ImageIcon(Main.class.getResource("/resources/button.png"));
			borderlessImage.paintIcon(this,  g,  375,  225);

			g.setFont(new Font("arial", Font.BOLD, 36));
			g.drawString("Choose Your Game Mode", 235, 175);;
			
			borderlessButton = new JButton(new AbstractAction("Borderless Snake") {
				/**
				 * 
				 */
			private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					gameStart=true;
					gameMode="borderless";
				}
			});
			
			borderlessButton.setVisible(true);
			borderlessButton.setBounds(375,225,150,40);
			this.add(borderlessButton);
			
			classicImage = new ImageIcon(Main.class.getResource("/resources/classic.png"));
			classicImage.paintIcon(this,  g,  375,  275);
			
			classicButton = new JButton(new AbstractAction("Classic Snake") {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					gameStart=true;
					gameMode="classic";
				}
			});
			
			classicButton.setVisible(true);
			classicButton.setBounds(375, 275, 150, 40);;
			this.add(classicButton);
			
			highScoreImage = new ImageIcon(Main.class.getResource("/resources/highscores.png"));
			highScoreImage.paintIcon(this,  g,  375,  375);
			
			highScoreButton = new JButton(new AbstractAction("High Scores") {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					gameMode="highScore";
					repaint();
					borderlessButton.setVisible(false);
					classicButton.setVisible(false);
					highScoreButton.setVisible(false);
				}
			});
			
			highScoreButton.setVisible(true);
			highScoreButton.setBounds(375, 375, 150, 40);;
			this.add(highScoreButton);
		}
		
		//highscore page
		try {
			if(gameMode.matches("highScore")) {
				g.setColor(Color.white);
				g.setFont(new Font("arial", Font.BOLD, 20));;
				g.drawString("Borderless", 185, 200);
				g.drawString("Classic", 630, 200);
				g.setFont(new Font("calibri", Font.PLAIN, 18));
				file = new File("src/resources/highscore.txt");
				@SuppressWarnings("resource")
				Scanner scan = new Scanner(file);
				x=1;
				while (scan.hasNext() && x<=5) {
					g.drawString(scan.nextLine(), 205, n);
					g.drawString(Integer.toString(x)+")" , 185, n);
					n+=30;
					x++;
				}
				file2 = new File("src/resources/classicHS.txt");
				@SuppressWarnings("resource")
				Scanner scan2 = new Scanner(file2);
				x=1;
				while (scan2.hasNext() && x<=5) {
					g.drawString(scan2.nextLine(), 650, m );
					g.drawString(Integer.toString(x)+")" , 630, m);
					m+=30;
					x++;
				}
				n=230;m=230;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if (gameStart==true) {
			if (counter ==0) {
				try {
					if (gameMode.matches("borderless")) {
						file = new File("src/resources/highscore.txt");
					} else if (gameMode.matches("classic")) {
						file = new File("src/resources/classicHS.txt");
					}
					@SuppressWarnings("resource")
					Scanner scan = new Scanner(file);
					
					// Delete/remove if another scan for highscores works
					x=1; previousContent="";
					while (scan.hasNext() && x<=5 && !scan.equals(null)) {
						previousContent += scan.nextLine() + "\n";
						x++;
					}
												
					String[] hsSplit = (previousContent.split("\\s"));
					for (int i=0; i<hsSplit.length; i++) {
						if (i%2==0) {
							hashMap.put(hsSplit[i],Integer.parseInt(hsSplit[i+1]));
						}
					}
					highScore = Integer.parseInt(hsSplit[1]);
					for (int n=2; n<hsSplit.length;n++) {
						if (n%2==0) {
							if (hashMap.get(hsSplit[n])>highScore) {
								highScore = hashMap.get(hsSplit[n]);
							} 
						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				counter++;
			}
			
			// draw the snake
			rightmouth = new ImageIcon(Main.class.getResource("/resources/rightmouth.png"));
			leftmouth = new ImageIcon(Main.class.getResource("/resources/leftmouth.png"));
			downmouth = new ImageIcon(Main.class.getResource("/resources/downmouth.png"));
			upmouth = new ImageIcon(Main.class.getResource("/resources/upmouth.png"));
			rightmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]); //head in the 0 index position
			if (gameover==true) {
				if (rightM==true) {
					rightmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]); //head in the 0 index position
				} else if (leftM==true){
					leftmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
				} else if (upM==true) {
					upmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
				} else if (downM==true){
					downmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
				}
			}
			
			for(int a = 0; a<lengthofsnake; a++) {
				//draws the head of snake
				if(a==0 && right) {
					rightmouth = new ImageIcon(Main.class.getResource("/resources/rightmouth.png"));
					rightmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]); 
				}
				if(a==0 && left) {
					leftmouth = new ImageIcon(Main.class.getResource("/resources/leftmouth.png"));
					leftmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]); 
				}
				if(a==0 && down) {
					downmouth = new ImageIcon(Main.class.getResource("/resources/downmouth.png"));
					downmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]); 
				}
				if(a==0 && up) {
					upmouth = new ImageIcon(Main.class.getResource("/resources/upmouth.png"));
					upmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]); 
				}
				// draws body of the snake
				if (a!=0) {
					snakeimage = new ImageIcon(Main.class.getResource("/resources/snakeimage.png"));
					if (a!=1) {
						snakeimage.paintIcon(this, g, snakexlength[a], snakeylength[a]); 
					} else if (a==1 && collision==false) {
						snakeimage.paintIcon(this, g, snakexlength[a], snakeylength[a]); 
					} else if (a==1 && collision==true) {
						snakeimage.paintIcon(this, g, snakexlength[lengthofsnake-1], snakeylength[lengthofsnake-1]); 
					}
				}

			}
			
			enemyimage = new ImageIcon(Main.class.getResource("/resources/enemy.png"));
			
			enemyimage.paintIcon(this,  g,  enemyxpos[xpos], enemyypos[ypos]);
		}
		
		//checks if enemy is colliding with head of snake
		if((enemyxpos[xpos] == snakexlength[0]) && (enemyypos[ypos] == snakeylength[0])) {
			score++;
			lengthofsnake++;
			xpos = random.nextInt(34);
			ypos = random.nextInt(23);
		}
				
		//checks if head of snake is colliding with its body
		for(int b=1; b<lengthofsnake; b++) {
			if(snakexlength[b] == snakexlength[0] && snakeylength[b] == snakeylength[0]) {
				//read highscore from file, parses number and sets it to highscore
				if (gameover==false) {
					try {
						if (gameMode.matches("borderless")) {
							file = new File("src/resources/highscore.txt");
						} else if (gameMode.matches("classic")) {
							file = new File("src/resources/classicHS.txt");
						}
						@SuppressWarnings("resource")
						Scanner scan = new Scanner(file);
						String[] hsSplit = (scan.nextLine()).split("\\s");
						for (int i=0; i<hsSplit.length; i++) {
							if (i%2==0) {
								hashMap.put(hsSplit[i],Integer.parseInt(hsSplit[i+1]));
							}
						}
						highScore = Integer.parseInt(hsSplit[1]);
						for (int n=2; n<hsSplit.length;n++) {
							if (n%2==0) {
								if (hashMap.get(hsSplit[n])>highScore) {
									highScore = hashMap.get(hsSplit[n]);
								} 
							}
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
				if (up==true || down==true || right==true || left == true) {
					rightM=false; leftM=false; upM=false; downM=false;
				}
				if (right==true) {
					rightM=true;
				} else if (left==true) {
					leftM=true;
				} else if (up==true) {
					upM=true;
				} else if (down==true) {
					downM=true;
				}
				right = false;
				left = false;
				up = false;
				down = false;
				gameover=true;
				
				//font for main game over text
				g.setColor(Color.white);
				g.setFont(new Font("arial", Font.BOLD, 20));;
				if (score <= highScore) {
					g.drawString("Press SPACE to restart", 350, 340);
				}
				
				//if highscore is beat
				if (score > highScore) {
					//instantiating a new Text object that has a JTextField and JTextArea
					Text text = new Text();
					while (textField==0) {	//prevents multiple Text() from being created at once
						text.createAndShowGUI();
						textField++;
					}
					
					//writes new high score, had issues with name being null before we made it static

					try {
						String fileContent = name + " " + Integer.toString(score);
						if (gameMode.matches("borderless")) {
							writer = new FileWriter("src/resources/highscore.txt");
						} else if (gameMode.matches("classic")) {
							writer = new FileWriter("src/resources/classicHS.txt");
						}
						writer.append(fileContent + "\n");
						writer.append(previousContent);
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					//trying to make this text appear after the Text object has been instantiated, hasn't worked yet
					if (name!=null) {
						g.drawString("CONGRATS! You beat the High Score!", 275, 340);
						g.drawString("New High Score: " + score, 370, 380);
						g.drawString("Press SPACE to restart", 350, 420);
					}
				}
				g.setFont(new Font("arial", Font.BOLD, 50));;								
				g.drawString("GAME OVER", 300, 300);
			}
		}
		
		g.dispose();

	}
	

	@Override
	public void actionPerformed(ActionEvent e) {


		timer.start();
		if (gameStart==true) {
			repaint();
			borderlessButton.setVisible(false);
			classicButton.setVisible(false);
			highScoreButton.setVisible(false);
			if(right) {
				for(int r = lengthofsnake-1; r>=0; r--) {
					snakeylength[r+1] = snakeylength[r];
				}
				for (int r = lengthofsnake; r>=0; r--) {
					if(r==0) {
						snakexlength[r] = snakexlength[r] + 25;
					} else {
						snakexlength[r] = snakexlength[r-1];
					}
					// goes past borders
					if (snakexlength[r]>850) {
						if (gameMode.matches("borderless")) {
							snakexlength[r]=25;
						} else {
							snakexlength[0] = snakexlength[1];
							snakeylength[0] = snakeylength[1];
							lengthofsnake++;
							collision=true;
						}
					}
				}
				repaint(); //refreshes the graphics for the new snake
			}
			if(left) {
				for(int r = lengthofsnake-1; r>=0; r--) {
					snakeylength[r+1] = snakeylength[r];
				}
				for (int r = lengthofsnake; r>=0; r--) {
					if(r==0) {
						snakexlength[r] = snakexlength[r] - 25;
					} else {
						snakexlength[r] = snakexlength[r-1];
					}
					// goes past borders
					if (snakexlength[r]<25) {
						if (gameMode.matches("borderless")) {
							snakexlength[r]=850;
						} else {
							snakexlength[0] = snakexlength[1];
							snakeylength[0] = snakeylength[1];
							lengthofsnake++;
							collision=true;
						}
					}
				}
				repaint(); //refreshes the graphics for the new snake
			}
			if(up) {
				for(int r = lengthofsnake-1; r>=0; r--) {
					snakexlength[r+1] = snakexlength[r];
				}
				for (int r = lengthofsnake; r>=0; r--) {
					if(r==0) {
						snakeylength[r] = snakeylength[r] - 25;
					} else {
						snakeylength[r] = snakeylength[r-1];
					}
					// goes past borders
					if (snakeylength[r]<75) {
						if (gameMode.matches("borderless")) {
							snakeylength[r]=625;
						} else {
							snakexlength[0] = snakexlength[1];
							snakeylength[0] = snakeylength[1];
							lengthofsnake++;
							collision=true;
						}
					}
				}
				repaint(); //refreshes the graphics for the new snake
			}
			if(down) {
				for(int r = lengthofsnake-1; r>=0; r--) {
					snakexlength[r+1] = snakexlength[r];
				}
				for (int r = lengthofsnake; r>=0; r--) {
					if(r==0) {
						snakeylength[r] = snakeylength[r] + 25;
					} else {
						snakeylength[r] = snakeylength[r-1];
					}
					// goes past borders
					if (snakeylength[r]>625) {
						if (gameMode.matches("borderless")) {
							snakeylength[r]=75;	
						} else {
							snakexlength[0] = snakexlength[1];
							snakeylength[0] = snakeylength[1];
							collision=true;
						}
					}
				}
				repaint(); //refreshes the graphics for the new snake
			}
			if(space) {
				repaint();
				space=false;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			moves = 0;
			score = 0;
			lengthofsnake = 3;
			gameover = false;
			textField = 0;
			collision=false;
			counter = 0;
			gameStart = false;
			gameMode = "";
			repaint();
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) { // have to reassign the variables to reset the game
			moves = 0;
			score = 0;
			lengthofsnake = 3;
			gameover = false;
			space=true;
			textField = 0;
			collision=false;
			counter = 0;
			try {
				@SuppressWarnings("resource")
				Scanner scan = new Scanner(file);
				String[] hsSplit = (scan.nextLine()).split("\\s");
				for (int i=0; i<hsSplit.length; i++) {
					if (i%2==0) {
						hashMap.put(hsSplit[i],Integer.parseInt(hsSplit[i+1]));
					}
				}
				highScore = Integer.parseInt(hsSplit[1]);
				for (int n=2; n<hsSplit.length;n++) {
					if (n%2==0) {
						if (hashMap.get(hsSplit[n])>highScore) {
							highScore = hashMap.get(hsSplit[n]);
						} 
					}
				}
			} catch (FileNotFoundException f) {
				f.printStackTrace();
			}
			repaint();
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT && gameover==false) {
			moves++;
			right=true;
			if(!left) {
				right=true;
			} else {
				right = false;
				left = true;
			}
			up = false;
			down = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT && gameover==false && moves!=0) {
			moves++;
			left=true;
			if(!right) {
				left=true;
			} else {
				right = true;
				left = false;
			}
			up = false;
			down = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP && gameover==false) {
			moves++;
			up=true;
			if(!down) {
				up=true;
			} else {
				down = true;
				up = false;
			}
			left = false;
			right = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN && gameover==false) {
			moves++;
			down=true;
			if(!up) {
				down=true;
			} else {
				up = true;
				down = false;
			}
			left = false;
			right = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
