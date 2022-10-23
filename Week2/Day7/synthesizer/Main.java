package com.example.synthesizer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Main {
    public static void main(String[] args) throws LineUnavailableException {
        // Get properties from the system about samples rates, etc.
        // AudioSystem is a class from the Java standard library.
        Clip c = AudioSystem.getClip(); // Note, this is different from our AudioClip class.

        // This is the format that we're following, 44.1 KHz mono audio, 16 bits per sample.
        AudioFormat format16 = new AudioFormat( 44100, 16, 1, true, false );

        AudioComponent gen = new SineWave(420); // Your code
        AudioComponent gen1 = new SineWave(920); // Your code
        AudioComponent linearRamp = new LinearRamp(50, 2000);
        AudioComponent vfgen = new VFSineWave();
        vfgen.connectInput(linearRamp);
        AudioClip clip = vfgen.getClip();

//        AudioComponent square = new SquareWave(440);
//        AudioClip clip = square.getClip();

//        AudioComponent whiteNoise = new WhiteNoise();
//        AudioClip clip = whiteNoise.getClip();

//        Mixers mixer = new Mixers();
//        mixer.connectInput(gen);
//        mixer.connectInput(gen1);
//        AudioClip clip = mixer.getClip();
//        AudioClip clip1 = gen.getClip();         // Your code
//        Filters filter = new Filters(1.5);
//        filter.connectInput(gen);
//        AudioClip clip = filter.getClip();

        c.open( format16, clip.getData(), 0, clip.getData().length ); // Reads data from our byte array to play it.

        System.out.println( "About to play..." );
        c.start(); // Plays it.
        c.loop( 2 ); // Plays it 2 more times if desired, so 6 seconds total

        // Makes sure the program doesn't quit before the sound plays.
        while( c.getFramePosition() < AudioClip.TOTAL_SAMPLES || c.isActive() || c.isRunning() ){
            // Do nothing while we wait for the note to play.
        }

        System.out.println( "Done." );
        c.close();
    }
}
