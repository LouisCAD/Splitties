# Releasing this library:
1. Test the library, and make sure there's no regression.
2. Build the library once with the previous release own dependencies.
3. Update the own dependencies versions and rebuild the library.
4. Run this command to upload the library.
```shell
./gradlew bintrayUpload
```
5. Sign in on Bintray and publish the packages.