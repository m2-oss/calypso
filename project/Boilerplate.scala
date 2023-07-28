import sbt.Keys._
import sbt._
import sbt.util.CacheStoreFactory

object Boilerplate {
  private final val letters: List[Char] =
    List.range(start = 'B', end = 'Z')

  private final val generateUpTo: Int =
    22

  private sealed trait BoilerplateFile {
    def generateFileContents(): List[String]
  }

  private final case object ProductDecoders extends BoilerplateFile {
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

  private final case object CoproductDecoders extends BoilerplateFile {
    override def generateFileContents(): List[String] = {
      def generateCoproductDecoder(n: Int): List[String] = {
        val parameters = letters.take(n)

        s"final def forCoproduct${n}[A, ${parameters.map(l => s"${l} <: A : Decoder").mkString(", ")}]" ::
          s"(${parameters.map(l => s"t${l.toLower}: String").mkString(", ")}): Decoder[A] =" ::
          "Decoder.instance { bson =>" ::
          "for {" ::
          "doc <- bson.focus" ::
          "tag <- doc.downField(\"tag\").as[String]" ::
          "a <- tag match {" ::
          parameters.map(l =>
            s"case t if t == t${l.toLower} => doc.downField(${'"'}value${'"'}).as[${l}]"
          ) :::
          "case _ => Left(s\"Tag.unknown: [$tag]\")" ::
          "}" ::
          "} yield a" ::
          "}" ::
          Nil
      }

      "package ru.m2.calypso.boilerplate" ::
        "import ru.m2.calypso.Decoder" ::
        "import ru.m2.calypso.syntax._" ::
        "trait CoproductDecoders {" ::
        List.tabulate(generateUpTo)(n => generateCoproductDecoder(n + 1)).flatten :::
        "}" ::
        Nil
    }
  }

  private final case object ProductEncoders extends BoilerplateFile {
    override def generateFileContents(): List[String] = {
      def generateCoproductDecoder(n: Int): List[String] = {
        val parameters = letters.take(n)

        s"final def forProduct${n}[A, ${parameters.map(l => s"${l} : Encoder").mkString(", ")}]" ::
          s"(${parameters.map(l => s"n${l.toLower}: String").mkString(", ")})" ::
          s"(fun: A => (${parameters.mkString(", ")})): Encoder[A] =" ::
          "Encoder.instance { a =>" ::
          s"val (${parameters.map(_.toLower).mkString(", ")}) = fun(a)" ::
          "Bson.obj(" ::
          parameters.map(l => s"n${l.toLower} -> ${l.toLower}.asBson,") :::
          ")" ::
          "}" ::
          Nil
      }

      "package ru.m2.calypso.boilerplate" ::
        "import ru.m2.calypso.{Bson, Encoder}" ::
        "import ru.m2.calypso.syntax._" ::
        "trait ProductEncoders {" ::
        List.tabulate(generateUpTo)(n => generateCoproductDecoder(n + 1)).flatten :::
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
          ProductDecoders,
          CoproductDecoders,
          ProductEncoders
        ).map { boilerplateFile =>
          // Save the boilerplate file to the managed sources dir.
          val file = (Compile / sourceManaged).value / "boilerplate" / s"${boilerplateFile}.scala"
          log.log(Level.Info, s"Generating file ${file}")

          IO.writeLines(file, lines = boilerplateFile.generateFileContents())

          file
        }
      }

    cachedFun(Set(file("project/Boilerplate.scala"))).toSeq
  }
}
