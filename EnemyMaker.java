package towerdefence;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class EnemyMaker implements ActionListener{

	public int count=0;
	public boolean onbattlebegin=false;
	private Model model;
	private int enecount=0;
	private Timer timer=new Timer(1000,this);


	public EnemyMaker(Model model){
		this.model=model;
		timer.start();

	}







	@Override
	public void actionPerformed(ActionEvent e) {
		if(onbattlebegin){
			switch(count){
			case 1:



					model.GetEnemies().add(model.MakeGob(1));
					enecount++;
					if(enecount==3){
				onbattlebegin=false;
				enecount=0;
					}
				break;
			case 2:


					model.GetEnemies().add(model.MakeDwf(1));
					enecount++;
					if(enecount==3){
				onbattlebegin=false;
				enecount=0;
					}
				break;
			case 3:

					//model.GetEnemies().add(model.MakeGob(2));


					model.GetEnemies().add(model.MakeIns(1));
					enecount++;
					if(enecount==3){
						onbattlebegin=false;
					enecount=0;
					}

				break;
			case 4:


					model.GetEnemies().add(model.MakeWlf());
					enecount++;
					if(enecount==3){

			onbattlebegin=false;
			enecount=0;
					}
					break;
			}

		}

	}






}
