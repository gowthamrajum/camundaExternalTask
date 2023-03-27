package externalTaskExample;

import java.util.Base64;
import org.camunda.bpm.client.interceptor.ClientRequestContext;
import org.camunda.bpm.client.interceptor.ClientRequestInterceptor;

public class BasicAuthInterceptor implements ClientRequestInterceptor {

    private final String basicAuthHeaderValue;

    public BasicAuthInterceptor(String username, String password) {
        this.basicAuthHeaderValue = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }

    @Override
    public void intercept(ClientRequestContext requestContext) {
        requestContext.addHeader("Authorization", basicAuthHeaderValue);
    }
}
