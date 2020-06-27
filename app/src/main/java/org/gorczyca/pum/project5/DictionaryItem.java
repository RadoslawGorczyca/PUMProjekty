package org.gorczyca.pum.project5;

/**
 * Created by: Radosław Gorczyca
 * 27.06.2020 15:13
 */
class DictionaryItem {
    private String englishWord;
    private String polishWord;

    public DictionaryItem(String englishWord, String polishWord) {
        this.englishWord = englishWord;
        this.polishWord = polishWord;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public String getPolishWord() {
        return polishWord;
    }

    public void setPolishWord(String polishWord) {
        this.polishWord = polishWord;
    }
}
