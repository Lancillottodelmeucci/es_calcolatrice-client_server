/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcalculatorapplication;

/**
 *
 * @author giova
 */
public class ClientCalculatorApplication {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CLICalculator calculator=new CLICalculator();
        calculator.connettiAlServer();
        calculator.inviaOperazioni();
    }
}