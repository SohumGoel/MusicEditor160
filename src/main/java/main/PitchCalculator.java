package main;

import java.io.Serializable;
import java.util.TreeMap;

public class PitchCalculator implements Serializable {

    private static final long serialVersionUID = 1L;
    private final TreeMap<Integer, Integer> pitchMap;

    public PitchCalculator() {
        pitchMap = new TreeMap<>();
        int[] keys = {186, 178, 170, 162, 155, 149, 141, 132, 127, 122, 84, 79, 74, 65, 59, 46, 43, 35, 29, 20, 14, 8};
        int[] values = {45, 47, 48, 50, 52, 53, 55, 57, 59, 60, 60, 62, 64, 65, 67, 69, 71, 72, 74, 76, 77, 79};
        for (int i = 0; i < keys.length; i++) {
            pitchMap.put(keys[i], values[i]);
        }
    }

    public int calculatePitch(int y) {
        Integer floorKey = pitchMap.floorKey(y);
        Integer ceilingKey = pitchMap.ceilingKey(y);
        if (floorKey == null) return pitchMap.get(ceilingKey);
        else if (ceilingKey == null) return pitchMap.get(floorKey);
        return selectAmbiguousPitch(y, floorKey, ceilingKey);
    }

    private int selectAmbiguousPitch(int y, int floorKey, int ceilingKey) {
        int floorDiff = Math.abs(y - floorKey);
        int ceilingDiff = Math.abs(y - ceilingKey);
        return pitchMap.get(floorDiff <= ceilingDiff ? floorKey : ceilingKey);
    }
}
