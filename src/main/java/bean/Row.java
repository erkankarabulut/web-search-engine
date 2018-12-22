package bean;

import java.util.ArrayList;
import java.util.Arrays;

public class Row {

    private String[] words;
    private Integer rotateCount;
    private Integer documentPointer;

    public String[] getWords() {
        return words;
    }

    public void setWords(String[] words) {
        this.words = words;
    }

    public Integer getRotateCount() {
        return rotateCount;
    }

    public void setRotateCount(Integer rotateCount) {
        this.rotateCount = rotateCount;
    }

    public Integer getDocumentPointer() {
        return documentPointer;
    }

    public void setDocumentPointer(Integer documentPointer) {
        this.documentPointer = documentPointer;
    }

    @Override
    public String toString() {
        return "Row{" +
                "words=" + Arrays.toString(words) +
                ", rotateCount=" + rotateCount +
                ", documentPointer=" + documentPointer +
                '}';
    }
}
