# Pong
I have created the original arcade game, Pong. I utilized Processing3 on Eclipse IDE to create this game using the JApplet library along with multiple Processing3 features.

## Prerequistes
1. Download Processing3 onto your computer, use this link: https://processing.org/download/ 
2. Download Eclipse IDE onto your computer, use this link: https://www.eclipse.org/downloads/

## Deployment Instructions
1. Open Eclipse and create a new project, FILE --> NEW --> JAVA PROJECT. Name the project.
2. Right click on your project in the Package Explorer --> IMPORT --> GENERAL --> FILE SYSTEM 
3. Within the "From Directory" section, click BROWSE --> (**Now go to your Processing3 folder**) PROCESSING-3.4 --> CORE --> LIBRARY --> SELECT FOLDER
4. Once all the .jar files are seen on the right box, only click CORE.JAR --> FINISH
5. Now the core.jar file should be within your Referenced Libraries inside your package where you will right click CORE.JAR --> BUILD PATH
6. Create the classes and copy the respective code into these files 
   - Pong.java
   - Paddle.java
   - Ball.java 
7. Create a JUnit Test Case and copy the respective code
   - AngleSegmentTest.java
8. Run Pong.java

For further assistance in adding the Processing file system to Eclipse, please refer to: 
- https://processing.org/tutorials/eclipse/ 
- https://www.youtube.com/watch?v=5h0G_MyDD5w
