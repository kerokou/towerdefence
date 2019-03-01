package towerdefence;

/*・読み込むときはコンストラクターにパスを文字列で渡し、インスタンス生成するだけ
SoundClip bgm = new SoundClip(media/StarDustDrive.wav);
・一時停止機能
bgm.pause();
・有限ループ、無限ループ
bgm.loop(5);bgm.loop();
・音量調整機能
bgm.setVolume(0.8);
・再生確認
if(bgm.isRunning()){ ... }
・JARファイル内でも音声ファイルが読み込める
・充実な例外処理、読み込みが失敗すると各状況に合わせメッセージボックス式でエラーを伝え、さらにその後それを参照してしまってもNullPointerExceptionが絶対でてこない


 */
import java.awt.Container;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

public class SoundClip{
 public Clip clip;
 public FloatControl control;
 boolean isRunning;
 public SoundClip(String url){
  URL u=this.getClass().getResource(url);
  Container box = null;
  try{
   clip = AudioSystem.getClip();
   clip.open(AudioSystem.getAudioInputStream(u));
   control = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
  }catch(IOException e){
   JOptionPane.showMessageDialog(box,"音声ファイル\"" + url + "\"が見つかりませんでした。このBGMは流れません。","読み込みエラー",JOptionPane.WARNING_MESSAGE);
  }catch(UnsupportedAudioFileException e){
   JOptionPane.showMessageDialog(box,"この音声ファイル\"" + url + "\"のメディア形式\"" + clip.getFormat() + "\"には対応できません。","読み込みエラー",JOptionPane.WARNING_MESSAGE);
  }catch(LineUnavailableException e){
   JOptionPane.showMessageDialog(box,"音声ファイル\"" + url + "\"を開けませんでした。ほかのアプリケーションが使用中の可能性があります。","読み込みエラー",JOptionPane.WARNING_MESSAGE);
  }
 }
 public void play(){ //再生
  if(clip != null)
   clip.start();
  clip.setFramePosition(0);
 }
 public void pause(){ //一時停止
  if(clip != null)
   clip.stop();
 }
 public void stop(){ //停止
  if(clip != null){
   clip.stop();
   clip.setFramePosition(0);
  }
 }
 public void loop(int count){ //ループ再生
  if(clip != null)
   clip.loop(count);
 }
 public void loop(){ //無限ループ再生
  if(clip != null)
   clip.loop(Clip.LOOP_CONTINUOUSLY);
 }
 public void setVolume(double volume){ //setVolume(0.5);  50%の音量で再生する
  if(control != null)
   control.setValue((float)Math.log10(volume) * 20);
 }
 boolean isRunning(){ //再生しているか
  if(clip != null)
   return clip.isRunning();
  else
   return false;
 }
 public void setFramePosition(int num){
	 clip.setFramePosition(num);
 }

}