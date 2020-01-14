package UFinal.monsters;

import UFinal.BadIceCreamFour;
import UFinal.Constants;
import UFinal.characters.Characters;
import UFinal.lvl.LevelLoader;
import UFinal.tiles.Tile;
import UFinal.tiles.TransparentTile;

public class Monster extends Tile {
	
	BadIceCreamFour bic4Obj = BadIceCreamFour.bic4;
	LevelLoader lvlObj = bic4Obj.lvlLoader;
	Monster thisMonster;
	Characters tempChar;
	
	public Monster(String url, int xTile, int yTile, int width, int height) {
		super(url, xTile, yTile, width, height);
		thisMonster = this;
	}
	
	public Monster(String url, int xTile, int yTile) {
		super(url, xTile, yTile);
		thisMonster = this;
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
			
			setGoalY((tileY)*Constants.tileSize);
			setRefresh(false, incR);
			lvlObj.getPane().setLayer(thisMonster, new Integer((tileY - 1)*10 + 2));	
			tileY--;
			
		} else {	

			setGoalY((tileY + 2)*Constants.tileSize);
			setRefresh(false, incR);
			lvlObj.getPane().setLayer(thisMonster, new Integer((tileY + 1)*10 + 2));	
			tileY++;
		}
	}
	
	public void checkForKill() {
		if(lvlObj.getTileList().get(tileY).get(tileX) instanceof Characters) {
			((Characters) lvlObj.getTileList().get(tileY).get(tileX)).isDead();
			Constants.numDead++;
			
			if(Constants.numDead >= Constants.numPlayers) {
				lvlObj.gameLost();
			}
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
}
