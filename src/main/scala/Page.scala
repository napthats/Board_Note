package com.napthats.android.everboard

import _root_.org.metalev.multitouch.controller.MultiTouchController
import _root_.org.metalev.multitouch.controller.MultiTouchController.MultiTouchObjectCanvas
import _root_.org.metalev.multitouch.controller.MultiTouchController.PointInfo
import _root_.org.metalev.multitouch.controller.MultiTouchController.PositionAndScale

import _root_.android.graphics.Color


object Page {
  def apply(x: Double, y: Double) = new Page(x, y, 100.0, Color.RED, "")
  def getDummy = new Page(0.0, 0.0, 0.0, 0, "") {
    override def scale = BoardView.base_scale
    override def dragAndPinch(pos_scale: PositionAndScale) {
      super.dragAndPinch(pos_scale)
      val pos = BoardView.window2WorldPosition(pos_scale.getXOff, pos_scale.getYOff)
      BoardView.base_x -= pos.x
      BoardView.base_y -= pos.y
      BoardView.base_scale = pos_scale.getScale
    }
  }
}

class Page private (var x: Double, var y: Double, var size: Double, val color: Int, title: String) {
  def winX = BoardView.world2WindowPosition(x, y).x
  def winY = BoardView.world2WindowPosition(x, y).y
  def scale = size / 100.0

  def dragAndPinch(pos_scale: PositionAndScale) {
    val pos = BoardView.window2WorldPosition(pos_scale.getXOff, pos_scale.getYOff)
    this.x = pos.x
    this.y = pos.y
    this.size = pos_scale.getScale * 100
  }    
}
