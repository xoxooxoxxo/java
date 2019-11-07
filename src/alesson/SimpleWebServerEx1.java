package alesson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleWebServerEx1 {

	public static void main(String[] args) throws IOException {
		
		System.out.println("test -- - --- -");
	      ServerSocket server = new ServerSocket(9000);
	      while(true)
	      {
	         System.out.println("클라이언트 접속 대기");
	         Socket client = server.accept();
	         HttpThread ht = new HttpThread(client);
	         ht.start();
	      }
	   }
	   
	   static class HttpThread extends Thread {
	      private Socket client;
	      BufferedReader br;
	      PrintWriter pw;
	      
	      HttpThread(Socket client) {
	         this.client = client;
	         
	         try
	         {
	            br = new BufferedReader
	                  (new InputStreamReader(client.getInputStream()));
	            pw = new PrintWriter(client.getOutputStream());
	         } catch(IOException e) { e.printStackTrace(); }
	      }
	      
	      public void run() {
	         BufferedReader fbr = null;
	         try
	         {
	            String line = br.readLine();
	            String filename = "index.html";
	            fbr = new BufferedReader(new FileReader(filename));
	            
	            String fline = null;
	            while((fline=fbr.readLine()) != null) {
	               pw.print(fline);
	               pw.flush();
	            }
	         } catch(IOException e) { e.printStackTrace(); }
	         
	         finally {
	            try {
	               if(fbr != null)
	                  fbr.close();
	               if(br != null)
	                  br.close();
	               if(pw != null)
	                  pw.close();
	               if(client != null)
	                  client.close();
	            } catch(IOException e) {}
	         }
	      }
	   }
	}
