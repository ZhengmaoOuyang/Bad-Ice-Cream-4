package UFinal.monsters;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import UFinal.Constants;
import UFinal.characters.Characters;
import UFinal.tiles.TransparentTile;
import UFinal.tiles.IceTile;
import UFinal.tiles.Tile.Update;

public class BlueCow extends Monster {
	
	private char lastDir;
	private double minDist;
	private int rand, init, width, height;
	private boolean isRandom, canChase;
	
	private Characters playerToChase;
	
	public BlueCow(int xTile, int yTile, int width, int height) {
		super("/UFinal/img/bcow.png", xTile, yTile, width, height);
		init = Constants.refreshCount;
		width = getWidth();
		height = getHeight();
		isRandom = true;
		canChase = true;
		minDist = Math.sqrt(Math.pow(lvlObj.getPlayerList().get(0).getTileX() - tileX, 2) 
				+ Math.pow(lvlObj.getPlayerList().get(0).getTileY() - tileY, 2));
		setInitDir();
		setUpdater();
	}

	public BlueCow(int xTile, int yTile) {
		super("/UFinal/img/bcow.png", xTile, yTile);
		init = Constants.refreshCount;
		width = getWidth();
		height = getHeight();
		isRandom = true;
		canChase = true;
		minDist = Math.sqrt(Math.pow(lvlObj.getPlayerList().get(0).getTileX() - tileX, 2) 
				+ Math.pow(lvlObj.getPlayerList().get(0).getTileY() - tileY, 2));
		setInitDir();
		setUpdater();
	}
	
	public void changeIcon(String url, int w, int h) {
		
		try {
			BufferedImage bfImg = ImageIO.read(getClass().getResource(url));
			setIcon(resizeImg(bfImg, w, h));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setInitDir() {

		rand = (int) (Math.random() * 8);

		if (rand == 0) {
			lastDir = 'd';
		} else if (rand == 1) {
			lastDir = 'u';
		} else if (rand == 2) {
			lastDir = 'r';
		} else if (rand == 3) {
			lastDir = 'l';
		}
	}
	
	public void setUpdater() {
		
		Update updater = new Update() {
			
			@Override
			public void refresh(boolean isX, double incr) {
				
				if(Math.abs(Constants.refreshCount) - init >= 5000) {
					
					if(isRandom) {
						
						changeIcon("/UFinal/img/circle.png", 50 , 50);
						setCoordinates(false);
						isRandom = false;
						lvlObj.getPane().setLayer(thisMonster, new Integer(tileY*10 + 2));
						init = Constants.refreshCount;
						
					} else {
						
						if(deltaX() == 0 && deltaY() == 0) {
							if(lvlObj.getTileList().get(tileY).get(tileX) instanceof IceTile) {
								destroyIce(tileX, tileY);
							} 
								
							changeIcon("/UFinal/img/bcow.png", width, height);
							setCoordinates(false);
							lvlObj.getPane().setLayer(thisMonster, new Integer(tileY*10 + 2));
							isRandom = true;
							init = Constants.refreshCount;
						}
					}
				}
				
				if(isRandom) {
					
					checkForKill();
					
					rand = (int) (Math.random() * 8);
	
					if (rand == 0) {
						lastDir = 'd';
					} else if (rand == 1) {
						lastDir = 'u';
					} else if (rand == 2) {
						lastDir = 'r';
					} else if (rand == 3) {
						lastDir = 'l';
					}
	
					if (deltaX() == 0 && deltaY() == 0) {
	
						if (lastDir == 'd' && tileY + 1 < Constants.Y_TILEROWS
							&& (lvlObj.getTileList().get(tileY + 1).get(tileX) instanceof TransparentTile 
								|| lvlObj.getTileList().get(tileY + 1).get(tileX) instanceof Characters)) {
	
							moveTilesVer(false, 0.75);
	
						} else if (lastDir == 'u' && tileY - 1 >= 0 
							&& (lvlObj.getTileList().get(tileY - 1).get(tileX) instanceof TransparentTile
								|| lvlObj.getTileList().get(tileY - 1).get(tileX) instanceof Characters)) {
	
							moveTilesVer(true, -0.75);
	
						} else if (lastDir == 'l' && tileX - 1 > 0 && tileX - 1 >= 0
							&& (lvlObj.getTileList().get(tileY).get(tileX - 1) instanceof TransparentTile
								|| lvlObj.getTileList().get(tileY).get(tileX - 1) instanceof Characters)) {
	
							moveTilesHor(false, -0.75);
	
						} else if (lastDir == 'r' && tileX + 1 < Constants.X_TILECOLS
							&& (lvlObj.getTileList().get(tileY).get(tileX + 1) instanceof TransparentTile
								||lvlObj.getTileList().get(tileY).get(tileX + 1) instanceof Characters)) {
	
							moveTilesHor(true, 0.75);
						}
					}
					
				} else {
					
					if(Constants.numPlayers > 1) {
						
						for(int i = 0; i < lvlObj.getPlayerList().size(); i++) {
							
							if(lvlObj.getPlayer(i).getIsAlive() && Math.sqrt(Math.pow(lvlObj.getPlayer(i).getTileX() - tileX, 2) 
								+ Math.pow(lvlObj.getPlayer(i).getTileY() - tileY, 2)) < minDist) {
								
								minDist = Math.sqrt(Math.pow(lvlObj.getPlayer(i).getTileX() - tileX, 2) 
										+ Math.pow(lvlObj.getPlayer(i).getTileY() - tileY, 2));
								
								playerToChase = lvlObj.getPlayer(i);
							}
							
							if(i == lvlObj.getPlayerList().size() && !lvlObj.getPlayer(i).getIsAlive()) {
								setRefresh(true, 0);
							}
						}
						
					} else if (lvlObj.getPlayer(0).getIsAlive()) {
						playerToChase = lvlObj.getPlayerList().get(0);
					} else {
						canChase = false;
						setRefresh(true, 0);
					}
					
					if(deltaX() == 0 && deltaY() == 0 && canChase) {
						
						if(Math.abs(playerToChase.getTileX() - tileX) >= Math.abs(playerToChase.getTileY() - tileY)) {
							
							if(playerToChase.getTileX() - tileX > 0) {
								moveTilesHor(true, 0.75);
							} else if (playerToChase.getTileX() - tileX == 0){
								setRefresh(true, 0);
							} else {
								moveTilesHor(false, -0.75);
							}
							
						} else {
							
							if(playerToChase.getTileY() - tileY > 0) {
								moveTilesVer(false, 0.75);
							} else {
								moveTilesVer(true, -0.75);
							}
						}
					}
				}
				
				if (isX && deltaX() > 0) {
					setCenterX(getCenterX() + incr);
				} else if (deltaY() > 0) {
					setCenterY(getCenterY() + incr);
				} else if (deltaX() == 0 && deltaY() == 0) {
					setRefresh(true, 0);
				}
			} 
		};
		
		setUpdater(updater);
	}

}
