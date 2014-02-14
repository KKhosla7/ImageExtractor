package in.blogspot.pythonicyway.timepass.slideshare;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by karan.khosla
 */

public class BasicPDFWriter {

  private static final BasicPDFWriter INSTANCE = new BasicPDFWriter();

  private BasicPDFWriter() {
  }

  public static BasicPDFWriter getInstance() {
    return INSTANCE;
  }


  public void writeImages(List<String> localDiskImagePathList, Orientation orientation, String fileName) {
    Document pdfDocument = new Document(orientation.getOrientation());
    try {
      PdfWriter.getInstance(pdfDocument, new FileOutputStream(fileName, false));
      pdfDocument.open();
      Image diskImage;
      for (String localDiskImagePath : localDiskImagePathList) {
        diskImage = Image.getInstance(localDiskImagePath);
        diskImage.setAbsolutePosition(0, 0);
        diskImage.scaleToFit(PageSize.A4.rotate().getWidth(), PageSize.A4.rotate().getRight());
        pdfDocument.add(diskImage);
        pdfDocument.newPage();
      }
      pdfDocument.close();
    } catch (DocumentException | IOException e) {
      e.printStackTrace();
    }
  }


  protected enum Orientation {
    PORTRAIT(PageSize.A4), LANDSCAPE((PageSize.A4).rotate());
    private Rectangle orientation;

    private Orientation(Rectangle orientation) {
      this.orientation = orientation;
    }

    public Rectangle getOrientation() {
      return orientation;
    }
  }


}
