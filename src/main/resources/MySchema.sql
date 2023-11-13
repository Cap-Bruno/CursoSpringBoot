create database vendas;

use vendas;

create table Cliente(
    id integer primary key auto_increment,
    nome varchar(100),
    cpf varchar(11)
);

create table Produto(
    id integer primary key auto_increment,
    descricao varchar(100),
    preco_unitario numeric(20,2)
);

create table Pedido(
    id integer primary key auto_increment,
    cliente_id integer references Cliente(id),
    data_pedido timestamp,
    statusPedido varchar(20),
    total numeric(20,2)
);

create table Item_Pedido(
    id integer primary key auto_increment,
    pedido_id integer references Pedido(id),
    produto_id integer references Produto(id),
    quantidade integer
);

create table Usuario(
    id integer primary key auto_increment,
    login varchar(50),
    senha varchar(255),
    admin bool default false
);