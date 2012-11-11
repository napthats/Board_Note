package com.napthats.android.everboard

import _root_.android.graphics.Color


object Page {
  def apply(x: Double, y: Double) = new Page(x, y, 100.0, Color.RED, "")
  val dummy = new Page(0.0, 0.0, 0.0, 0, "") {
    override def scale = BoardView.base_scale
    override def dragAndPinch(pos_scale: {val x: Double; val y: Double; val scale: Double}) {
      super.dragAndPinch(pos_scale)
      BoardView.base_x -= pos_scale.x
      BoardView.base_y -= pos_scale.y
      BoardView.base_scale = pos_scale.scale
    }
  }
}

class Page private (var x: Double, var y: Double, var size: Double, val color: Int, title: String) {
  def winX = BoardView.world2WindowPosition(x, y).x
  def winY = BoardView.world2WindowPosition(x, y).y
  def scale = size / 100.0

  def dragAndPinch(pos_scale: {val x: Double; val y: Double; val scale: Double}) {
    this.x = pos_scale.x
    this.y = pos_scale.y
    this.size = pos_scale.scale * 100
  }    
}


//object PageContainer {
  
