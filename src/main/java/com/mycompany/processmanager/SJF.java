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

    public void ejecutar(List<MenuManager.Process> processes) {
        System.out.println("Ejecutar algoritmo SJF");
        List<MenuManager.Process> remainingProcesses = new ArrayList<>(processes);
        List<MenuManager.Process> executedProcesses = new ArrayList<>();
        int currentTime = 0;

        while (!remainingProcesses.isEmpty()) {
            // Encuentra el proceso con el menor burst time entre los procesos que han llegado hasta currentTime
            MenuManager.Process shortestJob = null;
            for (MenuManager.Process process : remainingProcesses) {
                if (process.arrivalTime <= currentTime && (shortestJob == null || process.burstTime < shortestJob.burstTime)) {
                    shortestJob = process;
                }
            }

            if (shortestJob == null) {
                // Si no hay procesos que hayan llegado aún, avanza el tiempo
                currentTime++;
                continue;
            }

            // Calcula el tiempo de espera para este proceso
            int waitTime = currentTime - shortestJob.arrivalTime;
            // Calcula el tiempo de respuesta para este proceso
            int responseTime = waitTime + shortestJob.burstTime;

            // Actualiza los tiempos de espera y respuesta del proceso
            shortestJob.waitTime = waitTime;
            shortestJob.responseTime = responseTime;

            // Actualiza el tiempo actual
            currentTime += shortestJob.burstTime;

            // Añade el proceso a la lista de procesos ejecutados
            executedProcesses.add(shortestJob);

            // Elimina el proceso de la lista de procesos restantes
            remainingProcesses.remove(shortestJob);
        }

        // Reorganiza la lista original de procesos según el orden de ejecución
        processes.clear();
        processes.addAll(executedProcesses);
    }
}
