package UFinal.characters;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import UFinal.BadIceCreamFour;
import UFinal.Constants;
import UFinal.lvl.LevelLoader;
import UFinal.tiles.IceTile;
import UFinal.tiles.TransparentTile;

public class Icing implements Runnable {
	
	BadIceCreamFour bic4 = BadIceCreamFour.bic4;
	LevelLoader lvlObj = bic4.lvlLoader;
	
	private int tileX, tileY;
	private char lastDir;
	private Thread t;
	private boolean isMonsterBlocking;
	
	public Icing (int xTile, int yTile, char dirLast) {
		
		tileX = xTile;
		tileY = yTile; 
		lastDir = dirLast;
		isMonsterBlocking = false;
		start();
	}
	
	public void start() {
		
		t = new Thread(this);
		t.start();
	}
	
	public void run() {
		
		int temp = 1;
		IceTile temp2;
		TransparentTile temp3;
		int temp4 = Constants.refreshCount; 
		
		if(lastDir == 'd') {
			
			if(tileY + temp < Constants.Y_TILEROWS && !(lvlObj.getTileList().get(tileY + temp).get(tileX) instanceof IceTile)) {
				
				while(tileY + temp < Constants.Y_TILEROWS 
						&& (lvlObj.getTileList().get(tileY + temp).get(tileX) instanceof TransparentTile)) {
				
					for(int i = 0; i < lvlObj.getMonsterList().size(); i++) {
						if(lvlObj.getMonsterList().get(i).getTileX() == tileX  
							&& lvlObj.getMonsterList().get(i).getTileY() == tileY + temp) {
							isMonsterBlocking = true;
							break;
						}
					}
					
					if(isMonsterBlocking) {
						break;
					}
					
					System.out.println(temp4);
					
					if(temp == 1 || Math.abs(Constants.refreshCount - temp4) >= 40) {
						
						if(lvlObj.getTileList().get(tileY + 1).get(tileX) instanceof Characters) {
							tileY++;
						}
						
						temp2 = new IceTile(tileX, tileY + temp);
						lvlObj.getTileList().get(tileY + temp).set(tileX, temp2);
						lvlObj.getPane().add(temp2, new Integer((tileY + temp)*10));
						temp++;
						temp4 = Constants.refreshCount;
					}
				}
				
			} else {
				
				while(tileY + temp < Constants.Y_TILEROWS 
						&& lvlObj.getTileList().get(tileY + temp).get(tileX) instanceof IceTile) {
					
					System.out.println(temp4);
					
					if(Math.abs(Constants.refreshCount - temp4) >= 40) {
						
						temp3 = new TransparentTile(tileX, tileY + temp);
						
						lvlObj.getPane().remove(lvlObj.getTileList().get(tileY + temp).get(tileX));
						lvlObj.getTileList().get(tileY + temp).set(tileX, temp3);
						lvlObj.getPane().revalidate();
						lvlObj.getPane().repaint();
						
						temp++;
						temp4 = Constants.refreshCount;
						
					}
				}
			}
			
		} else if (lastDir == 'u') {
			
			if(!(lvlObj.getTileList().get(tileY - temp).get(tileX) instanceof IceTile)) {
			
				while(tileY - temp >= 0 && 
						(lvlObj.getTileList().get(tileY - temp).get(tileX) instanceof TransparentTile)) {
				
					for(int i = 0; i < lvlObj.getMonsterList().size(); i++) {
						if(lvlObj.getMonsterList().get(i).getTileX() == tileX  
							&& lvlObj.getMonsterList().get(i).getTileY() == tileY - temp) {
							isMonsterBlocking = true;
							break;
						}
					}
					
					if(isMonsterBlocking) {
						break;
					}
					
					System.out.println(temp4);
					
					if(temp == 1 || Math.abs(Constants.refreshCount - temp4) >= 40) {
						
						if(lvlObj.getTileList().get(tileY - 1).get(tileX) instanceof Characters) {
							tileY--;
						}
						
						temp2 = new IceTile(tileX, tileY - temp);
						lvlObj.getTileList().get(tileY - temp).set(tileX, temp2);
						lvlObj.getPane().add(temp2, new Integer((tileY - temp)*10));
						temp++;
						temp4 = Constants.refreshCount;
					}
				}
				
			} else {
				
				while(tileY - temp >= 0 && lvlObj.getTileList().get(tileY - temp).get(tileX) instanceof IceTile) {
					
					System.out.println(temp4);
					
					if(Math.abs(Constants.refreshCount - temp4) >= 40) {
						
						temp3 = new TransparentTile(tileX, tileY - temp);
						
						lvlObj.getPane().remove(lvlObj.getTileList().get(tileY - temp).get(tileX));
						lvlObj.getTileList().get(tileY - temp).set(tileX, temp3);
						lvlObj.getPane().revalidate();
						lvlObj.getPane().repaint();
						temp++;
						temp4 = Constants.refreshCount;
						
					}
				}
			}
			
		} else if (lastDir == 'r') {
			
			if(!(lvlObj.getTileList().get(tileY).get(tileX + temp) instanceof IceTile)) {
				
				while(tileX + temp < Constants.X_TILECOLS && 
						(lvlObj.getTileList().get(tileY).get(tileX + temp) instanceof TransparentTile)) {
				
					for(int i = 0; i < lvlObj.getMonsterList().size(); i++) {
						if(lvlObj.getMonsterList().get(i).getTileX() == tileX + temp 
							&& lvlObj.getMonsterList().get(i).getTileY() == tileY) {
							isMonsterBlocking = true;
							break;
						}
					}
					
					if(isMonsterBlocking) {
						break;
					}
					
					System.out.println(temp4);
					
					if(temp == 1 || Math.abs(Constants.refreshCount - temp4) >= 40) {
						
						if(lvlObj.getTileList().get(tileY).get(tileX + 1) instanceof Characters) {
							tileX++;
						}
						
						temp2 = new IceTile(tileX + temp, tileY);
						lvlObj.getTileList().get(tileY).set(tileX + temp, temp2);
						lvlObj.getPane().add(temp2, new Integer(tileY*10));
						temp++;
						temp4 = Constants.refreshCount;
					}
				}
				
			} else {
				
				while(tileX + temp < Constants.X_TILECOLS && 
						lvlObj.getTileList().get(tileY).get(tileX + temp) instanceof IceTile) {
				
					System.out.println(temp4);
					
					if(Math.abs(Constants.refreshCount - temp4) >= 40) {
						
						temp3 = new TransparentTile(tileX + temp, tileY);
						
						lvlObj.getPane().remove(lvlObj.getTileList().get(tileY).get(tileX + temp));
						lvlObj.getTileList().get(tileY).set(tileX + temp, temp3);
						lvlObj.getPane().revalidate();
						lvlObj.getPane().repaint();
						temp++;
						temp4 = Constants.refreshCount;
						
					}
				}
			}
			
		} else if (lastDir == 'l') {
			
			if(!(lvlObj.getTileList().get(tileY).get(tileX - temp) instanceof IceTile)) {
				
				while(tileX - temp >= 0 && 
						(lvlObj.getTileList().get(tileY).get(tileX - temp) instanceof TransparentTile)) {
					
					for(int i = 0; i < lvlObj.getMonsterList().size(); i++) {
						if(lvlObj.getMonsterList().get(i).getTileX() == tileX - temp 
							&& lvlObj.getMonsterList().get(i).getTileY() == tileY) {
							isMonsterBlocking = true;
							break;
						}
					}
					
					if(isMonsterBlocking) {
						break;
					}
					
					System.out.println(temp4);
					
					if(temp == 1 || Math.abs(Constants.refreshCount - temp4) >= 40) {
						
						if(lvlObj.getTileList().get(tileY).get(tileX - 1) instanceof Characters) {
							tileX--;
						}
						
						temp2 = new IceTile(tileX - temp, tileY);
						lvlObj.getTileList().get(tileY).set(tileX - temp, temp2);
						lvlObj.getPane().add(temp2, new Integer(tileY*10));
						temp++;
						temp4 = Constants.refreshCount;
					}
				}
				
			} else {
				
				while(tileX - temp >= 0 && 
						lvlObj.getTileList().get(tileY).get(tileX - temp) instanceof IceTile) {
					
					System.out.println(temp4);
					
					if(Math.abs(Constants.refreshCount - temp4) >= 40) {
						
						temp3 = new TransparentTile(tileX - temp, tileY);
						
						lvlObj.getPane().remove(lvlObj.getTileList().get(tileY).get(tileX - temp));
						lvlObj.getPane().add(temp3, new Integer(tileY*10));
						lvlObj.getTileList().get(tileY).set(tileX - temp, temp3);
						lvlObj.getPane().revalidate();
						lvlObj.getPane().repaint();
						temp++;
						temp4 = Constants.refreshCount;
					}
				}
			}
		}
	}
}
