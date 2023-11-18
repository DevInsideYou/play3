package dev.insideyou
package play3

import play.api.*

object Program:
  def make(context: ApplicationLoader.Context): Application =
    PlayApp.make(context): (httpErrorHandler, assets) =>
      router.Routes(
        httpErrorHandler,
        usecase1.controller,
        assets,
      )
