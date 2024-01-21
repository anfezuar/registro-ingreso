/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import static GUI.CapturarHuella.lblPasos;
import GUI.MainFrame;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import javax.swing.ImageIcon;

/**
 *
 * @author Rec Tecnologicos
 */
public class IngresoHuella extends MainFrame{
    
    private DPFPEnrollment enrrollador;
    private DPFPVerification verificador = DPFPGlobal.getVerificationFactory().createVerification();
    private DPFPTemplate plantillahuella;
    
    private DPFPVerificationResult resultado = null;
    private DPFPFeatureSet caracteristicas = null;
    
    private boolean buscar = false;

    public boolean isBuscar() {
        return buscar;
    }

    public void setBuscar(boolean buscar) {
        this.buscar = buscar;
    }

    public DPFPTemplate getPlantillahuella() {
        return plantillahuella;
    }

    public void setPlantillahuella(DPFPTemplate plantillahuella) {
        this.plantillahuella = plantillahuella;
    }
    
    
    
    public IngresoHuella() {
        super();
        
        try {
            enrrollador = DPFPGlobal.getEnrollmentFactory().createEnrollment();
        } catch (java.lang.UnsatisfiedLinkError | java.lang.NoClassDefFoundError e) {
            setVisible(false);
        }
    }
    

    @Override
    protected void procesarHuella(DPFPSample sample) {
        super.procesarHuella(sample); //To change body of generated methods, choose Tools | Templates.
        
        caracteristicas = extractFeature(sample, isBuscar()?DPFPDataPurpose.DATA_PURPOSE_VERIFICATION:DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
        
        if (caracteristicas != null) {
            try {
                enrrollador.addFeatures(caracteristicas);
            } catch (Exception e) {
            } finally{
                switch(enrrollador.getFeaturesNeeded()){
                    case 4:
                        lblPasos.setIcon(new ImageIcon(getClass().getResource("/images/score0.png")));
                        break;
                    case 3:
                        lblPasos.setIcon(new ImageIcon(getClass().getResource("/images/score1.png")));
                        break;
                    case 2:
                        lblPasos.setIcon(new ImageIcon(getClass().getResource("/images/score2.png")));
                        break;
                    case 1:
                        lblPasos.setIcon(new ImageIcon(getClass().getResource("/images/score3.png")));
                        break;
                    case 0:
                        lblPasos.setIcon(new ImageIcon(getClass().getResource("/images/score4.png")));
                        break;
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
        }
    }
}
