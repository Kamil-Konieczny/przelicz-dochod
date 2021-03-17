package com.przeliczdochod.przeliczdochod;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.przeliczdochod.przeliczdochod.pojos.TableRow;
import org.decimal4j.util.DoubleRounder;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Stream;

@Controller
public class CreatePdf {

    public Document generatePdfFromHtml(List<TableRow> list,HttpServletResponse http) throws DocumentException, IOException, URISyntaxException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document,http.getOutputStream());

        document.open();

        PdfPTable table = new PdfPTable(5);
        addTableHeader(table);
        addRows(table,list);
        Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 19, BaseColor.BLACK);
        Chunk chunk = new Chunk("Transakcje: ", font);
        document.add(chunk);
        document.add(new Paragraph("\n"));
        document.add(table);
        document.add(new Paragraph("\n\n"));
        document.add(add_sum(list));
        document.close();
        return document;
    }
    private void addTableHeader(PdfPTable table) {
        Stream.of("Waluta", "Kwota", "Kurs dnia poprzedniego","Kwota w PLN","Data transakcji")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }
    private void addRows(PdfPTable table, List<TableRow> list) {

        for(TableRow row : list)
        {
            String temp = String.valueOf(new StringBuilder("("+String.valueOf(row.getExchange_rate_date())+") ").append(row.getExchange_rate()));
            String symbol = row.getSymbol();
            String amount = String.valueOf(row.getAmount());
            String PLN_amount = String.valueOf(row.getPLN_amount());
            String date = row.getDate().toString();
            table.addCell(symbol);
            table.addCell(amount);
            table.addCell(temp);
            table.addCell(PLN_amount);
            table.addCell(date);
        }
    }
private Chunk add_sum(List<TableRow> list)
{
    Double sum=0.0;
    for(TableRow row : list) {
        sum += row.getPLN_amount();
    }
    DecimalFormat numberFormat = new DecimalFormat("#.00");
    Double sum_round= DoubleRounder.round((sum),3);
    Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 30, BaseColor.BLACK);
    Chunk chunk = new Chunk("Zysk/Strata:  "+sum_round.toString()+" PLN", font);
    return chunk;
}

}
