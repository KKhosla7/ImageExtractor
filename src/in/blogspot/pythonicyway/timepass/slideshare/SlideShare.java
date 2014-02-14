package in.blogspot.pythonicyway.timepass.slideshare;

import in.blogspot.pythonicyway.timepass.http.DownloadManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by karan.khosla
 */
public class SlideShare {

  private List<String> allSlides;

  private static final String USER_AGENT = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; .NET CLR 2.0.50727; MS-RTC LM 8)";

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

  public void getAllSlidesInList(String url, String fileLocation) throws IOException {
    allSlides = listAllSlides(url);
    DownloadManager downloadManager = DownloadManager.getInstance();
    for (String slideURL : allSlides) {
      if (!new File(fileLocationOnDisk(fileLocation, slideURL)).exists())
        downloadManager.basicFileDownload(slideURL, fileLocationOnDisk(fileLocation, slideURL));
    }
  }

  private String fileLocationOnDisk(String fileLocation, String slideURL) {
    return fileLocation + fileName(slideURL);
  }

  private String fileName(String slideURL) {
    return slideURL.substring(slideURL.lastIndexOf("/") + 1, slideURL.lastIndexOf("?"));
  }

  public void bindToPDF(String fileLocation, String pdfName) {
    List<String> imagesOnDisk = new ArrayList<>();
    for (String slideURL : allSlides) {
      imagesOnDisk.add(fileLocationOnDisk(fileLocation, slideURL));
    }
    BasicPDFWriter.getInstance().writeImages(imagesOnDisk, BasicPDFWriter.Orientation.LANDSCAPE, fileLocation + pdfName);
  }
}












