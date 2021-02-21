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
  .aggregate(core, testing, tests)

lazy val core = (project in file("modules/core"))
  .settings(
    name := "calypso-core",
    description := "calypso core",
    libraryDependencies ++= List(
      "eu.timepit"        %% "refined"         % "0.9.21",
      "org.mongodb"        % "bson"            % "4.2.1",
      "org.typelevel"     %% "cats-core"       % "2.4.2",
      "com.ironcorelabs"  %% "cats-scalatest"  % "3.0.8"   % "test",
      "org.scalatest"     %% "scalatest"       % "3.2.5"   % "test",
      "org.scalatestplus" %% "scalacheck-1-14" % "3.2.2.0" % "test"
    )
  )

lazy val testing = (project in file("modules/testing"))
  .settings(
    name := "calypso-testing",
    description := "calypso testing",
    libraryDependencies ++= List(
      "org.typelevel" %% "cats-laws" % "2.4.2"
    )
  )
  .dependsOn(core)

lazy val tests = (project in file("modules/tests"))
  .settings(
    name := "calypso-tests",
    description := "calypso tests",
    libraryDependencies ++= List(
      "com.github.alexarchambault" %% "scalacheck-shapeless_1.14" % "1.2.5"     % "test",
      "eu.timepit"                 %% "refined-scalacheck"        % "0.9.21"    % "test",
      "org.typelevel"              %% "discipline-scalatest"      % "1.0.0-RC2" % "test"
    ),
    publish / skip := true
  )
  .dependsOn(core, testing)
