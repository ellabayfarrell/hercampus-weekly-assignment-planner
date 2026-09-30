import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random random = new Random();

        System.out.println("========================================");
        System.out.println("    HER CAMPUS: SOCIAL MEDIA WEEKLY"); 
        System.out.println("          ASSIGNMENT PLANNER");
        System.out.println("========================================");

        // Director's name
        System.out.print("\nEnter the Director's name: ");
        String director = input.nextLine();

        // Getting all of the girls on the team
        System.out.print("\nHow many girls are on the Social Media Team? ");
        int numOfGirls = input.nextInt();

        String[] girls = new String[numOfGirls];

        for (int i = 0; i < numOfGirls; i++) {
            System.out.print("Enter girl #" + (i + 1) + "'s name: ");
            girls[i] = input.next();
        }

        // Get the number of articles being published for the week
        System.out.print("\nHow many articles are being published this week? ");
        int numOfArticles = input.nextInt();

        // Ask about any special content that needs to be posted this week
        System.out.print("\nAre there any special posts this week? (yes/no): ");
        String specialAnswer = input.next();

        String specialPost = "";
        String specialGirl = "";

        if(specialAnswer.equalsIgnoreCase("yes")) {
            input.nextLine();

            System.out.print("Describe the special post: ");
            specialPost = input.nextLine();

            System.out.println("\nWho should be assigned to the special post?");

            for (int i = 0; i < numOfGirls; i++) {
                System.out.println((i + 1) + ". " + girls[i]);
            }

            System.out.print("Enter the number of the girl: ");
            int specialChoice = input.nextInt();

            specialGirl = girls[specialChoice - 1];
        }
            
        /*
        * Assignment rules:
        *
        * Article Story Posts = number of articles
        * TikToks = at least 2
        * In-Feed Posts = remaining assignments
        * We want at least one assignment per girl.
        * Director will handle any extra remianing assignments.
        */

        int tiktokCount = 2;

        // Figure out how many article story posts can be assigned to girls
        int articlesAssignedToGirls = numOfArticles;

        if (articlesAssignedToGirls > numOfGirls) {
            articlesAssignedToGirls = numOfGirls;
        }

        // Figure out how many article story posts the co-directors will handle
        int remainingArticles = numOfArticles - articlesAssignedToGirls;

        int normalAssignmentCount = articlesAssignedToGirls + tiktokCount;

        int minimumAssignments = numOfGirls;

        int totalAssignments;

        // If there are not enough article story posts or tiktoks, then create infeed post
        if (normalAssignmentCount < minimumAssignments) {
            totalAssignments = minimumAssignments;
        } 
        else {
            totalAssignments = normalAssignmentCount;
        }

        int inFeedCount = totalAssignments - articlesAssignedToGirls - tiktokCount;

        // If there is already enough assignments from articles and tiktoks, might be no infeed post

        if (inFeedCount < 0) {
            inFeedCount = 0;            
        }

        // Create the arrays for assignments
        String[] assignmentTypes = new String[totalAssignments];
        String[] assignedGirls = new String[totalAssignments];
        
        int assignmentIndex = 0;

        // Add article story posts
        for (int i = 0; i < articlesAssignedToGirls; i++) {
            assignmentTypes[assignmentIndex] = "Article Story Post";
            assignmentIndex++;
        }

        // Add TikToks
        for (int i = 0; i < tiktokCount; i++) {
            assignmentTypes[assignmentIndex] = "TikTok";
            assignmentIndex++;
        }

        // Add infeed posts
        for (int i = 0; i < inFeedCount; i++) {
            assignmentTypes[assignmentIndex] = "In-Feed Post";
            assignmentIndex++;
        }

        /*
        * Randomly assign girls to the assignments.
        * First, need to shuffle the girls so everyone gets one assignment before assignments repeat.
        */

        int[] girlOrder = new int[numOfGirls];

        for (int i = 0; i < numOfGirls; i++) {
            girlOrder[i] = i;
        }

        // Shuffle the girl's order
        for (int i = numOfGirls - 1; i > 0; i--) {
            int randomIndex = random.nextInt(i + 1);

            int temp = girlOrder[i];
            girlOrder[i] = girlOrder[randomIndex];
            girlOrder[randomIndex] = temp;
        }

        // Assign the girls to normal assignments
        for (int i = 0; i < totalAssignments; i++) {
            assignedGirls[i] = girls[girlOrder[i % numOfGirls]];
        }

        // Print the assignments
        System.out.println("\n========================================");
        System.out.println("       WEEKLY ASSIGNMENTS");
        System.out.println("========================================");

        System.out.println("\nARTICLE STORY POSTS");
        System.out.println("----------------------------------------");

        int articleNumber = 1;

        for (int i = 0; i < totalAssignments; i++) {
            if (assignmentTypes[i].equals("Article Story Post")) {
                System.out.println(
                        articleNumber + ". " + assignedGirls[i]);
                articleNumber++;
            }
        }

        // Print the remaining article story posts for the co-directors
        if (remainingArticles > 0) {
            System.out.println(
                    "\nThe co-directors will handle the rest of the "
                    + remainingArticles + " articles.");
        }

        System.out.println("\nTIKTOKS");
        System.out.println("----------------------------------------");

        int tiktokNumber = 1;

        for (int i = 0; i < totalAssignments; i++) {
            if (assignmentTypes[i].equals("TikTok")) {
                System.out.println(
                        tiktokNumber + ". " + assignedGirls[i]);
                tiktokNumber++;
            }
        }

        System.out.println("\nIN-FEED POSTS");
        System.out.println("----------------------------------------");

        if (inFeedCount > 0) {

            int inFeedNumber = 1;

            for (int i = 0; i < totalAssignments; i++) {
                if (assignmentTypes[i].equals("In-Feed Post")) {
                    System.out.println(
                            inFeedNumber + ". " + assignedGirls[i]);
                    inFeedNumber++;
                }
            }

        } else {
            System.out.println(
                    "The co-directors will handle the In-Feed Posts "
                    + "for the week due to the amount of Article Story Posts.");
        }

        // Print special post if there is one
        if (specialAnswer.equalsIgnoreCase("yes")) {

            System.out.println("\nSPECIAL POST");
            System.out.println("----------------------------------------");
            System.out.println("Post: " + specialPost);
            System.out.println("Assigned to: " + specialGirl);
        }

        System.out.println("\n========================================");
        System.out.println("      WEEKLY ASSIGNMENTS PLANNED!");
        System.out.println("========================================");

        input.close();
    }
}