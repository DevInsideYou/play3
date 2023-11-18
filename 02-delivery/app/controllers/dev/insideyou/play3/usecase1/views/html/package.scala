package dev.insideyou
package play3
package usecase1
package views
package html

import java.time.format.DateTimeFormatter

extension (self: LocalTime)
  private def rendered: String =
    pattern.format(self)

private lazy val pattern: DateTimeFormatter =
  DateTimeFormatter.ofPattern("HH'h' mm'm' ss's' nnn'n'")
