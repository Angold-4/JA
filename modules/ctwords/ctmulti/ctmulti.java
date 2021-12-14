//===----------------------------------------------------------------------===//
//
//                         JA
//
// ctmulti.java
//
// Identification: ctmulti/ctmulti.java
//
// Usage: Using java Thread explicitly to create multiple threads to read multiple files
//
// Last Modified : 2021.12.14 Jiawei Wang
//
// Copyright (c) 2021 Angold-4
//
//===----------------------------------------------------------------------===//


import java.io.BufferedReader;
import java.io.FileReader;

public class ctmulti extends Thread {
    // Just implement Threads

    private static int notFinished; // static since they are not the threads
    // which share the same runnable object
    private static int totalWord;
    private String fileName;

    synchronized static void finished(int total) {
        notFinished--;
        totalWord += total;

        if (notFinished == 0) {
            System.out.println("Total number of words: " + totalWord);
        }
    }

    public ctmulti(String f) {
        fileName = f;
    }

    @Override
    public void run() {
        int total = 0;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            while (true) {
                String st = reader.readLine();
                if (st == null) {
                    break;
                }
                String word[] = st.trim().split(" +"); //note that this is regular expression for one or more spaces.
                total += word.length;
            }
            finished(total);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    public static void main(String[] st) {
        ctmulti.notFinished = st.length;
        for (String fileName : st) {
            ctmulti readFile2 = new ctmulti(fileName);
            readFile2.start(); // start instead of run
        }
    }
}

