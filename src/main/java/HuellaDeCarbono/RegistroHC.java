package HuellaDeCarbono;

import java.time.LocalDate;

public class RegistroHC {
    private LocalDate fecha;
    private HuellaDeCarbono valorHCDatoActividad;
    private HuellaDeCarbono valorHCTrayecto;
    private HuellaDeCarbono valorHCTotal;

    public RegistroHC(HuellaDeCarbono valorHCDatoActividad, HuellaDeCarbono valorHCTrayecto){
        this.valorHCDatoActividad= valorHCDatoActividad;
        this.valorHCTrayecto= valorHCTrayecto;
     }

}
