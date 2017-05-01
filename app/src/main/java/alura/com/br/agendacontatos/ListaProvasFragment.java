package alura.com.br.agendacontatos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import alura.com.br.agendacontatos.models.Prova;

/**
 * Created by root on 26/04/17.
 */

public class ListaProvasFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_prova, container, false);

        List<String> topicosPort = Arrays.asList("Sujeito", "Leitura", "Textos");
        Prova provaPortugues = new Prova("Portugues", "25/05/2017", topicosPort);

        List<String> topicosMat = Arrays.asList("Equações", "Trigonometria", "Pamonha");
        Prova provaMatematica = new Prova("Matematica", "24/05/2017", topicosMat);

        List<Prova> provas = Arrays.asList(provaPortugues, provaMatematica);
        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(getContext(), android.R.layout.simple_list_item_activated_1, provas);

        ListView listView = (ListView) view.findViewById(R.id.provas_lista);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Prova prova = (Prova) lista.getItemAtPosition(position);

                ProvasActivity provasActivity = (ProvasActivity) getActivity();

                provasActivity.selecionaProva(prova);
            }
        });

        return view;
    }
}
