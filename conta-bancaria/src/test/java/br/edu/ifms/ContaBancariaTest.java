package br.edu.ifms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContaBancariaTest {

    // 1. Criar conta com saldo inicial válido.
    @Test
    public void testCriarContaSaldoValido() {
        ContaBancaria conta = new ContaBancaria(100.0);
        assertEquals(100.0, conta.consultarSaldo(), "O saldo inicial deve ser 100.0");
    }

    // 2. Tentar criar conta com saldo negativo (deve lançar exceção).
    @Test
    public void testCriarContaSaldoNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ContaBancaria(-50.0);
        }, "Deve lançar exceção ao criar conta com saldo negativo");
    }

    // 3. Realizar depósito válido.
    @Test
    public void testDepositoValido() {
        ContaBancaria conta = new ContaBancaria(50.0);
        conta.depositar(50.0);
        assertEquals(100.0, conta.consultarSaldo(), "O saldo após depósito deve ser 100.0");
    }

    // 4. Realizar depósito com valor inválido (zero ou negativo).
    @Test
    public void testDepositoInvalido() {
        ContaBancaria conta = new ContaBancaria(100.0);
        
        // Testando zero
        assertThrows(IllegalArgumentException.class, () -> {
            conta.depositar(0.0);
        }, "Deve lançar exceção ao depositar zero");
        
        // Testando valor negativo
        assertThrows(IllegalArgumentException.class, () -> {
            conta.depositar(-20.0);
        }, "Deve lançar exceção ao depositar valor negativo");
    }

    // 5. Realizar saque válido.
    @Test
    public void testSaqueValido() {
        ContaBancaria conta = new ContaBancaria(100.0);
        conta.sacar(40.0);
        assertEquals(60.0, conta.consultarSaldo(), "O saldo após o saque deve ser 60.0");
    }

    // 6. Tentar sacar valor maior que o saldo.
    @Test
    public void testSaqueMaiorQueSaldo() {
        ContaBancaria conta = new ContaBancaria(100.0);
        assertThrows(IllegalArgumentException.class, () -> {
            conta.sacar(150.0);
        }, "Deve lançar exceção ao sacar valor maior que o saldo");
    }

    // 7. Tentar sacar valor zero ou negativo.
    @Test
    public void testSaqueInvalido() {
        ContaBancaria conta = new ContaBancaria(100.0);
        
        // Testando zero
        assertThrows(IllegalArgumentException.class, () -> {
            conta.sacar(0.0);
        }, "Deve lançar exceção ao sacar zero");
        
        // Testando valor negativo
        assertThrows(IllegalArgumentException.class, () -> {
            conta.sacar(-10.0);
        }, "Deve lançar exceção ao sacar valor negativo");
    }

    // 8. Executar sequência de operações (depósito e saque) e verificar saldo final.
    @Test
    public void testSequenciaDeOperacoes() {
        ContaBancaria conta = new ContaBancaria(200.0);
        conta.depositar(100.0); // Saldo vai para 300
        conta.sacar(50.0);      // Saldo vai para 250
        conta.sacar(50.0);      // Saldo vai para 200
        assertEquals(200.0, conta.consultarSaldo(), "O saldo final deve ser 200.0 após a sequência de operações");
    }
}