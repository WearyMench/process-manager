/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.processmanager;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 *
 * @author Amin
 */
public class RoundRobin implements Algoritmo {

    @Override
    public void ejecutar(List<MenuManager.Process> processes) {
        int currentTime = 0;
        int quantum = processes.get(0).quantum;
        Queue<MenuManager.Process> queue = new LinkedList<>();
        Map<MenuManager.Process, Integer> waitingTimeMap = new HashMap<>();
        Map<MenuManager.Process, Integer> firstExecutionTimeMap = new HashMap<>();
        Map<MenuManager.Process, Integer> responseTimeMap = new HashMap<>();

        for (MenuManager.Process process : processes) {
            waitingTimeMap.put(process, 0);
            firstExecutionTimeMap.put(process, -1);
            responseTimeMap.put(process, 0);
        }

        queue.addAll(processes);

        System.out.println("Tiempo | Proceso");
        System.out.println("----------------");

        while (!queue.isEmpty()) {
            MenuManager.Process currentProcess = queue.poll();
            if (firstExecutionTimeMap.get(currentProcess) == -1) {
                firstExecutionTimeMap.put(currentProcess, currentTime);
            }

            StringBuilder progressBar = new StringBuilder();
            progressBar.append(currentTime);
            progressBar.append("\t| ");
            progressBar.append(currentProcess.name);

            if (currentProcess.remainingTime > quantum) {
                for (int i = 0; i < quantum; i++) {
                    progressBar.append("*");
                }
                currentTime += quantum;
                currentProcess.remainingTime -= quantum;
                queue.add(currentProcess);
            } else {
                for (int i = 0; i < currentProcess.remainingTime; i++) {
                    progressBar.append("*");
                }
                currentTime += currentProcess.remainingTime;
                currentProcess.remainingTime = 0;

                int waitingTime = currentTime - currentProcess.arrivalTime - currentProcess.burstTime;
                waitingTimeMap.put(currentProcess, waitingTime);

                int firstExecutionTime = firstExecutionTimeMap.get(currentProcess);
                int responseTime = currentTime - firstExecutionTime;
                responseTimeMap.put(currentProcess, responseTime);
            }
            System.out.println(progressBar.toString());
        }

        for (MenuManager.Process process : processes) {
            process.waitTime = waitingTimeMap.get(process);
            process.responseTime = responseTimeMap.get(process);
        }
    }
}

