package UFinal.monsters;

import UFinal.Constants;
import UFinal.characters.Characters;
import UFinal.tiles.IceTile;
import UFinal.tiles.TransparentTile;

public class OrangeSquid extends Monster {

	private char lastDir;
	private int rand, init;
	private boolean isBrokenIce;
	
	public OrangeSquid(int xTile, int yTile, int width, int height) {
		super("/UFinal/img/osquid.png", xTile, yTile, width, height);
		isBrokenIce = true;
		setInitDir();
		setUpdater();
	}

	public OrangeSquid(int xTile, int yTile) {
		super("/UFinal/img/osquid.png", xTile, yTile);
		isBrokenIce = true;
		setInitDir();
		setUpdater();
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
				
				checkForKill();
				
				if(isBrokenIce) {
				
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

				if (deltaX() == 0 && deltaY() == 0) {

					if (lastDir == 'd' && tileY + 1 < Constants.Y_TILEROWS) {

						if(lvlObj.getTileList().get(tileY + 1).get(tileX) instanceof TransparentTile
							|| lvlObj.getTileList().get(tileY + 1).get(tileX) instanceof Characters) {
							moveTilesVer(false, 0.75);
						} else if (lvlObj.getTileList().get(tileY + 1).get(tileX) instanceof IceTile) {
							
							if(!isBrokenIce) {
								init = Constants.refreshCount;
								setRefresh(true, 0);
							}
							
							if(Math.abs(Constants.refreshCount - init) >= 1500) {
								destroyIce(tileX, tileY + 1);
								isBrokenIce = true;
							}
						}
						
					} else if (lastDir == 'u' && tileY - 1 >= 0) {
						
						if(lvlObj.getTileList().get(tileY - 1).get(tileX) instanceof TransparentTile
							|| lvlObj.getTileList().get(tileY - 1).get(tileX) instanceof Characters) {
							moveTilesVer(true, -0.75);
						} else if (lvlObj.getTileList().get(tileY - 1).get(tileX) instanceof IceTile) {
							
							if(!isBrokenIce) {
								init = Constants.refreshCount;
								setRefresh(true, 0);
							}
							
							if(Math.abs(Constants.refreshCount - init) >= 1500) {
								destroyIce(tileX, tileY - 1);
								isBrokenIce = true;
							}
							
						}

					} else if (lastDir == 'l' && tileX - 1 > 0) {

						if(lvlObj.getTileList().get(tileY).get(tileX - 1) instanceof TransparentTile
							|| lvlObj.getTileList().get(tileY).get(tileX - 1) instanceof Characters) {
							moveTilesHor(false, -0.75);
						} else if (lvlObj.getTileList().get(tileY).get(tileX - 1) instanceof IceTile) {
							
							if(!isBrokenIce) {
								init = Constants.refreshCount;
								setRefresh(true, 0);
							}
							
							if(Math.abs(Constants.refreshCount - init) >= 1500) {
								destroyIce(tileX - 1, tileY);
								isBrokenIce = true;
							}
							
						}

					} else if (lastDir == 'r' && tileX + 1 < Constants.X_TILECOLS) {

						if(lvlObj.getTileList().get(tileY).get(tileX + 1) instanceof TransparentTile
							|| lvlObj.getTileList().get(tileY).get(tileX + 1) instanceof Characters) {
							moveTilesHor(true, 0.75);
						} else if (lvlObj.getTileList().get(tileY).get(tileX + 1) instanceof IceTile) {
							
							if(!isBrokenIce) {
								init = Constants.refreshCount;
								setRefresh(true, 0);
							}
							
							if(Math.abs(Constants.refreshCount - init) >= 1500) {
								destroyIce(tileX + 1, tileY);
								isBrokenIce = true;
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
