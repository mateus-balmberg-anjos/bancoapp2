package com.example.banco2;

public class Pagamentos {

    private double saldo;

    // Construtor
    public Pagamentos(double saldoInicial) {
        saldo = saldoInicial;
    }

    // Método para realizar depósito
    public String realizarDeposito(double valor) {
        if (valor > 0) {
            saldo += valor; // Atualiza o saldo
            return "Depósito de R$" + valor + " realizado com sucesso!";
        } else {
            return "Valor inválido para depósito!";
        }
    }

    // Método para realizar saque
    public String realizarSaque(double valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor; // Atualiza o saldo
            return "Saque de R$" + valor + " realizado com sucesso!";
        } else if (valor > saldo) {
            return "Saldo insuficiente para saque!";
        } else {
            return "Valor inválido para saque!";
        }
    }

    // Método para retornar o saldo atual
    public double getSaldo() {
        return saldo;
    }
}
