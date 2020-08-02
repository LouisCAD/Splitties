./gradlew "$*" --scan -PbuildScan.termsOfServiceAgree=yes ||
./gradlew "$*" --scan -PbuildScan.termsOfServiceAgree=yes || # Retry if failed
./gradlew "$*" --scan -PbuildScan.termsOfServiceAgree=yes # Retry again
exit $? # Exit with the code of the last command run
