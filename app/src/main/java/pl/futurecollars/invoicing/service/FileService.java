package pl.futurecollars.invoicing.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileService {

  private ObjectMapper objectMapper;

  public FileService() {
    this.objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
  }

  public void writeDataToFile(String filename, Object object) {
    try {
      objectMapper.writeValue(new File(filename), object);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public <T> List<T> getDataListFromFile(String filename, Class<T> c) {
    try {
      // nie wiemy jaki typ danych jest trzymany w pliku, wiec musimy zadeklarwać,
      // że to będzie Lista typu "c" - w naszym przypadku w klasie FileDatabase bedziemy tam przekazywali Invoice.class
      JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, c);
      return objectMapper.readValue(new File(filename), type);
    } catch (IOException e) {
      System.out.println("error while reading data from file: " + e);
    }

    return new ArrayList<T>();
  }

}
