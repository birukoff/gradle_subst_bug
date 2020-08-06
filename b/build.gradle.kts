plugins {
    java
    `maven-publish`
}

group = "org.example"
version = "1"

publishing {
    publications {
        register("jar", MavenPublication::class.java) {
            from(components["java"])
        }
    }
}
