package models.Configuracion;

import models.Usuarios.FactorDeEmision;

import java.io.IOException;

public class Configuracion {
    private static final String CONFIG_PATH = "factoresDeEmision.config";
    private static Configuracion config;

    public Configuracion Configuracion(){
        if(config == null){
            config = new Configuracion();
        }
        return config;
    }


    public FactorDeEmision getConfiguracion(String key) throws IOException {
//        Properties config = new Properties();
//        config.load(new FileReader(CONFIG_PATH));
//
//        String valores = config.getProperty(key);
//        String []aux2 = valores.split(" ");
//
//        FactorDeEmision factor = new FactorDeEmision();
//        factor.setFactorEmision(Integer.parseInt(aux2[0]));
//        factor.setUnidad(aux2[1]);

        return null;
    }
}
