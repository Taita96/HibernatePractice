CREATE DATABASE IF NOT EXISTS warehouse;
USE warehouse;

CREATE TABLE bag (
    idbag INT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL UNIQUE,
    model VARCHAR(100) NOT NULL,
    status ENUM('ACTIVE','INACTIVE'),
    entry_date timestamp
);

CREATE TABLE stock (
    idstock INT PRIMARY KEY AUTO_INCREMENT,
    quantity INT NOT NULL,
    price DECIMAL NOT NULL,
    idbag INT
);

CREATE TABLE location (
    idlocation INT PRIMARY KEY AUTO_INCREMENT,
    aisle VARCHAR(50) NOT NULL,
    shelf VARCHAR(50) NOT NULL
);

CREATE TABLE location_bag (
    idlocation INT,
    idbag INT,
    PRIMARY KEY (idlocation, idbag)
);

CREATE TABLE technical_sheet (
    idtechnical INT PRIMARY KEY AUTO_INCREMENT,
    material VARCHAR(50),
    weight DECIMAL,
    color VARCHAR(50),
    idbag INT UNIQUE
);

CREATE TABLE supplier (
    idsupplier INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    contact VARCHAR(100) UNIQUE
);

CREATE TABLE bag_supplier (
    idbag INT,
    idsupplier INT,
    PRIMARY KEY (idbag, idsupplier)
);

ALTER TABLE stock
ADD FOREIGN KEY (idbag) REFERENCES bag(idbag);

ALTER TABLE location_bag
ADD FOREIGN KEY (idbag) REFERENCES bag(idbag),
ADD FOREIGN KEY (idlocation) REFERENCES location(idlocation);

ALTER TABLE technical_sheet
ADD FOREIGN KEY (idbag) REFERENCES bag(idbag);

ALTER TABLE bag_supplier
ADD FOREIGN KEY (idbag) REFERENCES bag(idbag),
ADD FOREIGN KEY (idsupplier) REFERENCES supplier(idsupplier);

INSERT INTO location (aisle, shelf) VALUES
('Pasillo A', 'Estantería 1'),
('Pasillo A', 'Estantería 2'),
('Pasillo B', 'Estantería 1'),
('Pasillo B', 'Estantería 3'),
('Pasillo C', 'Estantería 2');

INSERT INTO supplier (name, contact) VALUES
('Bolsos Martínez S.L.', 'martinez@bolsos.com'),
('Distribuciones La Moda', 'contacto@lamoda.es'),
('Fashion Import Europe', 'ventas@fashionimport.eu'),
('Cuero y Diseño Artesano', 'info@cueroydiseno.es'),
('Textiles Premium', 'soporte@textilespremium.com');

