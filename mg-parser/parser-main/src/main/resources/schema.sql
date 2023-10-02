CREATE TABLE IF NOT EXISTS market_coin_info
(
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    coin_name VARCHAR NOT NULL,
    market_name VARCHAR(50) NOT NULL,
    market_network_name VARCHAR NOT NULL,
    market_withdraw_fee VARCHAR NOT NULL,
    market_network_is_deposit BOOLEAN NOT NULL,
    market_network_is_withdraw BOOLEAN NOT NULL
);