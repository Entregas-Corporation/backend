package br.com.entregas.Entregas.core.constants;

public final class SendEmailMessageConstant {
    public static final String linkValidate = "http://localhost:8080/user/validate/account/";

    public static final String successfullValidation = "Parabéns! Sua conta foi validada e você já pode fazer login no sistema.";

    public static final String titleWellcome = "Bem-vindo ao Entregas!";
    
    public static final String titleSuccessfullValidation = "Conta ativada com sucesso no Entregas!";
    
    public static final String titleDisableAccount = "Conta desativada no Entregas!";

    public static final String titleTreatmentComplaint = "Denúncia Recebida!";

    public static final String titleTreatmentSupport = "Mensagem Recebida!";

    public static final String titleRequestedOrder = "Pedido Recebido!";

    public static final String titleCanceledOrder = "Pedido Cancelado!";

    public static final String titleSentOrder = "Pedido Enviado!";

    public static final String titleDeliveredOrder = "Pedido em Entregue!";

    
    public static final String textWellcome(String id_user, String username) {
        return "Olá, "+username+"! Sua conta está quase concluída."+
                "\nClique no link abaixo para validar a sua conta:"+
                "\n"+linkValidate+id_user+
                "\n\nCaso tenha alguma dúvida, sinta-se a vontade para nos enviar."+
                "\n\nAtenciosamente,"+
                "\nSuporte Entregas.";
    }

    public static final String textSuccessfullValidation(String username) {
        return "Olá, "+username+"! Sua conta foi aceita e ativada no sistema."+
        "\nAgora você pode fazer login e desfrutar do que há de melhor no nosso sistema."+
        "\nCaso tenha alguma dúvida, sinta-se a vontade para nos enviar."+
        "\n\nAtenciosamente,"+
        "\nSuporte Entregas.";
    }

    public static final String textDisableAccount(String username) {
        return "Olá, "+username+"! Sua conta foi desativada no sistema."+
        "\nCaso tenha alguma dúvida, sinta-se a vontade para nos enviar."+
        "\n\nAtenciosamente,"+
        "\nSuporte Entregas.";
    }

    public static final String textTreatmentSupport(String username) {
        return "Olá, "+username+"! Sua mensagem foi recebida em nosso sistema."+
        "\nAgora basta aguardar a nossa resposta perante ao atendimento realizado."+
        "\nCaso tenha alguma dúvida, sinta-se a vontade para nos enviar."+
        "\n\nAtenciosamente,"+
        "\nSuporte Entregas.";
    }

    public static final String textTreatmentComplaint(String username) {
        return "Olá, "+username+"! Sua reclamação foi recebida em nosso sistema."+
        "\nAgora basta aguardar a nossa resposta perante ao atendimento realizado."+
        "\nCaso tenha alguma dúvida, sinta-se a vontade para nos enviar."+
        "\n\nAtenciosamente,"+
        "\nSuporte Entregas.";
    }

    public static final String textRequestedOrder(String username, String institute) {
        return "Olá, "+username+"! Parabéns! O seu pedido da loja "+institute+" foi recebido e está em processo de preparação para ser enviado."+
        "\nAgora basta aguardar as próximos etapas de entrega do pedido neste mesmo e-mail."+
        "\n\nAh! Você pode cancelar o pedido antes dele ser enviado."+
        "\n\nTrabalhamos muito para garantir a melhor segurança e bem-estar entre nossos clientes, entregadores e empresários."+
        "\nCaso tenha alguma dúvida, sinta-se a vontade para nos enviar."+
        "\n\nAtenciosamente,"+
        "\nSuporte Entregas.";
    }

    public static final String textCanceledOrder(String username, String institute) {
        return "Olá, "+username+"! Infelizmente, o seu pedido da loja "+institute+" foi cancelado e está voltado para a vitrine do estabelecimento."+
        "\n\nTrabalhamos muito para garantir a melhor segurança e bem-estar entre nossos clientes, entregadores e empresários."+
        "\nCaso tenha alguma dúvida, sinta-se a vontade para nos enviar."+
        "\n\nAtenciosamente,"+
        "\nSuporte Entregas.";
    }

    public static final String textSentOrder(String username, String institute) {
        return "Olá, "+username+"! Temos boas notícias! O Seu pedido da loja "+institute+" foi enviado e está em trânsito para ser entregue."+
        "\nAgora basta aguardar as próximos etapas de entrega do pedido neste mesmo e-mail."+
        "\n\nAh! Caso tenha desistido do pedido, informe ao entregador sobre o cancelamento quando ele chegar em seu estabelecimento, por favor."+
        "\n\nTrabalhamos muito para garantir a melhor segurança e bem-estar entre nossos clientes, entregadores e empresários."+
        "\nCaso tenha alguma dúvida, sinta-se a vontade para nos enviar."+
        "\n\nAtenciosamente,"+
        "\nSuporte Entregas.";
    }

    public static final String textDeliveredOrder(String username, String institute) {
        return "Olá, "+username+"! Temos boas notícias! O seu pedido da loja "+institute+" foi entregue em seu estabelecimento."+
        "\nA loja agradece imensamente a sua preferência."+
        "\n\nTrabalhamos muito para garantir a melhor segurança e bem-estar entre nossos clientes, entregadores e empresários."+
        "\nCaso tenha alguma dúvida, sinta-se a vontade para nos enviar."+
        "\n\nAtenciosamente,"+
        "\nSuporte Entregas.";
    }
}