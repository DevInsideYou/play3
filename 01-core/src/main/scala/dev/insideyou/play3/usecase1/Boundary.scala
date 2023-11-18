package dev.insideyou
package play3
package usecase1

trait Boundary:
  def currentTime: UIO[LocalTime]
