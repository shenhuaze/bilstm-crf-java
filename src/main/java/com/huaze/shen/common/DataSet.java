package com.huaze.shen.common;

import org.jblas.DoubleMatrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Huaze Shen
 * @date 2019-05-09
 */
public class DataSet {
    private String dataFile;
    private Map<String, Integer> charIndex = new HashMap<>();
    private Map<Integer, String> indexChar = new HashMap<>();
    private Map<String, DoubleMatrix> charVector = new HashMap<>();
    private Map<String, Integer> tagIndex = new HashMap<>();
    private Map<Integer, String> indexTag = new HashMap<>();
    private Map<String, DoubleMatrix> tagVector = new HashMap<>();
    private List<LabeledSequence> labeledSequences = new ArrayList<>();

    public DataSet(String dataFile) {
        this.dataFile = dataFile;
        init();
    }

    public void init() {
        charIndex = new HashMap<>();
        indexChar = new HashMap<>();
        charVector = new HashMap<>();
        tagIndex = new HashMap<>();
        indexTag = new HashMap<>();
        tagVector = new HashMap<>();
        labeledSequences = new ArrayList<>();
        loadData();
        buildDistributedRepresentations();
    }

    private void loadData() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(dataFile)));
            String line;
            List<String> chars = new ArrayList<>();
            List<String> tags = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                if (line.length() == 0) {
                    LabeledSequence labeledSequence = new LabeledSequence(chars, tags);
                    labeledSequences.add(labeledSequence);
                    for (String ch : labeledSequence.getChars()) {
                        if (!charIndex.containsKey(ch)) {
                            charIndex.put(ch, charIndex.size());
                            indexChar.put(charIndex.get(ch), ch);
                        }
                    }
                    for (String tag : labeledSequence.getTags()) {
                        if (!tagIndex.containsKey(tag)) {
                            tagIndex.put(tag, tagIndex.size());
                            indexTag.put(tagIndex.get(tag), tag);
                        }
                    }
                    chars = new ArrayList<>();
                    tags = new ArrayList<>();
                    continue;
                }
                String[] lineSplit = line.split(" ");
                String ch = lineSplit[0];
                String tag = lineSplit[1];
                chars.add(ch);
                tags.add(tag);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildDistributedRepresentations() {
        for (String ch : charIndex.keySet()) {
            DoubleMatrix xt = DoubleMatrix.zeros(1, charIndex.size());
            xt.put(charIndex.get(ch), 1);
            charVector.put(ch, xt);
        }
        for (String tag : tagIndex.keySet()) {
            DoubleMatrix yt = DoubleMatrix.zeros(1, tagIndex.size());
            yt.put(tagIndex.get(tag), 1);
            tagVector.put(tag, yt);
        }
    }

    public Map<String, Integer> getCharIndex() {
        return charIndex;
    }

    public Map<String, DoubleMatrix> getCharVector() {
        return charVector;
    }

    public List<LabeledSequence> getLabeledSequences() {
        return labeledSequences;
    }

    public Map<Integer, String> getIndexChar() {
        return indexChar;
    }

    public Map<String, Integer> getTagIndex() {
        return tagIndex;
    }

    public Map<Integer, String> getIndexTag() {
        return indexTag;
    }

    public Map<String, DoubleMatrix> getTagVector() {
        return tagVector;
    }

    public static void main(String[] args) {
        String dataFile = "src/main/resources/data/seg/pku_seg_char_tag.txt";
        DataSet dataSet = new DataSet(dataFile);
        System.out.println();
    }
}
