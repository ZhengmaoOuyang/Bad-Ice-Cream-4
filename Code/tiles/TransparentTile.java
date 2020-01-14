package UFinal.tiles;

public class TransparentTile extends Tile {

	public TransparentTile(int xTile, int yTile, int width, int height) {
		super("/UFinal/img/transparentTile.png", xTile, yTile, 1, 1);
		setOpaque(false);
	}
	
	public TransparentTile(int xTile, int yTile) {
		super("/UFinal/img/transparentTile.png", xTile, yTile, 1, 1);
		setOpaque(false);
	}

	public void refresh() {};
}
