package com.example.banco2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private TextView saldoText;
    private double saldoAtual = 1000.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        saldoText = findViewById(R.id.tv_balance_amount);
        saldoText.setText("R$ " + String.format("%.2f", saldoAtual));

        // Botões para Depósito, Saque e Pix
        Button btnDeposit = findViewById(R.id.btn_deposit);
        Button btnWithdraw = findViewById(R.id.btn_withdraw);
        Button btnPix = findViewById(R.id.btn_pix);

        // Ações dos botões
        btnDeposit.setOnClickListener(v -> openInputDialog("Depósito"));
        btnWithdraw.setOnClickListener(v -> openInputDialog("Saque"));
        btnPix.setOnClickListener(v -> Toast.makeText(HomeActivity.this, "Área Pix clicada", Toast.LENGTH_SHORT).show());
    }

    private void openInputDialog(final String tipoOperacao) {
        // Criando a view do dialog
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_input, null);
        final EditText editTextValue = dialogView.findViewById(R.id.editTextValue);
        final TextView labelInput = dialogView.findViewById(R.id.label_input);
        labelInput.setText("Digite o valor para " + tipoOperacao);

        // Construindo o AlertDialog com o estilo CustomDialogTheme
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialogTheme);
        builder.setView(dialogView);
        builder.setCancelable(true);

        // Removendo os botões padrão
        builder.setPositiveButton(null, null); // Remover o botão de Confirmar padrão
        builder.setNegativeButton(null, null); // Remover o botão de Cancelar padrão

        // Criando o AlertDialog
        AlertDialog dialog = builder.create();

        // Configurar os botões personalizados
        Button btnConfirmar = dialogView.findViewById(R.id.btnConfirm);
        Button btnCancelar = dialogView.findViewById(R.id.btnCancel);

        // Ação do botão Confirmar
        btnConfirmar.setOnClickListener(v -> {
            String valorString = editTextValue.getText().toString().trim();
            if (valorString.isEmpty()) {
                Toast.makeText(HomeActivity.this, "Por favor, insira um valor.", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    double valor = Double.parseDouble(valorString);

                    if (tipoOperacao.equals("Depósito")) {
                        saldoAtual += valor;
                        Toast.makeText(HomeActivity.this, "Depósito de R$ " + String.format("%.2f", valor) + " realizado com sucesso.", Toast.LENGTH_SHORT).show();
                    } else if (tipoOperacao.equals("Saque")) {
                        if (valor <= saldoAtual) {
                            saldoAtual -= valor;
                            Toast.makeText(HomeActivity.this, "Saque de R$ " + String.format("%.2f", valor) + " realizado com sucesso.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(HomeActivity.this, "Saldo insuficiente para o saque.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    // Atualizando o saldo na tela
                    saldoText.setText("R$ " + String.format("%.2f", saldoAtual));

                } catch (NumberFormatException e) {
                    Toast.makeText(HomeActivity.this, "Valor inválido. Por favor, insira um número válido.", Toast.LENGTH_SHORT).show();
                }
            }
            dialog.dismiss(); // Fecha o diálogo após a confirmação
        });

        // Ação do botão Cancelar
        btnCancelar.setOnClickListener(v -> dialog.dismiss()); // Fecha o diálogo se o botão de cancelar for clicado

        // Exibindo o dialog
        dialog.show();
    }
}
