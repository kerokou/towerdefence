package towerdefence;

import java.awt.Graphics;
import java.awt.Image;

public class PhasePanel extends Chara implements Common{

	public PhasePanel(Model model, View view, String name, int x, int y,Image image) {
		super(model, view, name, x, y,image);

	}

	 public void draw(Graphics g) {
		  count=this.model.GetPhase();

	       g.drawImage(this.image,
	    		   Getx()*CS,
	    		   Gety()*CS,
	    		   Getx()*CS+this.image.getWidth(this.view),
	    		   Gety()*CS+this.image.getHeight(view)/2,
	            0, count*image.getHeight(view)/2, image.getWidth(view), image.getHeight(view)/2+count*image.getHeight(view)/2, this.view);




	    }
}
