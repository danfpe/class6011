package com.example.synthesizer;

import java.util.ArrayList;

import static com.example.synthesizer.AudioClip.TOTAL_SAMPLES;
import static com.example.synthesizer.AudioClip.sampleRate;

class SineWave implements AudioComponent{
    double frequency;
    ArrayList<AudioComponent> input_ = new ArrayList<>();
    short maxValue = 30000; // 32767

    public SineWave(double frequency) {
        this.frequency = frequency;
    }

    public SineWave() {
        this.frequency = 440;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }
    public short getSineSampleValue(int i) {
        return (short) (maxValue*Math.sin(2*(Math.PI)*frequency * i / sampleRate));
    }

    @Override
    public AudioClip getClip() {
        AudioClip temp = new AudioClip();
        for(int i = 0; i < TOTAL_SAMPLES; i++){
            short singleSample = getSineSampleValue(i);
            temp.setSample( i, singleSample);
        }
        return temp;
    }

    @Override
    public boolean hasInput() {
        return input_ != null;
    }

    @Override
    public void connectInput(AudioComponent input) {
        input_.add(input);
    }

}
