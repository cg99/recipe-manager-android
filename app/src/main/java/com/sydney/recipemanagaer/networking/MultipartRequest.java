package com.sydney.recipemanagaer.networking;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.util.Map;
import java.util.UUID;

public class MultipartRequest extends Request<JSONObject> {
    private final Map<String, String> params;
    private final Map<String, File> fileParams;
    private final Response.Listener<JSONObject> responseListener;
    private static final String BOUNDARY = "===" + UUID.randomUUID().toString() + "===";
    private static final String LINE_END = "\r\n";
    private static final String TWO_HYPHENS = "--";

    public MultipartRequest(String url, Map<String, String> params, Map<String, File> fileParams,
                            Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        this.params = params;
        this.fileParams = fileParams;
        this.responseListener = responseListener;
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data; boundary=" + BOUNDARY;
    }

    @Override
    public byte[] getBody() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        try {
            // Add text parameters
            for (Map.Entry<String, String> entry : params.entrySet()) {
                appendTextParameter(dataOutputStream, entry.getKey(), entry.getValue());
            }

            // Add file parameters
            for (Map.Entry<String, File> entry : fileParams.entrySet()) {
                appendFileParameter(dataOutputStream, entry.getKey(), entry.getValue());
            }

            dataOutputStream.writeBytes(LINE_END + TWO_HYPHENS + BOUNDARY + TWO_HYPHENS + LINE_END);

            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                outputStream.close();
                dataOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JSONException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        responseListener.onResponse(response);
    }

    private void appendTextParameter(DataOutputStream dataOutputStream, String key, String value) throws IOException {
        dataOutputStream.writeBytes(TWO_HYPHENS + BOUNDARY + LINE_END);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + LINE_END);
        dataOutputStream.writeBytes(LINE_END);
        dataOutputStream.writeBytes(value);
        dataOutputStream.writeBytes(LINE_END);
    }

    private void appendFileParameter(DataOutputStream dataOutputStream, String key, File file) throws IOException {
        dataOutputStream.writeBytes(TWO_HYPHENS + BOUNDARY + LINE_END);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" +
                key + "\"; filename=\"" + file.getName() + "\"" + LINE_END);
        dataOutputStream.writeBytes("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName()) + LINE_END);
        dataOutputStream.writeBytes(LINE_END);

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                dataOutputStream.write(buffer, 0, bytesRead);
            }
        }
        dataOutputStream.writeBytes(LINE_END);
    }
}
