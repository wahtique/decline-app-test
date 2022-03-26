package com.github.wahtique

import cats.effect._
import cats.implicits._

import com.monovore.decline._
import com.monovore.decline.effect._

// example from https://ben.kirw.in/decline/effect.html

// Subcommands inputs model

final case class ShowProcesses(all: Boolean)
final case class BuildImage(dockerFile: Option[String], path: String)
final case class Echo(message: String, times: Int)

// quiet flag

val quietFlag: Opts[Boolean] =
  Opts.flag("quiet", "Suppress output", short = "q").orFalse

// `ps` subcommand

val showProcessesOpts: Opts[ShowProcesses] =
  Opts.subcommand("ps", "Lists docker processes running") {
    Opts
      .flag("all", "Show all processes", short = "a")
      .orFalse
      .map(ShowProcesses.apply)
  }

// `build` subcommand

val dockerFileOpts: Opts[Option[String]] =
  Opts
    .option[String]("file", "Name of the dockerfile to use", short = "f")
    .orNone

val pathOpt: Opts[String] =
  Opts.argument[String](metavar = "path")

val buildOpts: Opts[BuildImage] =
  Opts.subcommand("build", "Builds a docker image") {
    (dockerFileOpts, pathOpt).mapN(BuildImage.apply)
  }

// `echo` subcommand

val messageOpts: Opts[String] =
  Opts.argument[String](metavar = "Message to print to stdout")

val timesOpt: Opts[Int] =
  Opts
    .option[Int]("times", "Times the message should be printed", short = "n")
    .withDefault(1)
    .validate("Must be positive")(_ > 0)

val echoOpts: Opts[Echo] =
  Opts.subcommand("echo", "Prints a message to stdout") {
    (messageOpts, timesOpt).mapN(Echo.apply)
  }

// app
object DeclineTestApp
    extends CommandIOApp(
      name = "decline-app-test",
      header = "Faux docker command line app",
      version = "0.1.0"
    ):

  override def main: Opts[IO[ExitCode]] =
    ((showProcessesOpts orElse buildOpts orElse echoOpts), quietFlag).mapN {
      (subCommand, quiet) =>
        val logic = subCommand match
          case ShowProcesses(all) =>
            IO.println(s"Show processes: $all")
          case BuildImage(dockerFile, path) =>
            IO.println(s"Build image: $dockerFile, $path")
          case Echo(message, times) =>
            IO.println(message).replicateA(times).unlessA(quiet)

        logic.as(ExitCode.Success)
    }
