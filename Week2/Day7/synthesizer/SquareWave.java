package com.example.synthesizer;

public class SquareWave extends SineWave{

    public SquareWave(int frequency) {
        this.frequency = frequency;
    }
    public int getSquareWaveValue(int i) {
        int sample = 0;
        if(  ( frequency * i / AudioClip.sampleRate) % 1 > 0.5) {
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
            short squareSample = (short) (getSquareWaveValue(i));
            temp.setSample( i, squareSample);
        }
        return temp;
    }
}
