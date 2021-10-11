package cz.marianjanik.ekurz;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientRestApi {

    protected String callApi(String address) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(address)).GET().build();
        HttpResponse response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new IOException("Problémy s načtením webové stránky.");
        } catch (InterruptedException e) {
            throw new InterruptedException("Přerušení při čtení webové stránky.");
        }
        return response.body().toString();
    }
}
