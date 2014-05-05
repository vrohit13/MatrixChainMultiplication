
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rohit
 */
public class MCMGuiBgWorker extends SwingWorker<Object, Void> {

    long DELAY = 500;

    Boolean pause = false;
    boolean nextStep = false;

    int[][] m;
    int[][] s;

    int len;
    int n;

    int[] p = {30, 35, 15, 5, 10, 20, 25};

    String optExp = null;

    MCMGui mcmGui;

    public MCMGuiBgWorker(int[] input, MCMGui mcmGui) {
        this.mcmGui = mcmGui;
        
        this.p = input;
        
        n = p.length - 1;
        len = n + 1;
    }

    public void setPause(Boolean flag) {
        this.pause = flag;
    }
    
    public void setNextStep(boolean flag) {
        
        this.nextStep = flag;   // This will make sure that the current step is unpaused and paused after the next step.
        pause = false;  // Unpause current step
    }

    @Override
    public Object doInBackground() {
        
        m = new int[n + 1][n + 1];

        //s[2..n-1][1..n]
        s = new int[n + 1][n + 1];

        // There is no cost for multiplying a single matrix.
        for (int i = 0; i < n; i++) {
            m[i][i] = 0;
        }

        // Update GUI with max values
        for (int l = 2; l <= n; l++) {

            for (int i = 1; i <= n - l + 1; i++) {
                int j = i + l - 1;
                
                
                // Assign a huge value to each cell, new values will be compare against this value to check which is lower
                // Of course, new values will be lower than this initial value.
               
                m[i][j] = Integer.MAX_VALUE;
                mcmGui.updateGUI_M(i, j, m[i][j]);
                
                try {
                            Thread.sleep(100);  // This is required, else the pause does not update {SWING mechanism)
                        } catch (Exception e) {
                            e.printStackTrace();

                        }

                // Unhighlight the Cell
                mcmGui.updateGUI_M_unhighlight(i, j);
            }
        }

        for (int l = 2; l <= n; l++) {

            for (int i = 1; i <= n - l + 1; i++) {

                int j = i + l - 1;

                for (int k = i; k <= j - 1; k++) {

                    // Check to see if a pause is required
                    while (pause) {
                        try {
                            Thread.sleep(100);  // This is required, else the pause does not update {SWING mechanism)
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                    
                    // Check for next step
                    if(nextStep) {
                        pause = true;
                        nextStep = false;
                    }
                    
                    mcmGui.setComputedLabel("");
                    mcmGui.setExistingLabel("");
                    
                    
                    // Highlight cell for evaluation
                    mcmGui.highlightMForEval(i, j);
                    
   
                    
                    // Check to see if a pause is required
                    while (pause) {
                        try {
                            Thread.sleep(100);  // This is required, else the pause does not update {SWING mechanism)
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                    
                    // Check for next step
                    if(nextStep) {
                        pause = true;
                        nextStep = false;
                    }
                    
                    
                    

                    int q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];
                    
                    mcmGui.unHighlightLabel1();
                    mcmGui.unHighlightLabel2();
                    
                    String str1 = "m[" + i + "][" + k + "] + " + "m[" + (k+1) + "][" + j + "] + p" + (i-1) + "p" + k + "p" + j + " = " + q + " ( K = " + k + " )"; 
                    mcmGui.setComputedLabel(str1);
                    
                    String str2 = "m[" + i + "][" + j + "] = " + m[i][j] + " ( K = " + s[i][j] + " ) ";
                    mcmGui.setExistingLabel(str2);
                    
                    try {
                            Thread.sleep(DELAY);
                        } catch (Exception e) {

                        }
                    
                    
                    
                    // Check to see if a pause is required
                    while (pause) {
                        try {
                            Thread.sleep(100);  // This is required, else the pause does not update {SWING mechanism)
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                    
                    // Check for next step
                    if(nextStep) {
                        pause = true;
                        nextStep = false;
                    }
                    
                    
                    
                    
                    
                    
                    if( q < m[i][j]) {
                        // Highlight Label1
                        mcmGui.highlightLabel1();
                        
                    } else {
                        // Highlight Label2
                        mcmGui.highlightLabel2();
                    }
                    
                     try {
                            Thread.sleep(DELAY);
                        } catch (Exception e) {

                        }

                     // Next Step
                    
                    if (q < m[i][j]) {
                        
                        
                        
                        
                        // Check to see if a pause is required
                    while (pause) {
                        try {
                            Thread.sleep(100);  // This is required, else the pause does not update {SWING mechanism)
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                    
                    // Check for next step
                    if(nextStep) {
                        pause = true;
                        nextStep = false;
                    }
                        
                        
                        
                        
                        

                        System.out.println("Quantity:\t" + q + " is less than \tm[" + i + "][" + j + "] " + m[i][j] + "| k=" + k);
                        m[i][j] = q;
                        s[i][j] = k;

                        mcmGui.updateGUI_M(i, j, m[i][j]);
                        mcmGui.updateGUI_S(i, j, s[i][j]);

                        try {
                            Thread.sleep(DELAY);
                        } catch (Exception e) {

                        }
                        
                        while (pause) {
                            try {
                                Thread.sleep(100);  // This is required, else the pause does not update {SWING mechanism)
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        }
                        
                        // Check for next step
                    if(nextStep) {
                        pause = true;
                        nextStep = false;
                    }

                    // Unhighlight the Cell
                        mcmGui.updateGUI_M_unhighlight(i, j);
                        mcmGui.updateGUI_S_unhighlight(i, j);
                    }
                    
                     if( q < m[i][j]) {
                        // Highlight Label1
                        mcmGui.unHighlightLabel1();
                        
                    } else {
                        // Highlight Label2
                        mcmGui.unHighlightLabel2();
                    }
                     
                     mcmGui.unHighlightMForEval(i, j);
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

        // Fetch the optimal expression
        optExp = printOptimalExpression1(s, 1, n);
        
        // Update the UI
        mcmGui.setOptimalExpression(optExp);

        return null;

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

    @Override
    public void done() {
        try {
            Object ob = get();
        } catch (InterruptedException ex) {
            Logger.getLogger(MCMGuiBgWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MCMGuiBgWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        mcmGui.postCompletion();
    }

}
