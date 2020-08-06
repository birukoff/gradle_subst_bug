Shows how "maven-publish" ignores substituted project dependencies 
when generating POM and Metadata files.

Project ":z" declares dependency on project ":a" but substitutes it with project ":b":
```kotlin
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
```

Clone, and invoke `./gradlew generatePomFileForJarPublication generateMetadataFileForJarPublication`.

Look at the output in `z/build/publications/jar`. It will have:
```xml
  <dependencies>
    <dependency>
      <groupId>com.squareup.okhttp3</groupId>
      <artifactId>logging-interceptor</artifactId>
      <version>4.8.0</version> <!-- CORRECT -->
      <scope>runtime</scope>
    </dependency>
    <dependency>
      <groupId>org.example</groupId>
      <artifactId>a</artifactId> <!-- WRONG! Should be "b" -->
      <version>1</version>
      <scope>runtime</scope>
    </dependency>
  </dependencies>
``` 
As you can see, okhttp is referenced correctly but project "a" is not. 
