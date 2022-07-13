package Combustible;

import Configuracion.Configuracion;

import java.io.IOException;

public class Nafta extends Combustible{
    @Override
    public void cargarFactorEmision() throws IOException {
        Configuracion config = new Configuracion();
        this.factorEmision= config.getConfiguracion("NAFTA");
    }
}
