import java.io.*;
import java.util.*;

public class GradeTracker {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String name[] = new String[100];
        int roll[] = new int[100];
        int marks[][] = new int[100][3];
        double avg[] = new double[100];
        char grade[] = new char[100];
        String status[] = new String[100];
        int n;

        System.out.print("How many students: ");
        n = sc.nextInt();

        // input
        for(int i = 0; i < n; i++) {
            System.out.print("Enter name: ");
            name[i] = sc.next();
            System.out.print("Enter roll: ");
            roll[i] = sc.nextInt();

            System.out.print("Enter 3 marks: ");
            int sum = 0;
            for(int j = 0; j < 3; j++) {
                marks[i][j] = sc.nextInt();
                sum = sum + marks[i][j];
            }
            avg[i] = sum / 3.0;

            if(avg[i] >= 90) grade[i] = 'A';
            else if(avg[i] >= 75) grade[i] = 'B';
            else if(avg[i] >= 60) grade[i] = 'C';
            else if(avg[i] >= 40) grade[i] = 'D';
            else grade[i] = 'F';

            if(avg[i] >= 40) status[i] = "Pass";
            else status[i] = "Fail";
        }

        // PROPER TABLE
        System.out.println();
        System.out.println("+------------+--------+---------+-------+--------+-----------+");
        System.out.printf("| %-10s | %-6s | %-7s | %-5s | %-6s | %-9s |\n", "NAME", "ROLL", "AVERAGE", "GRADE", "STATUS", "REMARK");
        System.out.println("+------------+--------+---------+-------+--------+-----------+");

        for(int i = 0; i < n; i++) {
            String remark;
            if(grade[i] == 'A') remark = "Excellent";
            else if(grade[i] == 'B') remark = "Good";
            else if(grade[i] == 'C') remark = "Average";
            else if(grade[i] == 'D') remark = "Poor";
            else remark = "Fail";

            System.out.printf("| %-10s | %-6d | %-7.2f | %-5c | %-6s | %-9s |\n",
                name[i], roll[i], avg[i], grade[i], status[i], remark);
        }
        System.out.println("+------------+--------+---------+-------+--------+-----------+");

        // Class Average
        double classTotal = 0;
        for(int i = 0; i < n; i++) classTotal += avg[i];
        double classAvg = classTotal / n;
        System.out.println("\nClass Average: " + String.format("%.2f", classAvg) + "%");

        // Topper
        double max = avg[0];
        int pos = 0;
        for(int i = 1; i < n; i++) {
            if(avg[i] > max) {
                max = avg[i];
                pos = i;
            }
        }
        System.out.println("Topper: " + name[pos] + " with " + String.format("%.2f", max) + "%");

        // Subject toppers
        System.out.println("\n--- Subject Toppers ---");
        for(int j = 0; j < 3; j++) {
            int subMax = marks[0][j];
            int subPos = 0;
            for(int i = 1; i < n; i++) {
                if(marks[i][j] > subMax) {
                    subMax = marks[i][j];
                    subPos = i;
                }
            }
            System.out.println("Subject " + (j+1) + " Topper: " + name[subPos] + " - " + subMax + " marks");
        }

        // save same table to file
        try {
            FileWriter fw = new FileWriter("Report.txt");
            fw.write("+------------+--------+---------+-------+--------+-----------+\n");
            fw.write(String.format("| %-10s | %-6s | %-7s | %-5s | %-6s | %-9s |\n", "NAME", "ROLL", "AVERAGE", "GRADE", "STATUS", "REMARK"));
            fw.write("+------------+--------+---------+-------+--------+-----------+\n");
            for(int i = 0; i < n; i++) {
                String remark;
                if(grade[i] == 'A') remark = "Excellent";
                else if(grade[i] == 'B') remark = "Good";
                else if(grade[i] == 'C') remark = "Average";
                else if(grade[i] == 'D') remark = "Poor";
                else remark = "Fail";
                fw.write(String.format("| %-10s | %-6d | %-7.2f | %-5c | %-6s | %-9s |\n",
                    name[i], roll[i], avg[i], grade[i], status[i], remark));
            }
            fw.write("+------------+--------+---------+-------+--------+-----------+\n");
            fw.write("\nClass Average: " + String.format("%.2f", classAvg) + "%");
            fw.close();
            System.out.println("\nReport saved in Report.txt");
        } catch(Exception e) {
            System.out.println("Error in file");
        }
        sc.close();
    }
}
