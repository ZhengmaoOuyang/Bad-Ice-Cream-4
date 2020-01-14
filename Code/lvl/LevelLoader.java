package UFinal.lvl;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import UFinal.Constants;
import UFinal.SecTimer;
import UFinal.characters.Characters;
import UFinal.fruit.Avocado;
import UFinal.fruit.Chili;
import UFinal.fruit.Fruit;
import UFinal.fruit.Grapes;
import UFinal.fruit.Kiwi;
import UFinal.fruit.Lemon;
import UFinal.fruit.Orange;
import UFinal.fruit.Peach;
import UFinal.fruit.Strawberry;
import UFinal.fruit.Watermelon;
import UFinal.monsters.BlueCow;
import UFinal.monsters.Cow;
import UFinal.monsters.Egg;
import UFinal.monsters.OrangeSquid;
import UFinal.monsters.Troll;
import UFinal.monsters.YellowCow;
import UFinal.tiles.IceTile;
import UFinal.tiles.Tile;
import UFinal.tiles.TransparentTile;

public class LevelLoader extends BufferedReader implements ActionListener {

	private ArrayList<ArrayList<Tile>> tileList;
	private ArrayList<ArrayList<Fruit>> fruitList;
	private ArrayList<Characters> playerList;
	private ArrayList<Tile> monsterList;
	private ArrayList<Fruit> fruitIndicator;
	private String lvlRow;
	private SecTimer timer;
	private JLayeredPane gamePane;
	private JLabel bg;
	private JButton sfx, music, back, restart;
	private JFormattedTextField userInput, pwdInput;
	private Clip nonLvlMusic, lvlMusic, bop, wonSound, lostSound;
	
	private String[] secretCodes = {"cerone i$ aw3somE", "ICs$U roXXX", "PlS Assign 1 0 0"};
	private int levelNumber;
	
	public LevelLoader(Reader inputStream) {
		
		super(inputStream);
		
		tileList = new ArrayList<ArrayList<Tile>>();
		monsterList = new ArrayList<Tile>();
		fruitList = new ArrayList<ArrayList<Fruit>>();
		playerList = new ArrayList<Characters>();
	    fruitIndicator = new ArrayList<Fruit>();
	    gamePane = new JLayeredPane();
	    
	    bg = new JLabel();
	    
		back = new JButton(new ImageIcon(getClass().getResource(
				"/UFinal/img/back.png")));
		back.setBounds(1095, 230, 
		back.getPreferredSize().width, back.getPreferredSize().height);
		back.addActionListener(this);
		back.setBorder(BorderFactory.createEmptyBorder());
		back.setContentAreaFilled(false);
		back.setFocusable(false);
		
	    sfx = new JButton(new ImageIcon(getClass().getResource(
	            "/UFinal/img/sfx.png")));
		sfx.setBounds(1095, 10, 
			sfx.getPreferredSize().width, sfx.getPreferredSize().height);
		sfx.setActionCommand("togglesfx");
		sfx.addActionListener(this);
		sfx.setBorder(BorderFactory.createEmptyBorder());
		sfx.setContentAreaFilled(false);
		sfx.setFocusable(false);
	    
	    music = new JButton(new ImageIcon(getClass().getResource(
	            "/UFinal/img/music.png")));
		music.setBounds(1095, 120, 
				music.getPreferredSize().width, music.getPreferredSize().height);
		music.setActionCommand("togglemusic");
		music.addActionListener(this);
		music.setBorder(BorderFactory.createEmptyBorder());
		music.setContentAreaFilled(false);
		music.setFocusable(false);
		
		restart = new JButton(new ImageIcon(getClass().getResource(
			"/UFinal/img/restart.png")));
		restart.setBounds(1095, 340, 
		restart.getPreferredSize().width, restart.getPreferredSize().height);
		restart.addActionListener(this);
		restart.setBorder(BorderFactory.createEmptyBorder());
		restart.setContentAreaFilled(false);
		restart.setFocusable(false);
		
		userInput = new JFormattedTextField();
		userInput.setLocation(560, 500);
		userInput.setBounds(560, 505, 300, 100);
		userInput.setFont(new Font("Arial", Font.BOLD, 26));
		
		try {
			
			AudioInputStream nlvlMusic = AudioSystem.getAudioInputStream(getClass().getResource("/UFinal/wav/africa.wav"));
			nonLvlMusic = AudioSystem.getClip();
			nonLvlMusic.open(nlvlMusic);
			nonLvlMusic.loop(Clip.LOOP_CONTINUOUSLY);
			
			AudioInputStream lvMusic = AudioSystem.getAudioInputStream(getClass().getResource("/UFinal/wav/rain.wav"));
			lvlMusic = AudioSystem.getClip();
			lvlMusic.open(lvMusic);
			
			AudioInputStream bopsfx = AudioSystem.getAudioInputStream(getClass().getResource("/UFinal/wav/blop.wav"));
			bop = AudioSystem.getClip();
			bop.open(bopsfx);
			
			AudioInputStream winsfx = AudioSystem.getAudioInputStream(getClass().getResource("/UFinal/wav/winSound.wav"));
			wonSound = AudioSystem.getClip();
			wonSound.open(winsfx);
			
			AudioInputStream losesfx = AudioSystem.getAudioInputStream(getClass().getResource("/UFinal/wav/lostSound.wav"));
			lostSound = AudioSystem.getClip();
			lostSound.open(losesfx);
			
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		
		gamePane.setBounds(0, 0, 2000, 1000);
		
		Constants.levelLoaded = false;
		
		try {
			mark(20000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initPassword() {
		
		gamePane.removeAll();
		
		JLabel pwdLabel = new JLabel("Enter password to play: ");
		pwdLabel.setBounds(100, 300, pwdLabel.getPreferredSize().width, pwdLabel.getPreferredSize().height);
		
		pwdInput = new JFormattedTextField();
		pwdInput.setBounds(250, 250, 200, 100);
		pwdInput.setFont(new Font("Arial", Font.BOLD, 26));
		
		JButton pwdButton = new JButton("Check password!");
		pwdButton.setBounds(475, 250, 200, 100);
		pwdButton.addActionListener(this);
		pwdButton.setActionCommand("pwdtotitle");
		
		gamePane.add(pwdLabel, new Integer(0));
		gamePane.add(pwdInput, new Integer(0));
		gamePane.add(pwdButton, new Integer(0));
	}
	
	public void titleScreen() {
		
		ImageIcon imgIcon = new ImageIcon(getClass().getResource(
            "/UFinal/img/titlebg.gif"));
		bg.setIcon(new ImageIcon(imgIcon.getImage().getScaledInstance(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT, Image.SCALE_DEFAULT)));
		bg.setBounds(-10, -10, bg.getPreferredSize().width, bg.getPreferredSize().height);
		
		JLabel titleImg = new JLabel(new ImageIcon(getClass().getResource(
            "/UFinal/img/bic4.png")));
		titleImg.setLocation(330, 100);
		titleImg.setBounds(330, 100, 
			titleImg.getPreferredSize().width, titleImg.getPreferredSize().height);
		
		JButton play = new JButton(new ImageIcon(getClass().getResource(
	        "/UFinal/img/play.png")));
		play.setLocation(400, 450);
		play.setBounds(400, 450, 
			play.getPreferredSize().width, play.getPreferredSize().height);
		play.setActionCommand("tolevelselect");
		play.addActionListener(this);
		play.setBorder(BorderFactory.createEmptyBorder());
		play.setContentAreaFilled(false);
		
		JButton howTo = new JButton(new ImageIcon(getClass().getResource(
		    "/UFinal/img/howto.png")));
		howTo.setLocation(540, 450);
		howTo.setBounds(540, 450, 
			howTo.getPreferredSize().width, howTo.getPreferredSize().height);
		howTo.setActionCommand("tohowto");
		howTo.addActionListener(this);
		howTo.setBorder(BorderFactory.createEmptyBorder());
		howTo.setContentAreaFilled(false);
		
		JButton credits = new JButton(new ImageIcon(getClass().getResource(
			"/UFinal/img/cred.png")));
		credits.setLocation(675, 450);
		credits.setBounds(675, 450, 
			credits.getPreferredSize().width, credits.getPreferredSize().height);
		credits.setActionCommand("tocredits");
		credits.addActionListener(this);
		credits.setBorder(BorderFactory.createEmptyBorder());
		credits.setContentAreaFilled(false);
		
		gamePane.removeAll();
		gamePane.add(bg, new Integer(-2));
		gamePane.add(titleImg, new Integer(-1));
		gamePane.add(play, new Integer(-1));
		gamePane.add(howTo, new Integer(-1));
		gamePane.add(credits, new Integer(-1));
		gamePane.add(music, new Integer (-1));
		gamePane.add(sfx, new Integer(-1));
	}
	
	public void howTo() {
		
		ImageIcon imgIcon = new ImageIcon(getClass().getResource(
			"/UFinal/img/selectbg.gif"));
		bg.setIcon(new ImageIcon(imgIcon.getImage().getScaledInstance(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT, Image.SCALE_DEFAULT)));
		bg.setBounds(-10, -10, bg.getPreferredSize().width, bg.getPreferredSize().height);
		
		JLabel howToImg = new JLabel(new ImageIcon(getClass().getResource(
	        "/UFinal/img/howToInst.png")));
		howToImg.setLocation(100, 50);
		howToImg.setBounds(100, 50, 
		howToImg.getPreferredSize().width, howToImg.getPreferredSize().height);
		
		back.setActionCommand("totitle");
		
		gamePane.removeAll();
		gamePane.add(bg, new Integer(-2));
		gamePane.add(howToImg, new Integer(-1));
		gamePane.add(back, new Integer(0));
		gamePane.add(sfx, new Integer(0));
		gamePane.add(music, new Integer(0));
	}
	
	public void credits() {
		
		ImageIcon imgIcon = new ImageIcon(getClass().getResource(
				"/UFinal/img/selectbg.gif"));
		bg.setIcon(new ImageIcon(imgIcon.getImage().getScaledInstance(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT, Image.SCALE_DEFAULT)));
		bg.setBounds(-10, -10, bg.getPreferredSize().width, bg.getPreferredSize().height);
		
		JLabel creditsImg = new JLabel(new ImageIcon(getClass().getResource(
		        "/UFinal/img/credits.png")));
		creditsImg.setLocation(100, 50);
		creditsImg.setBounds(100, 50, 
		creditsImg.getPreferredSize().width, creditsImg.getPreferredSize().height);
			
		back.setActionCommand("totitle");
		
		gamePane.removeAll();
		gamePane.add(bg, new Integer(-2));
		gamePane.add(creditsImg, new Integer(-1));
		gamePane.add(back, new Integer(0));
		gamePane.add(sfx, new Integer(0));
		gamePane.add(music, new Integer(0));
	}
	
	public void levelSelect() {
		
		ImageIcon imgIcon = new ImageIcon(getClass().getResource(
	        "/UFinal/img/selectbg.gif"));
		bg.setIcon(new ImageIcon(imgIcon.getImage().getScaledInstance(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT, Image.SCALE_DEFAULT)));
		bg.setBounds(-10, -10, bg.getPreferredSize().width, bg.getPreferredSize().height);
		
		back.setActionCommand("totitle");
		
		JLabel selectLabel = new JLabel(new ImageIcon(getClass().getResource(
			"/UFinal/img/lvlSelect.png")));
		selectLabel.setLocation(425, 15);
		selectLabel.setBounds(425, 15, 
			selectLabel.getPreferredSize().width, selectLabel.getPreferredSize().height);
		
		gamePane.removeAll();
		gamePane.add(bg, new Integer(-2));
		gamePane.add(sfx, new Integer(-1));
		gamePane.add(music, new Integer(-1));
		gamePane.add(back, new Integer(-1));
		gamePane.add(selectLabel, new Integer(-1));
		
		for(int i = 0; i < 10; i++) {
			
			JButton lvlButton = new JButton(new ImageIcon(getClass().getResource(
				"/UFinal/img/lvl" + (i + 1) + ".png")));
			lvlButton.setLocation(300 + 130*(i%5), 200 + 120*(i/5));
			lvlButton.setBounds(300 + 130*(i%5), 200 + 120*(i/5), 
			lvlButton.getPreferredSize().width, lvlButton.getPreferredSize().height);
			lvlButton.setActionCommand(Integer.toString(i + 1));
			lvlButton.addActionListener(this);
			lvlButton.setBorder(BorderFactory.createEmptyBorder());
			lvlButton.setContentAreaFilled(false);
			
			gamePane.add(lvlButton, new Integer(-1));
		}
		
		JLabel secretCodeLabel = new JLabel(new ImageIcon(getClass().getResource(
			"/UFinal/img/secretcode.png")));
		secretCodeLabel.setBounds(50, 505, secretCodeLabel.getPreferredSize().width, secretCodeLabel.getPreferredSize().height);
		
		gamePane.add(userInput, new Integer(-1));
		
		JButton codeButton = new JButton(new ImageIcon(getClass().getResource(
			"/UFinal/img/search.png")));
		codeButton.setLocation(850, 500);
		codeButton.setBounds(850, 500, 
		codeButton.getPreferredSize().width, codeButton.getPreferredSize().height);
		codeButton.setActionCommand("secret");
		codeButton.addActionListener(this);
		codeButton.setBorder(BorderFactory.createEmptyBorder());
		codeButton.setContentAreaFilled(false);
		
		gamePane.add(secretCodeLabel, new Integer(-1));
		gamePane.add(codeButton, new Integer(-1));
	}
	
	public void readFile(int num) {
		
		levelNumber = num;
		int numArrays = -2;
		int numLines = -1;
		Constants.fruitLvl = 0;
		
		try {
			
			reset();
			
			Constants.gameWon = false;
			Constants.gameLost = false;
			
			while(!readLine().trim().equals(Integer.toString(levelNumber))) {}
			
			lvlRow = readLine();
			String[] temp = lvlRow.split("/"); Constants.X_TILECOLS = Integer.parseInt(temp[0]); 
					Constants.Y_TILEROWS = Integer.parseInt(temp[1]);
			
			Constants.tileSize = Math.min(Constants.FRAME_WIDTH_ADJUSTED/(Constants.X_TILECOLS + 2), 
							Constants.FRAME_HEIGHT_ADJUSTED/(Constants.Y_TILEROWS + 2));
			
			Constants.X_OFFSET = (Constants.FRAME_WIDTH_ADJUSTED - Constants.tileSize*Constants.X_TILECOLS)/2.0;
			Constants.Y_OFFSET = (Constants.FRAME_HEIGHT_ADJUSTED - Constants.tileSize*Constants.Y_TILEROWS)/2.0;
			
			lvlRow = readLine();
			temp = lvlRow.split("/");
			
			for(int h = 0; h < temp.length; h++) {
				
				if(temp[h].equals("w")) {
					fruitIndicator.add(new Watermelon(0, 0, 75, 75/2, true));
				} else if(temp[h].equals("p")) {
					fruitIndicator.add(new Peach(0, 0, 60, 60, true));
				} else if(temp[h].equals("k")) {
					fruitIndicator.add(new Kiwi(0, 0, 50, 50, true));
				} else if(temp[h].equals("l")) {
					fruitIndicator.add(new Lemon(0, 0, 75, 65, true));
				} else if(temp[h].equals("g")) {
					fruitIndicator.add(new Grapes(0, 0, 75, 75, true));
				} else if(temp[h].equals("o")) {
					fruitIndicator.add(new Orange(0, 0, 75, 60, true));
				} else if(temp[h].equals("a")) {
					fruitIndicator.add(new Avocado(0, 0, 45, 75, true));
				} else if(temp[h].equals("s")) {
					fruitIndicator.add(new Strawberry(0, 0, 40, 75, true));
				} else if(temp[h].equals("c")) {
					fruitIndicator.add(new Chili(0, 0, 35, 75, true));
				} 
			}
			
			while((lvlRow = readLine()) != null && !lvlRow.equals(Integer.toString(levelNumber + 1))) {
				
				numLines++;
				
				if(lvlRow.trim().equals("endArray")) {

					numLines = -1;
					numArrays++;
					
					if(numArrays >= 0) {
						fruitList.add(new ArrayList<Fruit>());
					}
					
				} else { 
					
					if(numArrays == -2) {
						
						tileList.add(new ArrayList<Tile>());
						
						for(int i = 0; i < lvlRow.length(); i++) {
							
							switch(lvlRow.charAt(i)) {
							
							case 'n':
								tileList.get(numLines).add(new TransparentTile(i, numLines));
								break;
							case 'i':
								tileList.get(numLines).add(new IceTile(i, numLines)); 
								break;
							case '!':
								Characters tempChr = new Characters("/UFinal/img/Vainilla.png", i, numLines);
								playerList.add(tempChr);
								tileList.get(numLines).add(tempChr);
								break;
							}
						}
		
					} else if (numArrays == -1) {
						
						for(int i = 0; i < lvlRow.length(); i++) {
							
							if(lvlRow.charAt(i) == 'c') {
								monsterList.add(new Cow(i, numLines));
							} else if (lvlRow.charAt(i) == 'e') {
								monsterList.add(new Egg(i, numLines));
							} else if (lvlRow.charAt(i) == 'b') {
								monsterList.add(new BlueCow(i, numLines));
							} else if (lvlRow.charAt(i) == 'o') {
								monsterList.add(new OrangeSquid(i, numLines));
							} else if (lvlRow.charAt(i) == 't') {
								monsterList.add(new Troll(i, numLines));
							} else if (lvlRow.charAt(i) == 'y') {
								monsterList.add(new YellowCow(i, numLines));
							} 
						}
						
					} else {
						
						for(int i = 0; i < lvlRow.length(); i++) {
							
							if(lvlRow.charAt(i) == 'w') {
								fruitList.get(numArrays).add(new Watermelon(i, numLines, true));
							} else if(lvlRow.charAt(i) == 'p') {
								fruitList.get(numArrays).add(new Peach(i, numLines, true));
							} else if(lvlRow.charAt(i) == 'k') {
								fruitList.get(numArrays).add(new Kiwi(i, numLines, true));
							} else if(lvlRow.charAt(i) == 'l') {
								fruitList.get(numArrays).add(new Lemon(i, numLines, true));
							} else if(lvlRow.charAt(i) == 'g') {
								fruitList.get(numArrays).add(new Grapes(i, numLines, true));
							} else if(lvlRow.charAt(i) == 'o') {
								fruitList.get(numArrays).add(new Orange(i, numLines, true));
							} else if(lvlRow.charAt(i) == 'a') {
								fruitList.get(numArrays).add(new Avocado(i, numLines, true));
							} else if(lvlRow.charAt(i) == 's') {
								fruitList.get(numArrays).add(new Strawberry(i, numLines, true));
							} else if(lvlRow.charAt(i) == 'c') {
								fruitList.get(numArrays).add(new Chili(i, numLines, true));
							} 
						}
					}
				}
			}
			
			drawLevel();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void drawLevel() {
		
		gamePane.removeAll();
		gamePane.add(sfx, new Integer(-1));
		gamePane.add(music, new Integer(-1));
		
		back.setActionCommand("tolevelselect");
		restart.setActionCommand(Integer.toString(levelNumber));
		
		gamePane.add(back, new Integer(-1));
		gamePane.add(restart, new Integer(-1));
		
		ImageIcon imgIcon = new ImageIcon(getClass().getResource(
            "/UFinal/img/bg" + levelNumber + ".gif"));
		bg.setIcon(new ImageIcon(imgIcon.getImage().getScaledInstance(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT, Image.SCALE_DEFAULT)));
		bg.setBounds(-10, -10, bg.getPreferredSize().width, bg.getPreferredSize().height);
		gamePane.add(bg, new Integer(-2));
		
		for(int h = 0; h < fruitIndicator.size(); h++) {
			
			fruitIndicator.get(h).setCenterX(50);
			fruitIndicator.get(h).setCenterY(90*(h+1) + 45);
			gamePane.add(fruitIndicator.get(h), 1);
		}
		

		JLabel timerLabel = new JLabel(new ImageIcon(getClass().getResource(
			"/UFinal/img/timer.png")));
		timerLabel.setLocation(10, 10);
		timerLabel.setBounds(10, 10, 
			timerLabel.getPreferredSize().width, timerLabel.getPreferredSize().height);
		gamePane.add(timerLabel, new Integer(-1));
		
		timer = new SecTimer(180 + 15*levelNumber, 25, 15);
		gamePane.add(timer, new Integer(0));
		
		for(int i = 0; i < Constants.Y_TILEROWS; i++) {
			for(int j = 0; j < Constants.X_TILECOLS; j++) {
				
				if(tileList.get(i).get(j) instanceof IceTile) {
					gamePane.add(tileList.get(i).get(j), new Integer(i*10));
				} else if (tileList.get(i).get(j) instanceof Characters) {
					gamePane.add(tileList.get(i).get(j), new Integer(i*10));
				}
			}
		}
		
		for(int k = 0; k < monsterList.size(); k++) {
			gamePane.add(monsterList.get(k), new Integer(monsterList.get(k).getTileY()*10 + 2));
		}
		
		drawFruit(); //draws the first fruit
	}
	
	public void drawFruit() {
		
		int tileY;
		
		if(Constants.fruitLvl > 0) {
			setIconBW(fruitIndicator.get(Constants.fruitLvl - 1));
		}
		
		if(Constants.fruitLvl >= fruitList.size()) {
			gameWon();
			return;
		}
		
		if(!fruitList.isEmpty()) {
			
			for(int i = 0; i < fruitList.get(Constants.fruitLvl).size(); i++) {

				tileY = fruitList.get(Constants.fruitLvl).get(i).getTileY();
				gamePane.add(fruitList.get(Constants.fruitLvl).get(i), new Integer(tileY*10 + 1));
			}
			
			Constants.numFruit = fruitList.get(Constants.fruitLvl).size();
		}
		
		Constants.levelLoaded = true;
	}
	
	public void gameWon() {
		
		Constants.gameWon = true;
		Constants.gameLost = false;
		
		JLabel winLabel = new JLabel(new ImageIcon(getClass().getResource(
			"/UFinal/img/won.png")));
		winLabel.setLocation(310, 125);
		winLabel.setBounds(310, 125, 
		winLabel.getPreferredSize().width, winLabel.getPreferredSize().height);
		
		if(levelNumber < 11) {		
			
		    JButton next = new JButton(new ImageIcon(getClass().getResource(
		            "/UFinal/img/next.png")));
		    next.setLocation(290, 530);
			next.setBounds(290, 530, 
				next.getPreferredSize().width, next.getPreferredSize().height);
			next.setActionCommand("nextlevel");
			next.addActionListener(this);
			next.setBorder(BorderFactory.createEmptyBorder());
			next.setContentAreaFilled(false);
			gamePane.add(next, new Integer(400));
		}
	
		gamePane.add(winLabel, new Integer(400));
		
		if(Constants.sfxEnabled) {
			wonSound.loop(0);
		}
	}
	
	public void gameLost() {
		
		if(!Constants.gameWon && !Constants.gameLost) {
			if(Constants.sfxEnabled) {
				lostSound.loop(0);
			}
		}
		
		Constants.gameLost = true;
		Constants.gameWon = false;
		
		JLabel loseLabel = new JLabel(new ImageIcon(getClass().getResource(
				"/UFinal/img/lost.png")));
		loseLabel.setLocation(310, 125);
		loseLabel.setBounds(310, 125, 
		loseLabel.getPreferredSize().width, loseLabel.getPreferredSize().height);
		
		gamePane.add(loseLabel, new Integer(400));
	}
	
	public void setIconBW(Fruit fruit) {
		
		try {
			
			BufferedImage bfImg = ImageIO.read(getClass().getResource(fruit.getImgURL()));
			
			ImageFilter imgFilter = new GrayFilter(true, 50);  
			ImageProducer imgProducer = new FilteredImageSource(bfImg.getSource(), imgFilter);  
			Image img = Toolkit.getDefaultToolkit().createImage(imgProducer);

			fruit.setIcon(new ImageIcon(img));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ImageIcon resizeImg(String url, int width, int height) {
		
		BufferedImage bufferedImg;
		try {
			bufferedImg = ImageIO.read(getClass().getResource(url));
			Image image = bufferedImg.getScaledInstance(width, height, Image.SCALE_DEFAULT);
			return new ImageIcon(image);	
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<ArrayList<Fruit>> getFruitArray() {
		return fruitList;
	}
	
	public JLayeredPane getPane() {
		return gamePane;
	}
	
	public ArrayList<ArrayList<Tile>> getTileList() {
		return tileList;
	}
	
	public ArrayList<Characters> getPlayerList() {
		return playerList;
	}
	
	public ArrayList<Tile> getMonsterList() {
		return monsterList;
	}
	
	public Characters getPlayer(int index) {
		return playerList.get(index);
	}
	
	public SecTimer getTimer() {
		return timer;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String cmd = e.getActionCommand();
		
		if(Constants.sfxEnabled) {
			bop.loop(1);
		}
		
		if(Character.isDigit(cmd.charAt(0))) {
			
			Constants.levelLoaded = false;
			tileList.clear();
			fruitList.clear();
			playerList.clear();
			monsterList.clear();
			fruitIndicator.clear();
			
			if(Constants.musicEnabled) {
				nonLvlMusic.stop();
				lvlMusic.loop(Clip.LOOP_CONTINUOUSLY);
			}
			
			readFile(Integer.parseInt(cmd));
			
		} else {
			
			if(cmd.equals("tolevelselect")) {
				
				Constants.levelLoaded = false;
				
				tileList.clear();
				fruitList.clear();
				playerList.clear();
				monsterList.clear();
				fruitIndicator.clear();
				
				if(Constants.musicEnabled) {
					nonLvlMusic.loop(Clip.LOOP_CONTINUOUSLY);
					lvlMusic.stop();
				}
				
				levelSelect();
			} else if (cmd.equals("tohowto")) {
				howTo();
			} else if (cmd.equals("tocredits")) {
				credits();
			} else if (cmd.equals("togglesfx")) {
				
				if(Constants.sfxEnabled) {
					
					Constants.sfxEnabled = false;
					sfx.setIcon(new ImageIcon(getClass().getResource(
						"/UFinal/img/nsfx.png")));
				} else {
					
					Constants.sfxEnabled = true;
					sfx.setIcon(new ImageIcon(getClass().getResource(
						"/UFinal/img/sfx.png")));
				}
				
			} else if (cmd.equals("togglemusic")) {
				
				if(Constants.musicEnabled) {
					
					Constants.musicEnabled = false;
					music.setIcon(new ImageIcon(getClass().getResource(
						"/UFinal/img/nmusic.png")));	
					nonLvlMusic.stop();
					lvlMusic.stop();
					
				} else {
				
					Constants.musicEnabled = true;
					music.setIcon(new ImageIcon(getClass().getResource(
						"/UFinal/img/music.png")));
					if(!Constants.levelLoaded) {
						nonLvlMusic.loop(Clip.LOOP_CONTINUOUSLY);
					} else {
						lvlMusic.loop(Clip.LOOP_CONTINUOUSLY);
					}
					
				}
			} else if (cmd.equals("totitle")) {
				titleScreen();
			} else if (cmd.equals("secret")) {
				
				String code = userInput.getText();
				userInput.setText("");
				
				if(Constants.musicEnabled) {
					nonLvlMusic.stop();
					lvlMusic.loop(Clip.LOOP_CONTINUOUSLY);
				}
				
				for(int i = 0; i < secretCodes.length; i++) {
					
					if(code.equals(secretCodes[i])) {
						readFile(11);
					}
				}
				
			} else if (cmd.equals("nextlevel")) {
				
				levelNumber++;
				
				Constants.levelLoaded = false;
				tileList.clear();
				fruitList.clear();
				playerList.clear();
				monsterList.clear();
				fruitIndicator.clear();
				
				readFile(levelNumber);
				
			} else if (cmd.equals("pwdtotitle")) {
				
				if(pwdInput.getText().equals("100%")) {
					titleScreen();
				}
			}
		}
	}
}
