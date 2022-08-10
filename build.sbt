ThisBuild / scalaVersion := "2.13.8"

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
  ),
  Developer(
    id = "svol",
    name = "Vsevolod Levin",
    email = "vsevolod.levin@gmail.com",
    url = url("https://github.com/svol")
  ),
  Developer(
    id = "k7i3",
    name = "Klim Yakovlev",
    email = "klim.yakovlev@gmail.com",
    url = url("https://github.com/k7i3")
  ),
  Developer(
    id = "twizty",
    name = "Maxim Davydov",
    email = "maxim.a.davydoff@gmail.com",
    url = url("https://github.com/Twizty")
  ),
  Developer(
    id = "alexk412",
    name = "Alexander Kovalenko",
    email = "alexkovalenko412@gmail.com",
    url = url("https://github.com/alexk412")
  ),
  Developer(
    id = "sapizhak",
    name = "Bogdan Sapizhak",
    email = "gohcbro@gmail.com",
    url = url("https://github.com/sapizhak")
  )
)
ThisBuild / licenses := List(
  "MIT License" -> url("https://www.opensource.org/licenses/mit-license")
)
ThisBuild / homepage := Some(url("https://github.com/m2-oss/calypso"))

lazy val calypso = (project in file("."))
  .settings(publish / skip := true)
  .aggregate(core, scalapb, testing, tests, scalapbTests)

lazy val core = (project in file("modules/core"))
  .settings(
    name := "calypso-core",
    description := "calypso core",
    libraryDependencies ++= List(
      "eu.timepit"        %% "refined"         % "0.9.27",
      "org.mongodb"        % "bson"            % "4.2.3",
      "org.typelevel"     %% "cats-core"       % "2.6.1",
      "com.ironcorelabs"  %% "cats-scalatest"  % "3.1.1"   % "test",
      "org.scalatest"     %% "scalatest"       % "3.2.9"   % "test",
      "org.scalatestplus" %% "scalacheck-1-14" % "3.2.2.0" % "test"
    )
  )

lazy val scalapb = (project in file("modules/scalapb"))
  .settings(
    name := "calypso-scalapb",
    description := "calypso scalapb",
    libraryDependencies ++= List(
      "com.thesamet.scalapb" %% "scalapb-runtime" % "0.11.0"
    )
  )
  .dependsOn(core)

lazy val scalapbTests = (project in file("modules/scalapb-tests"))
  .settings(
    name := "calypso-scalapb-tests",
    description := "calypso scalapb tests",
    libraryDependencies ++= List(
      "com.github.alexarchambault" %% "scalacheck-shapeless_1.15" % "1.3.0"  % "test",
      "eu.timepit"                 %% "refined-scalacheck"        % "0.9.27" % "test",
      "org.typelevel"              %% "discipline-scalatest"      % "2.1.5"  % "test"
    ),
    publish / skip := true
  )
  .dependsOn(scalapb, testing)

lazy val testing = (project in file("modules/testing"))
  .settings(
    name := "calypso-testing",
    description := "calypso testing",
    libraryDependencies ++= List(
      "org.typelevel" %% "cats-laws" % "2.6.1"
    )
  )
  .dependsOn(core)

lazy val tests = (project in file("modules/tests"))
  .settings(
    name := "calypso-tests",
    description := "calypso tests",
    libraryDependencies ++= List(
      "com.github.alexarchambault" %% "scalacheck-shapeless_1.15" % "1.3.0"  % "test",
      "eu.timepit"                 %% "refined-scalacheck"        % "0.9.27" % "test",
      "org.typelevel"              %% "discipline-scalatest"      % "2.1.5"  % "test"
    ),
    publish / skip := true
  )
  .dependsOn(core, testing)
