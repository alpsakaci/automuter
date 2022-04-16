# Automuter
Batch account muting and unfollower finder for twitter.

### Build and run
#### Build application on local machine
```
./mvnw clean install
```
##### Build docker image
```
docker build -t automuter .
```
##### Run application
```
docker run -d --name automuter \
-e "TWITTER_CLIENT_ID=your-twitter-client-id" \
-e "TWITTER_CLIENT_SECRET=your-twitter-client-secret" \
-p 8080:8080 automuter
```
