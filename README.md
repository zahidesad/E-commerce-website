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
2) Open a file called application.properties in the src/main/resources directory and fill in the following information according to your needs.

   ``` java
   #View Resolver settings
   spring.mvc.view.prefix=/WEB-INF/view/
   spring.mvc.view.suffix=.jsp

   #Server settings
   server.port=8080

   #Database settings
   spring.datasource.url=jdbc:mysql://localhost:3306/e-commerce-website
   spring.datasource.username=your_database_username
   spring.datasource.password=your_database_password
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

   #Hibernate settings
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
   spring.jpa.open-in-view=true

   #E-mail verification
   spring.mail.host=smtp.gmail.com
   spring.mail.port=587
   spring.mail.username=your_email_address
   spring.mail.password=your_email_app_password
   spring.mail.properties.mail.smtp.auth=true
   spring.mail.properties.mail.smtp.starttls.enable=true
   spring.mail.properties.mail.debug=true
   ```

3) Configure the database
   - Create a MySQL database named e-commerce-website.
   - Update the application.properties file with your MySQL username and password.
4) Build the project
   - mvn clean install
5) Deploy to Tomcat
   - Copy the generated WAR file from target/ecommerce-website.war to the webapps directory of your Tomcat server.
6) Start the Tomcat server
   - Access the application at http://localhost:8080/ecommerce-website

## Database Design:
![Staj Projesi Database Tasarımı Güncellenmiş](https://github.com/zahidesad/E-commerce-website/assets/116666407/09640a4d-3ab8-4318-86a2-0c918d8daf7e)

## Database Creation Commands:

```
1)  CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    surname VARCHAR(255);
    tc_number BIGINT;
    birth_year INT;
    email VARCHAR(100) UNIQUE,
    mobile_number VARCHAR(15),
    security_question VARCHAR(200),
    answer VARCHAR(200),
    password VARCHAR(100),
    role VARCHAR(50),
    enabled TINYINT(1) DEFAULT 0
    verification_code VARCHAR(64)
);
```

```
2) CREATE TABLE address (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    address VARCHAR(500),
    city VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

```
3)  CREATE TABLE category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200)
);
```

```
4)  CREATE TABLE category_relationship (
    parent_category_id INT,
    child_category_id INT,
    PRIMARY KEY (parent_category_id, child_category_id),
    FOREIGN KEY (parent_category_id) REFERENCES category(id),
    FOREIGN KEY (child_category_id) REFERENCES category(id)
);
```

```
5)  CREATE TABLE product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(500),
    active VARCHAR(10)
    photo_data LONGBLOB
    photo_name VARCHAR(255)
);
```

```
6)  CREATE TABLE product_category (
    product_id INT,
    category_id INT,
    PRIMARY KEY (product_id, category_id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);
```

```
7)  CREATE TABLE price (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    price INT,
    start_date DATE,
    end_date DATE,
    FOREIGN KEY (product_id) REFERENCES product(id)
);
```

```
8)  CREATE TABLE stock (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    quantity INT,
    FOREIGN KEY (product_id) REFERENCES product(id)
);
```

```
9)  CREATE TABLE cart (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

```
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
```

```
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
```

```
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
```

## Usage

1) User Registration: Users can register by providing their details including TC Kimlik No for validation.
2) Login: Registered users can log in using their email and password.
3) Product Browsing: Users can browse products by category.
4) Search: Users can search for products using the search bar.
5) Add to Cart: Users can add products to their cart and proceed to checkout.
6) Admin Panel: Admin can manage products and categories.

## Screens

Note: This is a backend project, so I didn't pay much attention to the frontend part.

### General Screens

1) Login Screen
   ![image](https://github.com/user-attachments/assets/516213c0-7d92-400a-b46c-3f31a06506f1)

2) Register Screen
   ![image](https://github.com/user-attachments/assets/ac8cd653-8f1a-456a-b901-5a2e2b47a969)

3) Forgot Password Screen
   ![image](https://github.com/user-attachments/assets/ebcfe302-3446-4c9b-b459-daf90d1acfe4)

### User's Screens

1) Home Screen
   ![image](https://github.com/user-attachments/assets/1c364699-4501-49ac-a19a-0fdfcedb7e6e)

2) Cart Screen
   ![image](https://github.com/user-attachments/assets/64f7424e-ea8e-49f5-bda3-5889728756c7)

3) Proceed To Order Screen (I blacked out the address part because it is my own address.)
   <img width="1279" alt="image" src="https://github.com/user-attachments/assets/d65beb51-9f8f-4b31-948f-286ad6f0a2f3">

4) My Orders Screen
   ![image](https://github.com/user-attachments/assets/d5799492-24fe-4bf8-b69a-d3948c773413)

5) My Order Details Screen (I blacked out the address part because it is my own address.)
   <img width="1280" alt="image" src="https://github.com/user-attachments/assets/095f19cf-8c5c-4f47-a8c1-eadee3df644d">

6) My Addresses Screen (I blacked out the address part because it is my own address.)
   <img width="1279" alt="image" src="https://github.com/user-attachments/assets/f861e02d-6775-4f9e-a2a0-8064028cd051">

7) Add New Address Screen
   ![image](https://github.com/user-attachments/assets/3c92bd11-7c9a-48e1-9718-711c2fe7d321)

8) Edit My Address Screen (I blacked out the address part because it is my own address.)
   <img width="1279" alt="image" src="https://github.com/user-attachments/assets/7d2c0d0e-4779-43c8-9baa-9e0d74307079">

### Admin's Screens

1) Home Screen
   ![image](https://github.com/user-attachments/assets/7bbefed0-416d-4153-b57b-4c7c0edb4714)

2) Add New Product Screen
   ![image](https://github.com/user-attachments/assets/aabe02bf-60e7-4950-9aa8-4d3f16ab7e29)

3) All Product & Edit Products Screen
   ![image](https://github.com/user-attachments/assets/934f0610-156b-4149-b720-fd60ea4caa17)

4) Edit Product Screen
   ![image](https://github.com/user-attachments/assets/a75e8463-7733-48b0-b139-e486e4448011)

5) Add New Price Screen
   ![image](https://github.com/user-attachments/assets/8a15e89a-30ec-4a10-92c6-45b3d714b6f6)

6) Add New Category Screen
   ![image](https://github.com/user-attachments/assets/49e9be3b-448f-4ecf-a8a5-7259d4ba1afe)

7) Manage Category Relationship Screen
   ![image](https://github.com/user-attachments/assets/b89c49b9-c080-4296-bffd-de6dbbc41f5d)

8) Orders Screen
   ![image](https://github.com/user-attachments/assets/1eea5b55-9e5e-42e1-80ef-deb2263fa912)

9) View Order Details Screen
    ![image](https://github.com/user-attachments/assets/c6f49047-aa7e-40ee-b83b-541a7a0ac51f)

    

   


   










   





   


