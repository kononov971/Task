package ru.aspect.task;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


public class Parser{
    private static Map<String, Set<String>> contents = new LinkedHashMap<>();

    public static synchronized void addContent(Map<String, Set<String>> newContents) {
        Set<String> content;
        for(Map.Entry<String, Set<String>> entry : newContents.entrySet()) {
            content = contents.get(entry.getKey());
            if (content != null) {
                for(String element : entry.getValue()) {
                    content.add(element);
                }
                contents.put(entry.getKey(), content);
            } else {
                contents.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for(String fileName : args) {
            Thread thread = new Thread(new Reader(fileName));
            thread.start();
            thread.join();
        }

        for(Map.Entry<String, Set<String>> entry : contents.entrySet()) {
            System.out.println(entry.getKey() + ":");
            for(String element : entry.getValue()) {
                System.out.print(element + ";");
            }
            System.out.println();
        }
    }
}
