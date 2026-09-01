package br.edu.ifms;

public class ContaBancaria {
    private double saldo;

    // Construtor que recebe saldo inicial
    public ContaBancaria(double saldoInicial) {
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("O saldo inicial não pode ser negativo.");
        }
        this.saldo = saldoInicial;
    }

    // Método para depositar
    public void depositar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser maior que zero.");
        }
        this.saldo += valor;
    }

    // Método para sacar
    public void sacar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor de saque deve ser maior que zero.");
        }
        if (valor > this.saldo) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }
        this.saldo -= valor;
    }

    // Método para consultar saldo
    public double consultarSaldo() {
        return this.saldo;
    }
}