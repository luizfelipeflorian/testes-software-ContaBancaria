package br.edu.ifms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Importações cruciais do Mockito
import static org.mockito.Mockito.*;

public class ContaBancariaTest {

    @Test
    public void deveEnviarEmailAoSacar() {
        // 1. Criar o Mock
        EmailService emailMock = mock(EmailService.class);
        ContaBancaria conta = new ContaBancaria(100.0, emailMock);
        
        // 2. Executar a ação
        conta.sacar(30.0);
        
        // 3. Verificar se o método foi chamado exatamente 1 vez com qualquer String
        verify(emailMock, times(1)).enviarNotificacao(anyString());
    }

    @Test
    public void naoDeveEnviarEmailSeSaqueInvalido() {
        EmailService emailMock = mock(EmailService.class);
        ContaBancaria conta = new ContaBancaria(100.0, emailMock);
        
        // Ação e Assertiva da exceção
        assertThrows(IllegalArgumentException.class, () -> {
            conta.sacar(150.0); // Saldo insuficiente
        });
        
        // Verifica que o e-mail NUNCA foi enviado
        verify(emailMock, never()).enviarNotificacao(anyString());
    }

    @Test
    public void deveEnviarEmailAoDepositar() {
        EmailService emailMock = mock(EmailService.class);
        ContaBancaria conta = new ContaBancaria(100.0, emailMock);
        
        conta.depositar(50.0);
        
        verify(emailMock, times(1)).enviarNotificacao(anyString());
    }

    @Test
    public void deveEnviarDoisEmailsNaTransferencia() {
        // Precisamos de dois mocks independentes para rastrear as duas contas
        EmailService emailOrigemMock = mock(EmailService.class);
        EmailService emailDestinoMock = mock(EmailService.class);
        
        ContaBancaria origem = new ContaBancaria(100.0, emailOrigemMock);
        ContaBancaria destino = new ContaBancaria(50.0, emailDestinoMock);
        
        // Ação: transfere 50
        origem.transferir(destino, 50.0);
        
        // Verifica se a origem enviou o e-mail de saque
        verify(emailOrigemMock, times(1)).enviarNotificacao(anyString());
        // Verifica se o destino enviou o e-mail de depósito
        verify(emailDestinoMock, times(1)).enviarNotificacao(anyString());
    }

    @Test
    public void naoDeveTransferirSeSaldoInsuficiente() {
        EmailService emailOrigemMock = mock(EmailService.class);
        EmailService emailDestinoMock = mock(EmailService.class);
        
        ContaBancaria origem = new ContaBancaria(50.0, emailOrigemMock);
        ContaBancaria destino = new ContaBancaria(100.0, emailDestinoMock);
        
        // Ação: tentar transferir 100 de uma conta com 50
        assertThrows(IllegalArgumentException.class, () -> {
            origem.transferir(destino, 100.0);
        });
        
        // Nenhuma das contas deve enviar notificação
        verify(emailOrigemMock, never()).enviarNotificacao(anyString());
        verify(emailDestinoMock, never()).enviarNotificacao(anyString());
        
        // Opcional extra: garantir que saldo não mudou
        assertEquals(50.0, origem.consultarSaldo());
        assertEquals(100.0, destino.consultarSaldo());
    }

    @Test
    public void deveLancarExcecaoSeEmailFalharNoSaque() {
        EmailService emailMock = mock(EmailService.class);
        ContaBancaria conta = new ContaBancaria(100.0, emailMock);
        
        // Configuramos o Mock para lançar uma RuntimeException ao tentar enviar e-mail
        doThrow(new RuntimeException("Falha no servidor de e-mail"))
            .when(emailMock).enviarNotificacao(anyString());
        
        // Ao tentar sacar, a conta fará a subtração e chamará o serviço, que lançará erro
        assertThrows(RuntimeException.class, () -> {
            conta.sacar(20.0);
        });
    }
}