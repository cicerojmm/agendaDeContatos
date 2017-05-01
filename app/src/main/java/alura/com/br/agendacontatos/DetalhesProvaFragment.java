package alura.com.br.agendacontatos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import alura.com.br.agendacontatos.models.Prova;


public class DetalhesProvaFragment extends Fragment {

    private TextView campoMateria;
    private TextView campoData;
    private ListView lista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhes_prova, container, false);

        campoMateria = (TextView) view.findViewById(R.id.detalhes_prova_materia);
        campoData = (TextView) view.findViewById(R.id.detalhes_prova_data);
        lista = (ListView) view.findViewById(R.id.detalhes_prova_topicos);

        Bundle parametros = getArguments();

        if(parametros != null) {
            Prova prova = (Prova) parametros.getSerializable("prova");
            populaComposCom(prova);

        }

        return view;
    }

    public void populaComposCom(Prova prova) {
        campoMateria.setText(prova.getMateria());
        campoData.setText(prova.getData());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, prova.getTopicos());

        lista.setAdapter(adapter);
    }

}
