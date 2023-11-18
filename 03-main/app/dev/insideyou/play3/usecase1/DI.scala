package dev.insideyou
package play3
package usecase1

def controller: Using[ControllerComponents, Controller] =
  Controller(
    boundary = BusinessLogic.make
  )
