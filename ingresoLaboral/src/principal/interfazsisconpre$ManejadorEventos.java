// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   interfazsisconpre.java

package principal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JTable;

// Referenced classes of package principal:
//            interfazsisconpre

private class interfazsisconpre$ManejadorEventos
    implements ActionListener
{

    public void actionPerformed(ActionEvent evento)
    {
        for(int i = 0; i < interfazsisconpre.access$200(interfazsisconpre.this).length; i++)
            if(evento.getSource() == interfazsisconpre.access$200(interfazsisconpre.this)[i])
            {
                String nombreaccion = interfazsisconpre.access$200(interfazsisconpre.this)[i].getText();
                if("Modificar".equals(nombreaccion))
                    mostrardamodar(clavearticulo.toString());
                else
                if("Eliminar".equals(nombreaccion))
                    preguntardelete(clavearticulo.toString());
                return;
            }

    }

    final interfazsisconpre this$0;

    private interfazsisconpre$ManejadorEventos()
    {
        this$0 = interfazsisconpre.this;
        super();
    }


    // Unreferenced inner class principal/interfazsisconpre$1

/* anonymous class */
    class interfazsisconpre._cls1 extends JTable
    {

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

}
