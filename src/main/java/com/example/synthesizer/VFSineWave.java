package com.example.synthesizer;

public class VFSineWave extends SineWave{

    @Override
    public AudioClip getClip() {
        AudioClip temp = input_.get(0).getClip();
        double phase = 0;
        for(int i = 0; i < AudioClip.TOTAL_SAMPLES; i++){
            phase += 2 * Math.PI * temp.getSample(i) / AudioClip.sampleRate;
            short vfSample = (short) (maxValue * Math.sin(phase));
            temp.setSample( i, vfSample);
        }
        return temp;
    }
}
