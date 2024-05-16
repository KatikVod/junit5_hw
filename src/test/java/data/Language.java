package data;

public enum Language {
    Deutsch("Beliebte Flüge ab Berlin"),
    Polski("Popularne loty z Warszawy"),
    Español("Rutas populares desde Madrid");

    public final String title;
    Language(String title) {
        this.title = title;
    }
}