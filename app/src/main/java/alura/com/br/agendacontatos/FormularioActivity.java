package alura.com.br.agendacontatos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import alura.com.br.agendacontatos.dao.AlunoDAO;
import alura.com.br.agendacontatos.helpers.AlunoHelper;
import alura.com.br.agendacontatos.models.Aluno;


public class FormularioActivity extends AppCompatActivity {
    public static final int CODIGO_CAMERA = 567;
    private Button buttonSalvar;
    private Button buttonFoto;
    private Aluno aluno;
    private AlunoHelper alunoHelper;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        alunoHelper = new AlunoHelper(this);

        Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");

        if(aluno != null) {
            alunoHelper.preencheForm(aluno);
        }

        buttonFoto = (Button) findViewById(R.id.form_bntfoto);

        buttonFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1Camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                intent1Camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intent1Camera, CODIGO_CAMERA);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == CODIGO_CAMERA && resultCode == Activity.RESULT_OK) {
          alunoHelper.carregaImagem(caminhoFoto);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.menu_formulario, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_form_ok:
                aluno = alunoHelper.getAluno();
                AlunoDAO alunoDAO = new AlunoDAO(this);


                if(aluno.getId() != 0) {
                    alunoDAO.altera(aluno);
                } else {
                    alunoDAO.insereAluno(aluno);
                }

                alunoDAO.close();

                Toast.makeText(FormularioActivity.this, "Aluno "+ aluno.getNome()+ " Salvo Com Sucesso!", Toast.LENGTH_SHORT).show();
                finish();

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
