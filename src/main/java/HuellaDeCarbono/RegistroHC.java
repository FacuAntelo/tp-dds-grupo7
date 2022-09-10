package HuellaDeCarbono;

import unidad.KG;
import unidad.Unidad;
import java.time.LocalDate;


public class RegistroHC {
    private LocalDate fecha;
    private HuellaDeCarbono valorHCDatoActividad;
    private HuellaDeCarbono valorHCTrayecto;
    private HuellaDeCarbono valorHCTotal;
    private String masaUnidad;
    private Unidad tipoUnidad;

    public RegistroHC(HuellaDeCarbono valorHCDatoActividad, HuellaDeCarbono valorHCTrayecto){
        this.valorHCDatoActividad= valorHCDatoActividad;
        this.valorHCTrayecto= valorHCTrayecto;
        this.tipoUnidad = KG.getKG();
     }
     public String unidad() {
            return this.tipoUnidad.getUnidad().toString() + "CO2eq";
     }

}
