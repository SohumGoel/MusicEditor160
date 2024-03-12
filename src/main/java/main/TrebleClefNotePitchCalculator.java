package main;

public class TrebleClefNotePitchCalculator implements PitchCalculator {
    private static final int LINE_SPACING = 15; // Change this value to match the staff line spacing in your UI

    @Override
    public int calculatePitch(int y) {
        // The top line (E) is 64 in MIDI, for example, and each step represents a semitone.
        // Assuming the staff starts at y=0 and moves down.
        int midiNumberForTopLineE = 64;
        int numberOfStepsFromTopLine = y / LINE_SPACING;

        return midiNumberForTopLineE - numberOfStepsFromTopLine;
    }
}