package com.acueducto.backend.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

public class Utils {
	
	
	public static final String ALL_SUBSCRIBERS_REPORT_TEMPLATE = "reporte_todos_suscriptores.jrxml";
	public static final String SUBSCRIBERS_WITH_DEBT_ARREARS_REPORT_TEMPLATE = "reporte_suscriptores_en_mora.jrxml";
	public static final String INVOICES_PER_BILLED_PERIOD_REPORT_TEMPLATE= "reporte_recaudo_periodo_facturado.jrxml";

	
	public static void printCellValue(Cell cell) {
	    switch (cell.getCellTypeEnum()) {
	        case BOOLEAN:
	            System.out.print(cell.getBooleanCellValue());
	            break;
	        case STRING:
	            System.out.print(cell.getRichStringCellValue().getString());
	            break;
	        case NUMERIC:
	            if (DateUtil.isCellDateFormatted(cell)) {
	                System.out.print(cell.getDateCellValue());
	            } else {
	                System.out.print(cell.getNumericCellValue());
	            }
	            break;
	        case FORMULA:
	            System.out.print(cell.getCellFormula());
	            break;
	        case BLANK:
	            System.out.print("");
	            break;
	        default:
	            System.out.print("");
	    }

	    System.out.print("\t");
	}
	
	
	public static final LocalDate toLocalDate(Date date) {
		return date.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
}
