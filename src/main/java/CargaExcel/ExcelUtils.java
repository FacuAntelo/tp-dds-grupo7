package CargaExcel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.Iterator;
//falta importar mas, agregar dependencias??



public class ExcelUtils {

        public static void leerExcel(String path)
        {
            try
            {
                FileInputStream file = new FileInputStream(new File(path));

                //Crea la instancia de Workbook con referencia a al archivo .xlsx
                XSSFWorkbook workbook = new XSSFWorkbook(file);

                //Toma la hoja de calculo deseada desde el principio
                XSSFSheet sheet = workbook.getSheetAt(0);

                //Itera fila por fila una por una
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext())
                {
                    Row row = rowIterator.next();
                    //Para cada fila itero todas las columnas
                    Iterator<Cell> cellIterator = row.cellIterator();

                    while (cellIterator.hasNext())
                    {
                        Cell cell = cellIterator.next();
                        //Chequea la celda y el formato
                        switch (cell.getCellType())
                        {
                            case NUMERIC:
                                System.out.print(cell.getNumericCellValue() + "\t");
                                break;
                            case STRING:
                                System.out.print(cell.getStringCellValue() + "\t");
                                break;
                        }
                    }
                    System.out.println("");
                }
                file.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

}


