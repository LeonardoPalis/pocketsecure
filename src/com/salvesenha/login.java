package com.salvesenha;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class login extends Activity {
	

	public SQLiteDatabase bancoUser = null;
	public Cursor cursor;
	private EditText etUsuario,etSenha;
	private Button btEntrar;
	private TextView tvPrimeiroLogin,tvUsuario, tvSenha;
	
	public login(){}
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		criaOuAbreBanco();
		setComponents();
		verificaPrimeiroLogin();
		
		
		btEntrar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(verificaSenha()){
					
					Intent it = new Intent("com.salvesenha.PROCESSO");
					startActivity(it);
				}
				else
					mensagem("Login","Usuário ou senha inválidos");
			}
		});
		
	}
	
	@Override
	public void onBackPressed() {
		// VAZIO
	}
	public void criaOuAbreBanco(){
		
		try{
			bancoUser = openOrCreateDatabase("bancoUser", MODE_PRIVATE, null);
			String sql = "CREATE TABLE IF NOT EXISTS usuario(codigo INTEGER PRIMARY KEY, login TEXT, senha TEXT);";
			bancoUser.execSQL(sql);
			
		}catch(Exception erro){
			mensagem("Banco de Dados", "ERRO");
		}
		
	}
	
	
	public void gravaDados(String etLogin, String etKey){
		try{
			String sql = "INSERT INTO usuario (login,senha) values ('" + etLogin + "','"
																 	+ etKey + "');'";
			bancoUser.execSQL(sql);
			
		}catch(Exception erro){
			mensagem("Banco de Dados", "Erro ao salvar");
		}
	}
	
	public void mensagem(String title, String text){
		
		AlertDialog.Builder mensagem = new AlertDialog.Builder(this);
		mensagem.setTitle(title);
		mensagem.setMessage(text);
		mensagem.setNegativeButton("OK", null);
		mensagem.show();
	}
	
	public void verificaPrimeiroLogin(){
		try{
		bancoUser = openOrCreateDatabase("bancoUser", MODE_PRIVATE, null);
		cursor = bancoUser.rawQuery("Select * from usuario", null);
		
		if(cursor.getCount() != 0){
			cursor.moveToFirst();

		}
		else{
			Intent it = new Intent("com.salvesenha.PRINCIPAL");
			startActivity(it);
		}
		}catch(Exception e){}
		
	}
	
	public boolean verificaSenha(){
		try{
			cursor = bancoUser.query("usuario", new String[] {"login","senha"}, null, null, null, null, null);
			cursor.moveToFirst();
			String user = cursor.getString(0);
			String senha = cursor.getString(1);
			if(user.equals(etUsuario.getText().toString()) && senha.equals(etSenha.getText().toString())){
				return true;
			}
			else return false;
			
		}catch(Exception erro){}
		return false;
	}
	public void setComponents(){
		
		etUsuario = (EditText)findViewById(R.id.etUsuario);
		etSenha = (EditText)findViewById(R.id.etSenha);
		btEntrar = (Button)findViewById(R.id.btEntrar);
		//tvPrimeiroLogin = (TextView)findViewById(R.id.tvPrimeiroLogin);
		tvUsuario = (TextView)findViewById(R.id.tvUser);
		tvSenha = (TextView)findViewById(R.id.tvSenha);
	}

}
