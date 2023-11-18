import Dependencies._
import MyUtil._

ThisBuild / organization := "dev.insideyou"
ThisBuild / scalaVersion := "3.3.1"

lazy val play3 =
  project
    .in(file("."))
    .settings(commonSettings)
    .aggregate(core, delivery, main)

lazy val core =
  project
    .in(file("01-core"))
    .settings(commonSettings)
    .settings(commonAutoImports)
    .settings(commonDependencies)

lazy val delivery =
  project
    .in(file("02-delivery"))
    .enablePlugins(PlayScala)
    .dependsOn(core % Cctt)
    .settings(commonSettings)
    .settings(commonPlaySettings)
    .settings(commonAutoImports)
    .settings(withPlayAutoImports)
    .settings(
      libraryDependencies ++= Seq(
        com.lihaoyi.scalatags
      )
    )

lazy val persistence =
  project
    .in(file("02-persistence"))
    .dependsOn(core % Cctt)
    .settings(commonSettings)
    .settings(commonAutoImports)

lazy val main =
  project
    .in(file("03-main"))
    .enablePlugins(PlayScala)
    .dependsOn(delivery % Cctt)
    .dependsOn(persistence % Cctt)
    .settings(commonSettings)
    .settings(commonPlaySettings)
    .settings(commonAutoImports)
    .settings(withPlayAutoImports)

lazy val commonSettings = {
  lazy val commonScalacOptions = Seq(
    Compile / console / scalacOptions := {
      (Compile / console / scalacOptions)
        .value
        .filterNot(_.contains("wartremover"))
        .filterNot(Scalac.Lint.toSet)
        .filterNot(Scalac.FatalWarnings.toSet) :+ "-Wconf:any:silent"
    },
    Test / console / scalacOptions :=
      (Compile / console / scalacOptions).value,
  )

  lazy val otherCommonSettings = Seq(
    update / evictionWarningOptions := EvictionWarningOptions.empty
    // cs launch scalac:3.3.1 -- -Wconf:help
    // src is not yet available for Scala3
    // scalacOptions += s"-Wconf:src=${target.value}/.*:s",
  )

  Seq(
    commonScalacOptions,
    otherCommonSettings,
  ).reduceLeft(_ ++ _)
}

lazy val commonPlaySettings = Seq(
  wartremoverExcluded ++= (Compile / routes).value,
  wartremoverExcluded += (play.twirl.sbt.Import.TwirlKeys.compileTemplates / target).value,
)

lazy val commonAutoImports = Seq(
  scalacOptions +=
    Seq(
      "java.lang",
      "scala",
      "scala.Predef",
      "scala.annotation",
      "scala.util.chaining",
      "zio",
    ).mkString(start = "-Yimports:", sep = ",", end = ""),
  Test / scalacOptions +=
    Seq(
      "org.scalacheck",
      "org.scalacheck.Prop",
    ).mkString(start = "-Yimports:", sep = ",", end = ""),
)

lazy val withPlayAutoImports = Seq(
  scalacOptions +=
    Seq(
      "play.api.mvc"
    ).mkString(
      start = scalacOptions.value.find(_.startsWith("-Yimports:")).get + ",",
      sep = ",",
      end = "",
    )
)

lazy val commonDependencies = Seq(
  libraryDependencies ++= Seq(
    dev.zio.zio
  ),
  libraryDependencies ++= Seq(
    com.eed3si9n.expecty.expecty,
    org.scalacheck.scalacheck,
    org.scalameta.`munit-scalacheck`,
    org.scalameta.munit,
    org.typelevel.`discipline-munit`,
  ).map(_ % Test),
)
