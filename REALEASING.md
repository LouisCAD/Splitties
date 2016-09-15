#Releasing this library:
1. Tested the library, and make sure there's no regression.
- Run this command to build the library:
```shell
./gradlew install
```
- Run this command to publish the library after the previous completed with success.
```shell
./gradlew bintrayUpload
```
- Sign in on Bintray and publish the packages.