package ModelView.Global;

import java.io.*;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class MV_Ambiance {
    public void run(){
        String moozikLink = getDynamicDbPath() + "\\data\\Misc\\funkytown.wav";

        if(MV_Global.ambiancePlaying){
            MV_Global.ambiancePlaying = false;
        }
        else{
            MV_Global.ambiance = new Thread(new Moozik(moozikLink));
            MV_Global.ambiancePlaying = true;
            MV_Global.ambiance.start();
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

            clip.start();
        } 
        catch (Exception e) {
            //Suppressed
        }
    }
}