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

    public void ejecutar(List<MenuManager.Process> processes) {
        System.out.println("Ejecutar algoritmo SRTF");
        List<MenuManager.Process> remainingProcesses = new ArrayList<>(processes);
        List<MenuManager.Process> executedProcesses = new ArrayList<>();
        int currentTime = 0;
        int[] remainingBurstTimes = new int[processes.size()];

        for (int i = 0; i < processes.size(); i++) {
            remainingBurstTimes[i] = processes.get(i).burstTime;
        }

        while (!remainingProcesses.isEmpty()) {
            MenuManager.Process shortestJob = null;
            int shortestTime = Integer.MAX_VALUE;

            for (MenuManager.Process process : remainingProcesses) {
                if (process.arrivalTime <= currentTime && remainingBurstTimes[processes.indexOf(process)] < shortestTime) {
                    shortestJob = process;
                    shortestTime = remainingBurstTimes[processes.indexOf(process)];
                }
            }

            if (shortestJob == null) {
                // Avanzar al siguiente tiempo de llegada
                currentTime++;
            } else {
                // Ejecutar el proceso por una unidad de tiempo
                remainingBurstTimes[processes.indexOf(shortestJob)]--;

                // Si el proceso ha completado su ejecución, actualizar los tiempos y eliminarlo de la lista de procesos restantes
                if (remainingBurstTimes[processes.indexOf(shortestJob)] == 0) {
                    int waitTime = currentTime + 1 - shortestJob.arrivalTime - shortestJob.burstTime;
                    int responseTime = currentTime + 1 - shortestJob.arrivalTime;

                    shortestJob.waitTime = waitTime;
                    shortestJob.responseTime = responseTime;

                    executedProcesses.add(shortestJob);
                    remainingProcesses.remove(shortestJob);
                }

                // Incrementar el tiempo actual
                currentTime++;
            }
        }

        // Reorganiza la lista original de procesos según el orden de ejecución
        processes.clear();
        processes.addAll(executedProcesses);
    }
}
