package towerdefence;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener,Common{

	//modelへの参照
	private Model model;

	@Override
	public void keyTyped(KeyEvent e) {


	}

	@Override
	public void keyPressed(KeyEvent e) {

		if(!model.gameend){
		//↓これは、keytypedでは使うな
		int key = e.getKeyCode();
		  if (key == KeyEvent.VK_SPACE&&model.GetPhase()==ASSIGN){
			  model.AssigntoBattle();
			  model.Changed();
		  }else if(key == KeyEvent.VK_UP){
			  model.GetCursor().Sety(model.GetCursor().Gety()-1);
			  //何か変化を起こしたら必ずこれを書く↓
			 model.Changed();
		  }else if(key == KeyEvent.VK_LEFT){
			  model.GetCursor().Setx(model.GetCursor().Getx()-1);
			  model.Changed();
		  }else if(key == KeyEvent.VK_DOWN){
			  model.GetCursor().Sety(model.GetCursor().Gety()+1);
			  model.Changed();
		  }else if(key == KeyEvent.VK_RIGHT){
			  model.GetCursor().Setx(model.GetCursor().Getx()+1);
			  model.Changed();
		  }else if(key == KeyEvent.VK_1&&model.GetMoney()>=model.soldiercost&&model.GetPhase()==ASSIGN&&model.GetMap().ValidatePlace(model.GetCursor().Getx(),model.GetCursor().Gety())){
			  model.MakeSoldier(model.GetCursor().Getx(), model.GetCursor().Gety());
			  model.Changed();
			  model.AddMoney(-model.soldiercost);
		  }else if(key == KeyEvent.VK_2&&model.GetMoney()>=model.archercost&&model.GetPhase()==ASSIGN&&model.GetMap().ValidatePlace(model.GetCursor().Getx(),model.GetCursor().Gety())){
			  model.MakeArcher(model.GetCursor().Getx(), model.GetCursor().Gety());
			  model.Changed();
			  model.AddMoney(-model.archercost);
		  }else if(key == KeyEvent.VK_3&&model.GetMoney()>=model.wizardcost&&model.GetPhase()==ASSIGN&&model.GetMap().ValidatePlace(model.GetCursor().Getx(),model.GetCursor().Gety())){
			  model.MakeWizard(model.GetCursor().Getx(), model.GetCursor().Gety());
			  model.Changed();
			  model.AddMoney(-model.wizardcost);
		  }
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {


	}

	public Controller(Model model){
		this.model=model;


	}

	//バトルフェイズの時は基本的に操作を受け付けない
	//アサインフェイズ(味方を配置するフェイズ)時は、上下左右キーでカーソル移動、
	//キャラをおける領域(敵進軍ルートの周囲、ちなみに向きは場所により固定)Zキーを押すと配置する味方の選択肢が出てきて、
	//上下キーで選択してZキーで決定
	//Xボタンを押すと選択肢が消える
	//選択肢が出ていないとき、Sキーを押すと敵進軍開始

	//味方を配置した際はmodelを呼び出してデータを書き換える

	//選択肢を出すのがめんどいなら、キーに始めっから割りあてるのもあり






	////////////////////////////////　仕様書　//////////////////////////////////////////////
	//1,2,3キーはキャラを配置する
	//スペースキーを押すと敵の進軍開始、つまりバトルフェイズへ移行…☆
	//上下左右キーでカーソルを移動する…☆
	//アサインフェイズのみ操作を受け付け、バトルフェイズ時は操作を一切受け付けない
	//1キーを押すと近接型キャラを設置
	//2キーで弓
	//3キーで魔法使い
	//ただし、ユニットはカネ(money)が一定以上でなければ使えない

	//注：基本的には、押されたキーを調べて、押されたキーごとに、それぞれModelに書かれた関数を呼び出すだけでよい
	//ユニット設置に必要なコストはUnitクラスにある



	//サンプルとして☆の部分は実装しておきました、それを参考にして書いてみてください



	////////////////////////////////////////////////////////////////////////////////////////


}
