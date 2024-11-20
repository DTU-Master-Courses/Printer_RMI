## Introduction

## Authentication

### Password Storage 
#### Analysis of the 3 solutions
1. System File 
   For implementation, the credentials are stored in a ``system file`` that is protected by the OS's file system mechanisms. The file is only accessible by processes with highly ``privileged roles`` (e.g., admin). Normal users DO NOT have ``read and write`` access to the file. Passwords SHOULD BE ``encrypted`` and ``hashed`` using a strong cryptographic algorithm (e.g., AES,PBKDF2) before being stored in the file.

   **Confidentiality**: The ``OS's file system`` protection mechanisms ensure that ONLY ``authorized processes`` can access the file. ``Encryption`` adds an additional layer of security, so that even if the file is accessed, the passwords remain ``confidential``. Therefor, normal users CANNOT ``read`` the file, preventing users from ``learning`` the passwords of other users.

   **Integrity**: The ``file system`` protection mechanisms only allow processes with elevated privileges can ``update`` the file, ensuring the ``integrity`` of the stored passwords. Therefor normal users cannot change the passwords of other users without proper authentication.

2. Public File
   Passwords are stored in a ``public file`` that can be ``read by all users`` but NOT ``written`` to. Passwords SHOULD BE ``encrypted`` and ``hashed`` using a strong cryptographic algorithm (e.g., AES,PBKDF2) before being stored in the file.

   **Confidentiality**: ``Encryption`` ensures that the passwords remain ``confidential`` even though the file is ``publicly readable``. Confidentiality prevents users from learning the passwords of other users.

   **Integrity**: The file system protection mechanisms prevent unauthorized modifications to the file. However, updating passwords in such a file poses a challenge as normal users do not have write access. ``Password updates`` require a process with ``elevated privileges``, ensuring that users ``CANNOT`` change the passwords of other users without proper authentication.

3. DBMS
   The DBMS provides fine-grained access control mechanisms to manage who can read or write the password data. Passwords SHOULD BE ``encrypted`` and ``hashed`` using a strong cryptographic algorithm (e.g., AES,PBKDF2) before being stored in the file.

   **Confidentiality**: The access control ensures that only ``authorized`` users can access the password data. ``Encryption`` adds an additional layer of security, ensuring that even if the database is accessed, the passwords remain confidential. Therefor unauthorized users cannot learn the others'password.

   **Integrity**: The access control prevents unauthorized ``modifications`` to the password data(including other users'). Only authorized users can update the passwords, ensuring the integrity.

**Pros**: They all provide confidentiality and integrity, but ``DBMS`` ``fine-grained access control`` and is more flexible for managing password updates compared to the other two solutions.

**Cons**: management of file permissions and elevated privileges for password updates in ``System File`` can be complecated. ``Public File`` poses challenges for password updates due to read-only access for normal users.

   
   


Access Control: The file system protection mechanisms ensure that normal users have read-only access to the file.
Encryption: Passwords are encrypted using a strong cryptographic algorithm (e.g., AES) before being stored in the file.   

For convinence's sack in this lab, I use **public file** to store user credential. ``Confidentiality`` is reached as I encrypted(**AES**) username and password. I do not implement ``integrity`` due to practical matters. However, I should emphasis that normal user should not have write access to the file, in the implementation phase I just mimic that I have ``admin role``, and ``manualy`` upsert data to the file. If normal user want to update password, they should go through an ``extra authentication`` process by proving they are who they claim they are. When they are anthenticated, a process with admin role will update the password. 

![1698876535208](image/README/1698876535208.png)

![1731876354433](image/README/1731876354433.png)

### Password transport
Regarding Passwords Transport, we operate under the assumption that Transport Layer Security (TLS) is already implemented within our system. TLS plays a critical role in ensuring the secure transmission of passwords between the client and server, safeguarding sensitive information from potential interception by unauthorized entities.

### Password verification


### Session management

##  Access Control Lists

## Role Based Access Control

## Changes in the policy

## Evaluation

## Conclusion