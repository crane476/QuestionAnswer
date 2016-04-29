/*
 * Class: COSC 4315-001
 * Assignment 5
 */
package questionanswer;

import java.util.Scanner;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Justin Ridgeway
 */
public class QuestionAnswer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        System.out.print("Enter input file location: ");
        Scanner input = new Scanner(System.in);
        String fileLocation = input.nextLine();
        File inputFile = new File(fileLocation);
        parseDates(inputFile);
    }

    /*
     * Function Name: parseDates()
     * Arguments: File
     * Returns: N/A
     * Description: This method parses all dates from an input file and displays
     * the top 3 results in descending order.
     */
    public static void parseDates(File inputFile) throws FileNotFoundException, IOException {
        String standardDate = "((jan\\.|january)|(feb\\.|february)|(mar\\.|march)|(apr\\.|april)|may|(jun\\.|june)|(jul\\.|july)|(aug\\.|august)|(sept\\.|september)|(oct\\.|october)|(nov\\.|november)|(dec\\.|december)) (\\d\\d|\\d)(,)? \\d\\d\\d\\d";
        String standardDate2 = "(\\d\\d|\\d) ((jan\\.|january)|(feb\\.|february)|(mar\\.|march)|(apr\\.|april)|may|(jun\\.|june)|(jul\\.|july)|(aug\\.|august)|(sept\\.|september)|(oct\\.|october)|(nov\\.|november)|(dec\\.|december))(,)? \\d\\d\\d\\d";
        String numericDate = "(\\d{2}|\\d{1})(\\/|-)(\\d{2}|\\d{1})(\\/|-)\\d{4}";
        HashMap<String, Integer> dateOccurrences = new HashMap<>(); //list of unique dates and their number of occurrences
        BufferedReader in = new BufferedReader(new FileReader(inputFile));
        StringBuilder sb = new StringBuilder();
        String currentLine = null;
        while ((currentLine = in.readLine()) != null) { //read in the input file
            sb.append(currentLine);
        }
        in.close();
        String document = sb.toString().toLowerCase();
        //Standard Date Format (e.g. Jan. 2, 2006)
        Matcher match = Pattern.compile(standardDate).matcher(document);
        while (match.find()) {
            String date = document.substring(match.start(), match.end());
            String[] monthDayYear = date.split(" ");
            String month = null;
            switch (monthDayYear[0]) {
                case "jan.":
                case "january":
                    month = "1";
                    break;
                case "feb.":
                case "february":
                    month = "2";
                    break;
                case "mar.":
                case "march":
                    month = "3";
                    break;
                case "apr.":
                case "april":
                    month = "4";
                    break;
                case "may":
                    month = "5";
                    break;
                case "jun.":
                case "june":
                    month = "6";
                    break;
                case "jul.":
                case "july":
                    month = "7";
                    break;
                case "aug.":
                case "august":
                    month = "8";
                    break;
                case "sept.":
                case "sep.":
                case "september":
                    month = "9";
                    break;
                case "oct.":
                case "october":
                    month = "10";
                    break;
                case "nov.":
                case "november":
                    month = "11";
                    break;
                case "dec.":
                case "december":
                    month = "12";
                    break;
            }
            date = month + "/" + monthDayYear[1].replaceAll(",", "") + "/" + monthDayYear[2];
            if (!dateOccurrences.containsKey(date)) {
                dateOccurrences.put(date, 1);
            } else {
                dateOccurrences.put(date, dateOccurrences.get(date) + 1);
            }
        }
        //Variation of Standard Date Format (e.g. 15 September 2009)
        match = Pattern.compile(standardDate2).matcher(document);
        while (match.find()) {
            String date = document.substring(match.start(), match.end());
            String[] dayMonthYear = date.split(" ");
            String month = null;
            switch (dayMonthYear[1].replaceAll(",", "")) {
                case "jan.":
                case "january":
                    month = "1";
                    break;
                case "feb.":
                case "february":
                    month = "2";
                    break;
                case "mar.":
                case "march":
                    month = "3";
                    break;
                case "apr.":
                case "april":
                    month = "4";
                    break;
                case "may":
                    month = "5";
                    break;
                case "jun.":
                case "june":
                    month = "6";
                    break;
                case "jul.":
                case "july":
                    month = "7";
                    break;
                case "aug.":
                case "august":
                    month = "8";
                    break;
                case "sept.":
                case "sep.":
                case "september":
                    month = "9";
                    break;
                case "oct.":
                case "october":
                    month = "10";
                    break;
                case "nov.":
                case "november":
                    month = "11";
                    break;
                case "dec.":
                case "december":
                    month = "12";
                    break;
            }
            date = month + "/" + dayMonthYear[0] + "/" + dayMonthYear[2];
            if (!dateOccurrences.containsKey(date)) {
                dateOccurrences.put(date, 1);
            } else {
                dateOccurrences.put(date, dateOccurrences.get(date) + 1);
            }
        }
        
        //Numeric Date Pattern (Slashes and Dashes)
        match = Pattern.compile(numericDate).matcher(document);
        while (match.find()) {
            String date = document.substring(match.start(), match.end());
            if (date.contains("/")) {
                if (!dateOccurrences.containsKey(date)) {
                    dateOccurrences.put(date, 1);
                } else {
                    dateOccurrences.put(date, dateOccurrences.get(date) + 1);
                }
            } else if (date.contains("-")) {
                date = date.replaceAll("-", "/");
                if (!dateOccurrences.containsKey(date)) {
                    dateOccurrences.put(date, 1);
                } else {
                    dateOccurrences.put(date, dateOccurrences.get(date) + 1);
                }
            }
        }

        Map<String, Integer> sortedDates = sortByValue(dateOccurrences);
        System.out.println("Top 3 Results: ");
        int i = 0;
        for (String dates : sortedDates.keySet()) {
            if (!(i > 2)) {
                System.out.println(dates);
            }
            i++;
        }
    }

    /*
     * Function Name: sortByValue()
     * Arguments: Map<K, V>
     * Returns: Map<K, V>
     * Description: This method takes a hashmap and sorts it by value in
     * descending order.
     */
    
    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                return (e2.getValue()).compareTo(e1.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

}
