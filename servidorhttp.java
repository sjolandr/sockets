/* Código adaptado. Autor original: Sergio Teixeira de Carvalho  */
/* Alterações: Beatriz Carvalho de Barros do Vale Rochelle       */

import java.net.*;
import java.io.*;

class ConexaoHttp extends Thread {
  
  Socket socketCliente;
  
  ConexaoHttp (Socket aSocketCliente) throws IOException {
    this.socketCliente = aSocketCliente;
  }
  
  public void run() {
  
      PrintWriter saida = null;
      BufferedReader entrada = null;
                                   
      InetAddress endCliente = this.socketCliente.getInetAddress();
      
      String pdu = null;  

      public int contadorMensagem;
            
      try {
        saida = new PrintWriter(this.socketCliente.getOutputStream(), true);
        entrada = new BufferedReader (new InputStreamReader(this.socketCliente.getInputStream()));
         
        pdu = entrada.readLine();

        System.out.println("Mensagem: " + pdu + " | IP: " + endCliente.getHostAddress() + " | porta: " + endCliente.getPort() + "\n"); 
        System.out.println("Mensagem " + contadorMensagem + " imprimida com sucesso!\n")

        socketCliente.close();
        saida.close();
        entrada.close();
      }
      catch ( IOException e ) {        
        System.out.println( "Erro E/S " + e );
      } 
  }
}

public class ServidorHttp {
    public static void main(String[] args) throws IOException {

        final int portaDefault = 8080;
        
        Integer porta;
        int backlog = 5;
        
        Socket socketCliente = null;
        ServerSocket socketServidor = null;
       
        if ((args.length == 1))
           porta = Integer.parseInt(args[0]);   
        else
           porta = portaDefault;
       
        while (true) {
           try {
                socketServidor = new ServerSocket(porta, backlog);
                break;
           }
           catch (IOException e) {
              porta++;
           }
        }

        System.out.println ("\nServidor HTTP ativado. " + 
                            "Aguardando Cliente HTTP na porta " + porta + "...\n");
 
        while (true) {

            socketCliente = null;
           try {
                 socketCliente = socketServidor.accept();
           } 
           catch (IOException e) {
                 System.err.println("Erro de E/S " + e);
                 System.exit(1);
           }
            
           new ConexaoHttp(socketCliente).start();
        }         
    }
}