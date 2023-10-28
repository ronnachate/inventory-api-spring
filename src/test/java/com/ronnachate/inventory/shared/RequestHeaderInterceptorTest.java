package com.ronnachate.inventory.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;

import com.ronnachate.inventory.shared.interceptor.RequestHeaderInterceptor;

@SpringBootTest(classes = {RequestHeaderInterceptorTest.class})
public class RequestHeaderInterceptorTest {
    @Mock
    private ConfigurableEnvironment env;

    @BeforeEach
    void Setup() {
        when(this.env.getProperty("app.header.key")).thenReturn("application_key");
        when(this.env.getProperty("app.applicationkey")).thenReturn("96696a78-757b-11ee-b962-0242ac120002");
    }

    @Test
    @DisplayName("should return 200 with correct applicationkey header")
    public void should_return_200_with_correct_request_header() throws Exception {
        RequestHeaderInterceptor headerInterceptorInterceptor = new RequestHeaderInterceptor(this.env);
        MockHttpServletRequest req = get("/api/users")
                .buildRequest(new MockServletContext());
        req.addHeader("application_key", "96696a78-757b-11ee-b962-0242ac120002");
        MockHttpServletResponse resp = new MockHttpServletResponse();

        headerInterceptorInterceptor.preHandle(req, resp, "get users");

        assertEquals(200, resp.getStatus());
    }

    @Test
    @DisplayName("should return 401 with incorrect applicationkey header")
    public void should_return_401_with_incorrect_request_header() throws Exception {
        RequestHeaderInterceptor headerInterceptorInterceptor = new RequestHeaderInterceptor(this.env);
        MockHttpServletRequest req = get("/api/users")
                .buildRequest(new MockServletContext());
        req.addHeader("application_key", "5555555555");
        MockHttpServletResponse resp = new MockHttpServletResponse();

        headerInterceptorInterceptor.preHandle(req, resp, "get users");

        assertEquals(401, resp.getStatus());
    }

    @Test
    @DisplayName("should return 401 when no applicationkey header")
    public void should_return_401_when_no_applicationkey_request_header() throws Exception {
        RequestHeaderInterceptor headerInterceptorInterceptor = new RequestHeaderInterceptor(this.env);
        MockHttpServletRequest req = get("/api/users")
                .buildRequest(new MockServletContext());
        MockHttpServletResponse resp = new MockHttpServletResponse();

        headerInterceptorInterceptor.preHandle(req, resp, "get users");

        assertEquals(401, resp.getStatus());
    }

    @Test
    @DisplayName("should return 200 in development environment")
    public void should_return_200_in_dev_env() throws Exception {
        when(this.env.getProperty("app.env")).thenReturn("development");
        RequestHeaderInterceptor headerInterceptorInterceptor = new RequestHeaderInterceptor(this.env);
        //no header key defined
        MockHttpServletRequest req = get("/api/users")
                .buildRequest(new MockServletContext());
        MockHttpServletResponse resp = new MockHttpServletResponse();

        headerInterceptorInterceptor.preHandle(req, resp, "get users");

        assertEquals(200, resp.getStatus());
        //append header key
        req.addHeader("application_key", "5555555555");
        //ensure new response returned
        resp = new MockHttpServletResponse();
        headerInterceptorInterceptor.preHandle(req, resp, "get users with incorect header key");
        assertEquals(200, resp.getStatus());
    }

}
