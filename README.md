# Employee Monthly Pay Slip

[Web demo](https://employeepayslip.azurewebsites.net)

Coding exercise for generating pay slip of employee in a month. It is deployed to Azure App Services as a microservice that can be called in applications.

When supplied with employee details: first name, last name, annual salary (positive integer) and super rate (0%-50% inclusive), payment start date, the program should generate pay slip information which includes name, pay period, gross income, income tax, net income and super.

Noteable features:
- Microsoft Azure: For cloud deployment and management of the web app.
- Spring Boot: For building RESTful web services
- Gradle: For optimizing building procedures
- Java: Language used for coding (Version 17)

## Calculation Logic

- pay period = per calendar month
- gross income = annual salary / 12 months
- income tax = based on the tax table provide below
- net income = gross income - income tax
- super = gross income x super rate

Notes: All calculation results should be rounded to the whole dollar. If >= 50 cents round up to the next dollar increment, otherwise round down.

Income tax rates as below:

![image](https://github.com/user-attachments/assets/62e45331-691c-4ed7-a798-28623baba6bd)

## Instructions

Initiate a curl command from terminal/command prompt. Then add the absolute path of input csv and specify the desired name for the output csv.

```
curl -X POST -F 'file=@absolute-path-to-input.csv' https://employeepayslip.azurewebsites.net/generate --output output-name.csv
```

Example:

```
curl -X POST -F 'file=@C:/Users/User/input.csv' https://employeepayslip.azurewebsites.net/generate --output payslip.csv
```

The input file should be in csv format with headers, like so:

```
FirstName,LastName,AnnualSalary,SuperRate,DateRange
Monica,Tan,60050,9.0,01 March – 31 March
Brend,Tulu,120000,50.0,01 March – 31 March
...
```

Make sure the csv output look like this:

```
Name,Pay Period,Gross Income,Income Tax,Net Income,Super
Monica Tan,01 March – 31 March,5004,922,4082,450
Brend Tulu,01 March – 31 March,10000,2669,7331,5000
```

Run automated test:

```
./gradlew test
```

## Assumptions 
- The csv file has headers and contains no missing information.
- Super rate in csv is assumed to be in percentages (e.g. 10% is written 10.0 in the csv file)
- Each payment period lasts precisely one month.
