package com.example.banco2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText cpfField, senhaField;
    private BancoDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  // Verifique se o nome do arquivo XML está correto

        // Inicializando as views
        cpfField = findViewById(R.id.cpfField);
        senhaField = findViewById(R.id.senhaField);
        dbHelper = new BancoDatabaseHelper(this);

        // Configurando o botão "Entrar"
        Button entrarButton = findViewById(R.id.btn_login);
        entrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pegando os valores inseridos pelo usuário
                String cpf = cpfField.getText().toString();
                String senha = senhaField.getText().toString();

                // Verificando se CPF e senha são válidos
                if (cpf.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else if (dbHelper.checkLogin(cpf, senha)) {
                    // Navegar para a tela principal se o login for bem-sucedido
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();  // Finaliza a tela de login para que o usuário não volte para ela
                } else {
                    // Exibe mensagem caso os dados de login estejam incorretos
                    Toast.makeText(LoginActivity.this, "CPF ou Senha incorretos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
