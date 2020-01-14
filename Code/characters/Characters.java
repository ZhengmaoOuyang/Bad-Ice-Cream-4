package UFinal.characters;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import UFinal.BadIceCreamFour;
import UFinal.Constants;
import UFinal.lvl.LevelLoader;
import UFinal.tiles.Tile;
import UFinal.tiles.TransparentTile;

public class Characters extends Tile {
	
	BadIceCreamFour bic4Obj = BadIceCreamFour.bic4;
	LevelLoader lvlObj = bic4Obj.lvlLoader;
	
	private int score;
	private char lastDir;
	private boolean isAlive;
	private Clip iceSound;
	
	public Characters(String url, int xCoord, int yCoord, int width, int height) {
		
		super(url, xCoord, yCoord, width, height);
		
		lastDir = 'd';
		isAlive = true;
		makeUpdater();
		
		try {
			
			AudioInputStream iceSfx = AudioSystem.getAudioInputStream(getClass().getResource("/UFinal/wav/iceSound2.wav"));
			iceSound = AudioSystem.getClip();
			iceSound.open(iceSfx);
	
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public Characters(String url, int xCoord, int yCoord) {
		
		super(url, xCoord, yCoord);
		
		lastDir = 'd';
		isAlive = true;
		makeUpdater(); 
		
		try {
			
			AudioInputStream iceSfx = AudioSystem.getAudioInputStream(getClass().getResource("/UFinal/wav/iceSound2.wav"));
			iceSound = AudioSystem.getClip();
			iceSound.open(iceSfx);
	
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void faceDirection(char dir) {
		lastDir = dir;
	}
	
	public void iceInteraction() {
		
		if(isAlive) {
			
			if(Constants.sfxEnabled) {
				iceSound.loop(1);
			}
			
			new Icing(tileX, tileY, lastDir);
		}
	}
	
	public void moveTilesHor(boolean isRight) {
		
		if(isRight && isAlive) {
			
			setGoalX((tileX + 1)*Constants.tileSize + Constants.tileSize/2);
			
			lvlObj.getTileList().get(tileY).set(tileX + 1, this);
			lvlObj.getTileList().get(tileY).set(tileX, (new TransparentTile(tileX, tileY)));
			tileX++;
				
			setRefresh(true, 1);
			
		} else if (isAlive) {
			
			setGoalX((tileX - 1)*Constants.tileSize + Constants.tileSize/2);
			
			lvlObj.getTileList().get(tileY).set(tileX - 1, this);
			lvlObj.getTileList().get(tileY).set(tileX, (new TransparentTile(tileX, tileY)));
			tileX--;

			setRefresh(true, -1);
		}
	}
	
	public void moveTilesVer(boolean isUp) {
		
		if(isUp && isAlive) {
			
			setGoalY(tileY*Constants.tileSize);
			setRefresh(false, -1);
			
			lvlObj.getTileList().get(tileY - 1).set(tileX, this);
			lvlObj.getPane().setLayer(this, new Integer((tileY - 1)*10));
			
			lvlObj.getTileList().get(tileY).set(tileX, (new TransparentTile(tileX, tileY)));
			tileY--;
			
		} else if (isAlive) {	
			
			setGoalY((tileY + 2)*Constants.tileSize);	
			setRefresh(false, 1);
			
			lvlObj.getTileList().get(tileY + 1).set(tileX, this);
			lvlObj.getPane().setLayer(this, new Integer((tileY + 1)*10));
			
			lvlObj.getTileList().get(tileY).set(tileX, (new TransparentTile(tileX, tileY)));
			
			tileY++;
		}
	}

	public void makeUpdater() {
	
		Update updater = new Update() {

			@Override
			public void refresh(boolean isX, double incr) {
				
				if(deltaX() == 0 && deltaY() == 0) {
					setRefresh(true, 0);
				}
				
				if(isX && deltaX() > 0) {
					setCenterX(getCenterX() + incr);
				} else if (deltaY() > 0){
					setCenterY(getCenterY() + incr);
				}	
			}
		};
		
		setUpdater(updater);
	}
	
	public void changeIcon(String url) {
		
		try {
			BufferedImage bfImg = ImageIO.read(getClass().getResource(url));
			setIcon(resizeImg(bfImg, getWidth(), getHeight()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void isDead() {
		isAlive = false;
		lvlObj.getTileList().get(tileY).set(tileX, new TransparentTile(tileX, tileY));
	}
	 
	public void setTileX(int xTile) {
		tileX = xTile;
	}
	
	public void setTileY(int yTile) {
		tileY = yTile;
	}
	
	public char getLastDir() {
		return lastDir;
	}
	
	public boolean getIsAlive() {
		return isAlive;
	}

}
