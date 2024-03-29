package com.github.wahtique

import cats.implicits._
import com.monovore.decline._

object DeclineTestApp extends CommandApp(
  name = "decline-app-test",
  header = "Says hello !",
  main = {
    val userOpt =
      Opts.option[String]("target", help = "Person to greet.").withDefault("world")

    val quietOpt = Opts.flag("quiet", help = "Whether to be quiet.").orFalse

    (userOpt, quietOpt).mapN { (user, quiet) =>
      if (quiet) println("...")
      else println(s"Hello $user!")
    }
  }
)
