package com.napthats.android.everboard

import _root_.android.graphics.Color
import _root_.android.graphics.Paint
import _root_.android.graphics.Canvas


object Page {
  def apply(x: Double, y: Double, color: Int) = new Page(x, y, 100.0, color, "")
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
  private def winSize = size * BoardView.base_scale

  private implicit def double2Float(x: Double): Float = x.toFloat
  def dragAndPinch(pos_scale: {val x: Double; val y: Double; val scale: Double}) {
    this.x = pos_scale.x
    this.y = pos_scale.y
    this.size = pos_scale.scale * 100
  }    

  def containPoint(pos: {val x: Double; val y: Double}): Boolean =
    pos.x >= x && pos.x <= x + size && pos.y >= y && pos.y <= y + size

  def draw(canvas: Canvas) {
    val paint = new Paint()
    paint.setColor(color)
    canvas.drawRect(winX, winY, winX + winSize, winY + winSize, paint)
  }
}


object PageContainer {
  private var page_list: List[Page] = List()

  def add(page: Page) {page_list = page :: page_list}

  def getAtPoint(pos: {val x: Double; val y: Double}): Option[Page] =
    page_list.find(_.containPoint(pos))

  def drawAll(canvas: Canvas) {
    page_list.reverseMap(_.draw(canvas))
  }
}
