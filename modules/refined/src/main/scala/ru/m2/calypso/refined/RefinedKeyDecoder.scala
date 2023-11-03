package ru.m2.calypso.refined

import eu.timepit.refined.api.{Refined, Validate}
import eu.timepit.refined.refineV
import ru.m2.calypso.KeyDecoder

trait RefinedKeyDecoder {
  implicit def decodeKeyRefined[A: KeyDecoder, P](implicit
      v: Validate[A, P]
  ): KeyDecoder[A Refined P] = KeyDecoder[A].emap(refineV(_))
}
