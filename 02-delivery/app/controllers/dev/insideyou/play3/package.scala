package dev.insideyou
package play3

import java.nio.charset.StandardCharsets

import org.apache.pekko.util.ByteString
import play.api.http.*
import scalatags.Text.all.*
import scalatags.generic.Attr

type Using[-I, +O] = I ?=> O

given Writeable[scalatags.Text.all.doctype] =
  Writeable(
    transform = doctype => ByteString(doctype.render, StandardCharsets.UTF_8),
    contentType = Some(ContentTypes.HTML),
  )

extension [R[A], B](self: ActionBuilder[R, B])
  def zio[R1 >: Scope, E <: Throwable](f: Using[R[B], ZIO[R1, E, Result]]): Action[B] =
    self.async: rb =>
      Unsafe.unsafely:
        Runtime.default.unsafe.runToFuture(ZIO.scoped(f(using rb)))

object hx:
  private def hx(suffix: String): Attr = attr(s"hx-$suffix")

  lazy val get = hx("get")
  lazy val target = hx("target")

given AttrValue[Call] = genericAttr[Call]

def previous(in: String): String = s"previous $in"
