package bean;

// POJO class for the Document object
public class Document {

    private String  document;
    private String  url;
    private Integer documentId;
    private Integer rank;

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Document{" +
                "document='" + document + '\'' +
                ", url='" + url + '\'' +
                ", documentId=" + documentId +
                '}';
    }
}
