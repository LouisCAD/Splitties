./gradlew "$1 --scan -PbuildScan.termsOfServiceAgree=yes" ||
./gradlew "$1 --scan -PbuildScan.termsOfServiceAgree=yes" || # Retry if failed
./gradlew "$1 --scan -PbuildScan.termsOfServiceAgree=yes" # Retry again
exit $? # Exit with the code of the last command run
