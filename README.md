**ecommerce-spring-api-prototype**

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

Install ecommerce-api with **Github**

```bash
  git clone git@github.com:valentyna-koshelnyk/ecommerce-spring-api-prototype.git
  cd ecommerce-spring-api-prototype
```

**Run ecommmerce-api-prototype project with Docker**


To run the project within a Docker container, follow these steps:

1. **Build the Docker Image**


```bash
docker build ecommerce-api .
```

2. Run the Docker container

```bash
docker run -p 8080:8080 --name ecommerce-app ecommerce-api
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

The app has been deployed on Google cloud Run, hereafter we'll use https://localhost:8080/ and GCP hostname https://ecomapp-laowthm57a-oe.a.run.app/. 

## API Reference

Full documentation available at the following link:
```bash
https://localhost:8080/swagger-ui/index.html
```
```bash
https://ecomapp-laowthm57a-oe.a.run.app/swagger-ui/index.html
```

## Usage/Examples/Flow

On a local machine 

The program can be accessed by User or Admin. 

User flow:

User signs up using the following link:
```bash
https://localhost:8080/api/auth/register
```
```bash
https://ecomapp-laowthm57a-oe.a.run.app/api/auth/register
```

Shopping cart automatically created upon registration.

User has to input a valid email (the program validates user's email based on regex email pattern).

User has to input a valid password matching the following criteria:

_at least 8 characters long;_
_at least 1 upper case character;_
_at least 1 lower case character;_
_at least 1 digit._

(The program validates user's password based on custom regex pattern).

User has to confirm the email in matching password attribute and the program validates if passwords are matching.

User signs in using his username and password. JWT token is generated upon authentication and stored in cookies. 

User can check all available products using the following link: 
```bash
https://localhost:8080/api/v1/products/all
```
```bash
https://ecomapp-zalando-2-laowthm57a-oe.a.run.app/api/v1/products/all
```
Can search for particular product:
(containsIgnoreCase):
```bash
https://localhost:8080/api/v1/products/search/{searchWord}
```
```bash
https://ecomapp-zalando-2-laowthm57a-oe.a.run.app/api/v1/products/search/{searchWord}
```

Add products to his cart:
```bash
https://localhost:8080/api/v1/user/cart/{userId}/addProduct/
```
```bash
https://ecomapp-laowthm57a-oe.a.run.app/api/v1/user/cart/{userId}/addProduct/
```
Body request consists of 
product Id and quantity.

Check products in cart: 
```bash
https://localhost:8080/api/v1/user/cart/{userId}/checkCart
```
```bash
https://ecomapp-laowthm57a-oe.a.run.app/api/v1/user/cart/{userId}/checkCart
```

And place order:
```bash
https://localhost:8080/api/v1/user/orders/place/{userId}
```
```bash
https://ecomapp-laowthm57a-oe.a.run.app/api/v1/user/orders/place/{userId}
```

Before putting the order, user must update his user information
including:

first name;
last name;
address;
phone 

on the following link: 
```bash
https://localhost:8080/api/v1/user/orders/1/updateInformation
```
```bash
https://ecomapp-laowthm57a-oe.a.run.app/api/v1/user/orders/1/updateInformation
```

User can cancel his order:

```bash
https://localhost:8080/user/orders/cancel/{userId}/{orderId}
```
```bash
https://ecomapp-laowthm57a-oe.a.run.app/api/v1/user/orders/cancel/{userId}/{orderId}
```

and view his order history:
```bash
https://localhost:8080/api/v1/user/orders/view/old/{userId}
```
```bash
https://ecomapp-laowthm57a-oe.a.run.app/api/v1/user/orders/view/old/{userId}
```
Admin is authorized for all features of our app. Some features are available exclusively for admin, such as:

add another admin:
```bash
https://localhost:8080/api/auth/registerAdmin
```
```bash
https://ecomapp-laowthm57a-oe.a.run.app/api/auth/registerAdmin
```
Get the list of all products:
```bash
https://localhost:8080/api/v1/products/admin/all?pageSize=3
```
```bash
https://ecomapp-laowthm57a-oe.a.run.app/api/v1/products/admin/all?pageSize=3
```
Add products to the system:
```bash
https://localhost:8080/api/v1/products/admin/add
```
```bash
https://ecomapp-laowthm57a-oe.a.run.app/api/v1/products/admin/add
```

Increase product stock:
```bash
https://localhost:8080/api/v1/products/admin/increaseStock/{productId}?quantity=quantity
```
```bash
https://ecomapp-laowthm57a-oe.a.run.app/api/v1/products/admin/increaseStock/{productId}?quantity=quantity
```
Delete product using criteria API:
```bash
https://localhost:8080/api/v1/products/admin/delete?operator=LIKE&value=jacket&field=productName
```
```bash
https://ecomapp-laowthm57a-oe.a.run.app/api/v1/products/admin/delete?operator=LIKE&value=jacket&field=productName
```
Update produt using criteria API:
```bash
https://localhost:8080/api/v1/products/admin/update?operator=EQUALS&field=fieldName&value=
```
```bash
https://ecomapp-laowthm57a-oe.a.run.app/api/v1/products/admin/update?operator=EQUALS&field=fieldName&value=
```

Move order status based on State Design Pattern the admin is using the following link to move order one step further following the chain:
```bash
https://localhost:8080/api/v1/user/orders/status/{orderId}/next
```

```bash
https://ecomapp-laowthm57a-oe.a.run.app/api/v1/user/orders/status/orderId/next
```
_NotPaid->Paid->InProcess->Shipped->Delivered->Returned;_

Also it's possible to revert step n steps behind:
```bash
https://localhost:8080/api/v1/user/orders/status/orderId/revert
```
```bash
https://ecomapp-laowthm57a-oe.a.run.app/status/{orderId}/revert
```





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

## Automation

To reduce boilerplate code were used Mapstruct and Lomboc annotations.
Mapstruct helped us automatically map data transfer object to POJO.

Lombok helped to generate getters/setters/constructor.
