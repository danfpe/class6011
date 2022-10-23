package com.example.synthesizer;

import java.util.ArrayList;

public class Filters extends SineWave{
    ArrayList<AudioComponent> input_ = new ArrayList<>();
    double scale;
    public Filters (double scale) {
        this.scale = scale;
    }

    public AudioClip getClip() {
        AudioClip original = input_.get(0).getClip();
        return filterSound(original);
    }

    @Override
    public boolean hasInput() {
        return input_.size() != 0;
    }

    @Override
    public void connectInput(AudioComponent input) {
        input_.add(input);
    }

    public AudioClip filterSound(AudioClip original) {
        for(int i = 0; i < AudioClip.TOTAL_SAMPLES; i++){
            double newVolume = original.getSample(i) * scale;
            short singleVolume = (short) (clampVolume(newVolume));
            original.setSample(i, singleVolume);
        }
        return original;
    }

    public double clampVolume(double volume) {
        if (volume > 32767) {
            volume = 32767;
        }
        else if (volume < -32768) {
            volume = -32768;
        }
        return volume;
    }



//    public double squareWave(AudioComponent clip) {
//        double sample;
//        for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
//            if((clip.frequency * i / AudioClip.sampleRate) % 1 > 0.5) {
//                sample = AudioClip.MAX_VALUE;
//            }
//            else {
//                sample = AudioClip.MIN_VALUE;
//            }
//        }
//        return sample;
//
//    }

}
