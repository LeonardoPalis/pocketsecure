package com.salvesenha;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main extends Activity{

	private TextView tvTextos;
	private Button btComece;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setComponents();
		
		btComece.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it1 = new Intent("com.salvesenha.CADASTRO");
				startActivity(it1);
			}
		});
		
		
	}
	public void mensagem(String title, String text){
		
		AlertDialog.Builder mensagem = new AlertDialog.Builder(this);
		mensagem.setTitle(title);
		mensagem.setMessage(text);
		mensagem.setNegativeButton("OK", null);
		mensagem.show();
	}
	
	public void setComponents(){
		btComece = (Button)findViewById(R.id.btComece);
		
	}
	
}
