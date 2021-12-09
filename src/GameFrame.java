import javax.swing.*;

public class GameFrame extends JFrame{

	GameFrame() {
		
//		JButton button = new JButton("Restart");
//		
//		GamePanel panel = new GamePanel(button);
//		
//		button.addActionListener(e -> {
//			this.remove(panel);
//			panel = new GamePanel(button);
//			this.add(panel);
//			SwingUtilities.updateComponentTreeUI(this);
//		});
//		
//		this.add(panel);
		
		this.add(new GamePanel());
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		
	}

	
}
