package dekiru.simpletask.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * The response entity returned to client.
 */
public class Response<T> {
    private T data;

    private Map<String, Object> meta;

    private ResponseError error;

    public Response() {
    }

    public Response(T data) {
        this.data = data;
        this.meta = new HashMap<>();
    }

    public Response(ResponseError error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, Object> meta) {
        this.meta = meta;
    }

    public ResponseError getError() {
        return error;
    }

    public void setError(ResponseError error) {
        this.error = error;
    }
}
