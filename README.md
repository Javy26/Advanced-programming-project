# Advanced-programming-project
advanced programming group project which acts as a file management system. eg. dropbox

\\\What the code does now:
1.	A basic gui with 5 buttons; 1 for each folder on the server (removes the user option from looking elsewhere or in the future saving elsewhere.
2.	When a folder button is clicked:
a.	 the client sends a message to the server for a list of all files in that location
b.	That list is then used to create a new window with labels for each file
c.	These labels are clickable
3.	When a label is clicked:
a.	The client sends a request to the server for a copy of that file
b.	That file is then stored in a location specified by the code
c.	Using the desktop api the file is opened at the moment pdfs have to be clicked twice not sure why but will be addressed later.

NB.
All file paths must be redone by you to test the code.  The server the folder searches must have the five folders the project asked for and their names must match the labels in the fileWindows gui.
The folder on the client side does not require any folders and will act as a temporary holding space for the files sent by the server to be opened.

