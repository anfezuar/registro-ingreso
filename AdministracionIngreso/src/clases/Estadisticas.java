/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import Formularios.verEstadisticas;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rec Tecnologicos
 */
public class Estadisticas {

    Vector<Time> vEntradas = new Vector<Time>();
    Vector<Time> vSalidas = new Vector<Time>();
    Vector<Date> vFechas = new Vector<Date>();
    ConexionBD con = new ConexionBD();
    String where;
    String fecha;
    Time horaactual;
    
    public Estadisticas(String where) {
        this.where = where;
        Calendar ca1endar = Calendar.getInstance();
        String hour = Integer.toString(ca1endar.get(Calendar.HOUR));
        String minuto = Integer.toString(ca1endar.get(Calendar.MINUTE));

        String dia = Integer.toString(ca1endar.get(Calendar.DATE));
        String mes = Integer.toString(ca1endar.get(Calendar.MONTH)+1);
        String annio = Integer.toString(ca1endar.get(Calendar.YEAR));


        mes = String.format("%2s", mes).replace(' ','0');
        minuto = String.format("%2s", minuto).replace(' ','0');

        fecha = annio+"-"+mes+"-"+dia;
        minuto = String.format("%2s", minuto).replace(' ','0');
        String hora = hour+":"+minuto+":00";
        horaactual = Time.valueOf(hora);
        
        obtenerEntradas();
        obtenerSalidas();
    }
    
    

    public Vector<Time> getvEntradas() {
        return vEntradas;
    }

    public Vector<Time> getvSalidas() {
        return vSalidas;
    }
    
    public Vector<Date> getvFechas() {
        return vFechas;
    }
    
    private void obtenerEntradas(){
        try {
            
            String sql = "SELECT hora, fecha FROM registros where tipo = 'Entrada' and "+where+" order by idregistros asc";
            
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            ResultSet result = pst.executeQuery();
    
            while(result.next()){
                vEntradas.add(result.getTime(1));
                vFechas.add(result.getDate(2));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(verEstadisticas.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    private void obtenerSalidas(){
        try {
            
            String sql = "SELECT hora FROM registros where tipo = 'Salida' and "+where+" order by idregistros asc";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            ResultSet result = pst.executeQuery();
            System.out.println(sql);

            while(result.next()){
                vSalidas.add(result.getTime(1));
            } 
        } catch (SQLException ex) {
            Logger.getLogger(verEstadisticas.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    
    public static Time getDifferenceBetwenDates(Time dateInicio, Time dateFinal) {
        long milliseconds = dateFinal.getTime() - dateInicio.getTime();
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.SECOND, seconds);
        c.set(Calendar.MINUTE, minutes);
        c.set(Calendar.HOUR_OF_DAY, hours);
        return Time.valueOf(hours+":"+minutes+":"+seconds);
    }
}
