# GMail SMTP PoC (Proof of Concept)
A minimal and pure Java Spring Boot backend-only POC (proof-of-concept) implementing Forgot Password and Reset Password flows using JavaMailSender with Gmail SMTP, UUID-based token handling etc. No frontend required — tested via Postman.

## Configuration Before Running
- Rename "application.properties.template" to `application.properties` (in `src\main\resources`).
- Inside the newly renamed `application.properties` file, replace the following with your own google credentials:
  - spring.mail.username=`INSERT_GOOGLE_EMAIL_HERE`
  - spring.mail.password=`INSERT_16_DIGIT_PASSWORD_HERE`
- Add your own MySQL credentials inside `application.properties`:
  - spring.datasource.username=`INSERT_USERNAME_HERE`
  - spring.datasource.password=`INSERT_PASSWORD_HERE`

### Instructions and Workflow

- Register the user via `/register` API endpoint.
- Check `/login` endpoint with the same credentials.
- To reset the password, call the `/forgot-password` endpoint and provide the registered email ID to receive the reset link.
- Open the received link `localhost:8080/reset-password?token=....` in Postman.
- With token already present as the query parameter, send the following JSON body in POST.
  
  - `{ "newPassword": "NEW_PASSWORD_HERE"}`  
- Login with the new credentials using `/login` to verify.

### Technologies Used
- Java 11
- Spring Boot 2.3.1 RELEASE
- Gradle 7.6.1
- MySQL 8.0.41
- BCrypt
- JavaMailSender (Gmail SMTP)
- Postman
