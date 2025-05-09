class Solution 
{
    public int countBalancedPermutations(String num) 
    {
        // Step 1: Count the frequency of each digit (0-9)
        int[] f = new int[10]; 
        
        int s = 0; // This will hold the total sum of all digits

        for (char c : num.toCharArray()) 
        {
            f[c - '0']++;
            s += c - '0';
        }

        // Step 2: If the sum of all digits is odd, we can't split into two equal parts
        if (s % 2 != 0) 
        {
            return 0;
        }

        final int mod = 1000000007;
        
        // Step 3: Now we target sum s/2 for each half
        s /= 2; 
        
        int n = num.length();

        // Step 4: Initialize DP array and factorials
        long[][] dp = new long[n / 2 + 1][s + 1];
        int[][] fif = enumFIF(1000, mod); // factorials and inverse factorials

        // Step 5: Base case — number of ways to split into two halves
        dp[0][0] = (long) fif[0][n / 2] * fif[0][n - n / 2] % mod;

        // Step 6: Process each digit from 0 to 9
        for (int i = 0; i <= 9; i++) 
        {
            long[][] ndp = new long[n / 2 + 1][s + 1];

            // Step 7: Iterate backwards over current DP states
            for (int j = n / 2; j >= 0; j--) 
            {
                for (int k = s; k >= 0; k--) 
                {
                    if (dp[j][k] == 0)
                    {
                        continue;
                    }

                    // Step 8: Try placing 't' instances of digit 'i' into left half
                    for (int t = 0; t <= f[i] && k + i * t <= s && j + t <= n / 2; t++) 
                    {
                        long add = dp[j][k] * fif[1][t] % mod * fif[1][f[i] - t] % mod;
                        ndp[j + t][k + i * t] = (ndp[j + t][k + i * t] + add) % mod;
                    }
                }
            }

            // Step 9: Update DP with new state
            dp = ndp;
        }

        // Step 10: Result is number of ways to form a half of size n/2 and sum s
        return (int) dp[n / 2][s];
    }

    // Utility: Precompute factorials and inverse factorials modulo mod
    public static int[][] enumFIF(int n, int mod) 
    {
        int[] f = new int[n + 1];
        int[] invf = new int[n + 1];

        f[0] = 1;
        for (int i = 1; i <= n; i++) 
        {
            f[i] = (int) ((long) f[i - 1] * i % mod);
        }

        // Use Extended Euclidean Algorithm to find modular inverse
        long a = f[n], b = mod;
        long p = 1, q = 0;
        while (b > 0) 
        {
            long c = a / b, d;
            d = a; a = b; b = d % b;
            d = p; p = q; q = d - c * q;
        }

        invf[n] = (int) (p < 0 ? p + mod : p);
        for (int i = n - 1; i >= 0; i--) 
        {
            invf[i] = (int) ((long) invf[i + 1] * (i + 1) % mod);
        }

        return new int[][]{f, invf};
    }
}