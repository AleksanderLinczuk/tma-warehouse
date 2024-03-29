CREATE DATABASE IF NOT EXISTS warehouse;

USE warehouse;

CREATE TABLE IF NOT EXISTS items(
	item_id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    item_group VARCHAR(255) NOT NULL,
    unit_of_measurement VARCHAR(255) NOT NULL,
    quantity INT not null,
    price_UAH DOUBLE not null,
	`status` VARCHAR(255),
	storage_location VARCHAR(255),
	contact_person VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS requests(
	request_id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    employee_name VARCHAR(255) NOT NULL,
    `item_id` BIGINT NOT NULL,
    unit_of_measurement VARCHAR(255) NOT NULL,
    quantity INT not null,
    price_UAH DOUBLE not null,
    `comment` VARCHAR(255),
    `status` VARCHAR(255) DEFAULT 'NEW',
	FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`)
);


