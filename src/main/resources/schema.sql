DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
  customer_id VARCHAR(250)  PRIMARY KEY,
  customer_role VARCHAR(1) NOT NULL,
  customer_name VARCHAR(250) NOT NULL,
  address VARCHAR(250) NOT NULL,
  telephone VARCHAR(250) NOT NULL,
  facsimile VARCHAR(250),
  attention VARCHAR(250),
  trans_date VARCHAR(250)
);

DROP TABLE IF EXISTS account;

CREATE TABLE account (
  id VARCHAR(250)  PRIMARY KEY,
  password VARCHAR(250) NOT NULL,
  salt VARCHAR(250) NOT NULL,
  role VARCHAR(250) NOT NULL,
  company_id VARCHAR(250) NOT NULL
);

DROP TABLE IF EXISTS exchange_rate;

CREATE TABLE exchange_rate (
  date_id VARCHAR(250),
  currency VARCHAR(250),
  rate VARCHAR(250),
  PRIMARY KEY(date_id, currency)
);
