package app;

import javax.sound.midi.*;

public class MiniMiniMusicApp {

    private static final int NOTE_ON_COMMAND = 144;
    private static final int NOTE_OFF_COMMAND = 128;
    private static final int CHANGE_INSTRUMENT_COMMAND = 192;

    public static void main(String[] args) {
        MiniMiniMusicApp miniMiniMusicApp = new MiniMiniMusicApp();
        miniMiniMusicApp.play();
    }

    private void play() {
        try (Sequencer player  = MidiSystem.getSequencer()){
            // my CD player
            player.open();

            // make a CD
            Sequence sequence = new Sequence(Sequence.PPQ, 4);

            // make a track
            Track track = sequence.createTrack();

            ShortMessage setInstrument = new ShortMessage();
            setInstrument.setMessage(CHANGE_INSTRUMENT_COMMAND,1,102,0);
            MidiEvent changeInstrument = new MidiEvent(setInstrument,1);
            track.add(changeInstrument);

            // assemble the events in the track
            // make a message (what to do)
            ShortMessage a = new ShortMessage();
            // put the instruction in the message
            // message type, channel, note to play, velocity
            a.setMessage(NOTE_ON_COMMAND, 1, 44, 100);
            // make a new midi event using the message (when to do it)
            MidiEvent noteOn = new MidiEvent(a, 1);
            // add the event to the track
            track.add(noteOn);

            ShortMessage b = new ShortMessage();
            b.setMessage(NOTE_OFF_COMMAND, 1, 44, 100);
            MidiEvent noteOff = new MidiEvent(b, 3);
            track.add(noteOff);

            // CD in player
            player.setSequence(sequence);

            // play!
            player.start();

            Thread.sleep(2000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
