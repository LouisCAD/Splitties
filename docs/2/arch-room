# Arch Room

*Room helpers to instantiate your DB and perform transactions in Kotlin.*

## Content

### Room Database instantiation

The `roomDb(…)` function has a reified type parameter used to call
`Room.databaseBuilder` with the proper arguments. `name` is the only
required parameter.

You can specify a `Context` as first parameter if you don't want to use the
application context, if you want to use a different context (e.g. to use
`directBootCtx` in a direct boot aware app component).

The last parameter is a lambda with the db builder as receiver where you
can things like migrations.

### Room Database transactions

`transaction { … }` is the inline version of `runInTransaction`, with your
database as receiver.

`inTransaction { … }` does the same as `transaction { … }` but returns
the value of the last expression of the lambda.

Inlining brings a slight performance improvement at runtime and reduces the
number of classes, making the apk smaller.

## Download

```groovy
implementation "com.louiscad.splitties:splitties-arch-room:$splitties_version"
```
