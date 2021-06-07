package at.matthias.tourplanner.BL;

import at.matthias.tourplanner.models.LogItem;
import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class Charthandler {
  private static final Logger logger = Logger.getLogger(Charthandler.class);

  private Charthandler() {
    throw new IllegalArgumentException("Utility Class");
  }

  // generates a weather piechart
  public static JFreeChart generateWeatherChart(List<LogItem> logs) {
    logger.info("Creating Weather Chart");
    List<String> weather = new ArrayList<>();
    for (LogItem log : logs) {
      weather.add(log.getWeather()); // save weather in a seperate list
    }
    DefaultPieDataset dataSet = new DefaultPieDataset();
    Map<String, Long> counts = weather.stream().collect(Collectors.groupingBy(
        e -> e, Collectors.counting())); // count occurence of different Weather

    for (Map.Entry<String, Long> pair : counts.entrySet()) {
      dataSet.setValue(pair.getKey(), pair.getValue()); // set dataset
    }

    JFreeChart chart =
        ChartFactory.createPieChart("Weather", dataSet, true, true, false); // create chart
    chart.getPlot().setBackgroundPaint(Color.WHITE); // set background color

    return chart;
  }

  public static JFreeChart generateActivityChart(List<LogItem> logs) {
    logger.info("Creating Activity Chart");
    List<String> activity = new ArrayList<>();
    for (LogItem log : logs) {
      activity.add(log.getActivity()); // save activity in a seperate list
    }

    Map<String, Long> counts = activity.stream().collect(Collectors.groupingBy(
        e -> e, Collectors.counting())); // count occurence of different activites

    DefaultPieDataset dataSet = new DefaultPieDataset();
    for (Map.Entry<String, Long> pair : counts.entrySet()) {
      dataSet.setValue(pair.getKey(), pair.getValue()); // set dataset
    }

    JFreeChart chart =
        ChartFactory.createPieChart("Activity", dataSet, true, true, false); // create piechart
    chart.getPlot().setBackgroundPaint(Color.WHITE); // set background color

    return chart;
  }

  public static JFreeChart generateDegreesChart(List<LogItem> logs) {
    logger.info("Creating Degree Chart");
    TimeSeriesCollection dataset = new TimeSeriesCollection();
    TimeSeries series = new TimeSeries("Degrees");
    for (LogItem log : logs) {
      LocalDate ld = log.getDate();
      Day d = new Day(ld.getDayOfMonth(), ld.getMonthValue(), ld.getYear()); // Create new Day
      series.add(d, log.getDegrees()); // Day Degree pair to series
    }
    dataset.addSeries(series); // set dataset

    JFreeChart chart =
        ChartFactory.createTimeSeriesChart("Temperature over Time", // Time Series Chart
            "Date", // X-Axis Label
            "C°", // Y-Axis Label
            dataset);
    chart.getPlot().setBackgroundPaint(Color.WHITE); // set background color

    return chart;
  }
}
