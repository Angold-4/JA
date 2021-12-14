//===----------------------------------------------------------------------===//
//
//                         JA
//
// ctsingle.java
//
// Identification: ctsingle/ctsingle.java
//
// Usage: Using Java Runnable interface implements multi-threading program
// that counts the # of words in a single file (default # = 3)
//
// Last Modified : 2021.12.14 Jiawei Wang
//
// Copyright (c) 2021 Angold-4
//
//===----------------------------------------------------------------------===//


import java.io.BufferedReader;
import java.io.FileReader;

public class ctsingle implements Runnable {
    // java.lang.Runnable is an interface that is to be implemented by a class 
    // whose instances are intended to be executed by a thread.

    private int noFinishedThread = 0; // no need to set the static
    // because they are all belong to same object just executed in multiple threads
    private String filename;
    private BufferedReader reader;
    // InputStream vs FileInputStream vs BufferedReader vs FileReader
    //
    // InputStream: Just like FileInputStream, recieving data stream from stdin
    //
    // FileInputStream: This object reads a byte(8-bits) at a time and writes it to the given file.
    // A practical useful application of it would be to work with raw binary/data files, such as images or audio files
    // it is very inconvenient and slower for text files(also just support ASCII)
    // -> fetching a byte at a time, then encoding and fetching another is tedious
    //
    // FileReader: designed to deal with text files
    // FileStream with inclusive charset support (It reads2 bytes (or 4 bytes, depends on the charset) at a time
    // But again it's still very slow (Imaging reading 2 bytes at a time and looping through it!).
    //
    // FileBuffer: 
    // To create a buffer of data, before processing.
    // Store in RAM
    // 8-bit(ASCII) -> 16-bit(Unicode)
    //		    
    private int result = 0;

    public ctsingle(String st) {

        filename = st;
        try {
            reader = new BufferedReader(new FileReader(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized String readLine() {
	// any time only one readline
        try {
            String st = reader.readLine(); // key: can guarentee the whole file to be finished
            return st;
        } catch (Exception e) {
            try {
                reader.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            return null;
        }
    }

    public void run() {
	// runnable interface defined
        int threadResult = 0;
        while (true) {
            String st = readLine(); // only readLine is synchonized
            if (st == null) {
                break;
            }
            String word[] = st.trim().split(" +"); //note that this is regular expression for one or more spaces.
            threadResult += word.length;
        }
	// when all threads all jump out from while loop
	// and call putResult, means we finished
        putResult(threadResult);
    }

    private synchronized void putResult(int threadResult) {
        noFinishedThread++;
        result += threadResult;
        if (noFinishedThread == 3) {
            System.out.println("The total number of words is " + result + "\n");
        }
    }

    public static void main(String st[]) {
	long begin = System.nanoTime();

	ctsingle file = new ctsingle(st[0]);

	Thread thr1 = new Thread(file);
	Thread thr2 = new Thread(file);
	Thread thr3 = new Thread(file);

	thr1.run();
	thr2.run();
	thr3.run();

	long end = System.nanoTime();
	System.out.println("Time taken: "+ (end - begin) +" nanoseconds"); 
    }
}
 
