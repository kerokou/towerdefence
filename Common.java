package towerdefence;

public interface Common {

	 public static final int DOWN = 0;
	 public static final int LEFT = 1;
	 public static final int RIGHT = 2;
	 public static final int UP = 3;

	 public static final int CS = 32;

	//バトルフェイズ、アサインフェイズ
	 public static final int BATTLE = 0;
	 public static final int ASSIGN = 1;

	//ゲームオーバー座標
	 public static final int GAMEOVERX=14;
	 public static final int GAMEOVERY=14;

	  //chara,fieldmapなどがこれを継承する
}
