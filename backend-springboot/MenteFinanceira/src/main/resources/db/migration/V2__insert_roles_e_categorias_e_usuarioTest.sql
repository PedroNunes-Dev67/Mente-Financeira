-- Cadastra as roles
INSERT INTO role (role_name) VALUES ('ROLE_USER');
INSERT INTO role (role_name) VALUES ('ROLE_ADMIN');

-- Dados das categorias
INSERT INTO categoria (nome_categoria) VALUES ('Saúde');
INSERT INTO categoria (nome_categoria) VALUES ('Alimentação');
INSERT INTO categoria (nome_categoria) VALUES ('Assinaturas');
INSERT INTO categoria (nome_categoria) VALUES ('Casa');
INSERT INTO categoria (nome_categoria) VALUES ('Compras');
INSERT INTO categoria (nome_categoria) VALUES ('Educação');
INSERT INTO categoria (nome_categoria) VALUES ('Lazer');
INSERT INTO categoria (nome_categoria) VALUES ('Serviços');
INSERT INTO categoria (nome_categoria) VALUES ('Supermercado');
INSERT INTO categoria (nome_categoria) VALUES ('Transporte');
INSERT INTO categoria (nome_categoria) VALUES ('Viagem');
INSERT INTO categoria (nome_categoria) VALUES ('Economias');
INSERT INTO categoria (nome_categoria) VALUES ('Outros');


-- Dados do usuário de testes
INSERT INTO usuario (nome, email, senha, verificacao_email)
VALUES ('Pedro Nunes', 'exemplo@gmail.com', '$2a$10$e6CyQp/k0ccbwuZ1wT5kQe/xhYAHT9ItLcxxg31iXkApJEEej6O1u', true);

-- Vincula as roles (pegando os IDs pelo nome, mais seguro)
INSERT INTO usuario_role (id_usuario, id_role)
SELECT u.id_usuario, r.id_role
FROM usuario u, role r
WHERE u.email = 'exemplo@gmail.com'
  AND r.role_name IN ('ROLE_USER', 'ROLE_ADMIN');
