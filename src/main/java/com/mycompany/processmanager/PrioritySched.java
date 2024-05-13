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

    public void ejecutar(List<MenuManager.Process> processes, List<MenuManager.ProcessInfo> processInfoList) {
        System.out.println("Ejecutar algortimo Asignacion de Prioridades");
        List<MenuManager.Process> remainingProcesses = new ArrayList<>(processes);
        List<MenuManager.ProcessInfo> executionOrder = new ArrayList<>();
        int currentTime = 0;

        while (!remainingProcesses.isEmpty()) {
            // Ordenar los procesos restantes por su prioridad
            remainingProcesses.sort(Comparator.comparingInt(p -> p.priority));

            MenuManager.Process highestPriorityProcess = remainingProcesses.get(0);

            // Calcular el tiempo de espera para este proceso (si lo hubiera)
            int waitTime = Math.max(0, currentTime - highestPriorityProcess.arrivalTime);
            // Calcular el tiempo de respuesta para este proceso
            int responseTime = waitTime + highestPriorityProcess.burstTime;

            // Ejecutar el proceso por su tiempo de ráfaga
            currentTime += highestPriorityProcess.burstTime;

            // Añadir el proceso a la lista de ejecución
            executionOrder.add(new MenuManager.ProcessInfo(highestPriorityProcess.name, waitTime, responseTime));
            // Eliminar el proceso de la lista de procesos restantes
            remainingProcesses.remove(highestPriorityProcess);
        }

        // Copiar la lista de ejecución a processInfoList
        processInfoList.addAll(executionOrder);
    }
}
