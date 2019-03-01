package towerdefence;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class Unit extends Chara implements ActionListener{
	//もうこれ以上継承はしない
	//ここで味方キャラを作る
	//特殊な攻撃をする戦士は、別途特別にsearchandattackを書く必要があるようにも思えるが、
	//もうめんどいのでフラグで管理


	//多分待機アニメと戦闘アニメは一つの画像で扱う
	//その分引数の指定がめんどくなるけど

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

		public Unit(Model model,View view,String name,int x, int y, int direction,Image image,boolean issoldier,int atk,int atkarea,int wait) {
		super(model, view, name,x, y, direction,image);
		this.issoldier=issoldier;
		this.atk=atk;
		this.atkarea=atkarea;
		this.wait=wait;
		Timer timer=new Timer(this.wait,this);
		timer.start();
		//atkとかもここで初期化
		//敵の配列をこのクラスは参照しなければいけないのだから、コンスタラクタに敵配列を渡さなければならない
	}

		//キャラの攻撃力
		private int atk;

		//キャラの攻撃範囲(半径) abs(x1-x2)+abs(y1-y2)<=atkarea ? true : false などと使う
		private int atkarea;

		//敵の配列
		private ArrayList <Enemy>enemies;

		//現在攻撃可能な敵の配列
		private ArrayList <Enemy> targets=new ArrayList<Enemy>();

		//targets内の敵とゲームオーバー座標との距離の最小値
		private int dist_min;

		//攻撃する候補(targetsの中で、最もゲームオーバー座標に近い敵たち)
		private ArrayList<Enemy> candidates=new ArrayList<Enemy>();

		//乱数
		private int rand;

		//お前は戦士か?
		private boolean issoldier=false;

		//今、攻撃中？
		private boolean isattacking=false;

		//攻撃速度
		private int wait;

		//効果音
		private SoundClip soundclip;


		/*public void attack(){
			//TODO:敵の配列を参照し自分が攻撃可能な敵を探し、一番ゲームオーバ座標に近い敵の中からランダムに攻撃　その後ウェイト(つまりthreadを使う)を挟む？
			//TODO:これインタフェースでよくない? 個別の味方ユニットクラスでimplements

		}*/


		//バトルフェイズ終了時に呼び出し？　スレッドのオフ？
		public void stopattack(){


		}

		 public void draw(Graphics g) {

		        g.drawImage(this.image, this.Getx() * CS, this.Gety() * CS, this.Getx() * CS + CS, this.Gety()* CS + CS,
		            count * CS, this.GetDirection() * CS, count * CS + CS, this.GetDirection() * CS + CS, this.view);
		    }



		//TODO:ドローの実装
		//TODO:攻撃速度と待機速度は違うはずなので、二種類のウェイトを用意する




		//もし敵の動きをなめらかにしたら、getxとgetyを四捨五入してから処理する
		//多分、エネミーの動きを、x,yを1ずつインクリメントするんじゃなくて、
		//x,yを0.2ずつぐらいインクリメントする、みたいな実装になると思うから
		//たぶん四捨五入でうまくいく
		public void SearchandAttack() {
			int enemyx,enemyy;
			//最初に配列初期化・敵の更新
			enemies=model.GetEnemies();
			targets.clear();
			candidates.clear();

			if(enemies.isEmpty()){
				isattacking=false;
				return;
			}

			//まずは敵のサーチ

			for(int i=0;i<model.GetEnemies().size();i++){
				if(model.GetEnemies().get(i)!=null){
				enemyx=model.GetEnemies().get(i).Getx();
				enemyy=model.GetEnemies().get(i).Gety();
				if(Math.abs(this.Getx()-enemyx)+Math.abs(enemyy-this.Gety())<=atkarea){
					targets.add(model.GetEnemies().get(i));

				}
				}

			}
			if(!targets.isEmpty()){
				isattacking=true;
			//距離最小値特定
			dist_min=Math.abs(targets.get(0).Getx()-GAMEOVERX)+Math.abs(targets.get(0).Gety()-GAMEOVERY);
			for(Enemy enemy:targets){
				if(enemy!=null){
				if(dist_min>Math.abs(enemy.Getx()-GAMEOVERX)+Math.abs(enemy.Gety()-GAMEOVERY)){
					dist_min=Math.abs(enemy.Getx()-GAMEOVERX)+Math.abs(enemy.Gety()-GAMEOVERY);


				}
				}

			}

			//攻撃候補の最終決定
			for(Enemy enemy:targets){
				if(Math.abs(enemy.Getx()-GAMEOVERX)+Math.abs(enemy.Gety()-GAMEOVERY)==dist_min){
					candidates.add(enemy);

				}

			}

			if(!issoldier){
			//候補の中からランダム決定
			rand=(int) Math.floor(Math.random()*candidates.size());

			if(candidates.get(rand)!=null){
			//んで、攻撃
			candidates.get(rand).AddHp(-atk);
			//candidatesがもし空だったら攻撃しない
			//アニメも待機に戻す
			if(this.name=="archer"){
				model.MakeArrowAnime(candidates.get(rand).Getx(), candidates.get(rand).Gety());
				if(model.arrowcount%47==0)model.arrowcount=1;
				if(soundclip==null){
				soundclip=new SoundClip("arrow"+model.arrowcount+".wav");

				model.arrowcount++;
				}
				soundclip.play();
				//model.arrowsound.play();

			}else{
				model.MakeFireAnime(candidates.get(rand).Getx(), candidates.get(rand).Gety());

				if(model.firecount%47==0)model.firecount=1;
				if(soundclip==null){
				soundclip=new SoundClip("fire"+model.firecount+".wav");
				model.firecount++;
				}
				soundclip.play();
				//model.firesound.play();
			}
			}

			//TODO:んで、アニメ再生,changed
			}else{
					for(Enemy enemy:targets){
						enemy.AddHp(-atk);
						//戦士は攻撃範囲が1なので、距離の最小値を特定する必要がない
						//さらに、マップの特性上、戦士をどこに置いてもある方向にしか敵が来ない
						//だから、この方法でも上の敵、右の敵を一気に攻撃することはない
						//というかこうなるようにマップを作った

						//TODO:んで、アニメ再生,changed
					}
					model.MakeSwordAnime(targets.get(0).Getx(), targets.get(0).Gety());
					if(model.swordcount%47==0)model.swordcount=1;
					if(soundclip==null){
					soundclip=new SoundClip("sword"+model.swordcount+".wav");
					model.swordcount++;
					}
					soundclip.play();
					//model.swordsound.play();

				}


			}else{
				isattacking=false;
			}
		}
			/*
			 * 値を切り上げたり四捨五入したりした結果の値を求める方法です。
			 * 「ceil」メソッドは切り上げ、「
			 * floor」メソッドは切捨て、
			 * 「round」メソッドは四捨五入した結果を返します。
			 * 「round」メソッドには引数にdouble型の値を取るものとfloat型の値を取るものの2つが用意されています。
			 * http://www.javadrive.jp/start/math/index7.html
			 *
			 * 値      切り上げ   切り捨て   四捨五入
			---------------------------------------
 				1.34     2.0        1.0        1
 				3.67     4.0        3.0        4
			   -0.23    -0.0       -1.0        0
			   -3.89    -3.0       -4.0       -4*/

		@Override
		public void actionPerformed(ActionEvent e) {
			if(isattacking){
				//攻撃時は2,3,4
				if(count>=4||count<=0){
				count=1;
				}
				count++;
			}else{
				//非攻撃時は0,1
				if(count==0){
					count=1;
				}else{
					count=0;
				}
			}

			SearchandAttack();
			model.Changed();

		}

		}












