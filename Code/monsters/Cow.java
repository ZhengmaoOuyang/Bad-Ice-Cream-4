package UFinal.monsters;

import UFinal.Constants;
import UFinal.characters.Characters;
import UFinal.tiles.TransparentTile;

public class Cow extends Monster {
	
	private char lastDir;
	private int rand;
	
	public Cow(int xTile, int yTile, int width, int height) {
		super("/UFinal/img/cow.png", xTile, yTile, width, height);
		setInitDir();
		setUpdater();
	}

	public Cow(int xTile, int yTile) {
		super("/UFinal/img/cow.png", xTile, yTile);
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
