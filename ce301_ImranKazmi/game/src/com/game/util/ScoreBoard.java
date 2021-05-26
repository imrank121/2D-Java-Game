/*package com.game.util;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.HashMap;

public class ScoreBoard {
//TODO/// MAKE UI TABLE ON IMG & REPLACE STRING KAZ IN NAME WITH NAME VARIABLE FROM SCANNER INPUT
    private final static String storeScore = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/scores.txt";

    public void writeFile(String score) { //Function to write the file to text
//        if (Integer.parseInt(score) <= 0) {
//            return;
//        }

        try {
            FileWriter fileWriter = new FileWriter(storeScore, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter output = new PrintWriter(bufferedWriter);
            output.println(score);
            output.close();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String,Integer> read() {
        HashMap<String,Integer> scores = new HashMap<>();
        File txt = new File(storeScore);

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(txt));
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                var line = s.split(",");

                try {
                    scores.put(line[0], Integer.valueOf(line[1]));
                } catch (ArrayIndexOutOfBoundsException e){

                }


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //scores.sort(Collections.reverseOrder());

        return scores;
    }

    public void saveScore(String name,int score){
        writeFile((name+","+score));
    }

    /*public ArrayList<Integer> getScores() {
        ArrayList<Integer> scores = read();

        if (scores.size() > 10) {
            scores.subList(10, scores.size()).clear();
        }

        return scores;
    }//
}
*/
