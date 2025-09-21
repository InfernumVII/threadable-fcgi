package com.infernumvii;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import com.infernumvii.fastcgi.FCGIRequest;
import com.infernumvii.http.ContentType;
import com.infernumvii.http.Response;
import com.infernumvii.http.StatusCode;

public class FCGIThread implements Runnable {
    private final FCGIRequest fcgiRequest;
    public FCGIThread(FCGIRequest fcgiRequest){
        this.fcgiRequest = fcgiRequest;
    }

    private String getBody() throws IOException {
        String CONTENT_LENGTH = fcgiRequest.params.getProperty("CONTENT_LENGTH");
        if (CONTENT_LENGTH == null) {
            return "";
        }
        int contentLength = Integer.parseInt(CONTENT_LENGTH);
        byte[] bytes = fcgiRequest.inStream.readNBytes(contentLength);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(byteArrayInputStream));
        return bufferedReader.lines().collect(Collectors.joining("\n"));
    }

    @Override
    public void run() {
        // System.out.println("thread started");
        // System.out.println(fcgiRequest.params);
        // try {
        //     System.out.println(getBody());
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        CpuBoundTask.task();
        PrintStream out = new PrintStream(new BufferedOutputStream(fcgiRequest.outStream, 8192), true);
        out.println(
            new Response.Builder()
            .withStatusCode(StatusCode.C_200)
            .withContentType(ContentType.TEXT_HTML)
            .withBody("Sdasdasdas")
            .build()
            .toString()
        );
        try {
            fcgiRequest.errStream.close();
            fcgiRequest.outStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
}
