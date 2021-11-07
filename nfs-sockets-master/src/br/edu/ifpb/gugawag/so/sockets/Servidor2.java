package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Servidor2 {

    public static void main(String[] args) throws IOException {
        System.out.println("== Servidor ==");

        // Configurando o socket
        ServerSocket serverSocket = new ServerSocket(7001);
        Socket socket = serverSocket.accept();

        // pegando uma referência do canal de saída do socket. Ao escrever nesse canal, está se enviando dados para o
        // servidor
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        // pegando uma referência do canal de entrada do socket. Ao ler deste canal, está se recebendo os dados
        // enviados pelo servidor
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        // laço infinito do servidor
        while (true) {
            System.out.println("Cliente: " + socket.getInetAddress());

            String mensagem = dis.readUTF();
            
//            String[] arq = arquivos.toString().split(" ");

            Path dir = Paths.get("C:\\Users\\Bruna\\Documents\\Curso SI\\Program. Dist\\ProgramacaoDist\\nfs-sockets-master\\src\\br\\edu\\ifpb\\gugawag\\so\\arquivos");
            List<String> arquivos = new ArrayList<>();
            
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.txt")) {
            	Scanner teclado = new Scanner(System.in);
	            switch(mensagem) {     
	            	case "readdir":      	
                        for (Path file : stream) {
                        	arquivos.add(file.getFileName().toString());
                        }
                        dos.writeUTF(arquivos.toString());
	                    
	            		break;
	            		
	            	case "rename":
	            		 teclado = new Scanner(System.in);
	            		 dos.writeUTF("Qual arquivo vc deseja renomear: ");
	                     String arqASerRenomeado = dis.readUTF();          
	                     
	                     dos.writeUTF("Novo nome: ");
	                     String novoNome = dis.readUTF();
	                     
	                     boolean arqRenomeado = false;
	                     
	                     for(Path f: stream) {
	                    	 String nomeArquivo = f.getFileName().toString();
	                    	 if(nomeArquivo.equals(arqASerRenomeado)) {
	                    		 arqRenomeado = f.toFile().renameTo(new File(novoNome));   		
	                    	 }                     	 
	                     }
	                     if(arqRenomeado)
	                    	 dos.writeUTF("Arquivo renomeado");
	                     else
	                    	 dos.writeUTF("Arquivo não renomeado");
	                     break;
	         		
	            	case "create":
	            		 teclado = new Scanner(System.in);
	            		 dos.writeUTF("Nome do arquivo: ");
	                     String novoArquivo = dis.readUTF();          
	                     
	                     File file = new File(dir +"\\" + novoArquivo + ".txt");
	                     	               
                         file.createNewFile();
                         dos.writeUTF("Arquivo criado com sucesso"); 
                  
	                     break;
	            		
	            	case "remove":
	            		teclado = new Scanner(System.in);
	            		dos.writeUTF("Nome do arquivo: ");
	                    String arqRemover = dis.readUTF() + ".txt"; 
	            		
	                    for(Path f: stream) {
	                    	 String nomeArquivo = f.getFileName().toString();
	                    	 if(nomeArquivo.equals(arqRemover)) {
	                    		 f.toFile().delete();
	                    		 dos.writeUTF("Arquivo removido com sucesso"); 
	                    	 }    
              		 
	                     }
	                    
	                    break;
	            	
	            	default:
	            		dos.writeUTF("Li sua mensagem: " + mensagem);
	            		break;
	            }
        	}
               	
        }
        /*
         * Observe o while acima. Perceba que primeiro se lê a mensagem vinda do cliente (linha 29, depois se escreve
         * (linha 32) no canal de saída do socket. Isso ocorre da forma inversa do que ocorre no while do Cliente2,
         * pois, de outra forma, daria deadlock (se ambos quiserem ler da entrada ao mesmo tempo, por exemplo,
         * ninguém evoluiria, já que todos estariam aguardando.
         */
    }
}
