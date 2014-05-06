
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 * This class represents the GUI.
 * @author rohit vobbilisetty, tanuvir singh
 */
public class MCMGui extends javax.swing.JFrame {

    final int LENGTH_CELL = 75;
    
    int len = 0;
    String optExp = "";
    int[][] M;
    int[][] S;
    int[][] fill_M;
    int[][] fill_S;
    int[][] eval_M;
    
    MCMGuiBgWorker bg = null;

    public MCMGui() {
        initComponents();
        
        String tooltipText = "<html>The dimensions of the matrices that need to be multiplied."
                + "<br/>Each dimension is separated by a space."
                + "<br/>e.g. Input is 12 25 30 34 implies that the matrices A1 A2 A3 having dimensions,"
                + "<br/>&nbsp;A1 = (12 X 25)"
                + "<br/>&nbsp;A2 = (25 X 30)"
                + "<br/>&nbsp;A3 = (30 X 34)"
                + "<br/>will be multiplied.</html>";
        inputTextBox.setToolTipText( tooltipText );
    }
    
    public void highlightMForEval(int row, int col) {
        eval_M[row-1][col-1] = 1;
        this.repaint();
    }
    
    public void unHighlightMForEval(int row, int col) {
        eval_M[row-1][col-1] = 0;
        this.repaint();
    }
    
    public void updateGUI_M(int row, int col, int value) {
        M[row][col] = value;
        fill_M[row-1][col-1] = 1;
        this.repaint();
    }
    
    public void updateGUI_M_unhighlight(int row, int col) {
        fill_M[row-1][col-1] = 0;
        this.repaint();
    }
    
     public void updateGUI_S(int row, int col, int value) {
        S[row][col] = value;
        fill_S[row-1][col-1] = 1;
        this.repaint();
    }
    
    public void updateGUI_S_unhighlight(int row, int col) {
        fill_S[row-1][col-1] = 0;
        this.repaint();
    }
    
    public void setComputedLabel(String text) {
        computedExp.setText(text);
    }
    
    public void setExistingLabel(String text) {
        existingExp.setText(text);
    }
    
    public void setOptimalExpression(String exp) {
        optimalExpressionValue.setText(exp);
    }
    
    public void highlightLabel1() {
        computedExp.setForeground(Color.RED);
    }
    
    public void highlightLabel2() {
        existingExp.setForeground(Color.RED);
    }
    
    public void unHighlightLabel1() {
        computedExp.setForeground(Color.BLACK);
    }
    
    public void unHighlightLabel2() {
        existingExp.setForeground(Color.BLACK);
    }
    
    private void paintPanel(Graphics g) {
        
        Graphics2D g1 = (Graphics2D) g;

        int x0 = 20;
        int y0 = 50;

        // TO DO 
        // Print m
        int tempX = x0 + (LENGTH_CELL * (len - 1) / 2);
        int tempY = y0;

        Font font = getFont();
        Font hugeBoldFont = new Font("TimesRoman", Font.BOLD, 25);
        Font boldFont = new Font("TimesRoman", Font.BOLD, 16);

        g1.setFont(hugeBoldFont);
        if(len!=0) {
        g1.drawString("M", x0, tempY);
        }
        g1.setFont(font);
        
        g1.setFont(boldFont);
        g1.drawString("j", tempX, tempY); 
        g1.setFont(font);

        // Print j
        tempY += 30;
        
        tempX = x0 + LENGTH_CELL / 2;
        
        // Print horizontal headers for M
        g1.setColor(Color.RED);
        for (int i = 0; i < len - 1; i++) {
            g1.drawString(Integer.toString(i + 1), tempX, tempY);
            tempX += LENGTH_CELL;
        }
        g1.setColor(Color.BLACK);

        tempX = x0;
        tempY += 10;

        for (int i = 0; i < len - 1; i++) {
            for (int j = 0; j < len - 1; j++) {

                if (j >= i) {

                    g1.drawRect(tempX, tempY, LENGTH_CELL, LENGTH_CELL);

                    if (eval_M[i][j] == 1) {
                        g1.setColor(Color.RED);
                        g1.drawRect(tempX + 1, tempY + 1, LENGTH_CELL - 2, LENGTH_CELL - 2);
                        g1.setColor(Color.BLACK);
                    }

                    if (fill_M[i][j] == 1) {
                        g1.setColor(Color.YELLOW);
                        g1.fillRect(tempX + 1, tempY + 1, LENGTH_CELL - 1, LENGTH_CELL - 1);
                        g1.setColor(Color.BLACK);
                    }

                    String str = Integer.toString(M[i + 1][j + 1]);

                    FontMetrics fm = getFontMetrics(getFont());
                    int width = fm.stringWidth(str);

                    int offset = (LENGTH_CELL - width) / 2;

                    if (fill_M[i][j] == 1) {
                        g1.setColor(Color.RED);
                    }
                    g1.drawString(str, tempX + offset, tempY + (LENGTH_CELL / 2));
                    if (fill_M[i][j] == 1) {
                        g1.setColor(Color.BLACK);
                    }
                }
                tempX += LENGTH_CELL;
            }
            tempX = x0;
            tempY += LENGTH_CELL;

        }

        // Increase x co-ordinate by some amount, this will be used to print the matrix for S
        x0 = x0 + (LENGTH_CELL * (len - 1)) + 10;

        // Print j i s
        tempX = x0 + ((LENGTH_CELL * len) / 2);
        tempY = y0;
        
        g1.setFont(hugeBoldFont);
        if(len !=0) {
        g1.drawString("S", x0+ LENGTH_CELL, tempY);
        }
        g1.setFont(font);

        g1.setFont(boldFont);
        g1.drawString("j", tempX, tempY);
        g1.setFont(font);

        tempY += 20;


        tempX = x0;
        tempY = y0 + 10 + LENGTH_CELL / 2 + 30;

        g1.setColor(Color.RED);
        // Print vertical headers for M
        // Increase Y keeping X constant
        for (int i = 0; i < len - 1; i++) {
            g1.drawString(Integer.toString(i + 1), tempX, tempY);
            tempY += LENGTH_CELL;
        }
        g1.setColor(Color.BLACK);
        
        
        tempX = x0+ 10 + 10;
        tempY = y0 + 10 + 30 + ( (len -1)/2 * LENGTH_CELL);
        g1.setFont(boldFont);
        g1.drawString("i",tempX, tempY);
        g1.setFont(font);


        tempX = x0 + LENGTH_CELL / 2;
        tempY = y0 + 30;
        g1.setColor(Color.RED);

        // Print horizontal headers for S
        for (int i = 1; i <= len - 1; i++) {
            if (i >= 2) {
                g1.drawString(Integer.toString(i), tempX, tempY);
            }
            tempX += LENGTH_CELL;
        }
        g1.setColor(Color.BLACK);

        tempX = x0;
        tempY += 10;

        for (int i = 0; i < len - 1; i++) {
            for (int j = 0; j < len - 1; j++) {

                if (j > i) {
                    g1.drawRect(tempX, tempY, LENGTH_CELL, LENGTH_CELL);

                    if (fill_S[i][j] == 1) {
                        g1.setColor(Color.GREEN);
                        g1.fillRect(tempX + 1, tempY + 1, LENGTH_CELL - 1, LENGTH_CELL - 1);
                        g1.setColor(Color.BLACK);
                    }

                    String str = Integer.toString(S[i + 1][j + 1]);
                    FontMetrics fm = getFontMetrics(getFont());
                    int width = fm.stringWidth(str);

                    int offset = (LENGTH_CELL - width) / 2;

                    if (fill_S[i][j] == 1) {
                        g1.setColor(Color.RED);
                    }

                    g1.drawString(str, tempX + offset, tempY + (LENGTH_CELL / 2));

                    if (fill_S[i][j] == 1) {
                        g1.setColor(Color.BLACK);
                    }
                }
                tempX += LENGTH_CELL;
            }
            tempX = x0;
            tempY += LENGTH_CELL;

        }

        x0 = x0 + (LENGTH_CELL * (len - 1)) + 10;

        tempX = x0;
        tempY = y0 + 10 + LENGTH_CELL / 2 + 30;

        g1.setColor(Color.RED);
        // Print vertical headers for S
        // Increase Y keeping X constant
        for (int i = 0; i < len - 2; i++) {
            g1.drawString(Integer.toString(i + 1), tempX, tempY);
            tempY += LENGTH_CELL;
        }
        g1.setColor(Color.BLACK);
        
        tempX = x0 + 20;
        tempY = y0 + 10 + ( (len-1-1)*LENGTH_CELL/2 ) + 30;
        g1.setFont(boldFont);
        g1.drawString("i", tempX, tempY);
        g1.setFont(font);
        
    }
    
    private boolean validateInput() {
        boolean success = false;
        
        String input = inputTextBox.getText();
        
        Matcher m = Pattern.compile("^([0-9]{1,}\\s)+[0-9]{1,}\\s{0,1}$").matcher(input);
        
        if(m.matches() && inputTextBox.getText().split("\\s").length >= 2 ) {
            success = true;
        }
        
        return success;
    }
    
    private int[] parseInput() {
        int[] inArray = null;
        
        if(validateInput()) {
            
            String[] elems = inputTextBox.getText().split("\\s");
            inArray = new int[elems.length];
            
            int i=0;
            for(String elem : elems) {
                
                inArray[i] = Integer.parseInt(elem);
                i++;       
            }
        }
        
        return inArray;
    }
    
    public void postCompletion( String message ) {
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        resumeButton.setEnabled(false);
        nextStepButton.setEnabled(false);
        inputTextBox.setEnabled(true);
        statusLabel.setText( message );
        
    }

    public void setStatusMessage(String text) {
        statusLabel.setText(text);
    }
    
    
    /*
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inputsPanel = new javax.swing.JPanel();
        pauseButton = new javax.swing.JButton();
        startButton = new javax.swing.JButton();
        resumeButton = new javax.swing.JButton();
        inputTextBox = new javax.swing.JTextField();
        dimensionsLabel = new javax.swing.JLabel();
        nextStepButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        visualizationScrollPane = new javax.swing.JScrollPane();
        visualizationPanel = new javax.swing.JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                paintPanel(g);
            }
        };
        titleLabel = new javax.swing.JLabel();
        statusPanel = new javax.swing.JPanel();
        statusLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        outputsPanel = new javax.swing.JPanel();
        computedLabel = new javax.swing.JLabel();
        computedExp = new javax.swing.JLabel();
        existingExp = new javax.swing.JLabel();
        existingLabel = new javax.swing.JLabel();
        optimalExpressionLabel = new javax.swing.JLabel();
        optimalExpressionValue = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Matrix Chain Multiplication");

        inputsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Inputs"));

        pauseButton.setText("PAUSE");
        pauseButton.setEnabled(false);
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });

        startButton.setText("START");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        resumeButton.setText("RESUME");
        resumeButton.setEnabled(false);
        resumeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resumeButtonActionPerformed(evt);
            }
        });

        inputTextBox.setText("30 35 15 5 10 20 25");
        inputTextBox.setToolTipText("");
        inputTextBox.setAutoscrolls(false);
        inputTextBox.setMaximumSize(new java.awt.Dimension(20, 20));
        inputTextBox.setMinimumSize(new java.awt.Dimension(20, 20));

        dimensionsLabel.setText("Dimensions:");

        nextStepButton.setText("NEXT STEP");
        nextStepButton.setEnabled(false);
        nextStepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextStepButtonActionPerformed(evt);
            }
        });

        stopButton.setText("STOP");
        stopButton.setEnabled(false);
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout inputsPanelLayout = new javax.swing.GroupLayout(inputsPanel);
        inputsPanel.setLayout(inputsPanelLayout);
        inputsPanelLayout.setHorizontalGroup(
            inputsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputsPanelLayout.createSequentialGroup()
                .addGroup(inputsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inputsPanelLayout.createSequentialGroup()
                        .addComponent(startButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pauseButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resumeButton))
                    .addGroup(inputsPanelLayout.createSequentialGroup()
                        .addComponent(dimensionsLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(inputTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inputsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nextStepButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(stopButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        inputsPanelLayout.setVerticalGroup(
            inputsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inputsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startButton)
                    .addComponent(pauseButton)
                    .addComponent(resumeButton)
                    .addComponent(nextStepButton))
                .addGap(18, 18, 18)
                .addGroup(inputsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dimensionsLabel)
                    .addComponent(inputTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stopButton))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        visualizationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Visualization"));
        visualizationPanel.setPreferredSize(new java.awt.Dimension(500, 500));

        javax.swing.GroupLayout visualizationPanelLayout = new javax.swing.GroupLayout(visualizationPanel);
        visualizationPanel.setLayout(visualizationPanelLayout);
        visualizationPanelLayout.setHorizontalGroup(
            visualizationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 829, Short.MAX_VALUE)
        );
        visualizationPanelLayout.setVerticalGroup(
            visualizationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
        );

        visualizationScrollPane.setViewportView(visualizationPanel);

        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        titleLabel.setText("Matrix Chain Multiplication");

        statusPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        statusLabel.setText("Ready");

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        outputsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Runtime Parameters and Output"));

        computedLabel.setBackground(new java.awt.Color(255, 255, 255));
        computedLabel.setText("Computed:");

        computedExp.setText("-");

        existingExp.setText("-");

        existingLabel.setText("Existing:");

        optimalExpressionLabel.setText("Optimal Expression:");

        optimalExpressionValue.setText("-");

        javax.swing.GroupLayout outputsPanelLayout = new javax.swing.GroupLayout(outputsPanel);
        outputsPanel.setLayout(outputsPanelLayout);
        outputsPanelLayout.setHorizontalGroup(
            outputsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(outputsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(computedLabel)
                    .addComponent(existingLabel)
                    .addComponent(optimalExpressionLabel))
                .addGap(28, 28, 28)
                .addGroup(outputsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(computedExp)
                    .addComponent(optimalExpressionValue)
                    .addComponent(existingExp))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        outputsPanelLayout.setVerticalGroup(
            outputsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(outputsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(computedLabel)
                    .addComponent(computedExp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(outputsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(existingLabel)
                    .addComponent(existingExp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(outputsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(optimalExpressionLabel)
                    .addComponent(optimalExpressionValue))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(outputsPanel);

        fileMenu.setText("File");

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText("Help");

        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(statusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(visualizationScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 843, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titleLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(inputsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inputsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(visualizationScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        // TODO add your handling code here:
        
        // Validate Input textbox
        if(!validateInput()) {
            JOptionPane.showMessageDialog(this, "Invalid input detected.\n\nRules:\n\n1) The input should only contain numbers.\n2) Make sure that there are atleast 2 dimensions provided in the input.");
            return;
        }

       // System.out.println("parsing input");
        int[] input = parseInput();
      //  System.out.println("parsed input");

        // Init some variables
        
        len = input.length;

        M = new int[len][len];
        S = new int[len][len];
        
        fill_M = new int[len][len];
        fill_S = new int[len][len];
        eval_M = new int[len][len];
        
        for(int i=0;i<fill_M.length;i++) {
            for(int j=0;j<fill_M[0].length;j++) {
                fill_M[i][j] = 0;
                fill_S[i][j] = 0;
                eval_M[i][j] = 0;
            }
        }
        
        int width = len * 3 * LENGTH_CELL ;
        int height = (len + 2) * LENGTH_CELL;

        visualizationPanel.setPreferredSize(new Dimension(width,height));
        visualizationScrollPane.revalidate();
        
        startButton.setEnabled(false);
        resumeButton.setEnabled(true);
        nextStepButton.setEnabled(true);
        inputTextBox.setEnabled(false);
        stopButton.setEnabled(false);
        computedExp.setText("-");
        existingExp.setText("-");
        optimalExpressionValue.setText("PENDING");
        
        bg = new MCMGuiBgWorker(input, this);
        bg.setPause(true);
        bg.execute();
        
    }//GEN-LAST:event_startButtonActionPerformed

    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseButtonActionPerformed
        // TODO add your handling code here:
        pauseButton.setEnabled(false);
        resumeButton.setEnabled(true);
        nextStepButton.setEnabled(true);
        stopButton.setEnabled(false);
        statusLabel.setText("Execution Paused. Press 'Resume' or 'Next Step' to continue.");
        bg.setPause(true);
        
        
    }//GEN-LAST:event_pauseButtonActionPerformed

    private void resumeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resumeButtonActionPerformed
        // TODO add your handling code here:
        resumeButton.setEnabled(false);
        nextStepButton.setEnabled(false);
        pauseButton.setEnabled(true);
        stopButton.setEnabled(true);
        statusLabel.setText("Execution in progress.");
        bg.setPause(false);
        
    }//GEN-LAST:event_resumeButtonActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "Matrix Chain Multiplication v0.1\n\nCreated by:\nRohit Vobbilisetty\nTanuvir Singh.\n\nAlgorithm adapted from CRLS.");
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void nextStepButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextStepButtonActionPerformed
        // TODO add your handling code here:
        bg.setNextStep(true);
    }//GEN-LAST:event_nextStepButtonActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        // TODO add your handling code here:
        System.exit(-1);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        // TODO add your handling code here:
        stopButton.setEnabled(false);
        bg.stopExecution();
    }//GEN-LAST:event_stopButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MCMGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MCMGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MCMGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MCMGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MCMGui().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JLabel computedExp;
    private javax.swing.JLabel computedLabel;
    private javax.swing.JLabel dimensionsLabel;
    private javax.swing.JLabel existingExp;
    private javax.swing.JLabel existingLabel;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JTextField inputTextBox;
    private javax.swing.JPanel inputsPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JButton nextStepButton;
    private javax.swing.JLabel optimalExpressionLabel;
    private javax.swing.JLabel optimalExpressionValue;
    private javax.swing.JPanel outputsPanel;
    private javax.swing.JButton pauseButton;
    private javax.swing.JButton resumeButton;
    private javax.swing.JButton startButton;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JButton stopButton;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel visualizationPanel;
    private javax.swing.JScrollPane visualizationScrollPane;
    // End of variables declaration//GEN-END:variables
}
