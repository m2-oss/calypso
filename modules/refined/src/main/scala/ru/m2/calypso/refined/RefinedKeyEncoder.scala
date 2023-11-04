package ru.m2.calypso.refined

import eu.timepit.refined.api.Refined
import ru.m2.calypso.KeyEncoder

trait RefinedKeyEncoder:

  given [A: KeyEncoder, P]: KeyEncoder[A Refined P] =
    KeyEncoder[A].contramap(_.value)
