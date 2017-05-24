package swingpaint;
import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class Test1 {

	 private static int EXTERNAL_BUFFER_SIZE = 128000;
	 
	    public static void main(String[] args) throws Exception { 
	        Test1 a = new Test1();
	        
	        a.playAudioFile();
	    }
	 
	    public void playAudioFile()throws Exception {//메소드
	       
	          String audioFile = "c:\\project\\bgm1.wav"; // 연결할 wav파일 위치
	     
	          Clip clip;
	          File soundFile = new File(audioFile);
	          try {
	           AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
	           AudioFormat audioFormat = audioInputStream.getFormat();
	           DataLine.Info info = new DataLine.Info(SourceDataLine.class,audioFormat);
	           SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
	           line.open(audioFormat);
	           line.start();
	                                      
	           int nBytesRead = 0;
	           byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
	           while (nBytesRead != -1) {
	           nBytesRead = audioInputStream.read(abData, 0, abData.length);
	           if (nBytesRead >= 0) {
	           line.write(abData, 0, nBytesRead);
	           }
	           }
	           line.drain();
	           line.close();
	           audioInputStream.close();
	           } catch (Exception e) {
	           e.printStackTrace();
	           }
	     
	    }
	}


