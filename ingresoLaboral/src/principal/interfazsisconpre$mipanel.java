// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   interfazsisconpre.java

package principal;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

// Referenced classes of package principal:
//            interfazsisconpre

public class interfazsisconpre$mipanel extends JPanel
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

    public interfazsisconpre$mipanel()
    {
        this$0 = interfazsisconpre.this;
        super();
        setSize(256, 256);
    }
}
