package com.salvesenha;

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

public class ModificaDados extends Activity {
	
	private SQLiteDatabase bancoInfo;
	private Cursor cursor;
	private String usuario,senha;
	private EditText etModUser, etModSenha;
	private Button btModSalvar, btModVoltar, btModDeletar;
	private TextView tvMod;
	public ModificaDados(){}
	public ModificaDados(String usuario, String senha){
		
		this.usuario = usuario;
		this.senha = senha;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modificardados);
		setComponents();
		criaOuAbreBanco();
		
		Intent it = getIntent();
		
		if(it != null){
			Bundle parametro = it.getExtras();
			if(parametro != null){
				
				etModUser.setText(parametro.getString("user"));
				etModSenha.setText(parametro.getString("senha"));
				tvMod.setText(parametro.getString("codigo"));
			}
		}
		
		btModSalvar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				modificarRegistro();
				Intent it = new Intent("com.salvesenha.LISTA");
				startActivity(it);
			}
		});
		
		btModVoltar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent("com.salvesenha.LISTA");
				startActivity(it);
				
			}
		});
		
		btModDeletar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				deletarRegistro();	
				etModSenha.setText("");
				etModUser.setText("");
				Intent it = new Intent("com.salvesenha.LISTA");
				startActivity(it);
			}
		});
	}
		
	

	private void criaOuAbreBanco(){
		try{
			bancoInfo = openOrCreateDatabase("bancoInfo", MODE_PRIVATE, null);
			String sql = "CREATE TABLE IF NOT EXISTS dados(codigo INTEGER PRIMARY KEY,titulo TEXT, senha TEXT);";
			bancoInfo.execSQL(sql);
		}catch(Exception erro){}

	}
	
	private void modificarRegistro(){
		try{
			String sql = "UPDATE dados SET titulo = '"
					+ etModUser.getText().toString() + "', senha = '"
					+ etModSenha.getText().toString() + "' where codigo ='"
					+ tvMod.getText().toString() + "' ;'";
			bancoInfo.execSQL(sql);
			
			mensagem("Modificar","Salvo com sucesso!");
		}catch(Exception erro){
			mensagem("ERRO","Não foi possivel modificar");
		}
		
	}
	
	private void deletarRegistro(){
		try{
			String sql = "DELETE FROM dados where codigo = '"
					+ tvMod.getText().toString() + "' ;'";
			bancoInfo.execSQL(sql);
			
			mensagem("Deletar","Deletado com sucesso!");
		}catch(Exception erro){
			mensagem("ERRO","Não foi possivel modificar");
		}
		
	}
	
	private void mensagem(String title, String text){
		
		AlertDialog.Builder mensage = new AlertDialog.Builder(this);
		mensage.setTitle(title);
		mensage.setMessage(text);
		mensage.setNeutralButton("OK", null);
		mensage.show();
	}
	
	private void setComponents(){
		
		etModUser = (EditText)findViewById(R.id.etModUser);
		etModSenha = (EditText)findViewById(R.id.etModSenha);
		btModSalvar = (Button)findViewById(R.id.btModSalvar);
		btModVoltar = (Button)findViewById(R.id.btModVoltar);
		btModDeletar = (Button)findViewById(R.id.btModDeletar);
		tvMod = (TextView)findViewById(R.id.tvMod);
		
	}
}
