# Adding-Image-Filter 
Applications that allows a user to filter their image and receive  their image back with the new filter applied. Application is coded in Java and is focused on using Threads, each image being filtered is controlled by a thread. Once a process  is complete a new thread is created for the next process  to not allow bottle neck to occur.

## Table of contents.
* [User Guide](#user-guide)
* [Application Features](#application-features)
* [Software Used](#software-used)

## User Guide
1. Run Application: Open a command line and run the ''' $ java -jar oop.jar ''' .
2. You will have a choice between three options of adding images.
3. Insert directory as follow (e.g. C:\Name\Name\Name\NameofFolderofImages    Or    C:\Name\Name\NameofFolderofSingleImage).
4. After you insert the directory path you will be asked what filter you want to apply.
5. Images Will be filtered and sent back to the same folder under the name "ConvertedImage".png.
6. You will receive a confirmation that the images are filtered.
7. You will have option to repeat the process or leave application.

## Application Features
* Main Thread is Created in the Runner in the go() method Where it Runs all the other Threads (Each Thread is giving a unique name).
* If user enters "1" or "2" A second Thread will be created that runs class ImageDirectoryReader it is started (start()) and wait for it to come back(die)  to continue (join()).
* Once the ImageDirectoryReader Thread dies a 3rd Thread is made that runs  class ImageConverter that filters the image and wait for it to come back(die)  to continue (join()).
* If user enter "4" the main Thread is stopped (stop()) and application is finished.

## Software Used
* Java version 16
* Eclipse IDE
* Png/jpeg images.
