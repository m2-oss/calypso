import sbt.Keys._
import sbt._
import sbt.util.CacheStoreFactory

object Boilerplate {
  private final val letters: List[Char] =
    List.range(start = 'B', end = 'Z')

  private final val generateUpTo: Int =
    22

  private sealed trait BoilerplateFile {
    def filename: String

    def generateFileContents(): List[String]
  }

  val generatorTask = Def.task {
    val log = streams.value.log

    val cachedFun =
      FileFunction.cached(
        cacheBaseDirectory = streams.value.cacheDirectory / "boilerplate",
        inStyle = FilesInfo.exists,
        outStyle = FilesInfo.exists
      ) { _ =>
        log.info("Generating boilerplate files")

        Set.empty[ProductDecoders].map { boilerplateFile =>
          // Save the boilerplate file to the managed sources dir.
          val file = (Compile / sourceManaged).value / "boilerplate" / s"${boilerplateFile.filename}.scala"
          log.log(Level.Info, s"Generating file ${file}")

          IO.writeLines(file, lines = boilerplateFile.generateFileContents())

          file
        }
      }

    cachedFun(Set(file("project/Boilerplate.scala"))).toSeq
  }
}
