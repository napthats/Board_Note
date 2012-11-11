package com.napthats.android.everboard

import _root_.android.graphics.Canvas
import _root_.android.view.View
import _root_.android.content.Context
import _root_.android.util.AttributeSet
import _root_.android.os.Bundle
import _root_.android.content.res.TypedArray
import _root_.android.graphics.Paint
import _root_.android.graphics.Color
import _root_.android.view.MotionEvent
import _root_.android.widget.Toast

import _root_.org.metalev.multitouch.controller.MultiTouchController;
import _root_.org.metalev.multitouch.controller.MultiTouchController.MultiTouchObjectCanvas;
import _root_.org.metalev.multitouch.controller.MultiTouchController.PointInfo;
import _root_.org.metalev.multitouch.controller.MultiTouchController.PositionAndScale;


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
   

object BoardView {
  var base_x = 0.0
  var base_y = 0.0
  var base_scale = 1.0

  def window2WorldPosition(x_orig: Double, y_orig: Double): {val x: Double; val y: Double} = {
    new {val x = (x_orig / base_scale) + base_x; val y = (y_orig / base_scale) + base_y}
  }

  def world2WindowPosition(x_orig: Double, y_orig: Double): {val x: Double; val y: Double} = {
    new {val x = (x_orig - base_x) * base_scale; val y = (y_orig - base_y) * base_scale}
  }
}
  

class BoardView(context: Context, attrs:AttributeSet) extends View(context, attrs) with MultiTouchObjectCanvas[Page]{
  val multiTouchController = new MultiTouchController[Page](this)

  var page = new Page(200.0, 100.0, 100.0, Color.RED, "test")
  var page_alt = new Page(400.0, 400.0, 100.0, Color.BLUE, "test_alt")
  var page_dummy = new Page(0.0, 0.0, 0.0, 0, "") {
    override def scale = BoardView.base_scale
    override def dragAndPinch(pos_scale: PositionAndScale) {
      super.dragAndPinch(pos_scale)
      val pos = BoardView.window2WorldPosition(pos_scale.getXOff, pos_scale.getYOff)
      BoardView.base_x -= pos.x
      BoardView.base_y -= pos.y
      BoardView.base_scale = pos_scale.getScale
    }
  }
  


  //for Canvas#drawRect
  private implicit def double2Float(x: Double): Float = x.toFloat


  override def onDraw(canvas: Canvas) {
    super.onDraw(canvas)

    val paint = new Paint()

    paint.setColor(page.color)
    canvas.drawRect(
      (page.x - BoardView.base_x) * BoardView.base_scale,
      (page.y - BoardView.base_y) * BoardView.base_scale,
      (page.x + page.size - BoardView.base_x) * BoardView.base_scale,
      (page.y + page.size - BoardView.base_y) * BoardView.base_scale,
      paint
    )

    paint.setColor(page_alt.color)
    canvas.drawRect(
      (page_alt.x - BoardView.base_x) * BoardView.base_scale,
      (page_alt.y - BoardView.base_y) * BoardView.base_scale,
      (page_alt.x + page_alt.size - BoardView.base_x) * BoardView.base_scale,
      (page_alt.y + page_alt.size - BoardView.base_y) * BoardView.base_scale,
      paint
    )
  }

  override def selectObject(page: Page, point: PointInfo) {
    Toast.makeText(context, page.color.toString, Toast.LENGTH_SHORT).show()
  }
  
  override def setPositionAndScale(page: Page, pos_scale: PositionAndScale, point: PointInfo): Boolean = {
    page.dragAndPinch(pos_scale)
    invalidate()
    return true
  }

  override def getPositionAndScale(page: Page, pos_scale: PositionAndScale) {
    pos_scale.set(page.winX, page.winY, true, page.scale, false, page.scale, page.scale, false, 0)
  }

  override def getDraggableObjectAtPoint(point: PointInfo): Page = {
    val pos = BoardView.window2WorldPosition(point.getX, point.getY)
    if (pos.x >= page.x && pos.x <= page.x + page.size &&
        pos.y >= page.y && pos.y <= page.y + page.size) return page
    else if (pos.x >= page_alt.x && pos.x <= page_alt.x + page_alt.size &&
             pos.y >= page_alt.y && pos.y <= page_alt.y + page_alt.size) return page_alt
    return page_dummy
  }

  override def onTouchEvent(event: MotionEvent): Boolean = multiTouchController.onTouchEvent(event)
  
/*
  def onTouch(v: View, event: MotionEvent): Boolean = {
    val x: Int = event.getX.toInt
    val y: Int = event.getY.toInt

    event.getAction() match {
      case MotionEvent.ACTION_MOVE => 
        val diff_x = prev_touch_x - x
        val diff_y = prev_touch_y - y
        page.x -= diff_x
        page.y -= diff_y
        prev_touch_x = x
        prev_touch_y = y
      case MotionEvent.ACTION_DOWN =>
        prev_touch_x = x
        prev_touch_y = y
      case MotionEvent.ACTION_UP =>
      case _ =>
    }
    //Toast.makeText(context, "test", Toast.LENGTH_SHORT).show()
    this.invalidate()
    return true
  }
*/
}
