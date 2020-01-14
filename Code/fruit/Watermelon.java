package UFinal.fruit;

import UFinal.Constants;

public class Watermelon extends Fruit {

	public Watermelon(int xTile, int yTile, int width, int height, boolean isFruit) {
		super("/UFinal/img/watermelon.png", xTile, yTile, width, height, isFruit);
		setRefresh(true, 0);
		makeUpdater();
	}
	
	public Watermelon(int xTile, int yTile, boolean isFruit) {
		super("/UFinal/img/watermelon.png", xTile, yTile, Constants.tileSize, (int)(Constants.tileSize/2.5), isFruit);
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
