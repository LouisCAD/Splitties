# Typesafe RecyclerView

*Typesafe `ViewHolder` and `ItemViewHolder` for easy basic usage of
`RecyclerView`.*

This modules consists of two `ViewHolder` subclasses that make it typesafe,
and easier to use for the common use case which is to bind a `ViewHolder` to a
POJO (plain-old Java `Object`) or a POKA (plain-old Kotlin `Any`).
See the sample to understand how it works.

### Usage

When using `ViewHolder`, use the one from Splitties to get the typesafe one.

See it in action in the sample: [DemoAdapter](
../../samples/android-app/src/androidMain/kotlin/com/example/splitties/demo/DemoAdapter.kt
).

## Download

```groovy
implementation("com.louiscad.splitties:splitties-typesaferecyclerview:$splitties_version")
```
