package spring.config;

public enum AppProperties {
    APP_CHARACTER_ENCODING("UTF-8"),
    DRIVER_CLASS_NAME("spring.datasource.driver-class-name"),
    URL("spring.datasource.url"),
    USERNAME("spring.datasource.username"),
    PASSWORD("spring.datasource.password"),
    JPA_DATABASE_PLATFORM("spring.jpa.database-platform"),
    JPA_SHOW_SQL("spring.jpa.show-sql"),
    PACKAGES_TO_SCAN("spring.entity");

    private String name;

    private AppProperties(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

