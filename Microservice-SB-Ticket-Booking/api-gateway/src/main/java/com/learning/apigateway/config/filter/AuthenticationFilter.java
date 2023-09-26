package com.learning.apigateway.config.filter;


import com.learning.apigateway.config.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>  {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private RestTemplate template;
    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest headerRequest = null;
            if (validator.isSecured.test(exchange.getRequest())) {

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                    System.out.println(authHeader);
                }
                try {
                    System.out.println("before validation");
                    jwtUtil.validateToken(authHeader);
                    System.out.println("after validation");
                    System.out.println("before header request");
                    headerRequest= exchange.getRequest()
                            .mutate()
                            .header("token",authHeader)
                            .build();
                    System.out.println("after header request");
                    System.out.println(jwtUtil.extractEmail(authHeader));

                } catch (Exception e) {
                    System.out.println("invalid access...!");
                    throw new RuntimeException("un authorized access to application");
                }
            }

            assert headerRequest != null;
            return chain.filter(exchange.mutate().request(headerRequest).build());
//            return chain.filter(exchange.mutate().request(headerRequest).build());
//             return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
