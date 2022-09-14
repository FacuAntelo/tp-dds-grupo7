package CargaExcel;

import Organizacion.DatosDeActividad;
import Usuarios.FactorDeEmision;
import domain.Configurador;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.sound.midi.SysexMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;


public class ExcelUtils {
    //public List<DatosDeActividad> datosDeLasActividades;


    public static boolean esEncabezadoValido(String titulo, HashMap<String, Integer> indicesDeColumas){
        return indicesDeColumas.containsKey(titulo);
    }
    //Devuelve una lista de DatosDeActividad
    public static  List<DatosDeActividad> leerExcel(String path) throws IOException {

        FileInputStream file = new FileInputStream(path);
        XSSFWorkbook hojaDeCalculo = new XSSFWorkbook(file);
        XSSFSheet hoja1 = hojaDeCalculo.getSheet("Hoja1");
        List<DatosDeActividad> datosDeLasActividades = new ArrayList<>();
        //HashMap<Integer, ArrayList> datosDeLasActividades = new HashMap<Integer, ArrayList>();
        HashMap<String, Integer> indicesDeColumna = new HashMap<String, Integer>();

        /*Inicializo indices con valores estandar*/

        indicesDeColumna.put("Actividad", 0);
        indicesDeColumna.put("Tipo de consumo", 1);
        indicesDeColumna.put("Valor", 2);
        indicesDeColumna.put("Periodicidad", 3);
        indicesDeColumna.put("Periodo de imputacion", 4);

        Integer cantidadDeEncabezados = 5;
        String titulo;
        Configurador config = Configurador.getConfigurador();
        //Creo las key segun los alcances y sus respectivas listas
        //  datosDeLasActividades.put(1, arrayListInicial1);
        //  datosDeLasActividades.put(2, arrayListInicial2);
        //datosDeLasActividades.put(3, arrayListInicial3);
            /*HashMap<Integer, String> map
                    = new HashMap<Integer, String>();*/
        HashMap<String,FactorDeEmision> factorDeEmisionHashMap = config.getFactoresDeEmision();
        for (int r = 0; r <= hoja1.getLastRowNum(); r++) {
            if (r == 0) {
                for (int i = 0; i < cantidadDeEncabezados; i++) {
                    titulo = hoja1.getRow(r).getCell(i).getStringCellValue();
                    if (ExcelUtils.esEncabezadoValido(titulo, indicesDeColumna)) {
                        indicesDeColumna.put(titulo, i);

                    } else {
                        throw new RuntimeException("error aca");
                    }
                }
            } else {
                DatosDeActividad datos = new DatosDeActividad();
                datos.setTipoDeConsumo(hoja1.getRow(r)
                        .getCell(indicesDeColumna.get("Tipo de consumo"))
                        .getStringCellValue());
                datos.setActividad(hoja1.getRow(r)
                        .getCell(indicesDeColumna.get("Actividad"))
                        .getStringCellValue());
                datos.setFactorDeEmision(factorDeEmisionHashMap.get(datos.getTipoDeConsumo().toUpperCase()));
                datos.setValor(hoja1.getRow(r)
                        .getCell(indicesDeColumna.get("Valor"))
                        .getNumericCellValue());
                datos.setPeriodicidad(hoja1.getRow(r)
                        .getCell(indicesDeColumna.get("Periodicidad"))
                        .getStringCellValue());

                datos.setPeriodoDeImputacion(hoja1.getRow(r).getCell(indicesDeColumna.get("Periodo de imputacion")).getStringCellValue());

                datosDeLasActividades.add(datos);
            }

        }

        hojaDeCalculo.close();
        file.close();
        return datosDeLasActividades;
    }

}
