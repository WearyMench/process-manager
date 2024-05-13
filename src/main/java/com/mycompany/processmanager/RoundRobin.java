/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.processmanager;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Amin
 */
public class RoundRobin implements Algoritmo {

    public void ejecutar(List<MenuManager.Process> processes, List<MenuManager.ProcessInfo> processInfoList) {
        System.out.println("Ejecutar algortimo Quantum");

        Queue<MenuManager.Process> readyQueue = new ArrayDeque<>(processes);
        List<MenuManager.ProcessInfo> executionOrder = new ArrayList<>();
        int currentTime = 0;

        while (!readyQueue.isEmpty()) {
            MenuManager.Process currentProcess = readyQueue.poll();

            // Calcular el tiempo de espera para este proceso (si lo hubiera)
            int waitTime = Math.max(0, currentTime - currentProcess.arrivalTime);
            // Calcular el tiempo de respuesta para este proceso
            int responseTime = waitTime + currentProcess.burstTime;

            // Ejecutar el proceso durante el quantum o hasta que termine su ráfaga, lo que ocurra primero
            int remainingTime = Math.min(currentProcess.burstTime, currentProcess.quantum);
            currentTime += remainingTime;

            // Reducir el tiempo restante de ráfaga del proceso
            currentProcess.burstTime -= remainingTime;

            // Si el proceso aún tiene ráfaga pendiente, vuelve a agregarlo a la cola de listos
            if (currentProcess.burstTime > 0) {
                readyQueue.offer(currentProcess);
            }

            // Añadir el proceso a la lista de ejecución
            executionOrder.add(new MenuManager.ProcessInfo(currentProcess.name, waitTime, responseTime));
        }

        // Copiar la lista de ejecución a processInfoList
        processInfoList.addAll(executionOrder);
    }
}
