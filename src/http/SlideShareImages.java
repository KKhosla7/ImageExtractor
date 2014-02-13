package http;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by karan.khosla
 */
public class SlideShareImages {

  private static final SlideShareImages INSTANCE = new SlideShareImages();
  private List<String> slideLinks = new ArrayList<>();

  private SlideShareImages() {
  }

  public static SlideShareImages getInstance() {
    return INSTANCE;
  }

  public static void main(String[] args) {
    SlideShareImages dm = SlideShareImages.getInstance();
    // dm.slidesFinder("http://www.slideshare.net/jeremygoillot/thankgiving-prsentation");
    dm.downloadAlbum("gunday_2014");
    // BasicPDFWriter.getInstance().writeImages(dm.getSlideLinks(), BasicPDFWriter.Orientation.LANDSCAPE, "C:\\Users\\karan.khosla\\Desktop\\Slides\\test\\testLAND.pdf");
  }

  private void downloadAlbum(String albumName) {
    try {
      HttpURLConnection connection = getConnection("http://www.tunesdiary.com/search?type=music&q=none&directsearch=true&albumid=" + albumName);
      printServerTime(connection);
      InputStream inputStream = connection.getInputStream();
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
      String s;
      StringBuilder jsonBuilder = new StringBuilder();
      while ((s = bufferedReader.readLine()) != null) {
        jsonBuilder.append(s);
      }

      JsonFactory jsonFactory = new JsonFactory();
      JsonParser jsonFactoryParser = jsonFactory.createParser(jsonBuilder.toString());
      List<String> songId = new ArrayList<>();
      List<String> name = new ArrayList<>();

      while (jsonFactoryParser.nextToken() != JsonToken.END_ARRAY) {
        String field = jsonFactoryParser.getCurrentName();

        if ("songid".equalsIgnoreCase(field)) {
          jsonFactoryParser.nextToken();
          songId.add(jsonFactoryParser.getText());
        }

        if ("name".equalsIgnoreCase(field)) {
          jsonFactoryParser.nextToken();
          name.add(jsonFactoryParser.getText());
        }
      }
      for (int i = 0; i<songId.size(); i++)
        downloadSong(songId.get(i), name.get(i));


    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void downloadSong(String id, String name) {
    DataInputStream inputStream = null;
    DataOutputStream outputStream = null;
    HttpURLConnection connection = getConnection("http://cdn.tunesdiary.com/static/" + id + ".mp3");
    try {
      System.out.println("dow");
      inputStream = new DataInputStream(connection.getInputStream());
      outputStream = new DataOutputStream(new FileOutputStream("C:\\Users\\karan.khosla\\Desktop\\Slides\\test\\" + name + ".mp3", false));
      int bytesRead;
      while ((bytesRead = inputStream.read()) != -1)
        outputStream.write(bytesRead);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      connection.disconnect();
      safeClose(inputStream);
      safeClose(outputStream);
      System.out.printf("Downloaded { - %s.mp3 - }%n", name);
    }
  }

  private HttpURLConnection getConnection(final String url) {
    HttpURLConnection connection = null;
    try {
      connection = (HttpURLConnection) new URL(url).openConnection();
      setBasicHTTPConnectionParameters(connection);
      connection.connect();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return connection;
  }

  private void setBasicHTTPConnectionParameters(HttpURLConnection connection) {
    connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; .NET CLR 2.0.50727; MS-RTC LM 8)");
    connection.setInstanceFollowRedirects(true);
    connection.setUseCaches(false);
    connection.setDoInput(true);
    connection.setDoOutput(true);
  }

  public void slidesFinder(String url) {
    try {
      HttpURLConnection connection = getConnection(url);
      printServerTime(connection);
      InputStream inputStream = connection.getInputStream();
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
      String s;
      while ((s = bufferedReader.readLine()) != null)
        if (s.contains("slide_image"))
          slideLinks.add(s.substring(s.lastIndexOf("http://"), s.lastIndexOf(".jpg") + 4));
      System.out.println("No. of Slides: " + slideLinks.size());
      downloadSlideImages(slideLinks);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void download(String url) {
    DataInputStream inputStream = null;
    DataOutputStream outputStream = null;
    HttpURLConnection connection = getConnection(url);
    try {
      System.out.println("dow");
      inputStream = new DataInputStream(connection.getInputStream());
      outputStream = new DataOutputStream(new FileOutputStream("C:\\Users\\karan.khosla\\Desktop\\Slides\\test\\" + fileName(url), false));
      int bytesRead;
      while ((bytesRead = inputStream.read()) != -1)
        outputStream.write(bytesRead);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      connection.disconnect();
      safeClose(inputStream);
      safeClose(outputStream);
      System.out.printf("Downloaded { - %s - }%n", fileName(url));
    }
  }

  private void printServerTime(HttpURLConnection connection) {
    SimpleDateFormat serverDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a z");
    System.out.println("DateTime: " + serverDateFormat.format(new Date(connection.getDate())));
  }

  private void downloadSlideImages(List<String> slideLinks) {
    System.out.println("Starting Download...");
    for (String url : slideLinks)
      download(url);
  }

  private void safeClose(Closeable closeable) {
    try {
      closeable.close();
    } catch (IOException ignored) {
    }
  }

  private String fileName(String url) {
    return url.substring(url.lastIndexOf("/") + 1);
  }

  public List<String> getSlideLinks() {
    return slideLinks;
  }
}