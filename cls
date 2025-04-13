# -----------------------------------------
# ? THYMELEAF SETTINGS
# -----------------------------------------
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.cache=false

# -----------------------------------------
# ? TOMORROW.IO API KEY
# -----------------------------------------
tomorrow.api.key= Your_tomorrow_api_key

# -----------------------------------------
# ? GOOGLE LOGIN (OAuth2)
# Make sure this redirect URI is also added in Google Cloud
# -----------------------------------------
spring.security.oauth2.client.registration.google.client-id= Your_client_id
spring.security.oauth2.client.registration.google.client-secret= your_secret
spring.security.oauth2.client.registration.google.scope=openid,profile,email
spring.security.oauth2.client.registration.google.redirect-uri={baseUrl}/login/oauth2/code/{registrationId}


# Optional: define base URL (especially useful if deploying later)
spring.security.oauth2.client.registration.google.client-name=Google

# -----------------------------------------
# ? SERVER CONFIG FOR HEROKU DEPLOYMENT
# -----------------------------------------
server.port=${PORT:8080}

# -----------------------------------------
# ? LOGGING (Optional - helps debug login issues)
# -----------------------------------------
logging.level.org.springframework.security=DEBUG
logging.level.org.springframework.security.oauth2=DEBUG
