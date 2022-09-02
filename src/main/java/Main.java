import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        File inputTxt = new File("C:\\Users\\Admin\\Desktop\\test\\input.txt");
        List<String> firstInputList = new ArrayList<>();
        List<String> secondInputList = new ArrayList<>();
        readFromFile(inputTxt, firstInputList, secondInputList);

        List<List<String>> sortedList = sortByLength( firstInputList,  secondInputList);
        List<String> firstList = sortedList.get(0);
        List<String> secondList = sortedList.get(1);

        List<List<String>> substringsArraysList = splitString(firstList);

        Set<String> resultList = new LinkedHashSet<>();
        findSubstring(substringsArraysList, firstList, secondList, resultList);

        File outputTxt = new File("C:\\Users\\Admin\\Desktop\\test\\output.txt");
        writeToFile(outputTxt, resultList);


        for (String string : resultList) {
            System.out.println(string);
        }
    }


    public static void readFromFile(File inputTxt, List<String> firstInputList, List<String> secondInputList ) {
        try (BufferedReader inputReader = new BufferedReader(new FileReader(inputTxt))) {

            int firstNumberOfLines = Integer.parseInt(inputReader.readLine());
            while (firstNumberOfLines-- > 0) {
                firstInputList.add(inputReader.readLine());
            }

            int secondNumberOfLines = Integer.parseInt(inputReader.readLine());
            while (secondNumberOfLines-- > 0) {
                secondInputList.add(inputReader.readLine());
            }

        } catch (FileNotFoundException e) {
            System.err.println("Файл с указанным именем пути не существует! " + inputTxt.getAbsolutePath());
            System.exit(-1);
        } catch (IOException exception) {
            System.err.println("Непредвиденная ошибка при чтении файла!" + exception.getMessage());
            System.exit(-1);
        }
    }

    public static List<List<String>> sortByLength(List<String> firstInputList, List<String> secondInputList){

        List<List<String>> sortedList = new ArrayList<>();
        if (firstInputList.size() > secondInputList.size()) {
            sortedList.add(new ArrayList<>(firstInputList));
            sortedList.add(new ArrayList<>(secondInputList));
        } else {
            sortedList.add(new ArrayList<>(secondInputList));
            sortedList.add(new ArrayList<>(firstInputList));
        }
        return sortedList;
    }

    public static List<List<String>> splitString(List<String> firstList){
        List<List<String>> substringsArraysList = new ArrayList<>();
        for (String inputString : firstList) {
            String[] substringArray = inputString.split(" ");
            substringsArraysList.add(Arrays.stream(substringArray).toList());
        }
        return substringsArraysList;
    }

    public static void findSubstring(List<List<String>> substringsArraysList, List<String> firstList,
                                     List<String> secondList, Set<String> resultList){
        Set<String> invalidValuesList = new LinkedHashSet<>();

        for (int i = 0; i < substringsArraysList.size(); i++) {
            List<String> substringArray = substringsArraysList.get(i);

            for (String substring : substringArray) {
                for (String secondListString : secondList) {
                    boolean isSubstring = secondListString.contains(substring);
                    String firstListString = firstList.get(i);
                    if (isSubstring) {
                        resultList.add(firstListString + ":" + secondListString);
                    } else {
                        invalidValuesList.add(firstListString);
                    }
                }
            }
        }

        List<String> tempList = new ArrayList<>(invalidValuesList);
        for (String tempString : invalidValuesList) {
            for (String tempString2 : resultList) {
                if (tempString2.contains(tempString)) {
                    tempList.remove(tempString);
                }
            }
        }
        for (String tempString : tempList) {
            resultList.add(tempString + ":?");
        }
    }

    public static void writeToFile(File outputTxt, Set<String> resultList){
        try (BufferedWriter outputWriter = new BufferedWriter(new FileWriter(outputTxt))) {
            for (String writeString : resultList) {
                outputWriter.write(writeString + "\n");
            }
        } catch (IOException exception) {
            System.err.println("Непредвиденная ошибка при чтении файла!" + exception.getMessage());
            System.exit(-1);
        }
    }
}
