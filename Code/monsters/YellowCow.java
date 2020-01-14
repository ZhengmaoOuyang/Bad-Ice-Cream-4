package UFinal.monsters;

import java.util.ArrayList;

import UFinal.Constants;
import UFinal.characters.Characters;
import UFinal.tiles.IceTile;

public class YellowCow extends Monster {

	private char lastDir;
	private double minDist;
	private Characters playerToChase;
	
	public YellowCow(int xTile, int yTile, int width, int height) {
		super("/UFinal/img/ycow.png", xTile, yTile, width, height);
		minDist = Math.sqrt(Math.pow(lvlObj.getPlayerList().get(0).getTileX() - tileX, 2) 
				+ Math.pow(lvlObj.getPlayerList().get(0).getTileY() - tileY, 2));
		setRefresh(true, 0);
		setUpdater();
	}

	public YellowCow(int xTile, int yTile) {
		super("/UFinal/img/ycow.png", xTile, yTile);
		minDist = Math.sqrt(Math.pow(lvlObj.getPlayerList().get(0).getTileX() - tileX, 2) 
				+ Math.pow(lvlObj.getPlayerList().get(0).getTileY() - tileY, 2));
		setRefresh(true, 0);
		setUpdater();
	}

	public void setUpdater() {
		
		Update updater = new Update() {
			
			boolean iceOnLeft, iceOnRight, iceAbove, iceBelow;
			
			@Override
			public void refresh(boolean isX, double incr) {
				
				checkForKill();
				
				if (deltaX() == 0 && deltaY() == 0) {
					
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

					if(Constants.numPlayers > 1) {
						
						for(int i = 0; i < lvlObj.getPlayerList().size(); i++) {
							
							if(Math.sqrt(Math.pow(lvlObj.getPlayer(i).getTileX() - tileX, 2) 
								+ Math.pow(lvlObj.getPlayer(i).getTileY() - tileY, 2)) < minDist) {
								
								minDist = Math.sqrt(Math.pow(lvlObj.getPlayer(i).getTileX() - tileX, 2) 
										+ Math.pow(lvlObj.getPlayer(i).getTileY() - tileY, 2));
								
								playerToChase = lvlObj.getPlayer(i);
							}
						}
						
					} else {
						playerToChase = lvlObj.getPlayerList().get(0);
					}
					
					if(Math.abs(playerToChase.getTileX() - tileX) >= Math.abs(playerToChase.getTileY() - tileY)) {
						
						if(playerToChase.getTileX() - tileX > 0) {
							
							if(iceOnRight) {
								
								if(iceOnLeft && iceAbove && iceBelow) {
									lastDir = 'r';
								} else if (iceAbove && iceBelow) {
									lastDir = 'r';
								} else if (iceAbove) {
									lastDir = 'd';
								} else if (iceBelow) {
									lastDir = 'u';
								} else {
									if(playerToChase.getTileY() - tileY < 0) {
										lastDir = 'u';
									} else if (playerToChase.getTileY() - tileY > 0) {
										lastDir = 'd';
									} else {
										lastDir = 'r';
									}
								}
								
							} else {
								lastDir = 'r';
							}
							
						} else {
							
							if(iceOnLeft) {
								
								if(iceOnRight && iceAbove && iceBelow) {
									lastDir = 'l';
								} else if (iceAbove && iceBelow) {
									lastDir = 'l';
								} else if (iceAbove) {
									lastDir = 'd';
								} else if (iceBelow) {
									lastDir = 'u';
								} else {
									if(playerToChase.getTileY() - tileY < 0) {
										lastDir = 'u';
									} else if (playerToChase.getTileY() - tileY > 0) {
										lastDir = 'd';
									} else {
										lastDir = 'l';
									}
								}
								
							} else {
								lastDir = 'l';
							}
						}
						
					} else { //
												
						if(playerToChase.getTileY() - tileY > 0) {
							
							if(iceBelow) {
								
								if(iceOnRight && iceOnLeft && iceAbove) {
									lastDir = 'd';
								} else if (iceOnRight && iceOnLeft) {
									lastDir = 'd';
								} else if (iceOnRight) {
									lastDir = 'l';
								} else if (iceOnLeft) {
									lastDir = 'r';
								} else {
									if(playerToChase.getTileX() - tileX < 0) {
										lastDir = 'l';
									} else if (playerToChase.getTileX() - tileX > 0) {
										lastDir = 'r';
									} else {
										lastDir = 'd';
									}
								}
								
							} else {
								lastDir = 'd';
							}

						} else {
							
							if(iceAbove) {
								
								if(iceOnRight && iceOnLeft && iceBelow) {
									lastDir = 'u';
								} else if (iceOnRight && iceOnLeft) {
									lastDir = 'u';
								} else if (iceOnRight) {
									lastDir = 'l';
								} else if (iceOnLeft) {
									lastDir = 'r';
								} else {
									if(playerToChase.getTileX() - tileX < 0) {
										lastDir = 'l';
									} else if (playerToChase.getTileX() - tileX > 0) {
										lastDir = 'r';
									} else {
										lastDir = 'u';
									}
								}
								
							} else {
								lastDir = 'u';
							}
						}
					}
				}
					
				if(lastDir == 'd' && !iceBelow) {
					moveTilesVer(false, 0.65);
				} else if (lastDir == 'u' && !iceAbove) {
					moveTilesVer(true, -0.65);
				} else if (lastDir == 'r' && !iceOnRight) {
					moveTilesHor(true, 0.65);
				} else if (lastDir == 'l' && !iceOnLeft) {
					moveTilesHor(false, -0.65);
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
