package towerdefence;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
public class BattleAnime implements ActionListener,Common{



	//アニメ画像
	private Image image;

	//アニメの名前
	private String name;

	//アニメのためのカウント
	private int count=0;

	//アニメ画像の枚数
	private int animsize;

	//アニメ終了フラグ
	public boolean isanimend=false;

	//規定のアニメの幅
	private int width=180;

	//規定のアニメの幅
	private int height=180;

	//表示位置
	private int x,y;

	//view参照
	private View view;

	//model参照
	private Model model;

	//アニメ再生速度
	private int wait;

	private Timer timer;

	public BattleAnime(String name, int animsize,int x,int y,int wait,View view,Model model,Image image){
		this.name=name;
		this.x=x;
		this.y=y;
		this.view=view;
		this.model=model;
		this.animsize=animsize;
		this.wait=wait;
		this.image=image;
		timer=new Timer(this.wait,this);
		timer.start();



	}

	  public void draw(Graphics g) {
		  if(name=="arrow"){
			  g.drawImage(image,(x-2)*CS-5,(y-2)*CS+10,(x-2)*CS+width-5,(y-2)*CS+10+height,count*width,0,width+width*count,height,view);
		  }else if(name=="fire"){
			  g.drawImage(image,(x-2)*CS+5,(y-2)*CS-5,(x-2)*CS+width+5,(y-2)*CS+height-5,count*width,0,width+width*count,height,view);
		  }else{
			  g.drawImage(image,(x-2)*CS,(y-2)*CS,(x-2)*CS+width,(y-2)*CS+height,count*width,0,width+width*count,height,view);
		  }

	    }



	@Override
	public void actionPerformed(ActionEvent e) {
		if(!isanimend){
		count++;
		model.Changed();
		if(count>=animsize){
		isanimend=true;
		}
		}

	}



	  //もし、モンスターの座標に小数を許容するのなら、
	  //ここだけは別にGetx,getyにroundかぶせなくてもよさそう

	//メモ：透過？　　http://www.geocities.jp/ntaka329/java/faq/ques_image_alpha.html


	  /*
	   *    g.drawImage(Image img,
               int dx1, int dy1, int dx2, int dy2,
               int sx1, int sy1, int sx2, int sy2,
               ImageObserver observer)
です。このメソッドはソース画像（img）の(sx1,sy1)-(sx2,sy2)の部分をgの(dx1,dy1)-(dx2,dy2)に描画できます。実際に画像を表示するコードは、

    // countの値に応じて表示する画像を切り替える
    g.drawImage(heroImage, x*CS, y*CS, x*CS+CS, y*CS+CS,
                count*CS, 0, CS+count*CS, CS, this);
	   *
	   *
	   * */
}
