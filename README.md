# Bisection-Modified-Secant-GUI-with-Javafx
HOW TO USE:
---------------------------------
-Either by downloading the jar files and run it directly, or by compling the java code. If you had any problems running the jar files then compile the code but don't forget to add exp4j.jar file in the project directory through netbeans/eclipse.
-After running the program, enter the equation and the values of A, B and the tolerance. And click on which method you want your equation to be treated with.

Logic behind the code:
------------------------------------
There is nothing special, just using the refered methods rules and parsing the user input to double.
Note: the Modified-Secant may divrage so the code will enter in infite loop, that why I made the for loop stop at 10 iterations.
Both the methods are very samiler only the change in the Mid-Point rule which is referd with "C" in the code.
To eval the sting equation I used custom library exp4j, all right reserved to its respective owner.
If you want to edit in the code make sure to download it from here: https://www.objecthunter.net/exp4j/ or from my repository.
and insde the project right click on directories tree and click ADD Jar/Folder and use it, the code shall work with no problems.
The following picture illsturate how to add jar files to the project through netbeans.
https://imgur.com/a/n2Yf7

For further inquires contact me on mohamedtarekeltorky@gmail.com or leave a comment.

UPDATES:
------------------------------------
3/17/2018 added a modifed .java file to handle the user input error.
*Now the program won't accept any inputs unless it's vaild equation with vaild A,B values.
