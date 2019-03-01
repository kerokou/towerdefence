package towerdefence;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Enemy extends Chara implements ActionListener{
	//このクラスで敵・ボスを作る　継承はしない

		/*Charaからの継承
		//キャラ画像
		private Image image;

		//キャラの座標
		private int x,y;

		//キャラの向き(配置場所によって固定)
		private int direction;

		//キャラアニメのカウンタ
		private int count;

		//キャラアニメ用スレッド
		private Thread animethread;

		//Viewへの参照
		private View view;
		*/

	//加算合成は自作かopengl ちょっとopengl使っていいか怪しい


	public Enemy(Model model,View view,String name,int x, int y,Image image,int hp,int drop,int wait) {
		super(model, view, name,x,y,image);
		this.hp=hp;
		this.drop=drop;
		this.wait=wait;
		this.SetDirection(DOWN);//敵は最初は必ず下向き
		timer=new Timer(wait,this);
		timer.start();
		//hpとかもここで初期化
	}

	private Timer timer;

	//エネミー体力
	private int hp;

	//落とすカネ
	private int drop;

	//スレッドのウェイト
	private int wait;

	//ゲームオーバー座標到達
	private boolean isarrived=false;

	//消滅効果音
	private SoundClip disappearsound;



	//方向転換する座標群 xsの中で現在のx座標と等しいものがあったら、xsのindexを受け取って対応するysのなかのy座標(つまりindex番目のy座標)とも一致するか調べる
	//ちなみに方向転換座標は、左上を(0,0)とすれば(3,6)(6行3列目)と(14,6)(6行14列目)
	private int [] turnxs={3,14};
	private int [] turnys={6};

	//ゲームオーバー座標(到達したら負けとなる座標)
	//(15,15)
	//これはCommonにぶちこんでよくないか?→ぶちこんだ
	//ゲームオーバー座標への到達回数が5回になったら負け(ゲーム開始からの累計)

	//TODO:消滅メソッド

	//アニメスレッド内に移動処理挟むより、移動の方はtimerでいいんじゃないかしら
	//あと、hpが0になっただけでなく、ゲームオーバー座標にたどり着いても消滅だから、フラグを立てる
	//また、hpが0になって消滅するときは何か消滅したっぽいアニメが欲しいかも

	//敵が消滅アニメを生成して、味方が戦闘アニメを生成するってのでいいのかね

	//timerでメソッドを永遠に呼び出して、座標がゲームオーバー座標に一致したらフラグ立ててゲームオーバーカウント一つ進めて消滅
	//タイマで座標のインクリメント、スレッドでアニメカウンタの切り替えって感じでどうでしょうか
	//でも、めんどいから足踏みきりかえと進むタイミングは一致させたほうがいいかも
	//タイマ使わなくていいし
	//カクカクした動きになるけどそれは仕方ない　攻撃メソッドがめんどくなる
	//んで、普通の敵はsleep30で、早い敵はsleep20みたいな感じで

	//TODO:敵グラフィックは左足・右足だけの2枚*4方向に編集しておく

	  public void draw(Graphics g) {

	        g.drawImage(this.image, this.Getx() * CS, this.Gety() * CS, this.Getx() * CS + CS, this.Gety()* CS + CS,
	            count * CS, this.GetDirection() * CS, count * CS + CS, this.GetDirection() * CS + CS, this.view);
	    }


	//hpゲット　セットはいらないと思う
	public int GetHp(){

		return this.hp;

	}
	public void AddHp(int damage){

		hp=hp+damage;

	}

	public boolean IsArrived(){
		return this.isarrived;

	}





	@Override
	public void actionPerformed(ActionEvent e) {
		if(!isarrived&&this.hp>0){


				if(count==0){
					count=1;
					model.Changed();
				}else{
					count=0;
					model.Changed();
				}
				//ここでnotify

				//移動
				if(this.GetDirection()==DOWN){
					this.Sety(this.Gety()+1);
					model.Changed();
				} else if(this.GetDirection()==RIGHT){
					this.Setx(this.Getx()+1);
					model.Changed();
				}
				//ここでnotify

				//方向転換
				for(int x:turnxs){
					for(int y:turnys){
						if(this.Getx()==x&&this.Gety()==y){

							this.SetDirection(this.GetDirection()+(1-this.GetDirection())*2);
							//0+(1-0)*2=2 2+(1-2)*2=0
							//DOWN(0)→RIGHT(2)→DOWN(0)だから
							model.Changed();
						}
					}

				}
				//ここでnotify

				//ゲームオーバー座標到達時
				if(this.Getx()==GAMEOVERX&&this.Gety()==GAMEOVERY&&!isarrived){

						isarrived=true;
						model.gocount++;
						//System.out.print(model.gocount);

						model.Changed();
						//notify

				}
		}
				//死亡時
				if(this.hp<=0){

					model.MakeDisappearAnime(this.Getx(), this.Gety());
					model.Changed();
					model.AddMoney(this.drop);
					if(model.disappearcount%11==0)model.disappearcount=1;
					if(disappearsound==null){
					disappearsound=new SoundClip("disappear"+model.disappearcount+".wav");
					model.disappearcount++;
					}
					disappearsound.play();
					timer.stop();
					//model.disappearsound.play();
					//TODO:ここでアニメ再生
					//notify

					//notifyするのは実際にはmodelの役目なので何とかする
					//ただし、notifyはmodelがここに渡されていればmodel経由で呼び出し可能
					//実際、大量のオブジェクトがカウンタ切り替え時にnotifyするのでそんな気にする必要ない？
				}







	}






}
