package Combustible;

import Configuracion.Configuracion;

import java.io.IOException;

public class Gasoil extends Combustible{
    @Override
    public void cargarFactorEmision() throws IOException {
        Configuracion config = new Configuracion();
        this.factorEmision= config.getConfiguracion("GASOIL");

    }
}
