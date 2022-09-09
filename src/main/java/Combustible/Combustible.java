package Combustible;

import Usuarios.FactorDeEmision;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;


@Getter
@Setter
public class Combustible {
    private String nombre;
    private FactorDeEmision factorEmision;
    private int consumoCombustiblexKM;

    public Combustible(String nombre){
        this.nombre = nombre;
    }
}
