ThisBuild / scalaVersion := "2.13.4"

ThisBuild / organization := "ru.m2"
ThisBuild / organizationName := "m2"
ThisBuild / organizationHomepage := Some(url("https://m2.ru"))

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/m2-oss/calypso"),
    "scm:git@github.com:m2-oss/calypso.git"
  )
)
ThisBuild / developers := List(
  Developer(
    id = "asakaev",
    name = "Akhtiam Sakaev",
    email = "akhtiam.sakaev@gmail.com",
    url = url("https://github.com/asakaev")
  )
)
ThisBuild / licenses := List(
  "MIT License" -> url("https://www.opensource.org/licenses/mit-license")
)
ThisBuild / homepage := Some(url("https://github.com/m2-oss/calypso"))

// Remove all additional repository other than Maven Central from POM
ThisBuild / pomIncludeRepository := (_ => false)

lazy val calypso = (project in file("."))
  .settings(publish / skip := true)
  .aggregate(core)

lazy val core = (project in file("modules/core"))
  .settings(
    name := "calypso-core",
    description := "calypso core",
    libraryDependencies += "org.mongodb" % "bson" % "3.12.0"
  )
