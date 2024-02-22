# ecommerce-spring-api-prototype

![alt text](https://i.ibb.co/kxdPHRW/DALL-E-2024-01-08-15-48-09-A-modern-and-sleek-logo-for-an-ecommerce-app-The-logo-should-include-a-st.png)

A simple yet functional e-commerce shop. The application serves as a backend system, handles everything from product management to order processing. The API interacts with front-end applications, allowing users to browse products, add them to a cart, and place orders.








## Tech Stack

**Language:** Java 17.0

**Framework:** SpringBoot 3.2.1

**Server:** Tomcat

**Database:** MySQL8.0, JPA (Hibernate)

**Deployment:** Docker, Google Cloud Run

**Documentation:** OpenAPI Swagger

**Security:** SpringSecurity / JWT Authentication

**Build:** Maven3.0+

**Additional Plugins/API:** Lombok, Mapstruct, Criteria API




## Installation

Install my-project with **Github**

```bash
  git clone git@github.com:valentyna-koshelnyk/ecommerce-spring-api-prototype.git
  cd ecommerce-spring-api-prototype
```

**Run ecommmerce-api-prototype project with Docker**


To run the project within a Docker container, follow these steps:

1. **Build the Docker Image**


```bash
docker build -t ecommerce-api .
```

2. Run the Docker container

```bash
docker run ecommerce-api -p 8080:8080 ecommerce-app
```
Replace `8080:8080` with the appropriate port numbers for your application. If your application does not require port mapping, you can omit the `-p` option.

3.  After running the container, you can access the application at `http://localhost:8080`

4. **Stopping the container**:

 When you're done, you can stop the container using:

```bash
docker stop ecommerce-api
```

 And if you want to remove the container:

```bash
docker rm ecommerce-api
```


## API Reference

Full documentation available at the following link:

https://ecomapp-laowthm57a-oe.a.run.app/swagger-ui/index.html


## Usage/Examples/Flow

The program can be accessed by User or Admin. 

User flow:

User signs up using the following link:


https://ecomapp-laowthm57a-oe.a.run.app/api/auth/register

Shopping cart automatically created upon registration.

User has to input a valid email (the program validates user's email based on regex email pattern).

User has to input a valid password matching the following criteria:

at least 8 characters long;
at least 1 upper case character;
at least 1 lower case character;
at least 1 digit.

(The program validates user's password based on custom regex pattern).

User has to confirm the email in matching password attribute and the program validates if passwords are matching.

User signs in using his username and password. JWT token is generated upon authentication and stored in cookies. 

User can check all available products using the following link: 
https://ecomapp-zalando-2-laowthm57a-oe.a.run.app/api/v1/products/all

Can search for particular product:
(containsIgnoreCase):
https://ecomapp-zalando-2-laowthm57a-oe.a.run.app/api/v1/products/search/{searchWord}

Add products to his cart:

https://ecomapp-laowthm57a-oe.a.run.app/api/v1/user/cart/{userId}/addProduct/

Body request consists of 
product Id and quantity.

Check products in cart: 

https://ecomapp-laowthm57a-oe.a.run.app/api/v1/user/cart/{userId}/checkCart

And place order:
https://ecomapp-laowthm57a-oe.a.run.app/api/v1/user/orders/place/{userId}

Before putting the order, user must update his user information
including:

first name;
last name;
address;
phone 

on the following link: 

https://ecomapp-laowthm57a-oe.a.run.app/api/v1/user/orders/1/updateInformation

User can cancel his order:

https://ecomapp-laowthm57a-oe.a.run.app/api/v1/user/orders/cancel/{userId}/{orderId}

and view his order history:

https://ecomapp-laowthm57a-oe.a.run.app/api/v1/user/orders/view/old/{userId}

Admin is authorized for all features of our app. Some features are available exclusively for admin, such as:

add another admin:

https://ecomapp-laowthm57a-oe.a.run.app/api/auth/registerAdmin

Get the list of all products:

https://ecomapp-laowthm57a-oe.a.run.app/api/v1/products/admin/all?pageSize=3

Add products to the system:
https://ecomapp-laowthm57a-oe.a.run.app/api/v1/products/admin/add

Increase product stock:

https://ecomapp-laowthm57a-oe.a.run.app/api/v1/products/admin/increaseStock/{productId}?quantity=quantity

Delete product using criteria API:

https://ecomapp-laowthm57a-oe.a.run.app/api/v1/products/admin/delete?operator=LIKE&value=jacket&field=productName

Update produt using criteria API:

https://ecomapp-laowthm57a-oe.a.run.app/api/v1/products/admin/update?operator=EQUALS&field=fieldName&value=

Move order status based on State Design Pattern the admin is using the following link to move order one step further following the chain:

https://ecomapp-laowthm57a-oe.a.run.app/api/v1/user/orders/status/orderId/next

NotPaid->Paid->InProcess->Shipped->Delivered->Returned;

Also it's possible to revert step n steps behind:

https://ecomapp-laowthm57a-oe.a.run.app/status/{orderId}/revert






## Criteria API

Criteria API allows admin programmatically to manipulate products:

Can choose the following operator:

EQUALS;
NOT_EQUALS;
GREATER_THAN;
LESS_THAN;
LIKE.

Field of products model:

productId;
productName;
price;
description;
stock;
addedAtDate;
category;

Value:

## Automatisation

To reduce boilerplate code were used Mapstruct and Lomboc annotations.
Mapstruct helped us automatically map data transfer object to POJO.

Lombok helped to generate getters/setters/constructor.