package br.com.springboot.api.service.relatorios;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.springboot.api.export.ExportExcel;
import br.com.springboot.api.model.Pessoa;
import br.com.springboot.api.service.PessoaService;

@Service
public class PessoaExportService {

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private ExportExcel excel;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public void exportarRelatorioExcel(HttpServletResponse response) {
		HSSFWorkbook workbook = (HSSFWorkbook) excel.criarWorkbook();
		HSSFSheet sheet = excel.criarWorkSheet(workbook);
		HttpServletResponse hsr = excel.gerarCabecalho(response);
		iterarListaParaPopularCelulasExcel(workbook, pessoaService.listarPessoasExistentes(), sheet, hsr);
	}

	private void iterarListaParaPopularCelulasExcel(HSSFWorkbook workbook, List<Pessoa> listaPessoas, HSSFSheet sheet, HttpServletResponse response) {
		int linhas = 0;
		for (Pessoa pessoa : listaPessoas) {
			HSSFRow row = excel.criarNovaLinha(linhas++, sheet);
			int celulas = 0;
			HSSFCell celula1 = excel.criarNovaCelula(celulas++, row);
			celula1.setCellValue(pessoa.getId());
			HSSFCell celula2 = excel.criarNovaCelula(celulas++, row);
			celula2.setCellValue(pessoa.getNome());
			HSSFCell celula3 = excel.criarNovaCelula(celulas++, row);
			celula3.setCellValue(pessoa.getIdade());
		}
		try {
			ServletOutputStream sos = response.getOutputStream();
			workbook.write(sos);
			response.flushBuffer();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

}
