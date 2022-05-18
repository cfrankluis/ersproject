# Expense Reimbursement Project
This an expense reimbursment software that helps finance managers to track the reimbursement requests from employees.

## Technologies Used
- Java Servlets
- HTML, CSS, JavaScript
- Bootstrap
- AJAX
- JUnit

## Features
- Login as an employee or a finance manager
- Employees are allowed to submit a reimbursement
- Employees can see their past reimbursements
- Finance managers are allowed to approve or deny reimbursements
- Finance managers can see all reimbursements from all employees
- Finance managers can filter reimbursements by status

## Get Started
1. Clone Repository `git clone https://github.com/cfrankluis/ersproject.git`
2. Setup Environment Variables
```
datachanURL = datachan.czwpdxwxjdsh.us-east-1.rds.amazonaws.com:5432
datachanUsername = datachan
datachanPassword = p4ssw0rd
```
3. [Download and Extract Spring Tool Suite 3](https://download.springsource.com/release/STS/3.9.18.RELEASE/dist/e4.21/spring-tool-suite-3.9.18.RELEASE-e4.21.0-win32-x86_64.zip)
4. [Download and extract Tomcat](https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.63/bin/apache-tomcat-9.0.63.zip)
5. Open STS 3 and set workspace to any location
6. Import the cloned repository as a maven project
8. Add the extracted Tomcat folder into Runtime environments in the workspace
9. Add cloned repository to Tomcat Server
10. Start Tomcat server
11. Open a browser and go to `localhost:8080/ersproject`

## Usage
- Login as employee
```
Usename => qwerty
Password => qwerty
```

- Login as Finance Manager
```
Username => abc123
Password => cde456
```

## License
This project was made using Spring Tool Suite Version 3.9.18
