package UFinal.fruit;

import UFinal.Constants;
import UFinal.monsters.Monster;
import UFinal.tiles.TransparentTile;

public class Strawberry extends Fruit {

	private char lastDir;
	private boolean alreadyUpdated;
	
	public Strawberry(int xTile, int yTile, int width, int height, boolean isFruit) {
		
		super("/UFinal/img/Strawberry.png", xTile, yTile, width, height, isFruit);
		setInitDir();
		alreadyUpdated = false;
		setRefresh(true, 0);
		makeUpdater();
	}
	
	public Strawberry(int xTile, int yTile, boolean isFruit) {
		
		super("/UFinal/img/Strawberry.png", xTile, yTile, (int)(Constants.tileSize/1.2), (int)(Constants.tileSize/1.8), isFruit);
		setInitDir();
		alreadyUpdated = false;
		thisFruit = this;
		setRefresh(true, 0);
		makeUpdater();
	}
	
	public void setInitDir() {
		
		int rand = (int)(Math.random()*4);
		
		if(rand == 0) {
			lastDir = 'd';
		} else if (rand == 1) {
			lastDir = 'u';
		} else if (rand == 2) {
			lastDir = 'r';
		} else if (rand == 3) {
			lastDir = 'l';
		}
	}
	
	public void makeUpdater() {
		
		Update updater = new Update() {
			
			int rand;
			
			@Override
			public void refresh(boolean isX, double incr) {
				
				if(!getIsPicked()) {
				
					rand = (int)(Math.random()*6);
					
					if(rand == 0) {
						lastDir = 'd';
					} else if (rand == 1) {
						lastDir = 'u';
					} else if (rand == 2) {
						lastDir = 'r';
					} else if (rand == 3) {
						lastDir = 'l';
					}
					
					checkIsIced();
					
					if(deltaX() == 0 && deltaY() == 0 && !getIsIced()) {
						
						if(lastDir == 'd' && tileY + 1 < Constants.Y_TILEROWS
							&& (lvlObj.getTileList().get(tileY + 1).get(tileX) instanceof TransparentTile
								|| lvlObj.getTileList().get(tileY + 1).get(tileX) instanceof Monster)) {
							
							moveTilesVer(false, 0.75);
							
						} else if (lastDir == 'u' && tileY - 1 > 0
							&& (lvlObj.getTileList().get(tileY - 1).get(tileX) instanceof TransparentTile 
								|| lvlObj.getTileList().get(tileY - 1).get(tileX) instanceof Monster)) {
							
							moveTilesVer(true, -0.75);
							
						} else if (lastDir == 'l' && tileX - 1 > 0
							&& (lvlObj.getTileList().get(tileY).get(tileX - 1) instanceof TransparentTile 
								|| lvlObj.getTileList().get(tileY).get(tileX - 1) instanceof Monster)) {
							
							moveTilesHor(false, -0.75);
							
						} else if (lastDir == 'r' && tileX + 1 < Constants.X_TILECOLS
							&& (lvlObj.getTileList().get(tileY).get(tileX + 1) instanceof TransparentTile
								|| lvlObj.getTileList().get(tileY).get(tileX + 1) instanceof Monster)) {

							moveTilesHor(true, 0.75);
						}
					}
					
					if(isX && deltaX() > 0) {
						setCenterX(getCenterX() + incr);
					} else if (deltaY() > 0){
						setCenterY(getCenterY() + incr);
					} 
					
					if (deltaX() == 0 && deltaY() == 0) {
						setRefresh(true, 0);
					}
				
					detectWhenPicked();
				}
			}
		};
		
		setUpdater(updater);
	}
}
