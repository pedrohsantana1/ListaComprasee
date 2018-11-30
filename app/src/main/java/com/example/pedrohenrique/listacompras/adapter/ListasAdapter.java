package com.example.pedrohenrique.listacompras.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pedrohenrique.listacompras.models.OnItemClickListener;
import com.example.pedrohenrique.listacompras.R;
import com.example.pedrohenrique.listacompras.activity.TelaListas;
import com.example.pedrohenrique.listacompras.models.Listas;

import java.util.List;

public class ListasAdapter extends RecyclerView.Adapter<ListasAdapter.MyViewHolder> {

    private List<Listas> listas;

    private TextView somaTotal;
    private float preco;
    private float quantidade;
    private float calculo = 0;
    private TelaListas telaListas;
    private OnItemClickListener mListener;

    /*
    public interface OnItemClickListener{
        void somaValor(int position, String valor);
        void adicionaQuantidade(int position);
        void removeQuantidade(int position);
    }
*/
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }




    public ListasAdapter(List<Listas> list, TextView soma, TelaListas telaListas) {

        listas = list;
        this.somaTotal = soma;
        this.telaListas = telaListas;

        //Torna o formato da soma total com apenas uma casa após a virgula


    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista, parent, false);



        return new MyViewHolder(itemLista, mListener);
    }
    Listas l = null;
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        l = listas.get(position);
        holder.txtNome.setText(l.getNome());
        holder.txtQtd.setText(l.getTextQtd());
        holder.preco.setText(l.getTextPreco());

        holder.bind(position);



    }

    @Override
    public int getItemCount() {
        return listas.size();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNome;
        private TextView txtQtd;
        private Button btnMais;
        private Button btnMenos;
        private CheckBox checkCalcula;
        private EditText preco;




        public MyViewHolder(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);

            txtNome = itemView.findViewById(R.id.txtNome);
            txtQtd = itemView.findViewById(R.id.txtQtd);
            btnMais =  itemView.findViewById(R.id.btnMais);
            btnMenos = itemView.findViewById(R.id.btnMenos);
            checkCalcula = itemView.findViewById(R.id.checkCalcula);
            preco = itemView.findViewById(R.id.campoValor);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(listener != null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.removeItem(position, telaListas);
                            listener.somaValor(position, "0");
                        }
                    }
                    return false;
                }
            });

            btnMais.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.adicionaQuantidade(position);
                        }
                    }
                }
            });

            btnMenos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.removeQuantidade(position);
                        }
                    }
                }
            });

            checkCalcula.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listas.get(position).getSelecionado())
                    {
                        checkCalcula.setChecked(false);
                        listas.get(position).setSelecionado(false);
                        listener.updadteCheckBox(position);

                    }
                    else{
                        checkCalcula.setChecked(true);
                        listas.get(position).setSelecionado(true);
                        listener.updadteCheckBox(position);
                    }
                }
            });





            preco.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                 //   String texto = String.valueOf(charSequence.charAt(i));
                    try {
                        Log.e("Caractere: ",String.valueOf(charSequence.charAt(i)));
                    }catch (Exception e)
                    {

                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if(listener != null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.somaValor(position, preco.getText().toString());




                        }
                    }
                }
            });




        }
        void bind(int posicao)
        {
            if(listas.get(posicao).getSelecionado()){
                checkCalcula.setChecked(true);
            }
            else{
                checkCalcula.setChecked(false);
            }
        }


    }




}
