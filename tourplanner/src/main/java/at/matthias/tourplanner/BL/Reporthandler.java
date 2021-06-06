package at.matthias.tourplanner.BL;

import at.matthias.tourplanner.DL.XMLReader;
import at.matthias.tourplanner.models.LogItem;
import at.matthias.tourplanner.models.TourItem;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.Loader;
import org.jfree.chart.JFreeChart;

public class Reporthandler {
  private static TourItem tour;
  private static java.util.List<LogItem> logs;
  private static PdfFont fontBold;
  private static PdfFont font;
  private static final Logger logger = Logger.getLogger(Reporthandler.class);

  private Reporthandler() {
    throw new IllegalArgumentException("Utility Class");
  }

  public static void fileSummary(String path, TourItem t) {
    if (t != null && path != null) {
      if (Files.exists(Paths.get(path))) {
        if (!path.endsWith("/")) {
          logger.info("Does not end with /");
          path = path + "/";
        }
        path = path + t.getName() + "_summary.pdf";
        tour = t;
        logs = new Loghandler().get(tour.getId());

        try {
          logger.info("Creating document...");
          fontBold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
          font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
          PdfDocument pdf = new PdfDocument(new PdfWriter(path));

          Document doc = new Document(pdf);

          addTitlePage(doc);
          addImage(doc);
          addTourData(doc);
          if (logs != null && !logs.isEmpty()) {
            doc.add(new AreaBreak());
            addSpeed(doc);
            addTime(doc);
            addBreaks(doc);
            doc.add(new AreaBreak());
            doc.add(chartToImg(Charthandler.generateActivityChart(logs)));
            doc.add(new AreaBreak());
            doc.add(chartToImg(Charthandler.generateWeatherChart(logs)));
            doc.add(new AreaBreak());
            doc.add(chartToImg(Charthandler.generateDegreesChart(logs)));

            doc.close();
          }
        }

        catch (Exception e) {
          logger.error("Error creating PDF file! " + e);
        }
      } else {
        logger.error("Error creating PDF! TourItem is null");
      }
    }
  }

  public static void fileReport(String path, TourItem t) {
    if (t != null && path != null) {
      if (Files.exists(Paths.get(path))) {
        if (!path.endsWith("/")) {
          logger.info("Does not end with /");
          path = path + "/";
        }
        path = path + t.getName() + "_report.pdf";
        tour = t;
        logs = new Loghandler().get(tour.getId());

        try {
          logger.info("Creating document...");
          fontBold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
          font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
          PdfDocument pdf = new PdfDocument(new PdfWriter(path));

          Document doc = new Document(pdf);

          addTitlePage(doc);
          addImage(doc);
          addTourData(doc);
          if (logs != null && !logs.isEmpty()) {
            doc.add(new AreaBreak());
            addLogData(doc);

            doc.close();
          }
        }

        catch (Exception e) {
          logger.error("Error creating PDF file! " + e);
        }
      } else {
        logger.error("Error creating PDF! TourItem is null");
      }
    }
  }

  private static void addTitlePage(Document doc) {
    logger.info("Adding Title");
    Text title = new Text(tour.getName() + " Report").setFont(fontBold).setFontSize(18);
    Paragraph p = new Paragraph().add(title);
    p.setTextAlignment(TextAlignment.CENTER);
    doc.add(p);
    doc.add(new Paragraph(" "));
  }

  private static void addImage(Document doc) throws MalformedURLException {
    logger.info("Adding Map image");
    String imgPath = new XMLReader().getPath("imageAbsolute") + tour.getImage() + ".jpg";
    if (Files.exists(Paths.get(imgPath))) {
      ImageData data = ImageDataFactory.create(imgPath);
      Image img = new Image(data);
      doc.add(img);
      doc.add(new Paragraph(" "));
    }
  }

  private static void addTourData(Document doc) {
    logger.info("adding Tour Data");
    doc.add(new Paragraph("Name: " + tour.getName()).setFont(font).setFontSize(14));
    doc.add(new Paragraph("Start: " + tour.getStart()).setFont(font).setFontSize(14));
    doc.add(new Paragraph("End: " + tour.getEnd()).setFont(font).setFontSize(14));
    doc.add(new Paragraph("Distance: " + tour.getDescription()).setFont(font).setFontSize(14));
    doc.add(new Paragraph("Description: " + tour.getDescription()).setFont(font).setFontSize(14));
    doc.add(new Paragraph(" "));
  }

  private static void addSpeed(Document doc) {
    logger.info("adding Speed Info");
    doc.add(new Paragraph("Speed Stats").setFont(fontBold).setTextAlignment(TextAlignment.CENTER));
    doc.add(new Paragraph(" "));
    List<Float> speed = new ArrayList<>();
    for (LogItem log : logs) {
      speed.add(log.getSpeed());
    }
    Float min = Collections.min(speed);
    Float max = Collections.max(speed);
    Double avg = speed.stream().mapToDouble(d -> d).average().orElse(0.0);
    com.itextpdf.layout.element.List list = new com.itextpdf.layout.element.List()
                                                .setSymbolIndent(12)
                                                .setListSymbol("\u2022")
                                                .setFont(font)
                                                .setFontSize(14);
    list.setTextAlignment(TextAlignment.CENTER);
    ListItem item;
    item = new ListItem("Average Speed: " + avg);
    list.add(item);
    item = new ListItem("Maximum Speed: " + max);
    list.add(item);
    item = new ListItem("Minimum Speed: " + min);
    list.add(item);
    doc.add(list);
    doc.add(new Paragraph(" "));
  }

  private static void addTime(Document doc) {
    logger.info("adding Time Info");
    doc.add(new Paragraph("Time Stats").setFont(fontBold).setTextAlignment(TextAlignment.CENTER));
    doc.add(new Paragraph(" "));
    List<Integer> time = new ArrayList<>();
    for (LogItem log : logs) {
      time.add(log.getTime());
    }
    Integer min = Collections.min(time);
    Integer max = Collections.max(time);
    Double avg = time.stream().mapToDouble(d -> d).average().orElse(0.0);
    com.itextpdf.layout.element.List list = new com.itextpdf.layout.element.List()
                                                .setSymbolIndent(12)
                                                .setListSymbol("\u2022")
                                                .setFont(font)
                                                .setFontSize(14);
    ListItem item;
    list.setTextAlignment(TextAlignment.CENTER);
    item = new ListItem("Average Time (min): " + avg);
    list.add(item);
    item = new ListItem("Maximum Time (min): " + max);
    list.add(item);
    item = new ListItem("Minimum Time (min): " + min);
    list.add(item);
    doc.add(list);
    doc.add(new Paragraph(" "));
  }

  private static void addBreaks(Document doc) {
    logger.info("adding Break Info");
    doc.add(new Paragraph("Break Stats").setFont(fontBold).setTextAlignment(TextAlignment.CENTER));
    doc.add(new Paragraph(" "));
    List<Integer> breaks = new ArrayList<>();
    for (LogItem log : logs) {
      breaks.add(log.getBreaks());
    }
    Integer min = Collections.min(breaks);
    Integer max = Collections.max(breaks);
    Double avg = breaks.stream().mapToDouble(d -> d).average().orElse(0.0);
    com.itextpdf.layout.element.List list = new com.itextpdf.layout.element.List()
                                                .setSymbolIndent(12)
                                                .setListSymbol("\u2022")
                                                .setFont(font)
                                                .setFontSize(14);
    list.setTextAlignment(TextAlignment.CENTER);
    ListItem item;
    item = new ListItem("Average Breaks: " + avg);
    list.add(item);
    item = new ListItem("Maximum Breaks: " + max);
    list.add(item);
    item = new ListItem("Minimum Breaks: " + min);
    list.add(item);
    doc.add(list);
    doc.add(new Paragraph(" "));
  }

  public static Image chartToImg(JFreeChart chart) {
    logger.info("Converting chart to IMG");
    BufferedImage objBufferedImage = chart.createBufferedImage(600, 800);
    ByteArrayOutputStream bas = new ByteArrayOutputStream();
    try {
      ImageIO.write(objBufferedImage, "png", bas);
    } catch (IOException e) {
      logger.error("Error convering chart to IMG! " + e);
    }

    byte[] byteArray = bas.toByteArray();
    ImageData data = ImageDataFactory.create(byteArray);
    return new Image(data);
  }

  public static void addLogData(Document doc) {
    Table table = new Table(UnitValue.createPercentArray(9)).useAllAvailableWidth();
    Cell cell = new Cell(1, 3)
                    .add(new Paragraph("Log Table"))
                    .setFont(font)
                    .setFontSize(13)
                    .setFontColor(DeviceGray.WHITE)
                    .setBackgroundColor(DeviceGray.BLACK)
                    .setTextAlignment(TextAlignment.CENTER);
    table.addHeaderCell(cell); // Table header

    table.addCell("Date");
    table.addCell("Time");
    table.addCell("Distance in km");
    table.addCell("Rating");
    table.addCell("Speed");
    table.addCell("Breaks");
    table.addCell("Degrees");
    table.addCell("Weather");
    table.addCell("Activity");

    for (LogItem log : logs) {
      table.addCell(new Cell(1, 1).add(new Paragraph(log.getDate().toString())));
      table.addCell(new Cell(1, 1).add(new Paragraph(Integer.toString(log.getTime()))));
      table.addCell(new Cell(1, 1).add(new Paragraph(Integer.toString(log.getDistance()))));
      table.addCell(new Cell(1, 1).add(new Paragraph(Float.toString(log.getRating()))));
      table.addCell(new Cell(1, 1).add(new Paragraph(Float.toString(log.getSpeed()))));
      table.addCell(new Cell(1, 1).add(new Paragraph(Integer.toString(log.getBreaks()))));
      table.addCell(new Cell(1, 1).add(new Paragraph(Integer.toString(log.getDegrees()))));
      table.addCell(new Cell(1, 1).add(new Paragraph(log.getWeather())));
      table.addCell(new Cell(1, 1).add(new Paragraph(log.getActivity())));
    }

    doc.add(table);
  }
}
