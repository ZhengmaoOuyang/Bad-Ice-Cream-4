package UFinal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import UFinal.characters.Characters;
import UFinal.lvl.LevelLoader;

public class BadIceCreamFour extends JPanel {
	
	public static BadIceCreamFour bic4;
	public static LevelLoader lvlLoader;
	
	private JFrame mainFrame;
	private JLayeredPane mainLayeredPane, gamePane;
	private Timer updateTimer, timer;
	private Refresher refresher;
	private ActionListener updateListener, timerListener;
	private KeyAction keyAction;
	
	public BadIceCreamFour() {
		
		lvlLoader = new LevelLoader(new InputStreamReader(getClass().getResourceAsStream("/UFinal/lvl/levels.txt")));
		
		lvlLoader.initPassword();
		
		gamePane = lvlLoader.getPane();
		
		mainLayeredPane = new JLayeredPane();
		mainLayeredPane.add(gamePane, new Integer(0));
		
		refresher = new Refresher();
		keyAction = new KeyAction();
		
		mainFrame = new JFrame("Bad Ice Cream 4");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setContentPane(mainLayeredPane);
		mainFrame.setFocusable(true);
		mainFrame.addKeyListener(keyAction);
		mainFrame.setSize(new Dimension(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT));
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);

		setUpdateListener();

		updateTimer = new Timer(1, updateListener);
		updateTimer.start();
	}
	
	
	public void setUpdateListener() {
		
		updateListener = new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {	
				
				if(Constants.levelLoaded) {
				
					keyAction.useKeys();
					
					for(int i = 0; i < Constants.Y_TILEROWS; i++) {
						for(int j = 0; j < Constants.X_TILECOLS; j++) {
							lvlLoader.getTileList().get(i).get(j).refresh();
						}
					}
					
					for(int j = 0; j < Constants.numFruit; j++) {
						lvlLoader.getFruitArray().get(Constants.fruitLvl).get(j).refresh();
					}
					
					
					for(int k = 0; k < lvlLoader.getMonsterList().size(); k++) {
						lvlLoader.getMonsterList().get(k).refresh();
					}
					
					lvlLoader.getTimer().refresh();
				}
			}
		};
	}
	
	@Override
    public void paintComponent(Graphics g) {

		super.paintComponent(g);
		revalidate();
		repaint();
    } 
	
	private static void runGUI() {
		bic4 = new BadIceCreamFour();
	}
	
	public static void main(String[] args) {
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				runGUI();
			}
		});
	}
}
