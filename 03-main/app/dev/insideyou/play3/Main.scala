package dev.insideyou
package play3

import play.api.*

final class Main extends ApplicationLoader:
  override def load(context: ApplicationLoader.Context): Application =
    Program.make(context)
