/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import static GUI.CapturarHuella.lblPasos;
import clases.ConexionBD;
import clases.IngresoHuella;
import clases.LeerHuella;
//import clases.LeerHuella;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusListener;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 *
 * @author Rec Tecnologicos
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    private byte[] huella = null;
    private DPFPCapture lector = DPFPGlobal.getCaptureFactory().createCapture();
     private DPFPEnrollment enrrollador;
     public DPFPTemplate plantillahuella;

    public void setPlantillahuella(DPFPTemplate plantillahuella) {
        this.plantillahuella = plantillahuella;
    }

    public DPFPTemplate getPlantillahuella() {
        return plantillahuella;
    }
    
    
    
    public MainFrame() {
        initComponents();
        this.pack();
        this.setLocationRelativeTo(null);
        
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e){
                init();
                start();
            }
            
            public void componentHiden(ComponentEvent e){
                
            }
        });
        
        try {
            enrrollador = DPFPGlobal.getEnrollmentFactory().createEnrollment();
        } catch (java.lang.UnsatisfiedLinkError | java.lang.NoClassDefFoundError e) {
            setVisible(false);
        }
        
        switch(enrrollador.getTemplateStatus()){
            case TEMPLATE_STATUS_READY:
                stop();
                plantillahuella = enrrollador.getTemplate();
                setVisible(false);
                break;

            case TEMPLATE_STATUS_FAILED:
                enrrollador.clear();
                stop();
                plantillahuella = null;
                lblPasos.setIcon(new ImageIcon(getClass().getResource("/images/score0.png")));
                start();
                break;
            default: break;

        }
    }
    
    protected void init(){
        lector.addDataListener((DPFPDataEvent dpfpde)->{
            //procesarHuella(dpfpde.getSample());
            //validarHuella(dpfpde.getSample());
        });
        
        lector.addReaderStatusListener(new DPFPReaderStatusListener() {
            @Override
            public void readerConnected(DPFPReaderStatusEvent dpfprs) {
                lblHuella.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/finger_matched.png")).getImage().getScaledInstance(lblHuella.getWidth(), lblHuella.getHeight(), Image.SCALE_DEFAULT)));
            }

            @Override
            public void readerDisconnected(DPFPReaderStatusEvent dpfprs) {
                lblHuella.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/finger_disabled.png")).getImage().getScaledInstance(lblHuella.getWidth(), lblHuella.getHeight(), Image.SCALE_DEFAULT)));
            }
        });
    }
    
    protected void procesarHuella(DPFPSample sample){
        Image image = DPFPGlobal.getSampleConversionFactory().createImage(sample);
        drawPicture(image);
    }
    
    public void drawPicture(Image image){
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bufferedImage.createGraphics();
        bGr.drawImage(image, 0, 0, null);
        bGr.dispose();
        
        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(180), bufferedImage.getWidth()/2, bufferedImage.getHeight()/2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        bufferedImage = op.filter(bufferedImage, null);
        lblHuella.setIcon(new ImageIcon(image.getScaledInstance(lblHuella.getWidth(), lblHuella.getHeight(), Image.SCALE_DEFAULT)));
        
    }
    
    protected void start(){
        lector.startCapture();
    }
    
    protected void stop(){
        lector.stopCapture();
    }
    
    protected DPFPFeatureSet extractFeature(DPFPSample sample, DPFPDataPurpose purpose){
        DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
        try {
            return extractor.createFeatureSet(sample, purpose);
        } catch (DPFPImageQualityException e) {
            return null;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        rSLabelFecha1 = new rojeru_san.RSLabelFecha();
        rSLabelHora1 = new rojeru_san.RSLabelHora();
        lblHuella = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuRegistrar = new javax.swing.JMenuItem();
        menuCerrar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);

        rSLabelFecha1.setFont(new java.awt.Font("Roboto Bold", 1, 36)); // NOI18N

        rSLabelHora1.setFont(new java.awt.Font("Roboto Bold", 1, 24)); // NOI18N

        lblHuella.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHuella.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/emptySensor.png"))); // NOI18N
        lblHuella.setToolTipText("");
        lblHuella.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                lblHuellaPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rSLabelFecha1, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
                    .addComponent(rSLabelHora1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblHuella)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(rSLabelFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rSLabelHora1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(lblHuella, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jMenu1.setText("Administrador");

        menuRegistrar.setText("Registrar Funcionario");
        menuRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRegistrarActionPerformed(evt);
            }
        });
        jMenu1.add(menuRegistrar);

        menuCerrar.setText("Cerrar");
        menuCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCerrarActionPerformed(evt);
            }
        });
        jMenu1.add(menuCerrar);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCerrarActionPerformed

        MainFrame frame = new MainFrame();
        //String pass = JOptionPane.showInputDialog(frame, "Ingrese Contraseña de Administrador");
        
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Ingrese Contraseña de Administrador");
        JPasswordField pass = new JPasswordField(10);
        panel.add(label);
        panel.add(pass);
        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Administrador",
                              JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                              null, options, options[1]);
        pass.setFocusable(true);
        pass.requestFocus();
        if(option == 0) // pressing OK button
        {
            char[] password = pass.getPassword();
            //String password = pass.getText();
            String passadmin = new String(password);
            //System.out.println("Your password is: " + new String(password));
            ConexionBD con = new ConexionBD();
            con.conectar();

            try {
                ResultSet consulta = con.consultar("Select estado from administrador where pass = MD5('"+passadmin+"');");
                String estado="";
                //con.cerrar();
                while (consulta.next()) {                
                    estado = consulta.getString("estado");
                }
                if ("activo".equals(estado)) {
                    System.exit(0);                    
                    System.out.println(estado);
                    //frame.setVisible(false);
                    //frame.dispose();
                    //frame.finalize();
                } 
                //System.out.println(estado);
            } catch (SQLException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Throwable ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        
    }//GEN-LAST:event_menuCerrarActionPerformed

    private void menuRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRegistrarActionPerformed
        MainFrame frame = new MainFrame();
        //String pass = JOptionPane.showInputDialog(frame, "Ingrese Contraseña de Administrador");
        
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Ingrese Contraseña de Administrador");
        JPasswordField pass = new JPasswordField(10);
        panel.add(label);
        panel.add(pass);
        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Administrador",
                              JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                              null, options, options[1]);
        pass.setFocusable(true);
        pass.requestFocus();
        if(option == 0) // pressing OK button
        {
            char[] password = pass.getPassword();
            //String password = pass.getText();
            String passadmin = new String(password);
            //System.out.println("Your password is: " + new String(password));
            ConexionBD con = new ConexionBD();
            con.conectar();

            try {
                ResultSet consulta = con.consultar("Select estado from administrador where pass = MD5('"+passadmin+"');");
                String estado="";
                //con.cerrar();
                while (consulta.next()) {                
                    estado = consulta.getString("estado");
                }
                if ("activo".equals(estado)) {
                    Main main =  new Main();
                    main.setVisible(true);
                } 
                //System.out.println(estado);
            } catch (SQLException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Throwable ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_menuRegistrarActionPerformed

    private void lblHuellaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_lblHuellaPropertyChange
        //DPFPSample sample = null;
        // TODO add your handling code here:
        
        //IngresoHuella ih = new IngresoHuella();
        //revisarHuella(sample);
        System.out.println(lblHuella.getIcon());
        MainFrame mainFrm = new MainFrame();
        huella = mainFrm.getPlantillahuella().serialize();
        System.out.println(huella);
        
        
        //huella = lblHuella
        /*try {
            huella = lh.plantillahuella.serialize();
            System.out.println("huella h " + huella.length);
        } catch (Exception e) {
            System.out.println("Error "+ e);
        }*/
            

    }//GEN-LAST:event_lblHuellaPropertyChange

   
  
    
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblHuella;
    private javax.swing.JMenuItem menuCerrar;
    private javax.swing.JMenuItem menuRegistrar;
    private rojeru_san.RSLabelFecha rSLabelFecha1;
    private rojeru_san.RSLabelHora rSLabelHora1;
    // End of variables declaration//GEN-END:variables
}
