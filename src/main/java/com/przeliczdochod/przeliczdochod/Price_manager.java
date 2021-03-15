package com.przeliczdochod.przeliczdochod;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

@Service
public class Price_manager {
    private Data_layer data_layer;

    @Autowired
    public Price_manager(Data_layer data_layer) {
        this.data_layer = data_layer;
    }

     public Double getPrice(String symbol, LocalDate data) throws IOException, InterruptedException, TransformerException {
        try {
            return data_layer.getPrice(symbol, data);
        }
        catch (MismatchedInputException x)
        {
            return null;
        }
     }


}
