# hercampus-weekly-assignment-planner
A Java program that organizes and randomly assigns weekly social media tasks to the Her Campus Chapter at Texas State University Social Media Team members. .
The Her Campus Social Media Assigner is a Java program I created as one of the Social Media Co-Directors for the Her Campus Texas State University chapter.

As a Social Media Co-Director, I help organize weekly assignments for our Social Media Team. I created this program to provide a fair, efficient, and helpful way to organize weekly assignment plans instead of manually assigning each task.

The program takes information about the team and the week's content, then randomly assigns team members to different types of social media assignments. This helps with assigning weekly content more easily and helps distribute assignments across the whole team.

How It Works. .
  - Enter the number of Social Media Team members and their names
  - Enter the number of articles being published for the week
  - Automatically assign two TikToks
  - Create in-feed Instagram post assignments as additional assignments after TikToks and story posts are assigned
  - Randomly assign team members to assignments
  - Make sure that assignments are distributed across the team before repeating assignments
  - Automatically assign extra articles that have been assigned to a team member to be handled by the co-directors
  - Display all weekly assignments in an organized list

When the program starts, the user enters the Director's name and the names of every member of the Social Media Team.
The program asks how many articles are being published that week. Article story posts are automatically created based on that number. 
The program also creates at least two TikTok assignments. If there are not enough article story/special assignments to give everyone on the team, the program creates additional in-feed Instagram posts.
If there are more articles than team members, each member can receive one article story post, and the remaining articles will be assigned to the co-directors.
The program asks if there are any special posts planned for the week. If there is a special post, the user will enter a description and choose which team member should handle it. 

Technologies. .
  - Java
  - Java Scanner
  - Java Random
  - Arrays
  - Loops
  - Conditional statements
  - User Input
  - Random assignment logic

How to Run. .
  1. Make sure that Java is installed on your computer.
  2. Download or clone this repository.
  3. Open the project in VS Code or another Java IDE.
  4. Compile and run Main.java.
  5. Follow the prompts in the terminal.

Example. .
The program will ask questions such as:

Enter the Director's name: Ella
How many girls are on the Social Media Team? 15
How many articles are being published this week? 9
Are there any special posts this week? (yes/no): yes

The program will then display the weekly assignments by category:

ARTICLE STORY POSTS
----------------------------------------
1. alina
2. marina
3. jayda
...

TIKTOKS
----------------------------------------
1. heather
2. alexia

IN-FEED POSTS
----------------------------------------
...

SPECIAL POST
----------------------------------------
Post: exec board photo shoot
Assigned to: heather

Purpose. .
The purpose of this project is to make weekly social media assignment planning more fair, efficient, and organized for the Her Campus Texas State Social Media Team. As a Social Media Co-Director, I created this program to simplify the assignment process and reduce the time spent manually organizing weekly tasks.
