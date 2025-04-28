class Solution {
    public int shipWithinDays(int[] weights, int days) {
        int low = 0, high = 0;
        
        for (int num : weights) {
            low = Math.max(low, num); // At least the heaviest package
            high += num; // At most the sum of all packages
        }

        int ans = high; // Worst case

        while (low <= high) {
            int mid = (low + high) / 2;
            if (canShip(weights, mid, days)) {
                ans = mid;
                high = mid - 1; // Try to find a smaller capacity
            } else {
                low = mid + 1; // Need more capacity
            }
        }

        return ans;
    }

    private boolean canShip(int[] weights, int cap, int days) {
        int currentDay = 1;
        int currentCap = 0;

        for (int num : weights) {
            if (currentCap + num > cap) {
                currentDay++;
                currentCap = 0; // Reset capacity for the new day
            }
            currentCap += num;

            if (currentDay > days) {
                return false;
            }
        }

        return true;
    }
}
