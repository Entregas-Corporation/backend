package br.com.entregas.Entregas.core.constants;

public final class ExceptionMessageConstant {

    public static final String unsuccessfullValidation = "Deslcupe! Infelizmente sua conta foi desativada. Para mais informações, entre em contato com o suporte Entregas.";

    public static final String invalidAuthentication = "Autenticação inválida!";

    public static final String unauthorizedAccess = "Acesso não autorizado!";
    
    public static final String invalidQuantity = "Quantidade inválida!";

    public static final String fileError = "Erro ao carregar o arquivo!";

    public static final String attributeUsed(String attribute) {
        return attribute + " já em uso!";
    }

    public static final String notFound(String name) {
        return name + " não encontrado!";
    }

    public static final String token(String erro) {
        return "Erro ao gerar o token! Motivo de erro: " + erro;
    }

    public static final String userPermissionDenied(String role) {
        return "Você não pode alterar usuários do tipo " + role + "!";
    }
}
