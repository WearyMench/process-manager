/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.processmanager;

import java.text.SimpleDateFormat;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.util.*;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.data.gantt.Task;

/**
 *
 * @author Amin
 */
public class ganttChart extends ApplicationFrame {

    public ganttChart(String title, List<MenuManager.Process> processes) {
        super(title);

        List<org.jfree.data.gantt.Task> tasks = generateTasks(processes);

        JFreeChart chart = createChart(createDataset(tasks));
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private List<Task> generateTasks(List<MenuManager.Process> processes) {
        List<Task> tasks = new ArrayList<>();
        long currentTime = 0;
        for (MenuManager.Process p : processes) {
            long taskStartTime = Math.max(currentTime, p.arrivalTime * 1000L); // convert to milliseconds
            tasks.add(new Task(
                    p.name,
                    new SimpleTimePeriod(taskStartTime, taskStartTime + p.burstTime * 1000L) // convert to milliseconds
            ));
            currentTime = taskStartTime + p.burstTime * 1000L; // convert to milliseconds
        }
        return tasks;
    }

    private IntervalCategoryDataset createDataset(List<org.jfree.data.gantt.Task> tasks) {
        TaskSeriesCollection dataset = new TaskSeriesCollection();
        TaskSeries series = new TaskSeries("Processes");
        for (org.jfree.data.gantt.Task task : tasks) {
            series.add(task);
        }
        dataset.add(series);
        return dataset;
    }

    private JFreeChart createChart(IntervalCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createGanttChart(
                "Gantt Chart",
                "Processes",
                "Time (seconds)",
                dataset,
                true,
                true,
                false
        );

        // Configura el formato del eje de tiempo
        DateAxis dateAxis = (DateAxis) chart.getCategoryPlot().getRangeAxis();
        dateAxis.setDateFormatOverride(new SimpleDateFormat("ss"));
        dateAxis.setTickUnit(new DateTickUnit(DateTickUnitType.SECOND, 1));
        return chart;
    }
}
