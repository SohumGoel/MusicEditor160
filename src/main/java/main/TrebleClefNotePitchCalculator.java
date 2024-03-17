package main;

public class TrebleClefNotePitchCalculator implements PitchCalculator {
    private static final int LINE_SPACING = 15; // Change this value to match the staff line spacing in your UI

    @Override
    public int calculatePitch(int y) {
        int pitch = 60;
        int offset = (y - 96) / 6;
        pitch -= offset;
        return pitch;
    }
}