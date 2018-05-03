# Signed JSON Web tokens (JWT) generator

I was looking to integrate with DBS Developer APIs (https://www.dbs.com/developers/index.html) recently, where they need signed JWT Tokens to generate access tokens used for authentication (OAuth2).  
Generating JWTs are easy (its just a compiled list of claims) but I could not find a tool online to sign it. I was looking for browser based tools - http://jwt.io chrome extension comes close, but signing is still tricky.   

Useful links:  
1) If you are looking for more information  on JSON Web Tokens - https://tools.ietf.org/html/rfc7519  
2) http://jwt.io has a great chrome extension to create a unsigned JWT based on your claims  
3) Auth0 Project - Great library used in this generator - https://github.com/auth0/java-jwt   
  
In order to sign JWT, you first need a certificate!!  

## Certificates (If you don't have one yet)  
1) Generate public and private keys. I am using openssl.   
$ openssl req -nodes -text -x509 -newkey rsa:2048 -keyout private.pem -out cert.pem -days 365  
This should create two files - cert.pem and private.pem.   
2) You normally upload the public part (cert.pem) to the authenticating service. For the private key (for the sake of this utility) edit the private.pem and remove the lines that look like -   
-----BEGIN PRIVATE KEY-----  
and   
-----END PRIVATE KEY-----  

I dislike doing this, so note to future self: extend this class to accept other encodings.  

## Generate JWT
1) Edit the class "SampleJwtTestUsing0AuthJWT" where we generate claims with stuff you need (I reverse engineered some of the claims from DBS websites examples. You dont have to use these)  
2) Edit the class to refer this newly edited private key.  

## Run!
The token generated should have 3 parts ('.' separated) You can use http://jwt.io extension to check if your token is valid. The extension should help you reverse engineer your claims.   
 

