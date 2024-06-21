package ru.ancap.commons;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * Iterates through numbers like in positional number system, but with different caps for different digits.
 * <p>
 * Single thread principle. In the sense of threading should be considered as something similar
 * to for loop and Iterator class that only works on single thread stack. It's not thread unsafe, 
 * it isn't intended to use outside of single stack at all.
 * <p>
 * The easy relation to not get confused with ordering of caps and results is that cap capping the
 * same digit index as it has own. <br>
 * For example caps[2] = 3 will make digit at index 2 from results array that get() method output 
 * capped at 3 and not bigger than 2.
 * <p>
 * Digits ordered from smallest to largest, i.e. first
 * will be increased value at index 0, and the last index of array will be incremented last.
 * <p>
 * Example of output when dimensions are 3 and caps are 2, 3, 3: (triple semicolon means new call) <br>
 * 0, 0, 0;;;1, 0, 0;;;0, 1, 0;;;1, 1, 0;;;0, 2, 0;;;1, 2, 0;;;0, 0, 1;;;1, 0, 1;;;etc... <br>
 * and the latest is 1, 2, 2, after its null returned for every call, but i tested only for one call because there is
 * no sense to make a lot of calls when you know there is nothing next.
 */
@RequiredArgsConstructor
public class NumberIterator implements Supplier<int@Nullable[]> {
    
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
     * Use single cap for all dimensions.
     */
    public NumberIterator(int dimensions, int cap) {
        this(dimensions, filledArray(dimensions, cap));
    }
    
    private static int[] filledArray(int dimensions, int cap) {
        var array = new int[dimensions];
        Arrays.fill(array, cap);
        return array;
    }
    
    private int[] current;
    
    /**
     * Returns next element.
     * <p>
     * Digits ordered from smallest to largest, i.e. first
     * will be increased value at index 0, and the last index of array will be incremented last. Returned array should not be modified.
     */
    @Override
    public int@Nullable[] get() {
        if (current == null) {
            current = new int[dimensions];
            return current;
        }
        
        for (int i = 0; i < dimensions; i++) {
            if (current[i] < caps[i] - 1) {
                current[i]++;
                return current;
            } else {
                current[i] = 0;
            }
        }
        
        return null; // if this statement is reached, then, there is nothing next
    }
    
}