# Printer_RMI
a simple client/server application using RMI. The example used in this lab is a mock-up of a simple authenticated print server, such as a print server installed in a small company.

https://www.tutorialspoint.com/java_rmi/java_rmi_application.htm

# Printer_RMI
a simple client/server application using RMI. The example used in this lab is a mock-up of a simple authenticated print server, such as a print server installed in a small company.


The print server must support the following operations:

- print(String filename, String printer);   // prints file filename on the specified printer
- queue(String printer);   // lists the print queue for a given printer on the user's display in lines of the form <job number>   <file name>
- topQueue(String printer, int job);   // moves job to the top of the queue
- start();   // starts the print server
- stop();   // stops the print server
- restart();   // stops the print server, clears the print queue and starts the print server again
- status(String printer);  // prints status of printer on the user's display
- readConfig(String parameter);   // prints the value of the parameter on the print server to the user's display
- setConfig(String parameter, String value);   // sets the parameter on the print server to value



# how to use the program

1. Open the folder where you have stored all the programs, in this case /src, and compile all the Java files as shown below.
```
Javac *.java
```
2.  Start the rmi registry using the following command.
```
start rmiregistry
```
![image](https://github.com/DTU-Master-Courses/Printer_RMI/assets/7116535/ddee7e73-36c1-4de8-a24f-840547fd3b1c)
![image](https://github.com/DTU-Master-Courses/Printer_RMI/assets/7116535/157a734f-97b6-454d-a245-e4eb4c88f2d4)

3. 
```
Java Server
```

![image](https://github.com/DTU-Master-Courses/Printer_RMI/assets/7116535/bd667d58-54d5-4d45-825a-b7f1bc756daa)

4. 
```
Java Client
```

![image](https://github.com/DTU-Master-Courses/Printer_RMI/assets/7116535/1315715c-e3fd-43e8-a7b5-86bee27f5da4)

5.
![image](https://github.com/DTU-Master-Courses/Printer_RMI/assets/7116535/9f56bf18-3d43-485d-8b40-a877999a85e9)



