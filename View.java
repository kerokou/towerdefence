package towerdefence;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;



public class View extends JPanel implements Observer,Common{
	protected Model model;

	public View(Model m,Controller c){
		 this.setBackground(Color.white);
		 setFocusable(true);
	     addKeyListener(c);
		 model = m;
		 model.addObserver(this);
		 model.view=this;
	}


	 public void paintComponent(Graphics g) {
		 super.paintComponent(g);

		 FieldMap map = model.GetMap();
		 Chara cursor = model.GetCursor();
		 PhasePanel phasepanel=model.GetPhasePanel();
		 map.draw(g);

		 //Moneyの表示
		 Font font = new Font("Buxton Sketch", Font.PLAIN, 25);
		 Graphics2D g2 = (Graphics2D)g;
		 g2.setFont(font);
		 g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
		 RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		 g2.drawString("Money:"+model.GetMoney(), 1*CS-10, 13*CS+15);




		 if(model.GetPhase()==ASSIGN){
			 cursor.draw(g);

		 }
		 //描画順に注意。地味に大切
		 phasepanel.draw(g);

		 //敵の描画
		 for(int i=0;i<model.GetEnemies().size();i++){
			 if(model.GetEnemies().get(i)!=null){
			 model.GetEnemies().get(i).draw(g);
			 }
		 }


		 for(Unit unit:model.GetUnits()){
			 if(unit!=null){
			 unit.draw(g);
			 }
		 }


		 //modelの方では、常に再生が終わったものを消去しているので、
		 //for文をぶん回している最中にその要素を消していることになる
		 //その時、拡張forを使っていると例外が発生するので使わない
		 //enemyもまた然り
		 for(int i=0;i<model.GetBattleAnimes().size();i++){
			 if(model.GetBattleAnimes().get(i)!=null){
			 model.GetBattleAnimes().get(i).draw(g);
			 }
		 }


		 if(model.gameend){
			 model.gepanel.draw(g);

		 }



		 //ゲームオーバーになったら、
		 //while(true)でぐるぐる回してゲームオーバー画像のみを表示
		 //if(gameover)while(true){描画処理}


	        	//TODO:マップ・キャラ・敵・カネ・カーソルの再描画
	        	//具体的にはマップのdrawを呼び出す
	        	//そして、modelが保持する配列を参照し、現在配置されているキャラ全てのdrawを呼び出す
	        	//キャラがスレッド内でnotify→viewが反応してpaintcomponent内でキャラのdrawを呼び出す


	        ///////////////////////仕様書//////////////////////////////
	        //paintcomponent内でマップのdrawを呼び出す　←済
	        //↓スレッド・タイマを使うからいらないかも
	        //modelが保持する配列を参照し、現在配置されているユニット全てのdrawを呼び出す
	        //modelが保持する配列を参照し、現在配置されている敵全てのdrawを呼び出す…☆
	        //modelが保持する配列を参照し、現在配置されている戦闘アニメのdrawを呼び出す
		 	//↑実は必要。多分スレッド・タイマではアニメ切り替え用のカウンタを増減させるだけだから、
		 	//描画そのものは別途行わなければならない
		 	//やり方はマップと同じ。
		 	//今回は配列をGetして、配列の各要素に対してdraw()を呼び出すだけ。

		 	//あと、カーソルの表示。
		 	//バトルフェイズだったら(modelのGetPhase()を使う)、カーソルのdrawを呼び出す…☆

		 	//あと、カネの表示
		 	//こちらは常時表示
		 	//画像表示ではなく、数字で構わないです

		 	//あと、フェイズ状態の表示(今、バトルフェイズなのか、アサインフェイズなのか)…☆
		 //こちらも常時表示

		 //サンプルとして、☆の部分は実装しておきました
		 //それを参考にして、書いてみてください
	 }
	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}

}
