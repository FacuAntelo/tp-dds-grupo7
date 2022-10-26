package models.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrayectoDTO {
    private int id;
    private double distanciaTotal;
    private String direccionInicioCalle;
    private int direccionInicioAltura;
    private String direccionInicioLocalidad;
    private String direccionInicioProvincia;
    private String direccionFinCalle;
    private int direccionFinAltura;
    private String direccionFinLocalidad;
    private String direccionFinProvincia;

}
