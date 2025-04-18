//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.util.Arrays;
import java.util.Collections;

public class ARU {
    public ARU() {
    }

    public int[] ARU(int[] A) {
        int n = A.length;
        if (n == 0) {
            return A;
        } else {
            int k = (Integer)Collections.max(Arrays.stream(A).boxed().toList());
            double m = Math.ceil(Math.sqrt((double)k));
            int j = 0;
            int[] Q = new int[(int)m + 2];
            int[] R = new int[(int)m + 2];
            int[] B = new int[n];
            System.out.println("Calculating R and Q");

            while(j < n) {
                Q[j] = (int)((double)A[j] / m);
                R[j] = (int)((double)A[j] % m);
                ++j;
            }

            Arrays.fill(Q, 0);
            Arrays.fill(R, 0);
            int[] var11 = A;
            int var12 = A.length;

            int i;
            int d;
            int var10003;
            for(i = 0; i < var12; ++i) {
                d = var11[i];
                int q = (int)((double)d / m);
                int r = (int)((double)d % m);
                var10003 = Q[q]++;
                var10003 = R[r]++;
            }

            for(i = 1; (double)i <= m; ++i) {
                Q[i] += Q[i - 1];
                R[i] += R[i - 1];
            }

            for(j = n - 1; j >= 0; --j) {
                d = (int)((double)A[j] % m);
                var10003 = R[d]--;
                B[R[d]] = A[j];
            }

            for(i = n - 1; i >= 0; --i) {
                d = (int)((double)B[i] / m);
                var10003 = Q[d]--;
                A[Q[d]] = B[i];
            }

            return A;
        }
    }
}
