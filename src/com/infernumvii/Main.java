package com.infernumvii;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import com.infernumvii.fastcgi.FCGIInterface;
import com.infernumvii.http.ContentType;
import com.infernumvii.http.Response;
import com.infernumvii.http.StatusCode;


public class Main {
    private static String port = "9000";
    private static FCGIInterface fcgiInterface = new FCGIInterface();

    private static String getBody() throws IOException {
        String CONTENT_LENGTH = fcgiInterface.request.params.getProperty("CONTENT_LENGTH");
        if (CONTENT_LENGTH == null) {
            return "";
        }
        int contentLength = Integer.parseInt(CONTENT_LENGTH);
        byte[] bytes = fcgiInterface.request.inStream.readNBytes(contentLength);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(byteArrayInputStream));
        return bufferedReader.lines().collect(Collectors.joining("\n"));
    }
    public static void main(String[] args) throws IOException {


        System.setProperty("FCGI_PORT", port);
        while (fcgiInterface.FCGIaccept() >= 0) {
            FCGIThread fcgiThread = new FCGIThread(fcgiInterface.request);
            new Thread(fcgiThread).start();
            // System.out.println(FCGIInterface.request.params);
            // System.out.println(getBody());
            
            // PrintStream out = new PrintStream(new BufferedOutputStream(FCGIInterface.request.outStream, 8192), true);
            // String answer = new Response.Builder()
            // .withStatusCode(StatusCode.C_200)
            // .withContentType(ContentType.TEXT_HTML)
            // .withBody("Sdasdasdas")
            // .build()
            // .toString();
            // out.println(answer);
            // FCGIInterface.request.errStream.close();
            // FCGIInterface.request.outStream.close();
            //PrintWriter out = new PrintWriter(fcgiInterface.request.outStream, true);  
            // String answer = new Response.Builder()
            // .withStatusCode(StatusCode.C_200)
            // .withContentType(ContentType.TEXT_HTML)
            // .withBody("asss")
            // .build()
            // .toString();
            // byte[] b = answer.getBytes(StandardCharsets.UTF_8);
            // fcgiInterface.request.outStream.write(b);
        }
        // ServerSocket serverSocket = new ServerSocket(port);
        // Socket socket = serverSocket.accept();
        // System.out.println("started");
        // BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
        // System.out.println(in.readLine());
        // PrintWriter out = new PrintWriter(socket.getOutputStream(), true);  
        // out.println(
        //     new Response.Builder()
        //     .withStatusCode(StatusCode.C_405)
        //     .withContentType(ContentType.TEXT_PLAIN)
        //     .withBody("Method is not allowed")
        //     .build()
        //     .toString()
        // );
            
        
        
    }
}