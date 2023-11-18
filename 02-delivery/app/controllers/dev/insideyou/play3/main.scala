package dev.insideyou
package play3

import scalatags.Text.all.*
import scalatags.Text.tags2

def main(title: String)(content: Frag*): doctype =
  doctype("html")(
    html(lang := "en")(
      head(
        tags2.title(title),
        link(
          rel := "stylesheet",
          media := "screen",
          href := controllers.routes.Assets.versioned("stylesheets/main.css"),
        ),
        link(
          rel := "shortcut icon",
          `type` := "image/png",
          href := controllers.routes.Assets.versioned("images/favicon.png"),
        ),
      ),
      body(
        content,
        script(
          src := controllers.routes.Assets.versioned("javascripts/main.js"),
          `type` := "text/javascript",
        ),
        script(
          src := "https://unpkg.com/htmx.org@1.9.8",
          `type` := "text/javascript",
          integrity := "sha384-rgjA7mptc2ETQqXoYC3/zJvkU7K/aP44Y+z7xQuJiVnB/422P/Ak+F/AqFR7E4Wr",
          crossorigin := "anonymous",
        ),
      ),
    )
  )
