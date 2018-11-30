package com.example.pedrohenrique.listacompras.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pedrohenrique.listacompras.dao.BancoDados;
import com.example.pedrohenrique.listacompras.models.OnItemClickListener;
import com.example.pedrohenrique.listacompras.R;
import com.example.pedrohenrique.listacompras.adapter.ListasAdapter;
import com.example.pedrohenrique.listacompras.models.Listas;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TelaListas extends AppCompatActivity {

    private RecyclerView recyclerListas;
    private  List<Listas> listas = new ArrayList<>();
    private EditText input;
    private  ListasAdapter adapter;
    private  TextView somaTotal;
    private BancoDados banco;
    private SQLiteDatabase variavelBanco;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_listas);
        this.setTitle("Minha Lista");

        //Cria banco
        criarBanco();
        banco = new BancoDados(variavelBanco);

        //Pega os dados do banco e coloca em uma lista
        banco.PreencherLista(listas);


        somaTotal = (TextView) findViewById(R.id.totalLista);

        //Pega a referencia do botão Adicionar e dispara o metódo criar lista
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criaLista();
            }
        });


        //Pega a referência da recyvlerView
        recyclerListas = findViewById(R.id.recyclerListas);

        //Define Layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerListas.setLayoutManager(layoutManager);
        recyclerListas.setHasFixedSize(true);
        recyclerListas.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        //Cria um novo adapter
        adapter = new ListasAdapter(listas, somaTotal,this);
        recyclerListas.setAdapter(adapter);
        metodosLista();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.icone_compartilhar:
                compartilharWhats();
                break;
            case R.id.icone_sobre:
                menuSobre();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    public void compartilharWhats(){
        Intent sendIntent = new Intent();
        sendIntent.setPackage("com.whatsapp");
        sendIntent.setAction(Intent.ACTION_SEND);
        String texto = "";
        texto += "Lista de Compras - APP V 1.0 \n";
        texto += "Nome                    Quantidade \n";
        for(int i=0; i<listas.size(); i++)
        {
            String nome = listas.get(i).getNome().toUpperCase().toString();
            String quantidade = listas.get(i).getTextQtd().toUpperCase().toString();
            texto += nome + definirEspaco(nome.length()) +""+quantidade +" UN\n";
        }
        texto += "\nDevelop by PedroHSantana \n";
        sendIntent.putExtra(Intent.EXTRA_TEXT,texto);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public String definirEspaco(int tamanho){
        String espaco = "";
        if(tamanho < 15)
        {

            while(tamanho < 15){
                espaco += "*";
                tamanho ++;
            }
        }
        else{
            espaco = " ";
        }

        return espaco;
    }

    public void menuSobre()
    {
        Intent intencao = new Intent(this, TelaSobre.class);
        startActivity(intencao);
    }


    public void criaLista(){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Novo Item");
        alert.setIcon(R.mipmap.icone);
        alert.setMessage("Digite o nome");
        input = new EditText(this);
        alert.setView(input);
        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialogInterface, int which) {
                String txtNome = input.getText().toString();
                if(txtNome.equals("") || txtNome.equals(" ") || txtNome.equals("  ")|| txtNome.equals("  ")|| txtNome.equals("  ")) {
                    Toast toast = Toast.makeText(getApplication(), "Nome não pode ser vazio", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    listas.add(new Listas(txtNome));
                    banco.ExecutarComando("INSERT INTO Listas (nome, quantidade, preco, total, selecao) " +
                            "VALUES ('"+txtNome+"','0','0','0',0)");
                    listas.clear();
                    banco.PreencherLista(listas);
                    adapter.notifyItemInserted(listas.size());


                }
            }

        });
        alert.setNegativeButton("Não", null);
        alert.show();


    }

    public void editaLista(final int posicao){



        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Alterar Item");
        alert.setIcon(R.mipmap.icone);
        alert.setMessage("Novo Nome");
        input = new EditText(this);
        input.setText(listas.get(posicao).getNome());
        alert.setView(input);
        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialogInterface, int which) {
                String txtNome = input.getText().toString();
                if(txtNome.equals("")) {
                    Toast toast = Toast.makeText(getApplication(), "Nome não pode ser vazio", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    //listas.add(new Listas(txtNome, "0"));
                    String quantidade = listas.get(posicao).getTextQtd();
                    String preco = listas.get(posicao).getTextPreco();
                    String id = listas.get(posicao).getId();
                    String total = listas.get(posicao).getTotalItem();
                    Boolean selecao = listas.get(posicao).getSelecionado();
                    int opcao = 0;

                    if(selecao == true)
                    {
                        opcao = 1;
                    }

                    String sql = "UPDATE Listas SET nome ='"+txtNome+"',quantidade ='"+quantidade+"',preco ='"+preco+"',total ='"+total+"',selecao = '"+opcao+"' where id = "+id;

                    banco.ExecutarComando(sql);
                    listas.get(posicao).setNome(txtNome);
                    adapter.notifyItemChanged(posicao);

                }
            }

        });
        alert.setNegativeButton("Não", null);
        alert.show();


    }

    public void opcaoLista(final int posicao, final TelaListas contexto)
    {
       AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
       builder.setIcon(R.mipmap.icone);
       builder.setTitle(R.string.titulo_sub_menu)
               .setItems(R.array.menu, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                       if(which == 0)
                       {
                           contexto.editaLista(posicao);
                       }
                       else if(which == 1)
                       {
                           String sql = "DELETE FROM Listas WHERE id ="+contexto.listas.get(posicao).getId().toString();
                         //  Log.e("Celular", listas.get(posicao).getId().toString());
                          // Log.e("Celular ", posicao+" Id: "+listas.get(posicao).getId()+"NOME "+listas.get(posicao).getNome());


                            contexto.banco.ExecutarComando(sql);
                            contexto.listas.remove(posicao);
                            contexto.adapter.notifyItemRemoved(posicao);
                        }

                    }
                });

        builder.show();
    }

    public void metodosLista(){
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void somaValor(int position, String valor) {
                float preco = 0;
                float quantidade;
                float calculo = 0;
                float somatorio = 0;

                if(valor.equals("")) {
                    valor = 0+"";
                    listas.get(position).setTextPreco("0");
                }
                DecimalFormat ft = new DecimalFormat("0.#");
                if(!valor.equals(".") ) {
                    preco = Float.parseFloat(valor);
                    quantidade = Float.parseFloat(listas.get(position).getTextQtd().toString());
                    calculo  = preco*quantidade;
                    listas.get(position).setTextPreco(preco+"");
                    listas.get(position).setTotalItem(calculo+"");

                    for (int i=0; i<listas.size(); i++)
                    {
                        try {
                            somatorio += Float.parseFloat(listas.get(i).getTotalItem());
                        }catch (Exception e)
                        { }

                    }
                    somaTotal.setText(ft.format(somatorio)+"");
                    //Altera no banco
                    int posicao = position;
                    String txtNome = listas.get(posicao).getNome();
                    String quant = listas.get(posicao).getTextQtd();
                    String prec = listas.get(posicao).getTextPreco();
                    String id = listas.get(posicao).getId();
                    String total = listas.get(posicao).getTotalItem();
                    Boolean selecao = listas.get(posicao).getSelecionado();
                    int opcao = 0;

                    if(selecao == true)
                    {
                        opcao = 1;
                    }

                    String sql = "UPDATE Listas SET nome ='"+txtNome+"',quantidade ='"+quant+"',preco ='"+prec+"',total ='"+total+"',selecao = '"+opcao+"' where id = "+id;

                    banco.ExecutarComando(sql);


                }

            }

            @Override
            public void adicionaQuantidade(int position) {
                //  Log.e("Pedro", "Ok "+position+ "nome: "+listas.get(position).getNome());
                String valorQuantidade = listas.get(position).getTextQtd();
                int atual = Integer.parseInt(valorQuantidade);
                int soma = atual + 1;
                listas.get(position).setTextQtd(soma+"");
                //Altera no banco
                int posicao = position;
                String txtNome = listas.get(posicao).getNome();
                String quantidade = listas.get(posicao).getTextQtd();
                String preco = listas.get(posicao).getTextPreco();
                String id = listas.get(posicao).getId();
                String total = listas.get(posicao).getTotalItem();
                Boolean selecao = listas.get(posicao).getSelecionado();
                int opcao = 0;

                if(selecao == true)
                {
                    opcao = 1;
                }

                String sql = "UPDATE Listas SET nome ='"+txtNome+"',quantidade ='"+quantidade+"',preco ='"+preco+"',total ='"+total+"',selecao = '"+opcao+"' where id = "+id;

                banco.ExecutarComando(sql);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void removeQuantidade(int position) {
                String valorQuantidade = listas.get(position).getTextQtd();
                int atual = Integer.parseInt(valorQuantidade);
                int soma = 0;
                if(atual > 0) {
                    soma = atual - 1;
                }
                else {
                    soma = 0;
                }

                listas.get(position).setTextQtd(soma+"");
                //Altera no banco
                int posicao = position;
                String txtNome = listas.get(posicao).getNome();
                String quantidade = listas.get(posicao).getTextQtd();
                String preco = listas.get(posicao).getTextPreco();
                String id = listas.get(posicao).getId();
                String total = listas.get(posicao).getTotalItem();
                Boolean selecao = listas.get(posicao).getSelecionado();
                int opcao = 0;

                if(selecao == true)
                {
                    opcao = 1;
                }

                String sql = "UPDATE Listas SET nome ='"+txtNome+"',quantidade ='"+quantidade+"',preco ='"+preco+"',total ='"+total+"',selecao = '"+opcao+"' where id = "+id;

                banco.ExecutarComando(sql);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void removeItem(int position, TelaListas telaListas) {
                opcaoLista(position, telaListas);
            }

            @Override
            public void updadteCheckBox(int position) {
                int posicao = position;
                String txtNome = listas.get(posicao).getNome();
                String quantidade = listas.get(posicao).getTextQtd();
                String preco = listas.get(posicao).getTextPreco();
                String id = listas.get(posicao).getId();
                String total = listas.get(posicao).getTotalItem();
                Boolean selecao = listas.get(posicao).getSelecionado();
                int opcao = 0;

                if(selecao == true)
                {
                    opcao = 1;
                }

                String sql = "UPDATE Listas SET nome ='"+txtNome+"',quantidade ='"+quantidade+"',preco ='"+preco+"',total ='"+total+"',selecao = '"+opcao+"' where id = "+id;

                banco.ExecutarComando(sql);
            }
        });
    }

    public void criarBanco(){
            try {

                variavelBanco = openOrCreateDatabase("ListaCompras", MODE_PRIVATE,null);
            //    variavelBanco.execSQL("DROP TABLE IF EXISTS Listas");
                variavelBanco.execSQL("CREATE TABLE IF NOT EXISTS Listas" +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nome VARCHAR(30), " +
                        "quantidade VARCHAR(5), " +
                        "preco VARCHAR(5), "+
                        "total VARCHAR(7),"+
                        "selecao INTEGER)"

                );


            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }



    }




