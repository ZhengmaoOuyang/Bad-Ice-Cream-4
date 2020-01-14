package UFinal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Refresher implements Runnable {
	
	BadIceCreamFour bic4Obj = BadIceCreamFour.bic4;
	//LevelLoader lvlObj = bic4Obj.lvlLoader;
	
	private Thread t;
	private ActionListener timerListener;
	private Timer timer;
	
	
	public Refresher() {
		setTimerListener();
		timer = new Timer(1, timerListener);
		start();
	}
	
	public void start() {
		
		t = new Thread(this);
		t.run();
	}
	
	public void run() {
		
		timer.start();
		/*
		for(int i = 0; i < Constants.Y_TILEROWS; i++) {
			for(int j = 0; j < Constants.X_TILECOLS; j++) {
				lvlObj.getTileList().get(i).get(j).refresh();
			}
		}
		*/
	}
	
	public void setTimerListener() {
		
		timerListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//System.out.println(refreshCount);
				Constants.refreshCount++; 
				
				if(Constants.refreshCount > 1000000000) {
					Constants.refreshCount = 0;
				}
			}
		};
	}
}
