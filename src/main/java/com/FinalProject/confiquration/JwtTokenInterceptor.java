//package com.FinalProject.confiquration;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpRequest;
//import org.springframework.http.client.ClientHttpRequestExecution;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.http.client.ClientHttpResponse;
//
//import java.io.IOException;
//
//public class JwtTokenInterceptor implements ClientHttpRequestInterceptor {
//    private final String jwtToken;
//
//    public JwtTokenInterceptor(String jwtToken) {
//        this.jwtToken = jwtToken;
//    }
//
//    @Override
//    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException, IOException {
//        HttpHeaders headers = request.getHeaders();
//        headers.add("Authorization", "Bearer " + jwtToken);
//        return execution.execute(request, body);
//    }
//}

