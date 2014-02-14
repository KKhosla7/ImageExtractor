package in.blogspot.pythonicyway.timepass.tunesdairy;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by karan.khosla
 */
public class TunesDairyJSONParser {

  public Map<String, String> parseJSONToMap(String jsonData) {
    JsonFactory jsonFactory = new JsonFactory();
    JsonParser parser;
    Map<String, String> songsMap = new LinkedHashMap<>();
    String songID = "", songName = "";
    try {
      parser = jsonFactory.createParser(jsonData);
      while (parser.nextToken() != JsonToken.END_ARRAY) {
        String field = parser.getCurrentName();
        if ("songid".equalsIgnoreCase(field)) {
          parser.nextToken();
          songID = parser.getText();
        }
        if ("name".equalsIgnoreCase(field)) {
          parser.nextToken();
          songName = parser.getText();
        }
        if (!songID.equals("") && !songName.equals(""))
          songsMap.put(songID, songName);
      }
    } catch (IOException e) {
      System.out.println("Error Parsing JSON Data.\n" + e.getMessage());
    }
    return songsMap;
  }
}
