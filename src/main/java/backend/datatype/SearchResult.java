package backend.datatype;

/**
 * Created by andy on 14.07.17.
 */
public class SearchResult
{
    private int         wordId;
    private String      targetWord;
    private String      targetSentence;
    private String[]    sentenceParts;
    private String      targetTag;
    private String      precTag;
    private String      folTag;

    public SearchResult(final int wordId, final String targetWord, final String targetSentence, final String[] sentenceParts)
    {
        this.wordId = wordId;
        this.targetWord = targetWord;
        this.sentenceParts = sentenceParts;
        this.targetSentence = targetSentence.replaceAll(targetWord, "%w");
    }


    public String getTargetTag() {
        return targetTag;
    }

    public void setTargetTag(String targetTag) {
        this.targetTag = targetTag;
    }

    public String getPrecTag() {
        return precTag;
    }

    public void setPrecTag(String precTag) {
        this.precTag = precTag;
    }

    public String getFolTag() {
        return folTag;
    }

    public void setFolTag(String folTag) {
        this.folTag = folTag;
    }

    public String getTargetWord() {
        return targetWord;
    }

    public void setTargetWord(String targetWord) {
        this.targetWord = targetWord;
    }

    public String getTargetSentence() {
        return targetSentence;
    }

    public void setTargetSentence(String targetSentence) {
        this.targetSentence = targetSentence;
    }

    public String getTargetInSentence() {
        return this.getTargetSentence().replaceAll("%w", this.targetWord);
    }

    public String getTargetInSentenceShort(final int neighbours) {

        int maxPreNeighborId = this.wordId - neighbours;
        int maxFolNeighborId = this.wordId + neighbours;

        while (maxPreNeighborId < 0)
        {
            ++maxPreNeighborId;
        }

        while (maxFolNeighborId > (this.sentenceParts.length - 1))
        {
            --maxFolNeighborId;
        }

        int preSentenceId = this.targetSentence.indexOf(this.sentenceParts[maxPreNeighborId]);
        int folSentenceId = this.targetSentence.indexOf(this.sentenceParts[maxFolNeighborId]);
        String sentence = this.targetSentence.substring(preSentenceId, folSentenceId).replaceAll("%w", "%"+this.targetWord+"%");

        return (maxPreNeighborId > 0 ? "[..] " : "") + sentence + (maxFolNeighborId < this.sentenceParts.length - 1 ? " [..]" : "");
    }
}
