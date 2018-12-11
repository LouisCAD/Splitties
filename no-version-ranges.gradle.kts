configurations.forEach { it ->
    it.resolutionStrategy {
        eachDependency {
            val version = requested.version ?: return@eachDependency
            check('+' !in version) {
                "Version ranges are forbidden because they would make builds non reproducible."
            }
            check("SNAPSHOT" !in version) {
                "Snapshot versions are forbidden because they would make builds non reproducible."
            }
        }
    }
}
