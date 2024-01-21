// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   formatosfechahora.java

package principal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class formatosfechahora
{

    public formatosfechahora()
    {
    }

    public String obtenerfecha()
    {
        DateFormat yearp = new SimpleDateFormat("yyyy/MM/dd");
        String fechasalida = yearp.format(new Date());
        return fechasalida;
    }

    public String obtenerhora()
    {
        DateFormat df2p = new SimpleDateFormat("hh:mm:ss");
        String horasalida = df2p.format(new Date());
        return horasalida;
    }
}
