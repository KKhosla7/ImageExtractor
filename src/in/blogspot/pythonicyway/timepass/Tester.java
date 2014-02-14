package in.blogspot.pythonicyway.timepass;

import in.blogspot.pythonicyway.timepass.slideshare.SlideShare;
import in.blogspot.pythonicyway.timepass.tunesdairy.TunesDairy;

import java.io.IOException;

/**
 * Created by karan.khosla
 */
public class Tester {


  public static void main(String[] args) throws IOException {
    TunesDairy tunesDairy = new TunesDairy();
    tunesDairy.downloadSongFromAlbum("C:\\Users\\karan.khosla\\Desktop\\Slides\\test\\", "yaariyan", "Sunny Sunny");

    SlideShare slideShare = new SlideShare();
    slideShare.getAllSlidesInList("http://www.slideshare.net/arturoherrero/clean-code-8036914?v=default&b=&from_search=5", "C:\\Users\\karan.khosla\\Desktop\\Slides\\test\\");
    slideShare.bindToPDF("C:\\Users\\karan.khosla\\Desktop\\Slides\\test\\", "thanksgiving-presentation.pdf");
  }


}
