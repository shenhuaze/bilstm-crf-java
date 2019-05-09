package com.huaze.shen.preprocess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * @author Huaze Shen
 * @date 2019-05-09
 *
 * 将分词标注数据转换为char-tag文件
 */
public class GenerateCharTagFileForSeg {
    private void convertTextFileToFeatureFile(String textFile, String charTagFile) {
        try {
            BufferedReader textFileReader = new BufferedReader(new FileReader(textFile));
            BufferedWriter charTagFileWriter = new BufferedWriter(new FileWriter(charTagFile));
            String line;
            while ((line = textFileReader.readLine()) != null) {
                line = line.trim();
                if (line.length() == 0) {
                    continue;
                }
                String[] lineSplit = line.split("[ \t]+");
                for (String word : lineSplit) {
                    for (int i = 0; i < word.length(); i++) {
                        String tag = "";
                        char ch = word.charAt(i);
                        if (word.length() == 1) {
                            tag = "S";
                        } else if (i == 0) {
                            tag = "B";
                        } else if (i == word.length() - 1) {
                            tag = "E";
                        } else {
                            tag = "M";
                        }
                        charTagFileWriter.write(ch + " " + tag + "\n");
                    }
                }
                charTagFileWriter.write("\n");
            }
            textFileReader.close();
            charTagFileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String resourcesDir = "src/main/resources/";
        String textFile = resourcesDir + "data/seg/pku_test_gold.utf8";
        String charTagFile = resourcesDir + "data/seg/pku_seg_char_tag.txt";
        new GenerateCharTagFileForSeg().convertTextFileToFeatureFile(textFile, charTagFile);
    }
}
