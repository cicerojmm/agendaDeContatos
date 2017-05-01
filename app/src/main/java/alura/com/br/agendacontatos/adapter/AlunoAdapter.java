package alura.com.br.agendacontatos.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import alura.com.br.agendacontatos.R;
import alura.com.br.agendacontatos.models.Aluno;

/**
 * Created by root on 12/04/17.
 */

public class AlunoAdapter extends BaseAdapter {
    private List<Aluno> alunos;
    private Context context;

    public AlunoAdapter(Context context, List<Aluno> alunos) {
        this.alunos = alunos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aluno aluno = alunos.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;
        if(convertView == null) {
            view = inflater.inflate(R.layout.list_item_aluno, parent, false);
        }


        TextView nome = (TextView) view.findViewById(R.id.item_nome);
        TextView telefone = (TextView) view.findViewById(R.id.item_telefone);
        ImageView campoFoto = (ImageView) view.findViewById(R.id.item_foto);
        TextView campoSite = (TextView) view.findViewById(R.id.item_site);
        TextView campoEndereco = (TextView) view.findViewById(R.id.item_endereco);

        nome.setText(aluno.getNome());
        telefone.setText(aluno.getTelefone());

        if(campoEndereco != null && campoSite != null) {
            campoSite.setText(aluno.getSite());
            campoEndereco.setText(aluno.getEndereco());
        }


        String caminhoFoto = aluno.getCaminhoFoto();
        if(caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);

            bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);

            campoFoto.setImageBitmap(bitmap);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }


        return view;
    }
}
