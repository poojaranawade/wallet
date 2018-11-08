CREATE table user(
id INT(6) AUTO_INCREMENT PRIMARY KEY,
firstname varchar(50) not null,
lastname varchar(50),
email varchar(50) not null unique,
password varchar(50) not null,
active tinyint(2),
balance decimal(20,4)
);

CREATE table transaction(
id INT(6) AUTO_INCREMENT PRIMARY KEY,
type varchar(10) not null,
created_on DATE NOT NULL,
amount decimal(10,4),
user_id int(6),
FOREIGN KEY (user_id) REFERENCES user(id)
);