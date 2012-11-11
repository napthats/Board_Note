package com.napthats.android.everboard

import _root_.android.graphics.Color
import _root_.android.graphics.Paint
import _root_.android.graphics.Canvas


object Page {
  private val width = 100.0
  private val height = 20.0
  private def winWidth = width * BoardView.base_scale
  private def winHeight = height * BoardView.base_scale
  def apply(x: Double, y: Double, color: Int) = new Page(x, y, color, "")
  val dummy = new Page(0.0, 0.0, 0, "") {
    override def scale = BoardView.base_scale
    override def scale_=(x: Double) {}
    override def dragAndPinch(pos_scale: {val x: Double; val y: Double; val scale: Double}) {
      super.dragAndPinch(pos_scale)
      BoardView.base_x -= pos_scale.x
      BoardView.base_y -= pos_scale.y
      BoardView.base_scale = pos_scale.scale
    }
  }
}

class Page private (var x: Double, var y: Double, val color: Int, title: String) {
  //It means "var scale" for overriding
  private[this] var _scale = 1.0
  def scale = _scale
  def scale_=(x: Double) {_scale = x}

  def winX = BoardView.world2WindowPosition(x, y).x
  def winY = BoardView.world2WindowPosition(x, y).y
  def width = Page.width * scale
  def height = Page.height * scale
  def winWidth = Page.winWidth * scale
  def winHeight = Page.winHeight * scale

  private implicit def double2Float(x: Double): Float = x.toFloat
  def dragAndPinch(pos_scale: {val x: Double; val y: Double; val scale: Double}) {
    this.x = pos_scale.x
    this.y = pos_scale.y
    this.scale = pos_scale.scale
  }    

  def containPoint(pos: {val x: Double; val y: Double}): Boolean =
    pos.x >= x && pos.x <= x + width && pos.y >= y && pos.y <= y + height

  //TODO: change parameter to window-coordinated canvas
  def draw(canvas: Canvas) {
    val paint = new Paint()
    paint.setColor(color)
    canvas.drawRect(winX, winY, winX + winWidth, winY + winHeight, paint)
    paint.setColor(Color.WHITE)
    canvas.drawRect(winX + winWidth * 0.05, winY + winHeight * 0.05, winX + winWidth * 0.95, winY + winHeight * 0.95, paint)
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
