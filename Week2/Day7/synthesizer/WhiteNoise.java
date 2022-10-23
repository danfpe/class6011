package com.example.synthesizer;

public class WhiteNoise extends SineWave{

    public int getWhiteNoiseValue(int i) {
        int sample = 0;
        if(i % 2 == 0) {
            sample = maxValue;
        }
        else {
            sample = -maxValue;
        }
        return sample;
    }
    @Override
    public AudioClip getClip() {
        AudioClip temp = new AudioClip();
        for(int i = 0; i < AudioClip.TOTAL_SAMPLES; i++){
            short whiteNoiseValue = (short) (getWhiteNoiseValue(i));
            temp.setSample( i, whiteNoiseValue);
        }
        return temp;
    }
}
