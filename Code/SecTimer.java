package UFinal;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import UFinal.lvl.LevelLoader;

public class SecTimer extends JLabel {

	private BadIceCreamFour bic4Obj = BadIceCreamFour.bic4;
	private LevelLoader lvlObj = bic4Obj.lvlLoader;

	private int init, secsLeft;
	
	public SecTimer(int secLimit, int x, int y) {
		
		secsLeft = secLimit;
		init = Constants.refreshCount;
		setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		setForeground(Color.BLACK);
		setText("Time left: " + secsLeft + "s");
		
		setBounds((int)x, (int)y, getPreferredSize().width, getPreferredSize().height);
	}
	
	public void refresh() {
		
		if(!Constants.gameLost && !Constants.gameWon && Math.abs(Constants.refreshCount - init) >= 1000) {
			
			secsLeft = Math.max(0, secsLeft - 1);
			setText("Time left: " + secsLeft + "s");
			init = Constants.refreshCount;
			
			if(secsLeft == 0) {
				lvlObj.gameLost();
			}
		}
	}
}
