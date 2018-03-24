package com.soaic.networklibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.soaic.networklibrary.net.OnResponseListener;
import com.soaic.networklibrary.net.RequestClient;
import com.soaic.networklibrary.response.PostQueryResponse;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.requestButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request();
            }
        });

    }

    private void request(){
        new RequestClient.Builder()
                .url("http://www.kuaidi100.com/query")
                .param("type","yuantong")
                .param("postid","500379523313")
                .builder()
                .post(PostQueryResponse.class, new OnResponseListener<PostQueryResponse>() {
                    @Override
                    public void onSuccess(PostQueryResponse content) {
                        Toast.makeText(getApplicationContext(),content.toString(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Throwable err) {

                    }
                });
    }
}
