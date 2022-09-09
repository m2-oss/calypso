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

  private final case object ProductDecoders extends BoilerplateFile {
    override final val filename: String = "ProductDecoders"

    override def generateFileContents(): List[String] = {
      def generateProductDecoder(n: Int): List[String] = {
        val parameters = letters.take(n)


        s"final def forProduct${n}[A, ${parameters.map(l => s"${l}: Decoder").mkString(", ")}]" ::
        s"(${parameters.map(l => s"n${l.toLower}: String").mkString(", ")})" ::
        s"(fun: (${parameters.mkString(", ")}) => A): Decoder[A] =" ::
        "Decoder.instance { bson =>" ::
        "for {" ::
        "doc <- bson.focus" ::
        parameters.map(l => s"${l.toLower} <- doc.downField(n${l.toLower}).as[${l}]") :::
        s"} yield fun(${parameters.map(_.toLower).mkString(", ")})" ::
        "}" ::
        Nil
      }

      "package ru.m2.calypso.boilerplate" ::
      "import ru.m2.calypso.Decoder" ::
      "import ru.m2.calypso.syntax._" ::
      "trait ProductDecoders {" ::
      List.tabulate(generateUpTo)(n => generateProductDecoder(n + 1)).flatten :::
      "}" ::
      Nil
    }
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

        Set(
          ProductDecoders
        ).map { boilerplateFile =>
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
