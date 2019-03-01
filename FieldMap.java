package towerdefence;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class FieldMap implements Common{

	  private static final int ROW = 15;
	  private static final int COL = 20;


	   public FieldMap() {

	        loadImage();
	        for(int i=0;i<15;i++){
	        	for(int j=0;j<20;j++){
	        		placedpoint[i][j]=false;
	        	}
	        }
	    }

	  private int[][] map = {
		        {0,0,0,1,0,0,8,0,0,0,0,0,0,0,5,0,0,0,8,0},
		        {0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,6,0,0,0},
		        {0,0,0,1,0,8,0,0,0,0,5,0,0,3,0,0,0,0,7,0},
		        {0,4,0,1,0,0,5,0,0,0,0,0,0,0,0,0,0,0,0,0},
		        {6,0,0,1,0,0,0,0,3,0,0,0,6,0,0,0,0,4,0,0},
		        {0,0,0,1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		        {0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,2,0},
		        {0,0,0,0,0,0,0,0,0,0,0,0,0,3,1,0,0,0,0,0},
		        {0,2,0,0,0,0,2,0,0,2,0,3,0,0,1,0,0,0,0,0},
		        {0,0,0,0,3,0,6,0,0,0,0,0,4,0,1,0,5,0,0,0},
		        {0,0,0,0,2,0,0,2,0,0,0,0,0,0,1,0,0,0,6,0},
		        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
		        {0,0,4,0,7,0,2,0,0,0,0,0,0,0,1,0,0,0,0,0},
		        {0,0,0,0,0,0,0,5,0,0,8,0,5,0,1,0,0,3,0,0},
		        {8,0,0,0,6,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0}};

	  private boolean[][] placedpoint=new boolean[15][20];

	  private Image grass;
	  private Image sand;
	  private Image smalltree;
	  private Image smalltrees;
	  private Image bigtree;
	  private Image smallrock;
	  private Image bigrock;
	  private Image hole;
	  private Image pond;


	  private Model model;//これいる？

	  private View view;


	  //指定座標が道路かどうかを調べる　道路だったらtrue
	  public boolean IsRoad(int x, int y) {
		   if(x>19||x<0||y>14||y<0)return false;

	        if (map[y][x] == 1) {
	            return true;
	        } else {

	        	return false;

	        }
	  }

	  //向くべき方向を特定する関数　unitで使う
	  public int SpecifyDirection(int x,int y){

		  if(IsRoad(x,y-1)){//上
			  return UP;

		  } else if (IsRoad(x+1,y)){//右
			  return RIGHT;

		  } else if(IsRoad(x,y+1)){//下
			  return DOWN;

		  } else {
			  return LEFT;

		  }
	  }

	  //ユニットを配置できるかを返す関数
	  //まず、既にユニットが置かれてないか
	  //んで、何もない所か
	  //そして、隣が道路か
	  public boolean ValidatePlace(int x,int y){
		  if(x>19||x<0||y>14||y<0)return false;
		  if(placedpoint[y][x])return false;


		  if(map[y][x]==0){
		  if(IsRoad(x,y-1)){//上
			  placedpoint[y][x]=true;
			  return true;

		  } else if (IsRoad(x+1,y)){//右
			  placedpoint[y][x]=true;
			  return true;

		  } else if(IsRoad(x,y+1)){//下
			  placedpoint[y][x]=true;
			  return true;

		  } else if(IsRoad(x-1,y)){//左
			  placedpoint[y][x]=true;
			  return true;

		  }else{
			  return false;
		  }

		  }else{
			  return false;
		  }
	  }





	  //このクラス全体を保持しているのはmodelだけど、viewがdrawを呼び出す感じ
	  public void draw(Graphics g) {
	        for (int i = 0; i < ROW; i++) {
	            for (int j = 0; j < COL; j++) {

	                switch (map[i][j]) {
	                    case 0 :
	                        g.drawImage(grass, j * CS, i * CS,view);
	                        break;
	                    case 1 :
	                        g.drawImage(sand, j * CS, i * CS, view);
	                        break;
	                    case 2 :
	                        g.drawImage(smalltree, j * CS, i * CS, view);
	                        break;
	                    case 3 :
	                        g.drawImage(smalltrees, j * CS, i * CS, view);
	                        break;
	                    case 4 :
	                        g.drawImage(bigtree, j * CS, i * CS, view);
	                        break;
	                    case 5 :
	                        g.drawImage(smallrock, j * CS, i * CS, view);
	                        break;
	                    case 6 :
	                        g.drawImage(bigrock, j * CS, i * CS, view);
	                        break;
	                    case 7 :
	                        g.drawImage(hole, j * CS, i * CS, view);
	                        break;
	                    case 8 :
	                        g.drawImage(pond, j * CS, i * CS, view);
	                        break;
	                }
	            }
	        }
	    }

	  private void loadImage() {
		  /*private void loadImage() {
			  URL url=getClass().getResource(name+".png");
		        ImageIcon icon = new ImageIcon(url);
		        image = icon.getImage();

		  }
		  	*/


	        ImageIcon icon = new ImageIcon(getClass().getResource("grass.png"));
	        grass = icon.getImage();

	        icon = new ImageIcon(getClass().getResource("sand.png"));
	        sand = icon.getImage();

	        icon = new ImageIcon(getClass().getResource("smalltree.png"));
	        smalltree = icon.getImage();

	        icon = new ImageIcon(getClass().getResource("smalltrees.png"));
	        smalltrees = icon.getImage();

	        icon = new ImageIcon(getClass().getResource("bigtree.png"));
	        bigtree = icon.getImage();

	        icon = new ImageIcon(getClass().getResource("smallrock.png"));
	        smallrock = icon.getImage();

	        icon = new ImageIcon(getClass().getResource("bigrock.png"));
	        bigrock = icon.getImage();

	        icon = new ImageIcon(getClass().getResource("hole.png"));
	        hole = icon.getImage();

	        icon = new ImageIcon(getClass().getResource("pond.png"));
	        pond = icon.getImage();


	    }




}
