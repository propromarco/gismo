package com.github.propromarco.gismo.worker;

import org.springframework.stereotype.Service;

import javax.sound.sampled.*;

@Service
public class AudioSystemService {

    public AudioSystemService() {
    }

    public void setLineVolume(float volume) throws LineUnavailableException {
        setVolume(Port.Info.LINE_OUT, volume);
    }

    public void setSpeakerVolume(float volume) throws LineUnavailableException {
        setVolume(Port.Info.SPEAKER, volume);
    }

    public void setHeadphoneVolume(float volume) throws LineUnavailableException {
        setVolume(Port.Info.HEADPHONE, volume);
    }

    public float getLineVolume() throws LineUnavailableException {
        return getVolume(Port.Info.LINE_OUT);
    }

    public float getSpeakerVolume() throws LineUnavailableException {
        return getVolume(Port.Info.SPEAKER);
    }

    public float getHeadphoneVolume() throws LineUnavailableException {
        return getVolume(Port.Info.HEADPHONE);
    }

    private void setVolume(Port.Info info, float volume) throws LineUnavailableException {
        Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();

        //it Looks for those Mixers that suport the OutPut SPEAKER
        for (int i = 0; i < mixerInfo.length; i++) {
            Mixer mixer = AudioSystem.getMixer(mixerInfo[i]);

            //If the SPEAKER is supported, then it gets a line
            if (mixer.isLineSupported(info)) {
                Port lineOut = (Port) mixer.getLine(info);

                lineOut.open();

                if (lineOut.isControlSupported(FloatControl.Type.VOLUME)) {
                    //Once we have the line, we request the Volumen control as a FloatControl
                    FloatControl volControl = (FloatControl) lineOut.getControl(FloatControl.Type.VOLUME);
                    //Everything is done
                    volControl.setValue(volume);
                }

                lineOut.close();
            }
        }
    }

    private float getVolume(Port.Info info) throws LineUnavailableException {
        float volume = 0f;

        Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();

        //it Looks for those Mixers that suport the OutPut SPEAKER
        for (int i = 0; i < mixerInfo.length; i++) {
            Mixer mixer = AudioSystem.getMixer(mixerInfo[i]);

            //If the SPEAKER is supported, then it gets a line
            if (mixer.isLineSupported(info)) {
                Port lineOut = (Port) mixer.getLine(info);

                lineOut.open();

                if (lineOut.isControlSupported(FloatControl.Type.VOLUME)) {
                    //Once we have the line, we request the Volumen control as a FloatControl
                    FloatControl volControl = (FloatControl) lineOut.getControl(FloatControl.Type.VOLUME);
                    //Everything is done
                    volume = volControl.getValue();
                }

                lineOut.close();
            }
        }
        return volume;
    }

}
