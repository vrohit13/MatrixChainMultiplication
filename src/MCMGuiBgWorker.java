
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 * This class represents the Background worker for the GUI.
 * @author rohit vobbilisetty, tanuvir singh
 */
public class MCMGuiBgWorker extends SwingWorker<Object, Void> {

    long DELAY = 500;

    Boolean pause = false;
    boolean nextStep = false;
    boolean stop = false;

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
        
        this.stop = false;
        this.pause = false;
        this.nextStep = false;
    }

    public void setPause(Boolean flag) {
        if (flag) {
            mcmGui.setStatusMessage("Execution Paused. Press 'Resume' or 'Next Step' to continue.");
        }
        this.pause = flag;
    }
    
    public void setNextStep(boolean flag) {
        
        this.nextStep = flag;   // This will make sure that the current step is unpaused and paused after the next step.
        pause = false;  // Unpause current step
    }
    
    private void sleep() {
        
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void sleepWithDelay() {
        
        try {
            Thread.sleep( this.DELAY );
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void stopExecution() {
        this.stop = true;
    }
    
    private void checkForPauseAndNextStep() {
        
        // Check to see if a pause is required
        while (pause) {
            try {
                Thread.sleep(100);  // This is required, else the pause does not update {SWING mechanism)
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }

        // Check for next step
        if (nextStep) {
            pause = true;
            nextStep = false;
        } 
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

        mcmGui.setStatusMessage("Initializing each cell of 'M' with a huge value.");
        // Update GUI with max values
        for (int l = 2; l <= n; l++) {

            for (int i = 1; i <= n - l + 1; i++) {
                
                if(this.stop) {
                    return null;
                }
                
                int j = i + l - 1;

                // Assign a huge value to each cell, new values will be compared against this value to check which is lower
                // Of course, new values will be lower than this initial value.
                m[i][j] = Integer.MAX_VALUE;
                mcmGui.updateGUI_M(i, j, m[i][j]);

                this.sleep();   // This is required, else the pause does not update {SWING mechanism)

                // Unhighlight the Cell
                mcmGui.updateGUI_M_unhighlight(i, j);
            }
        }

        mcmGui.setStatusMessage("'M' initialized.");
        

        for (int l = 2; l <= n; l++) {

            for (int i = 1; i <= n - l + 1; i++) {

                int j = i + l - 1;

                for (int k = i; k <= j - 1; k++) {
                    
                    if(this.stop) {
                    return null;
                }
                    
                    this.checkForPauseAndNextStep();    // This is required, else the pause does not update {SWING mechanism)

                    mcmGui.setComputedLabel("");
                    mcmGui.setExistingLabel("");

                    // Highlight cell for evaluation
                    mcmGui.highlightMForEval(i, j);

                    this.checkForPauseAndNextStep();

                    int q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];

                    mcmGui.unHighlightLabel1();
                    mcmGui.unHighlightLabel2();

                    String str1 = "m[" + i + "][" + j + "] = "
                            + "m[" + i + "][" + k + "] + " + "m[" + (k + 1) + "][" + j + "] + p" + (i - 1) + "p" + k + "p" + j + " = "
                            + (m[i][k]) + " + " + (m[k + 1][j]) + " + " + (p[i - 1]) + " * " + (p[k]) + " * " + (p[j]) + " = "
                            + q + " ( K = " + k + " )";
                    
                    mcmGui.setComputedLabel(str1);

                    String str2 = "m[" + i + "][" + j + "] = " + m[i][j] + " ( K = " + s[i][j] + " ) ";
                    mcmGui.setExistingLabel(str2);

                    this.sleepWithDelay();
                    
                    this.checkForPauseAndNextStep();

                    if (q < m[i][j]) {
                        // Highlight Label1
                        mcmGui.highlightLabel1();

                    } else {
                        // Highlight Label2
                        mcmGui.highlightLabel2();
                    }

                    this.sleepWithDelay();

                     // Next Step
                    if (q < m[i][j]) {

                        this.checkForPauseAndNextStep();

                        m[i][j] = q;
                        s[i][j] = k;

                        mcmGui.updateGUI_M(i, j, m[i][j]);
                        mcmGui.updateGUI_S(i, j, s[i][j]);

                        this.sleepWithDelay();
                    
                        this.checkForPauseAndNextStep();

                        // Unhighlight the Cell
                        mcmGui.updateGUI_M_unhighlight(i, j);
                        mcmGui.updateGUI_S_unhighlight(i, j);
                    }

                    if (q < m[i][j]) {
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
        
        // Fetch the optimal expression
        optExp = computeOptimalExpression(s, 1, n);

        // Update the UI
        mcmGui.setOptimalExpression(optExp);

        return null;
    }

    public static String computeOptimalExpression(int s[][], int i, int j) {

        String opt = "";

        if (i == j) {
            System.out.print("A" + i);
            opt += "A" + i;
        } else {
            System.out.print("(");
            opt += "(";
            opt += computeOptimalExpression(s, i, s[i][j]);

            opt += " * ";
            System.out.print(" * ");

            opt += computeOptimalExpression(s, s[i][j] + 1, j);
            opt += ")";
            System.out.print(")");

        }

        return opt;
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
        
        String message = "Execution Completed.";
        
        if( this.stop ) {
            message = "Execution Aborted";
        }
        
        mcmGui.postCompletion( message );
    }
}