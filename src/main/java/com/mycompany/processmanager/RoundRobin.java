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
        // Variables esenciales
        int currentTime = 0;
        int quantum = processes.get(0).quantum;
        Queue<MenuManager.Process> queue = new LinkedList<>();
        Map<MenuManager.Process, Integer> waitingTimeMap = new HashMap<>();
        Map<MenuManager.Process, Integer> firstExecutionTimeMap = new HashMap<>(); // Mapa para almacenar el tiempo de primera ejecución de cada proceso
        Map<MenuManager.Process, Integer> responseTimeMap = new HashMap<>();

        // Inicializar el mapa con tiempo de espera 0 para cada proceso
        for (MenuManager.Process process : processes) {
            waitingTimeMap.put(process, 0);
            firstExecutionTimeMap.put(process, -1);
            responseTimeMap.put(process, 0);
        }

        queue.addAll(processes);

        //Header del grafico a mostrar en consola.
        System.out.println("Time\tProcess");

        // Algoritmo Round Robin
        while (!queue.isEmpty()) {
            MenuManager.Process currentProcess = queue.poll();
            System.out.print(currentTime + "\t" + currentProcess.name + "\t");

            if (firstExecutionTimeMap.get(currentProcess) == -1) {
                firstExecutionTimeMap.put(currentProcess, currentTime);
            }

            if (currentProcess.remainingTime > quantum) {
                for (int i = 0; i < quantum; i++) {
                    System.out.print("*");
                }
                currentTime += quantum;
                currentProcess.remainingTime -= quantum;
                queue.add(currentProcess);
            } else {
                for (int i = 0; i < currentProcess.remainingTime; i++) {
                    System.out.print("*");
                }
                currentTime += currentProcess.remainingTime;
                currentProcess.remainingTime = 0;

                // Calcular tiempo de espera
                int waitingTime = currentTime - currentProcess.arrivalTime - currentProcess.burstTime;
                waitingTimeMap.put(currentProcess, waitingTime);

                // Actualizar tiempo de respuesta
                int firstExecutionTime = firstExecutionTimeMap.get(currentProcess);
                int responseTime = currentTime - firstExecutionTime;
                responseTimeMap.put(currentProcess, responseTime);
            }
            System.out.println();
        }

        // Actualizar los tiempos de espera y respuesta en la lista de procesos
        for (MenuManager.Process process : processes) {
            process.waitTime = waitingTimeMap.get(process);
            process.responseTime = responseTimeMap.get(process);
        }

        // Devolver la lista modificada
        /*for (MenuManager.Process process : completedProcesses) {
            processes.add(process);
        }*/
    }
}
