val scala3Version = "3.1.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "decline-app-test",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    // for native image
    Compile / mainClass := Some("com.github.wahtique.HelloWorld"),
    nativeImageOptions += s"-H:ReflectionConfigurationFiles=${target.value / "native-image-configs" / "reflect-config.json"}",
    nativeImageOptions += s"-H:ConfigurationFileDirectories=${target.value / "native-image-configs" }",
    nativeImageOptions += "-H:+JNI"
  )
  .enablePlugins(NativeImagePlugin)

libraryDependencies ++= Seq(
  "com.monovore" %% "decline" % "2.2.0",
  "com.novocode" % "junit-interface" % "0.11" % "test"
)

