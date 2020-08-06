plugins {
    java
    `maven-publish`
}

group = "org.example"
version = "1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup.okhttp3:logging-interceptor:4.7.2")
    implementation(project(":a"))
}

configurations.all {
    resolutionStrategy.dependencySubstitution {
        substitute(module("com.squareup.okhttp3:logging-interceptor:4.7.2"))
                .with(module("com.squareup.okhttp3:logging-interceptor:4.8.0"))
        substitute(project(":a"))
                .with(project(":b"))
    }
}

publishing {
    publications {
        register("jar", MavenPublication::class.java) {
            from(components["java"])

            versionMapping {
                allVariants {
                    fromResolutionResult()
                }
            }
        }
    }
}
