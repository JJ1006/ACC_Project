package com.project.web.Service;

import org.springframework.stereotype.Component;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class WordFrequencyCounter {

    /*
        This function is used to calculate word
        frequency.
     */
    public  static int calculateFrequency(String inputStr, int minStrLength,String product) {
        Map<String, Integer> frequencyCounter = new HashMap<>();

        // Regex to remove punctuation
        String regexToMatch = "[.,!?;:'']";

        // Pattern object
        Pattern patternToMatch = Pattern.compile(regexToMatch);

        // Matcher object
        Matcher patternMatcher = patternToMatch.matcher(inputStr);

        // remove punctuation
        String output = patternMatcher.replaceAll("");

        // Initialize tokenizer
        StringTokenizer tokenizer = new StringTokenizer(output);

        // create token and add into the map with frequency
        while (tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken().toLowerCase();

            if (!(frequencyCounter.containsKey(token))) {
                if (token.length() > minStrLength) {
                    frequencyCounter.put(token, 1);
                }
            } else {
                int value = frequencyCounter.get(token);
                frequencyCounter.put(token, value + 1);
            }
        }
        int productFrequency = printWordFrequencyAndLexicalRichness(frequencyCounter,product);
        return productFrequency;
    }

    /*
        This function is used to sort word frequency in
        descending order, print the words and calculate lexical richness.
     */
    public  static int printWordFrequencyAndLexicalRichness(Map<String, Integer> freuencyCounter,String product) {
        System.out.println("Frequency of "+product);
        int productFrequency =0;
        try{
            productFrequency = freuencyCounter.get(product.toLowerCase());
            System.out.println(productFrequency);
        }catch (Exception e){
            System.out.println(product+" not found.");
        }
        float totalWords = 0;
        float uniqueWords = 0;
        float lexicalRichness = 0;
        totalWords = freuencyCounter.values().stream().reduce(0, Integer::sum);
        for (String key : freuencyCounter.keySet()) {
            if (freuencyCounter.get(key) == 1) {
                uniqueWords += 1;
            }
        }

        lexicalRichness = (uniqueWords / totalWords) * 100;
        System.out.println("Lexical richness of the document is: " + lexicalRichness + "%");
        return productFrequency;
    }


}