/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package msgs;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

/**
 *
 * @author eduardo.fernando
 */
public class Pbar {
    
       public   static   JFrame Progresso = new JFrame() {
                        {
                            // Configurações do JFrame
                            setTitle("Aguardando processo");
                            setSize(300, 100);
                            setUndecorated(true);
                            setLocationRelativeTo(null);

                            // Criação do JProgressBar
                            JProgressBar progressBar = new JProgressBar();
                            progressBar.setIndeterminate(true); // Configura o modo indeterminado

                            // Adiciona o JProgressBar ao JFrame
                            getContentPane().add(progressBar, BorderLayout.CENTER);

                            // Configura o comportamento ao fechar o JFrame
                            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                            // Exibe o JFrame
                            setVisible(true);
                        }
                    };
}
