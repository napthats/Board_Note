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


class BoardView(context: Context, attrs:AttributeSet) extends View(context, attrs) {
  var ball_x = 100
  var ball_y = 300
  var offset_x = 0
  var offset_y = 0
  val ball_size = 20

  override def onDraw(canvas: Canvas) {
    super.onDraw(canvas)

    val paint = new Paint()
    paint.setColor(Color.RED)

    canvas.drawRect(ball_x, ball_y, ball_x + ball_size, ball_y + ball_size, paint)
  }

  def onTouch(v: View, event: MotionEvent): Boolean = {
    val x: Int = event.getX.toInt
    val y: Int = event.getY.toInt

    event.getAction() match {
      case MotionEvent.ACTION_MOVE => 
        val diff_x = offset_x - x
        val diff_y = offset_y - y
        ball_x -= diff_x
        ball_y -= diff_y
        offset_x = x
        offset_y = y
      case MotionEvent.ACTION_DOWN =>
        offset_x = x
        offset_y = y
      case MotionEvent.ACTION_UP =>
      case _ =>
    }
    //Toast.makeText(context, "test", Toast.LENGTH_SHORT).show()
    this.invalidate()
    return true
  }
}
