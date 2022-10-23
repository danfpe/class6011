package com.example.synthesizer;

import java.util.Arrays;

public class AudioClip {
    static int duration = 2;
    static int sampleRate = 44100;

    static int MAX_VALUE = 32767;
    static int MIN_VALUE = -32768;

    static int TOTAL_SAMPLES = sampleRate * duration;
    byte[] sampleArray = new byte[sampleRate*duration*2];

    public short getSample(int index) {
//        System.out.println(newshort);
        return (short)((sampleArray[2*index] & 0xFF) | (sampleArray[2*index + 1] << 8));
    }

    public void setSample(int index, short value) {
        sampleArray[2*index] = (byte) value;
        sampleArray[2*index + 1] = (byte) ((value >> 8) & 0xff);
    }

    public byte[] getData() {
        return Arrays.copyOf(sampleArray, sampleRate*duration*2);
    }

    public static void main(String[] args) {
        AudioClip audio = new AudioClip();
        audio.setSample(0, (short) 700);
        short old_value = audio.getSample(0);
        System.out.println("old vlue" + old_value);
        short oldshort = 700;

        byte byte1= (byte) (oldshort);
        System.out.println(byte1);
        byte byte2= (byte) ((oldshort >> 8) & 0xff);
        System.out.println(byte2);

        short newshort = (short) ((byte2 << 8) + (byte1&0xFF));

        System.out.println(oldshort);
        System.out.println(newshort);
    }
}

