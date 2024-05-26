/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.processmanager;

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

    private List<org.jfree.data.gantt.Task> generateTasks(List<MenuManager.Process> processes) {

        List<org.jfree.data.gantt.Task> tasks = new ArrayList<>();
        int currentTime = 0;
        for (MenuManager.Process p : processes) {
            int taskStartTime = Math.max(currentTime, p.arrivalTime);
            tasks.add(new org.jfree.data.gantt.Task(
                    p.name,
                    new SimpleTimePeriod(taskStartTime, taskStartTime + p.burstTime)
            ));
            currentTime = taskStartTime + p.burstTime;
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
        return ChartFactory.createGanttChart(
                "Gantt Chart",
                "Processes",
                "Time",
                dataset,
                true,
                true,
                false
        );
    }

}
