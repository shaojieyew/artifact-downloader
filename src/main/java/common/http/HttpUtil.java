package common.http;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.io.InputStream;

public class HttpUtil {

    public static String httpEntityToString(HttpEntity entity) throws IOException {
        InputStream in = entity.getContent();
        String encoding ="UTF-8";
        if(entity!=null && entity.getContentEncoding()!=null){
            encoding = entity.getContentEncoding().getValue();
        }
        String body = IOUtils.toString(in, encoding);
        return body;
    }

    public static HttpEntity stringToHttpEntity(String content){
        return new StringEntity(content, "UTF-8");
    }
}
