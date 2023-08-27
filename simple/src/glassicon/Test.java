package glassicon;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import java.awt.EventQueue;

/**
 *
 * @author RAVEN
 */
public class Test extends javax.swing.JFrame {

    /**
     * Creates new form Test
     */
    public Test() {
        initComponents();
        jLabel1.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Glass.background;");
        jLabel2.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Glass.background;");
        jLabel3.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Glass.background;");
        jLabel4.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Glass.background;");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        glassIcon1 = new raven.glassmorphism.GlassIcon();
        glassIcon2 = new raven.glassmorphism.GlassIcon();
        glassIcon3 = new raven.glassmorphism.GlassIcon();
        glassIcon4 = new raven.glassmorphism.GlassIcon();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        glassIcon1.setGlassIconConfig(new raven.glassmorphism.GlassIconConfig(
            "/glassicon/card.svg", 5.0f, 0, 5,
            new java.util.HashMap<Integer, String>(){
                {
                    put(0,"@background");
                    put(1,"@foreground");
                    put(2,"@foreground");
                }
            },
            new raven.glassmorphism.GlassIconConfig.GlassShape(
                java.awt.Color.decode("#993872"),
                new java.awt.geom.RoundRectangle2D.Double(2.0, 2.0, 10.0, 10.0, 5.0, 5.0),
                45.0f)
        ));

        glassIcon2.setGlassIconConfig(new raven.glassmorphism.GlassIconConfig(
            "/glassicon/doc.svg", 5.0f, 1, 5,
            new java.util.HashMap<Integer, String>(){
                {
                    put(0,"@foreground");
                    put(1,"@background");
                    put(2,"@foreground");
                    put(3,"@foreground");
                    put(4,"@foreground");
                }
            },
            new raven.glassmorphism.GlassIconConfig.GlassShape(
                java.awt.Color.decode("#788507"),
                new java.awt.geom.RoundRectangle2D.Double(2.0, 2.0, 10.0, 10.0, 5.0, 5.0),
                45.0f)
        ));

        glassIcon3.setGlassIconConfig(new raven.glassmorphism.GlassIconConfig(
            "/glassicon/currency.svg", 5.0f, 0, 5,
            new java.util.HashMap<Integer, String>(){
                {
                    put(0,"@background");
                    put(1,"@foreground");
                    put(2,"@foreground");
                }
            },
            new raven.glassmorphism.GlassIconConfig.GlassShape(
                java.awt.Color.decode("#0ca064"),
                new java.awt.geom.RoundRectangle2D.Double(2.0, 2.0, 10.0, 10.0, 5.0, 5.0),
                45.0f)
        ));

        glassIcon4.setGlassIconConfig(new raven.glassmorphism.GlassIconConfig(
            "/glassicon/dollar.svg", 5.0f, 0, 5,
            new java.util.HashMap<Integer, String>(){
                {
                    put(0,"@background");
                    put(1,"@foreground");
                    put(2,"@foreground");
                    put(3,"@foreground");
                    put(4,"@foreground");
                }
            },
            new raven.glassmorphism.GlassIconConfig.GlassShape(
                java.awt.Color.decode("#e7e510"),
                new java.awt.geom.RoundRectangle2D.Double(7.0, 7.0, 10.0, 10.0, 10.0, 10.0),
                133.0f)
        ));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton2.setText("Change Mode");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(88, 139, 190));
        jLabel1.setIcon(glassIcon1);

        jLabel2.setForeground(new java.awt.Color(88, 139, 190));
        jLabel2.setIcon(glassIcon2);

        jLabel3.setForeground(new java.awt.Color(88, 139, 190));
        jLabel3.setIcon(glassIcon3);

        jLabel4.setForeground(new java.awt.Color(88, 139, 190));
        jLabel4.setIcon(glassIcon4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(198, 198, 198)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addContainerGap(131, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (!FlatLaf.isLafDark()) {
            EventQueue.invokeLater(() -> {
                FlatAnimatedLafChange.showSnapshot();
                FlatDarculaLaf.setup();
                FlatLaf.updateUI();
                FlatAnimatedLafChange.hideSnapshotWithAnimation();
            });
        } else {
            EventQueue.invokeLater(() -> {
                FlatAnimatedLafChange.showSnapshot();
                FlatIntelliJLaf.setup();
                FlatLaf.updateUI();
                FlatAnimatedLafChange.hideSnapshotWithAnimation();
            });
        }
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(Test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        FlatLaf.registerCustomDefaultsSource("glassicon");
        FlatIntelliJLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Test().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private raven.glassmorphism.GlassIcon glassIcon1;
    private raven.glassmorphism.GlassIcon glassIcon2;
    private raven.glassmorphism.GlassIcon glassIcon3;
    private raven.glassmorphism.GlassIcon glassIcon4;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
