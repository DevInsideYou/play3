package dev.insideyou
package play3
package usecase1

final class Controller(
  boundary: Boundary
)(using
  ControllerComponents
) extends FancyController:
  lazy val index =
    Action.zio:
      boundary.currentTime.map(time => Ok(views.html.index(time)))

  lazy val refresh =
    Action.zio:
      boundary.currentTime.map(time => Ok(views.html.refresh(time)))
