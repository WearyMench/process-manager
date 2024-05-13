/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.processmanager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Amin
 */
public class SJF implements Algoritmo {

    public void ejecutar(List<MenuManager.Process> processes, List<MenuManager.ProcessInfo> processInfoList) {
        System.out.println("Ejecutar algoritmo SJF");
        List<MenuManager.Process> remainingProcesses = new ArrayList<>(processes);
        List<MenuManager.ProcessInfo> executionOrder = new ArrayList<>();
        int currentTime = 0;

        while (!remainingProcesses.isEmpty()) {
            MenuManager.Process shortestJob = null;
            for (MenuManager.Process process : remainingProcesses) {
                if (process.arrivalTime <= currentTime && (shortestJob == null || process.burstTime < shortestJob.burstTime)) {
                    shortestJob = process;
                }
            }

            if (shortestJob == null) {
                // Avanzar el tiempo si no hay procesos llegados
                currentTime++;
                continue;
            }

            // Calcula el tiempo de espera para este proceso
            int waitTime = currentTime - shortestJob.arrivalTime;
            // Calcula el tiempo de respuesta para este proceso
            int responseTime = waitTime + shortestJob.burstTime;

            // Añadir el proceso a la lista de ejecución
            executionOrder.add(new MenuManager.ProcessInfo(shortestJob.name, waitTime, responseTime));
            // Actualizar el tiempo actual
            currentTime += shortestJob.burstTime;
            // Eliminar el proceso de la lista de procesos restantes
            remainingProcesses.remove(shortestJob);
        }
        // Copiar la lista de ejecución a processInfoList
        processInfoList.addAll(executionOrder);
    }
}
