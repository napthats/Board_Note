package com.napthats.android.everboard

import _root_.org.metalev.multitouch.controller.MultiTouchController
import _root_.org.metalev.multitouch.controller.MultiTouchController.MultiTouchObjectCanvas
import _root_.org.metalev.multitouch.controller.MultiTouchController.PointInfo
import _root_.org.metalev.multitouch.controller.MultiTouchController.PositionAndScale


class Page(var x: Double, var y: Double, var size: Double, val color: Int, title: String) {
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
