create table accounts (
    id varchar(12) primary key,
    account_number varchar(16),
    client_id varchar(12),
    balance int,
    block_fl varchar(1)
);

insert into accounts (id, account_number, client_id, balance, block_fl) values
('000000000001', '1111222233334444', '100000000001', '500', 'N'),
('000000000002', '1111555566667777', '100000000002', '1000', 'N');

