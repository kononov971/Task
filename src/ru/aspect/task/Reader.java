package ru.aspect.task;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Reader implements Runnable{
    private String fileName;
    public Reader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {

        Map<String, Set<String>> contents = new LinkedHashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            writeHeaders(contents,bufferedReader);
            writeContent(contents, bufferedReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parser.addContent(contents);
    }

    public void writeHeaders(Map<String, Set<String>> contents, BufferedReader bufferedReader) throws IOException {
        String currentLine;
        currentLine = bufferedReader.readLine();
        if(!currentLine.trim().isEmpty()) {
            String[] headers = currentLine.split(";");
            for(String header : headers) {
                Set<String> content = new LinkedHashSet<>();
                contents.put(header, content);
            }
        }
    }

    public void writeContent(Map<String, Set<String>> contents, BufferedReader bufferedReader) throws IOException {
        Iterator<Map.Entry<String, Set<String>>> iterator;
        Set<String> column;
        String[] cells;
        String currentLine;

        while ((currentLine = bufferedReader.readLine()) != null) {
            cells = currentLine.split(";");
            iterator = contents.entrySet().iterator();
            column = iterator.next().getValue();
            for(String cell : cells) {
                column.add(cell);
                if(iterator.hasNext()) {
                    column = iterator.next().getValue();
                } else {
                    break;
                }
            }
        }
    }
}
