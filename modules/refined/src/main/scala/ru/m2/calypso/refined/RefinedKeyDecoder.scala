package ru.m2.calypso.refined

import eu.timepit.refined.api.{Refined, Validate}
import eu.timepit.refined.refineV
import ru.m2.calypso.KeyDecoder

trait RefinedKeyDecoder:

  given [A: KeyDecoder, P](using Validate[A, P]): KeyDecoder[A Refined P] =
    KeyDecoder[A].emap(refineV(_))
