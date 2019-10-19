/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author cesar
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    
    
    /*
    Criar servidor de conexoes
    Esperar pedido de conexao e se sim, criar uma nova conexao
    Criar streams de i/o
    Tratar conversacao entre client/server (tratar protocolo)
    Fechar socket de cominicacao client/server
    Fechar stream de i/o
    Fechar server socket
    */
    
    
    private ServerSocket serverSocket;
    
    private void createServerSocket(int port) throws IOException{
        serverSocket = new ServerSocket(port);
        
    }
 
    private Socket waitConnection() throws IOException{
        Socket socket =  serverSocket.accept();
        return socket;
    }
    
    private void closeSocket(Socket socket) throws IOException{
        socket.close();
    }
            
    private void treatConnection(Socket socket) throws IOException{
        /*protocolo da app
        ** Cliente ----------- SOCKET ----------- servidor (1 socket para cada client
        Criar streams de i/o
        Tratar conversacao entre client/server (tratar protocolo)
        */
        try {
            
            
            //primeiro estancir output e fazer flush na mensagem
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            
            /*
            protocolo 
            client -> hello
            server <- hello world
            */
            System.out.println("Treating...");
            String msg = input.readUTF();
            System.out.println("Msg from client: " + msg);
            output.writeUTF("Hello World!");
            output.flush();
            
            input.close();
            output.close(); 
            
        } catch (Exception e) {
            //tratatmento falhas
            System.out.println("Cannot treat the connection with the client: "+ socket.getInetAddress());
            System.out.println("Error: " + e.getMessage());
        } finally{
            closeSocket(socket);
        }
    }
    
            
            
            
    public static void main(String[] args){
        try {
            Server server = new Server();
            System.out.println("Waiting connection...");
            server.createServerSocket(5555);
            Socket socket = server.waitConnection(); //protocolo
            System.out.println("Client found");
            server.treatConnection(socket);
            System.out.println("Client finished");
            
            
            
        } catch (Exception e) {
            //tratar exception
        }
        
    }
    
}
