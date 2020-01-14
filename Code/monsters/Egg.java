package UFinal.monsters;

import UFinal.Constants;
import UFinal.characters.Characters;
import UFinal.tiles.IceTile;
import UFinal.tiles.TransparentTile;

public class Egg extends Monster {
	
	private char lastDir;
	private int rand, jumpCounter;
	private boolean isFinishedJumping;
	
	public Egg(int xTile, int yTile, int width, int height) {
		super("/UFinal/img/egg.png", xTile, yTile, width, height);
		isFinishedJumping = true; 
		jumpCounter = 0;
		setInitDir();
		setUpdater();
	}

	public Egg(int xTile, int yTile) {
		super("/UFinal/img/egg.png", xTile, yTile);
		isFinishedJumping = true;
		jumpCounter = 0;
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
				
				if(isFinishedJumping) {
					
					checkForKill(); //sus
					
					rand = (int) (Math.random() * 6);
	
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
					
					if(jumpCounter == -1) {
						isFinishedJumping = true;
						jumpCounter++;
					}

					if (lastDir == 'd' && tileY + 1 < Constants.Y_TILEROWS) {

						if(lvlObj.getTileList().get(tileY + 1).get(tileX) instanceof TransparentTile 
							|| lvlObj.getTileList().get(tileY + 1).get(tileX) instanceof Characters) {
							moveTilesVer(false, 0.75);
						} else if (tileY + 2 < Constants.Y_TILEROWS 
								&& lvlObj.getTileList().get(tileY + 1).get(tileX) instanceof IceTile
								&& (lvlObj.getTileList().get(tileY + 2).get(tileX) instanceof TransparentTile
									|| lvlObj.getTileList().get(tileY + 2).get(tileX) instanceof Characters)) {
							
							if(jumpCounter == 0) {
								setGoalY(tileY*Constants.tileSize + Constants.tileSize/2);
								setRefresh(false, -0.3);
								lvlObj.getPane().setLayer(thisMonster, new Integer((tileY + 1)*10));
								jumpCounter++;
								isFinishedJumping = false;
							} else if (jumpCounter == 1) {
								setGoalY((tileY + 2)*Constants.tileSize + Constants.tileSize/2);
								setRefresh(false, 0.6);
								lvlObj.getPane().setLayer(thisMonster, new Integer((tileY + 2)*10));
								jumpCounter++; 
							} else if (jumpCounter == 2) {
								setGoalY((tileY + 3)*Constants.tileSize);
								setRefresh(false, 0.3);
								tileY += 2;
								
								jumpCounter = -1;
							}
						}
						
					} else if (lastDir == 'u' && tileY - 1 >= 0) {
						
						if(lvlObj.getTileList().get(tileY - 1).get(tileX) instanceof TransparentTile
							|| lvlObj.getTileList().get(tileY - 1).get(tileX) instanceof Characters) {
							moveTilesVer(true, -0.75);
						} else if (tileY - 2 >= 0
								&& lvlObj.getTileList().get(tileY - 1).get(tileX) instanceof IceTile
								&& (lvlObj.getTileList().get(tileY - 2).get(tileX) instanceof TransparentTile
									|| lvlObj.getTileList().get(tileY - 2).get(tileX) instanceof Characters)) {
							
							if(jumpCounter == 0) {
								setGoalY(tileY*Constants.tileSize + Constants.tileSize/2);
								setRefresh(false, -0.3);
								jumpCounter++;
								isFinishedJumping = false;
							} else if (jumpCounter == 1) {
								setGoalY((tileY - 2)*Constants.tileSize + Constants.tileSize/2);
								setRefresh(false, -0.6);
								jumpCounter++; 
							} else if (jumpCounter == 2) {
								setGoalY((tileY - 1)*Constants.tileSize);
								setRefresh(false, 0.3);
								tileY -= 2;
								lvlObj.getPane().setLayer(thisMonster, new Integer(tileY*10));
					
								jumpCounter = -1;
							}
						}

					} else if (lastDir == 'l' && tileX - 1 >= 0) {

						if(lvlObj.getTileList().get(tileY).get(tileX - 1) instanceof TransparentTile
							|| lvlObj.getTileList().get(tileY).get(tileX - 1) instanceof Characters) {
							moveTilesHor(false, -0.75);
						} else if (tileX - 2 >= 0
								&& lvlObj.getTileList().get(tileY).get(tileX - 1) instanceof IceTile
								&& (lvlObj.getTileList().get(tileY).get(tileX - 2) instanceof TransparentTile
									|| lvlObj.getTileList().get(tileY).get(tileX - 2) instanceof Characters)) {
							
							if(jumpCounter == 0) {
								setGoalY(tileY*Constants.tileSize);
								setRefresh(false, -0.3);
								jumpCounter++;
								isFinishedJumping = false;
							} else if (jumpCounter == 1) {
								setGoalX((tileX - 2)*Constants.tileSize + Constants.tileSize/2);
								setRefresh(true, -0.6);
								jumpCounter++; 
							} else if (jumpCounter == 2) {
								setGoalY((tileY + 1)*Constants.tileSize);
								setRefresh(false, 0.3);
								tileX -= 2;
								jumpCounter = -1;
							}
						}

					} else if (lastDir == 'r' && tileX + 1 < Constants.X_TILECOLS) {

						if(lvlObj.getTileList().get(tileY).get(tileX + 1) instanceof TransparentTile
							|| lvlObj.getTileList().get(tileY).get(tileX + 1) instanceof Characters) {
							moveTilesHor(true, 0.75);
						} else if (tileX + 2 < Constants.X_TILECOLS
								&& lvlObj.getTileList().get(tileY).get(tileX + 1) instanceof IceTile
								&& (lvlObj.getTileList().get(tileY).get(tileX + 2) instanceof TransparentTile
									|| lvlObj.getTileList().get(tileY).get(tileX + 2) instanceof Characters)) {
							
							if(jumpCounter == 0) {
								setGoalY(tileY*Constants.tileSize);
								setRefresh(false, -0.3);
								jumpCounter++;
								isFinishedJumping = false;
							} else if (jumpCounter == 1) {
								setGoalX((tileX + 2)*Constants.tileSize + Constants.tileSize/2);
								setRefresh(true, 0.6);
								jumpCounter++; 
							} else if (jumpCounter == 2) {
								setGoalY((tileY + 1)*Constants.tileSize);
								setRefresh(false, 0.3);
								tileX += 2;
								
								jumpCounter = -1;
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
