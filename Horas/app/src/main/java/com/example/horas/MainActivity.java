package com.example.horas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.horas.modelo.Horas;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    EditText editData, editHora_Inicial, editHora_Final, editHora_Cobrada;
    ListView listV_Dados;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private List<Horas> listHoras = new ArrayList<Horas>();
    private ArrayAdapter<Horas> arrayAdapterHoras;

    Horas horaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editData = (EditText) findViewById(R.id.editData);
        editHora_Inicial = (EditText) findViewById(R.id.editHora_Inicial);
        editHora_Final = (EditText) findViewById(R.id.editHora_Final);
        editHora_Cobrada = (EditText) findViewById(R.id.editHora_Cobrada);
        listV_Dados = (ListView) findViewById(R.id.listV_dados);

        inicializarFirebase();
        eventoDatabase();

        listV_Dados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                horaSelecionada = (Horas) parent.getItemAtPosition(position);
                editData.setText(horaSelecionada.getData());
                editHora_Inicial.setText(horaSelecionada.getHora_inicial());
                editHora_Final.setText((horaSelecionada.getHora_final()));
                editHora_Cobrada.setText((horaSelecionada.getHora_cobrada()));
            }
        });
    }



        private void eventoDatabase() {
        databaseReference.child("Hora").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listHoras.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Horas p = objSnapshot.getValue(Horas.class);
                    listHoras.add(p);
                }
                arrayAdapterHoras = new ArrayAdapter<Horas>(MainActivity.this, android.R.layout.simple_list_item_1, listHoras);
                listV_Dados.setAdapter(arrayAdapterHoras);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if (id == R.id.menu_novo){
            Horas p = new Horas();
            p.setUid(UUID.randomUUID().toString());
           p.setData(editData.getText().toString());
           p.setHora_inicial(editHora_Inicial.getText().toString());
           p.setHora_final(editHora_Final.getText().toString());
           p.setHora_cobrada(editHora_Cobrada.getText().toString());
           databaseReference.child("Hora").child(p.getUid()).setValue(p);
           limparCampos();
        } else if (id == R.id.menu_atualiza) {
            Horas p = new Horas();
            p.setUid(horaSelecionada.getUid());
            p.setData(editData.getText().toString().trim());
            p.setHora_inicial(editHora_Inicial.getText().toString().trim());
            p.setHora_final(editHora_Final.getText().toString().trim());
            p.setHora_cobrada(editHora_Cobrada.getText().toString().trim());
            databaseReference.child("Hora").child(p.getUid()).setValue(p);
            limparCampos();
        }else if (id == R.id.menu_deleta){
            Horas p = new Horas();
            p.setUid(horaSelecionada.getUid());
            databaseReference.child("Hora").child(p.getUid()).removeValue();
            limparCampos();
        }
        return true;
    }

    private void limparCampos() {
        editData.setText("");
        editHora_Inicial.setText("");
        editHora_Final.setText("");
        editHora_Cobrada.setText("");
    }
}
