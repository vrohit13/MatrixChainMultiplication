
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rohit
 */
public class MCMGui extends javax.swing.JFrame {

    int len = 0;
    String optExp = "";
    int[][] M;
    int[][] S;
    int[][] fill_M;
    int[][] fill_S;
    int[][] eval_M;
    
    MCMGuiBgWorker bg = null;

    /**
     * Creates new form NewJFrame
     */
    public MCMGui() {
        initComponents();

        
        len = MCM.getLen();
        System.out.println(len);
        M = new int[len][len];
        S = new int[len][len];
        
        fill_M = new int[len][len];
        fill_S = new int[len][len];
        eval_M = new int[len][len];
        
        for(int i=0;i<fill_M.length;i++) {
            for(int j=0;j<fill_M[0].length;j++) {
                fill_M[i][j] = 0;
            }
        }
        
        for(int i=0;i<fill_S.length;i++) {
            for(int j=0;j<fill_S[0].length;j++) {
                fill_S[i][j] = 0;
            }
        }
        
        for(int i=0;i<eval_M.length;i++) {
            for(int j=0;j<eval_M[0].length;j++) {
                eval_M[i][j] = 0;
            }
        }

      //  this.pack();
    }
    
    public void highlightMForEval(int row, int col) {
        eval_M[row-1][col-1] = 1;
        this.repaint();
       // this.pack();
    }
    
    public void unHighlightMForEval(int row, int col) {
        eval_M[row-1][col-1] = 0;
        this.repaint();
      //  this.pack();
    }
    
    public void updateGUI_M(int row, int col, int value) {
        M[row][col] = value;
        fill_M[row-1][col-1] = 1;
        this.repaint();
       // this.pack();
    }
    
    public void updateGUI_M_unhighlight(int row, int col) {
        fill_M[row-1][col-1] = 0;
        this.repaint();
       // this.pack();
    }
    
     public void updateGUI_S(int row, int col, int value) {
        S[row][col] = value;
        fill_S[row-1][col-1] = 1;
        this.repaint();
       // this.pack();
    }
    
    public void updateGUI_S_unhighlight(int row, int col) {
        fill_S[row-1][col-1] = 0;
        this.repaint();
      //  this.pack();
    }
    
    public void setLabel1(String text) {
        jLabel3.setText(text);
    }
    
    public void setLabel2(String text) {
        jLabel4.setText(text);
    }
    
    public void highlightLabel1() {
        jLabel3.setForeground(Color.RED);
    }
    
    public void highlightLabel2() {
        jLabel4.setForeground(Color.RED);
    }
    
    public void unHighlightLabel1() {
        jLabel3.setForeground(Color.BLACK);
    }
    
    public void unHighlightLabel2() {
        jLabel4.setForeground(Color.BLACK);
    }
    

    @Override
    public void paint(Graphics g) {
   
        super.paint(g);
        
        
        Graphics2D g1 = (Graphics2D) g;

        int x0 = 40;
        int y0 = 170;

        final int LEN = 75;

        int tempX = x0 + LEN/2;
        int tempY = y0;
        
        // Print horizontal headers for M
        for(int i=0;i<len-1;i++) {
            g1.drawString(Integer.toString(i+1), tempX, tempY);
            tempX+= LEN;
        }
        
        tempX = x0;
        tempY+=10;
        

        for (int i = 0; i < len - 1; i++) {
            for (int j = 0; j < len - 1; j++) {

                if (j >= i) {
                    
                                      
                    g1.drawRect(tempX, tempY, LEN, LEN);
                    
                    if(eval_M[i][j] == 1) {
                        g1.setColor(Color.RED);
                        g1.drawRect(tempX+1, tempY+1, LEN-2, LEN-2);
                        g1.setColor(Color.BLACK);
                    }
                    
                    if(fill_M[i][j] == 1) {
                        g1.setColor(Color.YELLOW);
                        g1.fillRect(tempX+1, tempY+1, LEN-1, LEN-1);
                        g1.setColor(Color.BLACK);
                    }

                    String str = Integer.toString(M[i + 1][j + 1]);
                    
                    FontMetrics fm = getFontMetrics(getFont());
                    int width = fm.stringWidth(str);

                    int offset = (LEN - width) / 2;

                     if(fill_M[i][j] == 1) {
                         g1.setColor(Color.RED);
                     }
                    g1.drawString(str, tempX + offset, tempY + (LEN / 2));
                    if(fill_M[i][j] == 1) {
                         g1.setColor(Color.BLACK);
                     }
                }
                tempX += LEN;
            }
            tempX = x0;
            tempY += LEN;

        }
        
        // Increase x co-ordinate by some amount, this will be used to print the matrix for S
        x0 = x0 + (LEN * (len -1) ) + 10 ;
        tempX = x0 ;
        tempY = y0+10+LEN/2;
        
        // Print vertical headers for M
        // Increase Y keeping X constant
        for(int i=0;i<len-1;i++){
            g1.drawString(Integer.toString(i+1), tempX, tempY);
            tempY+=LEN;
        }
        
        tempX = x0 + LEN/2;
        tempY= y0;
        
        
        // Print horizontal headers for S
        for(int i=1;i<=len-1;i++) {
            if (i >= 2) {
                g1.drawString(Integer.toString(i), tempX, tempY);
            }
            tempX+= LEN;
        }
        
        tempX = x0;
        tempY+=10;

        for (int i = 0; i < len -1 ; i++) {
            for (int j = 0; j < len -1 ; j++) {

                if (j>i) {
                    g1.drawRect(tempX, tempY, LEN, LEN);
                    
                    if(fill_S[i][j] == 1) {
                        g1.setColor(Color.GREEN);
                        g1.fillRect(tempX+1, tempY+1, LEN-1, LEN-1);
                        g1.setColor(Color.BLACK);
                    }
                    
                    String str = Integer.toString(S[i + 1][j + 1]);
                    FontMetrics fm = getFontMetrics(getFont());
                    int width = fm.stringWidth(str);

                    int offset = (LEN - width) / 2;
                    
                    if(fill_S[i][j] == 1) {
                         g1.setColor(Color.RED);
                     }

                    g1.drawString(str, tempX + offset, tempY + (LEN / 2));
                    
                    if(fill_S[i][j] == 1) {
                         g1.setColor(Color.BLACK);
                     }
                }
                tempX += LEN;
            }
            tempX = x0;
            tempY += LEN;

        }
        
         x0 = x0 + (LEN * (len -1) ) + 10 ;
         tempX = x0;
         tempY= y0 +10 + LEN/2;
        
        // Print vertical headers for S
         // Increase Y keeping X constant
        for(int i=0;i<len-2;i++) {
            g1.drawString(Integer.toString(i+1), tempX, tempY);
            tempY+=LEN;
        }     

    }

    /*
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Inputs"));

        jButton2.setText("PAUSE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("START");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("RESUME");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField1.setText("jTextField1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton3))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Outputs"));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Computed:");

        jLabel3.setText("jLabel3");

        jLabel4.setText("jLabel4");

        jLabel2.setText("Existing:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addContainerGap(406, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(331, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(421, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        bg = new MCMGuiBgWorker(this);
        bg.execute();
        optExp = MCM.getOptExp();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        bg.setPause(true);
        
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        bg.setPause(false);
        
    }//GEN-LAST:event_jButton3ActionPerformed

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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
