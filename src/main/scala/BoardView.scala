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


class Page(var x: Int, var y: Int, var size: Int, val color: Int, title: String) {
} 

class BoardView(context: Context, attrs:AttributeSet) extends View(context, attrs) with MultiTouchObjectCanvas[Page]{
/*
  var prev_touch_x = 0
  var prev_touch_y = 0
*/
  var page = new Page(200, 100, 100, Color.RED, "test")
  var page_alt = new Page(400, 400, 100, Color.BLUE, "test_alt")

  val multiTouchController = new MultiTouchController[Page](this)


  override def onDraw(canvas: Canvas) {
    super.onDraw(canvas)

    val paint = new Paint()

    paint.setColor(page.color)
    canvas.drawRect(page.x, page.y, page.x + page.size, page.y + page.size, paint)

    paint.setColor(page_alt.color)
    canvas.drawRect(page_alt.x, page_alt.y, page_alt.x + page_alt.size, page_alt.y + page_alt.size, paint)
  }

  override def selectObject(page: Page, point: PointInfo) {
    Toast.makeText(context, page.color.toString, Toast.LENGTH_SHORT).show()
  }
  
  override def setPositionAndScale(page: Page, pos_scale: PositionAndScale, point: PointInfo): Boolean = {
    page.x = pos_scale.getXOff.toInt
    page.y = pos_scale.getYOff.toInt
    page.size = (pos_scale.getScale * 100).toInt
    invalidate()
    return true
  }

  override def getPositionAndScale(page: Page, pos_scale: PositionAndScale) {
    pos_scale.set(page.x, page.y, true, page.size/100, false, page.size/100, page.size/100, false, 0)
  }

  override def getDraggableObjectAtPoint(point: PointInfo): Page = {
    if (point.getX >= page.x && point.getX <= page.x + page.size &&
        point.getY >= page.y && point.getY <= page.y + page.size) return page
    else if (point.getX >= page_alt.x && point.getX <= page_alt.x + page_alt.size &&
             point.getY >= page_alt.y && point.getY <= page_alt.y + page_alt.size) return page_alt
    return null
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
