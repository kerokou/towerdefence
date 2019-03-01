package towerdefence;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.ImageIcon;
import javax.swing.Timer;

/*
 *
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
 * */

public class Model extends Observable implements Common,ActionListener{


	/*メモ
	ArrayList<String> array = new ArrayList<String>();

	array.add("東京");
	array.add("大阪");
	array.add("東京");
	array.add("千葉");

	array.remove(1);
	array.clear();//全消去

	参考:http://www.javadrive.jp/start/arraylist/index5.html

	でも、敵クラスそのものが
	「あ、俺HPが0になった、modelさん、俺を現在の敵配列から消してくれ」
	というのは不可能に近い

	逆に、常に敵配列の更新を行うメソッドをぐるぐる回しといて、
	配列内の敵のHPを順に調べていって0以下だったらその都度消す、みたいな方が現実的
	アニメも同様かな　アニメの場合は再生終了フラグを立てとくとか

	*/



    //マップを保持する配列
    //private ArrayList<FieldMap> fieldmaps;

	//やっぱりマップは一種類？
	private FieldMap map;

    //配置できるキャラの配列
    //private ArrayList<Unit> selectablecharas;

    //現在配置されているキャラの配列
	private ArrayList<Unit> availableunits=new ArrayList<Unit>();

	//敵キャラの配列 new忘れるな！
	private ArrayList<Enemy> enemies=new ArrayList<Enemy>();

	//現在再生されているアニメ
	//仕様上、多分キャラも入れられるけど入れないで
	//Runnableをimplementsする
	//http://d.hatena.ne.jp/MrRadiology+IntroductionToProgramming/20160726/1469491484
	private ArrayList<BattleAnime> battleanimes=new ArrayList<BattleAnime>();

	//カネ
	private int money=400;

	public int soldiercost=60,archercost=90,wizardcost=150;

	//現在のフェイズ
	private int phase;

	//ゲームオーバーカウント　5になるとゲームオーバー
	public int gocount=0;

	//カーソル
	private Chara cursor;

	//フェイズ表示・ゲーム終了表示
	public PhasePanel phasepanel,gepanel;

	//viewへの参照
	public View view;

	//BGMもmodelが担当してみる
	private SoundClip assignbgm,battlebgm;

	//効果音もmodelが担当
	public SoundClip firesound,arrowsound,swordsound,disappearsound;

	//ゲーム終了ジングルも
	private SoundClip gcjingle,gojingle;

	private EnemyMaker enemymaker;

	public boolean gameend=false;

	private Image gob1,gob2,ins1,ins2,dwf1,dwf2,wlf;

	private Image soldier,archer,wizard;

	private Image fire,arrow,sword,disappear;

	private Image cursorimage,ppimage,geimage;

	public int arrowcount=1,swordcount=1,disappearcount=1,firecount=1;

	private Timer timer;


	//TODO:いちいちnewしてコンストラクタで画像を読み込むのがバカバカしくないですか？はじめからここで読み込んで、newするとき投げればいいのでは？

	 public Model() {
		 map=new FieldMap();


		 assignbgm=new SoundClip("assign.wav");
		 battlebgm=new SoundClip("battle.wav");
		 firesound=new SoundClip("fire.wav");
		 arrowsound=new SoundClip("arrow.wav");
		 swordsound=new SoundClip("sword.wav");
		 disappearsound=new SoundClip("disappear.wav");
		 gcjingle=new SoundClip("gameclear.wav");
		 gojingle=new SoundClip("gameover.wav");
		 timer=new Timer(20,this);


		 ImageIcon icon = new ImageIcon(getClass().getResource("gob1.png"));
	        gob1 = icon.getImage();

	        icon = new ImageIcon(getClass().getResource("gob2.png"));
	        gob2 = icon.getImage();

	        icon = new ImageIcon(getClass().getResource("ins1.png"));
	        ins1 = icon.getImage();

	        icon = new ImageIcon(getClass().getResource("ins2.png"));
	        ins2 = icon.getImage();

	        icon = new ImageIcon(getClass().getResource("dwf1.png"));
	        dwf1 = icon.getImage();

	        icon = new ImageIcon(getClass().getResource("dwf2.png"));
	        dwf2 = icon.getImage();

	        icon = new ImageIcon(getClass().getResource("wlf.png"));
	        wlf = icon.getImage();

	        icon = new ImageIcon(getClass().getResource("soldier.png"));
	        soldier = icon.getImage();

	        icon = new ImageIcon(getClass().getResource("archer.png"));
	        archer = icon.getImage();

	        icon = new ImageIcon(getClass().getResource("wizard.png"));
	        wizard = icon.getImage();

	        icon = new ImageIcon(getClass().getResource("fire.png"));
	        fire = icon.getImage();

	        icon = new ImageIcon(getClass().getResource("arrow.png"));
	        arrow = icon.getImage();

	        icon = new ImageIcon(getClass().getResource("sword.png"));
	        sword = icon.getImage();

	        icon = new ImageIcon(getClass().getResource("cursor.png"));
	        cursorimage = icon.getImage();

	        icon = new ImageIcon(getClass().getResource("gepanel.png"));
	        geimage = icon.getImage();

	        icon = new ImageIcon(getClass().getResource("phasepanel.png"));
	        ppimage = icon.getImage();

	        icon = new ImageIcon(getClass().getResource("disappear.png"));
	        disappear = icon.getImage();

		 enemymaker=new EnemyMaker(this);
		 cursor=new Chara(this, view, "cursor", 0, 0,cursorimage);
		 phasepanel=new PhasePanel(this,view,"phasepanel",14,0,ppimage);
		 gepanel=new PhasePanel(this,view,"gepanel",5,5,geimage);
		 timer.start();
		BattletoAssign();

		 //これを書いてviewに通知
		 Changed();


		 //TODO:配置できるキャラを作成する
	      //TODO: 作成可能なキャラの配列を作る
		 //何か動きがあればnotify()する
		 //最初はアサインフェイズなので、各種オブジェクトを作成したら、controllerが関数を呼び出すのを待つ
		 //んで、controllerがバトルフェイズ関数をmodelから呼び出す
		 //バトルフェイズ関数の最後に、アサインフェイズに移行
		 //カネは右上などに表示しておきたい(常に再描画する必要あり?)
}

	 //他のクラスから呼び出せるように苦し紛れの処理
	 //ついでに２つの関数をまとめたのでちょっと書くのがラクになる
	public void Changed(){
		super.setChanged();
		super.notifyObservers();
	}


	 //phaseのゲット、セット
	 public void SetPhase(int phase){
		 this.phase=phase;

	 }
	 public int GetPhase(){
		 return phase;
	 }

	 public Chara GetCursor(){
		 return cursor;

	 }

	 public PhasePanel GetPhasePanel(){
		 return phasepanel;

	 }

	 public ArrayList<Unit> GetUnits(){
		 return availableunits;

	 }

	 public ArrayList<BattleAnime> GetBattleAnimes(){

		 return battleanimes;
	 }

	 //アサインフェイズへの移行
	 public void BattletoAssign(){

		 if(gocount<5){
			 SetPhase(ASSIGN);
		 }
		 if(battlebgm.isRunning()){
			 battlebgm.stop();
		 }



		 //クリア処理
		 if(enemymaker.count==4){
			 gameend=true;
			 Changed();
			 gcjingle.play();
			 }

		 if(!gameend){
			 assignbgm.loop();
		 }

		 //カーソルを出す
		 //プレイヤー側にフェイズ移行の通知をする？
		 //↑なんか、右から左へ画像を流すとか
		 //setphase


	 }

	 public void AssigntoBattle(){
		 enemymaker.count++;
		 enemymaker.onbattlebegin=true;

		 SetPhase(BATTLE);

		 if(assignbgm.isRunning()){
			 assignbgm.stop();
		 }
		 battlebgm.loop();
		 //カーソルを消す
		 //プレイヤー側に通知？
		//↑なんか、右から左へ画像を流すとか
		 //右上で表示変えるとか
		 //setphase
		 //敵呼び込み

	 }

	 //各モンスター生成
	 public Enemy MakeDwf(int num){
		 if(num==1){
			 return new Enemy(this,view,"dwf"+num,3,0,dwf1,100,100,150);
		 }else{
			 return new Enemy(this,view,"dwf"+num,3,0,dwf2,100,100,150);
		 }

	 }

	 public Enemy MakeGob(int num){
		 if(num==1){
			 return new Enemy(this,view,"gob"+num,3,0,gob1,100,100,250);//300だとちょっと遅い
		 }else{
			 return new Enemy(this,view,"gob"+num,3,0,gob2,100,100,250);
		 }

	 }

	 public Enemy MakeIns(int num){
		 if(num==1){
			 return new Enemy(this,view,"ins"+num,3,0,ins1,100,100,120);
		 }else{
			 return new Enemy(this,view,"ins"+num,3,0,ins2,100,100,220);
		 }

	 }

	 public Enemy MakeWlf(){
		return new Enemy(this, view, "wlf", 3, 0,wlf,1200,1000,600);

	 }

	 //各アニメ生成
	 public void MakeFireAnime(int x,int y){
		 battleanimes.add(new BattleAnime("fire", 18, x, y, 8,view, this,fire));
	 }

	 public void MakeSwordAnime(int x,int y){
		 battleanimes.add(new BattleAnime("sword", 16, x, y, 20,view, this,sword));
	 }

	 public void MakeArrowAnime(int x,int y){
		 battleanimes.add(new BattleAnime("arrow", 16, x, y, 8,view, this,arrow));
	 }

	 public void MakeDisappearAnime(int x,int y){
		 battleanimes.add(new BattleAnime("disappear", 3, x, y, 20,view, this,disappear));
	 }

	 public void MakeSoldier(int x,int y){
		 availableunits.add(new Unit(this, view, "soldier", x, y, this.map.SpecifyDirection(x, y), soldier,true, 30, 1, 230));
	 }

	 public void MakeArcher(int x,int y){
		 availableunits.add(new Unit(this, view, "archer", x, y, this.map.SpecifyDirection(x, y),archer, false, 5, 5, 180));
	 }

	 public void MakeWizard(int x,int y){
		 availableunits.add(new Unit(this, view, "wizard", x, y, this.map.SpecifyDirection(x, y), wizard,false, 20, 8, 800));
	 }

	 public void Setview(View view){
		 this.view=view;

	 }

	 public void GameOver(){
		 gameend=true;
		 if(battlebgm.isRunning()){
			 battlebgm.stop();
		 }
		 gojingle.play();
		 Changed();
		 //System.out.print("GameOver");

		 //ゲームオーバー処理
		 //↑なんか、右から左へ画像を流すとか
		 //それで終了でもいいんじゃない

	 }

	 public void PlaceChara(Unit unit,int direction){
		 //キャラを配置
		 //上下左右どこが道路かで向きが変わるのだが、
		 //複数の場所が道路である場合上>右>下>左の順で優先
		 //して、directionを投げること
		 //notify()

	 }

	 //カネの操作　増やすときは正の値を投げ、減らすときは負の値を投げる
	 //まあ、無いとは思うけど一応moneyが負の値を取らないようにしておく
	 public void AddMoney(int d){
		 if(money+d<0){
			money=0;
		 }else{
		 money=money+d;
		 }

	 }

	 //カネのゲット
	 public int GetMoney(){
		 return money;
	 }

	 //マップのゲット
	 public FieldMap GetMap(){
		 return map;

	 }

	 //敵配列ゲット
	 public ArrayList<Enemy> GetEnemies(){
		return enemies;


	 }


	 //ゲームオーバー判定 永久に回す
	 public void IsGameOver(){
		 if(gocount>=5){

			 GameOver();

		 }

	 }

	 //エネミー死亡判定兼ゲームオーバー座標到達判定　これを永久に回す
	 public void IsEnemyDied(){
		 //Enemy enemy;

		 for(int i=0;i<enemies.size();i++){

			 if(enemies.get(i)!=null){
			 if(enemies.get(i).GetHp()<=0||enemies.get(i).IsArrived()){
				enemies.remove(i);

			 }
			 }
		 }
		 }

		//アニメ終了判定　これを永久に回す
	 public void Isanimend(){
		  BattleAnime battleanime;
		  if(!battleanimes.isEmpty()){
		 for(int i=0;i<battleanimes.size();i++){
			 battleanime=battleanimes.get(i);
			 if(battleanime!=null){
			 if(battleanime.isanimend){
				battleanimes.remove(i);

					 }
			 }


		 }
		  }

		 }



		@Override
		public void actionPerformed(ActionEvent e) {
			if(!gameend){
				IsGameOver();
				IsEnemyDied();
				Isanimend();



				if(this.enemies.isEmpty()&&!enemymaker.onbattlebegin&&phase==BATTLE&&gocount<5){
					BattletoAssign();
				}

				}
		}






}
