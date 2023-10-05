package ModelView.Global;

import java.io.*;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class MV_Ambiance {
    public void run(){
        String moozikLink = getDynamicDbPath() + "\\data\\Misc\\funkytown.wav";

        if(!MV_Global.ambianceRunning.get()){
            MV_Global.ambiance = new Thread(new Moozik(moozikLink));
            MV_Global.ambianceRunning.set(true);
            MV_Global.ambiance.start();
        }
        else{
            MV_Global.ambianceRunning.set(false);
        }
    }
          
    private String getDynamicDbPath(){
        File dynamicDir = new File("");
        String localDir = dynamicDir.getAbsolutePath();
        return localDir;
    }
}

class Moozik implements Runnable{
    private String moozikLink;
    private boolean exitThread = false;

    Moozik(String moozikLink){
        this.moozikLink = moozikLink;
    }
    
    @Override
    public void run() {
        try {
            URL url =  new File(moozikLink).toURI().toURL();
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-15.0f); //Lower volume

            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    LineEvent.Type type = event.getType();
                    if (type == LineEvent.Type.STOP){
                        clip.close();
                        MV_Global.ambianceRunning.set(false);
                        exitThread = true;
                        Thread.currentThread().interrupt();
                    }
                }
            });

            clip.start();
            MV_Global.ambianceRunning.set(true);

            while(!exitThread){
                Thread.sleep(1000);
                if(!MV_Global.ambianceRunning.get()){
                    clip.close();
                    exitThread = true;
                    Thread.currentThread().interrupt();
                }
            }
            return;
        } 
        catch (Exception e) {
            //Suppressed
        }
    }
}