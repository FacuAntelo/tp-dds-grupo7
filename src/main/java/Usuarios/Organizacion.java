package Usuarios;

import CargaExcel.ExcelUtils;

import java.nio.file.Path;
import java.time.LocalDate;

public class Organizacion {

    public String actividad;
    public String tipoDeConsumo;
    public String valor;
    public String periodicidad;
    public LocalDate localDate;

    public void cargarExcel(String path) {
        ExcelUtils.leerExcel(path);
    }
}

