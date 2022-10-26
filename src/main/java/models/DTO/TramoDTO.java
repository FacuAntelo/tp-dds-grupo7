package models.DTO;

import lombok.Getter;
import lombok.Setter;
import models.MediosDeTransporte.MediosDeTransporte;
import models.MediosDeTransporte.TipoTransporte;

import java.time.LocalTime;
@Getter
@Setter
public class TramoDTO {
    private int id;
    private TipoTransporte tipoTransporte;
    private LocalTime horaInicio;
    private String direccionInicioCalle;
    private int direccionInicioAltura;
    private String direccionInicioLocalidad;
    private String direccionInicioProvincia;
    private String direccionFinCalle;
    private int direccionFinAltura;
    private String direccionFinLocalidad;
    private String direccionFinProvincia;
}
