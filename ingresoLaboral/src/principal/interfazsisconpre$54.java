// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   interfazsisconpre.java

package principal;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

// Referenced classes of package principal:
//            interfazsisconpre

class interfazsisconpre$54
    implements Runnable
{

    public void run()
    {
        JScrollBar vbar = interfazsisconpre.access$5000(interfazsisconpre.this).getVerticalScrollBar();
        vbar.setValue(vbar.getMaximum());
    }

    final interfazsisconpre this$0;

    interfazsisconpre$54()
    {
        this$0 = interfazsisconpre.this;
        super();
    }
}
