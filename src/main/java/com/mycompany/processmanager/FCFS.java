/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.processmanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Amin
 */
public class FCFS implements Algoritmo {

    /**
     *
     * @param processes
     */
    @Override
    public void ejecutar(List<MenuManager.Process> processes) {
        System.out.println("Ejecutar algortimo FCFS");
        int tiempoTotal = 0;
        int tiempoEsperaTotal = 0;
        
        Collections.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));
        
        for (MenuManager.Process proceso : processes) {
            // Calcular el tiempo de espera para este proceso
            int tiempoEspera = Math.max(0, tiempoTotal - proceso.arrivalTime);
            // Sumar el tiempo de espera total
            tiempoEsperaTotal += tiempoEspera;
            // Calcular el tiempo de respuesta para este proceso
            int tiempoRespuesta = tiempoEspera + proceso.burstTime;
            
            proceso.waitTime = tiempoEspera;
            proceso.responseTime = tiempoRespuesta;
            
            // Actualizar el tiempo total
            tiempoTotal += proceso.burstTime;

        }
    }
}
