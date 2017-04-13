# Splitties
### What is this
This project is split in small modules, distributed as independent Android libraries, so you can include in your project only what you need.

### Why I made this
I personally often copy/pasted the code which makes these libraries into the projects I worked on, so I made this library to make it a one line operation, and make potential updates to this code easier. Now, everyone can use this work.

***Below is a list of the libraries included in this project***

## Typesafe RecyclerView
### What it does
It makes using RecyclerView simpler, with less boilerplate for basic or less basic usages.
### Usage
Download or clone the project and open it in Android Studio to see the sample. Alternatively, take a look at these classes:
[DemoAdapter](https://github.com/LouisCAD/Splitties/blob/master/sample/src/main/java/xyz/louiscad/splittiessample/ui/adapter/DemoAdapter.java), [DemoListItem](https://github.com/LouisCAD/Splitties/blob/master/sample/src/main/java/xyz/louiscad/splittiessample/ui/widget/DemoListItem.java), [DemoItem](https://github.com/LouisCAD/Splitties/blob/master/sample/src/main/java/xyz/louiscad/splittiessample/ui/model/DemoItem.java) and [ImmutableBasicItem](https://github.com/LouisCAD/Splitties/blob/master/sample/src/main/java/xyz/louiscad/splittiessample/ui/model/ImmutableBasicItem.java).
### Explanation
This modules consists of two `ViewHolder` subclasses that make it typesafe, and easier to use for the common use case which is to bind a ViewHolder to a POJO. See the sample to understand how it works.
## Selectable ViewGroups
### What it does
It adds a `foreground` attribute to common ViewGroups (`ConstraintLayout`, `RelativeLayout` and `LinearLayout`) which defaults to `@android:attr/selectableItemBackground`, allowing visual feedback when the user selects the View. This can be useful in a `RecyclerView`.
### Usage
Just use `SelectableLinearLayout`, `SelectableRelativeLayout` or `SelectableConstraintLayout` in your layouts, or extend them, and if you want to customize the foreground, follow this example:
```xml
<SelectableRelativeLayout
    ...
    app:foreground="@drawable/your_state_drawable>
    ...
</SelectableRelativeLayout>
```
Note that the `foreground` atrribute does not clash with android's one (for API 23+), and applies to subclasses.
### Explanation
`FrameLayout` is historically the only `ViewGroup` which supports a foreground drawable attribute. The foreground attribute is often used with value `@android:attr/selectableItemBackground` to provide visual feedback when the user touches a selectable element such as a list item in a RecyclerView, which translates as a Ripple effect starting from Android Lollipop. As a consequence, people usually wrap their `RelativeLayout`, `LinearLayout` or other `ViewGroup` with a `FrameLayout` just to provide this foreground attribute. This is unefficient, espacially if this is replicated as it is in a RecyclerView. The foreground attribute is part of the `View` class since Android Marshmallow now, but few apps can have their minSdk set to 23, so I made this library which provides a foreground attribute that defaults to `@android:attr/selectableItemBackground` for popular `ViewGroups`.

## Download
If you use gradle and have `jcenter()` (default for new Android Studio projects) in your repositories, just add the compile line of the modules you need.
```groovy
compile 'xyz.louiscad.splitties:selectableviewgroups:1.2'
compile 'xyz.louiscad.splitties:typesaferecyclerview:1.2'
```
For maven and alternative configurations, check the [Bintray page](https://bintray.com/louiscad/splitties).

## New versions notifications
To get notified for new versions, be sure to click on "Watch" on the [selectablewiewgroups Bintray page](https://bintray.com/louiscad/splitties/selectableviewgroups). Note that all modules (typesaferecyclerview and possible future ones) will always share the same version, so watching one is enough.

## Improve this library
Included classes are not exhaustive, so if you find some code that follows this library's philosophy and you think it should be here, feel free to open an issue.

And of course, the same goes if you find a bug.

## License
This library is published under Apache License version 2.0 which you can see [here](https://github.com/LouisCAD/Reusables/blob/master/LICENSE).
