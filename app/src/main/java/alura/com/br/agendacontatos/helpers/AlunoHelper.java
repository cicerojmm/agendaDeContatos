package alura.com.br.agendacontatos.helpers;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import alura.com.br.agendacontatos.FormularioActivity;
import alura.com.br.agendacontatos.R;
import alura.com.br.agendacontatos.models.Aluno;

/**
 * Created by root on 20/02/17.
 */

public class AlunoHelper {
    private final EditText campoNome;
    private final EditText campoEndereco;
    private final EditText campoTelefone;
    private final EditText campoSite;
    private final RatingBar campoNota;
    private final ImageView campoFoto;
    private Aluno aluno;

    public AlunoHelper(FormularioActivity activity) {
        campoNome = (EditText) activity.findViewById(R.id.form_nome);
        campoEndereco = (EditText) activity.findViewById(R.id.form_endereco);
        campoTelefone = (EditText) activity.findViewById(R.id.form_telefone);
        campoSite = (EditText) activity.findViewById(R.id.form_site);
        campoNota = (RatingBar) activity.findViewById(R.id.form_nota);
        campoFoto =  (ImageView) activity.findViewById(R.id.form_foto);

        aluno = new Aluno();
    }

    public Aluno getAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        aluno.setCaminhoFoto((String) campoFoto.getTag());

        return aluno;
    }

    public void preencheForm(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoNota.setProgress(((int) aluno.getNota()));
        carregaImagem(aluno.getCaminhoFoto());

        this.aluno = aluno;

    }

    public void carregaImagem(String caminhoFoto) {

        if(caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);

            bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);

            campoFoto.setImageBitmap(bitmap);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(caminhoFoto);
        }

    }
}
