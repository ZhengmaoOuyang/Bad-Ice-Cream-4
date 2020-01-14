package UFinal;

public class Constants {

	public static final int X_LEFTINSET = 11;
	public static final int X_RIGHTINSET = 11;
	public static final int Y_TOPINSET = 45;
	public static final int Y_BOTTOMINSET = 11;
	
	public static double X_OFFSET = 0;
	public static double Y_OFFSET = 0;
	
	public static final int CHAR_WIDTH = 36;
	public static final int CHAR_HEIGHT = 55;
	
	public static final int FRAME_WIDTH = 1250;
	public static final int FRAME_HEIGHT = 750;
	
	public static final int FRAME_WIDTH_ADJUSTED = FRAME_WIDTH - X_LEFTINSET - X_RIGHTINSET;
	public static final int FRAME_HEIGHT_ADJUSTED = FRAME_HEIGHT - Y_TOPINSET - Y_BOTTOMINSET;
	
	public static int X_TILECOLS = 0; //dummy
	public static int Y_TILEROWS = 0;
	public static int tileSize = 0;
	
	public static int refreshCount = 0;
	
	public static int numFruit = 0;
	public static int fruitLvl = 0;
	
	public static int numPlayers = 1; 
	public static int numDead = 0;
	
	public static boolean gameWon = false;
	public static boolean gameLost = false;
	public static boolean levelLoaded = false;
	
	public static boolean musicEnabled = true;
	public static boolean sfxEnabled = true;

}
