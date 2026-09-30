import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Scanner;

public class Main {
    private static final String HISTORY_FILE = "history.txt";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random random = new Random();
        Map<Integer, Map<String, Set<String>>> history = loadHistory();

        System.out.println("========================================");
        System.out.println("    HER CAMPUS: SOCIAL MEDIA WEEKLY"); 
        System.out.println("          ASSIGNMENT PLANNER");
        System.out.println("========================================");

        System.out.print("\nWhich posting week is this? Enter the week number: ");
        int postingWeek = input.nextInt();
        input.nextLine();

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

        // Keep assignments balanced while avoiding last week's category for each girl.
        Map<String, Set<String>> previousWeek = history.getOrDefault(
                postingWeek - 1, new HashMap<String, Set<String>>());
        int[] assignmentCounts = new int[numOfGirls];
        Set<String> fallbackCategories = new HashSet<String>();

        for (int i = 0; i < totalAssignments; i++) {
            String category = assignmentTypes[i];
            int fewestAssignments = Integer.MAX_VALUE;

            // First find the lowest assignment count across the whole team.
            for (int girlIndex = 0; girlIndex < numOfGirls; girlIndex++) {
                if (assignmentCounts[girlIndex] < fewestAssignments) {
                    fewestAssignments = assignmentCounts[girlIndex];
                }
            }

            ArrayList<Integer> leastAssignedGirls = new ArrayList<Integer>();
            ArrayList<Integer> nonRepeatingGirls = new ArrayList<Integer>();
            boolean anyGirlCanAvoidRepeat = false;

            // Among the least-assigned girls, prefer those who avoid a repeat.
            for (int girlIndex = 0; girlIndex < numOfGirls; girlIndex++) {
                if (assignmentCounts[girlIndex] == fewestAssignments) {
                    leastAssignedGirls.add(girlIndex);

                    Set<String> previousCategories = previousWeek.get(girls[girlIndex]);
                    boolean hadCategoryLastWeek = previousCategories != null
                            && previousCategories.contains(category);

                    if (!hadCategoryLastWeek) {
                        nonRepeatingGirls.add(girlIndex);
                    }
                }

                Set<String> previousCategories = previousWeek.get(girls[girlIndex]);
                if (previousCategories == null || !previousCategories.contains(category)) {
                    anyGirlCanAvoidRepeat = true;
                }
            }

            ArrayList<Integer> candidates = nonRepeatingGirls.isEmpty()
                    ? leastAssignedGirls : nonRepeatingGirls;
            int chosenGirl = candidates.get(random.nextInt(candidates.size()));

            // Warn only when no teammate could have avoided this category.
            if (nonRepeatingGirls.isEmpty() && !anyGirlCanAvoidRepeat) {
                fallbackCategories.add(category);
            }

            assignedGirls[i] = girls[chosenGirl];
            assignmentCounts[chosenGirl]++;
        }

        for (String category : fallbackCategories) {
            System.out.println("\nNOTE: No teammate could avoid repeating " + category
                    + " from week " + (postingWeek - 1)
                    + "; the assignment was made anyway.");
        }

        // A requested special-post assignee cannot be changed automatically.
        if (specialAnswer.equalsIgnoreCase("yes")) {
            Set<String> previousCategories = previousWeek.get(specialGirl);
            if (previousCategories != null && previousCategories.contains("Special Post")) {
                System.out.println("\nNOTE: " + specialGirl
                        + " also had a Special Post in week " + (postingWeek - 1)
                        + "; the requested assignment was kept.");
            }
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

        // Save this week's assignments so the next run can check for repeats.
        saveHistory(postingWeek, assignmentTypes, assignedGirls, totalAssignments,
                specialAnswer, specialGirl);

        System.out.println("\n========================================");
        System.out.println("      WEEKLY ASSIGNMENTS PLANNED!");
        System.out.println("========================================");

        input.close();
    }

    // Read saved rows into week -> person -> categories, if a history file exists.
    private static Map<Integer, Map<String, Set<String>>> loadHistory() {
        Map<Integer, Map<String, Set<String>>> history =
                new HashMap<Integer, Map<String, Set<String>>>();

        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE))) {
            String row;
            while ((row = reader.readLine()) != null) {
                String[] fields = row.split("\\t", 3);
                if (fields.length != 3) {
                    continue;
                }

                try {
                    int week = Integer.parseInt(fields[0]);
                    String person = fields[1];
                    String category = fields[2];
                    history.computeIfAbsent(week, key -> new HashMap<String, Set<String>>())
                            .computeIfAbsent(person, key -> new HashSet<String>())
                            .add(category);
                } catch (NumberFormatException exception) {
                    // Ignore rows that do not begin with a valid week number.
                }
            }
        } catch (IOException exception) {
            // A missing history file is normal the first time the program runs.
        }

        return history;
    }

    // Append one tab-separated row for each assignment made this week.
    private static void saveHistory(int week, String[] assignmentTypes,
            String[] assignedGirls, int totalAssignments, String specialAnswer,
            String specialGirl) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(HISTORY_FILE, true))) {
            for (int i = 0; i < totalAssignments; i++) {
                writer.println(week + "\t" + cleanHistoryField(assignedGirls[i])
                        + "\t" + cleanHistoryField(assignmentTypes[i]));
            }

            if (specialAnswer.equalsIgnoreCase("yes")) {
                writer.println(week + "\t" + cleanHistoryField(specialGirl)
                        + "\tSpecial Post");
            }
        } catch (IOException exception) {
            System.out.println("\nNOTE: The assignments could not be saved to "
                    + HISTORY_FILE + ".");
        }
    }

    // Keep each assignment on a single row in the history file.
    private static String cleanHistoryField(String value) {
        return value.replace('\t', ' ').replace('\n', ' ').replace('\r', ' ');
    }
}