package com.salvesenha;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.salvesenha.login;
public class Cadastrar extends Activity {

	public SQLiteDatabase bancoUser = null;
	public Cursor cursor;
	private Button btCadastro;
	private EditText etCadastroUser, etCadastroSenha;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro);
		setComponents();
		criaOuAbreBanco();
		
		
		btCadastro.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(etCadastroSenha.getText().toString().equals("") && etCadastroUser.getText().toString().equals("")){
					mensagem("ERRO","Campo vazio");
				}
				else{
					gravaDados(etCadastroUser.getText().toString(),etCadastroSenha.getText().toString());
					Intent it = new Intent("com.salvesenha.PROCESSO");
					startActivity(it);
				}
			}
		});
	}

	

	private void setComponents(){
		
		btCadastro = (Button)findViewById(R.id.btCadastro);
		etCadastroUser = (EditText)findViewById(R.id.etCadastroUser);
		etCadastroSenha = (EditText)findViewById(R.id.etCadastroSenha);
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
			mensagem("Banco de Dados", "Salvo");
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
			mensagem("ERRO","Banco já criado!" + cursor.getCount());
					
		}
		else{
			Intent it = new Intent("com.salvesenha.PRINCIPAL");
			startActivity(it);
		}
		}catch(Exception e){}
		
	}
	
	
}
