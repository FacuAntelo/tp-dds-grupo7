package Combustible;

import Configuracion.Configuracion;
import jdk.nashorn.internal.runtime.regexp.joni.Config;

import java.io.IOException;

public class Diesel extends Combustible{

    @Override
    public void cargarFactorEmision() throws IOException {
        Configuracion config = new Configuracion();
        this.factorEmision= config.getConfiguracion("DIESEL");
    }
}
