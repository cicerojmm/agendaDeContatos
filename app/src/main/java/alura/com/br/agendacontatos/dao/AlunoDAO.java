package alura.com.br.agendacontatos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import alura.com.br.agendacontatos.models.Aluno;

/**
 * Created by root on 21/02/17.
 */

public class AlunoDAO extends SQLiteOpenHelper {
    private static final String nomeDB = "db_agenda";
    private static final int versaoDB = 8;

    public AlunoDAO(Context context) {
        super(context, nomeDB, null, versaoDB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        sql = "CREATE TABLE Alunos(id INTEGER PRIMARY KEY, nome TEXT NOT NULL, telefone TEXT, endereco TEXT, site TEXT, " +
                "nota REAL, caminhoFoto TEXT);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";

        switch (oldVersion) {
            case 6:
                sql = "ALTER TABLE Alunos ADD COLUMN caminhoFoto TEXT";
                db.execSQL(sql);
        }

    }

    public void insereAluno(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getContentValuesAluno(aluno);

        db.insert("Alunos", null, dados);

    }

    @NonNull
    private ContentValues getContentValuesAluno(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        dados.put("caminhoFoto", aluno.getCaminhoFoto());
        return dados;
    }

    public List<Aluno> getAlunos() {
        String sql = "SELECT * FROM Alunos";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        List<Aluno> alunos = new ArrayList<>();

        while(cursor.moveToNext()) {
            Aluno aluno = new Aluno();

            aluno.setId(cursor.getLong(cursor.getColumnIndex("id")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            aluno.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            aluno.setSite(cursor.getString(cursor.getColumnIndex("site")));
            aluno.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
            aluno.setCaminhoFoto(cursor.getString(cursor.getColumnIndex("caminhoFoto")));

            alunos.add(aluno);

        }

        cursor.close();

        return alunos;

    }

    public void deletar(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        //String id = String.valueOf(aluno.getId());
        String[] params = {aluno.getId().toString()};

        db.delete("Alunos", "id = ?", params);
    }

    public void altera(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getContentValuesAluno(aluno);

        String[] params = {aluno.getId().toString()};
        db.update("Alunos", dados, "id = ?", params);
    }

    public boolean ehAluno(String telefone) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Alunos Where telefone = ?", new String[]{telefone});

        int resultado = cursor.getCount();
        cursor.close();;

        return resultado > 0;


    }
}
