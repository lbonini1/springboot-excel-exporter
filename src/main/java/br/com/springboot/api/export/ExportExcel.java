package br.com.springboot.api.export;

import java.time.LocalDate;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class ExportExcel{

	private String arquivo = criarArquivoExcel();

	public HttpServletResponse gerarCabecalho(HttpServletResponse response) {
		response.addHeader("Content-Disposition", "attachment;filename=\""+this.arquivo+"\"");
		response.setContentType("application/vnd-ms.excel");
		return response;
	}

	public HSSFWorkbook criarWorkbook() {
		return new HSSFWorkbook();
	}

	public HSSFSheet criarWorkSheet(HSSFWorkbook workbook) {
		return (HSSFSheet) workbook.createSheet("Relatorio "+LocalDate.now());
	}

	public HSSFRow criarNovaLinha(int linhas, HSSFSheet sheet) {
		return (HSSFRow) sheet.createRow(linhas);
	}

	public HSSFCell criarNovaCelula(int celulas, HSSFRow row) {
		return (HSSFCell) row.createCell(celulas);
	}

	private String criarArquivoExcel() {
		return "RelatorioExcel.xls";
	}

}
