package main;

import java.util.HashMap;
import java.util.Map;
// PitchCalculator.java
public class PitchCalculator {
    private final Map<Integer, Integer> pitchMap;

    public PitchCalculator() {
        pitchMap = new HashMap<>();
        pitchMap.put(186, 45);
        pitchMap.put(178, 47);
        pitchMap.put(170, 48);
        pitchMap.put(162, 50);
        pitchMap.put(155, 52);
        pitchMap.put(149, 53);
        pitchMap.put(141, 55);
        pitchMap.put(132, 57);
        pitchMap.put(82, 60);
        pitchMap.put(79, 62);
        pitchMap.put(74, 64);
        pitchMap.put(65, 65);
        pitchMap.put(59, 67);
        pitchMap.put(46, 69);
        pitchMap.put(43, 71);
        pitchMap.put(35, 72);
        pitchMap.put(29, 74);
        pitchMap.put(20, 76);
        pitchMap.put(14, 77);
        pitchMap.put(8, 79);
    }

    public int calculatePitch(int y) {
        for (int key : pitchMap.keySet()) {
            int value = pitchMap.get(key);
            int difference = Math.abs(y - key);
            if (difference < 3) return value;
        }
        
        return pitchMap.get(getClosestKey(y));
    }

    public int getClosestKey(int y) {
        int closestKey = 0;
        int minDifference = Integer.MAX_VALUE;
        for (int key : pitchMap.keySet()) {
            if (Math.abs(y - key) < minDifference) {
                minDifference = Math.abs(y - key);
                closestKey = key;
            }
        }
        return closestKey;
    }
}
