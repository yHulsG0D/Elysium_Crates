package dev.huls.elysium_crates.methods;

import java.util.List;
import java.util.Random;

public class RandomRewardMethod {

    private final List<RewardMethod> rewardMethods;
    private final Random random = new Random();

    public RandomRewardMethod(List<RewardMethod> rewardMethods) {
        this.rewardMethods = rewardMethods;
    }

    public RewardMethod getRandomReward() {
        double totalWeight = 0.0;
        for (RewardMethod rewardMethod : rewardMethods) {
            totalWeight += rewardMethod.getChance();
        }

        double randomValue = random.nextDouble() * totalWeight;
        double cumulativeWeight = 0.0;

        for (RewardMethod rewardMethod : rewardMethods) {
            cumulativeWeight += rewardMethod.getChance();
            if (randomValue <= cumulativeWeight) {
                return rewardMethod;
            }
        }
        return null; // Shouldn't reach here if probabilities are correctly set up
    }
}
