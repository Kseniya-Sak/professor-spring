package spring.repository;

public enum AppPropertiesForTest {
    URL("spring.datasource.url"),
    DATASOURCE_USERNAME("spring.datasource.username"),
    DATASOURCE_PASSWORD("spring.datasource.password"),
    DATABASE_NAME("tests-db"),
    USERNAME("postgres"),
    PASSWORD("postgres"),
    INIT_SCRIPT_NAME("sql/db.sql"),
    DOCKER_IMAGE_NAME("postgres:15-alpine");

    private String name;

    private AppPropertiesForTest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

