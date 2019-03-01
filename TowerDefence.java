package towerdefence;

import java.awt.Color;

import javax.swing.JFrame;


public class TowerDefence extends JFrame{
	Model model;
	//view,controllerもここで宣言、コンストラクタでnew
	View view;
	Controller controller;

	public TowerDefence(){
		model=new Model();
		controller=new Controller(model);
		view=new View(model, controller);
		this.setBackground(Color.black);
	      this.setTitle("Tower Defence");
	      this.setSize(640,480);
	     this.add(view);
	      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      this.setVisible(true);
	      model.Setview(view);

	}
	public static void main(String argv[]) {
	      new TowerDefence();
	   }

}
