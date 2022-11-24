package com.example.aarafaelyukilistview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    //Rafael Bianchi Presta e Yuki Hiroji Oyama

    //Atributos representando os objetos gráficos da interface

    private EditText txtMesa;
    private EditText txtItem;
    private EditText txtProduto;
    private Button btnInserir;
    private ListView listaProdutos;

    //Atributo que irá referenciar para o nó raiz do BD no firebase;
    private DatabaseReference BD = FirebaseDatabase.getInstance().getReference();

    //Atributo que é a referência para adapter do Firebase;
    private ProdutosAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ligando os atributos com os objetos na interface gráfica
        txtMesa = findViewById(R.id.txtMesa);
        txtItem = findViewById(R.id.txtItem);
        txtProduto = findViewById(R.id.txtProduto);
        btnInserir = findViewById(R.id.btnInserir);
        listaProdutos = findViewById(R.id.lista);

        btnInserir.setOnClickListener(new EscutadorBotaoInserir());

        DatabaseReference atividadeList = BD.child("restauranteLista");

        FirebaseListOptions<Item> options = new FirebaseListOptions.Builder<Item>().setLayout(R.layout.item_lista).setQuery(atividadeList, Item.class).setLifecycleOwner(this).build();

        adapter = new ProdutosAdapter(options);

        listaProdutos.setAdapter(adapter);


        adapter = new ProdutosAdapter(options);

        listaProdutos.setAdapter(adapter);

       listaProdutos.setOnItemClickListener(new EscutadorCliqueLista());

        EscutadorCliqueLista el = new EscutadorCliqueLista();

        listaProdutos.setOnItemClickListener( el );
        listaProdutos.setOnItemLongClickListener( el );









    }

    private class EscutadorBotaoInserir implements View.OnClickListener{
        @Override

        public void onClick(View view){
            String mesa, item, produto;

            DatabaseReference atividadeList = BD.child("restauranteLista");

            mesa = txtMesa.getText().toString();
            item = txtItem.getText().toString();
            produto = txtProduto.getText().toString();

            String chave = atividadeList.push().getKey();

            Item i = new Item(chave, mesa, item, produto);

            atividadeList.child(chave).setValue(i);

            txtMesa.setText("");
            txtItem.setText("");
            txtProduto.setText("");

            txtMesa.requestFocus();

        }

    }


    private class ProdutosAdapter extends FirebaseListAdapter<Item> {


        public ProdutosAdapter(FirebaseListOptions options) {
            super(options);
        }



        @Override
        protected void populateView(View v, Item i, int position) {





            TextView lblMesa  = v.findViewById( R.id.lblMesa );
            TextView lblItem = v.findViewById( R.id.lblItem);
            TextView lblProduto = v.findViewById(R.id.lblProduto);
            TextView lblAtendido = v.findViewById(R.id.lblAtendido);


            lblMesa.setText(i.getMesa());
            lblItem.setText(i.getItemProd());
            lblProduto.setText(i.getProduto());

            if(i.isAtendido() == false){
                int cor = 0;
                cor = Color.RED;
                lblAtendido.setTextColor(cor);
                lblAtendido.setText("NÃO");
            }


        }
    }

    private class EscutadorCliqueLista implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

           Item item = adapter.getItem(i);

           String chave = item.getChave();

           TextView lblAtendido = view.findViewById(R.id.lblAtendido);

            int cor = 0;
            cor = Color.GREEN;
            lblAtendido.setTextColor(cor);
            lblAtendido.setText("SIM");

            item.setAtendido(true);

            DatabaseReference atividadeList = BD.child("restauranteLista").child(chave);
            atividadeList.setValue(item);





        }

        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

            Item item = adapter.getItem(i);

            String chave = item.getChave();

            DatabaseReference atividadeList = BD.child("restauranteLista");;
            DatabaseReference m = atividadeList.child(chave);
            m.setValue(null);

            return true;
        }



    }


}