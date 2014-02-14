package http;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by karan.khosla
 */
public class SlideShare {

  private static final String USER_AGENT = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; .NET CLR 2.0.50727; MS-RTC LM 8)";

  public static void main(String[] args) throws IOException {
    SlideShare slideShare = new SlideShare();
    slideShare.getAllSlidesInList("http://www.slideshare.net/alexcheng1982/java-se-7-new-features-and-enhancements?v=default&b=&from_search=1");
  }

  private List<String> listAllSlides(String url) {
    List<String> slideURList = new ArrayList<>();
    Document htmlDocument;
    try {
      htmlDocument = Jsoup.connect(url).userAgent(USER_AGENT).get();
      Elements images = htmlDocument.getElementsByTag("img");
      for (Element image : images) {
        String content = image.attr("data-full");
        if (content != null && !content.equals(""))
          slideURList.add(content);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return slideURList;
  }

  public void getAllSlidesInList(String url) throws IOException {
    List<String> allSlides = listAllSlides(url);
    DownloadManager downloadManager = DownloadManager.getInstance();
    for (String slideURL : allSlides) {
      downloadManager.basicFileDownload(slideURL,  "C:\\Users\\karan.khosla\\Desktop\\Slides\\test\\" + fileName(slideURL));
    }
  }

  private String fileName(String slideURL) {
    return slideURL.substring(slideURL.lastIndexOf("/") + 1, slideURL.lastIndexOf("?"));
  }
}












