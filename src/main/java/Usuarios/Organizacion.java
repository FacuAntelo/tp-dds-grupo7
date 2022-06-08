package Usuarios;

import CargaExcel.ExcelUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;

public class Organizacion {

    public String actividad;
    public String tipoDeConsumo;
    public String valor;
    public String periodicidad;
    public LocalDate localDate;

    public void cargarExcel(String path) throws IOException {
        ExcelUtils.leerExcel(path);
    }
}

