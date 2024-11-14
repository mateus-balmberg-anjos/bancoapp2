package com.example.banco2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class BancoDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "banco.db";
    private static final int DATABASE_VERSION = 1;

    // Tabela e colunas
    public static final String TABLE_NAME = "usuarios";
    public static final String COL_ID = "id";
    public static final String COL_CPF = "cpf";
    public static final String COL_SENHA = "senha";

    // Comando de criação da tabela
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_CPF + " TEXT, " +
            COL_SENHA + " TEXT)";

    public BancoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

        // Inserir dados padrão para testes
        ContentValues values = new ContentValues();
        values.put(COL_CPF, "123");  // CPF de exemplo
        values.put(COL_SENHA, "123");  // Senha padrão
        db.insert(TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Método para verificar login
    public boolean checkLogin(String cpf, String senha) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COL_CPF + "=? AND " + COL_SENHA + "=?",
                new String[]{cpf, senha}, null, null, null);
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isValid;
    }

    // Método para atualizar CPF e senha
    public void updateDados(String novoCpf, String novaSenha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_CPF, novoCpf);
        values.put(COL_SENHA, novaSenha);

        db.update(TABLE_NAME, values, COL_CPF + "=?", new String[]{"12345678900"}); // Substitui os dados do CPF 12345678900
        db.close();
    }
}
