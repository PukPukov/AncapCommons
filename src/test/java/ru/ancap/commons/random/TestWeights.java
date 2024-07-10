package ru.ancap.commons.random;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

public class TestWeights {
    
    @Test
    public void testSingleElement() {
        List<Weight<String>> items = List.of(new Weight<>("OnlyItem", 100));
        Weights<String> weights = new Weights<>(items);
        
        // Roll multiple times to ensure consistency
        for (int i = 0; i < 1000; i++) {
            String result = weights.roll();
            assertEquals("OnlyItem", result);
        }
    }
    
    @Test
    public void testMultipleElements() {
        List<Weight<String>> items = List.of(
            new Weight<>("Item1", 10),
            new Weight<>("Item2", 30),
            new Weight<>("Item3", 60)
        );
        Weights<String> weights = new Weights<>(items);
        
        // Roll multiple times and record results
        int iterations = 10000;
        Map<String, Integer> results = new HashMap<>();
        for (int i = 0; i < iterations; i++) {
            String selectedItem = weights.roll();
            results.put(selectedItem, results.getOrDefault(selectedItem, 0) + 1);
        }
        
        // Since the weights are 10, 30, and 60, Item3 should be selected the most
        assertTrue(results.get("Item3") > results.get("Item2"));
        assertTrue(results.get("Item2") > results.get("Item1"));
    }
    
    @Test
    public void testDistribution() {
        for (int i = 0; i < 3; i++) {
            int weight1 = ThreadLocalRandom.current().nextInt(1, 100);
            int weight2 = ThreadLocalRandom.current().nextInt(1, 100);
            int weight3 = ThreadLocalRandom.current().nextInt(1, 100);
            int weight4 = ThreadLocalRandom.current().nextInt(1, 100);
            int totalWeight = weight1 + weight2 + weight3 + weight4;
            List<Weight<String>> items = List.of(
                new Weight<>("Item1", weight1),
                new Weight<>("Item2", weight2),
                new Weight<>("Item3", weight3),
                new Weight<>("Item4", weight4)
            );
            Weights<String> weights = new Weights<>(items);
            
            Multiset<String> counter = HashMultiset.create();
            
            int rolls = 10_000_000;
            
            for (int j = 0; j < rolls; j++) {
                counter.add(weights.roll());
            }
            
            assertInBounds(counter.count("Item1"), calcMin(weight1, totalWeight, rolls, 0.025), calcMax(weight1, totalWeight, rolls, 0.025));
            assertInBounds(counter.count("Item2"), calcMin(weight2, totalWeight, rolls, 0.025), calcMax(weight2, totalWeight, rolls, 0.025));
            assertInBounds(counter.count("Item3"), calcMin(weight3, totalWeight, rolls, 0.025), calcMax(weight3, totalWeight, rolls, 0.025));
            assertInBounds(counter.count("Item4"), calcMin(weight4, totalWeight, rolls, 0.025), calcMax(weight4, totalWeight, rolls, 0.025));
        }
    }
    
    private int calcMin(int base, int totalWeight, int rolls, double deviationAmount) {
        double expectedHits = calcExpectedHits(base, totalWeight, rolls);
        return (int) (expectedHits - deviation(expectedHits, deviationAmount));
    }
    
    private int calcMax(int base, int totalWeight, int rolls, double deviationAmount) {
        double expectedHits = calcExpectedHits(base, totalWeight, rolls);
        return (int) (expectedHits + deviation(expectedHits, deviationAmount));
    }
    
    private int deviation(double expectedHits, double deviationAmount) {
        return (int) (expectedHits * deviationAmount);
    }
    
    private double calcExpectedHits(double base, double totalWeight, int rolls) {
        double chance = base / totalWeight;
        return chance * rolls;
    }
    
    private void assertInBounds(long actual, double min, double max) {
        assertTrue(actual > min, "min: "+ ((long) min)+"; max: "+ ((long) max)+"; actual = "+actual);
        assertTrue(actual < max, "min: "+ ((long) min)+"; max: "+ ((long) max)+"; actual = "+actual);
    }
    
    @Test
    public void testEmptyWeights() {
        assertThrows(IllegalArgumentException.class, () -> new Weights<>(List.of()));
    }
    
    @Test
    public void testNegativeWeights() {
        List<Weight<String>> items = List.of(
            new Weight<>("Item1", -10),
            new Weight<>("Item2", 20),
            new Weight<>("Item3", 30)
        );
        
        assertThrows(IllegalArgumentException.class, () -> new Weights<>(items));
    }
    
}