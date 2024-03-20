package main;

import java.util.HashMap;
import java.util.TreeMap;

import java.util.Map;
// PitchCalculator.java
public class PitchCalculator {
    //private final Map<Integer, Integer> pitchMap;
    private final TreeMap<Integer, Integer> pitchMap;


    public PitchCalculator() {
         pitchMap = new TreeMap<>();
        // pitchMap.put(186, 45);
        // pitchMap.put(178, 47);
        // pitchMap.put(170, 48);
        // pitchMap.put(162, 50);
        // pitchMap.put(155, 52);
        // pitchMap.put(149, 53);
        // pitchMap.put(141, 55);
        // pitchMap.put(132, 57);
        // pitchMap.put(82, 60);
        // pitchMap.put(79, 62);
        // pitchMap.put(74, 64);
        // pitchMap.put(65, 65);
        // pitchMap.put(59, 67);
        // pitchMap.put(46, 69);
        // pitchMap.put(43, 71);
        // pitchMap.put(35, 72);
        // pitchMap.put(29, 74);
        // pitchMap.put(20, 76);
        // pitchMap.put(14, 77);
        // pitchMap.put(8, 79);

        int[] keys = {186, 178, 170, 162, 155, 149, 141, 132, 82, 79, 74, 65, 59, 46, 43, 35, 29, 20, 14, 8};
        int[] values = {45, 47, 48, 50, 52, 53, 55, 57, 60, 62, 64, 65, 67, 69, 71, 72, 74, 76, 77, 79};
        for (int i = 0; i < keys.length; i++) {
            pitchMap.put(keys[i], values[i]);
        }
    }

    public int calculatePitch(int y) {
        // Find the closest key for the given y-coordinate
        Integer floorKey = pitchMap.floorKey(y);
        Integer ceilingKey = pitchMap.ceilingKey(y);
        if (floorKey == null && ceilingKey == null) {
            throw new IllegalStateException("Pitch map is empty.");
        } else if (floorKey == null) {
            return pitchMap.get(ceilingKey);
        } else if (ceilingKey == null) {
            return pitchMap.get(floorKey);
        }
        // Choose the closest key
        int floorDiff = Math.abs(y - floorKey);
        int ceilingDiff = Math.abs(y - ceilingKey);
        return pitchMap.get(floorDiff <= ceilingDiff ? floorKey : ceilingKey);
    }

    // public int calculatePitch(int y) {
    //     for (int key : pitchMap.keySet()) {
    //         int value = pitchMap.get(key);
    //         int difference = Math.abs(y - key);
    //         if (difference < 3) return value;
    //     }
        
    //     return pitchMap.get(getClosestKey(y));
    // }

    // public int getClosestKey(int y) {
    //     int closestKey = 0;
    //     int minDifference = Integer.MAX_VALUE;
    //     for (int key : pitchMap.keySet()) {
    //         if (Math.abs(y - key) < minDifference) {
    //             minDifference = Math.abs(y - key);
    //             closestKey = key;
    //         }
    //     }
    //     return closestKey;
    // }
}
