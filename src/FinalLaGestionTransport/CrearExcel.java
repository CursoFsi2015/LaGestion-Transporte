package FinalLaGestionTransport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class CrearExcel {

	CrearExcel() {

		
		// Crear EXCEL
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Conductores");

		Row fila = sheet.createRow(0);
		File archivo = new File("C:\\LaGestion\\Conductores.xls");
		Cell celda;

		String[] titulos = { "  " };
		String[] datos = { "Nº Conductor", "Empresa", "Telefono", "Horario", "Nombre" };

		int i;

		// Creamos el encabezado
		for (i = 0; i < titulos.length; i++) {
			celda = fila.createCell(i);
			celda.setCellValue(titulos[i]);
		}

		// Nueva fila

		fila = sheet.createRow(1);
		for (i = 0; i < datos.length; i++) {
			celda = fila.createCell(i);
			celda.setCellValue(datos[i]);
		}

		// Escribimos el archivo
		if(!archivo.exists()){
			try {
				FileOutputStream out = new FileOutputStream(archivo);
				workbook.write(out);
				out.close();
	
				// System.out.println("Archivo creado exitosamente!");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Error de escritura");
				e.printStackTrace();
			}
		}
	}

	
}
