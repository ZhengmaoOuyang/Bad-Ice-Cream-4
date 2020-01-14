package UFinal.tiles;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import UFinal.Constants;

public class Tile extends JLabel {

	protected int tileX, tileY;
	protected double x, y, rightX, bottomY, centerX, centerY, incr, goalX, goalY;
	protected Update updater;
	protected boolean isX, isMvmtDone;
	
	public Tile(String url, int xTile, int yTile, int width, int height, boolean isFruit) {

		setIcon(resizeImg(url, width, height));
		
		tileX = xTile;
		tileY = yTile;
		
		setCoordinates(isFruit);
	}
	
	public Tile(String url, int xTile, int yTile, int width, int height) {

		this(url, xTile, yTile, width, height, false);
	}	
	
	public Tile(String url, int xTile, int yTile, boolean isFruit) {
		
		try {
			
			BufferedImage bufferedImg = ImageIO.read(getClass().getResource(url));	
			
			if(isFruit) {
				setIcon(new ImageIcon(bufferedImg));
			} else {
				setIcon(resizeImg(bufferedImg, Constants.tileSize, 
				(int)((double)bufferedImg.getHeight()/((double)bufferedImg.getWidth()/(double)Constants.tileSize))));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		tileX = xTile;
		tileY = yTile;
		
		setCoordinates(isFruit);
	}
	
	public Tile(String url, int xTile, int yTile) {
		
		this(url, xTile, yTile, false);
	}
	
	public void setCoordinates(boolean isFruit) {
		
		if(isFruit) {
			setCenterX((int)(tileX*Constants.tileSize + Constants.tileSize/2.0 + Constants.X_OFFSET));
			setCenterY((int)(tileY*Constants.tileSize + Constants.tileSize/2.0 + Constants.Y_OFFSET));
		} else {
			setCenterX((int)(tileX*Constants.tileSize + Constants.tileSize/2.0 + Constants.X_OFFSET));
			setCenterY((int)((tileY + 1)*Constants.tileSize + Constants.Y_OFFSET));
		}
		
		setBounds((int)x, (int)y, getWidth(), getHeight());
		setRefresh(true, 0);
		goalX = getCenterX();
		goalY = getCenterY();
	}
	
	public ImageIcon resizeImg(String url, int width, int height) {
	
		BufferedImage bufferedImg;
		try {
			bufferedImg = ImageIO.read(getClass().getResource(url));	
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return resizeImg(bufferedImg, width, height);
	}
	
	public ImageIcon resizeImg(BufferedImage buffImg, int width, int height) {

		Image image = buffImg.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		return new ImageIcon(image);		
	}
	
	public void setX(double xCoord) {
		setLocation((int)(x = xCoord), (int)y);
		setRightX();
	}
	
	public void setY(double yCoord) {
		setLocation((int)x, (int)(y = yCoord));
		setBottomY();
	}
	
	protected void setRightX() {
		rightX = x + getWidth();
	}
	
	protected void setBottomY() {
		bottomY = y + getHeight();
	}
	
	public void setCenterX(double xCoord) {
	
		centerX = xCoord;
		setX((int) (centerX - getWidth()/2.0));
	}
	
	public void setCenterY(double yCoord) {
		
		centerY = yCoord;
		setY((int) (centerY - getHeight()));
	}
	
	public void setUpdater(Update upd8) {
		updater = upd8;
	}
	
	public void setRefresh(boolean x, double num) {
		isX = x;
		incr = num;
	}
	
	public void setGoalX(double xGoal) {
		goalX = xGoal + (int)Constants.X_OFFSET;
	}
	
	public void setGoalY(double yGoal) {
		goalY = yGoal + (int)Constants.Y_OFFSET;
	}

	public int getX() {
		return (int)x;
	}
	
	public int getY() {
		return (int)y;
	}
	
	public double getRightX() {
		return rightX;
	}
	
	public double getBottomY() {
		return bottomY;
	}
	
	public int getWidth() {
		return getPreferredSize().width;
	}
	
	public int getHeight() {
		return getPreferredSize().height;
	}
	
	public double getCenterX() {
		return centerX;
	}
	
	public double getCenterY() {
		return centerY;
	}
	
	public int getTileX() {
		return tileX;
	}
	
	public int getTileY() {
		return tileY;
	}
	
	public double getGoalX() {
		return goalX;
	}
	
	public double getGoalY() {
		return goalY;
	}
	
	public double deltaX() {
		return (int)(Math.abs(goalX - getCenterX()));
	}
	
	public double deltaY() {
		return (int)(Math.abs(goalY - getCenterY()));
	}
	
	public interface Update {
		public void refresh(boolean isX, double incr);
	}
	
	public void refresh() {
		updater.refresh(isX, incr);
	}
}
