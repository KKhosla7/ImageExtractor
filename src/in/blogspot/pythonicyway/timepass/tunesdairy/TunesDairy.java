package in.blogspot.pythonicyway.timepass.tunesdairy;

import in.blogspot.pythonicyway.timepass.http.DownloadManager;

import java.io.IOException;
import java.util.Map;

/**
 * Created by karan.khosla
 */
public class TunesDairy {

  public void downloadSongFromAlbum(String albumName, String songName) {
    try {
      DownloadManager downloadManager = DownloadManager.getInstance();
      String rawJSONData = downloadManager.downloadHTMLFromURL("http://www.tunesdiary.com/search?type=music&q=none&directsearch=true&albumid=" + albumName);
      TunesDairyJSONParser parsed = new TunesDairyJSONParser();
      Map<String, String> parsedJSONResultMap = parsed.parseJSONToMap(rawJSONData);

      if (songName != null && !songName.equals("")) {
        for (Map.Entry<String, String> parsedJSONResult : parsedJSONResultMap.entrySet()) {
          if (songName.equalsIgnoreCase(parsedJSONResult.getValue()))
            downloadManager.basicFileDownload(buildURL(parsedJSONResult), buildDestination(parsedJSONResult));
        }
      } else {
        for (Map.Entry<String, String> parsedJSONResult : parsedJSONResultMap.entrySet()) {
          downloadManager.basicFileDownload(buildURL(parsedJSONResult), buildDestination(parsedJSONResult));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String buildDestination(Map.Entry<String, String> parsedJSONResult) {
    return "C:\\Users\\karan.khosla\\Desktop\\Slides\\test\\" + parsedJSONResult.getValue()+ ".mp3";
  }

  private String buildURL(Map.Entry<String, String> parsedJSONResult) {
    return "http://cdn.tunesdiary.com/static/" + parsedJSONResult.getKey() + ".mp3";
  }
}
