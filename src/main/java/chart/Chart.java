package chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.JFrame;

import static java.lang.Math.pow;
import static java.lang.Math.PI;


public class Chart {
    //public static void main(String[] args) {
    public static void show(){
        XYSeries series = new XYSeries("x\u00B3+4,81x²-17,37x+5,38");
        for (float i = -7.5f; i < 5; i+=0.1) {
            series.add(i, pow(i, 3) + 4.81 * pow(i, 2) - 17.37 * i + 5.38);
        }

        XYDataset xyDataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory
                .createXYLineChart("x\u00B3+4,81x²-17,37x+5,38", "x", "y",
                        xyDataset, PlotOrientation.VERTICAL,
                        true,true, true);
        JFrame frame = new JFrame("StaticChart");
        //Помещаем график на фрейм
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(800,800);
        frame.show();
    }
}
