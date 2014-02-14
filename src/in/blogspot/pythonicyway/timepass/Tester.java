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
    tunesDairy.downloadSongFromAlbum("gunday_2014", "Tune Maari Entriyaan");

    SlideShare slideShare = new SlideShare();
    slideShare.getAllSlidesInList("http://www.slideshare.net/juliuca.92/thanksgiving-presentation-837867?v=qf1&b=&from_search=2");
    slideShare.bindToPDF("C:\\Users\\karan.khosla\\Desktop\\Slides\\test\\thanksgiving-presentation.pdf");
  }



}
