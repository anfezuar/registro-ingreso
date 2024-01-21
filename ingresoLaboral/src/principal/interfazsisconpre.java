// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   interfazsisconpre.java

package principal;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.Date;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.*;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.button.StandardButtonShaper;
import org.jvnet.substance.utils.SubstanceConstants;

// Referenced classes of package principal:
//            formatosfechahora

public class interfazsisconpre extends JFrame
{
    public class mipanel extends JPanel
    {

        public void paint(Graphics g)
        {
            Dimension tamanio = getSize();
            ImageIcon imagenFondo = new ImageIcon(getClass().getResource("/iconos/iconlocked.png"));
            g.drawImage(imagenFondo.getImage(), 0, 0, tamanio.width, tamanio.height, null);
            setOpaque(false);
            super.paintComponent(g);
        }

        final interfazsisconpre this$0;

        public mipanel()
        {
            this$0 = interfazsisconpre.this;
            super();
            setSize(256, 256);
        }
    }

    private class ManejadorEventosven
        implements ActionListener
    {

        public void actionPerformed(ActionEvent evento)
        {
            for(int i = 0; i < elementosven.length; i++)
                if(evento.getSource() == elementosven[i])
                {
                    String nombreaccion = elementosven[i].getText();
                    if("Eliminar".equals(nombreaccion))
                        preguntardeleteven(fechaventa.toString(), ventanumero.toString());
                    else
                    if("Imprimir".equals(nombreaccion))
                    {
                        agregardtsvistaprevvc();
                        jFvistapreliminarv1.setLocationRelativeTo(null);
                        jFvistapreliminarv1.setVisible(true);
                    }
                    return;
                }

        }

        final interfazsisconpre this$0;

        private ManejadorEventosven()
        {
            this$0 = interfazsisconpre.this;
            super();
        }

    }

    private class ManejadorEventos
        implements ActionListener
    {

        public void actionPerformed(ActionEvent evento)
        {
            for(int i = 0; i < elementos.length; i++)
                if(evento.getSource() == elementos[i])
                {
                    String nombreaccion = elementos[i].getText();
                    if("Modificar".equals(nombreaccion))
                        mostrardamodar(clavearticulo.toString());
                    else
                    if("Eliminar".equals(nombreaccion))
                        preguntardelete(clavearticulo.toString());
                    return;
                }

        }

        final interfazsisconpre this$0;

        private ManejadorEventos()
        {
            this$0 = interfazsisconpre.this;
            super();
        }

    }


    public interfazsisconpre()
    {
        modificard = new ImageIcon(getClass().getResource("/iconos/modificar.gif"));
        eliminar = new ImageIcon(getClass().getResource("/iconos/remove.gif"));
        imprimir = new ImageIcon(getClass().getResource("/iconos/fileprint.gif"));
        grantotal = 0.0F;
        errorguardarventa = 0;
        dataart = new Object[cabecerasart.length];
        dataartv = new Object[cabecerasartv.length];
        dataartv1 = new Object[cabecerasartv1.length];
        datavistaprev = new Object[cabecerasvistaprev.length];
        datavistaprev1 = new Object[cabecerasvistaprev1.length];
        dataconven = new Object[cabecerasconven.length];
        dtmarticulos = new DefaultTableModel();
        dtmarticulosv = new DefaultTableModel();
        dtmarticulosv1 = new DefaultTableModel();
        dtmconven = new DefaultTableModel();
        dtmvistaprev = new DefaultTableModel();
        dtmvistaprev1 = new DefaultTableModel();
        formatos = new formatosfechahora();
        initComponents();
        ManejadorEventos manejador = new ManejadorEventos();
        ManejadorEventosven manejadorven = new ManejadorEventosven();
        String acciones[] = {
            "Modificar", "Eliminar"
        };
        String accionesven[] = {
            "Eliminar", "Imprimir"
        };
        Icon iconosJmenu[] = {
            modificard, eliminar
        };
        Icon iconosJmenuven[] = {
            eliminar, imprimir
        };
        menuContextual = new JPopupMenu();
        elementos = new JMenuItem[2];
        for(int cuenta = 0; cuenta < elementos.length; cuenta++)
        {
            elementos[cuenta] = new JMenuItem(acciones[cuenta], iconosJmenu[cuenta]);
            menuContextual.add(elementos[cuenta]);
            elementos[cuenta].addActionListener(manejador);
        }

        menuContextualven = new JPopupMenu();
        elementosven = new JMenuItem[2];
        for(int cuenta = 0; cuenta < elementosven.length; cuenta++)
        {
            elementosven[cuenta] = new JMenuItem(accionesven[cuenta], iconosJmenuven[cuenta]);
            menuContextualven.add(elementosven[cuenta]);
            elementosven[cuenta].addActionListener(manejadorven);
        }

        initDB();
        colocarnombreempresa();
        setExtendedState(6);
        jDlogin.setLocationRelativeTo(null);
        jDlogin.setVisible(true);
    }

    public void colocarnombreempresa()
    {
        try
        {
            ResultSet res = stnombreempresa.executeQuery();
            String nombreemp;
            if(res.next())
                nombreemp = res.getString("name");
            else
                nombreemp = "Empresa sin nombre S.A DE C.V.";
            super.setTitle(nombreemp);
        }
        catch(SQLException ex)
        {
            int codigoerror = ex.getErrorCode();
            if(codigoerror == 0)
            {
                initDB();
                consultarindicio();
            } else
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    public void agregardtsvistaprevvc()
    {
        DecimalFormat dosDigitos = new DecimalFormat("0.00");
        try
        {
            limpiartblvistaprev1();
            try
            {
                stimprimirventag.setString(1, ventanumero.toString());
                stimprimirventag.setString(2, fechaventa.toString());
                ResultSet res = stimprimirventag.executeQuery();
                int nColumnas = res.getMetaData().getColumnCount();
                for(; res.next(); dtmvistaprev1.addRow(datavistaprev1))
                {
                    for(int i = 1; i <= nColumnas; i++)
                    {
                        if(i == 3)
                        {
                            datavistaprev1[i - 1] = dosDigitos.format(res.getFloat(i));
                            continue;
                        }
                        if(i == 5)
                            datavistaprev1[i - 1] = dosDigitos.format(res.getFloat(i));
                        else
                            datavistaprev1[i - 1] = res.getString(i);
                    }

                }

            }
            catch(SQLException ex)
            {
                int codigoerror = ex.getErrorCode();
                if(codigoerror == 0)
                {
                    initDB();
                    agregardtsvistaprevvc();
                } else
                {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        }
        catch(NullPointerException ex)
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void preguntardelete(String clavebor)
    {
        int respuesta = JOptionPane.showConfirmDialog(null, "\277Realmente desea eliminar este art\355culo?");
        if(respuesta == 0)
            borrarart(clavebor);
    }

    public void preguntardeleteven(String fechaven, String numeroventa)
    {
        int respuesta = JOptionPane.showConfirmDialog(null, "\277Realmente desea eliminar la venta # ".concat(numeroventa).concat("?").concat("\nTenga en cuenta que ser\341n borrados todos los registros de esta."));
        if(respuesta == 0)
            borrarventa(fechaven, numeroventa);
    }

    public void preguntardeletetodasven()
    {
        int respuesta = JOptionPane.showConfirmDialog(null, "\277Realmente desea eliminar todas las ventas\nregistradas hasta el d\355a de hoy?");
        if(respuesta == 0)
            borrartodasven();
    }

    public void borrarart(String claveb)
    {
        boolean error = false;
        try
        {
            stdelarticulo.setString(1, claveb);
            stdelarticulo.execute();
        }
        catch(SQLException ex)
        {
            error = true;
            int codigoerror = ex.getErrorCode();
            if(codigoerror == 0)
            {
                initDB();
                preguntardelete(clavearticulo.toString());
            } else
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
        if(!error)
        {
            limpiartblventa();
            limpiartblconarv1();
            limpiartblconar();
            jtfnombreartcon.setText("");
            jtfnombreartcon.grabFocus();
            JOptionPane.showMessageDialog(null, "El art\355culo fue borrado con \351xito", "Mensaje", 1);
        }
    }

    public void borrarventa(String fechav, String numeroven)
    {
        boolean error = false;
        try
        {
            stdelventa.setString(1, fechav);
            stdelventa.setString(2, numeroven);
            stdelventa.execute();
        }
        catch(SQLException ex)
        {
            error = true;
            int codigoerror = ex.getErrorCode();
            if(codigoerror == 0)
            {
                initDB();
                preguntardeleteven(fechaventa.toString(), ventanumero.toString());
            } else
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
        if(!error)
        {
            limpiartblconventas();
            btnconsultaventas.grabFocus();
            JOptionPane.showMessageDialog(null, "La venta fue borrada con \351xito", "Mensaje", 1);
        }
    }

    public void borrartodasven()
    {
        boolean error = false;
        try
        {
            stdeltodasventa.execute();
        }
        catch(SQLException ex)
        {
            error = true;
            int codigoerror = ex.getErrorCode();
            if(codigoerror == 0)
            {
                initDB();
                borrartodasven();
            } else
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
        if(!error)
            JOptionPane.showMessageDialog(null, "Las ventas fueron borradas con \351xito", "Mensaje", 1);
    }

    private void initComponents()
    {
        jFaltaar = new JFrame();
        jLabel1 = new JLabel();
        jTFclavear = new JTextField();
        jLabel2 = new JLabel();
        jTFdescripcionar = new JTextField();
        jLabel3 = new JLabel();
        jTFprecioar = new JTextField();
        jPanel1 = new JPanel();
        jBguardarar = new JButton();
        jBlimpiardtsar = new JButton();
        jLabel8 = new JLabel();
        jTFpreciocompraar = new JTextField();
        jFconsultaar = new JFrame();
        jPresultadoc6 = new JPanel();
        jScrollPane11 = new JScrollPane();
        tablaconsultaarticulos = new JTable() {

            public boolean isCellEditable(int rowIndex, int colIndex)
            {
                return false;
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
;
        jPanel61 = new JPanel();
        jBExportar = new JButton();
        jButton1 = new JButton();
        btnlimpiartablacart = new JButton();
        jPcontcon6 = new JPanel();
        jPanel59 = new JPanel();
        jtfnombreartcon = new JTextField();
        jPanel60 = new JPanel();
        btnconsultalimnomar = new JButton();
        jFmodificarar = new JFrame();
        jLabel5 = new JLabel();
        jTFdescripcionarm = new JTextField();
        jLabel6 = new JLabel();
        jTFprecioarm = new JTextField();
        jPanel2 = new JPanel();
        jBguardararm = new JButton();
        jLabel14 = new JLabel();
        jTFpreciocompraarm = new JTextField();
        jDlogin = new JDialog();
        jLabel4 = new JLabel();
        jppassword = new JPasswordField();
        jBaceptarcontra = new JButton();
        jBcancelarcontra = new JButton();
        jLabel7 = new JLabel();
        jFactcontraseF1a = new JFrame();
        jLabel9 = new JLabel();
        jTFnuevacontra = new JTextField();
        jLabel10 = new JLabel();
        jTFconfirmarnuecon = new JTextField();
        jLabel11 = new JLabel();
        jTFindicionuecon = new JTextField();
        jPanel3 = new JPanel();
        jBguardardtsnuecon = new JButton();
        jBlimpiartdtsnuecon = new JButton();
        jFaltaventa = new JFrame();
        jPanel5 = new JPanel();
        jLabel16 = new JLabel();
        jLfechaventa = new JLabel();
        jLabel17 = new JLabel();
        jLhoraventa = new JLabel();
        jLabel18 = new JLabel();
        jLnumerodeventa = new JLabel();
        jParticulos = new JPanel();
        jPresultadoc7 = new JPanel();
        jScrollPane12 = new JScrollPane();
        tablaconsultaarticulosven = new JTable() {

            public boolean isCellEditable(int rowIndex, int colIndex)
            {
                return false;
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
;
        jPanel62 = new JPanel();
        btnlimpiartablacart1 = new JButton();
        jPcontcon7 = new JPanel();
        jPanel63 = new JPanel();
        jtfnombreartconv = new JTextField();
        jPanel64 = new JPanel();
        btnconsultalimnomar1 = new JButton();
        jParticulosventa = new JPanel();
        jScrollPane13 = new JScrollPane();
        tablaconsultaarticulosven1 = new JTable() {

            public boolean isCellEditable(int rowIndex, int colIndex)
            {
                return false;
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
;
        jPanel4 = new JPanel();
        jButton2 = new JButton();
        jButton9 = new JButton();
        jButton4 = new JButton();
        jPanel6 = new JPanel();
        jLabel15 = new JLabel();
        jLtotalventa = new JLabel();
        jPpasar = new JPanel();
        jBagregaraventa = new JButton();
        jBquitardeventa = new JButton();
        jPanel7 = new JPanel();
        jTFcantidadarticulos = new JTextField();
        jPextras = new JPanel();
        jLabel19 = new JLabel();
        jTFdescripcionarex = new JTextField();
        jLabel20 = new JLabel();
        jTFprecioarex = new JTextField();
        jLabel21 = new JLabel();
        jTFcantidadarex = new JTextField();
        jButton5 = new JButton();
        jButton6 = new JButton();
        jFconsultaventas = new JFrame();
        jPresultadoc8 = new JPanel();
        jScrollPane14 = new JScrollPane();
        tablaconsultaventas = new JTable() {

            public boolean isCellEditable(int rowIndex, int colIndex)
            {
                return false;
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
;
        jPanel65 = new JPanel();
        btnlimpiartablacart2 = new JButton();
        jPcontcon8 = new JPanel();
        jDCfechaventa = new JDateChooser();
        btnconsultaventas = new JButton();
        jFnombreempresa = new JFrame();
        jLabel12 = new JLabel();
        jTnombreempresa = new JTextField();
        jPanel8 = new JPanel();
        jButton3 = new JButton();
        jButton7 = new JButton();
        jFvistapreliminarv = new JFrame();
        jScrollPane15 = new JScrollPane();
        tablavistapreliminarventa = new JTable() {

            public boolean isCellEditable(int rowIndex, int colIndex)
            {
                return false;
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
;
        jPanel9 = new JPanel();
        jButton8 = new JButton();
        jFvistapreliminarv1 = new JFrame();
        jScrollPane16 = new JScrollPane();
        tablavistapreliminarventa1 = new JTable() {

            public boolean isCellEditable(int rowIndex, int colIndex)
            {
                return false;
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
;
        jPanel10 = new JPanel();
        jButton10 = new JButton();
        jLlogosisconpre = new JLabel();
        jMenuBar1 = new JMenuBar();
        jMenu1 = new JMenu();
        jMIcontraseF1a = new JMenuItem();
        jMenu5 = new JMenu();
        jMenuItem4 = new JMenuItem();
        jMarticulos = new JMenu();
        jMIaltaar = new JMenuItem();
        jMIconsultaar = new JMenuItem();
        jMenu2 = new JMenu();
        jMenuItem1 = new JMenuItem();
        jMenuItem2 = new JMenuItem();
        jMenu3 = new JMenu();
        jMenu4 = new JMenu();
        jMenuItem3 = new JMenuItem();
        jMayuda = new JMenu();
        jMIacercade = new JMenuItem();
        jFaltaar.setTitle("Alta de art\355culo");
        jFaltaar.setIconImage((new ImageIcon(getClass().getResource("/imagenes/logopng.png"))).getImage());
        jFaltaar.setMinimumSize(new Dimension(550, 250));
        GridBagLayout jFaltaarLayout = new GridBagLayout();
        jFaltaarLayout.columnWidths = (new int[] {
            0, 10, 0
        });
        jFaltaarLayout.rowHeights = (new int[] {
            0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 
            0
        });
        jFaltaar.getContentPane().setLayout(jFaltaarLayout);
        jLabel1.setText("Clave");
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = 21;
        jFaltaar.getContentPane().add(jLabel1, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = 21;
        jFaltaar.getContentPane().add(jTFclavear, gridBagConstraints);
        jLabel2.setText("Descripci\363n");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = 21;
        jFaltaar.getContentPane().add(jLabel2, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = 21;
        jFaltaar.getContentPane().add(jTFdescripcionar, gridBagConstraints);
        jLabel3.setText("Precio venta");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = 21;
        jFaltaar.getContentPane().add(jLabel3, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = 21;
        jFaltaar.getContentPane().add(jTFprecioar, gridBagConstraints);
        jPanel1.setLayout(new GridLayout(1, 2, 10, 0));
        jBguardarar.putClientProperty("substancelaf.buttonShaper", new StandardButtonShaper());
        jBguardarar.putClientProperty("substancelaf.buttonside", org.jvnet.substance.utils.SubstanceConstants.Side.BOTTOM);
        jBguardarar.setIcon(new ImageIcon(getClass().getResource("/iconos/filesaved.gif")));
        jBguardarar.setMnemonic('G');
        jBguardarar.setText("Guardar");
        jBguardarar.setPressedIcon(new ImageIcon(getClass().getResource("/iconos/filesavea.gif")));
        jBguardarar.setRolloverIcon(new ImageIcon(getClass().getResource("/iconos/filesavea.gif")));
        jBguardarar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jBguardararActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jPanel1.add(jBguardarar);
        jBlimpiardtsar.putClientProperty("substancelaf.buttonShaper", new StandardButtonShaper());
        jBlimpiardtsar.putClientProperty("substancelaf.buttonside", org.jvnet.substance.utils.SubstanceConstants.Side.BOTTOM);
        jBlimpiardtsar.setIcon(new ImageIcon(getClass().getResource("/iconos/locationbar_erased.gif")));
        jBlimpiardtsar.setMnemonic('L');
        jBlimpiardtsar.setText("Limpiar");
        jBlimpiardtsar.setPressedIcon(new ImageIcon(getClass().getResource("/iconos/locationbar_erase.gif")));
        jBlimpiardtsar.setRolloverIcon(new ImageIcon(getClass().getResource("/iconos/locationbar_erase.gif")));
        jBlimpiardtsar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jBlimpiardtsarActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jPanel1.add(jBlimpiardtsar);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 3;
        jFaltaar.getContentPane().add(jPanel1, gridBagConstraints);
        jLabel8.setText("Precio compra");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = 21;
        jFaltaar.getContentPane().add(jLabel8, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = 21;
        jFaltaar.getContentPane().add(jTFpreciocompraar, gridBagConstraints);
        jFconsultaar.setTitle("Consultar art\355culo");
        jFconsultaar.setIconImage((new ImageIcon(getClass().getResource("/imagenes/logopng.png"))).getImage());
        jFconsultaar.setMinimumSize(new Dimension(740, 500));
        jPresultadoc6.setBorder(BorderFactory.createTitledBorder(new SoftBevelBorder(1), "Clic derecho sobre el registro que desee modificar o eliminar.", 2, 0));
        jPresultadoc6.setLayout(new BorderLayout());
        tablaconsultaarticulos.setModel(dtmarticulos);
        tablaconsultaarticulos.setAutoResizeMode(0);
        for(int column = 0; column < cabecerasart.length; column++)
            dtmarticulos.addColumn(cabecerasart[column]);

        tablaconsultaarticulos.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent evt)
            {
                tablaconsultaarticulosMouseClicked(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jScrollPane11.setViewportView(tablaconsultaarticulos);
        TableColumn columnm = null;
        for(int i = 0; i < tablaconsultaarticulos.getColumnCount(); i++)
        {
            columnm = tablaconsultaarticulos.getColumnModel().getColumn(i);
            if(i == 0)
            {
                columnm.setPreferredWidth(60);
                continue;
            }
            if(i == 1)
            {
                columnm.setPreferredWidth(350);
                continue;
            }
            if(i == 2)
            {
                columnm.setPreferredWidth(150);
                continue;
            }
            if(i == 3)
                columnm.setPreferredWidth(150);
        }

        jPresultadoc6.add(jScrollPane11, "Center");
        jBExportar.putClientProperty("substancelaf.buttonShaper", new StandardButtonShaper());
        jBExportar.putClientProperty("substancelaf.buttonside", org.jvnet.substance.utils.SubstanceConstants.Side.BOTTOM);
        jBExportar.setIcon(new ImageIcon(getClass().getResource("/iconos/pdf-icon.gif")));
        jBExportar.setMnemonic('E');
        jBExportar.setText("Exportar");
        jBExportar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jBExportarActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jPanel61.add(jBExportar);
        jButton1.putClientProperty("substancelaf.buttonShaper", new StandardButtonShaper());
        jButton1.putClientProperty("substancelaf.buttonside", org.jvnet.substance.utils.SubstanceConstants.Side.BOTTOM);
        jButton1.setIcon(new ImageIcon(getClass().getResource("/iconos/fileprint.gif")));
        jButton1.setMnemonic('I');
        jButton1.setText("Imprimir");
        jButton1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jPanel61.add(jButton1);
        btnlimpiartablacart.putClientProperty("substancelaf.buttonShaper", new StandardButtonShaper());
        btnlimpiartablacart.putClientProperty("substancelaf.buttonside", org.jvnet.substance.utils.SubstanceConstants.Side.BOTTOM);
        btnlimpiartablacart.setIcon(new ImageIcon(getClass().getResource("/iconos/removed.gif")));
        btnlimpiartablacart.setMnemonic('R');
        btnlimpiartablacart.setText("Remover");
        btnlimpiartablacart.setPressedIcon(new ImageIcon(getClass().getResource("/iconos/remove.gif")));
        btnlimpiartablacart.setRolloverIcon(new ImageIcon(getClass().getResource("/iconos/remove.gif")));
        btnlimpiartablacart.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                btnlimpiartablacartActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jPanel61.add(btnlimpiartablacart);
        jPresultadoc6.add(jPanel61, "South");
        jFconsultaar.getContentPane().add(jPresultadoc6, "Center");
        jPcontcon6.setBorder(BorderFactory.createTitledBorder(null, "Capture el nombre o presione enter", 2, 0));
        jPcontcon6.setPreferredSize(new Dimension(308, 120));
        jPanel59.setBorder(BorderFactory.createBevelBorder(0));
        jPanel59.setMinimumSize(new Dimension(150, 54));
        jPanel59.setPreferredSize(new Dimension(400, 90));
        GridBagLayout jPanel59Layout = new GridBagLayout();
        jPanel59Layout.columnWidths = (new int[] {
            0, 10, 0
        });
        jPanel59Layout.rowHeights = (new int[] {
            0, 5, 0, 5, 0
        });
        jPanel59.setLayout(jPanel59Layout);
        jtfnombreartcon.addKeyListener(new KeyAdapter() {

            public void keyTyped(KeyEvent evt)
            {
                jtfnombreartconKeyTyped(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.anchor = 21;
        gridBagConstraints.insets = new Insets(3, 0, 0, 5);
        jPanel59.add(jtfnombreartcon, gridBagConstraints);
        jPanel60.setLayout(new GridLayout(1, 2, 2, 0));
        btnconsultalimnomar.putClientProperty("substancelaf.buttonShaper", new StandardButtonShaper());
        btnconsultalimnomar.putClientProperty("substancelaf.buttonside", org.jvnet.substance.utils.SubstanceConstants.Side.BOTTOM);
        btnconsultalimnomar.setIcon(new ImageIcon(getClass().getResource("/iconos/locationbar_erased.gif")));
        btnconsultalimnomar.setMnemonic('L');
        btnconsultalimnomar.setText("Limpiar");
        btnconsultalimnomar.setPressedIcon(new ImageIcon(getClass().getResource("/iconos/locationbar_erase.gif")));
        btnconsultalimnomar.setRolloverIcon(new ImageIcon(getClass().getResource("/iconos/locationbar_erase.gif")));
        btnconsultalimnomar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                btnconsultalimnomarActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jPanel60.add(btnconsultalimnomar);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new Insets(0, 0, 3, 0);
        jPanel59.add(jPanel60, gridBagConstraints);
        jPcontcon6.add(jPanel59);
        jFconsultaar.getContentPane().add(jPcontcon6, "First");
        jFmodificarar.setTitle("Modificar art\355culo");
        jFmodificarar.setIconImage((new ImageIcon(getClass().getResource("/imagenes/logopng.png"))).getImage());
        jFmodificarar.setMinimumSize(new Dimension(550, 250));
        GridBagLayout jFmodificararLayout = new GridBagLayout();
        jFmodificararLayout.columnWidths = (new int[] {
            0, 10, 0
        });
        jFmodificararLayout.rowHeights = (new int[] {
            0, 5, 0, 5, 0, 5, 0, 5, 0
        });
        jFmodificarar.getContentPane().setLayout(jFmodificararLayout);
        jLabel5.setText("Descripci\363n");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = 21;
        jFmodificarar.getContentPane().add(jLabel5, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = 21;
        jFmodificarar.getContentPane().add(jTFdescripcionarm, gridBagConstraints);
        jLabel6.setText("Precio venta");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = 21;
        jFmodificarar.getContentPane().add(jLabel6, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = 21;
        jFmodificarar.getContentPane().add(jTFprecioarm, gridBagConstraints);
        jPanel2.setLayout(new GridLayout(1, 2, 10, 0));
        jBguardararm.putClientProperty("substancelaf.buttonShaper", new StandardButtonShaper());
        jBguardararm.putClientProperty("substancelaf.buttonside", org.jvnet.substance.utils.SubstanceConstants.Side.BOTTOM);
        jBguardararm.setIcon(new ImageIcon(getClass().getResource("/iconos/filesaved.gif")));
        jBguardararm.setMnemonic('G');
        jBguardararm.setText("Guardar");
        jBguardararm.setPressedIcon(new ImageIcon(getClass().getResource("/iconos/filesavea.gif")));
        jBguardararm.setRolloverIcon(new ImageIcon(getClass().getResource("/iconos/filesavea.gif")));
        jBguardararm.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jBguardararmActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jPanel2.add(jBguardararm);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        jFmodificarar.getContentPane().add(jPanel2, gridBagConstraints);
        jLabel14.setText("Precio compra");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = 21;
        jFmodificarar.getContentPane().add(jLabel14, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = 21;
        jFmodificarar.getContentPane().add(jTFpreciocompraarm, gridBagConstraints);
        jDlogin.setAlwaysOnTop(true);
        jDlogin.setMinimumSize(new Dimension(260, 260));
        jDlogin.setModal(true);
        jDlogin.setResizable(false);
        jDlogin.setUndecorated(true);
        jDlogin.addWindowListener(new WindowAdapter() {

            public void windowOpened(WindowEvent evt)
            {
                jDloginWindowOpened(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        FlowLayout flowLayout1 = new FlowLayout(1, 5, 30);
        flowLayout1.setAlignOnBaseline(true);
        jDlogin.getContentPane().setLayout(flowLayout1);
        jLabel4.setFont(new Font("Tahoma", 1, 11));
        jLabel4.setText("\277Primera vez?, presione Aceptar");
        jDlogin.getContentPane().add(jLabel4);
        jppassword.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evento)
            {
                verificlavead();
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jppassword.setColumns(20);
        jDlogin.getContentPane().add(jppassword);
        jBaceptarcontra.putClientProperty("substancelaf.buttonShaper", new StandardButtonShaper());
        jBaceptarcontra.putClientProperty("substancelaf.buttonside", org.jvnet.substance.utils.SubstanceConstants.Side.BOTTOM);
        jBaceptarcontra.setIcon(new ImageIcon(getClass().getResource("/iconos/button_ok_1.gif")));
        jBaceptarcontra.setMnemonic('A');
        jBaceptarcontra.setText("Aceptar");
        jBaceptarcontra.setPressedIcon(new ImageIcon(getClass().getResource("/iconos/button_ok.gif")));
        jBaceptarcontra.setRolloverIcon(new ImageIcon(getClass().getResource("/iconos/button_ok.gif")));
        jBaceptarcontra.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jBaceptarcontraActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jDlogin.getContentPane().add(jBaceptarcontra);
        jBcancelarcontra.putClientProperty("substancelaf.buttonShaper", new StandardButtonShaper());
        jBcancelarcontra.putClientProperty("substancelaf.buttonside", org.jvnet.substance.utils.SubstanceConstants.Side.BOTTOM);
        jBcancelarcontra.setIcon(new ImageIcon(getClass().getResource("/iconos/button_cancel_1.gif")));
        jBcancelarcontra.setMnemonic('C');
        jBcancelarcontra.setText("Cancelar");
        jBcancelarcontra.setPressedIcon(new ImageIcon(getClass().getResource("/iconos/button_cancel.gif")));
        jBcancelarcontra.setRolloverIcon(new ImageIcon(getClass().getResource("/iconos/button_cancel.gif")));
        jBcancelarcontra.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jBcancelarcontraActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jDlogin.getContentPane().add(jBcancelarcontra);
        jLabel7.setFont(new Font("Tahoma", 2, 11));
        jLabel7.setText(">>>>> Indicio <<<<<");
        jLabel7.setToolTipText("Clic para mostrar el indicio de la contrase\361a");
        jLabel7.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent evt)
            {
                jLabel7MouseClicked(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jDlogin.getContentPane().add(jLabel7);
        jFactcontraseF1a.setTitle("Capturar o actualizar contrase\361a");
        jFactcontraseF1a.setIconImage((new ImageIcon(getClass().getResource("/imagenes/logopng.png"))).getImage());
        jFactcontraseF1a.setMinimumSize(new Dimension(400, 200));
        GridBagLayout jFactcontraseF1aLayout = new GridBagLayout();
        jFactcontraseF1aLayout.columnWidths = (new int[] {
            0, 10, 0
        });
        jFactcontraseF1aLayout.rowHeights = (new int[] {
            0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 
            0
        });
        jFactcontraseF1a.getContentPane().setLayout(jFactcontraseF1aLayout);
        jLabel9.setText("Nueva contrase\361a");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = 21;
        jFactcontraseF1a.getContentPane().add(jLabel9, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 190;
        gridBagConstraints.anchor = 21;
        jFactcontraseF1a.getContentPane().add(jTFnuevacontra, gridBagConstraints);
        jLabel10.setText("Confirmar nueva contrase\361a");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = 21;
        jFactcontraseF1a.getContentPane().add(jLabel10, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 190;
        gridBagConstraints.anchor = 21;
        jFactcontraseF1a.getContentPane().add(jTFconfirmarnuecon, gridBagConstraints);
        jLabel11.setText("Indicio de la nueva contrase\361a");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = 21;
        jFactcontraseF1a.getContentPane().add(jLabel11, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 190;
        gridBagConstraints.anchor = 21;
        jFactcontraseF1a.getContentPane().add(jTFindicionuecon, gridBagConstraints);
        jPanel3.setLayout(new GridLayout(1, 0, 10, 0));
        jBguardardtsnuecon.putClientProperty("substancelaf.buttonShaper", new StandardButtonShaper());
        jBguardardtsnuecon.putClientProperty("substancelaf.buttonside", org.jvnet.substance.utils.SubstanceConstants.Side.BOTTOM);
        jBguardardtsnuecon.setIcon(new ImageIcon(getClass().getResource("/iconos/filesaved.gif")));
        jBguardardtsnuecon.setMnemonic('G');
        jBguardardtsnuecon.setText("Guardar");
        jBguardardtsnuecon.setPressedIcon(new ImageIcon(getClass().getResource("/iconos/filesavea.gif")));
        jBguardardtsnuecon.setRolloverIcon(new ImageIcon(getClass().getResource("/iconos/filesavea.gif")));
        jBguardardtsnuecon.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jBguardardtsnueconActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jPanel3.add(jBguardardtsnuecon);
        jBlimpiartdtsnuecon.putClientProperty("substancelaf.buttonShaper", new StandardButtonShaper());
        jBlimpiartdtsnuecon.putClientProperty("substancelaf.buttonside", org.jvnet.substance.utils.SubstanceConstants.Side.BOTTOM);
        jBlimpiartdtsnuecon.setIcon(new ImageIcon(getClass().getResource("/iconos/locationbar_erased.gif")));
        jBlimpiartdtsnuecon.setMnemonic('L');
        jBlimpiartdtsnuecon.setText("Limpiar");
        jBlimpiartdtsnuecon.setPressedIcon(new ImageIcon(getClass().getResource("/iconos/locationbar_erase.gif")));
        jBlimpiartdtsnuecon.setRolloverIcon(new ImageIcon(getClass().getResource("/iconos/locationbar_erase.gif")));
        jBlimpiartdtsnuecon.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jBlimpiartdtsnueconActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jPanel3.add(jBlimpiartdtsnuecon);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 3;
        jFactcontraseF1a.getContentPane().add(jPanel3, gridBagConstraints);
        jFaltaventa.setTitle("Alta de ventas");
        jFaltaventa.setIconImage((new ImageIcon(getClass().getResource("/imagenes/logopng.png"))).getImage());
        jFaltaventa.setMinimumSize(new Dimension(880, 500));
        jFaltaventa.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent evt)
            {
                jFaltaventaWindowClosing(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jPanel5.setLayout(new FlowLayout(1, 20, 5));
        jLabel16.setFont(new Font("Tahoma", 1, 11));
        jLabel16.setText("Fecha");
        jPanel5.add(jLabel16);
        jLfechaventa.setFont(new Font("Tahoma", 1, 11));
        jPanel5.add(jLfechaventa);
        jLabel17.setFont(new Font("Tahoma", 1, 11));
        jLabel17.setText("Hora");
        jPanel5.add(jLabel17);
        jLhoraventa.setFont(new Font("Tahoma", 1, 11));
        jPanel5.add(jLhoraventa);
        jLabel18.setFont(new Font("Tahoma", 1, 11));
        jLabel18.setText("N\372mero de venta");
        jPanel5.add(jLabel18);
        jLnumerodeventa.setFont(new Font("Tahoma", 1, 11));
        jPanel5.add(jLnumerodeventa);
        jFaltaventa.getContentPane().add(jPanel5, "North");
        jParticulos.setBorder(BorderFactory.createTitledBorder(null, "Art\355culos", 2, 0));
        jParticulos.setPreferredSize(new Dimension(340, 100));
        jParticulos.setLayout(new BorderLayout());
        jPresultadoc7.setLayout(new BorderLayout());
        tablaconsultaarticulosven.setModel(dtmarticulosv);
        tablaconsultaarticulosven.setAutoResizeMode(0);
        for(int column = 0; column < cabecerasartv.length; column++)
            dtmarticulosv.addColumn(cabecerasartv[column]);

        tablaconsultaarticulosven.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent evt)
            {
                tablaconsultaarticulosvenMouseClicked(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jScrollPane12.setViewportView(tablaconsultaarticulosven);
        TableColumn columnv = null;
        for(int i = 0; i < tablaconsultaarticulosven.getColumnCount(); i++)
        {
            columnv = tablaconsultaarticulosven.getColumnModel().getColumn(i);
            if(i == 0)
            {
                columnv.setPreferredWidth(40);
                continue;
            }
            if(i == 1)
            {
                columnv.setPreferredWidth(200);
                continue;
            }
            if(i == 2)
                columnv.setPreferredWidth(80);
        }

        jPresultadoc7.add(jScrollPane12, "Center");
        btnlimpiartablacart.putClientProperty("substancelaf.buttonShaper", new StandardButtonShaper());
        btnlimpiartablacart.putClientProperty("substancelaf.buttonside", org.jvnet.substance.utils.SubstanceConstants.Side.BOTTOM);
        btnlimpiartablacart1.setIcon(new ImageIcon(getClass().getResource("/iconos/removed.gif")));
        btnlimpiartablacart1.setMnemonic('R');
        btnlimpiartablacart1.setText("Remover");
        btnlimpiartablacart1.setPressedIcon(new ImageIcon(getClass().getResource("/iconos/remove.gif")));
        btnlimpiartablacart1.setRolloverIcon(new ImageIcon(getClass().getResource("/iconos/remove.gif")));
        btnlimpiartablacart1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                btnlimpiartablacart1ActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jPanel62.add(btnlimpiartablacart1);
        jPresultadoc7.add(jPanel62, "South");
        jParticulos.add(jPresultadoc7, "Center");
        jPcontcon7.setBorder(BorderFactory.createTitledBorder(null, "Capture el nombre o presione enter", 2, 0));
        jPcontcon7.setPreferredSize(new Dimension(308, 120));
        jPanel63.setBorder(BorderFactory.createBevelBorder(0));
        jPanel63.setPreferredSize(new Dimension(300, 90));
        GridBagLayout jPanel63Layout = new GridBagLayout();
        jPanel63Layout.columnWidths = (new int[] {
            0
        });
        jPanel63Layout.rowHeights = (new int[] {
            0, 5, 0, 5, 0
        });
        jPanel63.setLayout(jPanel63Layout);
        jtfnombreartconv.addKeyListener(new KeyAdapter() {

            public void keyTyped(KeyEvent evt)
            {
                jtfnombreartconvKeyTyped(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.anchor = 21;
        gridBagConstraints.insets = new Insets(3, 0, 0, 5);
        jPanel63.add(jtfnombreartconv, gridBagConstraints);
        jPanel64.setLayout(new GridLayout(1, 2, 2, 0));
        btnconsultalimnomar.putClientProperty("substancelaf.buttonShaper", new StandardButtonShaper());
        btnconsultalimnomar.putClientProperty("substancelaf.buttonside", org.jvnet.substance.utils.SubstanceConstants.Side.BOTTOM);
        btnconsultalimnomar1.setIcon(new ImageIcon(getClass().getResource("/iconos/locationbar_erased.gif")));
        btnconsultalimnomar1.setMnemonic('L');
        btnconsultalimnomar1.setText("Limpiar");
        btnconsultalimnomar1.setPressedIcon(new ImageIcon(getClass().getResource("/iconos/locationbar_erase.gif")));
        btnconsultalimnomar1.setRolloverIcon(new ImageIcon(getClass().getResource("/iconos/locationbar_erase.gif")));
        btnconsultalimnomar1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                btnconsultalimnomar1ActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jPanel64.add(btnconsultalimnomar1);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new Insets(0, 0, 3, 0);
        jPanel63.add(jPanel64, gridBagConstraints);
        jPcontcon7.add(jPanel63);
        jParticulos.add(jPcontcon7, "First");
        jFaltaventa.getContentPane().add(jParticulos, "West");
        jParticulosventa.setBorder(BorderFactory.createTitledBorder(null, "Venta", 2, 0));
        jParticulosventa.setPreferredSize(new Dimension(440, 100));
        jParticulosventa.setLayout(new BorderLayout());
        tablaconsultaarticulosven1.setModel(dtmarticulosv1);
        tablaconsultaarticulosven1.setAutoResizeMode(0);
        for(int column = 0; column < cabecerasartv1.length; column++)
            dtmarticulosv1.addColumn(cabecerasartv1[column]);

        tablaconsultaarticulosven1.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent evt)
            {
                tablaconsultaarticulosven1MouseClicked(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jScrollPane13.setViewportView(tablaconsultaarticulosven1);
        TableColumn columnv1 = null;
        for(int i = 0; i < tablaconsultaarticulosven1.getColumnCount(); i++)
        {
            columnv1 = tablaconsultaarticulosven1.getColumnModel().getColumn(i);
            if(i == 0)
            {
                columnv1.setPreferredWidth(40);
                continue;
            }
            if(i == 1)
            {
                columnv1.setPreferredWidth(200);
                continue;
            }
            if(i == 2)
            {
                columnv1.setPreferredWidth(60);
                continue;
            }
            if(i == 3)
            {
                columnv1.setPreferredWidth(60);
                continue;
            }
            if(i == 4)
                columnv1.setPreferredWidth(60);
        }

        jParticulosventa.add(jScrollPane13, "Center");
        GridBagLayout jPanel4Layout = new GridBagLayout();
        jPanel4Layout.columnWidths = (new int[] {
            0, 10, 0, 10, 0
        });
        jPanel4Layout.rowHeights = (new int[] {
            0, 5, 0
        });
        jPanel4.setLayout(jPanel4Layout);
        jButton2.setIcon(new ImageIcon(getClass().getResource("/iconos/filesavea.gif")));
        jButton2.setMnemonic('G');
        jButton2.setText("Guardar");
        jButton2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jButton2ActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel4.add(jButton2, gridBagConstraints);
        jButton9.setIcon(new ImageIcon(getClass().getResource("/iconos/frameprint.gif")));
        jButton9.setMnemonic('V');
        jButton9.setText("Vista preliminar");
        jButton9.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jButton9ActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        jPanel4.add(jButton9, gridBagConstraints);
        jButton4.setIcon(new ImageIcon(getClass().getResource("/iconos/remove.gif")));
        jButton4.setMnemonic('e');
        jButton4.setText("Remover");
        jButton4.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jButton4ActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        jPanel4.add(jButton4, gridBagConstraints);
        jLabel15.setFont(new Font("Tahoma", 1, 12));
        jLabel15.setText("Gran total");
        jPanel6.add(jLabel15);
        jLtotalventa.setFont(new Font("Tahoma", 1, 20));
        jPanel6.add(jLtotalventa);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        jPanel4.add(jPanel6, gridBagConstraints);
        jParticulosventa.add(jPanel4, "South");
        jFaltaventa.getContentPane().add(jParticulosventa, "East");
        GridBagLayout jPpasarLayout = new GridBagLayout();
        jPpasarLayout.columnWidths = (new int[] {
            0
        });
        jPpasarLayout.rowHeights = (new int[] {
            0, 5, 0, 5, 0, 5, 0, 5, 0
        });
        jPpasar.setLayout(jPpasarLayout);
        jBagregaraventa.setMnemonic('>');
        jBagregaraventa.setText(">>");
        jBagregaraventa.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jBagregaraventaActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPpasar.add(jBagregaraventa, gridBagConstraints);
        jBquitardeventa.setMnemonic('<');
        jBquitardeventa.setText("<<");
        jBquitardeventa.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jBquitardeventaActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        jPpasar.add(jBquitardeventa, gridBagConstraints);
        jPanel7.setBorder(BorderFactory.createTitledBorder(null, "Cantidad", 2, 0));
        jPanel7.setLayout(new GridBagLayout());
        jTFcantidadarticulos.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evento)
            {
                verificarcantart();
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jTFcantidadarticulos.setHorizontalAlignment(0);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.ipadx = 48;
        jPanel7.add(jTFcantidadarticulos, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPpasar.add(jPanel7, gridBagConstraints);
        jFaltaventa.getContentPane().add(jPpasar, "Center");
        jPextras.setBorder(BorderFactory.createTitledBorder(null, "Art\355culos extras", 2, 0));
        jPextras.setLayout(new FlowLayout(1, 10, 5));
        jLabel19.setText("Descripci\363n");
        jPextras.add(jLabel19);
        jTFdescripcionarex.setColumns(18);
        jPextras.add(jTFdescripcionarex);
        jLabel20.setText("Precio");
        jPextras.add(jLabel20);
        jTFprecioarex.setColumns(8);
        jPextras.add(jTFprecioarex);
        jLabel21.setText("Cantidad");
        jPextras.add(jLabel21);
        jTFcantidadarex.setColumns(5);
        jPextras.add(jTFcantidadarex);
        jButton5.setIcon(new ImageIcon(getClass().getResource("/iconos/attach.gif")));
        jButton5.setMnemonic('A');
        jButton5.setText("Agregar");
        jButton5.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jButton5ActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jPextras.add(jButton5);
        jButton6.setIcon(new ImageIcon(getClass().getResource("/iconos/locationbar_erase.gif")));
        jButton6.setMnemonic('m');
        jButton6.setText("Limpiar");
        jButton6.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jButton6ActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jPextras.add(jButton6);
        jFaltaventa.getContentPane().add(jPextras, "South");
        jFconsultaventas.setTitle("Consultar venta / Eliminar venta");
        jFconsultaventas.setIconImage((new ImageIcon(getClass().getResource("/imagenes/logopng.png"))).getImage());
        jFconsultaventas.setMinimumSize(new Dimension(800, 500));
        jPresultadoc8.setBorder(BorderFactory.createTitledBorder(new SoftBevelBorder(1), "Clic derecho sobre la venta que desee eliminar o imprimir.", 2, 0));
        jPresultadoc8.setLayout(new BorderLayout());
        tablaconsultaventas.setModel(dtmconven);
        tablaconsultaventas.setAutoResizeMode(0);
        for(int column = 0; column < cabecerasconven.length; column++)
            dtmconven.addColumn(cabecerasconven[column]);

        tablaconsultaventas.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent evt)
            {
                tablaconsultaventasMouseClicked(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jScrollPane14.setViewportView(tablaconsultaventas);
        TableColumn columcv = null;
        for(int i = 0; i < tablaconsultaventas.getColumnCount(); i++)
        {
            columcv = tablaconsultaventas.getColumnModel().getColumn(i);
            if(i == 0)
            {
                columcv.setPreferredWidth(70);
                continue;
            }
            if(i == 1)
            {
                columcv.setPreferredWidth(70);
                continue;
            }
            if(i == 2)
            {
                columcv.setPreferredWidth(70);
                continue;
            }
            if(i == 3)
            {
                columcv.setPreferredWidth(70);
                continue;
            }
            if(i == 4)
            {
                columcv.setPreferredWidth(250);
                continue;
            }
            if(i == 5)
            {
                columcv.setPreferredWidth(70);
                continue;
            }
            if(i == 6)
            {
                columcv.setPreferredWidth(70);
                continue;
            }
            if(i == 7)
                columcv.setPreferredWidth(70);
        }

        jPresultadoc8.add(jScrollPane14, "Center");
        btnlimpiartablacart.putClientProperty("substancelaf.buttonShaper", new StandardButtonShaper());
        btnlimpiartablacart.putClientProperty("substancelaf.buttonside", org.jvnet.substance.utils.SubstanceConstants.Side.BOTTOM);
        btnlimpiartablacart2.setIcon(new ImageIcon(getClass().getResource("/iconos/removed.gif")));
        btnlimpiartablacart2.setMnemonic('R');
        btnlimpiartablacart2.setText("Remover");
        btnlimpiartablacart2.setPressedIcon(new ImageIcon(getClass().getResource("/iconos/remove.gif")));
        btnlimpiartablacart2.setRolloverIcon(new ImageIcon(getClass().getResource("/iconos/remove.gif")));
        btnlimpiartablacart2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                btnlimpiartablacart2ActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jPanel65.add(btnlimpiartablacart2);
        jPresultadoc8.add(jPanel65, "South");
        jFconsultaventas.getContentPane().add(jPresultadoc8, "Center");
        jPcontcon8.setBorder(BorderFactory.createTitledBorder(null, "Seleccione la fecha", 2, 0));
        jPcontcon8.setMinimumSize(new Dimension(111, 50));
        jPcontcon8.setPreferredSize(new Dimension(308, 100));
        GridBagLayout jPcontcon8Layout = new GridBagLayout();
        jPcontcon8Layout.columnWidths = (new int[] {
            0
        });
        jPcontcon8Layout.rowHeights = (new int[] {
            0, 5, 0, 5, 0
        });
        jPcontcon8.setLayout(jPcontcon8Layout);
        jDCfechaventa.setDateFormatString("yyyy-MM-dd");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPcontcon8.add(jDCfechaventa, gridBagConstraints);
        btnconsultalimnomar.putClientProperty("substancelaf.buttonShaper", new StandardButtonShaper());
        btnconsultalimnomar.putClientProperty("substancelaf.buttonside", org.jvnet.substance.utils.SubstanceConstants.Side.BOTTOM);
        btnconsultaventas.setIcon(new ImageIcon(getClass().getResource("/iconos/search.gif")));
        btnconsultaventas.setMnemonic('C');
        btnconsultaventas.setText("Consultar");
        btnconsultaventas.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                btnconsultaventasActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPcontcon8.add(btnconsultaventas, gridBagConstraints);
        jFconsultaventas.getContentPane().add(jPcontcon8, "First");
        jFnombreempresa.setTitle("Nombre de la empresa");
        jFnombreempresa.setIconImage((new ImageIcon(getClass().getResource("/imagenes/logopng.png"))).getImage());
        jFnombreempresa.setMinimumSize(new Dimension(300, 150));
        GridBagLayout jFnombreempresaLayout = new GridBagLayout();
        jFnombreempresaLayout.columnWidths = (new int[] {
            0, 10, 0
        });
        jFnombreempresaLayout.rowHeights = (new int[] {
            0, 5, 0, 5, 0
        });
        jFnombreempresa.getContentPane().setLayout(jFnombreempresaLayout);
        jLabel12.setText("Nombre");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jFnombreempresa.getContentPane().add(jLabel12, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 190;
        gridBagConstraints.anchor = 21;
        jFnombreempresa.getContentPane().add(jTnombreempresa, gridBagConstraints);
        jPanel8.setLayout(new GridLayout(1, 2, 5, 0));
        jButton3.setText("Guardar");
        jButton3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jButton3ActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jPanel8.add(jButton3);
        jButton7.setText("Limpiar");
        jButton7.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jButton7ActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jPanel8.add(jButton7);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        jFnombreempresa.getContentPane().add(jPanel8, gridBagConstraints);
        jFvistapreliminarv.setTitle("Vista preliminar");
        jFvistapreliminarv.setIconImage((new ImageIcon(getClass().getResource("/imagenes/logopng.png"))).getImage());
        jFvistapreliminarv.setMinimumSize(new Dimension(560, 500));
        tablavistapreliminarventa.setModel(dtmvistaprev);
        tablavistapreliminarventa.setAutoResizeMode(0);
        for(int column = 0; column < cabecerasvistaprev.length; column++)
            dtmvistaprev.addColumn(cabecerasvistaprev[column]);

        tablavistapreliminarventa.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent evt)
            {
                tablavistapreliminarventaMouseClicked(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jScrollPane15.setViewportView(tablavistapreliminarventa);
        TableColumn columnv2 = null;
        for(int i = 0; i < tablavistapreliminarventa.getColumnCount(); i++)
        {
            columnv2 = tablavistapreliminarventa.getColumnModel().getColumn(i);
            if(i == 0)
            {
                columnv2.setPreferredWidth(70);
                continue;
            }
            if(i == 1)
            {
                columnv2.setPreferredWidth(240);
                continue;
            }
            if(i == 2)
            {
                columnv2.setPreferredWidth(80);
                continue;
            }
            if(i == 3)
            {
                columnv2.setPreferredWidth(80);
                continue;
            }
            if(i == 4)
                columnv2.setPreferredWidth(80);
        }

        jFvistapreliminarv.getContentPane().add(jScrollPane15, "Center");
        jButton8.setIcon(new ImageIcon(getClass().getResource("/iconos/fileprint.gif")));
        jButton8.setMnemonic('I');
        jButton8.setText("Imprimir");
        jButton8.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jButton8ActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jPanel9.add(jButton8);
        jFvistapreliminarv.getContentPane().add(jPanel9, "South");
        jFvistapreliminarv1.setTitle("Vista preliminar");
        jFvistapreliminarv1.setIconImage((new ImageIcon(getClass().getResource("/imagenes/logopng.png"))).getImage());
        jFvistapreliminarv1.setMinimumSize(new Dimension(560, 500));
        tablavistapreliminarventa1.setModel(dtmvistaprev1);
        tablavistapreliminarventa1.setAutoResizeMode(0);
        for(int column = 0; column < cabecerasvistaprev1.length; column++)
            dtmvistaprev1.addColumn(cabecerasvistaprev1[column]);

        tablavistapreliminarventa1.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent evt)
            {
                tablavistapreliminarventa1MouseClicked(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jScrollPane16.setViewportView(tablavistapreliminarventa1);
        TableColumn columnv21 = null;
        for(int i = 0; i < tablavistapreliminarventa1.getColumnCount(); i++)
        {
            columnv21 = tablavistapreliminarventa1.getColumnModel().getColumn(i);
            if(i == 0)
            {
                columnv21.setPreferredWidth(70);
                continue;
            }
            if(i == 1)
            {
                columnv21.setPreferredWidth(240);
                continue;
            }
            if(i == 2)
            {
                columnv21.setPreferredWidth(80);
                continue;
            }
            if(i == 3)
            {
                columnv21.setPreferredWidth(80);
                continue;
            }
            if(i == 4)
                columnv21.setPreferredWidth(80);
        }

        jFvistapreliminarv1.getContentPane().add(jScrollPane16, "Center");
        jButton10.setIcon(new ImageIcon(getClass().getResource("/iconos/fileprint.gif")));
        jButton10.setMnemonic('I');
        jButton10.setText("Imprimir");
        jButton10.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jButton10ActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jPanel10.add(jButton10);
        jFvistapreliminarv1.getContentPane().add(jPanel10, "South");
        setDefaultCloseOperation(3);
        setIconImage((new ImageIcon(getClass().getResource("/imagenes/logopng.png"))).getImage());
        jLlogosisconpre.setHorizontalAlignment(0);
        jLlogosisconpre.setIcon(new ImageIcon(getClass().getResource("/imagenes/logopng.png")));
        getContentPane().add(jLlogosisconpre, "Center");
        jMenu1.setIcon(new ImageIcon(getClass().getResource("/iconos/User3.gif")));
        jMenu1.setMnemonic('A');
        jMenu1.setText("Administrador");
        jMIcontraseF1a.setIcon(new ImageIcon(getClass().getResource("/iconos/key.png")));
        jMIcontraseF1a.setMnemonic('o');
        jMIcontraseF1a.setText("Contrase\361a");
        jMIcontraseF1a.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jMIcontraseF1aActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jMenu1.add(jMIcontraseF1a);
        jMenu5.setIcon(new ImageIcon(getClass().getResource("/iconos/gohome.gif")));
        jMenu5.setMnemonic('E');
        jMenu5.setText("Empresa");
        jMenuItem4.setIcon(new ImageIcon(getClass().getResource("/iconos/tux.gif")));
        jMenuItem4.setMnemonic('N');
        jMenuItem4.setText("Nombre");
        jMenuItem4.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jMenuItem4ActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jMenu5.add(jMenuItem4);
        jMenu1.add(jMenu5);
        jMenuBar1.add(jMenu1);
        jMarticulos.setIcon(new ImageIcon(getClass().getResource("/iconos/edittrash.gif")));
        jMarticulos.setMnemonic('r');
        jMarticulos.setText("Art\355culos");
        jMIaltaar.setIcon(new ImageIcon(getClass().getResource("/iconos/attach.gif")));
        jMIaltaar.setMnemonic('l');
        jMIaltaar.setText("Alta");
        jMIaltaar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jMIaltaarActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jMarticulos.add(jMIaltaar);
        jMIconsultaar.setIcon(new ImageIcon(getClass().getResource("/iconos/search.gif")));
        jMIconsultaar.setMnemonic('C');
        jMIconsultaar.setText("Cons/Modi/Elim");
        jMIconsultaar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jMIconsultaarActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jMarticulos.add(jMIconsultaar);
        jMenuBar1.add(jMarticulos);
        jMenu2.setIcon(new ImageIcon(getClass().getResource("/iconos/Cash_register_Icon_16.png")));
        jMenu2.setMnemonic('V');
        jMenu2.setText("Venta");
        jMenuItem1.setIcon(new ImageIcon(getClass().getResource("/iconos/attach.gif")));
        jMenuItem1.setMnemonic('t');
        jMenuItem1.setText("Alta");
        jMenuItem1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jMenuItem1ActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jMenu2.add(jMenuItem1);
        jMenuItem2.setIcon(new ImageIcon(getClass().getResource("/iconos/search.gif")));
        jMenuItem2.setMnemonic('o');
        jMenuItem2.setText("Cons/Elim/Impr");
        jMenuItem2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jMenuItem2ActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jMenu2.add(jMenuItem2);
        jMenuBar1.add(jMenu2);
        jMenu3.setIcon(new ImageIcon(getClass().getResource("/iconos/configure.gif")));
        jMenu3.setMnemonic('M');
        jMenu3.setText("Mantenimiento");
        jMenu4.setIcon(new ImageIcon(getClass().getResource("/iconos/remove.gif")));
        jMenu4.setMnemonic('E');
        jMenu4.setText("Eliminar");
        jMenuItem3.setIcon(new ImageIcon(getClass().getResource("/iconos/Cash_register_Icon_16.png")));
        jMenuItem3.setMnemonic('n');
        jMenuItem3.setText("Ventas");
        jMenuItem3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jMenuItem3ActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jMenu4.add(jMenuItem3);
        jMenu3.add(jMenu4);
        jMenuBar1.add(jMenu3);
        jMayuda.setIcon(new ImageIcon(getClass().getResource("/iconos/help.gif")));
        jMayuda.setMnemonic('y');
        jMayuda.setText("Ayuda");
        jMIacercade.setIcon(new ImageIcon(getClass().getResource("/iconos/about.gif")));
        jMIacercade.setMnemonic('e');
        jMIacercade.setText("Acerca de");
        jMIacercade.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jMIacercadeActionPerformed(evt);
            }

            final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
        }
);
        jMayuda.add(jMIacercade);
        jMenuBar1.add(jMayuda);
        setJMenuBar(jMenuBar1);
        pack();
    }

    private void jMIaltaarActionPerformed(ActionEvent evt)
    {
        jFaltaar.setLocationRelativeTo(null);
        jFaltaar.setVisible(true);
    }

    private void jBlimpiardtsarActionPerformed(ActionEvent evt)
    {
        limpiardtsaltaar();
    }

    public void limpiardtsaltaar()
    {
        jTFclavear.setText("");
        jTFdescripcionar.setText("");
        jTFpreciocompraar.setText("");
        jTFprecioar.setText("");
    }

    private void jBguardararActionPerformed(ActionEvent evt)
    {
        verficardtsaltaar();
    }

    private void jMIconsultaarActionPerformed(ActionEvent evt)
    {
        jFconsultaar.setLocationRelativeTo(null);
        jFconsultaar.setVisible(true);
    }

    private void tablaconsultaarticulosMouseClicked(MouseEvent evt)
    {
        try
        {
            if(evt.getButton() == 3)
            {
                clavearticulo = tablaconsultaarticulos.getValueAt(tablaconsultaarticulos.getSelectedRow(), 0);
                menuContextual.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }
        catch(ArrayIndexOutOfBoundsException evento)
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Por favor seleccione un registro", "Error", 0);
        }
    }

    private void btnlimpiartablacartActionPerformed(ActionEvent evt)
    {
        limpiartblconar();
        jtfnombreartcon.grabFocus();
    }

    public void limpiartblconar()
    {
        for(int numfilas = dtmarticulos.getRowCount(); numfilas != 0; numfilas--)
            dtmarticulos.removeRow(0);

    }

    public void limpiartblvistaprev1()
    {
        for(int numfilas = dtmvistaprev1.getRowCount(); numfilas != 0; numfilas--)
            dtmvistaprev1.removeRow(0);

    }

    public void limpiartblconventas()
    {
        for(int numfilas = dtmconven.getRowCount(); numfilas != 0; numfilas--)
            dtmconven.removeRow(0);

    }

    public void limpiartblventa()
    {
        for(int numfilas = dtmarticulosv.getRowCount(); numfilas != 0; numfilas--)
            dtmarticulosv.removeRow(0);

    }

    public void limpiartblconarv()
    {
        for(int numfilas = dtmarticulosv.getRowCount(); numfilas != 0; numfilas--)
            dtmarticulosv.removeRow(0);

    }

    public void limpiartblvistap()
    {
        for(int numfilas = dtmvistaprev.getRowCount(); numfilas != 0; numfilas--)
            dtmvistaprev.removeRow(0);

    }

    public void limpiartblconarv1()
    {
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(Locale.US);
        for(int numfilas = dtmarticulosv1.getRowCount(); numfilas != 0; numfilas--)
            dtmarticulosv1.removeRow(0);

        grantotal = 0.0F;
        jLtotalventa.setText(String.valueOf(formatoMoneda.format(grantotal)));
    }

    private void jtfnombreartconKeyTyped(KeyEvent evt)
    {
        obtenerdcart();
    }

    public void obtenerdcart()
    {
        DecimalFormat dosDigitos = new DecimalFormat("0.00");
        try
        {
            limpiartblconar();
            String nombaconsul = jtfnombreartcon.getText();
            try
            {
                starticulolike.setString(1, (new StringBuilder()).append(nombaconsul).append("%").toString());
                starticulolike.setString(2, (new StringBuilder()).append("%").append(nombaconsul).append("%").toString());
                starticulolike.setString(3, (new StringBuilder()).append("%").append(nombaconsul).toString());
                ResultSet res = starticulolike.executeQuery();
                int nColumnas = res.getMetaData().getColumnCount();
                for(; res.next(); dtmarticulos.addRow(dataart))
                {
                    for(int i = 1; i <= nColumnas; i++)
                    {
                        if(i == 3)
                        {
                            dataart[i - 1] = dosDigitos.format(res.getFloat(i));
                            continue;
                        }
                        if(i == 4)
                            dataart[i - 1] = dosDigitos.format(res.getFloat(i));
                        else
                            dataart[i - 1] = res.getString(i);
                    }

                }

            }
            catch(SQLException ex)
            {
                int codigoerror = ex.getErrorCode();
                if(codigoerror == 0)
                {
                    initDB();
                    obtenerdcart();
                } else
                {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        }
        catch(NullPointerException ex)
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void mostrardamodar(String clavemodi)
    {
        DecimalFormat dosDigitos = new DecimalFormat("0.00");
        try
        {
            stcondtsarticulomod.setString(1, clavemodi);
            ResultSet res = null;
            for(res = stcondtsarticulomod.executeQuery(); res.next();)
            {
                int nColumnas = res.getMetaData().getColumnCount();
                int i = 1;
                while(i <= nColumnas) 
                {
                    if(i == 1)
                        jTFdescripcionarm.setText(res.getString(i));
                    else
                    if(i == 2)
                        jTFpreciocompraarm.setText(dosDigitos.format(res.getFloat(i)));
                    else
                    if(i == 3)
                        jTFprecioarm.setText(dosDigitos.format(res.getFloat(i)));
                    i++;
                }
            }

        }
        catch(SQLException e)
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        jFmodificarar.setLocationRelativeTo(null);
        jFmodificarar.setVisible(true);
    }

    private void btnconsultalimnomarActionPerformed(ActionEvent evt)
    {
        jtfnombreartcon.setText("");
        jtfnombreartcon.grabFocus();
    }

    private void jBguardararmActionPerformed(ActionEvent evt)
    {
        validardtsarmod();
    }

    public void validardtsarmod()
    {
        if(jTFdescripcionarm.getText().equals("") || jTFpreciocompraarm.getText().equals("") || jTFprecioarm.getText().equals(""))
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Por favor llene todos los campos", "Error", 0);
        } else
        {
            obtenerdtsmodar();
        }
    }

    private void jBcancelarcontraActionPerformed(ActionEvent evt)
    {
        System.exit(0);
    }

    private void jDloginWindowOpened(WindowEvent evt)
    {
        mipanel p = new mipanel();
        jDlogin.add(p, "Center");
        jDlogin.repaint();
        p.repaint();
    }

    private void jBaceptarcontraActionPerformed(ActionEvent evt)
    {
        verificlavead();
    }

    private void jMIcontraseF1aActionPerformed(ActionEvent evt)
    {
        jFactcontraseF1a.setLocationRelativeTo(null);
        jFactcontraseF1a.setVisible(true);
    }

    private void jBlimpiartdtsnueconActionPerformed(ActionEvent evt)
    {
        limpiardtsnuecontra();
    }

    private void jBguardardtsnueconActionPerformed(ActionEvent evt)
    {
        validardtscontra();
    }

    private void jLabel7MouseClicked(MouseEvent evt)
    {
        consultarindicio();
    }

    public void consultarindicio()
    {
        try
        {
            ResultSet res = stconindicio.executeQuery();
            if(res.next())
                JOptionPane.showMessageDialog(jDlogin, (new StringBuilder()).append("Indicio: ").append(res.getObject("indicio")).toString());
        }
        catch(SQLException ex)
        {
            int codigoerror = ex.getErrorCode();
            if(codigoerror == 0)
            {
                initDB();
                consultarindicio();
            } else
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    private void jMIacercadeActionPerformed(ActionEvent evt)
    {
        JOptionPane.showMessageDialog(null, "Esta aplicaci\363n fue desarrollada por el Ing. Isaac P\351rez Brice\361o para solucionar otra necesidad que \nse presenta dentro del negocio Techno-Cyber.\n\nLa presente herramienta permite consultar la descripci\363n y el precio de \u201Cx \u201Cart\355culo o producto con \nsolo escribir una palabra o frase en el buscador, ademas de que se le agrego la opci\363n de punto de venta.\n\nSe proh\355be la reproducci\363n parcial o total de este programa, DI NO A LA PIRATERIA.\n\nCotizaci\363n y ventas al correo: techno-cyber@hotmail.com", "Mensaje", 1);
    }

    private void jBExportarActionPerformed(ActionEvent evt)
    {
        JFileChooser seleccionar_archivo;
        seleccionar_archivo = new JFileChooser();
        int opcion = seleccionar_archivo.showSaveDialog(null);
        seleccionar_archivo;
        if(opcion != 0)
            break MISSING_BLOCK_LABEL_261;
        try
        {
            OutputStream texto_salida = new FileOutputStream(seleccionar_archivo.getSelectedFile().toString().concat(".pdf"));
            Document doc = new Document();
            PdfWriter.getInstance(doc, texto_salida);
            doc.open();
            doc.add(new Phrase("ART\315CULOS"));
            PdfPTable table = new PdfPTable(cabecerasart.length);
            for(int j = 0; j < cabecerasart.length; j++)
            {
                PdfPCell cabecera = new PdfPCell(new Phrase(cabecerasart[j]));
                table.addCell(cabecera);
            }

            int totalfilas = tablaconsultaarticulos.getRowCount();
            for(int fila = 0; fila < totalfilas; fila++)
            {
                for(int columna = 0; columna < cabecerasart.length; columna++)
                {
                    String textocelda = (String)tablaconsultaarticulos.getValueAt(fila, columna);
                    PdfPCell cell = new PdfPCell(new Phrase(textocelda));
                    table.addCell(cell);
                }

            }

            doc.add(table);
            doc.close();
            texto_salida.close();
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void jButton1ActionPerformed(ActionEvent evt)
    {
        String title = "ART\315CULOS";
        imprimirreporteena(title, tablaconsultaarticulos);
    }

    private void tablaconsultaarticulosvenMouseClicked(MouseEvent evt)
    {
        try
        {
            if(evt.getButton() == 1)
                jTFcantidadarticulos.grabFocus();
        }
        catch(ArrayIndexOutOfBoundsException evento)
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Por favor seleccione un registro", "Error", 0);
        }
    }

    private void btnlimpiartablacart1ActionPerformed(ActionEvent evt)
    {
        limpiartblventa();
        jtfnombreartconv.grabFocus();
    }

    private void jtfnombreartconvKeyTyped(KeyEvent evt)
    {
        obtenerdcartv();
    }

    public void obtenerdcartv()
    {
        DecimalFormat dosDigitos = new DecimalFormat("0.00");
        try
        {
            limpiartblconarv();
            String nombaconsul = jtfnombreartconv.getText();
            try
            {
                starticulolikev.setString(1, (new StringBuilder()).append(nombaconsul).append("%").toString());
                starticulolikev.setString(2, (new StringBuilder()).append("%").append(nombaconsul).append("%").toString());
                starticulolikev.setString(3, (new StringBuilder()).append("%").append(nombaconsul).toString());
                ResultSet res = starticulolikev.executeQuery();
                int nColumnas = res.getMetaData().getColumnCount();
                for(; res.next(); dtmarticulosv.addRow(dataartv))
                {
                    for(int i = 1; i <= nColumnas; i++)
                        if(i == 3)
                            dataartv[i - 1] = dosDigitos.format(res.getFloat(i));
                        else
                            dataartv[i - 1] = res.getString(i);

                }

            }
            catch(SQLException ex)
            {
                int codigoerror = ex.getErrorCode();
                if(codigoerror == 0)
                {
                    initDB();
                    obtenerdcartv();
                } else
                {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        }
        catch(NullPointerException ex)
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void btnconsultalimnomar1ActionPerformed(ActionEvent evt)
    {
        jtfnombreartconv.setText("");
        jtfnombreartconv.grabFocus();
    }

    private void jMenuItem1ActionPerformed(ActionEvent evt)
    {
        jFaltaventa.setLocationRelativeTo(null);
        jFaltaventa.setVisible(true);
        colocardatosdeventa();
    }

    public void colocardatosdeventa()
    {
        try
        {
            NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(Locale.US);
            jLtotalventa.setText(String.valueOf(formatoMoneda.format(grantotal)));
            int numeroventa = 0;
            String fecha = formatos.obtenerfecha();
            String hora = formatos.obtenerhora();
            jLfechaventa.setText(fecha);
            jLhoraventa.setText(hora);
            stnumerodeventa.setString(1, fecha);
            ResultSet res = null;
            res = stnumerodeventa.executeQuery();
            if(res.next())
                numeroventa = res.getInt("max(numeroventav)") + 1;
            else
                numeroventa = 1;
            jLnumerodeventa.setText(String.valueOf(numeroventa));
        }
        catch(SQLException e)
        {
            int codigoerror = e.getErrorCode();
            if(codigoerror == 0)
            {
                initDB();
                colocardatosdeventa();
            } else
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        catch(NumberFormatException ex)
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void tablaconsultaarticulosven1MouseClicked(MouseEvent evt)
    {
        try
        {
            if(evt.getButton() == 1)
                jBquitardeventa.grabFocus();
        }
        catch(ArrayIndexOutOfBoundsException evento)
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Por favor seleccione un registro", "Error", 0);
        }
    }

    private void jBagregaraventaActionPerformed(ActionEvent evt)
    {
        verificarcantart();
    }

    public void verificarcantart()
    {
        int cantidadar = 0;
        if(jTFcantidadarticulos.getText().equals(""))
        {
            jTFcantidadarticulos.setText("");
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Por favor capture la cantidad de art\355culos", "Error", 0);
        } else
        if(!jTFcantidadarticulos.getText().matches("\\d+"))
        {
            jTFcantidadarticulos.setText("");
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "La cantidad de art\355culos debe ser de tipo num\351rico", "Error", 0);
        } else
        {
            cantidadar = Integer.parseInt(jTFcantidadarticulos.getText());
            pasararticulo(cantidadar);
        }
    }

    public void pasararticulo(int cantidad)
    {
        boolean error = false;
        try
        {
            float total = 0.0F;
            NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(Locale.US);
            DecimalFormat dosDigitos = new DecimalFormat("0.00");
            Object clavearticulov = tablaconsultaarticulosven.getValueAt(tablaconsultaarticulosven.getSelectedRow(), 0);
            Object descripcionarticulov = tablaconsultaarticulosven.getValueAt(tablaconsultaarticulosven.getSelectedRow(), 1);
            Object precioarticulov = tablaconsultaarticulosven.getValueAt(tablaconsultaarticulosven.getSelectedRow(), 2);
            float precioar = Float.parseFloat(precioarticulov.toString());
            total = precioar * (float)cantidad;
            for(int i = 1; i <= 5; i++)
            {
                if(i == 1)
                {
                    dataartv1[i - 1] = clavearticulov;
                    continue;
                }
                if(i == 2)
                {
                    dataartv1[i - 1] = descripcionarticulov;
                    continue;
                }
                if(i == 3)
                {
                    dataartv1[i - 1] = dosDigitos.format(Float.parseFloat(precioarticulov.toString()));
                    continue;
                }
                if(i == 4)
                {
                    dataartv1[i - 1] = Integer.valueOf(cantidad);
                    continue;
                }
                if(i == 5)
                    dataartv1[i - 1] = dosDigitos.format(total);
            }

            dtmarticulosv1.addRow(dataartv1);
            SwingUtilities.invokeLater(new Runnable() {

                public void run()
                {
                    JScrollBar vbar = jScrollPane13.getVerticalScrollBar();
                    vbar.setValue(vbar.getMaximum());
                }

                final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
            }
);
            grantotal += total;
            jLtotalventa.setText(String.valueOf(formatoMoneda.format(grantotal)));
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Por favor seleccione un registro de la tabla art\355culos", "Error", 0);
        }
        catch(NumberFormatException ex)
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        if(!error)
            jTFcantidadarticulos.setText("");
    }

    public void pasararticuloex(int cantidad)
    {
        boolean error = false;
        try
        {
            float total = 0.0F;
            NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(Locale.US);
            DecimalFormat dosDigitos = new DecimalFormat("0.00");
            String descripcionarex = jTFdescripcionarex.getText();
            float precioarex = Float.parseFloat(jTFprecioarex.getText());
            int cantidadarex = Integer.parseInt(jTFcantidadarex.getText());
            total = precioarex * (float)cantidadarex;
            for(int i = 1; i <= 5; i++)
            {
                if(i == 1)
                {
                    dataartv1[i - 1] = Integer.valueOf(0);
                    continue;
                }
                if(i == 2)
                {
                    dataartv1[i - 1] = descripcionarex;
                    continue;
                }
                if(i == 3)
                {
                    dataartv1[i - 1] = dosDigitos.format(precioarex);
                    continue;
                }
                if(i == 4)
                {
                    dataartv1[i - 1] = Integer.valueOf(cantidad);
                    continue;
                }
                if(i == 5)
                    dataartv1[i - 1] = dosDigitos.format(total);
            }

            dtmarticulosv1.addRow(dataartv1);
            SwingUtilities.invokeLater(new Runnable() {

                public void run()
                {
                    JScrollBar vbar = jScrollPane13.getVerticalScrollBar();
                    vbar.setValue(vbar.getMaximum());
                }

                final interfazsisconpre this$0;

            
            {
                this$0 = interfazsisconpre.this;
                super();
            }
            }
);
            grantotal += total;
            jLtotalventa.setText(String.valueOf(formatoMoneda.format(grantotal)));
        }
        catch(NumberFormatException e)
        {
            error = true;
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, (new StringBuilder()).append("El precio debe ser de tipo num\351rico o decimal ").append(e.getMessage()).toString(), "Error", 0);
        }
        if(!error)
            limpiardtsarex();
    }

    public void quitarrticulo()
    {
        try
        {
            NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(Locale.US);
            int numerodefila = tablaconsultaarticulosven1.getSelectedRow();
            if(numerodefila < 0)
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Por favor seleccione un registro de la tabla venta", "Error", 0);
            } else
            {
                Object totalarticulo = tablaconsultaarticulosven1.getValueAt(tablaconsultaarticulosven1.getSelectedRow(), 4);
                float tarticulo = Float.parseFloat(totalarticulo.toString());
                grantotal = grantotal - tarticulo;
                dtmarticulosv1.removeRow(numerodefila);
                jLtotalventa.setText(String.valueOf(formatoMoneda.format(grantotal)));
            }
        }
        catch(NumberFormatException ex)
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void jButton4ActionPerformed(ActionEvent evt)
    {
        limpiartblconarv1();
    }

    private void jBquitardeventaActionPerformed(ActionEvent evt)
    {
        quitarrticulo();
    }

    private void jButton6ActionPerformed(ActionEvent evt)
    {
        limpiardtsarex();
    }

    private void jButton5ActionPerformed(ActionEvent evt)
    {
        validardtsarex();
    }

    private void jButton2ActionPerformed(ActionEvent evt)
    {
        validartablaventa();
    }

    private void tablaconsultaventasMouseClicked(MouseEvent evt)
    {
        try
        {
            if(evt.getButton() == 3)
            {
                ventanumero = tablaconsultaventas.getValueAt(tablaconsultaventas.getSelectedRow(), 2);
                fechaventa = tablaconsultaventas.getValueAt(tablaconsultaventas.getSelectedRow(), 0);
                menuContextualven.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }
        catch(ArrayIndexOutOfBoundsException evento)
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Por favor seleccione un registro", "Error", 0);
        }
    }

    private void btnlimpiartablacart2ActionPerformed(ActionEvent evt)
    {
        limpiartblconventas();
    }

    private void btnconsultaventasActionPerformed(ActionEvent evt)
    {
        validarfechaventas();
    }

    public void validarfechaventas()
    {
        Date fechavts = jDCfechaventa.getDate();
        if(fechavts == null)
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Por favor seleccione una fecha", "Error", 0);
        } else
        {
            consultarventas(fechavts);
        }
    }

    public void consultarventas(Date fechav)
    {
        DecimalFormat dosDigitos = new DecimalFormat("0.00");
        SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            limpiartblconventas();
            try
            {
                stconsultarventas.setString(1, formatofecha.format(fechav));
                ResultSet res = stconsultarventas.executeQuery();
                int nColumnas = res.getMetaData().getColumnCount();
                for(; res.next(); dtmconven.addRow(dataconven))
                {
                    for(int i = 1; i <= nColumnas; i++)
                    {
                        if(i == 6)
                        {
                            dataconven[i - 1] = dosDigitos.format(res.getFloat(i));
                            continue;
                        }
                        if(i == 8)
                            dataconven[i - 1] = dosDigitos.format(res.getFloat(i));
                        else
                            dataconven[i - 1] = res.getString(i);
                    }

                }

            }
            catch(SQLException ex)
            {
                int codigoerror = ex.getErrorCode();
                if(codigoerror == 0)
                {
                    initDB();
                    validarfechaventas();
                } else
                {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        }
        catch(NullPointerException ex)
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void jMenuItem2ActionPerformed(ActionEvent evt)
    {
        jFconsultaventas.setLocationRelativeTo(null);
        jFconsultaventas.setVisible(true);
    }

    private void jFaltaventaWindowClosing(WindowEvent evt)
    {
        limpiartblconarv1();
        limpiardtsarex();
        jTFcantidadarticulos.setText("");
        jtfnombreartconv.setText("");
        limpiartblventa();
        jtfnombreartconv.grabFocus();
    }

    private void jMenuItem3ActionPerformed(ActionEvent evt)
    {
        preguntardeletetodasven();
    }

    private void jMenuItem4ActionPerformed(ActionEvent evt)
    {
        jFnombreempresa.setLocationRelativeTo(null);
        jFnombreempresa.setVisible(true);
    }

    private void jButton7ActionPerformed(ActionEvent evt)
    {
        limpiarnombreemp();
    }

    private void jButton3ActionPerformed(ActionEvent evt)
    {
        validarnombreempresa();
    }

    private void tablavistapreliminarventaMouseClicked(MouseEvent mouseevent)
    {
    }

    private void jButton8ActionPerformed(ActionEvent evt)
    {
        imprimirtablaventa();
    }

    private void jButton9ActionPerformed(ActionEvent evt)
    {
        validarvistapre();
    }

    private void tablavistapreliminarventa1MouseClicked(MouseEvent mouseevent)
    {
    }

    private void jButton10ActionPerformed(ActionEvent evt)
    {
        imprimirremisionventa(fechaventa.toString(), ventanumero.toString(), tablavistapreliminarventa1);
    }

    public void validarnombreempresa()
    {
        if(jTnombreempresa.getText().equals(""))
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Por favor capture el nombre", "Error", 0);
        } else
        {
            guardarnombreemp();
        }
    }

    public void guardarnombreemp()
    {
        try
        {
            String nombre = jTnombreempresa.getText();
            ResultSet res = stnombreempresa.executeQuery();
            if(res.next())
            {
                boolean error = false;
                try
                {
                    actnombreempresa.setString(1, nombre);
                    actnombreempresa.execute();
                }
                catch(SQLException e)
                {
                    error = true;
                    int codigoerror = e.getErrorCode();
                    if(codigoerror == 0)
                    {
                        initDB();
                        guardarnombreemp();
                    } else
                    {
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(null, (new StringBuilder()).append("ERROR, Los datos no se pudieron guardar.\n ").append(e.getMessage()).toString());
                    }
                }
                if(!error)
                    JOptionPane.showMessageDialog(null, "El nombre de la empresa se actualizo con \351xito", "Mensaje", 1);
                limpiarnombreemp();
                colocarnombreempresa();
            } else
            {
                boolean error = false;
                try
                {
                    insnombreempresa.setString(1, nombre);
                    insnombreempresa.execute();
                }
                catch(SQLException e)
                {
                    error = true;
                    int codigoerror = e.getErrorCode();
                    if(codigoerror == 0)
                    {
                        initDB();
                        guardarnombreemp();
                    } else
                    {
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(null, (new StringBuilder()).append("ERROR, Los datos no se pudieron guardar.\n ").append(e.getMessage()).toString());
                    }
                }
                if(!error)
                    JOptionPane.showMessageDialog(null, "El nombre de la empresa se inserto con \351xito", "Mensaje", 1);
                limpiarnombreemp();
                colocarnombreempresa();
            }
        }
        catch(SQLException ex)
        {
            int codigoerror = ex.getErrorCode();
            if(codigoerror == 0)
            {
                initDB();
                guardarnombreemp();
            } else
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    public void limpiarnombreemp()
    {
        jTnombreempresa.setText("");
        jTnombreempresa.grabFocus();
    }

    public void validartablaventa()
    {
        int totalfilasventa = dtmarticulosv1.getRowCount();
        if(totalfilasventa == 0)
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "No hay ning\372n registro para guardar", "Error", 0);
        } else
        {
            obtenerdtsdeventa();
        }
    }

    public void validarvistapre()
    {
        int numerovent = Integer.parseInt(jLnumerodeventa.getText());
        int totalfilasventa = dtmarticulosv1.getRowCount();
        int totalcolumnasventa = dtmarticulosv1.getColumnCount();
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(Locale.US);
        if(totalfilasventa == 0)
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "No hay ning\372n registro para imprimir", "Error", 0);
        } else
        {
            limpiartblvistap();
            for(int fila = 0; fila < totalfilasventa; fila++)
            {
                for(int columna = 0; columna < totalcolumnasventa; columna++)
                    dataconven[columna] = dtmarticulosv1.getValueAt(fila, columna);

                dtmvistaprev.addRow(dataconven);
            }

            dataconven[0] = "";
            dataconven[1] = (new StringBuilder()).append("GRAN TOTAL DE LA VENTA # ".concat(String.valueOf(numerovent)).concat(": ")).append(formatoMoneda.format(grantotal)).toString();
            dataconven[2] = "";
            dataconven[3] = "";
            dataconven[4] = "";
            dtmvistaprev.addRow(dataconven);
            dataconven[0] = "";
            dataconven[1] = super.getTitle();
            dataconven[2] = "";
            dataconven[3] = "";
            dataconven[4] = "";
            dtmvistaprev.addRow(dataconven);
            jFvistapreliminarv.setLocationRelativeTo(null);
            jFvistapreliminarv.setVisible(true);
        }
    }

    public void imprimirtablaventa()
    {
        String fecha = jLfechaventa.getText();
        String venta = jLnumerodeventa.getText();
        imprimirremisionventa(fecha, venta, tablavistapreliminarventa);
    }

    public void obtenerdtsdeventa()
    {
        errorguardarventa = 0;
        int totalfilasventa = dtmarticulosv1.getRowCount();
        int totalcolumnasventa = dtmarticulosv1.getColumnCount();
        String fechaven = jLfechaventa.getText();
        String horaven = jLhoraventa.getText();
        int numeroven = Integer.parseInt(jLnumerodeventa.getText());
        int claveven = 0;
        String descripcionven = null;
        float precioven = 0.0F;
        int cantidadven = 0;
        float totalven = 0.0F;
        String grantotal = jLtotalventa.getText();
        for(int fila = 0; fila < totalfilasventa; fila++)
        {
            for(int columna = 0; columna < totalcolumnasventa; columna++)
            {
                String dato = dtmarticulosv1.getValueAt(fila, columna).toString();
                if(columna == 0)
                {
                    claveven = Integer.parseInt(dato);
                    continue;
                }
                if(columna == 1)
                {
                    descripcionven = dato;
                    continue;
                }
                if(columna == 2)
                {
                    precioven = Float.parseFloat(dato);
                    continue;
                }
                if(columna == 3)
                {
                    cantidadven = Integer.parseInt(dato);
                    continue;
                }
                if(columna == 4)
                    totalven = Float.parseFloat(dato);
            }

            guardarventa(fechaven, horaven, numeroven, claveven, descripcionven, precioven, cantidadven, totalven);
        }

        guardarventa(fechaven, horaven, numeroven, 0, "GRAN TOTAL DE LA VENTA # ".concat(String.valueOf(numeroven)).concat(": ").concat(grantotal), 0.0F, 0, 0.0F);
        guardarventa(fechaven, horaven, numeroven, 0, super.getTitle(), 0.0F, 0, 0.0F);
        colocardatosdeventa();
        limpiartblconarv1();
        if(errorguardarventa == 0)
        {
            JOptionPane.showMessageDialog(null, "La venta se ha guardado con \351xito", "Mensaje", 1);
        } else
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Alg\372n registro o registros no fueron guardados con \351xito, por favor consulte las ventas", "Error", 0);
        }
    }

    public void guardarventa(String fechave, String horave, int numerove, int claveve, String descripcionve, float preciove, int cantidadve, 
            float totalve)
    {
        try
        {
            stguardarventa.setString(1, fechave);
            stguardarventa.setString(2, horave);
            stguardarventa.setInt(3, numerove);
            stguardarventa.setInt(4, claveve);
            stguardarventa.setString(5, descripcionve);
            stguardarventa.setFloat(6, preciove);
            stguardarventa.setInt(7, cantidadve);
            stguardarventa.setFloat(8, totalve);
            stguardarventa.execute();
        }
        catch(SQLException ex)
        {
            errorguardarventa++;
            int codigoerror = ex.getErrorCode();
            if(codigoerror == 0)
            {
                initDB();
                obtenerdtsdeventa();
            } else
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, (new StringBuilder()).append("ERROR, Los datos no se pudieron guardar.\n ").append(ex.getMessage()).toString());
            }
        }
        catch(NumberFormatException e)
        {
            errorguardarventa++;
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void validardtsarex()
    {
        int cantidadarex = 0;
        if(jTFdescripcionarex.getText().equals("") || jTFprecioarex.getText().equals("") || jTFcantidadarex.getText().equals(""))
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Por favor llene todos los campos", "Error", 0);
        } else
        if(!jTFcantidadarex.getText().matches("\\d+"))
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "La cantidad de art\355culos debe ser de tipo num\351rico", "Error", 0);
        } else
        {
            cantidadarex = Integer.parseInt(jTFcantidadarex.getText());
            pasararticuloex(cantidadarex);
        }
    }

    public void limpiardtsarex()
    {
        jTFdescripcionarex.setText("");
        jTFprecioarex.setText("");
        jTFcantidadarex.setText("");
    }

    public void validardtscontra()
    {
        String nuevacontra = jTFnuevacontra.getText();
        String nuevacontrac = jTFconfirmarnuecon.getText();
        String indiciocon = jTFindicionuecon.getText();
        if(jTFnuevacontra.getText().equals("") || jTFconfirmarnuecon.getText().equals("") || jTFindicionuecon.getText().equals(""))
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Por favor llene todos los campos", "Error", 0);
        } else
        if(!nuevacontra.equals(nuevacontrac))
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "La nueva contrase\361a no coincide con la contrase\361a confirmada", "Error", 0);
        } else
        if(indiciocon.equals(nuevacontra) && indiciocon.equals(nuevacontrac))
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "El indicio no puede ser igual a la nueva contrase\361a", "Error", 0);
        } else
        {
            obtenerdtsactclv();
        }
    }

    public void obtenerdtsactclv()
    {
        try
        {
            String nuevacontra = jTFnuevacontra.getText();
            String indicio = jTFindicionuecon.getText();
            ResultSet res = stconclvadmin.executeQuery();
            if(res.next())
            {
                boolean error = false;
                try
                {
                    actclvadmin.setString(1, nuevacontra);
                    actclvadmin.setString(2, indicio);
                    actclvadmin.execute();
                }
                catch(SQLException e)
                {
                    error = true;
                    int codigoerror = e.getErrorCode();
                    if(codigoerror == 0)
                    {
                        initDB();
                        obtenerdtsactclv();
                    } else
                    {
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(null, (new StringBuilder()).append("ERROR, Los datos no se pudieron guardar.\n ").append(e.getMessage()).toString());
                    }
                }
                if(!error)
                    JOptionPane.showMessageDialog(null, "La clave del administrador se actualizo con \351xito", "Mensaje", 1);
                limpiardtsnuecontra();
            } else
            {
                boolean error = false;
                try
                {
                    insclvadmin.setString(1, nuevacontra);
                    insclvadmin.setString(2, indicio);
                    insclvadmin.execute();
                }
                catch(SQLException e)
                {
                    error = true;
                    int codigoerror = e.getErrorCode();
                    if(codigoerror == 0)
                    {
                        initDB();
                        obtenerdtsactclv();
                    } else
                    {
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(null, (new StringBuilder()).append("ERROR, Los datos no se pudieron guardar.\n ").append(e.getMessage()).toString());
                    }
                }
                if(!error)
                    JOptionPane.showMessageDialog(null, "La clave del administrador se inserto con \351xito", "Mensaje", 1);
                limpiardtsnuecontra();
            }
        }
        catch(SQLException ex)
        {
            int codigoerror = ex.getErrorCode();
            if(codigoerror == 0)
            {
                initDB();
                obtenerdtsactclv();
            } else
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    public void limpiardtsnuecontra()
    {
        jTFnuevacontra.setText("");
        jTFconfirmarnuecon.setText("");
        jTFindicionuecon.setText("");
    }

    public void verificlavead()
    {
        String contraseF1ausr = new String(jppassword.getPassword());
        String contraseF1abd = null;
        try
        {
            for(ResultSet rs = stconclvadmin.executeQuery(); rs.next();)
                contraseF1abd = rs.getString("claveadmin");

            if(contraseF1abd == null)
                jDlogin.setVisible(false);
            else
            if(contraseF1ausr.equals(contraseF1abd))
            {
                jDlogin.setVisible(false);
            } else
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(jDlogin, "La contrase\361a no coincide, vuelva a intentarlo", "Error", 0);
            }
        }
        catch(SQLException ex)
        {
            int codigoerror = ex.getErrorCode();
            if(codigoerror == 0)
            {
                initDB();
                verificlavead();
            } else
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    public void verficardtsaltaar()
    {
        if(jTFclavear.getText().equals("") || jTFdescripcionar.getText().equals("") || jTFpreciocompraar.getText().equals("") || jTFprecioar.getText().equals(""))
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Por favor llene todos los campos", "Error", 0);
        } else
        if(!jTFclavear.getText().matches("\\d+"))
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "La clave del art\355culo debe ser de tipo num\351rico", "Error", 0);
        } else
        {
            obtenerdtsaltaar();
        }
    }

    public void obtenerdtsaltaar()
    {
        boolean error = false;
        try
        {
            float preciocompraar = 0.0F;
            float precioar = 0.0F;
            int clvarticulo = Integer.parseInt(jTFclavear.getText());
            String descarticulo = jTFdescripcionar.getText();
            preciocompraar = Float.parseFloat(jTFpreciocompraar.getText());
            precioar = Float.parseFloat(jTFprecioar.getText());
            guardardtsart.setInt(1, clvarticulo);
            guardardtsart.setString(2, descarticulo);
            guardardtsart.setFloat(3, preciocompraar);
            guardardtsart.setFloat(4, precioar);
            guardardtsart.execute();
        }
        catch(SQLException ex)
        {
            error = true;
            int codigoerror = ex.getErrorCode();
            if(codigoerror == 0)
            {
                initDB();
                obtenerdtsaltaar();
            } else
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, (new StringBuilder()).append("ERROR, Los datos no se pudieron guardar.\n ").append(ex.getMessage()).toString());
            }
        }
        catch(NumberFormatException e)
        {
            error = true;
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Los precios de compra y venta deben ser de tipo num\351rico o decimal", "Error", 0);
        }
        if(!error)
        {
            limpiartblventa();
            limpiartblconarv1();
            JOptionPane.showMessageDialog(null, "Los datos del art\355culo se guardaron con \351xito", "Mensaje", 1);
            limpiardtsaltaar();
            jTFclavear.grabFocus();
        }
    }

    public void obtenerdtsmodar()
    {
        boolean error = false;
        try
        {
            float preciocompraarm = 0.0F;
            float precioarm = 0.0F;
            String descarticulom = jTFdescripcionarm.getText();
            preciocompraarm = Float.parseFloat(jTFpreciocompraarm.getText());
            precioarm = Float.parseFloat(jTFprecioarm.getText());
            modificardtsartm.setString(1, descarticulom);
            modificardtsartm.setFloat(2, preciocompraarm);
            modificardtsartm.setFloat(3, precioarm);
            modificardtsartm.setString(4, clavearticulo.toString());
            modificardtsartm.execute();
            limpiartblconar();
            jtfnombreartcon.grabFocus();
        }
        catch(SQLException ex)
        {
            error = true;
            int codigoerror = ex.getErrorCode();
            if(codigoerror == 0)
            {
                initDB();
                obtenerdtsmodar();
            } else
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, (new StringBuilder()).append("ERROR, Los datos no se pudieron modificar.\n ").append(ex.getMessage()).toString());
            }
        }
        catch(NumberFormatException e)
        {
            error = true;
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Los precios de compra y venta deben ser de tipo num\351rico o decimal", "Error", 0);
        }
        if(!error)
        {
            limpiartblventa();
            limpiartblconarv1();
            JOptionPane.showMessageDialog(null, "Los datos del art\355culo se modificaron con \351xito", "Mensaje", 1);
            jFmodificarar.setVisible(false);
            jTFdescripcionarm.grabFocus();
        }
    }

    public static void main(String args[])
    {
        JFrame.setDefaultLookAndFeelDecorated(true);
        SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.MangoSkin");
        SubstanceLookAndFeel.setCurrentWatermark("org.jvnet.substance.watermark.SubstanceBubblesWatermark");
        (new interfazsisconpre()).setVisible(true);
    }

    private void initDB()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sisconpre", "isaac", "perez");
            stnombreempresa = dbConnection.prepareStatement("select name from nombreempresa");
            guardardtsart = dbConnection.prepareStatement("INSERT INTO articulos(clave, descripcion, preciocompra, precio) values(?,?,?,?)");
            stguardarventa = dbConnection.prepareStatement("INSERT INTO ventas(fechav, horav, numeroventav, clavev, descripcionv, preciov, cantidadv, totalv) values(?,?,?,?,?,?,?,?)");
            starticulolike = dbConnection.prepareStatement("SELECT * FROM articulos WHERE descripcion LIKE ? || descripcion LIKE ? || descripcion LIKE ? order by clave");
            starticulolikev = dbConnection.prepareStatement("SELECT clave, descripcion, precio FROM articulos WHERE descripcion LIKE ? || descripcion LIKE ? || descripcion LIKE ? order by clave");
            stdelarticulo = dbConnection.prepareStatement("DELETE FROM articulos WHERE clave = ?");
            stdelventa = dbConnection.prepareStatement("delete from ventas where fechav = ? and numeroventav = ?");
            stdeltodasventa = dbConnection.prepareStatement("truncate ventas");
            stcondtsarticulomod = dbConnection.prepareStatement("SELECT descripcion, preciocompra, precio from articulos WHERE clave = ? ");
            modificardtsartm = dbConnection.prepareStatement("UPDATE articulos SET descripcion = ?, preciocompra = ?, precio = ? where clave = ?");
            stconclvadmin = dbConnection.prepareStatement("SELECT claveadmin FROM administrador");
            actclvadmin = dbConnection.prepareStatement("UPDATE administrador SET claveadmin = ?, indicio = ?");
            actnombreempresa = dbConnection.prepareStatement("UPDATE nombreempresa SET name = ?");
            insclvadmin = dbConnection.prepareStatement("INSERT INTO administrador (claveadmin, indicio) VALUES(?, ?)");
            insnombreempresa = dbConnection.prepareStatement("INSERT INTO nombreempresa (name) VALUES(?)");
            stconindicio = dbConnection.prepareStatement("SELECT indicio FROM administrador");
            stnumerodeventa = dbConnection.prepareStatement("select max(numeroventav) from ventas where fechav = ?");
            stconsultarventas = dbConnection.prepareStatement("select * from ventas where fechav = ?");
            stimprimirventag = dbConnection.prepareStatement("select clavev, descripcionv, preciov, cantidadv, totalv from ventas where numeroventav = ? and fechav = ?");
        }
        catch(Exception e)
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "No se puede conectar a la base de datos", "Error", 0);
        }
    }

    public void imprimirreporteena(String titulo, JTable tabla)
    {
        MessageFormat tituloreporteea = null;
        tituloreporteea = new MessageFormat(titulo);
        MessageFormat footer = null;
        footer = new MessageFormat("Page {0}");
        boolean showPrintDialog = true;
        boolean interactive = true;
        javax.swing.JTable.PrintMode mode = javax.swing.JTable.PrintMode.FIT_WIDTH;
        try
        {
            boolean complete = tabla.print(mode, tituloreporteea, footer, showPrintDialog, null, interactive, null);
            if(complete)
                JOptionPane.showMessageDialog(null, "Printing Complete", "Printing Result", 1);
            else
                JOptionPane.showMessageDialog(null, "Printing Cancelled", "Printing Result", 1);
        }
        catch(PrinterException pe)
        {
            JOptionPane.showMessageDialog(null, (new StringBuilder()).append("Printing Failed: ").append(pe.getMessage()).toString(), "Printing Result", 0);
        }
    }

    public void imprimirremisionventa(String fec, String ven, JTable tabla)
    {
        MessageFormat tituloreporteea = null;
        tituloreporteea = new MessageFormat("\241Gracias por su compra! ".concat(fec).concat(" #").concat(ven));
        MessageFormat footer = null;
        footer = new MessageFormat("Page {0}");
        boolean showPrintDialog = true;
        boolean interactive = true;
        javax.swing.JTable.PrintMode mode = javax.swing.JTable.PrintMode.FIT_WIDTH;
        try
        {
            Font tipofuenteantimp = new Font("Tahoma", 0, 7);
            Font tipofuentedesimp = new Font("Tahoma", 0, 12);
            tabla.setFont(tipofuenteantimp);
            boolean complete = tabla.print(mode, tituloreporteea, null, showPrintDialog, null, interactive, null);
            tabla.setFont(tipofuentedesimp);
            if(complete)
                JOptionPane.showMessageDialog(null, "Printing Complete", "Printing Result", 1);
            else
                JOptionPane.showMessageDialog(null, "Printing Cancelled", "Printing Result", 1);
        }
        catch(PrinterException pe)
        {
            JOptionPane.showMessageDialog(null, (new StringBuilder()).append("Printing Failed: ").append(pe.getMessage()).toString(), "Printing Result", 0);
        }
    }

    Connection dbConnection;
    PreparedStatement guardardtsart;
    PreparedStatement starticulolike;
    PreparedStatement stdelarticulo;
    PreparedStatement stcondtsarticulomod;
    PreparedStatement modificardtsartm;
    PreparedStatement stconclvadmin;
    PreparedStatement actclvadmin;
    PreparedStatement insclvadmin;
    PreparedStatement stconindicio;
    PreparedStatement starticulolikev;
    PreparedStatement stnumerodeventa;
    PreparedStatement stguardarventa;
    PreparedStatement stconsultarventas;
    PreparedStatement stdelventa;
    PreparedStatement stdeltodasventa;
    PreparedStatement stnombreempresa;
    PreparedStatement actnombreempresa;
    PreparedStatement insnombreempresa;
    PreparedStatement stimprimirventag;
    private JMenuItem elementos[];
    private JMenuItem elementosven[];
    private JPopupMenu menuContextual;
    private JPopupMenu menuContextualven;
    Icon modificard;
    Icon eliminar;
    Icon imprimir;
    formatosfechahora formatos;
    float grantotal;
    int errorguardarventa;
    String cabecerasart[] = {
        "Clave", "Descripci\363n", "Precio compra", "Precio venta"
    };
    Object dataart[];
    String cabecerasartv[] = {
        "Clave", "Descripci\363n", "Precio venta"
    };
    Object dataartv[];
    String cabecerasartv1[] = {
        "Clave", "Descripci\363n", "Precio", "Cantidad", "Total"
    };
    Object dataartv1[];
    String cabecerasvistaprev[] = {
        "Clave", "Descripci\363n", "Precio", "Cantidad", "Total"
    };
    Object datavistaprev[];
    String cabecerasvistaprev1[] = {
        "Clave", "Descripci\363n", "Precio", "Cantidad", "Total"
    };
    Object datavistaprev1[];
    String cabecerasconven[] = {
        "Fecha", "Hora", "Venta #", "Clave", "Descripci\363n", "Precio", "Cantidad", "Total"
    };
    Object dataconven[];
    DefaultTableModel dtmarticulos;
    DefaultTableModel dtmarticulosv;
    DefaultTableModel dtmarticulosv1;
    DefaultTableModel dtmconven;
    DefaultTableModel dtmvistaprev;
    DefaultTableModel dtmvistaprev1;
    Object clavearticulo;
    Object ventanumero;
    Object fechaventa;
    private JButton btnconsultalimnomar;
    private JButton btnconsultalimnomar1;
    private JButton btnconsultaventas;
    private JButton btnlimpiartablacart;
    private JButton btnlimpiartablacart1;
    private JButton btnlimpiartablacart2;
    private JButton jBExportar;
    private JButton jBaceptarcontra;
    private JButton jBagregaraventa;
    private JButton jBcancelarcontra;
    private JButton jBguardarar;
    private JButton jBguardararm;
    private JButton jBguardardtsnuecon;
    private JButton jBlimpiardtsar;
    private JButton jBlimpiartdtsnuecon;
    private JButton jBquitardeventa;
    private JButton jButton1;
    private JButton jButton10;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JButton jButton5;
    private JButton jButton6;
    private JButton jButton7;
    private JButton jButton8;
    private JButton jButton9;
    private JDateChooser jDCfechaventa;
    private JDialog jDlogin;
    private JFrame jFactcontraseF1a;
    private JFrame jFaltaar;
    private JFrame jFaltaventa;
    private JFrame jFconsultaar;
    private JFrame jFconsultaventas;
    private JFrame jFmodificarar;
    private JFrame jFnombreempresa;
    private JFrame jFvistapreliminarv;
    private JFrame jFvistapreliminarv1;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel14;
    private JLabel jLabel15;
    private JLabel jLabel16;
    private JLabel jLabel17;
    private JLabel jLabel18;
    private JLabel jLabel19;
    private JLabel jLabel2;
    private JLabel jLabel20;
    private JLabel jLabel21;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JLabel jLfechaventa;
    private JLabel jLhoraventa;
    private JLabel jLlogosisconpre;
    private JLabel jLnumerodeventa;
    private JLabel jLtotalventa;
    private JMenuItem jMIacercade;
    private JMenuItem jMIaltaar;
    private JMenuItem jMIconsultaar;
    private JMenuItem jMIcontraseF1a;
    private JMenu jMarticulos;
    private JMenu jMayuda;
    private JMenu jMenu1;
    private JMenu jMenu2;
    private JMenu jMenu3;
    private JMenu jMenu4;
    private JMenu jMenu5;
    private JMenuBar jMenuBar1;
    private JMenuItem jMenuItem1;
    private JMenuItem jMenuItem2;
    private JMenuItem jMenuItem3;
    private JMenuItem jMenuItem4;
    private JPanel jPanel1;
    private JPanel jPanel10;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel59;
    private JPanel jPanel6;
    private JPanel jPanel60;
    private JPanel jPanel61;
    private JPanel jPanel62;
    private JPanel jPanel63;
    private JPanel jPanel64;
    private JPanel jPanel65;
    private JPanel jPanel7;
    private JPanel jPanel8;
    private JPanel jPanel9;
    private JPanel jParticulos;
    private JPanel jParticulosventa;
    private JPanel jPcontcon6;
    private JPanel jPcontcon7;
    private JPanel jPcontcon8;
    private JPanel jPextras;
    private JPanel jPpasar;
    private JPanel jPresultadoc6;
    private JPanel jPresultadoc7;
    private JPanel jPresultadoc8;
    private JScrollPane jScrollPane11;
    private JScrollPane jScrollPane12;
    private JScrollPane jScrollPane13;
    private JScrollPane jScrollPane14;
    private JScrollPane jScrollPane15;
    private JScrollPane jScrollPane16;
    private JTextField jTFcantidadarex;
    private JTextField jTFcantidadarticulos;
    private JTextField jTFclavear;
    private JTextField jTFconfirmarnuecon;
    private JTextField jTFdescripcionar;
    private JTextField jTFdescripcionarex;
    private JTextField jTFdescripcionarm;
    private JTextField jTFindicionuecon;
    private JTextField jTFnuevacontra;
    private JTextField jTFprecioar;
    private JTextField jTFprecioarex;
    private JTextField jTFprecioarm;
    private JTextField jTFpreciocompraar;
    private JTextField jTFpreciocompraarm;
    private JTextField jTnombreempresa;
    private JPasswordField jppassword;
    private JTextField jtfnombreartcon;
    private JTextField jtfnombreartconv;
    private JTable tablaconsultaarticulos;
    private JTable tablaconsultaarticulosven;
    private JTable tablaconsultaarticulosven1;
    private JTable tablaconsultaventas;
    private JTable tablavistapreliminarventa;
    private JTable tablavistapreliminarventa1;

















































}
