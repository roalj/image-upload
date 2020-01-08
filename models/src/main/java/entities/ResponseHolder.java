package entities;

public class ResponseHolder {
    private boolean isSavedMongo;
    private String resultSavingCatalog;

    public ResponseHolder(boolean isSavedMongo, String resultSavingCatalog) {
        this.isSavedMongo = isSavedMongo;
        this.resultSavingCatalog = resultSavingCatalog;
    }

    public boolean isSavedMongo() {
        return isSavedMongo;
    }

    public void setSavedMongo(boolean savedMongo) {
        isSavedMongo = savedMongo;
    }

    public String getResultSavingCatalog() {
        return resultSavingCatalog;
    }

    public void setResultSavingCatalog(String resultSavingCatalog) {
        this.resultSavingCatalog = resultSavingCatalog;
    }
}
