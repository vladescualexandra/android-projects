package com.example.dam_exercise_003.network;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

public class HttpManager implements Callable<String> {

    private URL url;
    private HttpURLConnection connection;

    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;

    private final String URL_ADDRESS;

    public HttpManager(String url_address) {
        this.URL_ADDRESS = url_address;
    }

    @Override
    public String call() throws Exception {
        try {
            return getResultFromHttp();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }

    private String getResultFromHttp() throws IOException {
        url = new URL(URL_ADDRESS);
        connection = (HttpURLConnection) url.openConnection();
        inputStream = connection.getInputStream();
        inputStreamReader = new InputStreamReader(inputStream);
        bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder result = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    private void closeConnection() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        connection.disconnect();
    }


}
