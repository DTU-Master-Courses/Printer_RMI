# Printer_RMI

a simple client/server application using RMI. The example used in this lab is a mock-up of a simple authenticated print server, such as a print server installed in a small company.

https://www.tutorialspoint.com/java_rmi/java_rmi_application.htm

# Printer_RMI

a simple client/server application using RMI. The example used in this lab is a mock-up of a simple authenticated print server, such as a print server installed in a small company.

The print server must support the following operations:

- print(String filename, String printer);   // prints file filename on the specified printer
- queue(String printer);   // lists the print queue for a given printer on the user's display in lines of the form `<job number>`   `<file name>`
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

2. Start the rmi registry using the following command.

```
start rmiregistry
```

![1698839417045](image/README/1698839417045.png)

![image](https://github.com/DTU-Master-Courses/Printer_RMI/assets/7116535/11f12e42-cfd1-479b-b65a-f79783fad555)

```
Java Server
```

![1698839453178](image/README/1698839453178.png)

```
Java Client
```

![1698839481002](image/README/1698839481002.png)

3. process of choosing operations, authentication, and how what the server print out after an operation is done. It showcases print() and queue() operations:

![1698839561908](image/README/1698839561908.png)

![1698839729817](image/README/1698839729817.png)

in case of multiple printers:

![1698874362328](image/README/1698874362328.png)

![1698874232952](image/README/1698874232952.png)

4. Authentication is done by using hash, secret key and encryption, user name is being encrypted(**AES**), password being hashed by **SHA-256,** and a AES secretKey to encrypt/decrypt user name. This is an example of **public file** storing credentials.
   ![1698876535208](image/README/1698876535208.png)
5.
