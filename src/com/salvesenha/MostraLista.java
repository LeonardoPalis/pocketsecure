package com.salvesenha;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class MostraLista extends Activity{

	private SQLiteDatabase bancoInfo;
	private Cursor cursor;
	private EditText etModUser, etModSenha;
	private ListView lista;
	private Button button;
	private TextView tvListaCod, tvNull;
	public String title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mostralista);
		setComponents();
		criaOuAbreBanco();
		preencheLista();
		

	}
	
	@Override
	public void onBackPressed() {
		Intent it = new Intent(this, Processamento.class);
		startActivity(it);
		super.onBackPressed();
	}

	private void mensagem(String titulo, String texto){
		
		AlertDialog.Builder mensage = new AlertDialog.Builder(this);
		mensage.setTitle(titulo);
		mensage.setMessage(texto);
		mensage.setNegativeButton("OK", null);
		mensage.show();
		
	}
	
	private void preencheLista(){
		
		
		cursor = bancoInfo.query("dados", new String[] {"codigo","titulo","senha"}, null, null, null, null, null);
		cursor.moveToFirst();
		String a = "SELECIONE";
		final String[] dadosSQL = new String[cursor.getCount()];
		final String[] dadosSQLSenha = new String[cursor.getCount()];
		final String[] dadosSQLCodigo = new String[cursor.getCount()];
		int tamanhoSQL = dadosSQL.length;
		int i = 0, j = 0;
		
		try{
			do{	
					
					dadosSQLCodigo[i] = cursor.getString(0);
					dadosSQL[i] = cursor.getString(1);
					dadosSQLSenha[i] = cursor.getString(2);
					i++;
					tamanhoSQL--;
				
			}while(cursor.moveToNext());
		}catch(Exception erro){}
		

		View v = lista;
		//v.setBackgroundColor(R.color.laranja);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,dadosSQL);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		lista.setAdapter(adapter);	
		verificaDados();
		lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
		
					Bundle parametro = new Bundle();
					parametro.putString("user", dadosSQL[position]);
					parametro.putString("senha", dadosSQLSenha[position]);
					parametro.putString("codigo", dadosSQLCodigo[position]);
					
					Intent it = new Intent("com.salvesenha.MODIFICADADOS");
					
					it.putExtras(parametro);
					startActivity(it);
			}
		});
		
	}

	private void criaOuAbreBanco(){
		try{
			bancoInfo = openOrCreateDatabase("bancoInfo", MODE_PRIVATE, null);
			String sql = "CREATE TABLE IF NOT EXISTS dados(codigo INTEGER PRIMARY KEY, titulo TEXT, senha TEXT);";
			bancoInfo.execSQL(sql);
		}catch(Exception erro){}
	}
	
	public void verificaDados(){
		try{
		bancoInfo = openOrCreateDatabase("bancoInfo", MODE_PRIVATE, null);
		cursor = bancoInfo.rawQuery("Select * from usuario", null);
		
		if(cursor.getCount() != 0){
			cursor.moveToFirst();

		}
		else{
			tvNull.setText("Nenhum titulo encontrado");
		}
		}catch(Exception e){}
		
	}
	
	
	private void setComponents(){
		tvNull = (TextView)findViewById(R.id.tvNull);
		lista = (ListView)findViewById(R.id.lista);
		button= (Button)findViewById(R.id.btSee);
		etModUser = (EditText)findViewById(R.id.etModUser);
		etModSenha = (EditText)findViewById(R.id.etModSenha);
		tvListaCod = (TextView)findViewById(R.id.tvListaCod);
	}
	
}
