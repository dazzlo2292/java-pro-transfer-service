create table accounts (
    id varchar(10) primary key,
    account_number varchar(16),
    client_id varchar(10),
    balance int,
    block_fl varchar(1)
);

insert into accounts (id, account_number, client_id, balance, block_fl) values
('0000000001', '1111222233334444', '1000000001', '500', 'N'),
('0000000002', '1111555566667777', '1000000002', '1000', 'N');

