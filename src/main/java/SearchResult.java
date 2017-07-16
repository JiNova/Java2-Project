/**
 * Created by andy on 14.07.17.
 */
public class SearchResult
{
    private String targetWord;
    private String targetSentence;
    private String targetTag;
    private String precTag;
    private String folTag;

    public SearchResult(final String targetWord, final String targetSentence)
    {
        this.targetWord = targetWord;
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
}
