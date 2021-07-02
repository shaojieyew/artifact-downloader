package common.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class HttpCallerClient extends HttpCaller {
        public HttpResponse execute(HttpUriRequest request) throws IOException {
                HttpResponse httpResponse = null;
                HttpClient httpClient = HttpClientBuilder.create().build();

                httpResponse = httpClient.execute(request);
                return httpResponse;
        }
}
