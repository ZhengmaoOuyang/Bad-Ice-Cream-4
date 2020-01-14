package UFinal.fruit;

import UFinal.Constants;

public class Lemon extends Fruit {

	public Lemon(int xTile, int yTile, int width, int height, boolean isFruit) {
		super("/UFinal/img/lemon.png", xTile, yTile, width, height, isFruit);
		setRefresh(true, 0);
		makeUpdater();
	}
	
	public Lemon(int xTile, int yTile, boolean isFruit) {
		super("/UFinal/img/lemon.png", xTile, yTile, Constants.tileSize, (int)(Constants.tileSize/1.3), isFruit);
		setRefresh(true, 0);
		makeUpdater();
	}
	
	public void makeUpdater() {
		
		Update updater = new Update() {
			
			@Override
			public void refresh(boolean isX, double incr) {
				
				checkIsIced();
				detectWhenPicked();
			}
		};
		
		setUpdater(updater);
	}
}
