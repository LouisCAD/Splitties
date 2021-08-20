# Bit Flags

*`hasFlag`, `withFlag` and `minusFlag` extensions on `Long`, `Int`, `Short`, `Byte`,
and their unsigned counterparts.*

Supported platforms: **Linux** (x64), **MingW (x64)**, **macOS** (x64), **iOS** (arm32, arm64 & x64), **JS**, **JVM** (including Android).

These extensions make dealing with bit flags easier.

These small inline extensions can be proven handy since bit flags are found in
multiple places over the Android API, in low level stuff when you
have to deal with raw bytes, and maybe in your own codebase too.

## Setup

If you want to use this dependency without using one of the [fun packs](../../README.md#download),
you can use `Splitties.bitflags`, provided you have [refreshVersions](https://github.com/jmfayard/refreshVersions) added to the project.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-bitflags`.
