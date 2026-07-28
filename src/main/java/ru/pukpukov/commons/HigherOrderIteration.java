package ru.pukpukov.commons;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Iterates through numbers like in positional number system, but with different caps for different digits.
 * Using this approach, it is possible to achieve higher-order iteration: in plain words, loop of loop nesting. 
 * <p>
 * Single thread principle. In the sense of threading should be considered as something similar
 * to for loop and Iterator class that only works on single thread stack. It's not thread unsafe, 
 * it doesn't make sense to use it outside of single stack at all.
 * <p>
 * The easy relation to not get confused with ordering of caps and results is that cap is capping the
 * same digit index as it has own. <br>
 * For example caps[2] = 3 will make digit at index 2 from results array that get() method output 
 * capped at 3 and not bigger than 2.
 * <p>
 * Digits are ordered from smallest to largest, i.e. first
 * will be increased value at index 0, and the last index of array will be incremented last.
 * <p>
 * Example of output when dimensions are 3 and caps are 2, 3, 3: (triple semicolon means new call) <br>
 * 0, 0, 0;;;1, 0, 0;;;0, 1, 0;;;1, 1, 0;;;0, 2, 0;;;1, 2, 0;;;0, 0, 1;;;1, 0, 1;;;etc... <br>
 * and the latest is 1, 2, 2, then it loops back to 0 0 0. The method next() returns false if it did
 * loop back.
 * <p>
 * The expected way to use this class is do-while loop:
 * {@snippet :
 * var iter = new HigherOrderIteration(3, new int[]{2, 3, 3});
 * do {
 *   doSomethingWith(iter.__current);
 * } while (iter.next());
 * }
 */
@RequiredArgsConstructor
public class HigherOrderIteration {
    
    /**
     * Speaking easily, it is maximum amount of digits. When iteration try to go to digit larger 
     * than dimensions amount, the iteration is over and null is returned. Should be the same as length
     * of caps array.
     */
    private final int dimensions;
    
    /**
     * Cap for every digit in order from the smallest digit to the largest (it means the first value in the array will
     * be responsible for the cap of unit digits and the second, if the caps are decimal, will be responsible for tens).
     * <p>
     * The largest possible number in the digit is one smaller than cap for the digit. That means if the cap is 3 then maximum digit is 2.
     */
    private final int[] caps;
    
    /**
     * Current element.
     * <p>
     * Digits ordered from smallest to largest, i.e. first
     * will be increased value at index 0, and the last index of array will be incremented last.
     * This is flyweight array and should not be modified!
     */
    public final int[] __current;
    
    public HigherOrderIteration(int dimensions, int[] caps) {
        this(dimensions, caps.clone(), new int[dimensions]);
    }
    
    /**
     * Use single cap for all dimensions.
     */
    public HigherOrderIteration(int dimensions, int cap) {
        this(dimensions, filledArray(dimensions, cap));
    }
    
    private static int[] filledArray(int dimensions, int cap) {
        var array = new int[dimensions];
        Arrays.fill(array, cap);
        return array;
    }
    
    public boolean next() {
        for (int i = 0; i < dimensions; i++) {
            if (__current[i] < caps[i] - 1) {
                __current[i]++;
                return true;
            } else {
                __current[i] = 0;
            }
        }
        
        return false; // if this statement is reached it means it did loop back to 0 0 0
    }
    
}