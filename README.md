This project involved the writing of a functional chatting application using threads and sockets. For 
this, ChatServer and ChatClient classes were created. In the ChatServer class an integer PORT is 
specified, then in the ChatClient class this port is searched for when connecting. This number is 
arbitrary and could be changed if desired on the server side, and the client will always be able to 
connect to it.

If the client disconnects the server will display "Client disconnected", and if the user types "\q" the 
server will shut down; this will, on the client's side display "Client Shutdown, Goodbye" and then 
"Connection lost to server". If the client attempts to connect to the server and it is not running, the 
client will be given the message "Server cannot be reached". If the server severs the connection 
with the client the message "Connection lost to server" is displayed on the client's side.


