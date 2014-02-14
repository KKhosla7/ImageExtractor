package in.blogspot.pythonicyway.timepass.tunesdairy;

import in.blogspot.pythonicyway.timepass.http.DownloadManager;

import java.util.Map;

/**
 * Created by karan.khosla
 */
public class TunesDairy {

  public void downloadSongFromAlbum(String fileLocation, String albumName, String songName) {
    DownloadManager downloadManager = DownloadManager.getInstance();
    String url = "http://www.tunesdiary.com/search?type=music&q=none&directsearch=true&albumid=" + albumName;
    String rawJSONData = downloadManager.downloadHTMLFromURL(url);
    TunesDairyJSONParser parsed = new TunesDairyJSONParser();
    Map<String, String> parsedJSONResultMap = parsed.parseJSONToMap(rawJSONData);

    if (songName != null && !songName.equals("")) {
      for (Map.Entry<String, String> parsedJSONResult : parsedJSONResultMap.entrySet()) {
        if (songName.equalsIgnoreCase(parsedJSONResult.getValue()))
          downloadManager.basicFileDownload(buildURL(parsedJSONResult), buildDestination(fileLocation, parsedJSONResult));
      }
    } else {
      for (Map.Entry<String, String> parsedJSONResult : parsedJSONResultMap.entrySet()) {
        downloadManager.basicFileDownload(buildURL(parsedJSONResult), buildDestination(fileLocation, parsedJSONResult));
      }
    }
  }

  private String buildDestination(String fileLocation, Map.Entry<String, String> parsedJSONResult) {
    return fileLocation + parsedJSONResult.getValue() + ".mp3";
  }

  private String buildURL(Map.Entry<String, String> parsedJSONResult) {
    return "http://cdn.tunesdiary.com/static/" + parsedJSONResult.getKey() + ".mp3";
  }
}
