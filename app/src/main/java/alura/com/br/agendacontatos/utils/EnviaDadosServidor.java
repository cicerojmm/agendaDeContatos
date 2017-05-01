package alura.com.br.agendacontatos.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import alura.com.br.agendacontatos.dao.AlunoDAO;
import alura.com.br.agendacontatos.models.Aluno;

/**
 * Created by root on 14/04/17.
 */

public class EnviaDadosServidor extends AsyncTask<Void, Void, String> {

    private Context context;
    private ProgressDialog alertDialog;

    public EnviaDadosServidor( Context context){

        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        alertDialog = ProgressDialog.show(context,"Aguarde" , "Enviando para o servidor ...", true, true);
        alertDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {

        WebClient webClient = new WebClient();
        ConverterAluno converter = new ConverterAluno();
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.getAlunos();
        dao.close();
        String json = converter.toJSON(alunos);
        String resposta = webClient.post(json);


        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        alertDialog.dismiss();
        Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();

    }

}
