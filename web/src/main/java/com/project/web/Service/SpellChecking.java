package com.project.web.Service;

import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpellChecking {
    @Value("${word.completion.path}")
    String filePath = "C:\\\\Users\\\\admin\\\\Desktop\\\\git\\\\ACC_Project\\\\web\\\\src\\\\main\\\\resources\\\\wordcompletion.txt";
    List<String> validWords = new ArrayList<>();
    public  void readValidWords(){


        try{
            FileReader reader = new FileReader(filePath);
            BufferedReader br = new BufferedReader(reader);

            String line;
            while((line = br.readLine()) != null){
                validWords.add(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
    }

    private int editDistance(String word1, String word2) {
        int word1Len = word1.length();
        int word2Len = word2.length();

        int[][] intArray = new int[word1Len + 1][word2Len + 1];

        for (int i = 0; i <= word1Len; i++) {
            intArray[i][0] = i;
        }

        for (int j = 0; j <= word2Len; j++) {
            intArray[0][j] = j;
        }

        for (int i = 1; i <= word1Len; i++) {
            for (int j = 1; j <= word2Len; j++) {

                // Compare characters from both words
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    intArray[i][j] = intArray[i - 1][j - 1];
                } else {
                    intArray[i][j] = Math.min(intArray[i - 1][j], Math.min(intArray[i][j - 1], intArray[i - 1][j - 1])) + 1;
                }
            }
        }

        return intArray[word1Len][word2Len];
    }

    public List<String> suggestCorrections(String userInput, int threshold) {
        readValidWords();
        List<String> suggestedCorrections = new ArrayList<>();
        int minDistance = Integer.MAX_VALUE;

        // iterate through all the valid words and calculate suggestions
        for (String word : validWords) {
//			int distance = 0;
            if (!userInput.equalsIgnoreCase(word)) {
                int distance = editDistance(userInput.toLowerCase(), word.toLowerCase());

                if (distance < minDistance && distance <= threshold) {
                    minDistance = distance;
                    suggestedCorrections.clear();
                    suggestedCorrections.add(word);
                }
//			else if (distance == minDistance && distance <= threshold) {
//				suggestedCorrections.add(word);
//			}
            }

        }
        return suggestedCorrections;
    }
}
