package towerdefence;

import java.awt.Graphics;
import java.awt.Image;


public class Chara implements Common{

	//このクラスを、味方キャラと、敵キャラが継承する

	//キャラ画像
	protected Image image;

	//キャラの名前
	protected String name;

	//キャラの座標
	private int x;

	private int y;

	//キャラの向き(配置場所によって固定)
	private int direction;

	//キャラアニメのカウンタ
	protected int count=0;

	//キャラアニメ用スレッド
	//ユニットは敵を攻撃するときに攻撃アニメに切り替え　それ以外は待機モーション
	private Thread animethread;

	//Viewへの参照
	protected View view;

	//modelへの参照
	protected Model model;


	//TODO:drawの実装

	//TODO:待機アニメーションの実装

	//カーソルもCharaクラスとなる
	//カーソルには不要な値があるため、コンストラクタを2つ作る

	public int Getx(){

		return x;

	}

	public int Gety(){

		return y;
	}


	public Chara (Model model,View view,String name,int x,int y, int direction,Image image){
		this.model=model;
		this.view=view;
		this.name=name;
		this.x=x;
		this.y=y;
		this.direction=direction;
		this.image=image;
		//directionは例えばnew Chara(x,y,IsRoad(x,y))とかやってぶん投げる


	}

	//model(いらないかも),viewを投げることを忘れず
	public Chara (Model model,View view,String name,int x,int y,Image image){
		this.model=model;
		this.view=view;
		this.name=name;
		this.x=x;
		this.y=y;
		this.image=image;

	}




	//setx,setyはほぼカーソル用
	//コントローラで、xやyをgetしてCS分インクリメントしてsetにぶん投げる
	public void Setx(int x){

		this.x=x;


	}

	public void Sety(int y){

		this.y=y;
	}

	public int GetDirection(){

		return direction;
	}

	public void SetDirection(int direction){

		this.direction=direction;
	}




	  public void draw(Graphics g) {
		  //count=this.model.GetPhase();
	        g.drawImage(this.image, x*CS, y*CS, x*CS+CS,  y*CS+CS,
	            0, 0, CS, CS, this.view);


	        //実はthis.viewは値がきちんと明示的に入ってないのにエラーが出ずちゃんと動く。コワイ!
	    }




	//親の引数つきコンストラクタは継承されないため、子供内で独自に実装する必要がある
	//また、子供であっても、親のprivateフィールドにはアクセスできないため、super()を使う。
	//なお、super()はメソッドの一番上にすること
}
