package com.alikaanbaci.quickstarter;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProxyPatternTest {

    interface HttpHandler {

        String handleRequest();
    }

    class HttpHandlerImpl implements HttpHandler {
        @Override
        public String handleRequest() {
            return "Success";
        }
    }

    class HttpHandlerPoxy implements HttpHandler {

        private final HttpHandler httpHandler;

        public HttpHandlerPoxy(HttpHandler proxy) {
            this.httpHandler = proxy;
        }

        @Override
        public String handleRequest() {
            return httpHandler.handleRequest() + " 200";
        }
    }

    @Test
    void runTest() {
        // Prepare
        var handler = new HttpHandlerImpl();
        var proxy = new HttpHandlerPoxy(handler);
        // Execute
        final String response = handler.handleRequest();
        final String responseWithCode = proxy.handleRequest();
        // Verify
        assertThat(response).isEqualTo("Success");
        assertThat(responseWithCode).isEqualTo("Success 200");
    }
}
