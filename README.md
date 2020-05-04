# Healt-Care-App
## Config mongodb auth:
- Create user: get in mongo shell
````
use admin
db.createUser(
   {
       user: "userName",
       pwd: "passWord",
       roles: [ { role: "userAdminAnyDatabase", db: "admin" } ]
   }
)
````
do this the same with your own database


- Config auth for your mongo client

open file mongod.conf with command in terminal:

`sudo vim /etc/mongod.conf`

move to #security and remove '#'

````
security:
      authorization: "enabled"
````
*remember the beginning of authorization line with 2 spaces.*
save file and restart mongo.
