package com.salvesenha;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Processamento extends Activity{
	public Processamento(){};
	private Button btModificar, btNovoTitulo,btVer;
	private String title;
	private SQLiteDatabase bancoInfo = null;
	private Cursor cursor2;
	private Spinner spinner;
	private TextView tvMostraCodigo;
	private EditText etMostraUser, etMostraSenha;
	private ImageView imageInterrogacao;
	private int pos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.processamento);
		setComponents();

		criaOuAbraBanco();

		btNovoTitulo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent("com.salvesenha.ADDTITULO");
				startActivity(it);
				
			}
		} );
		
		btVer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent("com.salvesenha.LISTA");
				startActivity(it);
				
			}
		} );
		
		
		
	}
	
	@Override
	public void onBackPressed() {
		Intent it = new Intent(this, login.class);
		startActivity(it);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.itensmenu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private void setComponents(){
				
		btVer = (Button)findViewById(R.id.btSee);
		tvMostraCodigo = (TextView)findViewById(R.id.tvMostraCodigo);
		btNovoTitulo = (Button)findViewById(R.id.btNovoTitulo);
		
	}
	
	/*private void preencheSpinner(){
		
		

		cursor2 = bancoInfo.query("dados", new String[] {"codigo","titulo","senha"}, null, null, null, null, null);
		cursor2.moveToFirst();
		
		final String[] dadosSQL = new String[cursor2.getCount()];
		final String[] dadosSQLSenha = new String[cursor2.getCount()];
		final String[] dadosSQLCodigo = new String[cursor2.getCount()];
		int tamanhoSQL = dadosSQL.length;
		int i = 0, j = 0;
		
		try{
			do{	
					dadosSQLCodigo[i] = cursor2.getString(0);
					dadosSQL[i] = cursor2.getString(1);
					dadosSQLSenha[i] = cursor2.getString(2);
					i++;
					tamanhoSQL--;
				
			}while(cursor2.moveToNext());
			
		}catch(Exception e){}
		
		try{
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, dadosSQL);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);
			
		}catch(Exception erro){}
		
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					 int position, long id) {
				try{
					etMostraUser.setText(dadosSQL[position]);
					etMostraSenha.setText(dadosSQLSenha[position]);
					tvMostraCodigo.setText(dadosSQLCodigo[position]);
					
				}catch(Exception erro){mensagem("ERRO","Não foi possivel modificar");}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		btModificar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!etMostraSenha.getText().toString().equals("") && !etMostraUser.getText().toString().equals("")){
					modificarRegistro();
					preencheSpinner();
				}
				else
					mensagem("ERRO","Não existe titulos para serem modificados ou algum campo está vazio");
			}
		});
		
		
		
		btDeletar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!etMostraSenha.getText().toString().equals("") && !etMostraUser.getText().toString().equals("")){
					deletarRegistro();
					preencheSpinner();
					etMostraSenha.setText("");
					etMostraUser.setText("");
				}
				else
					mensagem("ERRO","Não existe titulos para serem deletados");
			}
		});
	}*/
	
	private void modificarRegistro(){
		try{
			String sql = "UPDATE dados SET titulo = '"
					+ etMostraUser.getText().toString() + "', senha = '"
					+ etMostraSenha.getText().toString() + "' where codigo ='"
					+ tvMostraCodigo.getText().toString() + "' ;'";
			bancoInfo.execSQL(sql);
			
			mensagem("Modificar","Salvo com sucesso!");
		}catch(Exception erro){
			mensagem("ERRO","Não foi possivel modificar");
		}
		
	}
	
	private void deletarRegistro(){
		try{
			String sql = "DELETE FROM dados where codigo = '"
					+ tvMostraCodigo.getText().toString() + "' ;'";
			bancoInfo.execSQL(sql);
			
			mensagem("Deletar","Deletado com sucesso!");
		}catch(Exception erro){
			mensagem("ERRO","Não foi possivel modificar");
		}
		
	}

	private void criaOuAbraBanco(){
		try{
			bancoInfo = openOrCreateDatabase("bancoInfo", MODE_PRIVATE, null);
			String sql = "CREATE TABLE IF NOT EXISTS dados(codigo INTEGER PRIMARY KEY, titulo TEXT, senha TEXTO);";
			bancoInfo.execSQL(sql);
			
		}catch(Exception erro){
			mensagem("ERRO","Erro ao criar/abrir banco de dados");
		}
	}
	
	public String verificaSenha(int ponteiro){
		try{
			cursor2 = bancoInfo.query("dados", new String[] {"titulo","senha"}, null, null, null, null, null);
			cursor2.moveToFirst();
			title = cursor2.getString(ponteiro);

		}catch(Exception erro){}
		return title;
	}
	
	private void gravaDados(){
		
		try{
			String sql = "INSERT INTO dados (titulo,senha) values ('teste','111');";
			bancoInfo.execSQL(sql);
			String sql1 = "INSERT INTO dados (titulo,senha) values ('teste2','222');";
			bancoInfo.execSQL(sql1);
		}catch(Exception erro){
			mensagem("ERRO","Não foi possivel salvar");
		}
	}
	
	private void mensagem(String titulo, String texto){
		
		AlertDialog.Builder mensage = new AlertDialog.Builder(this);
		mensage.setTitle(titulo);
		mensage.setMessage(texto);
		mensage.setNegativeButton("Ok",null);
		mensage.show();
	}
}
