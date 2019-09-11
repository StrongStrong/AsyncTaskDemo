package com.example.asynctaskdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressbar;
    private TextView text;
    private MySyncTask mySync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressbar=findViewById(R.id.progress_bar);
        text = findViewById(R.id.text);

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mySync=new MySyncTask();
                mySync.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mySync.cancel(true);
                mySync=null;
            }
        });
    }
    class MySyncTask extends AsyncTask<String, Integer, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            text.setText("加载中");
        }

        @Override
        protected String doInBackground(String[] objects) {
            try {
                int count = 0;
                int length = 1;
                while (count<99) {

                    count += length;
                    // 可调用publishProgress（）显示进度, 之后将执行onProgressUpdate（）
                    publishProgress(count);
                    // 模拟耗时任务
                    Thread.sleep(50);
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer[] values) {

            super.onProgressUpdate(values);
            progressbar.setProgress(values[0]);
            text.setText("loading..." + values[0] + "%");
        }

        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);
            text.setText("加载完毕");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            text.setText("已取消");
            progressbar.setProgress(0);
        }
    }

}
