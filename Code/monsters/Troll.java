package UFinal.monsters;

import UFinal.Constants;
import UFinal.characters.Characters;
import UFinal.tiles.IceTile;

public class Troll extends Monster {

	private char lastDir;
	
	public Troll(int xTile, int yTile, int width, int height) {
		super("/UFinal/img/troll.png", xTile, yTile, width, height);
		setInitDir();
		setUpdater();
	}

	public Troll(int xTile, int yTile) {
		super("/UFinal/img/troll.png", xTile, yTile);
		setInitDir();
		setUpdater();
	}
	
	public void setInitDir() {

		int rand = (int) (Math.random() * 4);

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
			
			boolean iceOnLeft, iceOnRight, iceAbove, iceBelow;
			
			@Override
			public void refresh(boolean isX, double incr) {
				
				checkForKill();
				
				iceOnLeft = false;
				iceOnRight = false;
				iceAbove = false;
				iceBelow = false; 
				
				if(tileX == 0 
					|| lvlObj.getTileList().get(tileY).get(tileX - 1) instanceof IceTile) {
					iceOnLeft = true;
				} 
					
				if (tileX == Constants.X_TILECOLS - 1
					|| lvlObj.getTileList().get(tileY).get(tileX + 1) instanceof IceTile) {
					iceOnRight = true;
				} 
					
				if (tileY == 0
					|| lvlObj.getTileList().get(tileY - 1).get(tileX) instanceof IceTile) {
					iceAbove = true;
				} 
					
				if (tileY == Constants.Y_TILEROWS - 1
					|| lvlObj.getTileList().get(tileY + 1).get(tileX) instanceof IceTile) {
					iceBelow = true;
				}
				
				if(deltaX() == 0 && deltaY() == 0) {
					
					if(lastDir == 'd') {
						
						if(iceBelow) {
							
							if(iceOnLeft && iceOnRight && iceAbove) {	
								setRefresh(true, 0);	
							} else if (iceOnLeft && iceOnRight) {
								lastDir = 'u';
								moveTilesVer(true, -0.33);
							} else if (iceOnLeft) {
								lastDir = 'r';
								moveTilesHor(true, 0.33);
							} else if (iceOnRight) {
								lastDir = 'l';
								moveTilesHor(false, -0.33);
							} else {
								lastDir = 'r';
								moveTilesHor(true, 0.33);
							}
							
						} else {
							moveTilesVer(false, 0.33);
						}
						
					} else if(lastDir == 'u') {
						
						if(iceAbove) {
						
							if(iceOnLeft && iceOnRight && iceBelow) {	
								setRefresh(true, 0);	
							} else if (iceOnLeft && iceOnRight) {
								lastDir = 'd';
								moveTilesVer(false, 0.33);
							} else if (iceOnLeft) {
								lastDir = 'r';
								moveTilesHor(true, 0.33);
							} else if (iceOnRight) {
								lastDir = 'l';
								moveTilesHor(false, -0.33);
							} else {
								lastDir = 'l';
								moveTilesHor(false, -0.33);
							}
							
						} else {
							moveTilesVer(true, -0.33);
						}
						
					} else if(lastDir == 'l') {
						
						if(iceOnLeft) {
							
							if(iceOnRight && iceAbove && iceBelow) {	
								setRefresh(true, 0);	
							} else if (iceAbove && iceBelow) {
								lastDir = 'r';
								moveTilesHor(true, 0.33);
							} else if (iceBelow) {
								lastDir = 'u';
								moveTilesVer(true, -0.33);
							} else if (iceAbove) {
								lastDir = 'd';
								moveTilesVer(false, 0.33);
							} else {
								lastDir = 'd';
								moveTilesVer(false, 0.33);
							}
							
						} else {
							moveTilesHor(false, -0.33);
						}
						
					} else if(lastDir == 'r') {
						
						if(iceOnRight) {
							
							if(iceOnLeft && iceAbove && iceBelow) {	
								setRefresh(true, 0);	
							} else if (iceAbove && iceBelow) {
								lastDir = 'l';
								moveTilesHor(false, -0.33);
							} else if (iceBelow) {
								lastDir = 'u';
								moveTilesVer(true, -0.33);
							} else if (iceAbove) {
								lastDir = 'd';
								moveTilesVer(false, 0.33);
							} else {
								lastDir = 'u';
								moveTilesVer(true, -0.33);
							}
							
						} else {
							moveTilesHor(true, 0.33);
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
