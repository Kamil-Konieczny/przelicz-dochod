package com.przeliczdochod.przeliczdochod;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.przeliczdochod.przeliczdochod.pojos.MyPojo;
import org.springframework.stereotype.Service;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.beans.XMLEncoder;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class Data_layer {
    public double getPrice(String symbol, LocalDate date) throws IOException, InterruptedException, TransformerException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.nbp.pl/api/exchangerates/rates/a/"+symbol+"/"+date+"/"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        MyPojo deserializedData = mapper.readValue(response.body(), MyPojo.class);

        double mid = Double.parseDouble(deserializedData.getRates()[0].getMid());
        return mid;
    }
}
