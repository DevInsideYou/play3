package dev.insideyou
package play3

import controllers.*
import play.api.*
import play.filters.HttpFiltersComponents
import router.Routes

object PlayApp:
  def make(
    context: ApplicationLoader.Context
  )(
    makeRoutes: Using[ControllerComponents, (http.HttpErrorHandler, Assets) => Routes]
  ): Application =
    val playApp =
      new BuiltInComponentsFromContext(context) with HttpFiltersComponents with AssetsComponents:
        override lazy val router: Routes =
          makeRoutes(using controllerComponents)(httpErrorHandler, assets)

    playApp.application
