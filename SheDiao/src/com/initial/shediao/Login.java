package com.initial.shediao;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	
	Button b;
	EditText un,pw;
	ProgressDialog dialog;
	HttpPost httpPost;
	HttpResponse response;
	HttpClient httpClient;
	List<NameValuePair> unPwPair;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		//hide action bar
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		b = (Button)findViewById(R.id.LoginButton);
		un = (EditText)findViewById(R.id.userNameEditText);
		pw = (EditText)findViewById(R.id.passwordEditText);
		
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View v){
				dialog = new ProgressDialog(Login.this);
				dialog.setMessage("Validating User ...");
				dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				new Thread(new Runnable(){
					@Override
					public void run() {
						login();
					}
				}).start();
			}
		});
	}
	
	public void login(){
		try{
			httpClient = new DefaultHttpClient();
			httpPost = new HttpPost("http://litiezhu.myrpi.org/sheDiao/check.php");
			unPwPair = new ArrayList<NameValuePair>(2);
			unPwPair.add(new BasicNameValuePair("username",un.getText().toString().trim()));
			unPwPair.add(new BasicNameValuePair("password",pw.getText().toString().trim()));
			httpPost.setEntity(new UrlEncodedFormEntity(unPwPair));
			response = httpClient.execute(httpPost);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			final String response = httpClient.execute(httpPost, responseHandler);
			System.out.println("Response : " + response);
			runOnUiThread(new Runnable() {
                public void run() {
                    dialog.dismiss();
                }
            });
             
            if(response.equalsIgnoreCase("User Found")){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(Login.this,"Login Success", Toast.LENGTH_SHORT).show();
                    }
                });
                 
                startActivity(new Intent(Login.this, HomeActivity.class));
            }else{
            	runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(Login.this,"Login Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
		} catch(Exception e){
			System.out.println("Exception: " + e.getMessage());
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	public void toSignUp(View view){
		startActivity(new Intent(this, SignUpActivity.class));
	}
}
