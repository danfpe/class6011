package com.example.synthesizer;

import java.util.ArrayList;

import static com.example.synthesizer.AudioClip.TOTAL_SAMPLES;

public class Mixers extends SineWave{
    ArrayList<AudioComponent> input_ = new ArrayList<>();

    @Override
    public AudioClip getClip() {
        int size = input_.size();
        if (!hasInput()) {
            return null;
        } else if (size == 1) {
            return input_.get(0).getClip();
        }

        AudioClip mixedClip = input_.get(0).getClip();
        for(int i = 1; i < size; i++) {
            AudioClip clip = input_.get(i).getClip();
            for(int j = 0; j < TOTAL_SAMPLES; j++) {
                short mixedSample = (short) (mixedClip.getSample(j) + clip.getSample(j));
                mixedClip.setSample(j, mixedSample);
            }
        }
        return mixedClip;
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
