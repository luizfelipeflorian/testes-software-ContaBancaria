package br.edu.ifms;

public class ContaBancaria {
    private double saldo;
    private EmailService emailService;

    // Construtor atualizado recebendo a dependência
    public ContaBancaria(double saldoInicial, EmailService emailService) {
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("O saldo inicial não pode ser negativo.");
        }
        this.saldo = saldoInicial;
        this.emailService = emailService;
    }

    public void depositar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser maior que zero.");
        }
        this.saldo += valor;
        this.emailService.enviarNotificacao("Depósito no valor de " + valor + " realizado com sucesso.");
    }

    public void sacar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor de saque deve ser maior que zero.");
        }
        if (valor > this.saldo) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }
        this.saldo -= valor;
        this.emailService.enviarNotificacao("Saque no valor de " + valor + " realizado com sucesso.");
    }

    // Método transferir utilizando os próprios métodos já validados
    public void transferir(ContaBancaria destino, double valor) {
        this.sacar(valor);          // Tira da conta origem e notifica
        destino.depositar(valor);   // Coloca na conta destino e notifica
    }

    public double consultarSaldo() {
        return this.saldo;
    }
}