taskWithArgs="$*"
./gradlew $taskWithArgs --scan -PbuildScan.termsOfServiceAgree=yes ||
./gradlew $taskWithArgs --scan -PbuildScan.termsOfServiceAgree=yes || # Retry if failed
./gradlew $taskWithArgs --scan -PbuildScan.termsOfServiceAgree=yes # Retry again
exit $? # Exit with the code of the last command run
