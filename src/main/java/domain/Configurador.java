package domain;

import Usuarios.FactorDeEmision;

import java.util.HashMap;

public class Configurador {
    private static Configurador instancia = null;
    private HashMap<String,FactorDeEmision> factoresDeEmision = new HashMap<>();


    public static Configurador getConfigurador(){
        if(instancia == null){
            instancia = new Configurador();
            return instancia;
        }
        return instancia;
    }


    public void agregarFactor(String nombre, FactorDeEmision factor){
        factoresDeEmision.put(nombre,factor);
    }
    public HashMap<String,FactorDeEmision> getFactoresDeEmision(){
        return factoresDeEmision;
    }
}
