# scala-tutorial
scala-tutorial


```
cd tutorial/
sbt
~run
```

To switch from main classe to another change the following line present into `build.sbt`  :

```scala
mainClass in (Compile, run) := Some("<MAIN_CLASS>")
```

replace `<MAIN_CLASS>` with one of the `object` name contained into following `scala` file :

**NOTE** only file name exmaple `Some("Classes_")`

```
src/main/scala/
├── Basics.scala
└── UnifiedTypes.scala
```
