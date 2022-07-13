package Combustible;

import Configuracion.Configuracion;

import java.io.IOException;

public class GNC extends Combustible {
    @Override
    public void cargarFactorEmision() throws IOException {
        Configuracion config = new Configuracion();
        this.factorEmision = config.getConfiguracion("GNC");

    }
}
