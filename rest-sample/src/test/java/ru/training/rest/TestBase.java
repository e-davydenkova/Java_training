package ru.training.rest;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.testng.SkipException;
import java.io.IOException;



public class TestBase {

    boolean isIssueOpen(int issueId) throws IOException {
        String json = getExecutor()
                .execute(Request.Get(String.format("http://demo.bugify.com/api/issues/%d.json?limit=1000", issueId)))
                .returnContent().asString();
        String status = new JsonParser().parse(json).getAsJsonObject().get("issues")
                .getAsJsonArray().get(0).getAsJsonObject().get("state_name").getAsString();
        return (!status.equals("Resolved"));
    }

    public void skipIfNotFixed(int issueId) throws IOException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }


    public Executor getExecutor() {
        return Executor.newInstance().auth("28accbe43ea112d9feb328d2c00b3eed", "");
    }




}
