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

public class AdicionarTitulo extends Activity {

	private SQLiteDatabase bancoInfo;
	private Cursor cursor;
	private Button btAddSalvar,btCancela;
	private EditText etCadastroTitulo,etCadastroSenha;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adicionatitulo);
		setComponents();
		criaOuAbreBanco();
		
		
		btAddSalvar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				try{
					if(etCadastroSenha.getText().toString().equals("") && etCadastroTitulo.getText().toString().equals("")){
						mensagem("ERRO", "Campo vazio");
					}
					else{
						gravaDados(etCadastroTitulo.getText().toString(),etCadastroSenha.getText().toString());
						mensagem("Salvar","Salvo com sucesso");
												
						Intent it = new Intent("com.salvesenha.PROCESSO");
						startActivity(it);
					}
				}catch(Exception erro){}
			}
		});
		
		btCancela.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
	}
	
	private void setComponents(){
		
		btAddSalvar = (Button)findViewById(R.id.btAddSalvar);
		etCadastroSenha = (EditText)findViewById(R.id.etCadastroSenha);
		btCancela = (Button)findViewById(R.id.btCancela);
		etCadastroTitulo = (EditText)findViewById(R.id.etCadastroTitulo);
	}

	private void criaOuAbreBanco(){
		try{
			bancoInfo = openOrCreateDatabase("bancoInfo", MODE_PRIVATE, null);
			String sql = "CREATE TABLE IF NOT EXISTS dados(codigo INTEGER PRIMARY KEY, titulo TEXT, senha TEXTO);";
			bancoInfo.execSQL(sql);
			
		}catch(Exception erro){
			mensagem("ERRO","Erro ao criar/abrir banco de dados");
		}
	}
	
	public void gravaDados(String etLogin, String etKey){
		try{
			String sql = "INSERT INTO dados (titulo,senha) values ('" + etLogin + "','"
																 	+ etKey + "');'";
			bancoInfo.execSQL(sql);
			
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
	
}
