package pl.futurecollars.invoicing.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import pl.futurecollars.invoicing.model.Invoice;

public class JsonService {

  public static String getJsonFromObject(Object object){
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      //todo: handle exception
      throw new RuntimeException(e);
    }
  }

  public static void saveJsonFile(String content, String filename){
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      objectMapper.writeValue(new File( filename), content);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static Object getObjectFromJson(String filename, Class T){
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readValue(new File (filename), new TypeReference<List<Invoice>>(){});
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
