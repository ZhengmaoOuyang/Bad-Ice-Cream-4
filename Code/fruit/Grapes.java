package UFinal.fruit;

import UFinal.Constants;

public class Grapes extends Fruit {
	
	public Grapes(int xTile, int yTile, int width, int height, boolean isFruit) {
		super("/UFinal/img/grape.png", xTile, yTile, width, height, isFruit);
		setRefresh(true, 0);
		makeUpdater();
	}
	
	public Grapes(int xTile, int yTile, boolean isFruit) {
		super("/UFinal/img/grape.png", xTile, yTile, (int)(Constants.tileSize/1.2), (int)(Constants.tileSize/1.2), isFruit);
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
