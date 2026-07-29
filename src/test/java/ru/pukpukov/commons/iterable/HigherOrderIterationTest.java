package ru.pukpukov.commons.iterable;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.recursive.comparison.RecursiveComparator;
import org.junit.jupiter.api.Test;
import ru.pukpukov.commons.HigherOrderIteration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HigherOrderIterationTest {
    
    
    @Test
    public void testIteratorWithCapsAndDimensions() {
        HigherOrderIteration iterator1 = new HigherOrderIteration(new int[]{0, 0, 0}, new int[]{1, 2, 2});
        int[][] expected1 = {
            {0, 0, 0},
            {1, 0, 0},
            {0, 1, 0},
            {1, 1, 0},
            {0, 2, 0},
            {1, 2, 0},
            {0, 0, 1},
            {1, 0, 1},
            {0, 1, 1},
            {1, 1, 1},
            {0, 2, 1},
            {1, 2, 1},
            {0, 0, 2},
            {1, 0, 2},
            {0, 1, 2},
            {1, 1, 2},
            {0, 2, 2},
            {1, 2, 2}
        };
        assertIteratorOutput(iterator1, expected1);
        
        HigherOrderIteration iterator2 = new HigherOrderIteration(new int[]{0, 0}, new int[]{2, 3});
        int[][] expected2 = {
            {0, 0},
            {1, 0},
            {2, 0},
            {0, 1},
            {1, 1},
            {2, 1},
            {0, 2},
            {1, 2},
            {2, 2},
            {0, 3},
            {1, 3},
            {2, 3}
        };
        
        assertIteratorOutput(iterator2, expected2);
        
        HigherOrderIteration iterator3 = new HigherOrderIteration(new int[]{0, 0, 0, 0}, new int[]{2, 1, 1, 1});
        int[][] expected3 = {
            {0, 0, 0, 0},
            {1, 0, 0, 0},
            {2, 0, 0, 0},
            {0, 1, 0, 0},
            {1, 1, 0, 0},
            {2, 1, 0, 0},
            {0, 0, 1, 0},
            {1, 0, 1, 0},
            {2, 0, 1, 0},
            {0, 1, 1, 0},
            {1, 1, 1, 0},
            {2, 1, 1, 0},
            {0, 0, 0, 1},
            {1, 0, 0, 1},
            {2, 0, 0, 1},
            {0, 1, 0, 1},
            {1, 1, 0, 1},
            {2, 1, 0, 1},
            {0, 0, 1, 1},
            {1, 0, 1, 1},
            {2, 0, 1, 1},
            {0, 1, 1, 1},
            {1, 1, 1, 1},
            {2, 1, 1, 1}
        };
        
        assertIteratorOutput(iterator3, expected3);
        
        HigherOrderIteration iterator4 = new HigherOrderIteration(new int[]{6, 7}, new int[]{8, 8});
        int[][] expected4 = {
            {6, 7},
            {7, 7},
            {8, 7},
            {6, 8},
            {7, 8},
            {8, 8}
        };
        
        assertIteratorOutput(iterator4, expected4);
    }
    
    private void assertIteratorOutput(HigherOrderIteration iterator, int[][] expected) {
        int count = 0;
        do {
            Assertions.assertThat(iterator.__current).usingComparator(new RecursiveComparator()).isEqualTo(expected[count]);
            count++;
        } while (iterator.next());
        assertEquals(expected.length, count);
    }
    
}