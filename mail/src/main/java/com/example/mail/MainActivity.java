package com.example.mail;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class MainActivity extends AppCompatActivity {
    private TextView textView=null;
    private TextView message=null;
    private Button button=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .permitDiskReads()
        .permitDiskWrites()
        .permitNetwork().build());

        textView = (TextView)findViewById(R.id.u_mail); //받는 사람의 이메일

        message = (TextView) findViewById(R.id.message); //본문 내용



        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"이메일 성공적으로 보냄",Toast.LENGTH_LONG);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            GMailSender gMailSender = new GMailSender("sae1013@gmail.com", "sae563365");

                            //GMailSender.sendMail(제목, 본문내용, 받는사람);

                            gMailSender.sendMail("테스트메일", message.getText().toString(), textView.getText().toString());

                            Toast.makeText(getApplicationContext(),"이메일 성공적으로 보냄",Toast.LENGTH_LONG);

                        } catch (SendFailedException e) {

                            Toast.makeText(getApplicationContext(),"sendFailedException",Toast.LENGTH_LONG);

                        } catch (MessagingException e) {

                            Toast.makeText(getApplicationContext(),"internet Exception",Toast.LENGTH_LONG);
                        } catch (Exception e) {

                            e.printStackTrace();

                        }
                    }
                }).start();


            }

        });



    }
}
