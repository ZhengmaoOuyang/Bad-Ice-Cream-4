package UFinal.fruit;

import UFinal.Constants;

public class Peach extends Fruit {

	public Peach(int xTile, int yTile, int width, int height, boolean isFruit) {
		super("/UFinal/img/peach.png", xTile, yTile, width, height, isFruit);
		setRefresh(true, 0);
		makeUpdater();
	}
	
	public Peach(int xTile, int yTile, boolean isFruit) {
		super("/UFinal/img/peach.png", xTile, yTile, Constants.tileSize, (int)(Constants.tileSize), isFruit);
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
