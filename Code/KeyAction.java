package UFinal;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import UFinal.lvl.LevelLoader;
import UFinal.tiles.TransparentTile;

public class KeyAction implements KeyListener {
	
	int keyNum, init = 0;
	
	ArrayList<Integer> keyArray = new ArrayList<Integer>();
	ArrayList<Integer> counterArray = new ArrayList<Integer>();
	
	BadIceCreamFour bic4Obj = BadIceCreamFour.bic4;
	LevelLoader lvlObj = bic4Obj.lvlLoader;
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {

			lvlObj.getPlayer(0).iceInteraction();
		} 
		
		if(keyArray.size() > -1 && !keyArray.contains(e.getKeyCode())) {
			
			keyArray.add(e.getKeyCode());
			counterArray.add(Constants.refreshCount);
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {

		counterArray.remove(keyArray.indexOf(e.getKeyCode()));
		keyArray.remove(keyArray.indexOf(e.getKeyCode()));
	}
	
	public void useKeys() {
		
		if(keyArray.size() > -1) {
		
			for(int i = 0; i < keyArray.size(); i++) {
				
				keyNum = keyArray.get(i);
				init = counterArray.get(i);
				
				if(keyNum == KeyEvent.VK_RIGHT && lvlObj.getPlayer(0).getRightX() < Constants.FRAME_WIDTH_ADJUSTED) {
					
					lvlObj.getPlayer(0).faceDirection('r');
					
					if(lvlObj.getPlayer(0).getTileX() + 1 < Constants.X_TILECOLS &&	
							lvlObj.getPlayer(0).deltaX() == 0 && lvlObj.getPlayer(0).deltaY() == 0 && Math.abs(Constants.refreshCount - init) >= 50
							&& (lvlObj.getTileList().get(lvlObj.getPlayer(0).getTileY()).get(lvlObj.getPlayer(0).getTileX() + 1) instanceof TransparentTile)) {
						
						lvlObj.getPlayer(0).moveTilesHor(true);
					}
					
				} else if (keyNum == KeyEvent.VK_LEFT && lvlObj.getPlayer(0).getX() > 0) {
					
					lvlObj.getPlayer(0).faceDirection('l');
					
					if(lvlObj.getPlayer(0).getTileX() - 1 >= 0 &&
							lvlObj.getPlayer(0).deltaX() == 0 && lvlObj.getPlayer(0).deltaY() == 0 && Math.abs(Constants.refreshCount - init) >= 50
							&&  (lvlObj.getTileList().get(lvlObj.getPlayer(0).getTileY()).get(lvlObj.getPlayer(0).getTileX() - 1) instanceof TransparentTile)) {
						
						lvlObj.getPlayer(0).moveTilesHor(false);
					}
		
				} else if (keyNum == KeyEvent.VK_DOWN && lvlObj.getPlayer(0).getBottomY() < Constants.FRAME_HEIGHT_ADJUSTED) {
					
					lvlObj.getPlayer(0).faceDirection('d');
					
					if(lvlObj.getPlayer(0).getTileY() + 1 < Constants.Y_TILEROWS &&
							lvlObj.getPlayer(0).deltaX() == 0 && lvlObj.getPlayer(0).deltaY() == 0 && Math.abs(Constants.refreshCount - init) >= 50
							&& (lvlObj.getTileList().get(lvlObj.getPlayer(0).getTileY() + 1).get(lvlObj.getPlayer(0).getTileX()) instanceof TransparentTile)) {
						
						lvlObj.getPlayer(0).moveTilesVer(false);
					}
					
				} else if (keyNum == KeyEvent.VK_UP && lvlObj.getPlayer(0).getY() > 0) {
		
					lvlObj.getPlayer(0).faceDirection('u');
					
					if(lvlObj.getPlayer(0).getTileY() - 1 >= 0 &&
							lvlObj.getPlayer(0).deltaX() == 0 && lvlObj.getPlayer(0).deltaY() == 0 && Math.abs(Constants.refreshCount - init) >= 50
							&& (lvlObj.getTileList().get(lvlObj.getPlayer(0).getTileY() - 1).get(lvlObj.getPlayer(0).getTileX()) instanceof TransparentTile)) {

						lvlObj.getPlayer(0).moveTilesVer(true);
					}
				}
			}
		}
	}
}
