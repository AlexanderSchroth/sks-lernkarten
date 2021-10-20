package sks.lernkarten;

public enum Category {

    Navigation("Navigation", "Lernkarten-Navigation.pdf",
        "https://www.elwis.de/DE/Sportschifffahrt/Sportbootfuehrerscheine/Fragenkatalog-SKS/Navigation/Navigation-node.html"), //
    Schifffahrtsrecht("Schifffahrtsrecht", "Lernkarten-Schifffahrtsrecht.pdf",
        "https://www.elwis.de/DE/Sportschifffahrt/Sportbootfuehrerscheine/Fragenkatalog-SKS/Schifffahrtsrecht/Schifffahrtsrecht-node.html"), //
    Wetterkunde("Wetterkunde", "Lernkarten-Wetterkunde.pdf",
        "https://www.elwis.de/DE/Sportschifffahrt/Sportbootfuehrerscheine/Fragenkatalog-SKS/Wetterkunde/Wetterkunde-node.html"), //
    Seemannschaft_I("Seemannschaft I", "Lernkarten-Seemannschaft-I.pdf",
        "https://www.elwis.de/DE/Sportschifffahrt/Sportbootfuehrerscheine/Fragenkatalog-SKS/Seemannschaft-I/Seemannschaft-I-node.html");

    private String url;
    private String topic;
    private String resultFileName;

    Category(String topic, String resultFileName, String url) {
        this.topic = topic;
        this.resultFileName = resultFileName;
        this.url = url;
    }

    public String getResultFileName() {
        return resultFileName;
    }

    public String getTopic() {
        return topic;
    }

    public String getUrl() {
        return url;
    }
}
