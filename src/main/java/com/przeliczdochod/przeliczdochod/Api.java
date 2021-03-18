package com.przeliczdochod.przeliczdochod;

import com.itextpdf.text.DocumentException;
import com.przeliczdochod.przeliczdochod.pojos.TableRow;
import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class Api {

    @Autowired
    private Price_manager price_manager;

    @Autowired
    private CreatePdf createPdf;

    private List<TableRow> rowList = new ArrayList<>();



    @GetMapping("/")
    public String mainPage( Model model){

        model.addAttribute("rows",rowList);
        model.addAttribute("newRow",new TableRow());
        model.addAttribute("now_date",LocalDate.now());
        double sum =0.0;
        for(TableRow row: rowList)
        {
            sum +=row.getPLN_amount();
        }
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        Double sum_round= DoubleRounder.round((sum),3);
        model.addAttribute("sum",sum_round);

        return "mainPage";
    }
    int x=1;
    @PostMapping("/addRow")
    public String addRow( @ModelAttribute TableRow tableRow) throws InterruptedException, TransformerException, IOException {
        LocalDate date = tableRow.getDate();
        String symbol = tableRow.getSymbol();
        Double amount = tableRow.getAmount();
        LocalDate date1 ;
        Double price;
        int i =1;
            do {
                date1 = date.minusDays(i);
                price = price_manager.getPrice(symbol, date1);
                i++;
            }
            while (price == null||i>10);
       tableRow.setId(x);
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        double pln_amount= DoubleRounder.round((amount*price),3);
        tableRow.setPLN_amount(pln_amount);
        double round_price= DoubleRounder.round((price),4);
        tableRow.setExchange_rate(round_price);
        tableRow.setExchange_rate_date(date1);
        rowList.add(tableRow);
        x++;
        return "redirect:/";
    }

    @GetMapping("/deleteRow/{id}")
    public String deleteRow(@PathVariable int id)
    {
        rowList.removeIf(row -> (row.getId()==id));

        return "redirect:/";
    }
    @GetMapping("/generate_pdf")
    public void generate_pdf(HttpServletResponse response) throws IOException, URISyntaxException, DocumentException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=podsumowanie-transakcji.pdf";
        response.setHeader(headerKey,headerValue);
        createPdf.generatePdfFromHtml(rowList,response);
    }
}
