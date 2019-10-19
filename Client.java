/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cesar
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            /*
            Estabelecer conexao
            Trocar msgs
            */
            //criacao de conexao (prestar atencao na porta
            Socket socket = new Socket("localhost", 5555);
            //criacar dos streams de i/o
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
           
            
            String msg = "Hello";
            output.writeUTF(msg);
            output.flush();
            //apos cada msg usando output fazer flush ==> liberar buffer para envio
            msg = input.readUTF();
            System.out.println("Server says: " + msg);
            
            input.close();
            output.close();
            
            socket.close();
            
        } catch (IOException ex) {
            System.out.println("Error on clientSide" + ex);
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
