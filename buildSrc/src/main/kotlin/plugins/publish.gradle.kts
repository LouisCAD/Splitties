plugins {
    `maven-publish`
    id("org.jetbrains.dokka")
}

configure<PublishingExtension> {
    setupAllPublications(project)
}
