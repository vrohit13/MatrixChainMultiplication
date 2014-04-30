
public class MCM {

    static int[][] m;
    static int[][] s;

    static int len;

    static String optExp = null;
    static int[] p = {30, 35, 15, 5, 10, 20, 25};
      static int n;
    static{
         n = p.length - 1;

        len = n + 1;
    }

    public static void main(MCMGui gui) {

        // The matrices dimension, we are not concerned with the actual values within each matrix
       // int[] p = {30, 35, 15, 5, 10, 20, 25};

        // Compute n
       /* int n = p.length - 1;

        len = n + 1;*/

        // Since the indices actually start from 1.
        //m[1..n][1..n]
        // Print to GUI
        m = new int[n + 1][n + 1];

        // Print to GUI
        //s[2..n-1][1..n]
        s = new int[n + 1][n + 1];

        // m[i][i] = 0. There is no cost for multiplying a single matrix.
        for (int i = 0; i < n; i++) {
            m[i][i] = 0;
        }

        for (int l = 2; l <= n; l++) {

            for (int i = 1; i <= n - l + 1; i++) {
                int j = i + l - 1;

                // Assign a huge value to each cell, new values will be compare against this value to check which is lower
                // Of course, new values will be lower than this initial value.
                m[i][j] = Integer.MAX_VALUE;
                gui.updateGUI_M(i, j, m[i][j]);
                
                try {
                    Thread.sleep(200);
                } catch (Exception e) {

                }

                for (int k = i; k <= j - 1; k++) {
                    int q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];

                    if (q < m[i][j]) {

                        System.out.println("Quantity:\t" + q + " is less than \tm[" + i + "][" + j + "] " + m[i][j] + "| k=" + k);
                        m[i][j] = q;
                        s[i][j] = k;
                        
                        gui.updateGUI_M(i, j, m[i][j]);

                        try {
                            Thread.sleep(200);
                        } catch (Exception e) {

                        }

                        // Update GUI
                    }
                }
            }

        }

        System.out.println("m:");

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                System.out.print(m[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println("s:");

        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[0].length; j++) {
                System.out.print(s[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println("Expression:");

        printOptimalExpression(s, 1, n);
        optExp = printOptimalExpression1(s, 1, n);

    }

    public static int[][] getS() {
        return s;
    }

    public static int[][] getM() {
        return m;
    }

    public static int getLen() {
        return len;
    }

    public static String getOptExp() {
        return optExp;
    }

    public static String printOptimalExpression1(int s[][], int i, int j) {

        String opt = "";

        if (i == j) {
            System.out.print("A" + i);
            opt += "A" + i;
        } else {
            System.out.print("(");
            opt += "(";
            opt += printOptimalExpression1(s, i, s[i][j]);

            opt += " * ";
            System.out.print(" * ");

            opt += printOptimalExpression1(s, s[i][j] + 1, j);
            opt += ")";
            System.out.print(")");

        }

        return opt;
    }

    public static void printOptimalExpression(int s[][], int i, int j) {

        if (i == j) {
            System.out.print("A" + i);
        } else {
            System.out.print("(");
            printOptimalExpression(s, i, s[i][j]);

            System.out.print(" * ");

            printOptimalExpression(s, s[i][j] + 1, j);
            System.out.print(")");
        }
    }

}
