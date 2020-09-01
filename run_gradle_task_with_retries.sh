taskWithArgs="$*"
./gradlew $taskWithArgs --scan -PbuildScan.termsOfServiceAgree=yes --console=plain ||
./gradlew $taskWithArgs --scan -PbuildScan.termsOfServiceAgree=yes --console=plain || # Retry if failed
./gradlew $taskWithArgs --scan -PbuildScan.termsOfServiceAgree=yes --console=plain # Retry again
exit $? # Exit with the code of the last command run
