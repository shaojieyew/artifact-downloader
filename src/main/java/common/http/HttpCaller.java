package common.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public abstract class HttpCaller {

    public abstract HttpResponse execute(HttpUriRequest request) throws IOException, LoginException;

}
