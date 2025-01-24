ThisBuild / scalaVersion := "3.6.2"

ThisBuild / organization         := "ru.m2"
ThisBuild / organizationName     := "m2"
ThisBuild / organizationHomepage := Some(url("https://m2.ru"))

ThisBuild / versionScheme := Some("early-semver")

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
  ),
  Developer(
    id = "gaponenko-andrei",
    name = "Andrei Gaponenko",
    email = "agp32.in@gmail.com",
    url = url("https://github.com/gaponenko-andrei")
  )
)
ThisBuild / licenses := List(
  "MIT License" -> url("https://www.opensource.org/licenses/mit-license")
)
ThisBuild / homepage := Some(url("https://github.com/m2-oss/calypso"))

ThisBuild / scalacOptions ++= List(
  "-Werror"
)

lazy val calypso = (project in file("."))
  .settings(
    publish / skip := true
  )
  .aggregate(core, tests, refined, refinedTests, scalapb, scalapbTests, testing)

lazy val core = (project in file("modules/core"))
  .settings(
    name        := "calypso-core",
    description := "calypso core",
    libraryDependencies ++= List(
      "org.mongodb"    % "bson"             % "5.3.1",
      "org.typelevel" %% "cats-core"        % "2.12.0",
      "org.scalameta" %% "munit"            % "1.0.3" % Test,
      "org.scalameta" %% "munit-scalacheck" % "1.0.0" % Test
    ),
    Compile / sourceGenerators += Boilerplate.generatorTask.taskValue,
    Compile / unmanagedSourceDirectories += baseDirectory.value / s"target/scala-${scalaVersion.value}/src_managed"
  )

lazy val tests = (project in file("modules/tests"))
  .settings(
    name        := "calypso-tests",
    description := "calypso tests",
    libraryDependencies ++= List(
      "org.typelevel" %% "discipline-munit" % "2.0.0" % Test
    ),
    publish / skip := true
  )
  .dependsOn(core, testing)

lazy val refined = (project in file("modules/refined"))
  .settings(
    name        := "calypso-refined",
    description := "calypso refined",
    libraryDependencies ++= List(
      "eu.timepit" %% "refined" % "0.11.2"
    )
  )
  .dependsOn(core)

lazy val refinedTests = (project in file("modules/refined-tests"))
  .settings(
    name        := "calypso-refined-tests",
    description := "calypso refined tests",
    libraryDependencies ++= List(
      "eu.timepit"    %% "refined-scalacheck" % "0.11.2" % Test,
      "org.typelevel" %% "discipline-munit"   % "2.0.0"  % Test
    ),
    publish / skip := true
  )
  .dependsOn(refined, testing)

lazy val scalapb = (project in file("modules/scalapb"))
  .settings(
    name        := "calypso-scalapb",
    description := "calypso scalapb",
    libraryDependencies ++= List(
      "com.thesamet.scalapb" %% "scalapb-runtime" % "0.11.17"
    )
  )
  .dependsOn(core)

lazy val scalapbTests = (project in file("modules/scalapb-tests"))
  .settings(
    name        := "calypso-scalapb-tests",
    description := "calypso scalapb tests",
    libraryDependencies ++= List(
      "org.typelevel" %% "discipline-munit" % "2.0.0" % Test
    ),
    publish / skip := true
  )
  .dependsOn(scalapb, testing)

lazy val testing = (project in file("modules/testing"))
  .settings(
    name        := "calypso-testing",
    description := "calypso testing",
    libraryDependencies ++= List(
      "org.typelevel" %% "cats-laws" % "2.12.0"
    )
  )
  .dependsOn(core)
