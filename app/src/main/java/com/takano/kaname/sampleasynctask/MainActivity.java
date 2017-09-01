package com.takano.kaname.sampleasynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.PushButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<String>();
                list.add("ぴーまん");
                list.add("たまねぎ");
                list.add("レタス");



                AsyncTask(list);
            }
        });
    }

    private void AsyncTask(ArrayList<String> list) {
        //PrograssDialog
        final ProgressDialog mProgressDialog;
        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setTitle("ロード中");
        mProgressDialog.setMessage("Loading now...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show(); //start

        //new AsyncTask<非同期処理に渡したい変数, 非同期処理途中で行いたいための変数?, 非同期処理後に行う変数>
        new AsyncTask<ArrayList, Void, String>() {

            /**
             * doInBackground
             * @param params 入力
             * @return 非同期結果
             */
            @Override
            protected String doInBackground(ArrayList... params) {
                ArrayList<String> param = params[0]; //こうしないと受け取れない
                String temp_string = "";

                //Listの値を取得
                for(int i=0,n=param.size(); i<n; i++){
                    temp_string += param.get(i) + ",";
                }
                temp_string = temp_string.trim();

                //故意的に5秒待つ
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return temp_string;
            }

            /**
             * onPostExecute
             * @param result 非同期処理の結果
             */
            @Override
            protected void onPostExecute(String result) {
                mProgressDialog.dismiss(); //ProgressDialogの停止
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
            }
        }.execute(list); //実行
    }
}
