package ru.pukpukov.commons.string;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Will split string to provided constants, if possible. If no, return null. <br>
 * For example: <br>
 * input = "Some text", constants = ["some", "text", " "], output = "Some text". <br>
 * If there more than one variant of composition, will return random.
 */

public class OnConstantSplitter {
    
    public int lastCallIterations = 0;
    
    @Nullable
    public List<String> findMinimalComposition(String inputString, Set<String> substrings) {
        this.lastCallIterations = 0;
        int n = inputString.length();
        List<String>[] dp = new List[n+1];
        dp[0] = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            for (String sub : substrings) {
                this.lastCallIterations++;
                if (i >= sub.length() && inputString.startsWith(sub, i-sub.length())) {
                    if (dp[i-sub.length()] == null) continue;
                    List<String> composition = new ArrayList<>(dp[i-sub.length()]);
                    composition.add(sub);
                    if (dp[i] == null || composition.size() < dp[i].size()) {
                        dp[i] = composition;
                    }
                }
            }
        }
        return dp[n];
    }
    
}
