package com.example.shediao;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Handler;
import android.content.Intent;

public class SplashActivity extends Activity {

	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		startMainActivity();
	}

	private void startMainActivity(){
		new Handler().postDelayed(new Runnable() {
			public void run() {
				intent = new Intent(SplashActivity.this, Login.class);
				startActivity(intent);
				SplashActivity.this.finish();
			}
		}, 1000);
	}

}
