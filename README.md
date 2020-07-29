# java-microservice-eureka

Eureka server and config server implemented in eureka service application and port number is 8090 for service

**Manager service consumes employee service through eureka client.** Here I used Employee service as a provider service to be consumed by manager Service.
configurations are present at git repository- https://github.com/ajaysh540/java-task-config-server.git

To start clone the repository into local and import as maven project into IDE.

First start Eureka-service-application which runs on port 8090.
Then Manager or Employee Application in any order.
Port for manager and employee application are 8081 and 8083 respectively.
Ports can be changed in bootstrap.properties file in resource folder of the application.



##Manager Service
    
  Spring Security Enabled with In Memory Credentials for two Roles:
  Calls Will be made to Employee service with JWT token received from service by logging in from Manager Service.
  **Internally logs into Employee service and uses JWT token to send requests.**
    
  - User
  - Manager
  
    User: can access some pages.
    Manager: can access All pages.
    Credentials: 
    User - username : user, password: user.
    Manager- username: manager, passwrod: manager.
        
    To Login-
    endpoint- localhost:8081/login
    Browser can be used to login via form or postman can be used by sending a post request.
    While sending post request via postman enter username and password as form-data in request for respective fields.
        
    End Points in Application:
        - /user/testconfig : returns value of message form config-server (Get Request Reuires Login as USER or MANAGER).
        - /user : Returns a simple message (Get request Requires login as USER or MANAGER).
        - /manager/employees: Get Request by manager to consume Employee Service and returns All available employees in DB. Initially empty.
        - /manager/addemployee: Post Request that Adds Employee to db via Employee Service
        - /manager/delete: Post Request, deletes an employee
        - /manager/update: Post Request to update an employee by passing Employee Id in Employee object
        
    Employee object to be passed contains following values: 
        
        {
        "Id": null, //auto generated passed null while adding.
        "firstName": "" ,
        "lastName":"",
        "department","", //any string values
        "contactNumber": 898799,//any number
        }
    
##Employee Service

Implemented JWT Authentication.

Takes request from Manager Service, is not protected by any security.

Uses MySql Db.
Db name is "employee" 
Assumed db is up and running on port:3306
To configure check properties.

Use post request to /authenticate with following object in body:
 
        {
        "username":"employee",
        "password":"employee"
        }

Return object will be JWT token used in all subsequent requests as
Authorization Header.
