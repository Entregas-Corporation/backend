# Entregas - Backend

    O Entregas é uma ferramenta web e móvel de delivery, que não só oferece um fluxo de entrega mais preciso, mas também proporciona um ambiente simplificado para o gerenciamento de produtos variáveis em lojas virtuais, reduzindo os custos de manutenção no mercado digital. 
    
    O repositório criado possui finalidade de desenvolvimento o backend do sistema, usando o freamwork spring. 

## Tecnologias usadas

- Spring - Java
- Postgres - SQL

# Configurações em localhost

    1 - Instale o Maven na sua máquina;
    2 - Instale o Postgres SQL na sua máquina;
    3 - Crie um usuário chamado postgres no Postgres SQL;
    4 - Altere a senha de usuário para postgres também;
    5 - Crie um banco de dados chamado "entregas";
    6 - No arquivo "aplication.properties", altere o valor de "JWT_SECRET" 
    na chave secreta para qualquer palavra que você ache secreta;
    7 - No arquivo "application.properties", altere o seu username (e-mail) e password nas confirgurações de smtp. Veja como adicionar a senha correta do seu e-mail 
    de envio escolhido: https://support.google.com/accounts/answer/185833.    

## Dependências implantadas

- Spring Web
- Spring Data JPA
- Lombok
- Spring Boot DevTools
- PostgreSQL Driver
- Spring Security
- Java Mail Sender
- Validation
- Java JWT
- Cloudinary
- Spring Doc
- Spring Fox Swagger

## Inicialização

- ### Instalação

        Antes de tudo, instale o JDK17 e o Maven na sua máquina. Siga o tutorial: https://www.treinaweb.com.br/blog/configurando-ambiente-de-desenvolvimento-spring-boot-no-windows

        Instale o docker e o docker-composer na sua máquina. 

- ### No Linux
        

        [Sem o uso do docker] 
        
        Execute o comando para limpar e reconstruir o projeto spring no linux:

        - mvn clean package
    
        Execute o comando para rodar o projeto no linux:

        - mvn spring-boot:run


- ### No Windows

        [Sem o uso do docker]

        Execute o comando para limpar e reconstruir o projeto spring no windows:
        
        - ./mvnw clean package
        
        Execute o comando para rodar o projeto no windows:

        - ./mvnw spring-boot:run
    
## Deploy [LINUX]

- ### ATUALIZE O PACKAGE

        sudo service postgresql start
        mvn package
        sudo service postgresql stop

- ### REBUILDE E TESTE AS IMAGENS
        
        sudo docker-compose up -d
        sudo docker-compose up

# Endpoints

    entregasEndpoints.json