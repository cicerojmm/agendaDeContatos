package alura.com.br.agendacontatos;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.jar.Manifest;

import alura.com.br.agendacontatos.adapter.AlunoAdapter;
import alura.com.br.agendacontatos.dao.AlunoDAO;
import alura.com.br.agendacontatos.models.Aluno;
import alura.com.br.agendacontatos.utils.ConverterAluno;
import alura.com.br.agendacontatos.utils.EnviaDadosServidor;

public class ListaAlunosActivity extends AppCompatActivity {
    private ListView listView;
    private Button buttonMaisAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        listView = (ListView) findViewById(R.id.listaContatos);
        buttonMaisAluno = (Button) findViewById(R.id.alunos_novoAluno);

        buttonMaisAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVaiParaFormAluno = new Intent(ListaAlunosActivity.this, FormularioActivity.class);

                startActivity(intentVaiParaFormAluno);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Aluno aluno = (Aluno) lista.getItemAtPosition(position);

                Intent formAlunos = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                formAlunos.putExtra("aluno", aluno);

                startActivity(formAlunos);
            }
        });

        registerForContextMenu(listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_lista_alunos, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.enviar_notas:
                new EnviaDadosServidor(this).execute();

                break;

            case R.id.baixar_provas:
                Intent intent = new Intent(this, ProvasActivity.class);
                startActivity(intent);

                break;

            case R.id.menu_mapa:
                Intent vaiParaMapa = new Intent(this, MapaActivity.class);
                startActivity(vaiParaMapa);

                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        carregaListaAluno();
    }

    public void carregaListaAluno() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.getAlunos();
        dao.close();

         //contexto, layout da lista, dados
        //ArrayAdapter<Aluno> arrayAdapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_activated_1, alunos);

        AlunoAdapter arrayAdapter = new AlunoAdapter(this, alunos);

        listView.setAdapter(arrayAdapter);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listView.getItemAtPosition(info.position);

        MenuItem itemLigar= menu.add("Ligar");

        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, android.Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(ListaAlunosActivity.this, new String[]{android.Manifest.permission.CALL_PHONE},
                            123);
                } else {
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + aluno.getTelefone()));
                    startActivity(intentLigar);
                }

                return false;
            }

        });



        MenuItem itemSMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + aluno.getTelefone()));
        itemSMS.setIntent(intentSMS);

        MenuItem itemMapa = menu.add("Visualizar no Mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q="+aluno.getEndereco()));
        itemMapa.setIntent(intentMapa);

        MenuItem itemSite = menu.add("Visualizar Site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);

        String site = aluno.getSite();

        if(!site.startsWith("http://")) {
            site = "http://" + site;
        }

        intentSite.setData(Uri.parse(site));

        itemSite.setIntent(intentSite);

        MenuItem deletar = menu.add("Deletar");


        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.deletar(aluno);

                Toast.makeText(ListaAlunosActivity.this, "Aluno " + aluno.getNome() + " Deletado com Sucesso!", Toast.LENGTH_SHORT).show();
                carregaListaAluno();
                return false;
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //realizar ligação aqui
    }
}
