package alura.com.br.agendacontatos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import alura.com.br.agendacontatos.models.Prova;

public class DetalhesProvaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_prova);

        Intent intent = getIntent();
        Prova prova = (Prova) intent.getSerializableExtra("prova");

        TextView campoMateria = (TextView) findViewById(R.id.detalhes_prova_materia);
        TextView campoData = (TextView) findViewById(R.id.detalhes_prova_data);
        ListView lista = (ListView) findViewById(R.id.detalhes_prova_topicos);

        campoMateria.setText(prova.getMateria());
        campoData.setText(prova.getData());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, prova.getTopicos());

        lista.setAdapter(adapter);
    }
}
