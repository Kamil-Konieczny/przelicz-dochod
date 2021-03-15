package com.przeliczdochod.przeliczdochod;

import com.lowagie.text.DocumentException;
import com.przeliczdochod.przeliczdochod.pojos.TableRow;
import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class Api {

    @Autowired
    private Price_manager price_manager;


    private CreatePdf createPdf;

    List<TableRow> rowList = new ArrayList<>();



    @GetMapping("/mainPage")
    public String mainPage( Model model){

        model.addAttribute("rows",rowList);
        model.addAttribute("newRow",new TableRow());
        model.addAttribute("now_date",LocalDate.now());
        double sum =0.0;
        for(TableRow row: rowList)
        {
            sum +=row.getPLN_amount();
        }
        model.addAttribute("sum",sum);

        return "mainPage";
    }
    int x=1;
    @PostMapping("/addRow")
    public String addRow( @ModelAttribute("tableRow") TableRow tableRow) throws InterruptedException, TransformerException, IOException {
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
        return "redirect:/mainPage";
    }

    @GetMapping("/deleteRow/{id}")
    public String deleteRow(@PathVariable int id)
    {
        rowList.removeIf(row -> (row.getId()==id));

        return "redirect:/mainPage";
    }
    @GetMapping("/generate_pdf")
    public void generate_pdf() throws IOException, DocumentException {
        String str = "cos tam cos tam";
        createPdf.generatePdfFromHtml(str);
    }
}
