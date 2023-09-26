CREATE DATABASE Project;

USE Project;

-- 2 Create a table within that database called Customer
CREATE TABLE Customer (
	CustomerID int,
    Name varchar(70),
    Address varchar(200),
    Phone varchar(64)
);

-- 3 Describe this new table
DESCRIBE Customer;

-- 4 Alter the Customer table to add a primary key
ALTER TABLE Customer
	ADD PRIMARY KEY(CustomerID);

-- 5 Alter the Customer table to set the CustomerID field to auto-incrementing
ALTER TABLE Customer MODIFY CustomerID int AUTO_INCREMENT;

-- 6 View the current CREATE TABLE statement used to recreate the Customer table, notice the changes from the create table statement you ran earlier
SHOW CREATE TABLE Customer;

-- 7 Create another table called Order that already has an auto-incrementing primary key column. Notice the need to use quotes around Order as this is also a MySQL key word, try running the statement without the quotes.
CREATE TABLE `Order` (
	OrderID int PRIMARY KEY AUTO_INCREMENT,
    CustomerID int,
    DispatchDate DATETIME
);

-- 8 Add a column to the Order table called OrderDate of type TIMESTAMP, this value will automatically be updated any time the row is updated
ALTER TABLE `Order`
ADD COLUMN OrderDate TIMESTAMP
AFTER CustomerID;

-- 9 Alter the OrderDate column so the value only gets updated when the row is created
ALTER TABLE  `Order`
MODIFY COLUMN OrderDate TIMESTAMP
DEFAULT CURRENT_TIMESTAMP;

-- 10 Create a table within that database called Product
CREATE TABLE Product (
	ProductID int,
    Name varchar(50),
    Description varchar(500),
    Price decimal(11,2)
);

-- TASK (First one) 11 Alter the Product table to add a primary key
ALTER TABLE Product
	ADD PRIMARY KEY(ProductID);

-- TASK 12 Alter the Product table to set the ProductID field to auto-incrementing
ALTER TABLE Product
MODIFY ProductID int AUTO_INCREMENT;

-- TASK 13 Alter the Product table to add a column called CreatedDate of type TIMESTAMP that will only get updated when the row is created
ALTER TABLE Product
	ADD COLUMN CreatedDate TIMESTAMP
    DEFAULT CURRENT_TIMESTAMP;
-- Question currently above jsut defaults, but allows other values

-- TASK 14 Insert some rows into the Product table
INSERT INTO Product (Name, Description, Price)
VALUES ("One", "One Description", 1.33);
INSERT INTO Product (Name, Description, Price)
VALUES ("Two", "Two Description", 2.2229);
INSERT INTO Product (Name, Description, Price)
VALUES ("Three", "Three Description", 3.999);

-- 15 Explain a select statement on your product table with a where clause on the name column
-- 16 Notice the number of rows that the query is having to check to return your rows. On a project you might want to filter on a column such as name on a table with millions of rows which would be exteremely inefficient
-- 18 Re-run the explain statement above. Notice the diffence in the number of rows the query had to read
EXPLAIN SELECT NAME FROM Product WHERE Name = 'Two';

-- 17 Create an index on the Name column in the Product table
CREATE INDEX product_name on Product(Name);
