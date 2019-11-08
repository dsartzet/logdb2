# logdb
http://cgi.di.uoa.gr/~ad/M149/assignments-F19/M149-Project01.pdf

```
M149/M116: Database Management Systems
University of Athens
Deprt. of Informatics & Telecommunications
```
```
Programming Project I
Today’s Date: October 19th, 2019
Due Date: November 27th, 2019
```
## PREAMBLE

In this project, you will design, implement and demonstrate a database solution to manageserver
logs. You will also provide access to your database through a web application. A server–log is
a file that consists of a list of activities a machine has performed and recorded. For example, a
web–server log comprises a list of page requests.

The database you will create, termedLogDB, will be populated with data already collected and
available at:
https://hive.di.uoa.gr/M149/logs.tar.gz.
Pertinent data of the above data set have to bestoredinLogDBand can be used when the system
becomes operational.

PROBLEM DESCRIPTION:
Your database should include information about the server logs in anormalized formand should
provide various queries and/or aggregations on logs recorded. Moreover, the database should hold
data related to the registered users of the web application and their actions. Below is a description
of the general guidelines of the project. While you are working, keep in mind that these items make
up a minimum set of requirements. Hence, you may extend the scope of your work as you see it
appropriate and/or interesting.

Server Log Data: Different services run by the OS often maintain a history of their activities scat-
tered in a number of files and formats. A typical web server will add an entry to its access–log for
every request it services, featuring among else the IP address of the client, the request line, and a
time stamp.

LogDBwill allow for its users to perform powerful analysis on various server logs of different services
and formats. In this context, registered users can analyze the data provided either though a set of
(canned) queries or via having read-access to the database (for the more sophisticated).

There are 5 sets of log files in.txtformat:

1. access.log:This is a web–server log that features:
    - IPaddress of the client (remote host),
    - User IDof the person requesting the document as determined byHTTPauthentication.
       If the document is not password protected, this entry will be ‘-’,
    - Time stamp,
    - HTTPmethod (GET, POST, etc),
    - Resource requested,
    - HTTPresponse status (200, 400, etc),


- Response size,
- Referer,
- User agent string.
2. HDFSDataXceiver.log:
- Time stamp,
- Block ID,
- SourceIP,
- DestinationIP,
- (Optional) Size,
- Type (receiving, received, served, etc).
3. HDFSFSNamesystem.log:
- Time stamp,
- BlockID(s),
- SourceIP,
- DestinationIP(s),
- Type (replicate, remove).

Clearly, one table for each type of log will not work in a advantageous manner when it comes to
querying the data provided. While developing anormalizedschema for your database, you should
strive tonot discardany information available from the data set provided.

UsingPostgreSQL,^1 create the schema of your database, andinsert all piecesof log information.
You have the freedom to define the relations as per your own choice/design.

User Data: should include the name of every registered user, his/her login name, password, address,
email, and time-stamped queries issued to the database through the web application.

Stored Functions (Procedures):You should create a stored function for each of the following queries,
as they are particularly useful to the context ofLogDB:

1. Find the total logs per type that were created within a specified time range and sort them in
    a descending order. Please note that individual files may log actions of more than one type.
2. Find the total logs per day for a specific action type and time range.
3. Find the most common log per source IP for a specific day.
4. Find thetop-5 Block IDs with regards to total number of actions per day for a specific date
    range (for types thatBlock IDis available)
5. Find the referers (if any) that have led to more than one resources.
6. Find the 2nd–most–common resource requested.

(^1) https://www.postgresql.org/download/


7. Find the access log (all fields) where the size is less than a specified number.
8. Find the blocks that have been replicated the same day that they have also been served.
9. Find the blocks that have been replicated the same day and hour that they have also been
    served.
10. Find access logs that specified a particular version of Firefox as their browser.
11. FindIPs that have issued a particularHTTPmethod on a particular time range.
12. FindIPs that have issued two particularHTTPmethods on a particular time range.
13. FindIPs that have issued anyfourdistinctHTTPmethods on a particular time range.

It isparticularly importantthat you make sure that all above queries are executedefficiently
by adding the necessaryindices.

Web Application:You shouldalso provide accessto your database toauthorized usersthrough a web
application.

The following type of events should be handled:

```
A. Registering withLogDB: a newLogDBuser has to provide the appropriate information; she/he
can pick a login-name and a password. The login name should be checked for uniqueness.
```
```
B.QueryingLogDB: After registration, a user can start issuing queries. In particular, a user may
search for all logs associated with a specificIP(source or destination) and issue a query using
stored functions 1-3.
```
```
C.UpdatingLogDB: A user may also issue updates as she should be able to insert anewlog of
any type.
```
## IMPLEMENTATION ASPECTS:

You will usePostgreSQLas your relational database in this project. In addition, you may use any
language/framework you want includingC++, Java, Python, PHP, Ruby on Rails, Django,
Spring, Spring-boot, Angular, React, etc. As a matter of fact, the use of the https://
spring.io/projects/spring-bootis encouraged.

All the above-mentioned queries to the database could be realized via parameterizedSQLstored-
functions. The Web user interface (UI) will present users with input-fields (Web forms) and the
data are expected to be passed on to theSQLstored functions. The results have to also be displayed
in some manageable manner (should they are long).

You may work in any environment you wish but at the end you should be able to demonstrate your
work (with your notebook or via remote access to a machine).


## OVERVIEW OF PROJECT PHASES:

There are two distinct phases in this project that you will need to work on:

Database
In this phase, you are required to sketch (possibly an E/R-diagram) and generate the precise
database schema you need based on the problem definition. You must also provide theSQLqueries
and implement the stored-functions and views—the latter if deemed necessary.

Web Application
You will use the framework of your choice (such asSpring-boot,Laravel,Django,RoR, etc.) to
offer required interfaces. You will integrate/extend the Web-based user interface that consumes
the stored-functions you created.

## COOPERATION:

You may either work individually or pickat most one partnerfor this project. If you work with a
partner you areboth expected tocontribute equally. You should articulate the division of labor
in your reportandprovide sufficient explanation while demostrating your work.

## REPORTING:

The finaltypedproject report (brief report) must consist of:

1. A final schema design of the database used along with justification for your choices.
2. A parameterized stored-function for each of the requirements presented in theStored Functions
    section.
3. The code of your Web Application, preferably through a link to agitrepository.
4. Sample snapshots of the interface.

Finally as mentioned earlier, you will have to demonstrate your work.

## SUPPORT:

Panagiotis Liakos (p.liakos+@-di.) will be escorting this assignment, fill in questions, and carry
out the project interviews.


