package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        File inFile = new File("input.txt");
        Scanner scr = null;
        try {
            scr = new Scanner(inFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileWriter fout = new FileWriter("output.txt");
        String con = new String(scr.nextLine());
        int[][] dp = new int[con.length()][con.length()];
        for (int i = 0; i < con.length(); i++) {
            dp[i][i] = 1;
        }
        for (int j = 1; j < con.length(); j++) {
            for (int i = j-1; i >= 0; i--) {
                if (con.charAt(i) == con.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }

        System.out.println(dp[0][con.length()-1]);
        int lenght =dp[0][con.length()-1];
        StringBuilder ans = new StringBuilder(dp[0][con.length()-1]);
        Stack<Character> ansSecondPart = new Stack<Character>();
        int i = 0, j = con.length() - 1;
        while ((i <= j)) {
            if (con.charAt(i) == con.charAt(j)) {
                ans.append(con.charAt(i));
                ansSecondPart.push(con.charAt(i));
                i++;
                j--;
            } else {
                if (dp[i][j] == dp[i+1][j]) {
                    i++;
                } else {
                    j--;
                }
            }
        }
        if(lenght%2!=0)ansSecondPart.pop();
        while(!ansSecondPart.isEmpty()){
                ans.append(ansSecondPart.pop());
        }
        fout.write(Integer.toString(lenght)+'\n');
        fout.write(new String(ans));
        scr.close();
        fout.close();
    }
}