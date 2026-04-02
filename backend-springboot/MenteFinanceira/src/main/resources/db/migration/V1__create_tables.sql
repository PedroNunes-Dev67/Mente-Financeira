CREATE TABLE role (
                      id_role     BIGINT AUTO_INCREMENT PRIMARY KEY,
                      role_name   VARCHAR(255) NOT NULL
);

CREATE TABLE categoria (
                           id_categoria    BIGINT AUTO_INCREMENT PRIMARY KEY,
                           nome_categoria  VARCHAR(255)
);

CREATE TABLE usuario (
                         id_usuario          BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nome                VARCHAR(255),
                         email               VARCHAR(255) UNIQUE,
                         senha               VARCHAR(255),
                         verificacao_email   BOOLEAN
);

CREATE TABLE usuario_role (
                              id_usuario  BIGINT NOT NULL,
                              id_role     BIGINT NOT NULL,
                              PRIMARY KEY (id_usuario, id_role),
                              CONSTRAINT fk_usuario_role_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
                              CONSTRAINT fk_usuario_role_role    FOREIGN KEY (id_role)    REFERENCES role(id_role)
);

CREATE TABLE despesa (
                         id_despesa              BIGINT AUTO_INCREMENT PRIMARY KEY,
                         titulo                  VARCHAR(255) NOT NULL,
                         valor                   DECIMAL(19,2) NOT NULL,
                         tipo_despesa            VARCHAR(50) NOT NULL,
                         despesa_status          VARCHAR(50),
                         id_usuario              BIGINT NOT NULL,
                         data_vencimento         INT,
                         data_criacao_despesa    DATETIME NOT NULL,
                         data_atualizacao_despesa DATETIME,
                         parcelas_totais_despesa INT NOT NULL,
                         parcelas_pagas_despesa  INT NOT NULL,
                         idCategoria             BIGINT,
                         CONSTRAINT fk_despesa_usuario   FOREIGN KEY (id_usuario)  REFERENCES usuario(id_usuario),
                         CONSTRAINT fk_despesa_categoria FOREIGN KEY (idCategoria) REFERENCES categoria(id_categoria)
);

CREATE TABLE pagamento_despesa (
                                   id_pagamento                BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   dia_pagamento               DATE,
                                   despesa                     BIGINT,
                                   tipo_pagamento              VARCHAR(50),
                                   id_despesa                  BIGINT NOT NULL,
                                   titulo_despesa              VARCHAR(255) NOT NULL,
                                   valor_pagamento_despesa     DECIMAL(19,2) NOT NULL,
                                   parcelas_pagas              INT NOT NULL,
                                   parcelas_totais             INT NOT NULL,
                                   CONSTRAINT fk_pagamento_despesa FOREIGN KEY (despesa) REFERENCES despesa(id_despesa)
);

CREATE TABLE token_verificacao (
                                   id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   token       VARCHAR(255),
                                   ativo       BOOLEAN,
                                   duracao     DATETIME,
                                   id_usuario  BIGINT
);

CREATE TABLE refresh_token (
                               id_refresh_token BIGINT NOT NULL AUTO_INCREMENT,
                               token            VARCHAR(255) NOT NULL UNIQUE,
                               id_usuario       BIGINT UNIQUE,
                               expires_at       TIMESTAMP NOT NULL,

                               CONSTRAINT pk_refresh_token PRIMARY KEY (id_refresh_token),
                               CONSTRAINT fk_refresh_token_usuario
                                   FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario)
);