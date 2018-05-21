# View DSL AppCompat styles

*AppCompat styles for [View DSL](../viewdsl)*

## About xml styles

### The problem

AppCompat provides a bunch of xml styles to use in the `style` attribute of
the compatible widgets, but this only applies to xml layouts.

Using styles defined in xml programmatically is possible only on compatible
Views on SDK 21+ only (using the View constructor that has 4 parameters), but
the AppCompat widget are not among the compatible ones since they miss this 4
View constructor, and minSdk >= 21 apps is not a 2018 thing anyway.

### The solutions

*Note that this split uses a mix of the solutions below.*

#### Rewrite xml styles to Kotlin functions

It is possible to write a function that does programmatically what the xml
style would have done, as long as the attributes have a code equivalent.

#### Create functions that inflate styled xml layouts

It is possible to write a function that takes a `Context` parameter, inflates
an xml file that uses the desired `style` and returns the created `View`.

#### Use theme attributes

There exists some theme attributes that can be used in the 3 parameters `View`
constructors like `progressBarStyle` and `buttonStyle`.

## Content

**IMPORTANT NOTE:** *This module has **not** been checked for completeness.
That means it currently doesn't provide all the styles defined in AppCompat.
If you need a style that is missing here, feel free to open an issue or
directly submit a pull request adding the style you needed.*

This split is an extension to [View DSL AppCompat](../viewdsl-appcompat).
It provides functions to use with View DSL:

Styled `Button`s:
* `flatButton`
* `coloredFlatButton`
* `coloredButton`

Styled `ImageButton`s:
* `imgActionButton`

Styled `ProgressBar`s:
* `progressBar`
* `largeProgressBar`
* `horizontalProgressBar`

## Download

```groovy
implementation "com.louiscad.splitties:splitties-viewdsl-appcompat-styles:$splitties_version"
```
