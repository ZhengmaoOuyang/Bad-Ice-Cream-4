package UFinal.fruit;

import UFinal.Constants;

public class Orange extends Fruit {
	
	public Orange(int xTile, int yTile, int width, int height, boolean isFruit) {
		super("/UFinal/img/orange.png", xTile, yTile, width, height, isFruit);
		setRefresh(true, 0);
		makeUpdater();
	}
	
	public Orange(int xTile, int yTile, boolean isFruit) {
		super("/UFinal/img/orange.png", xTile, yTile, Constants.tileSize, Constants.tileSize, isFruit);
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
