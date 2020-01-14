package UFinal.tiles;

public class IceTile extends Tile {

	public IceTile(int xTile, int yTile, int width, int height) {
		super("/UFinal/img/Ice_Block.png", xTile, yTile, width, height);
	}
	
	public IceTile(int xTile, int yTile) {
		super("/UFinal/img/Ice_Block.png", xTile, yTile);
	}
	
	public void refresh() {};
}

