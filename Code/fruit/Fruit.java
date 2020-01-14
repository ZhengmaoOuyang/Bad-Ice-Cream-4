package UFinal.fruit;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

import UFinal.BadIceCreamFour;
import UFinal.Constants;
import UFinal.characters.Characters;
import UFinal.lvl.LevelLoader;
import UFinal.tiles.IceTile;
import UFinal.tiles.Tile;
import UFinal.tiles.TransparentTile;
import UFinal.tiles.Tile.Update;

public class Fruit extends Tile {

	protected BadIceCreamFour bic4Obj = BadIceCreamFour.bic4;
	protected LevelLoader lvlObj = bic4Obj.lvlLoader;
	
	private boolean isIced, isPicked;
	private String imgUrl;
	private Clip fruitCollected;
	
	protected Fruit thisFruit;
	
	public Fruit(String url, int xTile, int yTile, int width, int height, boolean isFruit) {
		super(url, xTile, yTile, width, height, isFruit);
		imgUrl = url;
		isIced = false;
		isPicked = false;
		thisFruit = this;
		
		try {
			
			AudioInputStream snapSfx = AudioSystem.getAudioInputStream(getClass().getResource("/UFinal/wav/snap.wav"));
			fruitCollected = AudioSystem.getClip();
			fruitCollected.open(snapSfx);
	
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public Fruit(String url, int xTile, int yTile, boolean isFruit) {
		
		super(url, xTile, yTile, isFruit);
		imgUrl = url;
		isIced = false;
		isPicked = false;
		thisFruit = this;
		
		try {
			
			AudioInputStream snapSfx = AudioSystem.getAudioInputStream(getClass().getResource("/UFinal/wav/snap.wav"));
			fruitCollected = AudioSystem.getClip();
			fruitCollected.open(snapSfx);
	
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		
		checkIsIced();
	}

	public void checkIsIced() {

		if(isIced != lvlObj.getTileList().get(tileY).get(tileX) instanceof IceTile) {	
			
			isIced = !isIced;
			
			if(isIced) {
				coveredIce();
			} else {
				uncoverIce();
			}
		}
	}
	
	public void coveredIce() {
	
		try {
			
			BufferedImage bfImg = ImageIO.read(getClass().getResource(imgUrl));
			Color tempColor, temp2Color;
			
			for(int i = 0; i < bfImg.getHeight(); i++) {
				for(int j = 0; j< bfImg.getWidth(); j++) {
					
					tempColor = new Color(bfImg.getRGB(j, i), true);
					temp2Color = new Color(tempColor.getRed(), tempColor.getGreen(), tempColor.getBlue(), 100);
					bfImg.setRGB(j, i, temp2Color.getRGB());
				}
			}
			
			setIcon(resizeImg(bfImg, getWidth(), getHeight()));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void uncoverIce() {
		setIcon(resizeImg(imgUrl, getWidth(), getHeight()));
	}
	
	public void detectWhenPicked() {
		
		if(lvlObj.getTileList().get(tileY).get(tileX) instanceof Characters) {
			
			isPicked = true;
			
			setRefresh(true, 0);
			
			if(Constants.sfxEnabled) {
				fruitCollected.loop(1);
			}
			
			lvlObj.getPane().remove(this);
			lvlObj.getPane().revalidate();
			lvlObj.getPane().repaint();
			
			lvlObj.getFruitArray().get(Constants.fruitLvl).remove(thisFruit);
			
			Constants.numFruit--;
			
			if(Constants.numFruit <= 0) {
				Constants.fruitLvl++;
				lvlObj.drawFruit();
			}
		} 
	}
	
	public void moveTilesHor(boolean isRight, double incR) {
		
		if(isRight) {
			
			setGoalX((tileX + 1)*Constants.tileSize + Constants.tileSize/2);
			setRefresh(true, incR);
			tileX++;
			
		} else {
			
			setGoalX((tileX - 1)*Constants.tileSize + Constants.tileSize/2);
			setRefresh(true, incR);
			tileX--;
		}
	}
	
	public void moveTilesVer(boolean isUp, double incR) {
		
		if(isUp) {
			
			setGoalY((tileY - 1)*Constants.tileSize + Constants.tileSize/2);
			setRefresh(false, incR);
			lvlObj.getPane().setLayer(thisFruit, new Integer((tileY - 1)*10));	
			tileY--;
			
		} else {	

			setGoalY((tileY + 1)*Constants.tileSize + Constants.tileSize/2);
			setRefresh(false, incR);
			lvlObj.getPane().setLayer(thisFruit, new Integer((tileY + 1)*10));	
			tileY++;
		}
	}
	
	public void destroyIce(int xTile, int yTile) {
		
		TransparentTile transTile = new TransparentTile(xTile, yTile);
		
		lvlObj.getPane().remove(lvlObj.getTileList().get(yTile).get(xTile));
		lvlObj.getPane().add(transTile, new Integer((yTile)*10));
		lvlObj.getTileList().get(yTile).set(xTile, transTile);
		lvlObj.getPane().revalidate();
		lvlObj.getPane().repaint();
	
	}
	
	public boolean getIsIced() {
		return isIced;
	}
	
	public boolean getIsPicked() {
		return isPicked;
	}
	
	public String getImgURL() {
		return imgUrl;
	}
}
