package dev.insideyou
package play3
package usecase1
package views
package html

import scalatags.Text.all.*

def index(time: LocalTime): doctype =
  main(title = "Welcome to Play")(
    h1(time.rendered),
    button(
      hx.get := routes.Controller.refresh,
      hx.target := previous(h1.tag),
    )("Refresh"),
  )
