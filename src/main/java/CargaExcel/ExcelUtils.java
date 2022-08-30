package CargaExcel;

import Organizacion.DatosDeActividad;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
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
        indicesDeColumna.put("Tipo de Consumo", 1);
        indicesDeColumna.put("Unidad", 2);
        indicesDeColumna.put("Alcance", 3);
        indicesDeColumna.put("Valor", 4);
        indicesDeColumna.put("Periodicidad", 5);
        indicesDeColumna.put("Periodo de imputacion", 6);

        Integer cantidadDeEncabezados = 7, alcance;
        String titulo;
        //Creo las key segun los alcances y sus respectivas listas
        ArrayList arrayListInicial1 = new ArrayList<>();
        ArrayList arrayListInicial2 = new ArrayList<>();
        ArrayList arrayListInicial3 = new ArrayList<>();
        //  datosDeLasActividades.put(1, arrayListInicial1);
        //  datosDeLasActividades.put(2, arrayListInicial2);
        //datosDeLasActividades.put(3, arrayListInicial3);
            /*HashMap<Integer, String> map
                    = new HashMap<Integer, String>();*/

        for (int r = 0; r <= hoja1.getLastRowNum(); r++) {
            if (r == 0) {
                for (int i = 0; i < cantidadDeEncabezados; i++) {
                    titulo = hoja1.getRow(r).getCell(i).getStringCellValue();
                    if (ExcelUtils.esEncabezadoValido(titulo, indicesDeColumna)) {
                        indicesDeColumna.put(titulo, i);

                    } else {
                        //errorPorTituloInvalido TODO
                    }
                }
                System.out.println("Indices:\t" + indicesDeColumna);
            } else {
                DatosDeActividad datos = new DatosDeActividad();

                datos.alcance = hoja1.getRow(r)
                        .getCell(indicesDeColumna.get("Alcance"))
                        .getNumericCellValue();

                datos.actividad = hoja1.getRow(r)
                        .getCell(indicesDeColumna.get("Actividad"))
                        .getStringCellValue();
                datos.tipoDeConsumo = hoja1.getRow(r)
                        .getCell(indicesDeColumna.get("Tipo de Consumo"))
                        .getStringCellValue();
                datos.unidad = hoja1.getRow(r)
                        .getCell(indicesDeColumna.get("Unidad"))
                        .getStringCellValue();
                datos.valor = hoja1.getRow(r)
                        .getCell(indicesDeColumna.get("Valor"))
                        .getNumericCellValue();
                datos.periodicidad = hoja1.getRow(r)
                        .getCell(indicesDeColumna.get("Periodicidad"))
                        .getStringCellValue();

                datos.periodoDeImputacion = hoja1.getRow(r)
                        .getCell(indicesDeColumna.get("Periodo de imputacion"))
                        .getStringCellValue();

                datosDeLasActividades.add(datos);



            }

        }

        System.out.println(datosDeLasActividades);

        hojaDeCalculo.close();
        file.close();
        return datosDeLasActividades;
    }

}
