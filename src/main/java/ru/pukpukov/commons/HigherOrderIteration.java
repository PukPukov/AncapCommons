package ru.pukpukov.commons;

/**
 * Iterates through numbers like in positional number system, but with different bases and caps for different
 * digits. Using this approach, it is possible to achieve higher-order iteration: in plain words, loop of loop
 * nesting. 
 * <p> Single thread principle. In the sense of threading should be considered as something similar to for loop
 * and Iterator class that only works on single thread stack. It's not thread unsafe, it doesn't make sense
 * to use it from different threads at all, as it does not make sense to use for loop from different threads.
 * <p> Both caps and __current have little-endian ordering. For example if caps[2] = 3, then __current[2] will
 * always be less or equal to 3, and first will be increased value at index 0, and the last index of array will be
 * incremented last.
 * <p>
 * Example of output when caps array is {1, 2, 2}: <br>
 * 0, 0, 0 -> 1, 0, 0 -> 0, 1, 0 -> 1, 1, 0 -> 0, 2, 0 -> 1, 2, 0 -> 0, 0, 1 -> 1, 0, 1 -> etc... <br>
 * and the latest is 1, 2, 2, then it loops back to 0 0 0. The method next() returns false if it did
 * loop back.
 * <p>
 * The length of bases and caps arrays corresponds to dimensions amount. You can think of dimensions amount as
 * maximum amount of digits. When iteration try to go to digit larger than dimensions amount, the iteration is
 * over.
 * <p>
 * The expected way to use this class is do-while loop:
 * {@snippet :
 * var iter = new HigherOrderIteration(new int[]{0, 0, 0}, new int[]{2, 3, 3}); do {
 *   doSomethingWith(iter.__current);
 * } while (iter.next());
 * }
 */
public class HigherOrderIteration {
    
    private final int[] bases;
    
    /**
     * The largest possible number in the digit is the same as cap for the digit. That means if the cap is 3 then
     * maximum digit is 3.
     */
    private final int[] caps;
    
    /**
     * Current element. This is flyweight array and should be used accordingly!
     */
    public final int[] __current;
    
    public HigherOrderIteration(int[] bases, int[] caps) {
        if (bases.length != caps.length) throw new IllegalStateException("Ambigous dimensions amount");
        this.bases = bases.clone();
        this.caps = caps.clone();
        this.__current = new int[bases.length]; System.arraycopy(this.bases, 0, this.__current, 0, this.__current.length);
    }
    
    public boolean next() {
        for (int i = 0; i < this.caps.length; i++) {
            if (this.__current[i] < this.caps[i]) {
                this.__current[i]++;
                return true;
            } else {
                this.__current[i] = this.bases[i];
            }
        }
        
        return false; // if this statement is reached it means it did loop back to base
    }
    
}