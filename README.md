# gmail-smtp-poc
A minimal and pure Java Spring Boot backend-only POC (proof-of-concept) implementing Forgot Password and Reset Password flows using JavaMailSender with Gmail SMTP, UUID-based token handling etc. No frontend required — tested via Postman.

## Configuration Before Running
- Rename "application.properties.template" to `application.properties` (in `src\main\resources`).
- Inside the newly renamed `application.properties` file, replace the following with your own google credentials:
  - spring.mail.username=`INSERT_GOOGLE_EMAIL_HERE`
  - spring.mail.password=`INSERT_16_DIGIT_PASSWORD_HERE`
- Add your own MySQL credentials inside `application.properties`:
  - spring.datasource.username=`INSERT_USERNAME_HERE`
  - spring.datasource.password=`INSERT_PASSWORD_HERE`

## Features
- Forgot password via email with secure token
- Token expires in 15 minutes
- Secure password reset
- Token stored in database (with expiration)
- Basic validation and exception handling
- Clean separation of controller, service, entity, and DTO layers

### Technologies Used
- Java 11
- Spring Boot 2.3.1 RELEASE
- Gradle 7.6.1
- MySQL 8.0.41
- BCrypt
- JavaMailSender (Gmail SMTP)
- Postman
