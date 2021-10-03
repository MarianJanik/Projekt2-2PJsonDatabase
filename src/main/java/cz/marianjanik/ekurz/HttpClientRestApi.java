package cz.marianjanik.ekurz;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientRestApi {

    protected String callApi(String address) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address)).GET().build();
        HttpResponse response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body().toString();
    }

}
