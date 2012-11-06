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


class Page(var x: Int, var y: Int, val size: Int, title: String) {
} 

class BoardView(context: Context, attrs:AttributeSet) extends View(context, attrs) {
  var prev_touch_x = 0
  var prev_touch_y = 0
  var page = new Page(200, 100, 20, "test")

  override def onDraw(canvas: Canvas) {
    super.onDraw(canvas)

    val paint = new Paint()
    paint.setColor(Color.RED)

    canvas.drawRect(page.x, page.y, page.x + page.size, page.y + page.size, paint)
  }

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
}
