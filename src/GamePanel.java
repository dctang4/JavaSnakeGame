import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {

	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 20;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 100;
	int delay = 200;
	int playCount = 0;
	int bestScore = 0;
//	final int[] x = new int[GAME_UNITS];
//	final int[] y = new int[GAME_UNITS];
	int[] x = new int[GAME_UNITS];
	int[] y = new int[GAME_UNITS];
	int bodyParts = 6;
	int applesEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	JButton  button;
	
	GamePanel() {
//	GamePanel(JButton b) {
		
		random = new Random();
		
//		button = b;
		button = new JButton("Start");
		button.setFont(new Font("Ink Free", Font.PLAIN, 30));
		button.setFocusable(false);
		button.addActionListener(e -> {
			
			if(playCount == 0) {
				playCount++;
				button.setVisible(false);
				button.setText("Restart");
				startGame();
			}
			else {
				restartGame();
			}
			
		});
		button.setVisible(true);
		
		this.add( button);
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.setLayout(null);
		this.addKeyListener(new MyKeyAdapter());
		
	}
	
	public void startGame() {
		
		newApple();
		running = true;
//		timer = new Timer(DELAY, this);
		timer = new Timer(delay, this);
		timer.start();
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		draw(g);
		
	}
	
	public void draw(Graphics g) {
		
		if(running) {
			
//			for(int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
//				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
//				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
//			}
			
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			for(int i = 0; i < bodyParts; i++) {
				
				if(i == 0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else {
					g.setColor(new Color(45, 180, 0));
					
//					// random color body parts.  keeps changing color
//					g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
					
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				
			}
			
			// Game Score Text
			g.setColor(Color.yellow);
			g.setFont(new Font("Ink Free", Font.ITALIC, 50));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
			
		}
		else {
			
			if(applesEaten > bestScore) {
				bestScore = applesEaten;
			}
			
			gameOver(g);
			
		}
	}
	
	public void newApple() {
		
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		
	}
	
	public void move() {
		
		for(int i = bodyParts; i > 0; i--) {
			
			x[i] = x[i-1];
			y[i] = y[i-1];
			
		}
		
		switch(direction) {
		case 'U': 
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D': 
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L': 
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R': 
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
		
	}
	
	public void checkApple() {
		
		if(x[0] == appleX && y[0] == appleY) {
			
			bodyParts++;
			applesEaten++;
			
			// increases the movement speed of the snake by lowering the delay
			if(delay > 50) {
				delay = 200 - 10*(bodyParts-6);
				timer.setDelay(delay);
			}
			
			newApple();
			
		}
		
	}
	
	public void checkCollisions() {
		
		// checks if head collides with body
		for(int i = bodyParts; i > 0; i--) {
			
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
			
		}
		
		// checks if head collides with border
		if(x[0] < 0 || y[0] < 0 || x[0] >= SCREEN_WIDTH || y[0] >= SCREEN_HEIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
		
	}
	
	public void gameOver(Graphics g) {
		
		// Game Score Text
		g.setColor(Color.yellow);
		g.setFont(new Font("Ink Free", Font.ITALIC, 50));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
		
		// Game Best Score Text
		g.setColor(Color.orange);
		g.setFont(new Font("Ink Free", Font.ITALIC, 50));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString(" Best Score: " + bestScore, (SCREEN_WIDTH - metrics2.stringWidth("Best Score: " + bestScore))/2, SCREEN_HEIGHT/4);
		
		// Snake Game Title and Game Over Text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics3 = getFontMetrics(g.getFont());
		if(playCount == 0) {
			g.drawString("Snake Game", (SCREEN_WIDTH - metrics3.stringWidth("Snake Game"))/2, SCREEN_HEIGHT/2);
		}
		else {
			g.drawString("Game Over", (SCREEN_WIDTH - metrics3.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
		}
		
		button.setBounds(200, 500, 200, 50);
		button.setForeground(Color.red);
		button.setBackground(Color.black); 
		button.setVisible(true);
		
	}
	
	public void restartGame() {
		
		button.setVisible(false);
		
		playCount++;
		//  with each restart the speed of the snake seems to increase even when restart to 200.
		//  so I increased the delay exponentially in relations to the playCount
		delay = DELAY*playCount*playCount;   
		bodyParts = 6;
		applesEaten = 0;
		direction = 'R';
		x = new int[GAME_UNITS];
		y = new int[GAME_UNITS];
		
		newApple();
		running = true;
		timer = new Timer(delay, this);
		timer.start();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			
			move();
			checkApple();
			checkCollisions();
			
		}
		
		repaint();
		
	}
	
	public class MyKeyAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
			
		}
		
	}

}
