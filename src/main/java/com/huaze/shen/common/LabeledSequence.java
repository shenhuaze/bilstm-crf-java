package com.huaze.shen.common;

import java.util.List;

/**
 * @author Huaze Shen
 * @date 2019-05-09
 */
public class LabeledSequence {
    private List<String> chars;
    private List<String> tags;

    public LabeledSequence(List<String> chars, List<String> tags) {
        this.chars = chars;
        this.tags = tags;
    }

    public List<String> getChars() {
        return chars;
    }

    public void setChars(List<String> chars) {
        this.chars = chars;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
