package UFinal.fruit;

import UFinal.Constants;
import UFinal.characters.Characters;

public class Avocado extends Fruit {

	private int init;
	
	public Avocado(int xTile, int yTile, int width, int height, boolean isFruit) {
		super("/UFinal/img/avocado.png", xTile, yTile, width, height, isFruit);
		init = Constants.refreshCount;
		setRefresh(true, 0);
		makeUpdater();
	}
	
	public Avocado(int xTile, int yTile, boolean isFruit) {
		super("/UFinal/img/avocado.png", xTile, yTile, Constants.tileSize/2, Constants.tileSize, isFruit);
		init = Constants.refreshCount;
		checkIsIced();
		setRefresh(true, 0);
		makeUpdater();
	}
	
	public void makeUpdater() {
		
		Update updater = new Update() {
			
			int newX;
			int newY;
			boolean validNew;
			Fruit fruit;
			Characters character;
			
			@Override
			public void refresh(boolean isX, double incr) {
				
				if(Math.abs(Constants.refreshCount - init) >= 1000) {
		
					do {
						
						validNew = true;
						
						newX = (int)(Math.random()*Constants.X_TILECOLS);
						newY = (int)(Math.random()*Constants.Y_TILEROWS);

						for(int i = 0; i < lvlObj.getFruitArray().get(Constants.fruitLvl).size(); i++) {
							
							fruit = lvlObj.getFruitArray().get(Constants.fruitLvl).get(i);
							if(fruit.getTileX() == newX && fruit.getTileY() == newY) {
								validNew = false;
								break;
							}
						}
						
						for(int j = 0; j < lvlObj.getPlayerList().size(); j++) {
							
							character = lvlObj.getPlayerList().get(j);
							if(!validNew || character.getTileX() == newX && character.getTileY() == newY) {
								validNew = false;
								break;
							}
						}
						
						init = Constants.refreshCount;
						
					} while(!validNew);
					
					if(validNew) {
						
						setCenterX(newX*Constants.tileSize + Constants.tileSize/2 + Constants.X_OFFSET);
						setCenterY(newY*Constants.tileSize + Constants.tileSize/2 + Constants.Y_OFFSET);
						tileX = newX;
						tileY = newY;
						lvlObj.getPane().setLayer(thisFruit, new Integer(tileY*10 + 1));
						validNew = false;
					}
				}
				checkIsIced();
				detectWhenPicked();
			}
		};
		
		setUpdater(updater);
	}
}
