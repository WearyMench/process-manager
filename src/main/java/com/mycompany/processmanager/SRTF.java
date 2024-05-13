/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.processmanager;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amin
 */
public class SRTF implements Algoritmo {

    public void ejecutar(List<MenuManager.Process> processes, List<MenuManager.ProcessInfo> processInfoList) {
        System.out.println("Ejecutar algortimo SRTF");
        List<MenuManager.Process> remainingProcesses = new ArrayList<>(processes);
        List<MenuManager.ProcessInfo> executionOrder = new ArrayList<>();
        int currentTime = 0;

        while (!remainingProcesses.isEmpty()) {
            MenuManager.Process shortestJob = null;
            int shortestTime = Integer.MAX_VALUE;

            for (MenuManager.Process process : remainingProcesses) {
                if (process.arrivalTime <= currentTime && process.burstTime < shortestTime) {
                    shortestJob = process;
                    shortestTime = process.burstTime;
                }
            }

            if (shortestJob == null) {
                // Avanzar al siguiente tiempo de llegada
                currentTime++;
            } else {
                // Calcular el tiempo de espera para este proceso (si lo hubiera)
                int waitTime = Math.max(0, currentTime - shortestJob.arrivalTime);
                // Calcular el tiempo de respuesta para este proceso
                int responseTime = waitTime + shortestJob.burstTime;

                // Ejecutar el proceso por una unidad de tiempo
                shortestJob.burstTime--;

                // Si el proceso ha completado su ejecución, eliminarlo de la lista de procesos restantes
                if (shortestJob.burstTime == 0) {
                    remainingProcesses.remove(shortestJob);
                }

                // Añadir el proceso a la lista de ejecución
                executionOrder.add(new MenuManager.ProcessInfo(shortestJob.name, waitTime, responseTime));
                // Incrementar el tiempo actual
                currentTime++;
            }
        }
        // Copiar la lista de ejecución a processInfoList
        processInfoList.addAll(executionOrder);
    }
}
