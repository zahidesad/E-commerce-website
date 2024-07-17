# E-commerce Website
This is a comprehensive e-commerce website project developed as part of an internship. The project utilizes a variety of modern technologies and frameworks to provide a robust online shopping experience.

## Table of Contents
- Features
- Technologies Used
- Setup and Installation
- Database Schema
- Project Structure
- Usage
- Contributing
- License
  
## Features
- User registration and login
- Product listing with categories
- Search functionality
- Shopping cart
- Order placement
- Admin panel for product management
- Spring Security for authentication and authorization
- TC Kimlik No (Turkish National ID) validation during registration

## Technologies Used
- Java: Core programming language
- Spring Framework: Including Spring MVC, Spring Security, and Spring Data JPA
- Hibernate: ORM tool for database interaction
- MySQL: Relational database management system
- JSP: JavaServer Pages for dynamic web content
- Bootstrap: Frontend framework for responsive design
- jQuery: JavaScript library for dynamic content
- Tomcat: Java servlet container

## Setup and Installation
### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Apache Maven
- MySQL Server
- Apache Tomcat

### Steps
1) Clone the repository
   git clone https://github.com/yourusername/ecommerce-website.git
   cd ecommerce-website
2) Configure the database
   - Create a MySQL database named e-commerce-website.
   - Update the application.properties file with your MySQL username and password.
3) Build the project
   - mvn clean install
4) Deploy to Tomcat
   - Copy the generated WAR file from target/ecommerce-website.war to the webapps directory of your Tomcat server.
5) Start the Tomcat server
   - Access the application at http://localhost:8080/ecommerce-website

## Database Design:
![Staj Projesi Database Tasarımı Güncellenmiş](https://github.com/zahidesad/E-commerce-website/assets/116666407/09640a4d-3ab8-4318-86a2-0c918d8daf7e)

## Database Creation Commands:

1)  CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    surname VARCHAR(255);
    tc_number VARCHAR(11);
    birth_year INT;
    email VARCHAR(100) UNIQUE,
    mobile_number VARCHAR(15),
    security_question VARCHAR(200),
    answer VARCHAR(200),
    password VARCHAR(100),
    role VARCHAR(50),
    enabled TINYINT(1) DEFAULT 1
);

2) CREATE TABLE address (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    address VARCHAR(500),
    city VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

3)  CREATE TABLE category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200)
);

4)  CREATE TABLE category_relationship (
    parent_category_id INT,
    child_category_id INT,
    PRIMARY KEY (parent_category_id, child_category_id),
    FOREIGN KEY (parent_category_id) REFERENCES category(id),
    FOREIGN KEY (child_category_id) REFERENCES category(id)
);

5)  CREATE TABLE product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(500),
    active VARCHAR(10)
    photo_data LONGBLOB
    photo_name VARCHAR(255)
);

6)  CREATE TABLE product_category (
    product_id INT,
    category_id INT,
    PRIMARY KEY (product_id, category_id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);

7)  CREATE TABLE price (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    price INT,
    start_date DATE,
    end_date DATE,
    FOREIGN KEY (product_id) REFERENCES product(id)
);

8)  CREATE TABLE stock (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    quantity INT,
    FOREIGN KEY (product_id) REFERENCES product(id)
);

9)  CREATE TABLE cart (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

10) CREATE TABLE cart_item (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cart_id INT,
    product_id INT,
    quantity INT,
    price INT,
    total INT,
    FOREIGN KEY (cart_id) REFERENCES cart(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

11) CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    order_date DATE,
    delivery_date DATE,
    payment_method VARCHAR(100),
    transaction_id VARCHAR(100),
    status VARCHAR(100),
    address_id INT
    FOREIGN KEY (user_id) REFERENCES users(id)
    FOREIGN KEY (address_id) REFERENCES address(id)
);

12) CREATE TABLE order_item (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT,
    price INT,
    total INT,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

## Usage

1) User Registration: Users can register by providing their details including TC Kimlik No for validation.
2) Login: Registered users can log in using their email and password.
3) Product Browsing: Users can browse products by category.
4) Search: Users can search for products using the search bar.
5) Add to Cart: Users can add products to their cart and proceed to checkout.
6) Admin Panel: Admin can manage products and categories.

