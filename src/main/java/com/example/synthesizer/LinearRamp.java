package com.example.synthesizer;
import static com.example.synthesizer.AudioClip.TOTAL_SAMPLES;
import static com.example.synthesizer.AudioClip.sampleRate;

public class LinearRamp extends SineWave{
    int start;
    int stop;
    public LinearRamp(int start, int stop) {
        this.start = start;
        this.stop = stop;
    }

    public double getLinearRampValue(int i) {
//        return (short) (maxValue*Math.sin(2*(Math.PI)*frequency * i / sampleRate));
        return (double)( start * ( TOTAL_SAMPLES - i ) + stop * i ) / TOTAL_SAMPLES;
    }

    @Override
    public AudioClip getClip() {
        AudioClip temp = new AudioClip();
        for(int i = 0; i < TOTAL_SAMPLES; i++){
            short singleSample = (short)(getLinearRampValue(i));
            temp.setSample( i, singleSample);
        }
        return temp;
    }
}
