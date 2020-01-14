package UFinal.fruit;

import UFinal.Constants;
import UFinal.monsters.Monster;
import UFinal.tiles.IceTile;
import UFinal.tiles.TransparentTile;

public class Chili extends Fruit {

	private char lastDir;
	private boolean alreadyUpdated, alreadyIced;
	private int init;

	public Chili(int xTile, int yTile, int width, int height, boolean isFruit) {

		super("/UFinal/img/chili.png", xTile, yTile, width, height, isFruit);
		setInitDir();
		alreadyUpdated = false;
		alreadyIced = false;
		setRefresh(true, 0);
		makeUpdater();
	}

	public Chili(int xTile, int yTile, boolean isFruit) {

		super("/UFinal/img/chili.png", xTile, yTile, (int) (Constants.tileSize / 2), (int) (Constants.tileSize / 1.2),
				isFruit);
		setInitDir();
		alreadyUpdated = false;
		alreadyIced = false;
		setRefresh(true, 0);
		makeUpdater();
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

	public void makeUpdater() {

		Update updater = new Update() {

			int rand;

			@Override
			public void refresh(boolean isX, double incr) {

				if (!getIsPicked()) {

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

					if (deltaX() == 0 && deltaY() == 0) {

						checkIsIced();

						if (getIsIced()) {

							if (!alreadyIced) {
								alreadyIced = true;
								init = Constants.refreshCount;
								setRefresh(true, 0);
							}

							if (Math.abs(Constants.refreshCount - init) >= 4000) {
								destroyIce(tileX, tileY);
								alreadyIced = false;
							}

						} else {

							if (tileY + 1 < Constants.Y_TILEROWS
									&& lvlObj.getTileList().get(tileY + 1).get(tileX) instanceof IceTile) {

								destroyIce(tileX, tileY + 1);
							}

							if (tileY - 1 >= 0 && lvlObj.getTileList().get(tileY - 1).get(tileX) instanceof IceTile) {

								destroyIce(tileX, tileY - 1);
							}

							if (tileX - 1 >= 0 && lvlObj.getTileList().get(tileY).get(tileX - 1) instanceof IceTile) {

								destroyIce(tileX - 1, tileY);
							}

							if (tileX + 1 < Constants.X_TILECOLS
									&& lvlObj.getTileList().get(tileY).get(tileX + 1) instanceof IceTile) {

								destroyIce(tileX + 1, tileY);
							}

							if (lastDir == 'd' && tileY + 1 < Constants.Y_TILEROWS
									&& (lvlObj.getTileList().get(tileY + 1).get(tileX) instanceof TransparentTile
											|| lvlObj.getTileList().get(tileY + 1).get(tileX) instanceof Monster)) {

								moveTilesVer(false, 0.5);

							} else if (lastDir == 'u' && tileY - 1 > 0
									&& (lvlObj.getTileList().get(tileY - 1).get(tileX) instanceof TransparentTile
											|| lvlObj.getTileList().get(tileY - 1).get(tileX) instanceof Monster)) {

								moveTilesVer(true, -0.5);

							} else if (lastDir == 'l' && tileX - 1 > 0
									&& (lvlObj.getTileList().get(tileY).get(tileX - 1) instanceof TransparentTile
											|| lvlObj.getTileList().get(tileY).get(tileX - 1) instanceof Monster)) {

								moveTilesHor(false, -0.5);

							} else if (lastDir == 'r' && tileX + 1 < Constants.X_TILECOLS
									&& (lvlObj.getTileList().get(tileY).get(tileX + 1) instanceof TransparentTile
										|| lvlObj.getTileList().get(tileY).get(tileX + 1) instanceof Monster)) {

								moveTilesHor(true, 0.5);
							}
						}
					}

					if (!getIsIced() && isX && deltaX() > 0) {
						setCenterX(getCenterX() + incr);
					} else if (!getIsIced() && deltaY() > 0) {
						setCenterY(getCenterY() + incr);
					} else if (deltaX() == 0 && deltaY() == 0) {
						setRefresh(true, 0);
					}

					detectWhenPicked();
				}
			}
		};

		setUpdater(updater);
	}
}
