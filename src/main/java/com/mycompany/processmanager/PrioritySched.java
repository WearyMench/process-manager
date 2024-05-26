/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.processmanager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Amin
 */
public class PrioritySched implements Algoritmo {

    public void ejecutar(List<MenuManager.Process> processes) {
        System.out.println("Ejecutar algoritmo Asignacion de Prioridades");
        List<MenuManager.Process> remainingProcesses = new ArrayList<>(processes);
        List<MenuManager.Process> executedProcesses = new ArrayList<>();
        int currentTime = 0;

        while (!remainingProcesses.isEmpty()) {
            // Filtrar los procesos que han llegado hasta el currentTime
            List<MenuManager.Process> arrivedProcesses = new ArrayList<>();
            for (MenuManager.Process process : remainingProcesses) {
                if (process.arrivalTime <= currentTime) {
                    arrivedProcesses.add(process);
                }
            }

            if (arrivedProcesses.isEmpty()) {
                // Si no hay procesos que hayan llegado aún, avanza el tiempo
                currentTime++;
                continue;
            }

            // Ordenar los procesos que han llegado por su prioridad
            arrivedProcesses.sort(Comparator.comparingInt(p -> p.priority));

            MenuManager.Process highestPriorityProcess = arrivedProcesses.get(0);

            // Calcular el tiempo de espera para este proceso (si lo hubiera)
            int waitTime = Math.max(0, currentTime - highestPriorityProcess.arrivalTime);
            // Calcular el tiempo de respuesta para este proceso
            int responseTime = waitTime + highestPriorityProcess.burstTime;

            // Ejecutar el proceso por su tiempo de ráfaga
            currentTime += highestPriorityProcess.burstTime;

            // Asignar valores calculados
            highestPriorityProcess.responseTime = responseTime;
            highestPriorityProcess.waitTime = waitTime;

            // Añadir el proceso a la lista de procesos ejecutados
            executedProcesses.add(highestPriorityProcess);

            // Eliminar el proceso de la lista de procesos restantes
            remainingProcesses.remove(highestPriorityProcess);
        }

        // Reorganiza la lista original de procesos según el orden de ejecución
        processes.clear();
        processes.addAll(executedProcesses);
    }
}
