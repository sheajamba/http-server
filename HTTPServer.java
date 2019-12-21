import java.util.*;
import java.io.*;
import java.net.*;

public class HTTPServer {
  private static ServerSocket serverSocket;

  public static void main(String[] args) throws IOException {
  //serverSocket=new ServerSocket(2055); 
    
	int portNumber = Integer.parseInt(args[0]);
	
	serverSocket = new ServerSocket(portNumber);
	
	//File command_file = new File(args[1]);
   
    while (true) {
      try {
        Socket s = serverSocket.accept();  
        new ClientHandler(s);  
      }
      catch (Exception x) {
        System.out.println(x);
      }
    }
  }
}

class ClientHandler extends Thread {
  private Socket socket;  

  public ClientHandler(Socket s) {
    socket = s;
    start();
  }

  public void run() {
    try {

      BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintStream output = new PrintStream(new BufferedOutputStream(socket.getOutputStream()));

      String s = input.readLine();
      String fileName= "";
      StringTokenizer stringTokenizer = new StringTokenizer(s);

      System.out.println(s); 

      try {

        if (stringTokenizer.nextToken().equalsIgnoreCase("GET"))
          
        	fileName = stringTokenizer.nextToken();
        else
        	
          throw new FileNotFoundException();  

        if (fileName.endsWith("/"))
          fileName += "index.html";

        while (fileName.indexOf("/") == 0)
          fileName = fileName.substring(1);

        fileName = fileName.replace('/', File.separator.charAt(0));


        InputStream f = new FileInputStream(fileName);

        String mimeType="text/plain";
        if (fileName.endsWith(".html") || fileName.endsWith(".htm"))
          mimeType="text/html";
        else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg"))
          mimeType="image/jpeg";
        else if (fileName.endsWith(".css"))
          mimeType="text/css";
        else if (fileName.endsWith(".gif"))
          mimeType="image/gif";
        else if (fileName.endsWith(".mpeg"))
        	mimeType = "video/mpeg";
        else if (fileName.endsWith(".avi"))
        	mimeType = "video/avi";
        else if (fileName.endsWith(".mp3"))
        	mimeType = "audio/mp3";
        
        File file = new File(fileName);
        int length = (int)file.length();
        
        output.println("HTTP/1.1 200 OK");
        output.println("Server: Shea's COS 460 HTTP Server");
        output.println("Content-type: " +mimeType);
        output.println("Content-length: "+length );
        output.println();

        byte[] content = new byte[4096];
        int i;
        
        while ((i = f.read(content)) > 0)
          output.write(content, 0, i);
        	output.close();
        	
      }
      catch (FileNotFoundException x) {
        output.println("HTTP/1.1 404 Not Found\r\n");
        output.close();
        
      }
    }
    catch (IOException x) {
      System.out.println(x);
    }
  }
}