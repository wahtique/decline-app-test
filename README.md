# Decline + GraalVM

This is a test project I created to learn

- [Decline](https://ben.kirw.in/decline/) command line lib
- GraalVM native image generation from a Scala project with [sbt-native-image](https://github.com/scalameta/sbt-native-image)
- Github Actions workflows

## Usage

```
Usage: decline-app-test [--target <string>] [--quiet]

Says hello !

Options and flags:
    --help
        Display this help text.
    --target <string>
        Person to greet.
    --quiet
        Whether to be quiet.
```

## Run Locally

Clone the project

```bash
  git clone git@github.com:wahtique/decline-app-test.git
```

Go to the project directory

```bash
  cd decline-app-test
```

To build the native image, running `native-image-agent` first is needed as decline uses some reflection

```bash
  sbt nativeImageRunAgent
```

Then build the native image

```bash
  sbt nativeImage
```

or directly run it

```bash
  sbt nativeImageRun
```

Better yet, start the sbt interpreter with `sbt` and then :

```shell
  nativeImageRunAgent
  # then either build
  nativeImage
  # or run
  nativeImageRun
```
